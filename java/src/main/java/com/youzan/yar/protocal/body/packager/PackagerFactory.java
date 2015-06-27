package com.youzan.yar.protocal.body.packager;


public class PackagerFactory extends Factory{

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends Packager> T getInstance(Class<T> c) {
		T obj = null;
		
		try {
			obj = (T)(Class.forName(c.getName())).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
