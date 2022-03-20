# Annotation test file generator example for jmisb

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It generates a test file containing video and annotations per ST 0602 (KLV metadata). The output is intended for
testing / validating other parts of jmisb, primarily annotation display.

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
java -jar target/annotations-2.0.0-SNAPSHOT-jar-with-dependencies.jar --help 
```

That should provide a list of supported command line options.

## Helping us

Please provide pull requests.
