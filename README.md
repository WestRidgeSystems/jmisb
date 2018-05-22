# Introduction

## About

jMISB is an open source Java library implementing various 
[MISB](http://www.gwg.nga.mil/misb/ "MISB home page") standards. The
project was begun in March 2017, and is currently an unreleased work in
progress. Stay tuned here for updates, and please let us know if you have
suggestions or would like to participate!

## Why jMISB?

The Motion Imagery Standards Board, or MISB's mission is to develop and 
maintain standards for interoperability between motion imagery systems in use 
within the Department of Defense (DoD) and Intelligence Community (IC).
The goal of the jMISB project is to provide an open implementation of 
these standards and allow government and industry to leverage them more easily 
and effectively. The jMISB project is not affiliated with, nor endorsed by 
MISB in any way.

## Scope

The MISB has been quite prolific in creation of new standards since its 
inception in 2000. As of March 2017, over three dozen standards are listed
on its web site. While the scope of the jMISB project is to support as many of
these standards as possible, the initial focus will be on those
in most widespread use. Usage is not easy to quantify, so we anticipate 
making course corrections based on feedback from the community. The initial
set of standards to be supported are:

* ST 0102: Security Metadata Universal and Local Sets for Digital Motion
           Imagery
* ST 0601: UAS Datalink Local Set
* ST 0805: KLV to Cursor-on-Target (CoT) Conversions
* ST 0903: Video Moving Target Indicator and Track Metadata
* ST 1201: Floating Point to Integer Mapping
* ST 1402: MPEG-2 Transport Stream for Class 1/Class 2 Motion Imagery, Audio,
           and Metadata

jMISB aims to be cross-platform to run on any modern operating system. However,
some OSes are more widely used than others, and we will prioritize those to
reach the widest audience possible. The OSes we are initially targeting are:

1. Windows
2. Linux
3. Android
4. MacOS

# Building

To build, simply run the Maven command
<pre>
mvn package
</pre>
This will compile the source code, run unit tests, and generate the JAR files.

To include jMISB as a dependency in your Maven build, include the following dependencies in
your <code>pom.xml</code>:
```xml
    <dependency>
        <groupId>org.jmisb</groupId>
        <artifactId>jmisb-api</artifactId>
        <version>1.0.0</version>
    </dependency>
```
Or to include in your Gradle build, include the following:
```groovy
dependencies {
    compile group: 'org.jmisb', name: 'jmisb-api', version: '1.0.0'
}
```

# Usage

A primary objective of jMISB is to provide an easy-to-use API allowing 
non-domain experts to create applications leveraging MISB-formatted data.
Below is a simple example of opening a network stream containing video
and (optionally) metadata.

```java
IVideoStreamInput stream = VideoSystem.createInputStream();
try 
{
    stream.open("udp://225.1.1.1:35800");
    stream.addFrameListener(exampleProcessor);
    stream.addMetadataListener(exampleProcessor);
}
catch (IOException e)
{
    System.out.println("Could not open the stream");
}
```

The <code>ExampleProcessor</code> class simply needs to implement the
<code>FrameListener</code> and <code>MetadataListener</code> interfaces
to receive video and metadata asynchronously as the data arrives.

```java
class ExampleProcessor implements FrameListener, MetadataListener
{
    @Override
    private void onFrameReceived(VideoFrame frame)
    {
        BufferedImage image = frame.getImage();
        System.out.println("Center pixel RGB: " +
            image.getRGB(image.getWidth()/2, image.getHeight()/2));
    }
    
    @Override
    private void onMetadataReceived(MetadataFrame frame)
    {
        IMisbMessage metadata = frame.getMisbMessage();
        if (metadata instanceof UasDatalinkMessage)
        {
            UasDatalinkMessage msg = (UasDatalinkMessage)metadata;
            System.out.println("Sensor position: " + 
                msg.getField(SensorLatitude) + ", " + msg.getField(SensorLongitude));
        }
        else if (metadata instanceof SecurityMetadataMessage)
        {
            // ...
        }
    }
}
```

More complete examples of usage may be found in the <code>viewer</code> demo 
application and in unit tests. Additional sample applications are a work in
progress.

# Versioning

jMISB employs the <i>semantic versioning</i> approach to communicate to 
client developers about the scope of changes in any new release. Version
numbers are formatted as *major.minor.patch*, where:

1. The major number is incremented to indicate incompatible API changes.
2. The minor number is incremented to indicate new functionality has been
added, but in a backward-compatible manner.
3. The patch number is incremented to indicate a backwards-compatible bug
fix.

In other words, users of the library should feel comfortable updating to use
a new version unless the major number has changed. In general, users should 
keep up to date with the latest patch release for a given major.minor 
release branch.

Use of -SNAPSHOT within the version number indicates that the version is
for internal development only, i.e., the artifact is not to be used in a
production environment.

# Contact

