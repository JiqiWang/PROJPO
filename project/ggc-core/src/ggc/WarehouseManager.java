package ggc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

  	/**
   	* @@throws IOException
   	* @@throws FileNotFoundException
   	* @@throws MissingFileAssociationException
   	*/
  	public void save() throws IOException, FileNotFoundException, 
  	MissingFileAssociationException {
    	try{
			ObjectOutputStream oo = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
			oo.writeObject(this._warehouse);
			oo.close();
		} catch (IOException e){
			e.printStackTrace();
		} 
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
    	
		try{
			ObjectInputStream oi = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			this.setWarehouse((Warehouse) oi.readObject());
			oi.close();
			this._filename = filename;
		} catch (IOException e){
			// do something
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}

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

	public void setWarehouse(Warehouse warehouse){
    	this._warehouse = warehouse;
  	}

	public boolean hasNoFile(){
		return _filename.equals("");
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
