package org.supermarket.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.supermarket.dao.daoInterface.EmployeeDao;
import org.supermarket.entity.Employee;
import org.supermarket.entity.enumP.Role;
import org.supermarket.util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class EmployeeDaoImpl extends UnicastRemoteObject implements EmployeeDao {
	private final EntityManager entityManager;
	private final EntityTransaction entityTransaction;
	
	public EmployeeDaoImpl () throws RemoteException {
		super();
		entityManager = HibernateUtil.
				getInstance().getManagerFactory().createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	@Override
	public boolean addEmloyee (Employee employee) throws RemoteException {
		
		try{
			entityTransaction.begin();
			entityManager.persist(employee);
			entityTransaction.commit();
			return true;
		}catch(Exception exception){
			exception.printStackTrace();
			
			return false;
		}
	}
	
	@Override
	public Employee getEmployeeById (long id) throws RemoteException {
		try{
			entityTransaction.begin();
			Employee employee = entityManager.find(Employee.class, id);
			entityTransaction.commit();
			return employee;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public List<Employee> getAllEmployees () throws RemoteException {
		try{
			entityTransaction.begin();
			List<Employee> employee = entityManager.createNativeQuery("select * from employees",Employee.class).getResultList();
			entityTransaction.commit();
			return employee;
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Employee> getEmployeeByNameLike (String name) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Employee> employee = entityManager.createQuery("select Employee from Employee where name like :name", Employee.class)
					.setParameter("name", name)
					.getResultList();
			entityTransaction.commit();
			return employee;
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public Employee getEmployeeByPhone (String phone) throws RemoteException {
		try{
			entityTransaction.begin();
			Employee employee = entityManager.createQuery("select Employee from Employee where phoneNumber like :phone", Employee.class)
					.setParameter("phone", phone).getSingleResult();
			entityTransaction.commit();
			return employee;
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public boolean updateEmployeeInforById (long id, Employee update) throws RemoteException {
		try{
			entityTransaction.begin();
			Employee employee = entityManager.find(Employee.class, id);
			employee.setName(update.getName());
			employee.setAge(update.getAge());
			employee.setDob(update.getDob());
			employee.setGender(update.getGender());
			employee.setPhoneNumber(update.getPhoneNumber());
			employee.setAddress(update.getAddress());
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean updateEmployeePasswordById (long id, String oldPassword, String newPassword) throws RemoteException {
		try{
			entityTransaction.begin();
			Employee employee = entityManager.find(Employee.class, id);
			if(Objects.equals(employee.getPassword(), oldPassword)){
				employee.setPassword(newPassword);
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
	public boolean updateEmployeeRoleById (long id, Role newRole) throws RemoteException {
		try{
			entityTransaction.begin();
			Employee employee = entityManager.find(Employee.class, id);
			employee.setRole(newRole);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean deleteEmployeeById (long id) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.remove(getEmployeeById(id));
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
		
		
	}
	
	@Override
	public boolean deleteEmployeeByName (String name) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("DELETE employees FROM employees where employees.name LIKE :name")
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
	public boolean deleteEmployeeByPhone (String phone) throws RemoteException {
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("DELETE employees FROM employees where employees.name LIKE :phone")
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
	public Employee login (String username, String password) throws Exception {
		
		try{
			entityTransaction.begin();
			entityManager.createNativeQuery("select * from  employees" +
							" where username like :user and password like :pass", Employee.class)
					.setParameter("user", username)
					.setParameter("pass", password).getSingleResult();
			entityTransaction.commit();
			return (Employee) entityManager.createNativeQuery("select * from  employees" +
							" where username like :user and password like :pass", Employee.class)
					.setParameter("user", username)
					.setParameter("pass", password).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public int count ()throws RemoteException {
		return Integer.parseInt(entityManager.createNativeQuery("SELECT COUNT(*) FROM employees")
				.getSingleResult().toString());
	}
}

