package ggc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// import javax.swing.plaf.basic.BasicScrollPaneUI.ViewportChangeHandler;

import ggc.exceptions.BadEntryException;
import ggc.exceptions.DuplicatePartnerKeyExceptionCore;
import ggc.exceptions.ImportFileException;
import ggc.exceptions.InvalidDateExceptionCore;
import ggc.exceptions.MissingFileAssociationException;
import ggc.exceptions.UnavailableFileException;
import ggc.exceptions.UnknownPartnerKeyExceptionCore;
import ggc.products.Batch;
import ggc.products.Product;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

/** Façade for access. */

public class WarehouseManager {

  	/** Name of file storing current store. */
  	private String _filename = "";

  	/** The warehouse itself. */
  	private Warehouse _warehouse = new Warehouse();

  	//FIXME define other attributes
  	//FIXME define constructor(s)
  	//FIXME define other methods

  	/**
   	* @@throws IOException
   	* @@throws FileNotFoundException
   	* @@throws MissingFileAssociationException
   	*/
  	public void save() throws IOException, FileNotFoundException, 
  	MissingFileAssociationException {
    	//FIXME implement serialization method
  	}

  	/**
   	* @@param filename
   	* @@throws MissingFileAssociationException
   	* @@throws IOException
   	* @@throws FileNotFoundException
   	*/
  	public void saveAs(String filename) 
  	throws MissingFileAssociationException, FileNotFoundException, IOException {
    	_filename = filename;
    	save();
  	}

  	/**
  	 * @@param filename
  	 * @@throws UnavailableFileException
   	*/
  	public void load(String filename) throws UnavailableFileException {
    	//FIXME implement serialization method
  	}

  /**
   * @param textfile
   * @throws ImportFileException
   */
  	public void importFile(String textfile) throws ImportFileException {
    	try {
	    	_warehouse.importFile(textfile);
    	} catch (IOException | BadEntryException /* maybe other exceptions */ e) {
	    	throw new ImportFileException(textfile);
 		}
  	}

  	public Warehouse getCurrentWarehouse(){
    	return _warehouse;
  	}

  	public int displayDate(){
    	return getCurrentWarehouse().getDate();
  	}

  	public void advanceDate(int amount) throws InvalidDateExceptionCore{
    	getCurrentWarehouse().advanceDate(amount);
  	}

  	public void registerPartner(String id, String name, String address) 
  	throws DuplicatePartnerKeyExceptionCore {
    	getCurrentWarehouse().registerPartner(id, name, address);
  	}

  	public ArrayList<String> allPartners(){
    	return getCurrentWarehouse().getAllPartners();
  	}

  	public String findPartner(String id) 
  	throws UnknownPartnerKeyExceptionCore {
    	return getCurrentWarehouse().getPartner(id);
  	}

	public List<Batch> getAvailableBatches(){
		return getCurrentWarehouse().getAvailableBatches();
	}

	public List<Product> getAllProducts(){
		return getCurrentWarehouse().getAllProducts();
	}

}
