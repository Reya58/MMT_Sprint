package com.fakestore.models;

import java.util.List;


public class Cart {


    private Integer id;
    private Integer userId;
    private String date;
    private List<CartProduct> products;

    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public List<CartProduct> getProducts() {
		return products;
	}

	public void setProducts(List<CartProduct> products) {
		this.products = products;
	}

	public static class CartProduct {
        private Integer productId;
        private Integer quantity;

        public Integer getProductId() { return productId; }
        public Integer getQuantity()  { return quantity; }
        public void setProductId(Integer productId) { this.productId = productId; }
        public void setQuantity(Integer quantity)   { this.quantity  = quantity; }
    }
}