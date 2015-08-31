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

VLC_CMD = '/usr/bin/vlc'
RTSP = 'rtsp://192.168.1.14:8554/plainrtp'
FILE_NAME = '/tmp/{0}.mp4'

VLC_FULL_CMD = '{0} -vvv {1} --sout file/ts:{2}'.format(VLC_CMD, RTSP, '{}')

MITSU_CMD = '/home/marcin/workspace/transcoderwebapiscripts/mitsuLinux'
MITSU_FULL_CMD = '{0} {1} 320 240 25 100 0'

SH_CMD = '/bin/sh'

def start_process(cmd):
    cmd_arr = cmd.split(' ')
    process = subprocess.Popen(cmd_arr, stdout=subprocess.PIPE)
    return process;

def read_data(process):
    out, err = process.communicate()
    out_str = out.decode("utf-8")
    print(out_str[out_str.index('Average'):])

if __name__ == '__main__':
    while True:
        try:
            file_name = FILE_NAME.format(time.strftime('%Y%m%d.%H%M%S'))
            vlc_cmd = VLC_FULL_CMD.format(file_name)
            process_vlc = start_process(vlc_cmd)
            sleep(5)
            process_vlc.kill()
            
            mitsu_cmd = MITSU_FULL_CMD.format(MITSU_CMD, file_name)
            process_mitsu = start_process(mitsu_cmd)
            data = read_data(process_mitsu)
            
        except Exception as exc:
            print(exc)
        sleep(50)