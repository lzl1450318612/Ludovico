package coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import pojo.InvokeInfo;

import java.util.List;

/**
 * @author lizilin
 */
public interface Coder {

    void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> objects);

    void encode(ChannelHandlerContext channelHandlerContext, InvokeInfo invokeInfo, ByteBuf byteBuf);

}
