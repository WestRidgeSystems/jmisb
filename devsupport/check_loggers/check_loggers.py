import os
modules = ['api', 'core']

sourcedirs = []

expectedToHaveNoTest = [ 'api/src/main/java/org/jmisb/api/klv/LdsParser.java',
    'api/src/main/java/org/jmisb/api/video/VideoDecodeThread.java',
    'api/src/main/java/org/jmisb/api/video/VideoOutput.java',
    'api/src/main/java/org/jmisb/api/video/VideoStreamOutput.java',
    'api/src/main/java/org/jmisb/api/video/DemuxerUtils.java',
    'api/src/main/java/org/jmisb/api/video/MetadataDecodeThread.java',
    'api/src/main/java/org/jmisb/api/video/VideoInput.java',
    'api/src/main/java/org/jmisb/api/video/StreamDemuxer.java',
    'api/src/main/java/org/jmisb/api/video/VideoFileOutput.java',
    'api/src/main/java/org/jmisb/api/video/FfmpegLog.java',
    'api/src/main/java/org/jmisb/api/video/FileDemuxer.java',
    'core/src/main/java/org/jmisb/core/video/FrameConverter.java']

# flag that says whether everything was OK. Any failing check fails the result.
checkPasses = True

def fileHasMatchingLine(filePath, text):
    f = open(filePath, 'r')
    for line in f.readlines():
        if text in line:
            return True
    return False

def usesJavaUtilLogging(filePath):
    return fileHasMatchingLine(filePath, 'java.util.logging')

def hasLogging(filePath):
    return fileHasMatchingLine(filePath, 'org.slf4j.Logger')

def isCalledLOGGER(text):
    textParts = text.split('=')
    leftPart = textParts[0].strip()
    variableName = leftPart.split()[-1].strip()
    # would be better to pick just one, but two cases isn't too bad
    if variableName in ['logger', 'LOGGER']:
        return True
    else:
        print('Unexpected variable name: ' + variableName)
        return False

def isPrivateStaticFinal(text):
    # TODO: fix final
    # return text.startswith('private static final ')
    return text.startswith('private static ')

def matchesExpectedFormat(text):
    return isCalledLOGGER(text) and isPrivateStaticFinal(text)

def matchesFileName(text, filePath):
    fileName = filePath.split('/')[-1]
    # print(fileName)
    className = fileName.split('.')[0]
    # print(className)
    # print(text.split('(')[-1])
    classInLoggerName = text.split('(')[-1].split('.')[0]
    # print(classInLoggerName)
    if className == classInLoggerName:
        return True
    else:
        print('Class from filename:' + className + ", but class from logger: " + classInLoggerName)
        return False

def checkUsesExpectedLoggerName(filePath):
    didFindFactory = False
    f = open(filePath, 'r')
    for line in f.readlines():
        if "LoggerFactory.getLogger" in line:
            didFindFactory = True
            text = line.strip()
            if not matchesExpectedFormat(text):
                print('Does not match expected format ' + text)
                checkPasses = False
            if not matchesFileName(text, filePath):
                print('Does not match expected class name ' + text)
                checkPasses = False
    if not didFindFactory:
        print("Did not find expected factory line in " + filePath)
        checkPasses = False

def addUsefulSourceFiles(filePath):
    if usesJavaUtilLogging(filePath):
        filesWithJavaUtilLogging.append(filePath)
    if hasLogging(filePath):
        filesWithLoggers.append(filePath)

def checkTestFile(testFilePath):
    fileIsOK = False
    f = open(testFilePath, 'r')
    for line in f.readlines():
        if 'extends LoggerChecks' in line:
            fileIsOK = True
            break
        if 'TestLoggerFactory.getTestLogger' in line:
            fileIsOK = True
            break
    if not fileIsOK:
        print(testFilePath + " did not contain the expected test")
        checkPasses = False

def checkHasTestCase(sourceFilePath):
    # print(sourceFilePath)
    testFilePath = sourceFilePath.replace('main', 'test').replace('.java', 'Test.java')
    # print(testFilePath)
    if not os.path.exists(testFilePath):
        if not sourceFilePath.replace('../../', '') in expectedToHaveNoTest:
            print('Did not find test case for ' + sourceFilePath + " at " + testFilePath)
    elif sourceFilePath.replace('../../', '') in expectedToHaveNoTest:
        print('Found unexpected test case for ' + sourceFilePath + " at " + testFilePath)
    else:
        checkTestFile(testFilePath)

filesWithJavaUtilLogging = []
filesWithLoggers = []
for module in modules:
    sourcedir = os.path.join("..", "..", module, "src", "main", "java")
    for subdir, dirs, files in os.walk(sourcedir):
        for file in files:
            filePath = os.path.join(subdir, file)
            if not filePath.endswith('.java'):
                continue
            addUsefulSourceFiles(filePath)


if len(filesWithJavaUtilLogging) > 0:
    print('The following files use legacy Java logging:')
    for fileName in filesWithJavaUtilLogging:
        print('\t' + fileName)
    checkPasses = False

print('The following files use SLF4J logging:')
for fileName in filesWithLoggers:
    print('\t' + fileName)
    checkUsesExpectedLoggerName(fileName)
    checkHasTestCase(fileName)

