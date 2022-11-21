package org.supermarket.entity.enumP;

public enum Option {
	
	CUSTOMER_DAO("customerdao"),EMPLOYEE_DAO("employeedao"), ORDER_DAO("orderdao"),
	PRODUCT_DAO("productdao"), RECEIPT_DAO("receiptdao"), SUPPLIER_DAO("supplierdao");
	
	private String value;
	
	Option (String value) {
		this.value = value;
	}
	
	public String getValue () {
		return value;
	}
}
