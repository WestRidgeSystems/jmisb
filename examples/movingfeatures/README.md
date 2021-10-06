# VMTI to Moving Features example for jmisb

This is demonstration / example code for <https://github.com/WestRidgeSystems/jmisb>

It converts files with KLV-encoded VMTI data (ST0903 VMTI Local Set) to OGC Moving Features (Trajectory JSON).

It is optimised for the case where there is KLV (in particular, it skips decoding video), and files that do not contain KLV may not behave well.

This example is considered a work-in-progress. There is a lot of things that could be added given suitable input data to test.

## Building

To build it, use maven.

``` sh
mvn clean install
```

## Using

There are several ways to invoke it. One way is:

``` sh
java -jar target/movingfeatures-1.10.0-SNAPSHOT-jar-with-dependencies.jar  {filename}
```

For example:

``` sh
java -jar target/movingfeatures-1.10.0-SNAPSHOT-jar-with-dependencies.jar ~/KLV_samples/my-vmti.mpeg
```

You should adjust the version part to match the current version number.

The result of the conversion will be found in `target/vmti.json`

The output looks like:

``` json
{
  "type" : "FeatureCollection",
  "features" : [ {
    "type" : "Feature",
    "id" : 2,
    "geometry" : {
      "type" : "LineString",
      "coordinates" : [ [ 138.59948236466724, -34.9200361431714, 45.276569771877575 ], ...  ]
    },
    "properties" : {
      "datetimes" : [ "2011-11-16T01:47:41.602526Z", ... ]
    }
  }, {
    "type" : "Feature",
    "id" : 3,
    "geometry" : {
      "type" : "LineString",
      "coordinates" : [ [ 138.5990909766811, -34.91524335695552, 39.20347905699248 ], ... ]
    },
    "properties" : {
      "datetimes" : [ "2011-11-16T01:47:41.602526Z", ... ]
    }
  }, {
    ....
  } ]
}
```

It will likely be a lot of data for a real VMTI capture. mf-cesium may not handle it well.

## Helping us

Real VMTI data would help to make this example more complete.
Please send pull requests if you can't make the data available.
