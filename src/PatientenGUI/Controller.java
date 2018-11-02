package src.PatientenGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Medikament;
import src.Fachlogik.Patient;
import src.Fachlogik.Patientverwaltung;

public class Controller {

    private Patientverwaltung pv;
    private TableView tv;
    private TableView tvMed;
    private ObservableList obsList;
    private ObservableList obsListMed;
    private Medikament[] m;

    public Controller(TableView tv){
        this.tv = tv;
        tvMed = new TableView();
        pv = new Patientverwaltung();
        obsList = FXCollections.observableArrayList();
        obsListMed = FXCollections.observableArrayList();
        tv.setItems(obsList);
        tvMed.setItems(obsListMed);
        this.m = this.MedikamentList();

    }

    public TableView getLv() {
        return tv;
    }

    public void setTvmed(TableView tv){
        this.tvMed = tv;
        tvMed.setItems(obsListMed);
        tv.getItems().addAll();

    }

    public Medikament[] MedikamentList(){
        Medikament m1 = new Medikament();
        m1.setStatus("active");
        m1.setForm("Tablet");
        m1.setManufacturer("Bayer");
        m1.setOverCounter(false);
        m1.setCode("Code1");

        Medikament m2 = new Medikament();
        m2.setStatus("inactive");
        m2.setForm("Capsule");
        m2.setManufacturer("Siemens");
        m2.setOverCounter(false);
        m2.setCode("Code2");
        Medikament[] m = {m1,m2};
        return m;
    }

    public void testDatenladen(){


        Patient p1 = new Patient();
        p1.setName("Müller");
        p1.setVorname("Max");

        Patient p2 = new Patient();
        p2.setVorname("Mario");
        p2.setName("Baloteli");

    }
    public void addPatient(Patient neu){
        //tv.getItems().clear();
        pv.addPatient(neu);
        obsList.add(neu);
    }

    public void addMedikamente(Patient p, Medikament med, String taken, String status){
        p.addmedicament(med,taken,status);
        obsListMed.add(p.getMedicament());

    }
    public void setTableview( TableView tv, Patient p){
        tvMed = tv;
        tvMed.getItems().clear();;
        tvMed.getItems().addAll(p.getMedicament());
    }
    public void setGrid(Patient p){

    }
    public Patient getPatient(int i){
        return pv.getPatient(i);
    }
}
