package org.xfh.mid.biz.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableOrderService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IBackMenuService;
import org.xfh.mid.db.dao.BackUserDao;
import org.xfh.mid.db.po.BackMenu;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.vo.BackMenuVo;

@Component
public class BackMenuServiceImpl extends AbstractSingleTableOrderService<BackMenu> 
	implements IBackMenuService{
	
	private Logger logger = LoggerFactory.getLogger(BackMenuServiceImpl.class);
	
	@Autowired
	BackUserDao userDao;

	@Override
	protected void checkBeforeCreateOrUpdate(BackMenu entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getName(), "请输入菜单名称");
		
		if(entity.getLevel() == BackMenu.MENU_CHILD) {
			VUtils.checkM(entity.getLink(), "请输入页面链接");			
		}
		
		String error = "同一层级的菜单名称已经存在: " + entity.getName();
		
		// 同一层级的分类名字不能重复
		String sqlFilter = DSqlUtils.andEqNumber("level", entity.getLevel(), true);
		sqlFilter += DSqlUtils.andEqString("name", entity.getName(), true);
		sqlFilter += DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
				
		if(isCreate) {
			// 新增的情况只需要检查重名
			int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
			if(existCount > 0) {
				throw new LogicException(error);
			}
			
			// 设置排序
			String orderSql = DSqlUtils.andEqNumber("level", entity.getLevel(), true);
			orderSql += DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", orderSql);
			entity.setOrder(maxOrder + 1);
						
			return;
		}

		// 更新的情况, 如果记录数大于1肯定重复, 如果只是1, 检查是否本记录. 
		
		Map exist = commonDao.getOneBySql(this.tableName, sqlFilter);
		
		if(exist == null || exist.get("id") == null) {
			return;
		}
		
		long existId = Long.valueOf(exist.get("id").toString());
		if(existId != entity.getId().intValue()) {
			throw new LogicException(error);	
			
		}
	}

	// 获取系统的所有菜单
	@Override
	public List<BackMenuVo> getAllMenu(Long userId) throws Exception {
		
		List<BackMenuVo> menus = userDao.getAllMenus();
		return this.convertToTreeNodeWithOrder(menus);
	}

	// 获取用户的菜单
	@Override
	public List<BackMenuVo> getUserMenuList(Long userId) throws Exception {
		
		// 获取用户的权限
		List<BackMenuVo> userMenus = userDao.getUserMenuList(userId);
		
		
		return this.convertToTreeNodeWithOrder(userMenus);
	}
	
	// 将菜单按照排序变成树形结构
	@Override
	public List<BackMenuVo> convertToTreeNodeWithOrder(List<BackMenuVo> menus) throws Exception {
				
		Map<Long, BackMenuVo> parentMap = new HashMap<>();
		List<BackMenuVo> childList = new ArrayList<>();
		
		// 先找出根节点
		for(BackMenuVo data : menus) {

			boolean isParent = data.getLevel() == BackMenu.MENU_PARENT;
			boolean isChild = data.getLevel() == BackMenu.MENU_CHILD;
						
			if(isParent) {
				parentMap.put(data.getId(), data);
			}
			
			if(isChild) {
				childList.add(data);
			}
		}

		// 找出二级节点并添加
		for(BackMenuVo city : parentMap.values()) {

			for(BackMenuVo vo : childList) {

				Long parentId = vo.getParentId();

				if(city.getId().longValue() == parentId.longValue()) {
					city.addChild(vo);;
				}
				
			}
			
			// 确保排序
			city.getChildren().sort(new BackMenu());
			
			
		}

		List<BackMenuVo> resultList = new ArrayList<>();
		for(BackMenuVo vo : parentMap.values()) {
			resultList.add(vo);
		}
				
		resultList.sort(new BackMenu());
		
		return resultList;
	}

	@Override
	public List<Long> getUserMenuIdList(Long userId) throws Exception {
		
		Collection<BackMenuVo> menus = this.getUserMenuList(userId);
		List<Long> ids = new ArrayList<>();
		
		for(BackMenuVo vo : menus) {
			
			ids.add(vo.getId());
			
			for(BackMenuVo child : vo.getChildren()) {
				ids.add(child.getId());
			}
			
		}
		return ids;
	}

	@Override
	public List<Long> getMenuIdByRoleId(Long roleId) {

		return userDao.getMenuIdByRoleId(roleId);
	}

	@Override
	public Long checkSaveUserMenu(Long userId, BackMenu entity) throws Exception {
		Long id = this.saveWithCheck(userId, entity, null);
		
		// 获取用户的角色id, 注: 能够操作菜单界面的肯定是管理员
		Map user = commonDao.getById(DBs.TABLE_BACK_USER, userId);
		Long roleId = Long.valueOf(user.get("id").toString());
		
		// 查看菜单关联
		String sql = DSqlUtils.andEqNumber("roleId", roleId, true);
		sql += DSqlUtils.andEqNumber("menuId", id, true);
		Map exist = commonDao.getOneBySql(DBs.TABLE_BACK_ROLE_MENU, sql);
		if(exist == null) {
			TreeMap param = new TreeMap<>();
			param.put("roleId", roleId);
			param.put("menuId", id);
			commonDao.insertOne(DBs.TABLE_BACK_ROLE_MENU, param);
		}
		return id;
	}

	@Override
	protected String sameParentFilter(BackMenu entity) {
		return DSqlUtils.andEqNumber("parentId", entity.getParentId(), true);
	}
	
	
	@Transactional
	@Override
	public void deleteById(Long loginUserId, Long id) throws Exception {

		
		String sql = DSqlUtils.andEqNumber(DBs.MENU_ID, id, true);
		int count = commonDao.getCountBySql(DBs.TABLE_BACK_ROLE_MENU, sql);
		if(count > 0) {
			throw new LogicException("已有角色使用了这个菜单, 无法删除. 请先在角色管理界面移除角色和这个菜单的关联");
		}
		

		BackMenu theData = this.getById(id);
		if(theData == null) {
			return;
		}
		
		Integer theOrder = theData.getOrder();
		Long parentId = theData.getParentId();
		
		// 比这个order小的数据不用动, 比它大的数据均需要 order -1
		String sqlFilter = DSqlUtils.andGtNumber("order", theOrder, true);
		sqlFilter += DSqlUtils.andEqNumber("parentId", parentId, true);
		
		commonDao.deleteById(this.tableName, id);
		commonDao.subtractFieldBySql(this.tableName, "order", 1, sqlFilter);
				
	}

}
