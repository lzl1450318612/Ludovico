package transport.client;

import coders.MsgDecoder;
import coders.MsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author lizilin
 * @date 2020/11/16 7:57 下午
 */
@Slf4j
public class NettyClient {

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new MsgEncoder());
                            socketChannel.pipeline().addLast(new MsgDecoder());
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
        }catch (Exception e){
            log.error("Client start failed:",e);
        }finally {
            workerGroup.shutdownGracefully();
            log.info("--------Client shutDown--------");
        }





    }

}
