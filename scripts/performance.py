'''
Created on Sep 3, 2015

@author: marcin
'''
import logging
import subprocess
from time import sleep
import sys
import os
import tempfile

TOP_CMD = 'ls'

#logging.basicConfig(filename='capture.log',level=logging.DEBUG)
logging.basicConfig(level=logging.DEBUG)

def read_data():
    f = tempfile.TemporaryFile() 
    p = subprocess.Popen(["top"], stdout=f)
    sleep(1)
    p.terminate()
    p.wait(1, 1)
    f.seek(0) # rewind to the beginning of the file
    print(f.read().decode("utf-8"))
    f.close()    

if __name__ == '__main__':

    logging.info('main')

    while True:
        try:
            data = read_data()
            logging.debug(data)
        except Exception as exc:
            logging.error(exc)
        sleep(1)
