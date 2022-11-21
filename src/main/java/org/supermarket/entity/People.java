package org.supermarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import org.supermarket.entity.enumP.Gender;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class People implements Serializable {
	
	@Column(nullable = false)
	protected String name;
	@Enumerated(EnumType.STRING)
	protected Gender gender;
	@Column(name = "date_of_birth")
	protected LocalDate dob;
	protected int age;
	protected String address;
	protected String phoneNumber;
	
	public People () {
	}
	
	protected People (String name, Gender gender, LocalDate dob, String address, String phoneNumber) {
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.age = LocalDate.now().getYear() - this.dob.getYear();
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public Gender getGender () {
		return gender;
	}
	
	public void setGender (Gender gender) {
		this.gender = gender;
	}
	
	public LocalDate getDob () {
		return dob;
	}
	
	public void setDob (LocalDate dob) {
		this.dob = dob;
	}
	
	public int getAge () {
		return age;
	}
	
	public void setAge (int age) {
		this.age = age;
	}
	
	public String getAddress () {
		return address;
	}
	
	public void setAddress (String address) {
		this.address = address;
	}
	
	public String getPhoneNumber () {
		return phoneNumber;
	}
	
	public void setPhoneNumber (String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString () {
		return "People{" +
				"name='" + name + '\'' +
				", gender=" + gender +
				", dob=" + dob +
				", age=" + age +
				", address='" + address + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
}
