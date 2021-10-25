package ggc.partners;

public class Partner {
    
    private String _id;
    private String _name;
    private String _address;

    // add more atributes here

    private double _points; 

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

}
