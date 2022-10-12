# Dump raw KLV as text

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It does a console dump of the raw KLV data in a file to standard output (`System.out` in Java). In this context, "raw" is the de-multiplexed stream content,
which is not normally used. It is more common to find the KLV multiplexed with the video stream in an MPEG Transport Stream (TS) or an encoding like CMAF.
If you are looking for an example using TS, see the systemout example.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/rawklv-1.12.0-SNAPSHOT-jar-with-dependencies.jar  {filename}
```

For example:

``` sh
java -jar target/rawklv-1.12.0-SNAPSHOT-jar-with-dependencies.jar ~/MIMD_0.01.bin
```

You should adjust the version part to match the current version number.

That will produce console output - you can redirect it to a file using standard command line functionality,
since there could be a lot of output.

The output depends on the input file, but might look like:

``` txt
MIMD
        Security: REF<Security>(1, 0)
        CompositeProductSecurity: REF<Security>(1, 0)
        Timers: LIST[Timer]
                Timer: [Timer]
                        MIMD Id: [4, 0]
                        NanoPrecisionTimestamp: 1624293300565345 ns
                        UtcLeapSeconds: 18 s
                        TimeTransferMethod: Inter-range Instrumentation Group (IRIG-B)
        Platforms: LIST[Platform]
                Platform: [Platform]
                        Name: Test Platform
                        Identity: MISB Test 002
                        PlatformType: Trailer. See NTAX for detailed description
                        Stages: LIST[Stage]
                                Stage: [Stage]
                                        MIMD Id: [2, 0]
                                        Position: [Position]
                                                AbsGeodetic: [AbsGeodetic]
                                                        Lat: 0.564206 rad
                                                        Lon: -1.863583 rad
                                                        Hae: 1188.719999 m
                                Stage: [Stage]
                                        MIMD Id: [3, 0]
                                        ParentStage: REF<Stage>(2, 0)
                                        Position: [Position]
                                                RelPosition: [RelPosition]
                                                        X: 0.099609 m
                                                        Y: 0.199219 m
                                                        Z: 0.299805 m
                                        Orientation: [Orientation]
                                                AbsEnu: [AbsEnu]
                                                        RotAboutUp: 0.610865 rad
                        Payloads: LIST[Payload]
                                Payload: [Payload]
                                        Stages: LIST[Stage]
                                                Stage: [Stage]
                                                        Timer: REF<Timer>(4, 0)
                                                        ParentStage: REF<Stage>(3, 0)
                                                        Orientation: [Orientation]
                                                                RelOrientation: [RelOrientation]
                                                                        Alpha: 2.617994 rad
                                                                        Beta: 0.959931 rad
        SecurityOptions: LIST[Security]
                Security: [Security]
                        MIMD Id: [1, 0]
                        ClassifyingMethod: US-1
                        Classification: UNCLASSIFIED//REL TO USA, AUS, CAN, GBR
```
