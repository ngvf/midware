package cn.com.jr.HTUmidware.clientofdev;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufTest {
    public static void main(String[] args) {
        //创建一个16字节的buffer,这里默认是创建heap buffer
        ByteBuf buf = Unpooled.buffer(16);
        //写数据到buffer
        for(int i=0; i<16; i++){
            buf.writeByte(i+1);
        }
        //读数据
        for(int i=0; i<buf.capacity(); i++){
            int i1 = buf.readableBytes();
            buf = buf.markReaderIndex();
//            System.out.println(buf.getByte(i)+",开始读下标："+buf.readerIndex()+", 开始写下标："+buf.writerIndex());
            System.out.println(buf.readByte()+",开始读下标："+buf.readerIndex()+", 开始写下标："+buf.writerIndex());
//            ByteBuf byteBuf = buf.markReaderIndex();
            buf = buf.discardReadBytes();
//             buf = buf.resetReaderIndex();
            System.out.println("dsdsd");

        }
    }

}
