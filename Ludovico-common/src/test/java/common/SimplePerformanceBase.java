package common;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime, Mode.Throughput, Mode.SingleShotTime})
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class SimplePerformanceBase {
}
