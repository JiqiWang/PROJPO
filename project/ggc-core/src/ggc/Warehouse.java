package ggc;

import java.io.IOException;
import java.io.Serializable;

import ggc.exceptions.BadEntryException;
import ggc.exceptions.InvalidDateExceptionCore;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  private int _date = 0; // if the warehouse is loaded from a file, the date starts on its date

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

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

}
