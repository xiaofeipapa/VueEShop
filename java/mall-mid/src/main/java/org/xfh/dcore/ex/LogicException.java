package org.xfh.dcore.ex;

/** 
 * 业务代码异常
 * 
 * @author cys
 *
 */
public class LogicException extends Exception{

	private static final long serialVersionUID = -5626844230531361534L;
	
	public LogicException(){}

	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogicException(String message){
		super(message);
	}
	
	public LogicException(Throwable th){
		super(th);
	}
	
}
