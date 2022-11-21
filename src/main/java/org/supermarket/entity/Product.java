package org.supermarket.entity;

import jakarta.persistence.*;
import org.supermarket.entity.enumP.Category;
import org.supermarket.entity.enumP.ProductStatus;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)
	private double price;
	@Enumerated(EnumType.STRING)
	private Category category;
	private String description;
	private double VAT; // tùy sản phẩmhttps://www.youtube.com/results?search_query=vim+intellij+tutorial
	@Column(nullable = false)
	private LocalDate exp; // bắt buộc
	
	private LocalDate date; // if Housewere == null
	@Column(nullable = false)
	private int quantity;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus productStatus;
	
	public Product () throws Exception {
	}
	
	public Product (String productName, double price, Category category, String description
			, LocalDate exp, LocalDate date, int quantity) throws Exception {
		this.productName = productName;
		if(price > 0){
			this.price = price;
		}else throw new Exception("price not -1");
		this.category = category;
		this.description = description;
		if(category == Category.FOOD){
			this.VAT = 0.03;
		}else if(category == Category.DRINK){
			this.VAT = 0.05;
		}else if(category == Category.HOUSEWARES){
			this.VAT = 0.1;
		}
		this.exp = exp == null ? LocalDate.now() : exp;
		this.date = category == Category.HOUSEWARES ? null : date;
		this.quantity = Math.max(quantity, 0);
		this.productStatus = this.getQuantity() <= 0 ? ProductStatus.OUT_OF_STOCK : ProductStatus.STOCKING;
		
	}
	
	public Long getProductId () {
		return productId;
	}
	
	public String getProductName () {
		return productName;
	}
	
	public double getPrice () {
		return price;
	}
	
	public Category getCategory () {
		return category;
	}
	
	public String getDescription () {
		return description;
	}
	
	public double getVAT () {
		return VAT;
	}
	
	public LocalDate getExp () {
		return exp;
	}
	
	public LocalDate getDate () {
		return date;
	}
	
	public int getQuantity () {
		return quantity;
	}
	
	public ProductStatus getProductStatus () {
		return productStatus;
	}
	
	public void setProductName (String productName) {
		this.productName = productName;
	}
	
	public void setPrice (double price) throws Exception {
		if(price > 0){
			this.price = price;
		}else throw new Exception("PRICE NOT -1");
	}
	
	public void setCategory (Category category) {
		this.category = category;
		if(category == Category.FOOD){
			this.VAT = 0.03;
		}else if(category == Category.DRINK){
			this.VAT = 0.05;
		}else if(category == Category.HOUSEWARES){
			this.VAT = 0.1;
		}
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public void setExp (LocalDate exp) {
		
		this.exp = exp == null ? LocalDate.now() : exp;
		
	}
	
	public void setDate (LocalDate date) {
		this.date = this.getCategory() == Category.HOUSEWARES ? null : date;
	}
	
	public void setQuantity (int quantity) {
		this.quantity = quantity;
		this.productStatus = this.getQuantity() <= 0 ? ProductStatus.OUT_OF_STOCK : ProductStatus.STOCKING;
	}
	
	@Override
	public String toString () {
		return "Product{" +
				"productId=" + productId +
				", productName='" + productName + '\'' +
				", price=" + price +
				", category=" + category +
				", description='" + description + '\'' +
				", VAT=" + VAT +
				", exp=" + exp +
				", date=" + date +
				", quantity=" + quantity +
				", productStatus=" + productStatus +
				'}';
	}
}
