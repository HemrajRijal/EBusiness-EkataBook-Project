package com.mvc.util;

public class Test {
	
	public Test()
	{
		String id="JSESSIONID";
		if(id.equalsIgnoreCase("userid"))
		{
			System.out.println("OK");
		}
		else
		{
			System.out.println("NOK");
		}
		String arr[]=new String[100];
		arr[0]="abc";
		arr[1]="def";
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]!=null)
			System.out.println(arr[i]);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       Test obj=new Test();
	}

}
