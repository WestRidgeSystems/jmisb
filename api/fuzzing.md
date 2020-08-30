Input fuzzing
=============

jMISB has some input fuzzing support, using JQF.

It is integrated into maven, so you can do:

``` sh
mvn jqf:fuzz -Dclass=org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest -Dmethod=checkCreateValue -Dtime=25m
```

(the `-Dtime` argument is minutes in this case, you can use `s` for seconds if preferred)

You can ^C or wait for the results.
Output goes to `target/fuzz-results/org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest/checkCreateValue/`, and
the `failure` directory of that has the failure cases.

If there are any failure cases, you can reproduce it using:

``` sh
mvn jqf:repro -Dclass=org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest -Dmethod=checkCreateValue -Dinput=target/fuzz-results/org.jmisb.api.klv.st0601.UasDatalinkFactoryFuzzTest/checkCreateValue/failures/id_000000 -DprintArgs
```

where the `id_000000` part corresponds to the failure.

Other fuzz targets:

- None yet.
