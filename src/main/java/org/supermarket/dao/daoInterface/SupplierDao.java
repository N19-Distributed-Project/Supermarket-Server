package org.supermarket.dao.daoInterface;

import org.supermarket.entity.Supplier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SupplierDao extends Remote {
	
	boolean addSuppier (Supplier supplier)throws RemoteException;
	
	List<Supplier> getAllSuppier ()throws RemoteException;
	
	Supplier getSupplierById (long id)throws RemoteException;
	
	List<Supplier> getSupplierByNameLike (String name)throws RemoteException;
	
	boolean updateSupplierById (long id, Supplier newSupplier)throws RemoteException;
	
	boolean deleteSupplierById (long id)throws RemoteException;
	
	int count ()throws RemoteException;
}
