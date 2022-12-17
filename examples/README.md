# Examples for jmisb

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

Examples are intended to be illustrative of API use. They prefer simplicity over completeness. They don't always handle error conditions, and should not be considered production-level code.

Examples are not considered part of the API, and may be changed or removed in minor versions. We mean it.

In addition, some examples may be work-in-progress (WIP). They should build, but may not run.
Or they may run but generate incorrect / invalid results.

We welcome corrections and updates.

## annotations

It generates a test file with video and annotations encoded as ST 0602 (KLV) metadata. This example is a work-in-progress. See [its README](annotations/README.md) for more information.

## cotconverter

It provides a Cursor on Target converter for KLV metadata.
This example is a work-in-progress. See [its README](cotconverter/README.md) for more information.

## generator

It generates a test file with video and KLV metadata. This example is a work-in-progress. See [its README](generator/README.md) for more information.

## gstsink

It provides a GStreamer Sink for KLV metadata.
This example is a work-in-progress. See [its README](gstsink/README.md) for more information.

## mimd1generator

It generates a test file with video and KLV metadata using the MIMD Version 1 (ST1901.1 through ST 1908.1) standards. This example is a work-in-progress. See [its README](mimd1generator/README.md) for more information.

## mimd2generator

It generates a test file with video and KLV metadata using the MIMD Version 2 (ST1901.2 through ST 1908.2) standards. This example is a work-in-progress. See [its README](mimd2generator/README.md) for more information.

## movingfeatures

It converts KLV metadata with ST 0903 Video Moving Target Indication local set information into OGC Moving Features (Trajectory JSON).
This example is a work-in-progress. See [its README](movingfeatures/README.md) for more information.

## parserplugin

It shows how to parse an unsupported KLV blob (perhaps a local set or universal set) without changing jmisb.
This example considered complete. See [its README](parserplugin/README.md) for more information.

## rawklv

It does a console dump of the raw KLV in a file to standard output. In this context, "raw" is the de-multiplexed stream content.
This example is considered complete. See [its README](rawklv/README.md) for more information.

## systemout

It does a console dump of the KLV metadata in a file to standard output (`System.out` in Java, hence the name).
This example differs from the rawklv example in that it takes an MPEG Transport Stream (typical format) rather than a de-multiplexed stream.
This example is considered complete. See [its README](systemout/README.md) for more information.

## timetransfer

It generates a test file with video and time transfer (ST 1603) KLV metadata, based on a local installation of chrony.
This example is a work-in-progress. See [its README](timetransfer/README.md) for more information.
