package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.InvalidDateException;
import ggc.exceptions.InvalidDateExceptionCore;

//FIXME import classes

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    // FIXME NEEDS SOME REWORK, THIS IS ONLY AN IDEA
    addIntegerField("amount", Prompt.daysToAdvance());
    //FIXME add command fields
  }

  @Override
  public final void execute() throws CommandException {
    // FIXME NEEDS SOME REWORK, THIS IS ONLY AN IDEA
    try{
      _receiver.getCurrentWarehouse().advanceDate(integerField("amount"));
    } catch(InvalidDateExceptionCore e){
      throw new InvalidDateException(integerField("amount"));
    }
  }

}
