package org.supermarket;

import org.supermarket.dao.*;
import org.supermarket.entity.enumP.Option;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static void main (String[] args) {
		try{
			int port = 9000;
			LocateRegistry.createRegistry(port);
			
			Server.startOn(port, Option.CUSTOMER_DAO);
			Server.startOn(port,Option.EMPLOYEE_DAO);
			Server.startOn(port,Option.SUPPLIER_DAO);
			Server.startOn(port,Option.ORDER_DAO);
			Server.startOn(port,Option.RECEIPT_DAO);
			Server.startOn(port,Option.PRODUCT_DAO);
			
			System.out.println("Reddy..!");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void startOn(int port,Option option) throws RemoteException, MalformedURLException, AlreadyBoundException {
		Object obj ;
		String res = "rmi://localhost:"+port+"/"+option.getValue();
		switch(option){
			case ORDER_DAO -> obj = new OrderDaoImpl();
			case PRODUCT_DAO -> obj = new ProductDaoImpl();
			case RECEIPT_DAO -> obj = new ReceiptDaoImpl();
			case CUSTOMER_DAO -> obj = new CustomerDaoImpl();
			case EMPLOYEE_DAO -> obj = new EmployeeDaoImpl();
			case SUPPLIER_DAO -> obj = new SupplierDaoImpl();
			default -> throw new IllegalStateException("Unexpected value: " + option);
		}
		
		Naming.bind(res, (Remote) obj);
	}
}
