package ggc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import ggc.exceptions.BadEntryException;
import ggc.exceptions.DuplicatePartnerKeyExceptionCore;
import ggc.exceptions.InvalidDateExceptionCore;
import ggc.exceptions.UnknownPartnerKeyExceptionCore;
import ggc.partners.Partner;
import ggc.products.Batch;
import ggc.products.ComplexProduct;
import ggc.products.Product;
import ggc.utils.BatchComparator;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	private int _date = 0; // if the warehouse is loaded from a file, the date starts on its date

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	// A TreeMap is used so the IDs are inserted by alphabetical order.
	private TreeMap<String, Partner> _partners = new TreeMap<>();

	private TreeMap<String, ArrayList<Batch>> _batchesByProduct = new TreeMap<>();
	private TreeMap<String, ArrayList<Batch>> _batchesByPartner = new TreeMap<>();

	private TreeMap<String, Product> _products = new TreeMap<>();
  
	private List<Batch> _batches = new ArrayList<>();
	
  	// FIXME define attributes
  	// FIXME define contructor(s)
  	// FIXME define methods

	/**
   	* @param txtfile filename to be loaded.
   	* @throws IOException
   	* @throws BadEntryException
   	*/
  	void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    	//FIXME implement method
  	}

  	public int getDate(){
    	return _date;
  	}

  	public void setDate(int date) throws InvalidDateExceptionCore {
    	if(date < 0){
    		throw new InvalidDateExceptionCore(); // should we use another constructor?
    	} else {
      		_date = date; 
    	}
 	}

  	public void advanceDate(int amount) throws InvalidDateExceptionCore { 
    	// should probably add another exception, InvalidAmountException for example
    	if(amount < 0){
      		throw new InvalidDateExceptionCore();
    	} else {
      		setDate(_date + amount);
    	}
  	}

  	public void registerPartner(String id, String name, String address) 
  	throws DuplicatePartnerKeyExceptionCore {

    	if(_partners.keySet().contains(id)){
      		throw new DuplicatePartnerKeyExceptionCore();
    	}
    
    	Partner p = new Partner(id, name, address);
    	_partners.put(id, p);
 	}

	public String getPartner(String id) 
  	throws UnknownPartnerKeyExceptionCore {
    	if(!_partners.keySet().contains(id)){
      		throw new UnknownPartnerKeyExceptionCore();
		}

    	return _partners.get(id).buildAttributesString();
  	}

  	public ArrayList<String> getAllPartners(){
    	// maybe sort the IDs by ACTUAL alphabetic order (accents included) ? FIXME i guess
    	ArrayList<String> result = new ArrayList<>();

    	_partners.values().stream()
    	.forEach(o->{result.add(o.buildAttributesString());});

    	return result;
	}

	private void addBatchByProduct(Batch batch){
		if(_batchesByProduct.keySet().contains(batch.getProductID())){
			this._batchesByProduct.get(batch.getProductID()).add(batch);
		} else {
			this._batchesByProduct.put(batch.getProductID(), new ArrayList<Batch>());
			addBatchByProduct(batch);
		}
	}

	private void addBatchByPartner(Batch batch){
		if(_batchesByPartner.keySet().contains(batch.getPartnerID())){
			this._batchesByPartner.get(batch.getPartnerID()).add(batch);
		} else {
			this._batchesByPartner.put(batch.getPartnerID(), new ArrayList<Batch>());
			addBatchByPartner(batch);
		}
	}

	private void addProductFromBatch(String productID, String partnerID, 
	double price, double stock) {
		if(!_products.keySet().contains(productID)){
			Product newP = new Product(productID, partnerID, price, stock);
			this._products.put(productID, newP);
		} else {
			_products.get(productID).addStock(stock);
			double currPrice = _products.get(productID).getPrice();
			if(currPrice < price) 
				_products.get(productID).setPrice(price);
		}
	}

	private void addProductFromBatch(String productID, String partnerID, 
	double price, double stock, double aggravation, String recipe) {
		if(!_products.keySet().contains(productID)){
			Product newP = new ComplexProduct(productID, partnerID, price, stock, aggravation, recipe);
			this._products.put(productID, newP);
		} else {
			_products.get(productID).addStock(stock);
			double currPrice = _products.get(productID).getPrice();
			if(currPrice < price) 
				_products.get(productID).setPrice(price);
		}
	}

	public void registerBatch(String productID, String partnerID, 
	double price, double stock){
		Batch newBatch = new Batch(productID, partnerID, price, stock);

		addProductFromBatch(productID, partnerID, price, stock);

		_batches.add(newBatch);
		addBatchByPartner(newBatch);
		addBatchByProduct(newBatch);
		Collections.sort(_batches, new BatchComparator());
	}

	public void registerBatch(String productID, String partnerID, 
	double price, double stock, double aggravation, String recipe){
		Batch newBatch = new Batch(productID, partnerID, price, stock);

		addProductFromBatch(productID, partnerID, price, stock, 
		aggravation, recipe);

		_batches.add(newBatch);
		addBatchByPartner(newBatch);
		addBatchByProduct(newBatch);
		Collections.sort(_batches, new BatchComparator());
	}

	public List<Batch> getAvailableBatches(){
		return _batches;
	}

	public List<Product> getAllProducts(){
		return new ArrayList<>(_products.values());
	}

}
