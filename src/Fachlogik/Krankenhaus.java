package src.Fachlogik;



import src.PatientenGUI.NichtErlaubException;

import java.security.KeyRep;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Krankenhaus {

    private Patient patient = null;
    private Medikament medicament;
    private MedicationStatement medicationStatement;
    private DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD");

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
            conn = DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
            System.out.println("*** SQLException:\n" + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("*** Exception\n" + e);
            e.printStackTrace();
        }
    }

    public void patientStatement() throws SQLException {

        conn.setAutoCommit(false);
        String insert = "INSERT INTO Patient(identifier,name, vorname,gender,active,telefon,birthdate, deseased,street,housenumber, location, postalcode, aufnahmeDatum, entlassungsDatum ,entlassungStatus) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        // String insert = "INSERT INTO Patient(piz,name,vorname) VALUES(?,?,?)";
        PreparedStatement ps = null;

        try {


            ps = conn.prepareStatement(insert);

            ps.setInt(1, patient.getIdentifier());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getVorname());
            ps.setString(4, patient.getGender());
            ps.setBoolean(5, patient.getActive());
            ps.setInt(6, patient.getTelefon());
            ps.setDate(7, (Date) patient.getGeburtsdatum());
            ps.setBoolean(8, patient.getDeseased());
            ps.setString(9, patient.getStreet());
            ps.setInt(10, patient.getHousenumber());
            ps.setString(11, patient.getLocation());
            ps.setInt(12, patient.getPostalcode());
            ps.setDate(13, (Date) patient.getAufnahmeDatum());
            ps.setDate(14, (Date) patient.getEntlassungsdatum());
            ps.setBoolean(15, patient.getEntlassungStatus());

         //   ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addmedikament(Patient p, Medikament m, String taken, String status) throws SQLException {
        conn.setAutoCommit(false);

        String select = "INSERT INTO MedikamentStament(PID,MID,taken,status)VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(select);

            ps.setInt(1, patient.getIdentifier());
            ps.setInt(2, m.getMedID());
            ps.setString(3, taken);
            ps.setString(4, status);


            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<Patient> getpatientlist() throws SQLException, NichtErlaubException {

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
                patient.setTelefon(rs.getInt(6));
                patient.setGeburtsdatum(convertFromSQLDateToJAVADate(rs.getDate(7)));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(convertFromSQLDateToJAVADate(rs.getDate(13)));
                patient.setEntlassungsDatum(convertFromSQLDateToJAVADate(rs.getDate(14)));
                patient.setEnlassungStatus(rs.getBoolean(15));

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

        // String insert = "INSERT INTO Patient(piz,name,vorname) VALUES(?,?,?)";
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
                patient.setTelefon(rs.getInt(6));
                patient.setGeburtsdatum(convertFromSQLDateToJAVADate(rs.getDate(7)));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(convertFromSQLDateToJAVADate(rs.getDate(13)));
                patient.setEntlassungsDatum(convertFromSQLDateToJAVADate(rs.getDate(14)));
                patient.setEnlassungStatus(rs.getBoolean(15));
            }


        } catch (SQLException e) {
            e.printStackTrace();
       }
        return patient;
    }


    public static java.util.Date convertFromSQLDateToJAVADate(
            java.sql.Date sqlDate) {
        java.util.Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        return javaDate;
    }

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

    public Medikament getmedicment(int id) throws SQLException {

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
                medicament.setOverCounter(rs.getBoolean(3));
                medicament.setForm(rs.getString(4));
                medicament.setManufacturer(rs.getString(5));
                medicament.setStatus(rs.getString(6));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }

        public List<Patient> suchtPaI(int id) throws SQLException, NichtErlaubException {
        conn.setAutoCommit(false);
        String insert = "SELECT * FROM patient WHERE PID =" + id;

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
                patient.setTelefon(rs.getInt(6));
                patient.setGeburtsdatum(convertFromSQLDateToJAVADate(rs.getDate(7)));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(convertFromSQLDateToJAVADate(rs.getDate(13)));
                patient.setEntlassungsDatum(convertFromSQLDateToJAVADate(rs.getDate(14)));
                patient.setEnlassungStatus(rs.getBoolean(15));

                list.add(patient);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Patient> suchtpaNa(String id) throws SQLException, NichtErlaubException {
        conn.setAutoCommit(false);
        String insert = "SELECT * FROM patient WHERE name =" + id;

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
                patient.setTelefon(rs.getInt(6));
                patient.setGeburtsdatum(convertFromSQLDateToJAVADate(rs.getDate(7)));
                patient.setDeseased(rs.getBoolean(8));
                patient.setStreet(rs.getString(9));
                patient.setHousenumber(rs.getInt(10));
                patient.setLocation(rs.getString(11));
                patient.setPostalcode(rs.getInt(12));
                patient.setAufnahmeDatum(convertFromSQLDateToJAVADate(rs.getDate(13)));
                patient.setEntlassungsDatum(convertFromSQLDateToJAVADate(rs.getDate(14)));
                patient.setEnlassungStatus(rs.getBoolean(15));

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
                medicationStatement.setMedikament(getmedicment(rs.getInt(2)));
                medicationStatement.setTaken((rs.getString(3)));
                medicationStatement.setNote(rs.getString(4));
                medicationStatement.setDosage(rs.getString(5));
                medicationStatement.setPeriode(rs.getString(6));
                medicationStatement.setForm((rs.getString(7)));
                medicationStatement.setName(rs.getString(8));
                medicationStatement.setManufacturer(rs.getString(9));
                medicationStatement.setPrescription(rs.getBoolean(10));
                medicationStatement.setStatus((rs.getString(11)));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medi;
    }





    public static void main(String[] args) throws Exception
    {
        Patient pa = null;
        Medikament me = null;
        Krankenhaus kr = new Krankenhaus();
        kr.connect();
       // kr.patientStatement();
        //kr.addmedikament(pa,me,"","");
        // List<Patient> list = kr.getpatientlist();
       //for (int i = 0; i < list.size(); i++){
        // System.out.println(""+list.get(i).getName()+ " " +list.get(i).getVorname());
      // }
      //System.out.println(""+kr.getPatient(3).getName()+ " " +kr.getPatient(3).getVorname());
        System.out.println(kr.getmedicment(1).getName());

      kr.connclose();
    }


}
