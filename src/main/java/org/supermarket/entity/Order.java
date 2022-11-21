package org.supermarket.entity;

import jakarta.persistence.*;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.supermarket.dao.ProductDaoImpl;
import org.supermarket.entity.enumP.OrderStatus;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	@ManyToOne
	@JoinColumn(name = "saleEmployee_id", nullable = false)
	private Employee saleEmployee;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	@ElementCollection(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.EXCEPTION)
	private List<OrderDetail> shoppingList;
	private LocalDateTime date;
	@Enumerated(EnumType.STRING)
	@NonNull
	private OrderStatus orderStatus;
	private double total;
	
	public Order () throws Exception {
	}
	
	public Order (Employee saleEmployee, Customer customer, List<OrderDetail> shoppingList, OrderStatus orderStatus) throws Exception {
		ProductDaoImpl productDao = new ProductDaoImpl();
		this.saleEmployee = saleEmployee;
		this.customer = customer;
		this.shoppingList = shoppingList;
		this.date = LocalDateTime.now();
		this.orderStatus = orderStatus;
		if(orderStatus == OrderStatus.PAID){
			shoppingList.forEach(orderDetail -> {
				Product product = null;
				try{
					product = productDao.getProductById(orderDetail.getProduct().getProductId());
				}catch(RemoteException e){
					throw new RuntimeException(e);
				}
				if(orderDetail.getProduct().getQuantity() < orderDetail.getQuantity()){
					product.setQuantity(0);
					try{
						productDao.updateProductById(product.getProductId(), product);
					}catch(RemoteException e){
						throw new RuntimeException(e);
					}
				}else{
					product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
					try{
						productDao.updateProductById(product.getProductId(), product);
					}catch(RemoteException e){
						throw new RuntimeException(e);
					}
				}
			});
		}
		AtomicReference<Double> sum = new AtomicReference<>((double) 0);
		shoppingList.forEach(item -> {
			double price = item.getProduct().getPrice();
			double quantity = item.getQuantity();
			double VAT = item.getProduct().getVAT();
			sum.updateAndGet(v -> (v + (price * quantity + (VAT * price))));
		});
		this.total = sum.get();
	}
	
	public Long getOrderId () {
		return orderId;
	}
	
	public Employee getSaleEmployee () {
		return saleEmployee;
	}
	
	public Customer getCustomer () {
		return customer;
	}
	
	public List<OrderDetail> getShoppingList () {
		return shoppingList;
	}
	
	public LocalDateTime getDate () {
		return date;
	}
	
	public OrderStatus getOrderStatus () {
		return orderStatus;
	}
	
	public double getTotal () {
		return total;
	}
	
	public void setSaleEmployee (Employee saleEmployee) {
		this.saleEmployee = saleEmployee;
	}
	
	public void setCustomer (Customer customer) {
		this.customer = customer;
	}
	
	public void setShoppingList (List<OrderDetail> shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	public void setDate (LocalDateTime date) {
		this.date = date;
	}
	
	public void setOrderStatus (OrderStatus orderStatus) throws RemoteException {
		this.orderStatus = orderStatus;
		ProductDaoImpl productDao = new ProductDaoImpl();
		if(orderStatus == OrderStatus.PAID){
			this.shoppingList.forEach(orderDetail -> {
				Product product = null;
				try{
					product = productDao.getProductById(orderDetail.getProduct().getProductId());
				}catch(RemoteException e){
					throw new RuntimeException(e);
				}
				if(orderDetail.getProduct().getQuantity() < orderDetail.getQuantity()){
					product.setQuantity(0);
					try{
						productDao.updateProductById(product.getProductId(), product);
					}catch(RemoteException e){
						throw new RuntimeException(e);
					}
				}else{
					product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
					try{
						productDao.updateProductById(product.getProductId(), product);
					}catch(RemoteException e){
						throw new RuntimeException(e);
					}
				}
			});
		}
		AtomicReference<Double> sum = new AtomicReference<>((double) 0);
		this.shoppingList.forEach(item -> {
			double price = item.getProduct().getPrice();
			double quantity = item.getQuantity();
			double VAT = item.getProduct().getVAT();
			sum.updateAndGet(v -> (v + (price * quantity + (VAT * price))));
		});
		this.total = sum.get();
	}
	
	public void setTotal (double total) {
		this.total = total;
	}
	
	@Override
	public String toString () {
		return "Order{" +
				"orderId=" + orderId +
				", saleEmployee=" + saleEmployee +
				", customer=" + customer +
				", shoppingList=" + shoppingList +
				", date=" + date +
				", orderStatus=" + orderStatus +
				", total=" + total +
				'}';
	}
}

