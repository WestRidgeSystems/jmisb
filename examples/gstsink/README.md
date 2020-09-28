# GStreamer Sink example for jMISB

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It uses GStreamer to demux the KLV stream, before passing that to a Sink that decodes it and outputs the message
type. Consult the GStreamer documentation and source code as required.

It will only work with asynchronous streams because of limitations in how GStreamer demux works. Its a known bug.

This example is considered a work-in-progress.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/gstsink-1.10.0-SNAPSHOT-jar-with-dependencies.jar  {filename}
```

For example:

``` sh
java -jar target/gstsink-1.10.0-SNAPSHOT-jar-with-dependencies.jar ~/KLV_samples/HD_H264_06011_TS_ASYN_V1_001.mpg
```

You should adjust the version part to match the current version number.

The output looks like:

``` txt

/home/bradh/KLV_samples/HD_H264_06011_TS_ASYN_V1_001.mpg
ST 0601
ST 0601
ST 0601
ST 0601
ST 0601
ST 0601
ST 0601
ST 0601
ST 0601
```

## Helping us

This requires developer level assistance - please send pull requests.
