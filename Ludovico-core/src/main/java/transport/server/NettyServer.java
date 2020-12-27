package transport.server;

import coders.MsgDecoder;
import coders.MsgEncoder;
import handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author lizilin
 * @date 2020/10/26 5:16 下午
 */
@Slf4j
public class NettyServer {

    @Value("ludovico.service.port")
    private int port = 12345;

    @SneakyThrows
    public void start() {
        log.error("abc");
        String host = InetAddress.getLocalHost().getHostAddress();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new MsgDecoder());
                            socketChannel.pipeline().addLast(new MsgEncoder());
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            // 绑定端口，同步等待绑定成功
            ChannelFuture f = serverBootstrap.bind(host, port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Server boot failed:", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.info("----------Server shutdown----------");
        }
    }
}
