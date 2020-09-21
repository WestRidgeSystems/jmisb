# Examples for jMISB

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

Examples are intended to be illustrative of API use. They prefer simplicity over completeness. They don't always handle error conditions, and should not be considered production-level code.

Examples are not considered part of the API, and may be changed or removed in minor versions. We mean it.

In addition, some examples may be work-in-progress (WIP). They should build, but may not run.
Or they may run but generate incorrect / invalid results.

We welcome corrections and updates.

## systemout

It does a console dump of the KLV metadata in a file to standard output (`System.out` in Java, hence the name). This example is considered complete. See [its README](systemout/README.md) for more information.

## generator

It generates a test file with video and KLV metadata. This example is a work-in-progress. See [its README](generator/README.md) for more information.

## movingfeatures

It converts KLV metadata with ST 0903 Video Moving Target Indication local set information into OGC Moving Features (Trajectory JSON).
This example is a work-in-progress. See [its README](movingfeatures/README.md) for more information.

## gstsink

It provides a GStreamer Sink for KLV metadata.
This example is a work-in-progress See [its README](gstsink/README.md) for more information.
