#!/bin/sh

while true
do
#	ffmpeg -re -i /home/marcin/Downloads/voxnews.dv -map 0:0 -vcodec libx264 -bf 0 -f rtp rtp://localhost:5004
	ffmpeg -re -i /home/marcin/Downloads/trailer.mp4 -map 0:0 -vcodec libx264 -bf 0 -f rtp rtp://localhost:5004
done
