package org.supermarket.dao.daoInterface;

import org.supermarket.entity.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerDao extends Remote {
	Long addCustomer (Customer customer) throws RemoteException;
	
	List<Customer> getAllCustomers ()throws RemoteException;
	
	Customer getCustomerById (long id)throws RemoteException;
	
	List<Customer> getCustomerByNameLike (String name)throws RemoteException;
	
	Customer getCustomerByPhone (String phone)throws RemoteException;
	
	boolean updateCustomerInforById (long id, Customer update)throws RemoteException;
	
	boolean deleteCustomerById (long id)throws RemoteException;
	
	boolean deleteCustomerByName (String name)throws RemoteException;
	
	boolean deleteCustomerByPhone (String phone)throws RemoteException;
	
	int count ()throws RemoteException;
	
	
}
