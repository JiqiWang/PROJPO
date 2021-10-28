package ggc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import ggc.utils.BatchComparatorPrice;
import ggc.utils.ProductComparator;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	private int _date = 0;

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	// A TreeMap is used so the IDs are inserted by alphabetical order.
	private TreeMap<String, Partner> _partners = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	private TreeMap<String, ArrayList<Batch>> _batchesByProduct = new TreeMap<>();
	private TreeMap<String, ArrayList<Batch>> _batchesByPartner = new TreeMap<>();

	private TreeMap<String, Product> _products = new TreeMap<>();
  
	private List<Batch> _batches = new ArrayList<>();

	/**
	 * Imports data from a file.
	 * 
   	 * @param txtfile filename to be loaded.
   	 * @throws IOException
   	 * @throws BadEntryException
   	*/
  	void importFile(String txtfile) throws IOException, BadEntryException {
    	try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {
			String s;
			while ((s = in.readLine()) != null) {
			  String line = new String(s.getBytes(), "UTF-8");
	  
			  String[] fields = line.split("\\|");
			  switch (fields[0]) {
			  case "PARTNER":
				try {
					registerPartner(fields[1], fields[2], fields[3]);
				} catch (DuplicatePartnerKeyExceptionCore e) {
					e.printStackTrace();
				}
				break;
			  case "BATCH_S": 
			  	registerBatch(fields[1], fields[2], 
			  	Double.parseDouble(fields[3]), Double.parseDouble(fields[4]));
				break;
			  case "BATCH_M": 
			  	registerBatch(fields[1], fields[2], 
			  	Double.parseDouble(fields[3]), Double.parseDouble(fields[4]), 
			  	Double.parseDouble(fields[5]), fields[6]);
			  	break;
			  default: 
			  	throw new BadEntryException(fields[0]);
			  }
			}
		  } catch (FileNotFoundException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  } catch (BadEntryException e) {
			e.printStackTrace();
		  }
  	}
	
	/**
	 * @return the warehouse's current date.
	 */
  	public int getDate(){
    	return _date;
  	}

	/**
	 * Changes the value of the warehouse's date.
	 * 
	 * @param date the warehouse's current date
	 * @throws InvalidDateExceptionCore
	 */
  	public void setDate(int date) throws InvalidDateExceptionCore {
    	if(date < 0){
    		throw new InvalidDateExceptionCore(); 
    	} else {
      		_date = date; 
    	}
 	}

	/**
	 * Advances the date by the given amount.
	 * 
	 * @param amount the amount to advance the date by
	 * @throws InvalidDateExceptionCore if the amount to advance is zero or negative 
	 */
  	public void advanceDate(int amount) throws InvalidDateExceptionCore { 

    	if(amount <= 0){
      		throw new InvalidDateExceptionCore();
    	} else {
      		setDate(_date + amount);
    	}
  	}
	

	/**
	 * Creates a partner and stores it.
	 * 
	 * @param id the partner's id
	 * @param name the partner's name
	 * @param address the partner's address
	 * @throws DuplicatePartnerKeyExceptionCore if a partner with the 
	 *    given id already exists
	 */  
  	public void registerPartner(String id, String name, String address) 
  	throws DuplicatePartnerKeyExceptionCore {

    	if(_partners.keySet().contains(id)){
      		throw new DuplicatePartnerKeyExceptionCore();
    	}
    
    	Partner p = new Partner(id, name, address);
    	_partners.put(id, p);
 	}

	/**
	 * Searches the partner storage for a specified partner.
	 * 
	 * @param id the partner's id 
	 * @return a string with the attributes of the partner
	 * @throws UnknownPartnerKeyExceptionCore when there isn't a partner
	 *    with the given id
	 */
	public String getPartner(String id) 
  	throws UnknownPartnerKeyExceptionCore {
    	if(!_partners.keySet().contains(id)){
      		throw new UnknownPartnerKeyExceptionCore();
		}
    	return _partners.get(id).buildAttributesString();
  	}

	/**
	 * @return a list with every partner's attributes string
	 */
  	public ArrayList<String> getAllPartners(){
		  
    	ArrayList<String> result = new ArrayList<>();

    	_partners.values().stream()
    	.forEach(o->{result.add(o.buildAttributesString());});

    	return result;
	}

	/**
	 * Auxiliary function. Used to add a batch to a list of batches
	 *    of the same product. 
	 * 
	 * @param batch the batch to be added
	 */
	private void addBatchByProduct(Batch batch){
		if(_batchesByProduct.keySet().contains(batch.getProductID())){
			this._batchesByProduct.get(batch.getProductID()).add(batch);
		} else {
			this._batchesByProduct.put(batch.getProductID(), new ArrayList<Batch>());
			addBatchByProduct(batch);
		}
	}

	/**
	 * Auxiliary function. Used to add a batch to a list of batches
	 *    owned by the same partner.
	 * 
	 * @param batch the batch to be added
	 */
	private void addBatchByPartner(Batch batch){
		if(_batchesByPartner.keySet().contains(batch.getPartnerID())){
			this._batchesByPartner.get(batch.getPartnerID()).add(batch);
		} else {
			this._batchesByPartner.put(batch.getPartnerID(), new ArrayList<Batch>());
			addBatchByPartner(batch);
		}
	}

	/**
	 * Auxiliary function. Used to update the product storage when 
	 *    a new simple batch is registered.
	 * 
	 * @param productID the product's id
	 * @param partnerID the partner's id
	 * @param price the price of the product
	 * @param stock the stock of the product
	 */
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

	/**
	 * Auxiliary function. Used to update the product storage when 
	 *    a new complex batch is registered.
	 * 
	 * @param productID the product's id
	 * @param partnerID the partner's id
	 * @param price the price of the product
	 * @param stock the stock of the product
	 */
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

	/**
	 * Creates a new simple batch and stores it. Also updates the storage
	 *    of products.
	 * 
	 * @param productID the product of the batch
	 * @param partnerID the id of the supplier of the batch
	 * @param price the price of the batch
	 * @param stock the amount of products in the batch
	 */
	public void registerBatch(String productID, String partnerID, 
	double price, double stock){
		Batch newBatch = new Batch(productID, partnerID, price, stock);

		addProductFromBatch(productID, partnerID, price, stock);

		_batches.add(newBatch);
		addBatchByPartner(newBatch);
		addBatchByProduct(newBatch);
		Collections.sort(_batches, new BatchComparator());
		Collections.sort(_batches, new BatchComparatorPrice());
	}

	/**
	 * Creates a new complex batch and stores it. Also updates the storage
	 *    of products.
	 * 
	 * @param productID the product of the batch
	 * @param partnerID the id of the supplier of the batch
	 * @param price the price of the batch
	 * @param stock the amount of products in the batch
	 */
	public void registerBatch(String productID, String partnerID, 
	double price, double stock, double aggravation, String recipe){
		Batch newBatch = new Batch(productID, partnerID, price, stock);

		addProductFromBatch(productID, partnerID, price, stock, 
		aggravation, recipe);

		_batches.add(newBatch);
		addBatchByPartner(newBatch);
		addBatchByProduct(newBatch);
		Collections.sort(_batches, new BatchComparator());
		Collections.sort(_batches, new BatchComparatorPrice());
	}

	/**
	 * @return the list of batches
	 */
	public List<Batch> getAvailableBatches(){
		return _batches;
	}

	/**
	 * @return a sorted list of every existing product
	 */
	public List<Product> getAllProducts(){
		ArrayList<Product> _result = new ArrayList<>(_products.values());
		Collections.sort(_result, new ProductComparator());
		return _result;
	}

}
