package com.youzan.yar.protocal;

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
		
		prefix = bodyPackager.getBytes();
	}
	
	public Packager getPackager()
	{
		PackagerFactory factory = new PackagerFactory();
		
		Packager p = null;
		
		switch (bodyPackager) {
			case Packager.MSGPACK_PACKAGE:
				p = factory.getInstance(MsgPack.class);
			break;
			
			case Packager.JSON_PACKAGE:
				
			break;
			
			case Packager.PHP_PACKAGE:
				
			break;
		}
		return p;
	}
}
