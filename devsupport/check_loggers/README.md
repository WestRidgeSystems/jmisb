# Checking Loggers

The `check_loggers.py` script verifies some aspects of logger implementation within jmisb.

The concept is that you run it from the top level of the source tree checkout, and it provides
a list of files and any problems noted.

``` sh
python3 ./devsupport/check_loggers/check_loggers.py 
The following files use SLF4J logging:
        api/src/main/java/org/jmisb/api/common/LogOnInvalidDataStrategy.java
        api/src/main/java/org/jmisb/api/common/InvalidDataHandler.java
        api/src/main/java/org/jmisb/api/common/IInvalidDataHandlerStrategy.java
        api/src/main/java/org/jmisb/api/common/ThrowOnInvalidDataStrategy.java
        api/src/main/java/org/jmisb/api/klv/LdsParser.java
        api/src/main/java/org/jmisb/api/klv/KlvParser.java
        api/src/main/java/org/jmisb/api/klv/st1204/CoreIdentifier.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoStreamOutput.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/FfmpegLog.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/MetadataDecodeThread.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoFileInput.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoStreamInput.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoOutput.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/StreamDemuxer.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/FileDemuxer.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoInput.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/FrameConverter.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoIO.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoFileOutput.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/DemuxerUtils.java
        api-ffmpeg/src/main/java/org/jmisb/api/video/VideoDecodeThread.java
        st0102/src/main/java/org/jmisb/st0102/ObjectCountryCodeString.java
        st0102/src/main/java/org/jmisb/st0102/localset/SecurityMetadataLocalSet.java
        st0102/src/main/java/org/jmisb/st0102/universalset/SecurityMetadataUniversalSet.java
        st0601/src/main/java/org/jmisb/st0601/UasDatalinkMessage.java
        st0601/src/main/java/org/jmisb/st0806/RvtLocalSet.java
        st0601/src/main/java/org/jmisb/st0806/userdefined/RvtUserDefinedLocalSet.java
        st0601/src/main/java/org/jmisb/st0806/poiaoi/RvtPoiLocalSet.java
        st0601/src/main/java/org/jmisb/st0806/poiaoi/RvtAoiLocalSet.java
        st0601/src/main/java/org/jmisb/st0903/VmtiLocalSet.java
        st0601/src/main/java/org/jmisb/st0903/vtracker/VTrackerLS.java
        st0601/src/main/java/org/jmisb/st0903/vobject/VObjectLS.java
        st0601/src/main/java/org/jmisb/st0903/vfeature/VFeatureLS.java
        st0601/src/main/java/org/jmisb/st0903/vchip/VChipLS.java
        st0601/src/main/java/org/jmisb/st0903/algorithm/AlgorithmLS.java
        st0601/src/main/java/org/jmisb/st0903/vtarget/VTargetPack.java
        st0601/src/main/java/org/jmisb/st0903/ontology/OntologyLS.java
        st0601/src/main/java/org/jmisb/st0903/vmask/VMaskLS.java
        st0601/src/main/java/org/jmisb/st0903/vtrack/VTrackLocalSet.java
        st0601/src/main/java/org/jmisb/st0903/vtrack/VTrackItem.java
        st0602/src/main/java/org/jmisb/st0602/AnnotationMetadataUniversalSet.java
        st0808/src/main/java/org/jmisb/st0808/AncillaryTextLocalSet.java
        st0809/src/main/java/org/jmisb/st0809/MeteorologicalMetadataLocalSet.java
        st1108/src/main/java/org/jmisb/st1108/InterpretabilityQualityLocalSetFactory.java
        st1108/src/main/java/org/jmisb/st1108/st1108_3/IQLocalSet.java
        st1108/src/main/java/org/jmisb/st1108/st1108_3/metric/MetricLocalSet.java
        st1108/src/main/java/org/jmisb/st1108/st1108_2/LegacyIQLocalSet.java
        st1206/src/main/java/org/jmisb/st1206/SARMILocalSet.java
        st1301/src/main/java/org/jmisb/st1301/MiisLocalSet.java
        st1603/src/main/java/org/jmisb/st1603/localset/TimeTransferLocalSet.java
    ```

It attempts to identify which files use logging, and if those source files
match conventions in terms of:

- SLF4J instead of `java.util.logging`
- the logger variable name
- the logger being `private static final`
- there being an SLF4J factory 
- the logger being initialised with the correct class name
- the logging is tested

Like most automated checks, there can be false positive and false negative results.
This is a backstop, not a replacement for code review.

There are ways to suppress incorrect error messages - see the top of the script.
