package com.srija.lab_a1_a2_android_srija_c0813274.models;

import java.io.Serializable;


public class Product implements Serializable {

    private int productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private int providerId;

    public Product() {
    }

    public Product(String productName, String productDescription, int productPrice, int providerId) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.providerId = providerId;
    }

    public Product(int productId, String productName, String productDescription, int productPrice, int providerId) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.providerId = providerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}