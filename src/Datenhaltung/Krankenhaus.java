package src.Datenhaltung;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Medikament;
import src.Fachlogik.Patient;
import src.PatientenGUI.NichtErlaubException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Krankenhaus {
    private Patient patient = null;
    private Medikament medicament;
    private MedicationStatement medicationStatement;
   // private DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
    Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/krankenhaus?serverTimezone=MET";

    public Krankenhaus() {

        patient = new Patient();
        medicament = new Medikament();
        medicationStatement = new MedicationStatement();
    }

    public void connect() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "dany");
        } catch (SQLException e) {
            System.out.println("*** SQLException:\n" + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("*** Exception\n" + e);
            e.printStackTrace();
        }
    }
    public void addPatientDB(Patient p) throws SQLException {
        conn.setAutoCommit(false);
        String insert = "INSERT INTO patient(name, vorname,gender,telefon,birthdate, deseased,street,housenumber, location, postalcode,aufnahmeDatum ,entlassungStatus, idServer) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            String str="2015-03-31";
            Date date=Date.valueOf(str);
            ps = conn.prepareStatement(insert);

            ps.setString(1, p.getName());
            ps.setString(2, p.getVorname());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getTelefon());
            ps.setDate(5, Date.valueOf(p.getGeburtsdatumS()));
            ps.setBoolean(6, p.getDeseased());
            ps.setString(7, p.getStreet());
            ps.setInt(8, p.getHousenumber());
            ps.setString(9, p.getLocation());
            ps.setInt(10, p.getPostalcode());
            ps.setDate(11,p.stringToSqlDate(p.getAufnahmeDatumS()));
            ps.setBoolean(12, p.getEntlassungStatus());
            ps.setInt(13,p.getIdServer());

            ps.execute();
            conn.commit();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    /* Diese Methode fügt einen Medikament in der Datenbank*/
    public void addMedikamentDB(Medikament m) throws SQLException {
        conn.setAutoCommit(false);
        String insert = "INSERT INTO medicament(name, code,isOvercounter,form,manufacturer, status,idServer) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);

            ps.setString(1, m.getName());
            ps.setString(2, m.getCode());
            ps.setBoolean(3, m.isOverTheCounter());
            ps.setString(4, m.getForm());
            ps.setString(5, m.getManufacturer());
            ps.setString(6, m.getStatusMed());
            ps.setInt(7, m.getIdServer());

            ps.execute();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Patient getLastInserted() throws SQLException, NichtErlaubException {
        conn.setAutoCommit(false);
        patient = new Patient();
        String insert = "SELECT * FROM patient";
        Statement ps = null;

        try {
            ps = conn.createStatement();
            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            rs.afterLast();
            GETLASTINSERTED:
            while (rs.previous()) {
                patient = new Patient();
                patient.setIdentifier(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setVorname(rs.getString(3));
                patient.setGender(rs.getString(4));
                patient.setActive(rs.getBoolean(5));
                patient.setTelefon(rs.getString(6));
                patient.setGeburtsdatum(rs.getDate(7));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(rs.getDate(13));
                patient.setEntlassungsDatum(rs.getDate(14));
                patient.setEnlassungStatus(rs.getBoolean(15));
                patient.setIdServer(rs.getInt(16));
                break GETLASTINSERTED;//to read only the last row
            }
        }catch (SQLException e){

        }
        return patient;
    }

    public void addMedikamentStatement(Patient p, Medikament m, String taken, String status , String period, String note, String dosage) throws SQLException {
        conn.setAutoCommit(false);
        String select = "INSERT INTO medicamentstatement(PID,MeID,taken,note,status,periode,dosage)VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(select);

            ps.setInt(1, p.getIdentifier());
            ps.setInt(2, m.getMedID());
            ps.setString(3, taken);
            ps.setString(4,note );
            ps.setString(5, status);
            ps.setString(6,period );
            ps.setString(7,dosage );
            //ps.setString(8,idServer);

            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<Patient> getpatientlist() throws SQLException {

        conn.setAutoCommit(false);
        String insert = "SELECT * FROM patient";
        Statement ps = null;
        List<Patient> list = new ArrayList<>();
        try {


            ps = conn.createStatement();
            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            int i = 1;

            while (rs.next()) {
                patient = new Patient();
                patient.setIdentifier(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setVorname(rs.getString(3));
                patient.setGender(rs.getString(4));
                patient.setActive(rs.getBoolean(5));
                patient.setTelefon(rs.getString(6));
                patient.setGeburtsdatum(rs.getDate(7));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(rs.getDate(13));
                patient.setEntlassungsDatum(rs.getDate(14));
                patient.setEnlassungStatus(rs.getBoolean(15));
                patient.setIdServer(rs.getInt(16));

                list.add(patient);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Patient getPatient(int id) throws SQLException, NichtErlaubException {

        conn.setAutoCommit(false);
        String insert = "SELECT * FROM patient WHERE identifier =" + id;

        Statement ps = null;

        try {

            ps = conn.createStatement();

            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            if(rs.next()) {
                patient = new Patient();
                patient.setIdentifier(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setVorname(rs.getString(3));
                patient.setGender(rs.getString(4));
                patient.setActive(rs.getBoolean(5));
                patient.setTelefon(rs.getString(6));
                patient.setGeburtsdatum(rs.getDate(7));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(rs.getDate(13));
                patient.setEntlassungsDatum(rs.getDate(14));
                patient.setEnlassungStatus(rs.getBoolean(15));
                patient.setIdServer(rs.getInt(16));
            }


        } catch (SQLException e) {
            e.printStackTrace();
       }
        return patient;
    }


    /*public static java.util.Date convertFromSQLDateToJAVADate(
            java.sql.Date sqlDate) {
        java.util.Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        return javaDate;
    }*/

    public void connclose() throws SQLException {
        try{
            conn.close();
        }
        finally{
            try {
                conn.close();
            }catch (SQLException e){

            }
         }
    }

    public Medikament getMedicament(int id) throws SQLException {

        conn.setAutoCommit(false);
        String insert = "SELECT * FROM medicament WHERE MeID =" + id;

        Statement ps = null;

        try {


            ps = conn.createStatement();

            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            if(rs.next()) {
                medicament = new Medikament();
                medicament.setMedID(rs.getInt(1));
                medicament.setName(rs.getString(2));
                medicament.setCode(rs.getString(3));
                medicament.setOverCounter(rs.getBoolean(4));
                medicament.setForm(rs.getString(5));
                medicament.setManufacturer(rs.getString(6));
                medicament.setStatusMed(rs.getString(7));
                medicament.setIdServer(rs.getInt(8));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }

        public List<Patient> suchtPaI(int id) throws SQLException, NichtErlaubException {
        conn.setAutoCommit(false);
        String insert = "SELECT * FROM patient WHERE identifier =" + id;

        Statement ps = null;
        List<Patient> list = new ArrayList<>();
        try {
            ps = conn.createStatement();
            conn.commit();
            ResultSet rs = ps.executeQuery(insert);

            while (rs.next()) {
                patient = new Patient();
                patient.setIdentifier(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setVorname(rs.getString(3));
                patient.setGender(rs.getString(4));
                patient.setActive(rs.getBoolean(5));
                patient.setTelefon(rs.getString(6));
                patient.setGeburtsdatum(rs.getDate(7));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(rs.getDate(13));
                patient.setEntlassungsDatum(rs.getDate(14));
                patient.setEnlassungStatus(rs.getBoolean(15));
                patient.setIdServer(rs.getInt(16));
                list.add(patient);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Patient> suchtpaNa(String name) throws SQLException{
        conn.setAutoCommit(false);
        String insert = "SELECT * FROM patient WHERE name = \"" +name+" \" ";

        Statement ps = null;
        List<Patient> list = new ArrayList<>();
        try {


            ps = conn.createStatement();
            conn.commit();
            ResultSet rs = ps.executeQuery(insert);

            while (rs.next()) {
                patient = new Patient();
                patient.setIdentifier(rs.getInt(1));
                patient.setName(rs.getString(2));
                patient.setVorname(rs.getString(3));
                patient.setGender(rs.getString(4));
                patient.setActive(rs.getBoolean(5));
                patient.setTelefon(rs.getString(6));
                patient.setGeburtsdatum(rs.getDate(7));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(rs.getDate(13));
                patient.setEntlassungsDatum(rs.getDate(14));
                patient.setEnlassungStatus(rs.getBoolean(15));
                patient.setIdServer(rs.getInt(16));

                list.add(patient);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MedicationStatement> getMediList(int id) throws SQLException, NichtErlaubException {
        conn.setAutoCommit(false);

        String insert = "SELECT * FROM medicamentStatement WHERE PID =" + id;

        List<MedicationStatement> medi = new ArrayList<>();

        Statement ps = null;

        try {


            ps = conn.createStatement();

            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            while(rs.next()) {
                medicationStatement = new MedicationStatement();
                medicationStatement.setPatient(getPatient(rs.getInt(1)));
                medicationStatement.setMedikament(getMedicament(rs.getInt(2)));
                medicationStatement.setTaken((rs.getString(3)));
                medicationStatement.setNote(rs.getString(4));
                medicationStatement.setStatusStmt((rs.getString(5)));
                medicationStatement.setPeriode(rs.getString(6));
                medicationStatement.setDosage(rs.getString(7));
                medicationStatement.setForm((rs.getString(7)));
                medicationStatement.setName(rs.getString(8));
                medicationStatement.setManufacturer(rs.getString(9));
                medicationStatement.setPrescription(rs.getBoolean(10));
                medicationStatement.setStatusStmt((rs.getString(11)));
                medicationStatement.setIdServer(rs.getInt(12));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medi;
    }
    public List<MedicationStatement> getMediListPa(Patient p) throws SQLException, NichtErlaubException {
        conn.setAutoCommit(false);
        int id = p.getIdentifier();
        String insert = "SELECT * FROM medicamentStatement WHERE PID =" + id;

        List<MedicationStatement> medi = new ArrayList<>();
        Medikament med = null;
        Statement ps = null;

        try {
            ps = conn.createStatement();

            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            while(rs.next()) {
                medicationStatement = new MedicationStatement();
                medicationStatement.setIdentifier((rs.getInt(1)));
                medicationStatement.setPatient(getPatient(rs.getInt(2)));
                medicationStatement.setMedikament(getMedicament(rs.getInt(3)));
                medicationStatement.setTaken((rs.getString(4)));
                medicationStatement.setNote(rs.getString(5));
                medicationStatement.setStatusStmt((rs.getString(6)));
                medicationStatement.setPeriode(rs.getString(7));
                medicationStatement.setDosage(rs.getString(8));
                medicationStatement.setIdServer(rs.getInt(9));


                med = getMedicament(rs.getInt(3));
                medicationStatement.setForm(med.getForm());
                medicationStatement.setName(med.getName());
                medicationStatement.setManufacturer(med.getManufacturer());
                medicationStatement.setPrescription(med.isOverTheCounter());
                //medicationStatement.setIdServer(med.getIdServer());

                medi.add(medicationStatement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medi;
    }

    public List<Medikament> getMedikamentList() throws SQLException {
        conn.setAutoCommit(false);
        String insert = "SELECT * FROM medicament";

        Statement ps = null;
        List<Medikament> list = new ArrayList<>();
        try {
            ps = conn.createStatement();

            conn.commit();
            ResultSet rs = ps.executeQuery(insert);
            while(rs.next()) {
                medicament = new Medikament();
                medicament.setMedID(rs.getInt(1));
                medicament.setName(rs.getString(2));
                medicament.setCode(rs.getString(3));
                medicament.setOverCounter(rs.getBoolean(4));
                medicament.setForm(rs.getString(5));
                medicament.setManufacturer(rs.getString(6));
                medicament.setStatusMed(rs.getString(7));
                medicament.setIdServer(rs.getInt(8));
                list.add(medicament);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updatepaDaten(Patient p) throws SQLException {
        conn.setAutoCommit(false);
        int id = p.getIdentifier();
        String updatePa = "UPDATE patient SET  name = ?, vorname = ?,gender = ?,active = ?,telefon = ?,birthdate = ?, deseased = ?,street = ?,housenumber = ?, location = ?, postalcode = ?, aufnahmeDatum = ?, entlassungsDatum = ? ,entlassungStatus = ? idServer = ? WHERE identifier = " + id;
        PreparedStatement ps = null;

        try {

            patient = p;
            ps = conn.prepareStatement(updatePa);
            ps.setString(1, patient.getName());
            ps.setString(2, patient.getVorname());
            ps.setString(3, patient.getGender());
            ps.setBoolean(4, patient.getActive());
            ps.setString(5, patient.getTelefon());
            ps.setDate(6, (Date) patient.getGeburtsdatum());
            ps.setBoolean(7, patient.getDeseased());
            ps.setString(8, patient.getStreet());
            ps.setInt(9, patient.getHousenumber());
            ps.setString(10, patient.getLocation());
            ps.setInt(11, patient.getPostalcode());
            ps.setDate(12, (Date) patient.getAufnahmeDatum());
            ps.setDate(13, (Date) patient.getEntlassungsdatum());
            ps.setBoolean(14, patient.getEntlassungStatus());
            ps.setInt(15,patient.getIdServer());

            ps.executeUpdate();
            conn.commit();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public MedicationStatement updateMeDaten(MedicationStatement me) throws SQLException {
        conn.setAutoCommit(false);
        int  id = me.getIdentifier();
        String updateMe = "UPDATE medicamentstatement SET  MeID = ? , taken = ? , note = ? , status = ?, periode = ?, dosage = ? WHERE statID = " + id;

        PreparedStatement ps = null;
        try{
            ps =  conn.prepareStatement(updateMe );
            ps.setInt(1,me.getMedikament().getMedID() );
            ps.setString(2,me.getTaken());
            ps.setString(3,me.getNote() );
            ps.setString(4, me.getStatusStmt());
            ps.setString(5,me.getPeriode() );
            ps.setString(6,me.getDosage() );


            ps.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return me;
    }

    public void patientLoeschen(Patient p) throws SQLException {
        conn.setAutoCommit(false);
        int  id = p.getIdentifier();
        String deletePa = "DELETE from patient WHERE identifier = ?";

        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(deletePa);
            ps.setInt(1 ,id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void medStatementtLoeschen(MedicationStatement medS) throws SQLException {
        conn.setAutoCommit(false);
        int  id = medS.getIdentifier();
        String deleteMe = "DELETE from medicamentstatement WHERE statID = ?";

        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(deleteMe);
            ps.setInt(1 ,id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Patient pa = null;
        Medikament me = null;
        Krankenhaus kr = new Krankenhaus();
        kr.connect();
        pa = kr.getPatient(11);
        System.out.println(pa.getName());
        List<MedicationStatement> list = kr.getMediListPa(pa);
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.println("abruf " + i );
            System.out.println("" + list.get(i).getName() + " " + list.get(i).getManufacturer());
        }
        kr.connclose();
    }
    {
     Krankenhaus kr = new Krankenhaus();
     kr.connect();
     Patient pa = new Patient();
        pa.setIdServer(123456);
        pa.setName("Pierre");
        pa.setGeburtsdatum(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        pa.setAufnahmeDatum(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        try {
            kr.addPatientDB(pa);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            kr.connclose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
