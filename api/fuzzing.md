Input fuzzing
=============

jmisb has some input fuzzing support, using JQF.

It is integrated into maven, so you can do:

``` sh
./mvnw jqf:fuzz -pl :api -Dclass=org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest -Dmethod=checkCreateValue -Dtime=25m
```

(the `-Dtime` argument is minutes in this case, you can use `s` for seconds if preferred)

You can ^C or wait for the results.
Output goes to `target/fuzz-results/org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest/checkCreateValue/`, and
the `failure` directory of that has the failure cases.

If there are any failure cases, you can reproduce a failure using:

``` sh
./mvnw jqf:repro -pl :api -Dclass=org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest -Dmethod=checkCreateValue -Dinput=api/target/fuzz-results/org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest/checkCreateValue/failures/id_000000 -DprintArgs
```

where the `id_000000` part corresponds to the failure.

Other fuzz targets:

- `-Dclass=org.jmisb.api.klv.st0102.localset.LocalSetFactoryFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.VmtiLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.algorithm.AlgorithmLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.ontology.OntologyLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vchip.VChipLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vfeature.VFeatureLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vmask.VMaskLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vobject.VObjectLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vtarget.VTargetPackFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vtrack.VTrackLocalSetFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vtrack.VTrackItemFuzzTest -Dmethod=checkCreateValue`
- `-Dclass=org.jmisb.api.klv.st0903.vtracker.VTrackerLocalSetFuzzTest -Dmethod=checkCreateValue`
