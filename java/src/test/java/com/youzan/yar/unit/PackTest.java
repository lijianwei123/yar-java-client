package com.youzan.yar.unit;

import org.junit.Test;

import com.youzan.yar.protocal.header.Pack;

public class PackTest {

	@Test
	public void testPackLong4() {
		int l = 55;
		
		System.out.println(Long.toBinaryString(l));
		
		printHexString(Pack.packint4(l));
		
	}

	@Test
	public void testPackInt2() {
		short i = 10;
		
		System.out.println(Integer.toBinaryString(i));
		
		printHexString(Pack.packShort2(i));
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
