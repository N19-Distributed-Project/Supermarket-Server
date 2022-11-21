package org.supermarket.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt implements Serializable { // Hóa đơn nhận
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long receiptId;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Product> productList;
	
	private LocalDate date;
	
	private double total;
	
	public Receipt () {
	}
	
	public Receipt (Supplier supplier, Employee employee, List<Product> productList, LocalDate date) {
		this.supplier = supplier;
		this.employee = employee;
		this.productList = productList;
		this.date = date;
		productList.forEach(product -> {
			this.total = this.getTotal() + (product.getPrice() * product.getQuantity());
		});
	}
	
	public Long getReceiptId () {
		return receiptId;
	}
	
	public Supplier getSupplier () {
		return supplier;
	}
	
	public void setSupplier (Supplier supplier) {
		this.supplier = supplier;
	}
	
	public Employee getEmployee () {
		return employee;
	}
	
	public void setEmployee (Employee employee) {
		this.employee = employee;
	}
	
	public List<Product> getProductList () {
		return productList;
	}
	
	public void setProductList (List<Product> productList) {
		this.productList = productList;
	}
	
	public LocalDate getDate () {
		return date;
	}
	
	public void setDate (LocalDate date) {
		this.date = date;
	}
	
	public double getTotal () {
		return total;
	}
	
	@Override
	public String toString () {
		return "Receipt{" +
				"receiptId=" + receiptId +
				", supplier=" + supplier +
				", employee=" + employee +
				", productList=" + productList +
				", date=" + date +
				", total=" + total +
				'}';
	}
}
