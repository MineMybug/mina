package com.myminebug.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myminebug.mina.entity.Message;

public class ClientSessionHandler extends IoHandlerAdapter {
	
//	private Object msg;
//
//	public ClientSessionHandler(Object msg) {
//		this.msg = msg;
//	}
	
	private static Logger logger = LoggerFactory.getLogger(ClientSessionHandler.class);
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {

		System.out.println("服务端与客户端创建连接...");

	}

//	@Override
//	public void sessionOpened(IoSession session) throws Exception {
//
//		session.write(this.msg);
//		System.out.println("服务端与客户端连接打开...");
//
//	}

	@Override
	public void messageReceived(IoSession session, Object message) {
//		System.out.println("客户端收到的数据为：" + message);
//		Message rm = (Message) message;
//
//		// SessionLog.debug(session, rm.getMsgBody());
//		System.out.println("message is: " + rm.getMsgBody());
//		session.write(rm);
		
		String msg = message.toString();
		logger.info("客户端接收到的信息：" +  msg);
		System.out.println("客户端接收到的信息：" +  msg);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
//		session.close();
		 logger.error("客户端发生异常...", cause);
	}
}
