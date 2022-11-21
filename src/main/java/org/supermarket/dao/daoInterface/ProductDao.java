package org.supermarket.dao.daoInterface;

import org.supermarket.entity.Product;
import org.supermarket.entity.enumP.Category;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface ProductDao extends Remote {
	boolean addProduct (Product product) throws RemoteException;
	
	boolean addProduct (List<Product> product) throws RemoteException;
	
	Product getProductById (Long id) throws RemoteException;
	
	List<Product> getAllProducts () throws RemoteException;
	
	List<Product> getProductByNameLike (String name) throws RemoteException;
	
	List<Product> getProductsByCategory (Category category) throws RemoteException;
	
	List<Product> getProductsEndDate () throws RemoteException;
	
	List<Product> getProductsByReceiptId (Long id) throws RemoteException;
	
	List<Product> getProductsByReceiptDate (LocalDate date) throws RemoteException;
	
	boolean updateProductById (Long oldId, Product newProductUpdate) throws RemoteException;
	
	
	boolean deleteProductById (Long id) throws RemoteException;
	
	int deleteProductByName (String productName) throws RemoteException;
	
	int deleteProductEndDate () throws RemoteException;
	
	int count () throws RemoteException;
	
}
