package org.xfh.mid.biz.orderService;

import java.util.List;

import org.xfh.mid.vo.BizError;
import org.xfh.mid.vo.form.AllocBatchSaveForm;
import org.xfh.mid.vo.form.TempAllocInfoPackageVo;

/**
 * 1) 保存调拨信息
 * 2) 生成出库单 
 * 
 * @author cys
 *
 */
public interface IOrderMakeAllocInfoPackage {

	// 批量保存调拨信息
	void batchSaveAllocInfo(Long userId, AllocBatchSaveForm form)throws Exception;

	// 生成临时的出货单
	TempAllocInfoPackageVo showAllocPackageInfo(Long userId, String bizId)throws Exception;
	
	// 检查出库单数据是否ok
	List<BizError> checkPackageOK(Long backUserId, String bizId)throws Exception;
	
	// 确认生成出库单
	void confirmPackageData(Long backUserId, String bizId)throws Exception;

}
