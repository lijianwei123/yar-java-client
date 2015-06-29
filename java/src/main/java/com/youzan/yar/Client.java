package com.youzan.yar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.youzan.yar.protocal.Body;
import com.youzan.yar.protocal.Header;
import com.youzan.yar.protocal.body.packager.Packager;
import com.youzan.yar.unit.PackTest;

public class Client {
	
	private static final HttpClient client = new HttpClient();
	
	private static final int HTTP_SUCCESS = 200;
	
	private static final String CHARSET_UTF8 = "UTF-8";
	
	
	String yarServerUrl = "";
	
	Header header = null;
	
	Body body = null;
	
	Header respHeader = null;
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	ByteBuffer byteBuf = ByteBuffer.allocate(4096);
	
	
	byte[] packager = new byte[8];
	
	int bodyLen = 0;
	
	byte[] tmpBytes = null;

	public Client(String url, String packager)
	{
		if (url != null) {
			yarServerUrl = url;
		}
		
		body = new Body(packager);
	}
	
	
	public  Object call(String method) throws Exception
	{
		return call(method, null);
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

	public  Object call(String method, Object params) throws Exception
	{	
		if (yarServerUrl == null) {
			throw new Exception("yarServerUrl为空！");
		}
		
		header = new Header();
		respHeader = new Header();
		
		int headerId = getRandNum();
		headerId = 2222;
		header.setHeaderId(headerId);
		
		
		map.clear();
		map.put("i", headerId);
		map.put("m", method);
		map.put("p", params);
		
		
		byte[] bodyData = body.getPackager().pack(map);
//		printHexString(bodyData);
//		System.exit(0);
		
		header.setHeaderBodyLen(bodyData.length);
		
		byteBuf.clear();
		header.pack(byteBuf);
		byteBuf.put(body.prefix);
		byteBuf.put(bodyData);
		
		byteBuf.flip();
		tmpBytes = new byte[byteBuf.limit()];
		byteBuf.get(tmpBytes, 0, tmpBytes.length);
		
		byte[] response = postRawData(tmpBytes);
		
		if (response != null) {
			
			byteBuf.clear();
			byteBuf.put(response);
			byteBuf.flip();
			
//			printHexString(response);
			
			respHeader.unPack(byteBuf);
			if (respHeader.getHeaderMagicNum() != header.getHeaderMagicNum()) {
				throw new Exception("magic num error");
			}
			
			Arrays.fill(packager, (byte)0);
			byteBuf.get(packager, 0, packager.length);
		
			body = new Body(new String(packager, CHARSET_UTF8).trim());
			bodyLen = respHeader.getHeaderBodyLen();
			
			tmpBytes = new byte[bodyLen - packager.length];
			byteBuf.get(tmpBytes, 0, tmpBytes.length);
			
			return body.getPackager().unpack(tmpBytes);
		}
		
		return null;
	}
	
	
	
	@SuppressWarnings("deprecation")
	protected byte[] postRawData(byte[] postData)
	{
		PostMethod method = new PostMethod(yarServerUrl);

		try {
			
			InputStream in = new ByteArrayInputStream(postData);

			method.setRequestBody(in);
			
			HttpClientParams params = new HttpClientParams();
			method.setParams(params);
		
			client.executeMethod(method);
			
			if (HTTP_SUCCESS == method.getStatusCode()) {
				return method.getResponseBody();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
		return null;
	}
	
	protected  int getRandNum()
	{
		Random r = new Random();
		return r.nextInt(Integer.MAX_VALUE);
	}
	
	
	public static void main(String[] args)
	{
		System.out.println(0x80DFEC60);
	}
}
