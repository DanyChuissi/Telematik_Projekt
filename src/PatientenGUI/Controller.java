package src.PatientenGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import src.Fachlogik.Patientverwaltung;

public class Controller {

    private Patientverwaltung pv;
    private TableView lv;
    private ObservableList obsList;

    public Controller(TableView lv){
        this.lv = lv;
        pv = new Patientverwaltung();
        obsList = FXCollections.observableArrayList();
        lv.setItems(obsList);
    }
}
