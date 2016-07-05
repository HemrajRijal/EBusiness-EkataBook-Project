package com.ebl;

public class Test {

	public static void main(String[] args) {
		try
		{
			PaymentGatewayStub stub=new PaymentGatewayStub();
			PaymentGatewayStub.CheckBalance params=new PaymentGatewayStub.CheckBalance();
			params.setActno("12345");
			params.setAmount(300);
			System.out.println("Before Sending Request");
			PaymentGatewayStub.CheckBalanceResponse res=stub.checkBalance(params);
			System.out.println("After Sending Request");
			String result=res.get_return();
			System.out.println("Result="+result);
			//stub.add(params);
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error Occured"+e);
			e.printStackTrace();
		}

	}

}
