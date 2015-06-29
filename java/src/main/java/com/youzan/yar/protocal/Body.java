package com.youzan.yar.protocal;

import java.util.Arrays;
import java.util.jar.Pack200.Packer;

import com.youzan.yar.protocal.body.packager.Packager;
import com.youzan.yar.protocal.body.packager.PackagerFactory;
import com.youzan.yar.protocal.body.packager.msgpack.MsgPack;

public class Body
{	
	private String bodyPackager = Packager.MSGPACK_PACKAGE;
	
	public byte[] prefix = new byte[8];
	
	public Body(String packager)
	{
		if (packager != null) {
			bodyPackager = packager;
		}
	
		System.arraycopy(packager.getBytes(), 0, prefix, 0, packager.getBytes().length);
	}
	
	public Packager getPackager()
	{
		PackagerFactory factory = new PackagerFactory();
		
		Packager p = null;

		
		if (bodyPackager.equals(Packager.MSGPACK_PACKAGE)) {
				p = factory.getInstance(MsgPack.class);
		} else if (bodyPackager.equals(Packager.JSON_PACKAGE)) {
				
		} else if (bodyPackager.equals(Packager.PHP_PACKAGE)) {
			
		}
		return p;
	}
}
