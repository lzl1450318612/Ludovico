package hessian;

import common.SimplePerformanceBase;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import pojo.InvokeInfo;
import utils.HessianUtils;

public class HessianTest extends SimplePerformanceBase {

    private static InvokeInfo invokeInfo = new InvokeInfo();

    private static byte[] invokeInfoBytes;

    static {
        invokeInfo.setArgs(new Object[]{"abc", 1, 2L, 2.2d});
        invokeInfo.setArgsTypes(new Class[]{String.class, Integer.class, Long.class, Double.class});
        invokeInfo.setMethodName("coders.decode");

        invokeInfoBytes = HessianUtils.serialize(invokeInfo);
    }

    @Test
    public void testHessianDecodeAndEncode() {
        byte[] bytes = HessianUtils.serialize(invokeInfo);
        InvokeInfo deserializeInvokeInfo = HessianUtils.deserialize(bytes, InvokeInfo.class);

        Assert.assertEquals(invokeInfo, deserializeInvokeInfo);
    }


    @Benchmark
    public byte[] testHessianDecodePerformance() {
        return HessianUtils.serialize(invokeInfo);
    }

    @Benchmark
    public InvokeInfo testHessianEncodePerformance() {
        return HessianUtils.deserialize(invokeInfoBytes, InvokeInfo.class);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(HessianTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
