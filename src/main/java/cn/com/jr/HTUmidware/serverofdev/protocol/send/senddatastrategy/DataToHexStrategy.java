package cn.com.jr.HTUmidware.serverofdev.protocol.send.senddatastrategy;

/*
 * 接收数据解析接口
 */
public interface DataToHexStrategy {

	byte[] toHexByteArrStrategy(String content);

}
