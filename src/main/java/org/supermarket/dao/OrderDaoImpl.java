package org.supermarket.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.supermarket.dao.daoInterface.OrderDao;
import org.supermarket.entity.Order;
import org.supermarket.entity.enumP.OrderStatus;
import org.supermarket.util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

public class OrderDaoImpl extends UnicastRemoteObject implements OrderDao {
	private final EntityManager entityManager;
	private final EntityTransaction entityTransaction;
	
	public OrderDaoImpl () throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getManagerFactory().createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	@Override
	public boolean addOrder (Order order) throws RemoteException {
		try{
			entityTransaction.begin();
			if(order.getCustomer().getCustomerId() == null){
				CustomerDaoImpl customerDao = new CustomerDaoImpl();
				customerDao.addCustomer(order.getCustomer());
			}
			entityManager.persist(order);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public Order getOrderById (long id) throws RemoteException {
		try{
			entityTransaction.begin();
			Order order = entityManager.find(Order.class, id);
			entityTransaction.commit();
			return order;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
		
	}
	
	@Override
	public List<Order> getOrdersByEmployerName (String employeeName) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Order> orderList = entityManager.createNativeQuery(
							"select orderId, date, orderStatus, total, customer_id, saleEmployee_id, employeeId" +
									", address, age, date_of_birth, gender, name, phoneNumber, password, role, username " +
									"from  orders inner JOIN employees e on orders.saleEmployee_id = e.employeeId " +
									"where e.name like :name", Order.class)
					.setParameter("name", employeeName)
					.getResultList();
			entityTransaction.commit();
			return orderList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Order> getOrdersByCustomerName (String customerName) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Order> orderList = entityManager.createNativeQuery(
							"select orderId, date, orderStatus, total, customer_id, saleEmployee_id, employeeId" +
									", address, age, date_of_birth, gender, name, phoneNumber, password, role, username " +
									"from  orders inner JOIN employees e on orders.saleEmployee_id = e.employeeId " +
									"where e.name like :name", Order.class)
					.setParameter("name", customerName)
					.getResultList();
			entityTransaction.commit();
			return orderList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Order> getOrdersByEmployeeId (String employeeId) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Order> orderList = entityManager.createNativeQuery(
							"select orderId, date, orderStatus, total, customer_id, saleEmployee_id, employeeId" +
									", address, age, date_of_birth, gender, name, phoneNumber, password, role, username " +
									"from  orders inner JOIN employees e on orders.saleEmployee_id = e.employeeId " +
									"where e.name like :id", Order.class)
					.setParameter("id", employeeId)
					.getResultList();
			entityTransaction.commit();
			return orderList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Order> getOrdersByCustomerId (String customerId) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Order> orderList = entityManager.createNativeQuery(
							"select orderId, date, orderStatus, total, customer_id, saleEmployee_id, employeeId" +
									", address, age, date_of_birth, gender, name, phoneNumber, password, role, username " +
									"from  orders inner JOIN employees e on orders.saleEmployee_id = e.employeeId " +
									"where e.name like :id", Order.class)
					.setParameter("id", customerId)
					.getResultList();
			entityTransaction.commit();
			return orderList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Order> getOrdersByDate (LocalDate date) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Order> orderList = entityManager.createQuery("select o from Order o WHERE o.date < :date", Order.class)
					.setParameter("date", date).getResultList();
			entityTransaction.commit();
			return orderList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Order> getOrdersByStatus (OrderStatus orderStatus) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Order> orderList = entityManager.createQuery("select o from Order o WHERE o.orderStatus < :orderStatus", Order.class)
					.setParameter("orderStatus", orderStatus).getResultList();
			entityTransaction.commit();
			return orderList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public boolean updateWaitOrderById (long id, Order order) throws RemoteException {
		try{
			entityTransaction.begin();
			Order oldOrder = entityManager.find(Order.class, id);
			if(oldOrder.getOrderStatus() == OrderStatus.WAIT){
				oldOrder.setCustomer(order.getCustomer());
				oldOrder.setOrderStatus(order.getOrderStatus());
				oldOrder.setDate(order.getDate());
				oldOrder.setSaleEmployee(order.getSaleEmployee());
				oldOrder.setShoppingList(order.getShoppingList());
				oldOrder.setTotal(order.getTotal());
				entityTransaction.commit();
				return true;
			}else return false;
			
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteOrderById (long id) throws RemoteException {
		try{
			entityTransaction.begin();
			Order order = entityManager.find(Order.class, id);
			entityManager.remove(order);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteOrdersCancle () throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("DELETE orders FROM orders where orders.orderStatus = :orderSatatus")
					.setParameter("orderSatatus", OrderStatus.CANCEL).executeUpdate();
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteOrderWait () throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("DELETE orders FROM orders where orders.orderStatus = :orderSatatus")
					.setParameter("orderSatatus", OrderStatus.WAIT).executeUpdate();
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public int count () throws RemoteException {
		return Integer.parseInt(entityManager
				.createNativeQuery("select count(*) from orders where orderStatus = :orderSatatus ")
				.setParameter("orderSatatus", OrderStatus.PAID)
				.getSingleResult().toString());
	}
}

