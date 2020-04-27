package org.xfh.dcore.ex;

/**
 * session 不再有效的时候抛出这个异常. 
 *  
 * @author cys
 *
 */
public class SessionInvalidException extends Exception{
	
	// 此属性通常用于ajax 检查与解析
	public static final String INVALID_KEY = "merchant_session_is_invalid";

	private static final long serialVersionUID = -5626844230531361534L;
	
	public SessionInvalidException(){}
	
	public SessionInvalidException(String message){
		super(message);
	}
	
	public SessionInvalidException(Throwable th){
		super(th);
	}
	
}
