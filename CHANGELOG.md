### October 12, 2022, version 1.12.0
 * The majority of development over the past year has been on 2.x, so
   this is primarily a maintenance release 
 * Added support for top-level ST 0903 messages in MisbMessageFactory
 * Numerous dependency updates

### December 29, 2021, version 1.11.0
 * Completed ST 0805 implementation and added `cotconverter` example
 * Implemented ST 1108, Interpretability and Quality Metadata Local Set
 * Implemented ST 1303, Multi-Dimensional Array Pack (MDAP)
 * Implemented STs 1901-1908, Motion Imagery Metadata (MIMD), via
  `miml-maven-plugin`
 * Implemented Image Horizon Pixel Pack (ST 0601 item 81)
 * Support for multiple control commands within a single ST 0601 local set
 * Updated ST 1201.4 to 1201.5
 * Updated ST 1909 to 1909.1
 * Initial work to decouple API from FFmpeg dependency; split non-FFmpeg 
   code from `core` into a new `core-common` module
 * Added H.265 (HEVC) encoding support
 * Added `geoid` module for converting HAE and MSL elevation values
 * Added map visualization to the viewer application
 * Added Maven plugin to generate Software Bill of Materials (SBOM)
 * Numerous bug fixes, code samples, and improved test coverage

### December 20, 2021, version 1.10.1
 * Updated log4j to 2.17.0 (log4j is only used by viewer app)

### February 22, 2021, version 1.10.0
 * Implemented ST 0603, MISP Time System and Timestamps
 * Implemented ST 0806, Remote Video Terminal Metadata Set
 * Implemented ST 0808, Ancillary Text Metadata Sets
 * Implemented ST 1206, SAR Motion Imagery Metadata
 * Implemented ST 1909, Metadata Overlay for Visualization
 * Implemented EG 0104 (read only)
 * Added the ability for clients to register their own custom parsers, e.g., 
   to handle non-standard metadata
 * Implemented additional ST 0601 items: 47, 60, 61, 72, 107, 122, 127, 130, 
   131, 138, 139, 140
 * Implemented ST 1204.3 UUID combination approach
 * Enhanced support for nested metadata, including the viewer application
 * Added a configurable error handler to allow a consistent strategy to be 
   employed when bad metadata is encountered (log, throw, etc.)
 * Support for VTrack Local Set in ST 0903
 * Support for legacy floating point encoding in ST 0903
 * Support 5-7 byte and "special" floating point values per ST 1201
 * Support for generating synchronous or asynchronous metadata in accordance 
   with ST 1402, and properly set metadata stream ID
 * Added EOF event listener to video input classes
 * Deprecated VideoSystem in favor of public constructors for video I/O classes
 * Upgraded to FFmpeg to 4.3.1
 * Added SpotBugs Maven plugin for static code analysis
 * Added input fuzz testing using JQF
 * Numerous bug fixes, code samples, and improved test coverage

### April 11, 2020 version 1.9.0
 * Implemented ST 0903, Video Moving Target Indicator
 * Implemented ST 1204, MIIS Core Identifier
 * Implemented many additional ST 0601 tags: 8, 9, 34, 35, 36, 37, 39, 43, 44, 
   45, 46, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 62, 63, 64, 71, 75, 76, 77, 
   79, 80, 93, 94, 110, 111, 121, 123, 124, 125, 126, 128, 136, 141
 * Implemented ST 0102 tag 21
 * Added 3-byte encode/decode support (ST 1201)
 * Added updates for latest version of UAS Datalink standard (0601.16)
 * Added toggle to enable/disable decoding of video/metadata streams
 * Fixed some tests for Java 11; library now fully supports Java 8/11
 * Viewer updates: display non-0601 data, scroll bars, clear metadata on load
 * Upgraded to ffmpeg 4.2.1, log4j 2
 * Numerous bug fixes
 * Added javadoc descriptions for all value types to UasDatalinkTag
 * Significant unit testing/code coverage enhancement

### September 5, 2019 version 1.8.0
 * Support BER-OID encoding/decoding as required by ST 0601
 * Added new 0601 tags (up to ST 0601.15)
 * Fixed h264 encoding options causing corrupt video output
 * Fixed corrupted metadata produced by VideoStreamOutput
 * Improved synchronization of metadata with video (for output)
 * Fixed viewer to display KLV metadata from network streams
 * Added StreamerUtil as a simple interactive VideoStreamOutput test 
 * Bumped JavaCPP Presets to 1.5.1
 
### May 11, 2019 version 1.7.0
 * Updated to JavaCPP Presets 1.5 / FFmpeg 4.1.3
 * Added option to enable or disable KLV stream in output
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
