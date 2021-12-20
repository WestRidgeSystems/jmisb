# Simple command line example for jMISB

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It does a console dump of the KLV metadata in a file to standard output (`System.out` in Java, hence the name).

It is optimised for the case where there is KLV (in particular, it skips decoding video), and files that do not contain KLV may not behave well.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/systemout-1.10.1-jar-with-dependencies.jar  {filename}
```

For example:

``` sh
java -jar target/systemout-1.10.1-jar-with-dependencies.jar ~/KLV_samples/CheyenneVAhospital.mpeg4
```

You should adjust the version part to match the current version number.

That will produce console output - you can redirect it to a file using standard command line functionality,
since there will likely be a lot of output.

The output looks like:

``` txt
ST 0601
        Precision Time Stamp: 1348087442397531
        Mission ID: ESRI_Metadata_Collect
        Platform Tail Number: N97826
        Platform Heading Angle: 259.8746°
        Platform Pitch Angle: 3.1483°
        Platform Roll Angle: -11.4826°
        Platform Designation: C208B
        Image Source Sensor: 
        Image Coordinate System: 
        Sensor Latitude: 41.1606°
        Sensor Longitude: -104.7934°
        Sensor True Altitude: 2974.6m
        Sensor Horizontal Field of View: 6.5260°
        Sensor Vertical Field of View: 3.6695°
        Sensor Relative Azimuth: 327.7266°
        Sensor Relative Elevation: -5.4375°
        Sensor Relative Roll: 0.0000°
        Slant Range: 7160.242m
        Target Width: 0.00m
        Frame Center Latitude: 41.1190°
        Frame Center Longitude: -104.8572°
        Frame Center Elevation: 1872.1m
        Offset Corner Latitude Point 1: -0.0139°
        Offset Corner Longitude Point 1: -0.0121°
        Offset Corner Latitude Point 2: -0.0070°
        Offset Corner Longitude Point 2: -0.0200°
        Offset Corner Latitude Point 3: 0.0096°
        Offset Corner Longitude Point 3: 0.0085°
        Offset Corner Latitude Point 4: 0.0049°
        Offset Corner Longitude Point 4: 0.0138°
        Generic Flag Data 01: [Flag data]
                Laser Range: Laser off
                Auto-Track: Auto-Track off
                IR Polarity: White Hot
                Icing Status: No Icing Detected
                Slant Range: Calculated
                Image Invalid: Image Valid
        Security: [Security metadata]
                Classification: UNCLASSIFIED
                Country Coding Method: ISO3166_TWO_LETTER
                Classifying Country: //CA
                Security-SCI/SHI Information: 
                Caveats: 
                Releasing Instructions: CA
                Item Designator Id: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                ST0102 Version: 5
        Platform Ground Speed: 0m/s
        Platform Call Sign: Firebird
        Version Number: 1
        Event Start Time UTC: 1970-01-01T00:00:00
```

## Multiple files

You can run it on a directory using a shell script like:

``` sh
for f in ~/KLV_samples/*
do
    if test -f "$f"; then
        echo "Processing" "$f"
        java -jar target/systemout-1.10.1-jar-with-dependencies.jar "$f" > "$(basename "$f").klv.txt"
    fi
done
```

where `~/KLV_samples` is the directory containing KLV videos to parse.

Adjust as required if you aren't using Linux or have a different structure.

## Helping us

If you have files that you are confident have standard KLV metadata, and its not working correctly, please report issues and send pull requests. If you can't send pull requests, sample data will help to get your issues resolved.
