package cn.com.jr.HTUmidware.serverofdev.supagreementserver.TCP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 * @author yangdd 容器类
 *
 *
 */

public class ServerOfDevContainer {

	/**
	 * 存储每一个客户端接入进来时的channel对象
	 */
	public final static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 通道对应设备id
	 */
	public final static Map<Channel, String> sourceIds = new ConcurrentHashMap<Channel, String>();

	/**
	 * Id对应通道
	 */
	public final static Map<String, Channel> sourceChannels = new ConcurrentHashMap<String, Channel>();

	/**
	 * 通道断开设备id集合
	 */
	public final static Set<String> warnSet = Collections.synchronizedSet(new HashSet<String>());

	/**
	 * 对每个断线设备的心跳请求次数统计.超过3次不再请求.
	 */
	public final static Map<String, Integer> askCount = new ConcurrentHashMap<String, Integer>();


	/**
	 * 消息控制
	 */
	public static Map<Channel, List<byte[]>> messageStack = new ConcurrentHashMap<Channel, List<byte[]>>();

	/**
	 * 写入数据
	 * 
	 * @param channel
	 * @param Msgdata
	 */
	public static void addMessage(Channel channel, byte[] Msgdata) {
		if (!ServerOfDevContainer.messageStack.containsKey(channel)) {
			messageStack.put(channel, new ArrayList<byte[]>());
		}
		List<byte[]> list = ServerOfDevContainer.messageStack.get(channel);
		list.add(Msgdata);
	}

	/**
	 * 获得剩余全部的消息数量
	 * 
	 * @return
	 */
	public static int getMesCount() {
		int count = 0;
		Collection<List<byte[]>> values = ServerOfDevContainer.messageStack.values();
		for (List<byte[]> list : values) {
			if (list == null) continue;
			count += list.size();
		}
		return count;
	}

}