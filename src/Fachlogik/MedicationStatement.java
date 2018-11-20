package src.Fachlogik;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MedicationStatement {
    private Patient patient;


    private Medikament medikament;
    private String idServer;
    private int identifier ;
    private String taken; // y | n | unk | na
    private String note;
    private String dosage;
    private String periode;
    private String form;
    private String name;
    private String manufacturer;
    private boolean prescription;
    private String statusStmt; //active | completed | entered-in-error | intended | stopped | on-hold
    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MMM-dd");


    public MedicationStatement(Patient patient, Medikament medikament, String taken, String status){
        this.patient = patient;
        this.medikament = medikament;
        this.taken = taken;
        this.statusStmt = status;
    }
    public MedicationStatement(){
    }

    public String getIdServer() {
        return idServer;
    }
    public void setIdServer(String idServer) {
        this.idServer = idServer;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    public boolean isPrescription() {
        return prescription;
    }
    public String getForm() {
        return this.form;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public boolean isPresciption() {
        return this.isPresciption();
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setPeriodevonbis(String von, String bis) {
        periode =   von+ " bis "+ bis;
    }

    public String getPeriode() {
        return periode;
    }

    public void setStatusStmt(String status) {
        this.statusStmt = status;
    }

    public String getStatusStmt() {
        return this.statusStmt;
    }

    public Medikament getMedikament() {
        return medikament;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getTaken() {
        return taken;
    }

    public String getNote() {
        return note;
    }

    public String getDosage() {
        return dosage;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setMedikament(Medikament medikament) {
        this.medikament = medikament;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}



