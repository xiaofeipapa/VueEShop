package org.xfh.mid.vo.web;

import org.xfh.mid.db.po.FrontHomeAdConfig;
import org.xfh.mid.vo.HomeFloorVo;

/**
 * 把楼层和广告放在一起
 * @author cys
 *
 */
public class WebFloorAdPair {
	HomeFloorVo floor;
	FrontHomeAdConfig config;		// 横幅广告
	public HomeFloorVo getFloor() {
		return floor;
	}
	public void setFloor(HomeFloorVo floor) {
		this.floor = floor;
	}
	public FrontHomeAdConfig getConfig() {
		return config;
	}
	public void setConfig(FrontHomeAdConfig config) {
		this.config = config;
	}
}
