'''
Created on Aug 31, 2015

@author: marcin
'''
import subprocess
from time import sleep
import time
import re
import urllib
import requests
import logging

logging.basicConfig(filename='capture.log',level=logging.INFO)
# logging.basicConfig(level=logging.DEBUG)

STATS = {}

VLC_CMD = '/usr/bin/cvlc'
MITSU_CMD = '/home/marcin/workspace/transcoderwebapiscripts/mitsuLinux'
SH_CMD = '/bin/sh'
FILE_NAME = '/tmp/{0}.mp4'

RTSP = 'rtsp://127.0.0.1:8554/plainrtp'
WIDTH=1280
HEIGHT=720
FPS=15

STATS_LEN=10
STATS_FILE='capture.csv'

ENCODER_SERVER = 'http://localhost:8080/transcoderwebapi/rest/configuration'

DEFAULT_SETTINGS = {'fps': '20', 'bitrate' : 'MB_1', 'size' : 'HD'}


def apply_rules(params):
    settings = DEFAULT_SETTINGS

    if params['Noise'] < 0.91:
        settings['bitrate'] = 'MB_2'

    return settings

def start_process(cmd):
    logging.debug("CMD: {0}".format(cmd))
    return subprocess.Popen(cmd.split(' '), stdout=subprocess.PIPE)

def read_data(process):
    out_b, err_b  = process.communicate()
    out_str = out_b.decode("utf-8")
    return out_str[out_str.index('Average'):]

def parse_data(data):
    data_map = {}
    matches = re.findall('\w+[ ]?: \d\.\d+', data)
    for match in matches:
        tokens = match.split(':')
        data_map[tokens[0].strip()] = float(tokens[1].strip())
    return data_map

def feedback_to_server(settings):
    logging.debug("server feedback: {0}".format(settings))

    params = urllib.parse.urlencode({
      'bitrate': settings['bitrate'],
      'fps': settings['fps'],
      'size': settings['size']
    })
    request = requests.put('{0}?{1}'.format(ENCODER_SERVER, params))
    logging.debug(request)
    logging.debug(request.text)

def collect_stats(stats):

    for k, v in stats.items():
        if k in STATS:
            STATS[k].append(v)
        else:
            STATS[k] = []
            STATS[k].append(v)

def stats_collected():
    item = next (iter (STATS.values()))
    return len(item) == STATS_LEN

def export_stats_csv():
    
    cvs_file = open(STATS_FILE, 'w')
    keys = list(STATS.keys())
    keys.sort()
    for key in keys:
        cvs_file.write(key)
        cvs_file.write('\n')
        values = STATS.get(key)
        for value in values:
            cvs_file.write(str(value));
            cvs_file.write('\t');
        cvs_file.write('\n')
    cvs_file.close()

if __name__ == '__main__':

    logging.debug('main')

    curr_settings = {}
    while True:
        try:
            file_name = FILE_NAME.format(time.strftime('%Y%m%d.%H%M%S'))
            logging.debug("output file name: {0}".format(file_name))

            vlc_cmd = '{0} -vvv {1} --sout file/ts:{2}'.format(VLC_CMD, RTSP, file_name)
            process_vlc = start_process(vlc_cmd)
            sleep(15)
            process_vlc.kill()

            mitsu_cmd = '{0} {1} {2} {3} {4}'.format(MITSU_CMD, file_name, WIDTH, HEIGHT, FPS)
            process_mitsu = start_process(mitsu_cmd)
            data = read_data(process_mitsu)
            stats = parse_data(data)
            collect_stats(stats)
            if stats_collected():
                export_stats_csv()
                logging.info("STATS stored in the CSV file") 
                exit(0)
            logging.info("file stats: {0}".format(stats))

            settings = apply_rules(stats)
#             if(settings != curr_settings):
#                 feedback_to_server(settings)
            curr_settings = settings
        except Exception as exc:
            logging.error(exc)
#        sleep(5)
