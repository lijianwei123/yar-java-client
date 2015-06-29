package com.youzan.yar;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.youzan.yar.protocal.body.packager.Packager;
import com.youzan.yar.protocal.body.packager.msgpack.MsgPack;
import com.youzan.yar.protocal.header.Unpack;
import com.youzan.yar.unit.PackTest;

public class test
{	
	
	public test() {
	}
	
	protected void t(test2 t2)
	{
		t2.a = 100;
	}
	


	public static void main(String[] args) throws Exception {
		
//		byte[] s = new byte[4];
//		s[0] = (byte)0x80;
//		s[1] = (byte)0xDF;
//		s[2] = (byte)0xEC;
//		s[3] = (byte)0x60;
//		
//		ByteBuffer b = ByteBuffer.wrap(s);
//		
//		int l = Unpack.readB4(b);
//		
//		System.out.println(l);
//		
//		System.out.println(0x80DFEC60);
//		
//		System.exit(0);
		
//		String bodyPackager = "MSGPACK";
//		switch (bodyPackager) {
//			case Packager.MSGPACK_PACKAGE:
//				System.out.println("lijianwei");
//			break;
//			
//			case Packager.JSON_PACKAGE:
//				
//			break;
//			
//			case Packager.PHP_PACKAGE:
//				
//			break;
//		}
//		System.exit(0);
		
//		String s = new String("lijianwei");
//		String s2 = new String("lijianwei");
//		
//		System.out.println(s.compareTo(s2));
		
		Client phpClient = new Client("http://10.211.55.4/server.php", Packager.MSGPACK_PACKAGE);
		String a = "33333";
		Map<String, Object> map = (HashMap<String, Object>)phpClient.call("test", a);
		System.out.println(map.get("r").getClass().getSimpleName());
	}
	
	public static void printHexString(byte[] b)
	{ 
		for (int i = 0; i < b.length; i++) { 
			String hex = Integer.toHexString(b[i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex;
			} 
			System.out.print(hex.toUpperCase()); 
		}
		
		System.out.println();
	}
}

class test2
{
	int a = 10;
}

