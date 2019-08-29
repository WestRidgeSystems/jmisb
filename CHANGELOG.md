 * Added StreamerUtil as a simple interactive VideoStreamOutput test 
 * Bumped JavaCPP Presets to 1.5.1
 
### May 11, 2019 version 1.7.0
 * Update to JavaCPP Presets 1.5 / FFmpeg 4.1.3
 * Option to enable or disable KLV stream in output
 * Metadata framing performance optimizations
 * KLV parser wraps unsupported message types and returns them in a
   RawMisbMessage
 * Metadata usage example fixed
 
### September 14, 2018 version 1.6.0
 * Updated FFmpeg from 3.4.2 to 4.0.1
 * Added API methods to get PES information as a JSON string
 * Separated integration tests using Maven Failsafe Plugin
 * De-clutter build output making warnings easier to spot
 
### July 18, 2018 version 1.5.0
 * KLV data added to output streams
 * Output streams provide statistics on the # frames queued/sent
 * Perform encoding on a separate thread for performance
 * Attempt to use hardware-accelerated encoding when available
 
### July 11, 2018 version 1.4.0
 * Added Exec Maven plugin to allow running the viewer from the command line
 * Added command-line argument -DuberJar to produce an uber-JAR of jmisb-api
   and all its dependencies
 * Fixed resource leaks in output streams

### June 20, 2018 version 1.3.0
 * Added a new method to get elementary stream info from IVideoInput
 * Video output now handles input images with arbitrary underlying raster type
 * Implemented ST 0601 tags 90-93
 * Added API methods to get displayable (stringified) values
 * Added a panel to viewer to display metadata, currently limited to ST 0601

### June 6, 2018 version 1.2.0
 * Added the ability to write to a UDP stream
 * Renamed all interfaces to begin with 'I' for consistency
 * Build creates an uber-JAR for jmisb-viewer so it may be run more easily 
   from the command-line
 
### May 30, 2018 version 1.1.0
 * Prepared project files for release to the Central Repository
 * Clean up and publish javadocs to GitHub pages

### May 23, 2018 version 1.0.0
 * Initial Release
