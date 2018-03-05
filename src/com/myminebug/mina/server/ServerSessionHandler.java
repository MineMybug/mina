package com.myminebug.mina.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myminebug.mina.client.ClientSessionHandler;
import com.myminebug.mina.entity.Message;

public class ServerSessionHandler extends IoHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(ServerSessionHandler.class);
	
//	/**
//	 * 当一个客户端连接进入时 
//	 */
//	@Override
//	public void sessionOpened(IoSession session) {
//		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
//		session.getRemoteAddress();
//
//		System.out.println("服务端与客户端连接打开。。。");
//	}

	/**
	 * 接收到客户端消息时
	 */
	@Override
	public void messageReceived(IoSession session, Object message) {
		System.out.println("服务端收到的数据：" + message.toString());
//		int times = ((Integer) (session.getAttribute("times"))).intValue();
//		System.out.println("times = " + times);

		// communicate 30 times,then close the session.
//		if (times < 30) {
//			times++;
//			session.setAttribute("times", new Integer(times));
//			Message msg;
//			msg = (Message) message;
//			msg.setMsgBody("in server side: " + msg.getMsgBody());
//			System.out.println("begin send msg: " + msg.getMsgBody());
//			session.write(msg);
//		} else {
//			session.close();
//		}
		String phoneMes = message.toString();

        String[]megs=phoneMes.split(";");

        String sendPhone = megs[0];

        String receivePhone = megs[1];

        String mes = megs[2];

        logger.info("发送人手机号码：" + sendPhone);

        logger.info("接受人手机号码：" + receivePhone);

        logger.info("发送信息：" + mes);

        // 短信信息存入移动服务端数据库或者写入手机短信转发队列

        // ............

        session.write("发送成功！"); // 告诉手机发送信息成功啦
	}

	@Override

	public void messageSent(IoSession session, Object message) throws Exception {

		session.close();

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// close the connection on exceptional situation
		logger.error("服务端发送异常...", cause);
	}
}
