package src.PatientenGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.json.JSONException;
import src.Datenhaltung.Krankenhaus;
import src.Datenhaltung.KrankenhausMvenrepository;
import src.Datenhaltung.KrankenhausPostRequest;
import src.Fachlogik.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Controller verbindet die Faklogic und die GUI
 */
public class Controller {

    private TableView tv;
    private TableView tvMed;
    private ObservableList obsList;
    private ObservableList obsListMed;
    private Medikament[] medList;
    private Krankenhaus kr;
    private KrankenhausMvenrepository krServ;
    private KrankenhausPostRequest krPost;

    public Controller(TableView tv){
        krServ = new KrankenhausMvenrepository();
        this.tv = tv;
        tvMed = new TableView();
        obsList = FXCollections.observableArrayList();
        obsListMed = FXCollections.observableArrayList();
        tv.setItems(obsList);
        tvMed.setItems(obsListMed);
        this.kr = new Krankenhaus();
        krPost = new KrankenhausPostRequest();
    }

    /**
     * Patient wird über den Controller mit dem ID im Server gesucht
     * @param Id
     * @return
     * @throws JSONException
     * @throws RuntimeException
     */

    public List<Patient> suchPatientmitIDServer(String Id) throws JSONException, RuntimeException {
        List<Patient> erg = new ArrayList<>();
        erg.add(krServ.getPatientbyId(Id));
        return  erg;
    }

    public void patientPatch(Patient orig , Patient alt) throws Exception {
         krPost.patchPatient(orig, alt);
    }

    public String patientPost(Patient patient) throws Exception {
       return  krPost.postPatient(patient);
    }

    public String medikamentSPost(MedicationStatement medS, Patient patient) throws Exception {
        return krPost.postMedStatement(medS, patient);
    }

    /**
     * Die Methode gibt eien Liste zurück mit alle Medikamente die im Server sind
     * @return List Medikament
     */

    public List<Medikament> getMedikamentList(){
        try {
            return kr.getMedikamentList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Über dem Controlller wird ein Patient im DB Gelöscht
     * @param p
     * @throws SQLException
     */

    public void loeschePatientFromDB(Patient p) throws SQLException {
        kr.patientLoeschen(p);
    }

    /**
     * Die Methose lädt alle patient, die im DB sind..
     * @return
     */
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


    /**
     * Die Methode aktualisiert die TableView und zeigt nur die Medikament der Patient, der als Parameter übergeben wird
     * gibt falls zrück falls der patient kein Medikation hast
     * @param tv
     * @param p
     * @return boolean
     */
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


    /**
     * Patient wird über den Controller im Server mit Name gesucht
     * @param name
     * @return
     * @throws JSONException
     */
    public List<Patient> suchmitNameServer(String name) throws JSONException {
        return krServ.getPatientbyName(name);
    }





    // Methode der DB

    /**
     * Die Daten der MedikamenteStateemnt, der im Parameter übergeben wird werden im DB aktualisiert
     * @param med
     * @return
     * @throws SQLException
     */
    public MedicationStatement updateMedDaten(MedicationStatement med) throws SQLException {
        return  kr.updateMeDaten(med);
    }

    /**
     * Die Methode gibt der letzte eingefügte Patient zurück
     * @return
     * @throws SQLException
     * @throws NichtErlaubException
     */
    public Patient getLastInsertedDB() throws SQLException, NichtErlaubException {
        return kr.getLastInserted();
    }

    /**
     * die Methode fügt ein Patient im DB
     * gibt false zurüch wenn der Patient nicht eingefügt werden könnte
     * z.B wenn der Patient schon existiert
     * @param neu
     * @return
     * @throws SQLException
     */
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

    /**
     * Die Methode sucht alle Medikamente Stement einene patiente zurück
     * @param p
     * @return
     */
    public List<MedicationStatement> getMedSteServer(Patient p){
        return krServ.getMedikamentStatementbyPatient(p.getIdServer());
    }

    /**
     * Die Methode fügt Medikamente im DB
     * @param m
     * @return
     * @throws SQLException
     */
    public boolean addMedicamentDB(Medikament m) throws SQLException {
        return kr.addMedikamentDB(m);
    }

    /**
     * Die Methode fügt ein Medikamente Statement im DB Mit alle Parameter, die übergeben werden
     * @param p
     * @param med
     * @param taken
     * @param status
     * @param period
     * @param note
     * @param dosage
     * @throws SQLException
     */

    public void addMedikamenteDB(Patient p, Medikament med, String taken, String status, String period, String note, String dosage, String idServer) throws SQLException {
        kr.addMedikamentStatement(p, med,taken,status, period, note, dosage, idServer);
        //obsListMed.add(p.getMedicament());
        //setMedTableview(tv , p);
    }

    /**
     * die Methose gibt der Letzte Medikamente, der im Db eingefugt ist zurück
     * @return
     * @throws SQLException
     * @throws NichtErlaubException
     */

    public Medikament getLastMedInserted() throws SQLException, NichtErlaubException {
        return kr.getLastInsertedMed();
    }

 /*   public void setMedTableview( TableView tv, Patient p){
        // tvMed = tv;
        //tvMed.getItems().clear();
        //tv.setItems(obsListMed);
        obsListMed.add(p.getMedicament());

        // tv.getItems().addAll(p.getMedicament());
    }*/

    public void updatePatient(Patient patient) throws SQLException {
        System.out.println("control : ok");
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
