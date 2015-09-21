'''
Created on 21 Sep 2015

@author: mawa
'''
import os


INPUT_DIR = '/home/mawa/Downloads/stats/hd'
OUTPUT_FILE = '/home/mawa/Downloads/stats_hd.csv'

TOTAL_STATS = {}


def get_files(path):

    files = os.listdir(path)
    files.sort()
    return files

def add_stat(stat_name, stat_value, file_name):
    if not TOTAL_STATS.has_key(stat_name):
        TOTAL_STATS[stat_name] = {}
    bag = TOTAL_STATS[stat_name]
    bag[file_name] = stat_value
    
    
def parse_file(file_path):
    file_desc = open(file_path, 'r')
    while(True):
        stat_name = file_desc.readline();
        stat_value = file_desc.readline()
        if(stat_name == None or len(stat_name) == 0):
            break
        add_stat(stat_name.strip(), stat_value.strip(), os.path.basename(file_path))

    file_desc.close()

def export_stats():
    
    fwrite = open(OUTPUT_FILE, 'w')
    stat_names = TOTAL_STATS.keys()
    stat_names.sort()
    for stat_name in stat_names:
        fwrite.write(stat_name)
        fwrite.write('\n')
        stat_values = TOTAL_STATS[stat_name]
        file_names = stat_values.keys()
        file_names.sort()
        for file_name in file_names:
            fwrite.write(file_name)
            fwrite.write('\t')
            fwrite.write(stat_values[file_name])
            fwrite.write('\n') 
    fwrite.close()

if __name__ == '__main__':
    files = get_files(INPUT_DIR)
    for file_item in files:
        parse_file(os.path.join(INPUT_DIR, file_item))
    export_stats()