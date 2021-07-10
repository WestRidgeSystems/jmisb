# jMISB development support tools and utilities

## egm96

For EGM 96 Geoid calculations, as implemented in the org.jmisb.elevation.geoid package.

The egm96datatool converts the WW15MGH.GRD interpolation grid provided by NGA into an easier-to-load equivalent.
It is intended to be run once, and the resulting egm96.dat file becomes a resource file in org.jmisb.elevation.geoid.
