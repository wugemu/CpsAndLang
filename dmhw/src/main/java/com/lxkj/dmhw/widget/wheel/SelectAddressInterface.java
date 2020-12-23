package com.lxkj.dmhw.widget.wheel;

public interface SelectAddressInterface {

	/**
	 * 用于地址选择器完成选择后更新最新的地址
	 * @param area
	 */
	void setAreaString(String area);
	void setArea(String province, String city, String area);
}
