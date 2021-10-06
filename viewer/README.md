# jmisb viewer

This directory contains a Java Swing-based GUI to visualize video and metadata on the desktop. It is primarily intended to exercise the API for development and testing, and not as an end user tool.

To run it from the command line, from the `viewer` directory, run:
```sh
mvn exec:exec
```

There is a map view - available from the View menu. It will overlay the sensor footprint and platform / sensor location on the map
if the required information is provided in the metadata. Typical map pan / zoom operations can be done with the mouse and arrow
keys. You can also make the map track the sensor field of view, which will basically override those operations (unless the 
metadata is updated very slowly). Map options are set in a configuration file which is written on first run. The location is 
operating system dependent - whatever is usual for user specific configuration (e.g. ~/.config/jmisb_map/configuration.properties
on Linux; or under the roaming AppData on Windows).
