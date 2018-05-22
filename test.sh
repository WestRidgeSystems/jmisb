#!/bin/sh

# Script for generating an mpegts stream with a test pattern using gstreamer

gst-launch-1.0 videotestsrc ! 'video/x-raw,width=640,height=480' ! x264enc ! mpegtsmux ! udpsink port=30120 host=127.0.0.1
