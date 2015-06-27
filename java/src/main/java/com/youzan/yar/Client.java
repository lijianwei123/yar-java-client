package com.youzan.yar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.youzan.yar.protocal.Body;
import com.youzan.yar.protocal.Header;

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
	

	public  Object call(String method, Map params) throws Exception
	{	
		if (yarServerUrl == null) {
			throw new Exception("yarServerUrl为空！");
		}
		
		header = new Header();
		respHeader = new Header();
		
		int headerId = getRandNum();
		header.setHeaderId(headerId);
		
		
		map.clear();
		map.put("i", headerId);
		map.put("m", method);
		map.put("p", params);
		
		
		byte[] bodyData = body.getPackager().pack(map);
		header.setHeaderBodyLen(bodyData.length);
		
		
		byteBuf.clear();
		
		header.pack(byteBuf);
		byteBuf.put(body.prefix);
		byteBuf.put(bodyData);
		
		byte[] response = postRawData(byteBuf.array());
		
		if (response != null) {
			
			byteBuf.clear();
			byteBuf.put(response);
			byteBuf.flip();
			
			
			respHeader.unPack(byteBuf);
			if (respHeader.getHeaderMagicNum() != header.getHeaderMagicNum()) {
				throw new Exception("magic num error");
			}
			
			byteBuf.get(packager, byteBuf.position(), packager.length);
			body = new Body(new String(packager, CHARSET_UTF8));
			bodyLen = respHeader.getHeaderBodyLen();
			
			tmpBytes = new byte[bodyLen];
			byteBuf.get(tmpBytes, byteBuf.position(), bodyLen);
			
			map = (Map<String, Object>) body.getPackager().unpack(tmpBytes);
			
			return map;
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
