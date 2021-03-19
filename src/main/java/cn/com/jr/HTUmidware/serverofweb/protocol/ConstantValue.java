package cn.com.jr.HTUmidware.serverofweb.protocol;
/**
 * 
 * @author yangdd
 *
 *
 */
 
public class ConstantValue {

    /**
     * 消息头标识位
     */
    public static final int HEAD_DATA = 0xCAFF;

    /**
     * 协议最消息长度
     */
    public static final int MAX_LENGTH = 2048;

    /**
     * 基本信息长度 消息头int 4byte 消息长度 int 4byte
     */
    public static final int BASE_LENGTH = 4 + 4 + 4;
}
