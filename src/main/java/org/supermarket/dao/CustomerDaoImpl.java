package org.supermarket.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.supermarket.dao.daoInterface.CustomerDao;
import org.supermarket.entity.Customer;
import org.supermarket.entity.enumP.Gender;
import org.supermarket.util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

public class CustomerDaoImpl extends UnicastRemoteObject implements CustomerDao {
	private final EntityManager entityManager;
	private final EntityTransaction entityTransaction;
	
	public CustomerDaoImpl () throws RemoteException {
		entityManager = HibernateUtil.getInstance().getManagerFactory().createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	
	@Override
	public Long addCustomer (Customer customer) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.persist(customer);
			
			entityManager.flush();
			entityTransaction.commit();
			return customer.getCustomerId();
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Customer> getAllCustomers () throws RemoteException {
		try{
			entityTransaction.begin();
			List<Customer> customerList = entityManager.createNativeQuery("select * from customers ", Customer.class)
					.getResultList();
			entityTransaction.commit();
			return customerList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public Customer getCustomerById (long id) throws RemoteException{
		try{
			entityTransaction.begin();
			Customer customer = entityManager.find(Customer.class, id);
			entityTransaction.commit();
			return customer;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Customer> getCustomerByNameLike (String name) throws RemoteException{
		String s = "%" + name + "%";
		try{
			entityTransaction.begin();
			List<Customer> customerList = entityManager.createQuery("select Customer from Customer where name like :name", Customer.class)
					.setParameter("name", s)
					.getResultList();
			entityTransaction.commit();
			return customerList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public Customer getCustomerByPhone (String phone) throws RemoteException{
		try{
			entityTransaction.begin();
			Customer customer = entityManager.createQuery("select Customer from Customer where phoneNumber like :phone", Customer.class)
					.setParameter("phone", phone).getSingleResult();
			entityTransaction.commit();
			return customer;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
		
	}
	
	@Override
	public boolean updateCustomerInforById (long id, Customer update) throws RemoteException {
		try{
			entityTransaction.begin();
			Customer customer = entityManager.find(Customer.class, id);
			customer.setName(update.getName());
			customer.setAge(update.getAge());
			customer.setDob(update.getDob());
			customer.setGender(update.getGender());
			customer.setPhoneNumber(update.getPhoneNumber());
			customer.setAddress(update.getAddress());
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteCustomerById (long id) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.remove(getCustomerById(id));
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteCustomerByName (String name) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("DELETE customers FROM customers where customers.name LIKE :name")
					.setParameter("name", name).executeUpdate();
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteCustomerByPhone (String phone) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("DELETE customers FROM customers where customers.name LIKE :phone")
					.setParameter("phone", phone).executeUpdate();
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
		return Integer.parseInt(entityManager.createNativeQuery("SELECT COUNT(*) FROM customers")
				.getSingleResult().toString());
	}
	

}
