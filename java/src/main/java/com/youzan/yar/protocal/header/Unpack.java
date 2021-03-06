package com.youzan.yar.protocal.header;

import java.nio.ByteBuffer;

public class Unpack
{	
	public static int readB4(ByteBuffer b)
	{
		int l = (int)(b.get() & 0xff) << 24;
		l |= (int)(b.get() & 0xff)  << 16;
		l |= (int)(b.get() & 0xff) << 8;
		l |= (int)(b.get() & 0xff);
		return l;
	}
	
	public static short readB2(ByteBuffer b)
	{
		int l = (int)(b.get() & 0xff) << 8;
		l |= (int)(b.get() & 0xff);
		return (short)l;
	}
}
