package src.PatientenGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import src.Datenhaltung.Krankenhaus;
import src.Fachlogik.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private TableView tv;
    private TableView tvMed;
    private ObservableList obsList;
    private ObservableList obsListMed;
    private Medikament[] medList;
    private Krankenhaus kr;

    public Controller(TableView tv){
        this.tv = tv;
        tvMed = new TableView();
        obsList = FXCollections.observableArrayList();
        obsListMed = FXCollections.observableArrayList();
        tv.setItems(obsList);
        tvMed.setItems(obsListMed);
        this.kr = new Krankenhaus();
    }

    public List<Medikament> getMedikamentList(){
        try {
            return kr.getMedikamentList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loeschePatientFromDB(Patient p) throws SQLException {
        kr.patientLoeschen(p);
    }

    public List<Patient> ladePatientDB() {
        this.kr = new Krankenhaus();
        kr.connect();
        List<Patient> list = new ArrayList<>();
        try {
           list = kr.getpatientlist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public TableView getLv() {
        return tv;
    }

    public void setTvmed(TableView tv){
        this.tvMed = tv;
        tvMed.setItems(obsListMed);
        tv.getItems().addAll();
    }

    public void addPatientDB(Patient neu) throws SQLException {
        kr.addPatientDB(neu);

        try {
            obsList.add(kr.getLastInserted());
        } catch (NichtErlaubException e) {
            e.printStackTrace();
        }
    }

    public void addMedikamenteDB(Patient p, Medikament med, String taken, String status, String period, String note, String dosage) throws SQLException {
        kr.addMedikamentStatement(p, med,taken,status, period, note, dosage);
        //obsListMed.add(p.getMedicament());
        //setMedTableview(tv , p);

    }
    public void setMedTableview( TableView tv, Patient p){
       // tvMed = tv;
        //tvMed.getItems().clear();
       //tv.setItems(obsListMed);
        obsListMed.add(p.getMedicament());

       // tv.getItems().addAll(p.getMedicament());

    }

    public void updatePatient(Patient patient) throws SQLException {
        kr.updatepaDaten(patient);
    }

    public MedicationStatement updateMedDaten(MedicationStatement med) throws SQLException {
       return  kr.updateMeDaten(med);
    }

    public void setPatTabelview(List<Patient> list){
        tv.getItems().clear();
        tv.getItems().addAll(list);
    }

    public void setMedtabelviewBD(TableView tv, MedicationStatement me) {
        tvMed.getItems().clear();
       //Patient p = me.getPatient();
        try {
            tvMed.getItems().addAll(kr.getMediList(me.getPatientId()));
         } catch (SQLException e) {
         e.printStackTrace();
        } catch (NichtErlaubException e) {
          e.printStackTrace();
          }
    }
    public void setMedtabelviewBD(TableView tv, Patient p) {
        tvMed = tv;
        tvMed.getItems().clear();
        if (p != null)
        try {

        tvMed.getItems().addAll(kr.getMediListPa(p));
          } catch (SQLException e) {
         e.printStackTrace();
        } catch (NichtErlaubException e) {
          e.printStackTrace();
          }
    }

    public void setPatTableview(){
        tv.getItems().clear();
        tvMed.getItems().clear();

        try {
            tv.getItems().addAll(kr.getpatientlist());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient getPatient(int i){
        try {
            return  kr.getPatient(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NichtErlaubException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void conClose(){
        try {
            kr.connclose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> suchMitId(int i) throws SQLException, NichtErlaubException {
        return kr.suchtPaI(i);
    }

    public List<Patient> suchMitName(String name) throws SQLException, NichtErlaubException {
        return kr.suchtpaNa(name);
    }

    public List<MedicationStatement> getMedStat(Patient p){
        List<MedicationStatement> list = null;
        try {
           list = kr.getMediListPa(p);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NichtErlaubException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void loescheMedStatement (MedicationStatement medS) throws SQLException {
        kr.medStatementtLoeschen(medS);
    }
}
