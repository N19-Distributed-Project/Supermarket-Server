package org.supermarket.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.supermarket.dao.daoInterface.SupplierDao;
import org.supermarket.entity.Supplier;
import org.supermarket.util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SupplierDaoImpl extends UnicastRemoteObject implements SupplierDao {
	private final EntityManager entityManager;
	private final EntityTransaction entityTransaction;
	
	public SupplierDaoImpl () throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getManagerFactory().createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	@Override
	public boolean addSuppier (Supplier supplier) throws RemoteException {
		
		try{
			entityTransaction.begin();
			entityManager.persist(supplier);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
		
	}
	
	@Override
	public List<Supplier> getAllSuppier () throws RemoteException {
		try{
			entityTransaction.begin();
			List<Supplier> supplierList = entityManager.createNativeQuery("select * from suppliers", Supplier.class)
					.getResultList();
			entityTransaction.commit();
			return supplierList;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public Supplier getSupplierById (long id) throws RemoteException {
		
		try{
			entityTransaction.begin();
			Supplier supplier = entityManager.find(Supplier.class, id);
			entityTransaction.commit();
			return supplier;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
		
	}
	
	@Override
	public List<Supplier> getSupplierByNameLike (String name) throws RemoteException {
		String s = "%" + name + "%";
		try{
			entityTransaction.begin();
			List<Supplier> supplier = entityManager.createNativeQuery("select * from suppliers " +
									"where suppliers.name like :name"
							, Supplier.class)
					.setParameter("name", s).getResultList();
			entityTransaction.commit();
			return supplier;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public boolean updateSupplierById (long id, Supplier newSupplier) throws RemoteException {
		try{
			entityTransaction.begin();
			Supplier supplier = entityManager.find(Supplier.class, id);
			supplier.setName(newSupplier.getName());
			supplier.setEmail(newSupplier.getEmail());
			supplier.setPhone(newSupplier.getPhone());
			supplier.setAddress(newSupplier.getAddress());
			entityTransaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}
	
	@Override
	public boolean deleteSupplierById (long id) throws RemoteException {
		try{
			entityTransaction.begin();
			Supplier supplier = entityManager.find(Supplier.class, id);
			entityManager.remove(supplier);
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
		return Integer.parseInt(entityManager.createNativeQuery("select count(*) from suppliers").getSingleResult().toString());
	}
}
