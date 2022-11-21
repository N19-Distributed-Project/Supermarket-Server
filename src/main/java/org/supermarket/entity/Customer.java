package org.supermarket.entity;

import jakarta.persistence.*;
import org.supermarket.entity.enumP.Gender;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class Customer extends People { // Khách hàng
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerId;
	
	public Customer () {
	}
	
	public Customer (String name, Gender gender, LocalDate dob, String address, String phoneNumber) {
		super(name, gender, dob, address, phoneNumber);
	}
	
	public Long getCustomerId () {
		return customerId;
	}
	
	public void setCustomerId (Long customerId) {
		this.customerId = customerId;
	}
}
