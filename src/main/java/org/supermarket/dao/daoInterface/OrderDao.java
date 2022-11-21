package org.supermarket.dao.daoInterface;

import org.supermarket.entity.Order;
import org.supermarket.entity.enumP.OrderStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends Remote {
	boolean addOrder (Order order) throws RemoteException;
	
	Order getOrderById (long id) throws RemoteException;
	
	List<Order> getOrdersByEmployerName (String employeeName) throws RemoteException;
	
	List<Order> getOrdersByCustomerName (String customerName) throws RemoteException;
	
	List<Order> getOrdersByEmployeeId (String employeeId) throws RemoteException;
	
	List<Order> getOrdersByCustomerId (String customerId) throws RemoteException;
	
	List<Order> getOrdersByDate (LocalDate date) throws RemoteException;
	
	List<Order> getOrdersByStatus (OrderStatus orderStatus) throws RemoteException;
	
	boolean updateWaitOrderById (long id, Order order) throws RemoteException;
	
	boolean deleteOrderById (long id) throws RemoteException;
	
	boolean deleteOrdersCancle () throws RemoteException;
	
	boolean deleteOrderWait () throws RemoteException;
	
	int count () throws RemoteException;
	
	
}
