# CoT Converter for KLV metadata

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It does a console dump of the KLV metadata in a file to a UDP output in Cursor on Target (CoT) format. See ST 0805.1 for more info.

Because the file represents a point in the past, and CoT clients will typically not show "stale" track infomation, the time stamp on the KLV metadata will be updated to appear as if the KLV was current, by adding an offset calculated from the first timestamp. Note that it doesn't send every message as "now", so variations in the file will be reflected in the CoT times.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/cotconverter-1.12.0-SNAPSHOT-jar-with-dependencies.jar  {filename}
```

For example:

``` sh
java -jar target/cotconverter-1.12.0-SNAPSHOT-jar-with-dependencies.jar ~/KLV_samples/CheyenneVAhospital.mpeg4
```

You should adjust the version part to match the current version number.

## Helping us

Please report issues and send pull requests. If you can't send pull requests, sample data will help to get your issues resolved.
