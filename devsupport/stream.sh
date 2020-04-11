#!/bin/sh

# Script for re-streaming a video file using FFmpeg

usage() {
  echo "Usage: $0 <input_file> <output_url>"
}

if [ ! $# -eq 2 ]; then
  usage
  exit
fi

FILENAME=$1
URL=$2

ffmpeg -stream_loop -1 -re -i $FILENAME -c copy -map 0 -f mpegts $URL


