package com.youzan.yar.protocal.body.packager;


public abstract class Factory {
	public abstract <T extends Packager> T getInstance(Class<T> c);
}
