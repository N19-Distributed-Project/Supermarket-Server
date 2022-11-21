package org.supermarket.entity;

import jakarta.persistence.*;
import org.supermarket.entity.enumP.Gender;
import org.supermarket.entity.enumP.Role;

import java.time.LocalDate;

@Entity
@Table(name = "employees", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class Employee extends People { //nhân viên
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long employeeId;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Employee () {
	}
	
	public Employee (String name, Gender gender, LocalDate dob, String address, String phoneNumber, String username, String password, Role role) {
		super(name, gender, dob, address, phoneNumber);
		
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public Long getEmployeeId () {
		return employeeId;
	}
	
	public void setEmployeeId (Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getUsername () {
		return username;
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getPassword () {
		return password;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	public Role getRole () {
		return role;
	}
	
	public void setRole (Role role) {
		this.role = role;
	}
	
	@Override
	public String toString () {
		return "Employee{" +
				"employeeId=" + employeeId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				", name='" + name + '\'' +
				", gender=" + gender +
				", dob=" + dob +
				", age=" + age +
				", address='" + address + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
}
