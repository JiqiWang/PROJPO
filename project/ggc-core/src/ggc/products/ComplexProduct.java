package ggc.products;

public class ComplexProduct extends Product {

    // for now, treated as a string. recipe class to be implemented later
    private String _recipe;
    
    private double _aggravation;

    public ComplexProduct(String id, String supplier, double price, 
    double stock, double aggravation, String recipe) {
        super(id, supplier, price, stock);
        this._aggravation = aggravation;
        this._recipe = recipe;
    }    
    
    public String getRecipe(){
        return this._recipe;
    }

    public double getAggravation(){
        return this._aggravation;
    }

    @Override
    public String buildAttributesString(){
        return String.format("%s|%s|%s|%s|%s", getID(), (int) getPrice(), (int) getStock(), getAggravation(), getRecipe());
    }
}
