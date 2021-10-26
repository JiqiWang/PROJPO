package ggc.products;

import java.io.Serializable;

public class Product implements Serializable {

    private String _id;
    private String _supplier;

    private double _price;
    private double _stock;

    public Product(String id, String supplier, double price, double stock){
        this._id = id;
        this._supplier = supplier;
        this._price = price;
        this._stock = stock;
    }

    public String getID(){
        return this._id;
    }

    public String getSupplier(){
        return this._supplier;
    }

    public double getPrice(){
        return this._price;
    }

    public double getStock(){
        return this._stock;
    }

    



}