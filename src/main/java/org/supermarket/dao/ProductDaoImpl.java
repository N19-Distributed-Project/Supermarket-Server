package org.supermarket.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.supermarket.dao.daoInterface.ProductDao;
import org.supermarket.entity.Product;
import org.supermarket.entity.enumP.Category;
import org.supermarket.util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductDaoImpl extends UnicastRemoteObject implements ProductDao {
	private final EntityManager entityManager;
	private final EntityTransaction entityTransaction;
	
	public ProductDaoImpl () throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getManagerFactory().createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	@Override
	public boolean addProduct (Product product) throws RemoteException {
		
		try{
			entityTransaction.begin();
			entityManager.persist(product);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public boolean addProduct (List<Product> product)  throws RemoteException{
		
		try{
			entityTransaction.begin();
			product.forEach(entityManager::persist);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public Product getProductById (Long id)  throws RemoteException{
		try{
			entityTransaction.begin();
			Product product = entityManager.find(Product.class, id);
			entityTransaction.commit();
			return product;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Product> getAllProducts ()  throws RemoteException{
		try{
			entityTransaction.begin();
			List<Product> products = entityManager.createQuery("select p FROM Product p", Product.class).getResultList();
			entityTransaction.commit();
			return products;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
		
	}
	
	@Override
	public List<Product> getProductByNameLike (String name) throws RemoteException {
		String s = "'%" + name + "%'";
		try{
			entityTransaction.begin();
			List<Product> products = entityManager.createNativeQuery("select * from products where products.productName like :name", Product.class)
					.setParameter("name", s).getResultList();
			entityTransaction.commit();
			return products;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Product> getProductsByCategory (Category category) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Product> products = entityManager.createQuery("select p from Product p WHERE p.category = :category", Product.class)
					.setParameter("category", category).getResultList();
			entityTransaction.commit();
			return products;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
		
	}
	
	@Override
	public List<Product> getProductsEndDate ()  throws RemoteException{
		LocalDate localDate = LocalDate.now();
		try{
			entityTransaction.begin();
			List<Product> products = entityManager.createQuery("select p from Product p WHERE p.date < :date", Product.class)
					.setParameter("date", localDate).getResultList();
			entityTransaction.commit();
			return products;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Product> getProductsByReceiptId (Long id) throws RemoteException {
		try{
			entityTransaction.begin();
			List<Product> products = entityManager.createNativeQuery("select p.productId,p.VAT,p.category, p.date,p.description, p.exp," +
							"p.price,p.productName, p.productStatus,p.quantity from products p " +
							"inner JOIN receipts_products rp on p.productId = rp.productList_productId " +
							"inner JOIN receipts on rp.Receipt_receiptId = receipts.receiptId\n" +
							"where receiptId = :id", Product.class)
					.setParameter("id", id).getResultList();
			entityTransaction.commit();
			return products;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public List<Product> getProductsByReceiptDate (LocalDate date)  throws RemoteException{
		try{
			entityTransaction.begin();
			List<Product> products = entityManager.createNativeQuery(
							"select * from products inner JOIN receipts_products rp on products.productId = rp.productList_productId " +
									"inner JOIN receipts on rp.Receipt_receiptId = receipts.receiptId where receipts.date = :date", Product.class)
					.setParameter("date", date).getResultList();
			entityTransaction.commit();
			return products;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return null;
		}
	}
	
	@Override
	public boolean updateProductById (Long oldId, Product newProductUpdate)  throws RemoteException{
		try{
			entityTransaction.begin();
			Product oldProductUpdate = entityManager.find(Product.class, oldId);
			oldProductUpdate.setProductName(newProductUpdate.getProductName());
			oldProductUpdate.setDate(newProductUpdate.getDate());
			oldProductUpdate.setDescription(newProductUpdate.getDescription());
			oldProductUpdate.setQuantity(newProductUpdate.getQuantity());
			oldProductUpdate.setCategory(newProductUpdate.getCategory());
			oldProductUpdate.setExp(newProductUpdate.getExp());
			oldProductUpdate.setPrice(newProductUpdate.getPrice());
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
		
	}
	
	@Override
	public boolean deleteProductById (Long id)  throws RemoteException{
		
		try{
			entityTransaction.begin();
			Product product = entityManager.find(Product.class, id);
			entityManager.remove(product);
			entityTransaction.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			entityTransaction.rollback();
			return false;
		}
	}
	
	@Override
	public int deleteProductByName (String productName)  throws RemoteException{
		List<Product> productList = getProductByNameLike(productName);
		AtomicInteger count = new AtomicInteger();
		productList.forEach(product -> {
			try{
				deleteProductById(product.getProductId());
			}catch(RemoteException e){
				throw new RuntimeException(e);
			}
			count.getAndIncrement();
		});
		return count.get();
	}
	
	@Override
	public int deleteProductEndDate ()  throws RemoteException{
		LocalDate localDate = LocalDate.now();
		List<Product> productList = entityManager.createQuery("select p from Product p WHERE p.date < :date", Product.class)
				.setParameter("date", localDate)
				.getResultList();
		AtomicInteger count = new AtomicInteger();
		productList.forEach(product -> {
			try{
				deleteProductById(product.getProductId());
			}catch(RemoteException e){
				throw new RuntimeException(e);
			}
			count.getAndIncrement();
		});
		return count.get();
		
	}
	
	@Override
	public int count ()  throws RemoteException{
		return Integer.parseInt(entityManager.createNativeQuery("select count(*) from products").getSingleResult().toString());
		
	}
}
