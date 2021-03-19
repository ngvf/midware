package cn.com.jr.HTUmidware.serverofweb.protocol;


import java.io.Serializable;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——--------——+——----------——+——---------——+——-----——+——----——+
 * |协议开始标志 |  web系统长度 | web系统类型  | 数据长度  |  数据  |
 * +——-------——+——---------——+——---------——+——-------——+——---——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0xCAFF
 * 2.web系统长度 webSysLength，int类型
 * 3.web系统类型 webSysType，  String类型
 * 4.要传输的数据长度 contentLength，int类型
 * 5.要传输的数据 content，byte[]类型
 * </pre>
 */
public class SimpleProduct implements Serializable{
	private static Logger logger = LoggerFactory.getLogger(SimpleProduct.class);
    /**
     * 消息的开头的信息标志
     */
    private int head_data = ConstantValue.HEAD_DATA;
    /**
     * 操作系统长度
     */
    private int webSysLength;
    /**
     * 操作系统类型
     */
    private String webSysType;
    /**
     * 消息的长度
     */
    private int contentLength;
    /**
     * 消息的内容
     */
    private byte[] content;



    /**
     * 用于初始化，SmartCarProtocol
     *
     * @param contentLength
     *            协议里面，消息数据的长度
     * @param content
     *            协议里面，消息的数据
     */
    public SimpleProduct(int webSysLength, String webSysType, int contentLength, byte[] content) {
        this.webSysLength = webSysLength;
        this.webSysType = webSysType;
        this.contentLength = contentLength;
        this.content = content;

    }

    public int getHead_data() {
        return head_data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getWebSysLength() {
        return webSysLength;
    }

    public void setWebSysLength(int webSysLength) {
        this.webSysLength = webSysLength;
    }

    public String getWebSysType() {
        return webSysType;
    }

    public void setWebSysType(String webSysType) {
        this.webSysType = webSysType;
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "head_data=" + head_data +
                ", webSysLength=" + webSysLength +
                ", webSysType=" + webSysType +
                ", contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
    
}
