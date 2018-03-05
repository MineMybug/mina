package com.myminebug.mina.entity;

import java.io.Serializable;

public class Message implements Serializable { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 767417377830113106L;
	private int type; 
    private int status; 
    private String msgBody; 
    
    public Message(int type, int status, String msgBody) 
    { 
        this.type = type; 
        this.status = status; 
        this.msgBody = msgBody; 
    } 
    public String getMsgBody() { 
        return msgBody; 
    } 
    public void setMsgBody(String msgBody) { 
        this.msgBody = msgBody; 
    } 
    public int getStatus() { 
        return status; 
    } 
    public void setStatus(int status) { 
        this.status = status; 
    } 
    public int getType() { 
        return type; 
    } 
    public void setType(int type) { 
        this.type = type; 
    } 
} 