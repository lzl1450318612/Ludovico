package transport.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import pojo.InvokeResponse;

/**
 * @author lizilin
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<InvokeResponse> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, InvokeResponse invokeResponse) throws Exception {
        RequestPool.complete(invokeResponse);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState idleState = ((IdleStateEvent) evt).state();
            if (idleState == IdleState.WRITER_IDLE) {

            }
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }


}
