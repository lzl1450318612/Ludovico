package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import pojo.InvokeInfo;
import pojo.InvokeResponse;

import javax.annotation.Resource;

/**
 * @author lizilin
 * @date 2020/10/26 7:07 下午
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private InvokeHandler invokeHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InvokeInfo invokeInfo = (InvokeInfo) msg;
        Channel channel = ctx.channel();
        Object result = invokeHandler.handle(invokeInfo);
        InvokeResponse response = InvokeResponse.builder().data(result).code(200).build();
        if (channel.isActive()) {
            ctx.writeAndFlush(response);
        }


    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState idleState = ((IdleStateEvent) evt).state();
            if (idleState == IdleState.READER_IDLE) {
                log.warn("Server idle, close!");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }
}
