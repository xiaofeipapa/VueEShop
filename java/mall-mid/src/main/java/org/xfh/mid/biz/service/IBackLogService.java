package org.xfh.mid.biz.service;

import java.util.Date;
import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.mid.db.po.BackLog;
import org.xfh.mid.vo.ChangeFieldDataVo;

public interface IBackLogService extends ISingleTableService<BackLog>{

	// 修改关键数据的时候, 需要插入log
	void insertCriticalLog(String biz,String tableName, Long dataId, Long userId, Date now, List<ChangeFieldDataVo> dataList);
}
