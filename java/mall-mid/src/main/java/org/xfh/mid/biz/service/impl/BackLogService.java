package org.xfh.mid.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.mid.biz.service.IBackLogService;
import org.xfh.mid.db.po.BackLog;
import org.xfh.mid.db.po.BackUser;
import org.xfh.mid.vo.ChangeFieldDataVo;

@Component
public class BackLogService extends AbstractSingleTableService<BackLog> 
	implements IBackLogService{
	
	private Logger logger = LoggerFactory.getLogger(BackLogService.class);

	@Override
	protected void checkBeforeCreateOrUpdate(BackLog entity, boolean isCreate) throws LogicException {
		// log 不需要检查已有数据
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void insertCriticalLog(String biz, String tableName, Long dataId, Long userId, Date now,
			List<ChangeFieldDataVo> dataList) {
		
		BackUser backUser = daoHelper.getById(BackUser.class, userId);
		
		BackLog log = new BackLog();
		log.setCreateTime(now);
		log.setCreateUserId(userId);
		log.setRealName(backUser.getRealName());
		log.setLoginIp(backUser.getLoginIp());
		log.setPhone(backUser.getPhone());
		
		StringBuilder sb = new StringBuilder();
		sb.append("=============== 核心数据修改 begin");
		sb.append("=============== 业务: " + biz);
		sb.append("=============== 修改表名: " + tableName);
		sb.append("=============== 数据pk: " + dataId);
		for(ChangeFieldDataVo data : dataList) {
			sb.append("---- " + data);
		}
		sb.append("=============== 核心数据修改 end");
		
		log.setDetail(sb.toString());
		daoHelper.insertOne(BackLog.class, log);		
	}

}
