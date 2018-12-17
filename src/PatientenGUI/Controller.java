package src.PatientenGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.json.JSONException;
import src.Datenhaltung.Krankenhaus;
import src.Datenhaltung.KrankenhausMvenrepository;
import src.Fachlogik.*;

import java.io.IOException;
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
    private KrankenhausMvenrepository krServ;

    public Controller(TableView tv){
        krServ = new KrankenhausMvenrepository();
        this.tv = tv;
        tvMed = new TableView();
        obsList = FXCollections.observableArrayList();
        obsListMed = FXCollections.observableArrayList();
        tv.setItems(obsList);
        tvMed.setItems(obsListMed);
        this.kr = new Krankenhaus();
    }

    public List<Patient> suchPatientmitIDServer(String Id) throws JSONException, RuntimeException {
        List<Patient> erg = new ArrayList<>();
        erg.add(krServ.getPatientbyId(Id));
        return  erg;
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



    public boolean setMedtabelviewServer(TableView<MedicationStatement> tv, Patient p) {
        tvMed = tv;
        tvMed.getItems().clear();
        List<MedicationStatement>  med = krServ.getMedikamentStatementbyPatient(p.getIdServer());
        boolean b = false;
        //Patient p = me.getPatient();
        if(med!= null && med.size() > 0) {
            tvMed.getItems().addAll(med);
            return true;
        }
        return false;
    }




    public void conClose(){
        try {
            kr.connclose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<Patient> suchmitNameServer(String name) throws JSONException {
        return krServ.getPatientbyName(name);
    }





    // Methode der DB
    public MedicationStatement updateMedDaten(MedicationStatement med) throws SQLException {
        return  kr.updateMeDaten(med);
    }
    public Patient getLastInsertedDB() throws SQLException, NichtErlaubException {
        return kr.getLastInserted();
    }
    public boolean addPatientDB(Patient neu) throws SQLException {
        boolean b =  kr.addPatientDB(neu);
        System.out.println("Addpatient Controller zeilr 78");
        try {
            if(b == true) {
                System.out.println("Addpatien controller OK");
                obsList.add(kr.getLastInserted());
            }
        } catch (NichtErlaubException e) {
            e.printStackTrace();
        }
        return b;
    }

    public List<MedicationStatement> getMedSteServer(Patient p){
        return krServ.getMedikamentStatementbyPatient(p.getIdServer());
    }
    public boolean addMedicamentDB(Medikament m) throws SQLException {
        return kr.addMedikamentDB(m);
    }

    public void addMedikamenteDB(Patient p, Medikament med, String taken, String status, String period, String note, String dosage) throws SQLException {
        kr.addMedikamentStatement(p, med,taken,status, period, note, dosage);
        //obsListMed.add(p.getMedicament());
        //setMedTableview(tv , p);
    }

    public Medikament getLastMedInserted() throws SQLException, NichtErlaubException {
        return kr.getLastInsertedMed();
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
    public boolean setMedtabelviewBD(TableView<MedicationStatement> tv, Patient p) {
        boolean b = false;
        tvMed = tv;
        tvMed.getItems().clear();
        List<MedicationStatement> list = null;
        if (p != null)
            try {
                list = kr.getMediListPa(p);
                if(list != null && list.size() > 0){
                    tvMed.getItems().addAll(kr.getMediListPa(p));
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NichtErlaubException e) {
                e.printStackTrace();
            }
            return false;
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
    //Methode der DB Ende
}
