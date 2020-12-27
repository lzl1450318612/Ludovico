package coders;

import enums.ExceptionEnum;
import exceptions.ServerException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import pojo.InvokeInfo;
import utils.CrcUtils;
import utils.HessianUtils;

import java.util.List;


/**
 * 魔数            1B
 * 数据长度         2B
 * Hessian格式方法调用信息数据  NB
 * 校验码           2B
 *
 * @author lizilin
 */
@Component("hessianCoder")
public class HessianCoder implements Coder {

    private static final int MAGIC = 123;


    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> objects) {
        if (in.readableBytes() < 8) {
            return;
        }
        in.markReaderIndex();
        // 读取魔数
        int magic = in.readByte();

        if (magic != MAGIC) {
            throw new ServerException(ExceptionEnum.DECODE_EXCEPTION);
        }

        // 读取调用信息包长度
        int dataLength = in.readUnsignedShort();

        // 读取校验码
        int verifyCode = in.readShort();

        // 读取调用信息数据
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        in.readBytes(verifyCode);
        in.readerIndex();
        in.resetReaderIndex();

        int crcCode = CrcUtils.getCrc16(data);
        if (crcCode != verifyCode) {
            throw new ServerException(ExceptionEnum.DECODE_EXCEPTION);
        }

        InvokeInfo invokeInfo = HessianUtils.deserialize(data, InvokeInfo.class);

        objects.add(invokeInfo);
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, InvokeInfo invokeInfo, ByteBuf byteBuf) {
        byteBuf.markWriterIndex();
        byteBuf.writeByte(MAGIC);

        byte[] invokeData = HessianUtils.serialize(invokeInfo);
        byteBuf.writeShort(invokeData.length);
        int crcCode = CrcUtils.getCrc16(invokeData);
        byteBuf.writeShort(crcCode);
        byteBuf.resetWriterIndex();
    }
}
