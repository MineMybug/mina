package com.myminebug.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端
 * 
 * @author 阮航
 *
 */
public class SimpleClient {

	private static Logger logger = LoggerFactory.getLogger(SimpleClient.class);

	private static final String HOSTNAME = "localhost";
	private static final int PORT = 10010;
	private static final int CONNECT_TIMEOUT = 30 * 1000;

	public static void main(String[] args) throws Throwable {

		// 创建一个非阻塞的客户端socket
		SocketConnector connector = new NioSocketConnector();

		// 设置链接超时时间
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
	     
		// 设置日志过滤器
		connector.getFilterChain().addLast("logger",new LoggingFilter());
		
		// 设置过滤器
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter((ProtocolCodecFactory) new TextLineCodecFactory(Charset

						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),

						LineDelimiter.WINDOWS.getValue())));

		// //消息过滤器
		// connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(
		// new ObjectSerializationCodecFactory()));
		// 添加业务逻辑处理器类

		connector.setHandler(new ClientSessionHandler());
		IoSession session = null;

		try {

			ConnectFuture future = connector.connect(new InetSocketAddress(

					HOSTNAME, PORT));// 创建连接

			future.awaitUninterruptibly();// 等待连接创建完成

			session = future.getSession();// 获得session

			String sendPhone = "13681803609"; // 当前发送人的手机号码

			String receivePhone = "13721427169"; // 接收人手机号码

			String message = "测试发送短信，这个是短信信息哦，当然长度是有限制的哦....";

			String msg = sendPhone + ";" + receivePhone + ";" + message;

			session.write(msg);// 发送给移动服务端

		} catch (Exception e) {

			logger.error("客户端链接异常...", e);

		}

		session.getCloseFuture().awaitUninterruptibly();// 等待连接断开

		connector.dispose();

		// Message msg = new Message(0,1,"hello");
		// connector.setHandler(new ClientSessionHandler(msg));//客户端事务逻辑
		//
		// //连接服务器
		// ConnectFuture future = connector.connect(new InetSocketAddress(
		// HOSTNAME, PORT ));

	}
}
