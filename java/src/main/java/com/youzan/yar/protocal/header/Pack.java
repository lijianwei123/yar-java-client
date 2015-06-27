package com.youzan.yar.protocal.header;

public class Pack
{
	//N   unsigned 32 bit
	public static byte[] packint4(int l)
	{
        byte[] b = new byte[4];
        b[0] = (byte) (l >>> 24);
        b[1] = (byte) (l >>> 16);
        b[2] = (byte) (l >>> 8);
        b[3] = (byte) (l & 0xff);
         
        return b;
	}
	
	//n unsigned 16 bit
	public static byte[] packShort2(short l)
	{
		byte[] b = new byte[2];
		
		b[0] = (byte)(l >>> 8);
		b[1] = (byte)(l & 0xff);
		
		return b;
	}
}
