package utils;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author lizilin
 */
public class HessianUtils {

    /**
     * Hessian序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Hessian的序列化输出
        HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
        try {
            hessianOutput.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                hessianOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Hessian反序列化
     *
     * @param dataBytes
     * @return
     */
    public static <T> T deserialize(byte[] dataBytes, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataBytes);
        // Hessian的反序列化读取对象
        HessianInput hessianInput = new HessianInput(byteArrayInputStream);
        try {
            byteArrayInputStream = new ByteArrayInputStream(dataBytes);
            // Hessian的反序列化读取对象
            hessianInput = new HessianInput(byteArrayInputStream);
            return clazz.cast(hessianInput.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianInput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
