# SpringMVC
sample spring mvc app
init

ffmpeg -f v4l2 -framerate 25 -video_size 640x480 -i /dev/video0 -vcodec libx264 -f rtp rtp://localhost:5004
ffmpeg -re -i AV36_1.AVI -map 0:0 -vcodec libx264 -bf 0 -f rtp rtp://localhost:5004

