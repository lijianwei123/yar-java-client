package com.youzan.yar.protocal.body.packager;

import java.io.IOException;


public interface Packager<T>
{
	public static final String PHP_PACKAGE = "PHP";
	
	public static final String JSON_PACKAGE = "JSON";
	
	public static final String MSGPACK_PACKAGE = "MSGPACK";
	
	
	
	public byte[] pack(T o) throws IOException;
	
	public T unpack(byte[] b) throws IOException;
}
