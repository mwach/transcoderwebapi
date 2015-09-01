'''
Created on Aug 31, 2015

@author: marcin
'''
import subprocess
from time import sleep
import time
import fcntl
import os
import select
import re
import decimal
import urllib
import urllib3
import requests

VLC_CMD = '/usr/bin/vlc'
MITSU_CMD = '/home/marcin/workspace/transcoderwebapiscripts/mitsuLinux'
SH_CMD = '/bin/sh'
FILE_NAME = '/tmp/{0}.mp4'

RTSP = 'rtsp://192.168.1.14:8554/plainrtp'
WIDTH=320
HEIGHT=240
FPS=25

ENCODER_SERVER = 'http://localhost:8080/transcoderwebapi/rest/configuration'

def check_rules(params):
    settings = {}
    settings['fps'] = '20'
    settings['bitrate'] = 'MB_1'
    settings['size'] = 'HD'
    
    if params['Noise'] < 0.91:
        settings['bitrate'] = 'MB_2'

    return settings

def start_process(cmd):
    cmd_arr = cmd.split(' ')
    process = subprocess.Popen(cmd_arr, stdout=subprocess.PIPE)
    return process;

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
    params = urllib.parse.urlencode({
      'bitrate': settings['bitrate'],
      'fps': settings['fps'],
      'size': settings['size']
    })
    request = requests.put('{0}?{1}'.format(ENCODER_SERVER, params))
    print(request)
    print(request.text)


if __name__ == '__main__':

    curr_settings = {}
    while True:
        try:
            file_name = FILE_NAME.format(time.strftime('%Y%m%d.%H%M%S'))
            vlc_cmd = '{0} -vvv {1} --sout file/ts:{2}'.format(VLC_CMD, RTSP, file_name)
            process_vlc = start_process(vlc_cmd)
            sleep(5)
            process_vlc.kill()

            mitsu_cmd = '{0} {1} {2} {3} {4} 100 0'.format(MITSU_CMD, file_name, WIDTH, HEIGHT, FPS)
            process_mitsu = start_process(mitsu_cmd)
            data = read_data(process_mitsu)
            stats = parse_data(data)
            settings = check_rules(stats)
            if(settings != curr_settings):
                feedback_to_server(settings)
            curr_settings = settings
        except Exception as exc:
            print(exc)
        sleep(5)