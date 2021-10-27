package ggc.partners;

import java.io.Serializable;

public class Partner implements Serializable {
    
    private String _id;
    private String _name;
    private String _address;

    // state missing

    private double _points = 1000; 
    private double _purchasesValue = 0;
    private double _performedSalesValue = 0;
    private double _paidSalesValue = 0;

    public Partner(String id, String name, String address){
        this._id = id;
        this._name = name;
        this._address = address;
    }

    public String getID(){
        return this._id;
    }

    public String getName(){
        return this._name;
    }

    public String getAddress(){
        return this._address;
    }

    public double getPoints(){
        return this._points;
    }

    public double getPurchasesValue(){
        return this._purchasesValue;
    }

    public double getPerformedSalesValue(){
        return this._performedSalesValue;
    }

    public double getPaidSalesValue(){
        return this._paidSalesValue;
    }

    public String buildAttributesString(){
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s", getID(), 
        getName(), getAddress(), "NORMAL", (int) getPoints(), 
        (int) getPurchasesValue(), (int) getPerformedSalesValue(), 
        (int) getPaidSalesValue());
    }


}
