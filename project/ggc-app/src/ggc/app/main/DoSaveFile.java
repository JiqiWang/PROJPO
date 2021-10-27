package ggc.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.FileNotFoundException;
import java.io.IOException;

import ggc.WarehouseManager;
//FIXME import classes
import ggc.exceptions.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  private String _filename;

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
    if(!_receiver.hasNoFile()){
      _filename = Form.requestString(Prompt.newSaveAs());
    }
  }

  @Override
  public final void execute() throws CommandException {

    if(_receiver.hasNoFile()){
      try {
        _receiver.save();
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (MissingFileAssociationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else {
      try {
        _receiver.saveAs(this._filename);
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (MissingFileAssociationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }


  }

}
