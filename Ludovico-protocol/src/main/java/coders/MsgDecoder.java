package coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import utils.SpringContextUtils;

import java.util.List;

/**
 * @author lizilin
 */
public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        // TODO:支持多协议，目前仅支持hessian
        Coder coder = (Coder) SpringContextUtils.getContext().getBean("hessianCoder");
        coder.decode(channelHandlerContext, byteBuf, list);

    }
}
