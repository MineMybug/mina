package com.myminebug.mina.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务器
 * 
 * @author 阮航
 *
 */
public class MinaServer {

	private static Logger logger = LoggerFactory.getLogger(MinaServer.class);

	private static final int SERVER_PORT = 10010; // 定义监听端口

	public static void main(String[] args) throws Throwable {

		// //创建一个非阻塞的Server端socket，基于NIO
		// IoAcceptor acceptor = new NioSocketAcceptor();
		//
		// //获得接受数据的过滤器，并设定这个过滤器将以对象为单位读取数据
		// acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(
		// new ObjectSerializationCodecFactory()));

		//
		//
		// //指定业务逻辑处理器
		// acceptor.setHandler(new ServerSessionHandler( ) );
		// acceptor.bind( new InetSocketAddress( SERVER_PORT )); //启动监听
		//
		// System.out.println( "Mina server Listening on port " + SERVER_PORT );

		IoAcceptor acceptor = null;

		try {

			// 创建一个非阻塞的server端的Socket

			acceptor = new NioSocketAcceptor();

			//消息过滤器
			acceptor.getFilterChain().addLast("logger", new LoggingFilter());

			// 设置过滤器
			acceptor.getFilterChain().addLast(

					"codec",

					new ProtocolCodecFilter((ProtocolCodecFactory) new TextLineCodecFactory(Charset

							.forName("UTF-8"),

							LineDelimiter.WINDOWS.getValue(),

							LineDelimiter.WINDOWS.getValue())));

			// 获得IoSessionConfig对象
			IoSessionConfig cfg = acceptor.getSessionConfig();

			// 读写通道10秒内无操作进入空闲状态
			cfg.setIdleTime(IdleStatus.BOTH_IDLE, 1000);

			// 绑定逻辑处理器
			acceptor.setHandler(new ServerSessionHandler());

			// 绑定端口
			acceptor.bind(new InetSocketAddress(SERVER_PORT));

			System.out.println("服务端启动成功... 端口号为：" + SERVER_PORT);
			
			logger.info("服务端启动成功... 端口号为：" + SERVER_PORT);

		} catch (Exception e) {

			logger.error("服务端启动异常....", e);

			e.printStackTrace();

		}

	}
}