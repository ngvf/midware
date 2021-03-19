package cn.com.jr.HTUmidware.configuration;

import cn.com.jr.HTUmidware.util.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 协议长度保存容器类
 * 
 */
public class ProtocolConfig {

	private ProtocolConfig(){

	}

	private static ProtocolConfig protocolConfig = new ProtocolConfig();

	/**
	 * 可接收的协议标识集合
	 */
	private final List<Byte> receiveContainer = new ArrayList<Byte>();

	/**
	 * 各个协议标识对应的数据长度
	 */
	private final Map<Byte, Integer> receiveMap = new HashMap<Byte, Integer>();

	/**
	 * 系统协议头 : 长度 104 , 1
	 */
	private final Map<String,Integer> websystemMap=new HashMap<String,Integer>();


	public List<Byte> getReceiveContainer() {
		return receiveContainer;
	}

	public Map<Byte, Integer> getReceiveMap() {
		return receiveMap;
	}

	public Map<String,Integer> getWebsystemMap(){
		return websystemMap;
	}

	/**
	 *
	 * @return
	 */
	public static ProtocolConfig getInstace(){
		return protocolConfig;
	}


	public Integer getLength(Byte type) {
		if (type != null) return receiveMap.get(type);
		return null;
	}

	public boolean addReceiveType(Byte item) {
		if (item != null) { return receiveContainer.add(item); }
		return false;
	}

	public boolean removeReceiveType(Byte item) {
		if (item != null) { return receiveContainer.remove(item); }
		return false;
	}

	public Integer addReceiveLength(Byte type, Integer length) {
		if (type != null && length != null) { return receiveMap.put(type, length); }
		return null;
	}

	public Integer removeReceiveLength(Byte type) {
		if (type != null) { return receiveMap.remove(type); }
		return null;
	}

	public Integer addWebsystemMap(String key,Integer len){
		if(Tools.isNotEmpty(key) && Tools.isNotEmpty(len))
			return websystemMap.put(key, len);

		return null;
	}

	public Integer removeWebsystemMap(String key){
		if(Tools.isNotEmpty(key))
			return websystemMap.remove(key);
		return null;
	}

}
