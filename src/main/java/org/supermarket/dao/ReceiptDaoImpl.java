package org.supermarket.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.supermarket.dao.daoInterface.ReceiptDao;
import org.supermarket.entity.Receipt;
import org.supermarket.entity.Supplier;
import org.supermarket.util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

public class ReceiptDaoImpl extends UnicastRemoteObject implements ReceiptDao {
	private final EntityManager entityManager;
	private final EntityTransaction entityTransaction;
	
	public ReceiptDaoImpl () throws RemoteException {
		entityManager = HibernateUtil.getInstance().getManagerFactory().createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	@Override
	public boolean addReceipt (Receipt receipt) throws RemoteException {
		try{
			SupplierDaoImpl suppierDao = new SupplierDaoImpl();
			ProductDaoImpl productDao = new ProductDaoImpl();
			EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
			entityTransaction.begin();
			if(receipt.getSupplier().getSupplierId() == null){
				suppierDao.addSuppier(receipt.getSupplier());
			}
			receipt.getProductList().forEach(product -> {
				try{
					if(product.getProductId() == null){
						productDao.addProduct(product);
					}
				}catch(java.rmi.RemoteException e){
					throw new RuntimeException(e);
				}
			});
			entityManager.persist(receipt);
			productDao.addProduct(receipt.getProductList());
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public List<Receipt> getAllReceipt () throws RemoteException {
		try{
			
			entityTransaction.begin();
			List<Receipt> receiptList = entityManager.createNativeQuery("select * from receipts", Receipt.class)
					.getResultList();
			entityTransaction.commit();
			return receiptList;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Receipt getReceiptById (Long id) throws RemoteException {
		try{
			entityTransaction.begin();
			Receipt receipt = entityManager.find(Receipt.class, id);
			entityTransaction.commit();
			return receipt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Receipt> getReceiptsByDate (LocalDate date) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Receipt> receiptList = entityManager.createQuery("select p from Product p WHERE p.date = :date", Receipt.class).setParameter("date", date).getResultList();
			entityTransaction.commit();
			return receiptList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int count () throws RemoteException {
		return Integer.parseInt(entityManager.createNativeQuery("select count(*) from receipts").getSingleResult().toString());
	}
	
	@Override
	public List<Receipt> getReceiptsBySupplier (Supplier supplier) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Receipt> receiptList = entityManager.createNativeQuery(
							"select receiptId, date, total, employee_id, supplier_id, supplierId, address, email, name, phone from receipts " +
									"inner join suppliers on receipts.supplier_id = suppliers.supplierId " +
									"where supplierId = :id ", Receipt.class)
					.setParameter("id", supplier.getSupplierId())
					.getResultList();
			entityTransaction.commit();
			return receiptList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

}
