package org.supermarket.dao.daoInterface;

import org.supermarket.entity.Receipt;
import org.supermarket.entity.Supplier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface ReceiptDao extends Remote {
	boolean addReceipt (Receipt receipt)throws RemoteException;
	
	List<Receipt> getAllReceipt ()throws RemoteException;
	
	Receipt getReceiptById (Long id)throws RemoteException;
	
	List<Receipt> getReceiptsByDate (LocalDate date)throws RemoteException;
	
	List<Receipt> getReceiptsBySupplier (Supplier supplier)throws RemoteException;
	
	int count ()throws RemoteException;
}
