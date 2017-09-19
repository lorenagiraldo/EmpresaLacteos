package com.example.lorena.empresalacteos;

/**
 * Created by JORGE_ALEJANDRO on 18/09/2017.
 */

public class Product {

    private int productId;
    private String productCode;
    private String productName;
    private int productPrice;
    private int productQuantity;

    public Product() {
    }


    public Product(int productId) {
        this.productId = productId;
        this.productCode = null;
        this.productName=null;
        this.productPrice = -1;
        this.productQuantity = -1;
    }

    public Product(int productId, String productCode, String productName, int productPrice, int productQuantity) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
