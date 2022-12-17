# MIMD Version 2 test file generator example for jmisb

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It generates a test video and KLV metadata file with synthetic data consistent with ST 1801.2 Situational Awareness
profile (i.e. this is MIMD data per ST 190x, rather than ST 0601 / ST 0903 style). The output is intended for
testing / validating other parts of jmisb, primarily MIMD.

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
java -jar target/mimdgenerator-2.0.0-SNAPSHOT-jar-with-dependencies.jar
```

There will be no command line output, but it will create a file called `mimd2.mpeg`.

Note that it reads a file called `test1280.jpg`.

## Helping us

Please provide pull requests.
