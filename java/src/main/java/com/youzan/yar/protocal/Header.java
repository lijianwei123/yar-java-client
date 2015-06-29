package com.youzan.yar.protocal;

import java.nio.ByteBuffer;

import com.youzan.yar.protocal.header.Pack;
import com.youzan.yar.protocal.header.Unpack;


public class Header
{	
	private int headerId = 0;
	
	private short headerVersion = 0;
	
	private int headerMagicNum = 0x80DFEC60;
	
	private int headerReserved = 0;
	
	private byte[] headerProvider = new byte[32];
	
	private byte[] headerToken = new byte[32];
	
	private int headerBodyLen = 0;
	
	public Header()
	{
	}
	
	public void pack(ByteBuffer b)
	{
		b.put(Pack.packint4(headerId));
		b.put(Pack.packShort2(headerVersion));
		b.put(Pack.packint4(headerMagicNum));
		b.put(Pack.packint4(headerReserved));
		b.put(headerProvider);
		b.put(headerToken);
		b.put(Pack.packint4(headerBodyLen));
	}
	
	public void unPack(ByteBuffer b)
	{
		headerId = Unpack.readB4(b);
		headerVersion = Unpack.readB2(b);
		headerMagicNum = Unpack.readB4(b);
		headerReserved = Unpack.readB4(b);
		
		b.get(headerProvider, 0, headerProvider.length);
		b.get(headerToken, 0, headerToken.length);
		
		headerBodyLen = Unpack.readB4(b);
	}
	
	public int getByteLen()
	{
		return 4 + 2 + 4 + 4 + 32 + 32 + 4;
	}

	
	public int getHeaderId() {
		return headerId;
	}


	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}


	public short getHeaderVersion() {
		return headerVersion;
	}


	public void setHeaderVersion(short headerVersion) {
		this.headerVersion = headerVersion;
	}


	public int getHeaderMagicNum() {
		return headerMagicNum;
	}


	public void setHeaderMagicNum(int headerMagicNum) {
		this.headerMagicNum = headerMagicNum;
	}


	public int getHeaderReserved() {
		return headerReserved;
	}


	public void setHeaderReserved(int headerReserved) {
		this.headerReserved = headerReserved;
	}


	public byte[] getHeaderProvider() {
		return headerProvider;
	}


	public void setHeaderProvider(byte[] headerProvider) {
		this.headerProvider = headerProvider;
	}


	public byte[] getHeaderToken() {
		return headerToken;
	}


	public void setHeaderToken(byte[] headerToken) {
		this.headerToken = headerToken;
	}


	public int getHeaderBodyLen() {
		return headerBodyLen;
	}


	public void setHeaderBodyLen(int headerBodyLen) {
		this.headerBodyLen = headerBodyLen;
	}
}
