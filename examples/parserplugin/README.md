# Parser Plugin example for jMISB

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It shows how to add a parser to jMISB without modifying the source code. This might be useful
where you need to support a custom (not MISB standardised) local set or universal set.

In this example (based on a production system), there is a message that is not parsed correctly by
jMISB, and is to returned as a `RawMisbMessage`.

The structure looks like:

``` txt
0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x03, 0x07, 0x02, 0x01, 0x01, 0x01, 0x05, 0x00, 0x00,
0x08, 0x00, 0x05, 0x09, 0x95, 0xc4, 0xff, 0xba, 0x00
```

For the sake of this example, this is the "Time Message" structure.

The first 16 bytes are a Universal Label. Typically you'd have documentation defining your message,
but in this case, it happens to match the Precision Time Stamp field registered in SMPTE RP210.
The next byte (`0x08`) is the length - it is BER-OID encoded, but for this example is effectively
a constant. The remaining 8 bytes are the timestamp value.

There is more on this in TRM1006, which just happens to use the same structure for one of its examples.

Writing a parser for this is relatively easy. One approach would be to skip over the Universal
Label and length, and then parse the timestamp. Because a timestamp is such a common thing in motion
imagery, we can just use the code in ST0603 for the timestamp itself. A more complex example might use
the `LdsParser` utility code to parse a local set, or the `UdsParser` to parse a universal set. More
advanced parsing is of course possible. If you are looking for a simple local set example, try ST0808.

So we can parse the message, and return our own `IMisbMessage` implementation instead of getting a
`RawMisbMessage`. That implementation is `TimeMessage` in the example code. The supporting code is
more complex than strictly needed, but is intended provide a model for more complex structures.

To link in our parser, we use a simple factory (`TimeMessageFactory` in the example). That gets
registered with code that looks like:

``` java
        TimeMessageFactory timeMessageFactory = new TimeMessageFactory();
        MisbMessageFactory.getInstance()
                .registerHandler(TimeMessageConstants.TIME_STAMP_UL, timeMessageFactory);
```

The constant `TIME_STAMP_UL` is just the `UniversalLabel` that matches what we want to handle.

When its all wired up, the output of the metadata dump will look like:

``` txt
Time Message Example
        Precision Time Stamp: 2014-12-07T00:55:43.424
```

Note that this plugin is not restricted to command line parsing. It will work wherever its loaded.

## Building

To build the example, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/parserplugin-1.10.0-SNAPSHOT-jar-with-dependencies.jar {filename}
```

## Helping us

Please provide pull requests.
