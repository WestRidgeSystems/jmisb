# Time Transfer test file generator example for jmisb

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It generates a test file containing video and time transfer information per ST 1603. The time transfer information
is derived from the Command and Monitoring interface on a local chrony instance. Without that, it'll probably fail.
That isn't required in general for time transfer - you can create the Pack or Local Set using whatever information
source makes sense for your application.

This is a work-in-progress (WIP), and is intended for developers. It does not generate compliant test files at this
point.

It does not provide useful functionality to typical end-users.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/timetransfer-2.0.0-SNAPSHOT-jar-with-dependencies.jar --help 
```

That should provide a list of supported command line options.

Note that it reads a file called `test1280.jpg`.

## Helping us

Please provide pull requests.
