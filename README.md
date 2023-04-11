# jmisb

![Build Status](https://github.com/WestRidgeSystems/jmisb/actions/workflows/jdk11.yml/badge.svg)
[![codecov](https://codecov.io/gh/WestRidgeSystems/jmisb/branch/develop/graph/badge.svg?token=SWXQJKERQY)](https://codecov.io/gh/WestRidgeSystems/jmisb)
[![CodeQL](https://github.com/WestRidgeSystems/jmisb/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/WestRidgeSystems/jmisb/actions/workflows/codeql-analysis.yml)
[![Maven Central](https://maven-badges-generator.herokuapp.com/maven-central/org.jmisb/jmisb/badge.svg)](https://maven-badges-generator.herokuapp.com/maven-central/org.jmisb/jmisb)
[![Gitter](https://badges.gitter.im/jmisb/community.svg)](https://gitter.im/jmisb/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## About

jmisb is an open source Java library implementing various
[MISB](https://gwg.nga.mil/gwg/focus-groups/misb/ "MISB home page") standards.
It leverages the excellent work by
[bytedeco](https://github.com/bytedeco) on bringing video support to Java.
Stay tuned here for updates, and please join us on [gitter](https://gitter.im/jmisb/community) if you need help
or would like to participate!

## Why jmisb?

The Motion Imagery Standards Board, or MISB's mission is to develop and
maintain standards for interoperability between motion imagery systems in use
within the Department of Defense (DoD) and Intelligence Community (IC).

The goal of the jmisb project is to provide an open implementation of
these standards and allow government and industry to leverage them more easily
and effectively. The jmisb project is not affiliated with, nor endorsed by
MISB in any way.

## Scope

The MISB has been quite prolific in creation of new standards since its
inception in 2000. As of December 2022, over 50 standards are listed on the NSG register.
While the scope of the jmisb project is to support as many of
these standards as possible, the initial focus will be on those
in most widespread use.

The table below lists the status of currently-supported standards:

| Identifier | Name                                                                            | Implementation Status                                                                                              | Known Issues                                                                                                             |
| ---------- | ----                                                                            | ---------------------                                                                                              | ------------                                                                                                             |
| ST 0102    | Security Metadata Universal and Local Sets for Digital Motion Imagery           | Implemented as of ST 0102.12. There is read-only support for some tags (not UMID) that were removed in ST 0102.12. |                                                                                                                          |
| EG 0104    | Predator UAV Basic Universal Metadata Set                                       | Read only support for EG 0104.5. Writing is not planned, since this metadata set is deprecated by MISB.            |                                                                                                                          |
| ST 0601    | UAS Datalink Local Set                                                          | Mostly implemented as of ST 0601.17.                                                                               | [#140](https://github.com/WestRidgeSystems/jmisb/issues/140)                                                             |
| ST 0602    | Annotation Metadata Set                                                         | Mostly implemented as of ST 0602.5. No support for CGM annotations.                                                |                                                                                                                          |
| ST 0603    | MISP Time System and Timestamps                                                 | Partly implemented as of ST 0603.5.                                                                                | [#97](https://github.com/WestRidgeSystems/jmisb/issues/97)                                                               |
| ST 0604    | Timestamps for Class 1 / Class 2 Motion Imagery                                 | Partly implemented as of ST 0604.6.                                                                                | [#102](https://github.com/WestRidgeSystems/jmisb/issues/102)                                                             |
| ST 0805    | KLV to Cursor-on-Target (CoT) Conversions                                       | Implemented as of ST 0805.1. Interoperability testing with FalconView and CoT Debug Tool.                          |                                                                                                                          |
| ST 0806    | Remote Video Terminal Metadata Set                                              | Implemented as of ST 0806.5. Unit tests only, no interoperability testing.                                         |                                                                                                                          |
| ST 0808    | Ancillary Text Metadata Sets                                                    | Implemented as of ST 0808.2. Local Set support only, no universal set support. Deprecated by MISB.                 |                                                                                                                          |
| ST 0809    | Meteorological Metadata Local Set                                               | Implemented as of ST 0809.2. No interoperability testing.                                                          |                                                                                                                          |
| ST 0903    | Video Moving Target Indicator and Track Metadata                                | VMTI and VTrack Local Sets implemented as of ST 0903.5. We also support pre-ST0903.4 files.                        |                                                                                                                          |
| ST 1002    | Range Motion Imagery                                                            | Partly implemented as of ST 1002.2. No interoperability testing.                                                   |                                                                                                                          |
| ST 1010    | Generalized Standard Deviation and Correlation Coefficient Metadata             | Partly implemented as of ST 1010.3. No support for ST 1201 formatted standard deviation values.                    |                                                                                                                          |
| ST 1108    | Motion Imagery Interpretability and Quality Metadata                            | Implemented as of ST 1108.3. ST 1108.2 and earlier is also supported. No interoperability testing.                 |                                                                                                                          |
| ST 1201    | Floating Point to Integer Mapping                                               | Fully implemented per ST 1201.5.                                                                                   |                                                                                                                          |
| ST 1202    | Generalized Transformation Parameters                                           | Mostly implemented as of ST 1202.2.                                                                                |                                                                                                                          |
| ST 1204    | Motion Imagery Identification System (MIIS) Core Identifier                     | Implemented as of ST 1204.3.                                                                                       |                                                                                                                          |
| ST 1206    | Synthetic Aperture Radar (SAR) Motion Imagery Metadata                          | Implemented as of ST 1206.1. Unit tests only, no interoperability testing.                                         |                                                                                                                          |
| ST 1301    | Motion Imagery Identification System (MIIS) - Augmentation Identifiers          | Implemented as of ST 1301.2. Validated with CMITT.                                                                 |                                                                                                                          |
| ST 1303    | Multi-Dimensional Array Pack (MDAP)                                             | Partly implemented as of ST 1303.2. Only formats and dimensions known to be used are available. Limited testing.   | [#198](https://github.com/WestRidgeSystems/jmisb/issues/198)                                                             |
| ST 1402    | MPEG-2 Transport Stream for Class 1/Class 2 Motion Imagery, Audio, and Metadata | Mostly implemented, support for Sync and Asynchronous multiplexing.                                                |                                                                                                                          |
| ST 1601    | Geo-Registration Local Set                                                      | Implemented as of ST 1601.1. Unit tests only, no interoperability testing.                                         |                                                                                                                          |
| ST 1602    | Composite Imaging Local Set                                                     | Implemented as of ST 1602.1. Unit tests only, no interoperability testing.                                         |                                                                                                                          |
| ST 1603    | Time Transfer Pack                                                              | Implemented as of ST 1603.2. No interoperability testing.                                                          |                                                                                                                          |
| ST 1902    | Motion Imagery Metadata (MIMD): Model-to-KLV Transmutation Instructions         | Implemented as of ST 1902.1 (`mimd1`) and ST 1902.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1903    | Motion Imagery Metadata (MIMD): Model                                           | Implemented as of ST 1903.1 (`mimd1`) and ST 1903.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1904    | Motion Imagery Metadata (MIMD): Base Attributes                                 | Implemented as of ST 1904.1 (`mimd1`) and ST 1904.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1905    | Motion Imagery Metadata (MIMD): Platform                                        | Implemented as of ST 1905.1 (`mimd1`) and ST 1905.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1906    | Motion Imagery Metadata (MIMD): Staging System                                  | Implemented as of ST 1906.1 (`mimd1`) and ST 1906.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1907    | Motion Imagery Metadata (MIMD): Payload                                         | Implemented as of ST 1907.1 (`mimd1`) and ST 1907.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1908    | Motion Imagery Metadata (MIMD): Imager System                                   | Implemented as of ST 1908.1 (`mimd1`) and ST 1908.2 (`mimd2`). No interoperability testing.                        |                                                                                                                          |
| ST 1909    | Metadata Overlay for Visualization                                              | Mostly implemented as of ST 1909.1. No support for next zoom or the reticle.                                       | [#97](https://github.com/WestRidgeSystems/jmisb/issues/97)                                                               |

jmisb aims to be cross-platform to run on any modern operating system. However,
since efficient video coding tends to leverage natively-compiled binaries, currently
platform support is limited to Linux, Windows, and MacOS. Android is next on our roadmap
(see [#253](https://github.com/WestRidgeSystems/jmisb/issues/253)).

## Including in Your Project

NOTE: version 2 is under active development, and has not yet been released to Maven 
[Central Repository](https://search.maven.org/).
The latest 1.x release, available on the `main` branch, is the recommended version for most projects.

To use version 2, you must first build jmisb locally (see [Building jmisb](#building-jmisb) section below).
Once built, either add it to your Maven POM:

```xml
    <dependency>
        <groupId>org.jmisb</groupId>
        <artifactId>jmisb-api-ffmpeg</artifactId>
        <version>2.0.0-SNAPSHOT</version>
    </dependency>
```

Or for Gradle, include the following:

```groovy
repositories {
    mavenLocal()
}

dependencies {
    implementation 'org.jmisb:jmisb-api-ffmpeg:2.0.0-SNAPSHOT'
}
```

## API Usage

A primary objective of jmisb is to provide an easy-to-use API allowing
non-domain experts to create applications leveraging MISB standards.

The primary API for reading/writing video and metadata is in the `org.jmisb.api` package.
See the [javadocs](https://westridgesystems.github.io/jmisb) for an extensive API
reference.

Below is a simple example of reading a network stream containing video
and (optionally) metadata.

```java
        try (IVideoStreamInput stream = new VideoStreamInput())
        {
            stream.open("udp://127.0.0.1:35800");
            stream.addFrameListener(new ExampleProcessor());
            stream.addMetadataListener(new ExampleProcessor());
            while (stream.isOpen()) {
                Thread.sleep(1000);
            }
        }
        catch (IOException e) {
            System.out.println("Could not open the stream");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```

The `ExampleProcessor` class simply needs to implement the
`IVideoListener` and `IMetadataListener` interfaces
to receive video and metadata asynchronously as the data arrives.

```java
class ExampleProcessor implements IVideoListener, IMetadataListener
{
    @Override
    public void onVideoReceived(VideoFrame frame)
    {
        BufferedImage image = frame.getImage();
        System.out.println("Center pixel RGB: " +
            image.getRGB(image.getWidth()/2, image.getHeight()/2));
    }

    @Override
    public void onMetadataReceived(MetadataFrame frame)
    {
        IMisbMessage metadata = frame.getMisbMessage();
        if (metadata instanceof UasDatalinkMessage)
        {
            UasDatalinkMessage msg = (UasDatalinkMessage)metadata;
            System.out.println("Sensor position: " +
                msg.getField(UasDatalinkTag.SensorLatitude).getDisplayableValue() +
                ", " +
                msg.getField(UasDatalinkTag.SensorLongitude).getDisplayableValue());
        }
        else if (metadata instanceof SecurityMetadataMessage)
        {
            // ...
        }
    }
}
```

The result of `msg.getField(UasDatalinkTag.SensorLatitude)` will be an instance
of the `SensorLatitude` class (implementing `IUasDatalinkValue`). In addition
to displayable name and value, that instance will also provide get and set of the
underlying value (e.g. a double for something like Latitude). Consult the javadoc
for the relevant class.

For more complete examples of usage, see the [examples](./examples) directory,
as well as [jmisb-viewer](viewer), a Java Swing-based tool for displaying video and metadata.

## Elevation

While not a core focus, jmisb provides some elevation / terrain related support. This includes:

- EGM 96 conversion of altitude between ellipsoid (aka HAE) and geoidal (aka MSL).

## Building jmisb

To build the library from the command line, run the Maven wrapper:

```sh
./mvnw install
```

This will compile the source code, run unit tests, and install the JARs to your local Maven repository.

To get started, you may want to run `jmisb-viewer` and experiment
with some test data. This is a sample application intended mainly to aid in
development. To run it from the command line, issue:

```sh
./mvnw exec:exec -pl :jmisb-viewer
```

## Versioning

jmisb adheres to *semantic versioning* to communicate to client
developers about the scope of changes in any new release. Version numbers
are formatted as `major.minor.patch`, where:

1. The major number is incremented to indicate incompatible API changes.
2. The minor number is incremented to indicate new functionality has been
added, but in a backward-compatible manner.
3. The patch number is incremented to indicate a backwards-compatible bug
fix.

In other words, users of the library should feel comfortable updating to use
a new version unless the major number has changed. In general, users should
keep up to date with the latest patch release for a given
`major.minor` release branch.

Use of -SNAPSHOT within the version number indicates that the version is
for internal development only, i.e., the artifact is not to be used in a
production environment.
