package coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pojo.InvokeInfo;
import utils.SpringContextUtils;

/**
 * @author lizilin
 */
public class MsgEncoder extends MessageToByteEncoder<InvokeInfo> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, InvokeInfo invokeInfo, ByteBuf byteBuf) throws Exception {

        // TODO:支持多协议，目前仅支持hessian
        Coder coder = (Coder) SpringContextUtils.getContext().getBean("hessianCoder");
        coder.encode(channelHandlerContext, invokeInfo, byteBuf);
    }

}
