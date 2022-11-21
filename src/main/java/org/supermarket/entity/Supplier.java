package org.supermarket.entity;

import jakarta.persistence.*;

import java.io.Serializable;

//Nhà Cung Cấp

@Entity
@Table(name = "suppliers")
public class Supplier implements Serializable { //Nhà Cung Cấp
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long supplierId;
	@Column(nullable = false, unique = true)
	private String name;
	private String address;
	private String phone;
	private String email;
	
	public Supplier () {
	}
	
	public Supplier (String name, String address, String phone, String email) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	public Long getSupplierId () {
		return supplierId;
	}
	
	public String getName () {
		return name;
	}
	
	public String getAddress () {
		return address;
	}
	
	public String getPhone () {
		return phone;
	}
	
	public String getEmail () {
		return email;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setAddress (String address) {
		this.address = address;
	}
	
	public void setPhone (String phone) {
		this.phone = phone;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}
	
	@Override
	public String toString () {
		return "Supplier{" +
				"supplierId=" + supplierId +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
