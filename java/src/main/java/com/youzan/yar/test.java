package com.youzan.yar;

public class test
{	
	
	public test() {
	}
	
	protected void t(test2 t2)
	{
		t2.a = 100;
	}
	


	public static void main(String[] args) {
		test t1 = new test();
		test2 t2 = new test2();
		
		t1.t(t2);
		
		System.out.println(t2.a);
	}
}

class test2
{
	int a = 10;
}

