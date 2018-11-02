package src.Fachlogik;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicationStatement {
    private Patient patient;
    private Medikament medikament;
    private String identifier;
    private String BasedOn; // (MedicationRequest | CarePlan | ProcedureRequest | ReferralRequest)
    private String partOf; // (MedicationAdministration | MedicationDispense | MedicationStatement | Procedure | Observation)
    private String context; // (Encounter | EpisodeOfCare)
    private Date dateAsserted;
    private String taken; // y | n | unk | na
    private String note;
    private String dosage;
    private String periode;
    private String form;
    private String name;
    private String manufacturer;

    public void setForm(String form) {
        this.form = form;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPresciption(boolean presciption) {
        this.presciption = presciption;
    }

    private boolean presciption;
    private String status; //active | completed | entered-in-error | intended | stopped | on-hold
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD");


    public MedicationStatement(Patient patient, Medikament medikament, String taken, String status){
        this.patient = patient;
        this.medikament = medikament;
        this.taken = taken;
        this.status = status;
    }
    public MedicationStatement(){

    }



    public String getForm() {
        return medikament.getForm();
    }

    public String getName() {
        return medikament.getName();
    }

    public String getManufacturer() {
        return medikament.getManufacturer();
    }

    public boolean isPresciption() {
        return medikament.isOverCounter();
    }

    public void setPeriode(String von, String bis) {
        periode =   von+ " bis "+ bis;

    }

    public String getPeriode() {
        return periode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Medikament getMedikament() {
        return medikament;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getBasedOn() {
        return BasedOn;
    }

    public String getPartOf() {
        return partOf;
    }

    public String getContext() {
        return context;
    }

    public Date getDateAsserted() {
        return dateAsserted;
    }
    public Date stringToDate(String input) throws ParseException {
        return DATE_FORMAT.parse(input);
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

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setBasedOn(String basedOn) {
        BasedOn = basedOn;
    }

    public void setPartOf(String partOf) {
        this.partOf = partOf;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setDateAsserted(Date dateAsserted) {
        this.dateAsserted = dateAsserted;
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



