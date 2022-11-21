package org.supermarket.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class OrderDetail {
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private int quantity;
	
	public OrderDetail (Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public OrderDetail () {
	}
	
	public Product getProduct () {
		return product;
	}
	
	public void setProduct (Product product) {
		this.product = product;
	}
	
	public int getQuantity () {
		return quantity;
	}
	
	public void setQuantity (int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString () {
		return "OrderDetail{" +
				"product=" + product +
				", quantity=" + quantity +
				'}';
	}
}
