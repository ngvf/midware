package cn.com.jr.HTUmidware.configuration;

import java.util.HashMap;
import java.util.Map;

/*
 *   web端项目标识 和 web端项目地址   保存容器类
 */
public class ProjectConfig {

	private final Map<Object, String> projectAddressMap = new HashMap<Object, String>();

	public String getProjectAddress(Object obj) {
		return projectAddressMap.get(obj);
	}

	public  String addProjectAddressMap(Object obj, String address) {
		if (obj != null && address != null) {
			return projectAddressMap.put(obj, address);
		}
		return null;
	}

	public String removeProjectAddressMap(Object obj) {
		if (obj != null) {
			return projectAddressMap.remove(obj);
		}
		return null;
	}

}
