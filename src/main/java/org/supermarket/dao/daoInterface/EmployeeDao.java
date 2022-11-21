package org.supermarket.dao.daoInterface;

import org.supermarket.entity.Employee;
import org.supermarket.entity.enumP.Role;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EmployeeDao extends Remote {
	boolean addEmloyee (Employee employee) throws RemoteException;
	
	Employee getEmployeeById (long id) throws RemoteException;
	
	List<Employee> getAllEmployees () throws RemoteException;
	
	List<Employee> getEmployeeByNameLike (String name) throws RemoteException;
	
	Employee getEmployeeByPhone (String phone) throws RemoteException;
	
	boolean updateEmployeeInforById (long id, Employee update) throws RemoteException;
	
	boolean updateEmployeePasswordById (long id, String oldPassword, String newPassword) throws RemoteException;
	
	boolean updateEmployeeRoleById (long id, Role newRole) throws RemoteException;
	
	boolean deleteEmployeeById (long id) throws RemoteException;
	
	boolean deleteEmployeeByName (String name) throws RemoteException;
	
	boolean deleteEmployeeByPhone (String phone) throws RemoteException;
	
	Employee login(String username,String password) throws Exception;
	
	int count ()throws RemoteException;
}
