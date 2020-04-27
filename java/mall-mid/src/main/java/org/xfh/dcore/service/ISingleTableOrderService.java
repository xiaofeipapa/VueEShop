package org.xfh.dcore.service;

/**
 * 带上移/下移功能的service
 * 
 * @author cys
 *
 */
public interface ISingleTableOrderService<T> extends ISingleTableService<T> {

	void moveUp(Long userId,Long id, String orderField)throws Exception;
	
	void moveDown(Long userId,Long id, String orderField)throws Exception;
}
