#!/bin/bash

# Script to run all fuzzing tests

# length of each test
time=10m

# todo: generate list dynamically
tests=(org.jmisb.api.klv.st0102.localset.LocalSetFactoryFuzzTest
org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest
org.jmisb.api.klv.st0903.VmtiLocalSetFuzzTest
org.jmisb.api.klv.st0903.algorithm.AlgorithmLocalSetFuzzTest
org.jmisb.api.klv.st0903.ontology.OntologyLocalSetFuzzTest
org.jmisb.api.klv.st0903.vchip.VChipLocalSetFuzzTest
org.jmisb.api.klv.st0903.vfeature.VFeatureLocalSetFuzzTest
org.jmisb.api.klv.st0903.vmask.VMaskLocalSetFuzzTest
org.jmisb.api.klv.st0903.vobject.VObjectLocalSetFuzzTest
org.jmisb.api.klv.st0903.vtarget.VTargetPackFuzzTest
org.jmisb.api.klv.st0903.vtrack.VTrackLocalSetFuzzTest
org.jmisb.api.klv.st0903.vtrack.VTrackItemFuzzTest
org.jmisb.api.klv.st0903.vtracker.VTrackerLocalSetFuzzTest)

pushd "../api"

for item in "${tests[@]}"
do
    echo "Fuzz-testing $item..."
    mvn jqf:fuzz -Dclass=$item -Dmethod=checkCreateValue -Dtime=$time
done

popd
