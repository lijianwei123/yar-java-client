package com.youzan.yar.protocal.body.packager.msgpack;

import java.io.IOException;

import org.msgpack.MessagePack;

import com.youzan.yar.protocal.body.packager.Packager;

public class MsgPack implements Packager<Object>
{

	@Override
	public byte[] pack(Object o) throws IOException {
		MessagePack msgPack = new MessagePack();
		return msgPack.write(o);
	}

	@Override
	public Object unpack(byte[] bytes) throws IOException {
		MessagePack msgPack = new MessagePack();
		
		return msgPack.read(bytes, ObjectTemplate.getInstance());
	}

}
