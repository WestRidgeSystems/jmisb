# Test file generator example for jMISB

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It generates a test video and KLV metadata file with synthetic data. The output is intended for testing / validating other parts of jMISB, especially nested ST0903 items.

This is a work-in-progress (WIP), and is intended for developers. It does not generate compliant test files at this point.

It does not provide useful functionality to typical end-users.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/generator-1.10.0-SNAPSHOT-jar-with-dependencies.jar
```

There will be no command line output, but it will create a file called `generator_output.mpeg`.

Note that it reads a file called `test.jpg`.

There are command line options - pass `--help` to discover configuration options.

## Helping us

Please provide pull requests.
