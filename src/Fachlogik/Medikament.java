package src.Fachlogik;

import java.util.ArrayList;
import java.util.List;

public class Medikament {

   ;
    private List<String> basedOn;
    private List<String> partOf;
    private boolean context;
    private boolean status;
    private boolean category;
    private boolean medication;
    private boolean effective;
    private int dateAsserted;
    private String informationSource;
    private boolean subject;
    private List<String> derivedForm;
    private boolean taken;
    private List<String> reasonNotTaken;
    private List<String> reasonCode;
    private List<String> reasonReference;
    private List<String> note;
    private List<String> dosage;
    private List<Long> identifier;


    public Medikament(boolean status, boolean medication, boolean subject,boolean taken){
        identifier = new ArrayList<>();
        basedOn = new ArrayList<>();
        partOf = new ArrayList<>();
        derivedForm = new ArrayList<>();
        reasonReference= new ArrayList<>();
        reasonNotTaken = new ArrayList<>();
        reasonCode = new ArrayList<>();
        note = new ArrayList<>();
        dosage = new ArrayList<>();
        this.medication=medication;
        this.status= status;
        this.subject=subject;
        this.taken=taken;



    }
    public void addIdentifier( long newIdentifier){
        identifier.add(newIdentifier);
    }
    public void addBasedOn (String newBesedOn ){
        basedOn.add(newBesedOn);
    }
    public void addPartOf ( String newPartOf){
        partOf.add(newPartOf);
    }
    public void addDrivedForm( String newDrivedFrom){
        derivedForm.add(newDrivedFrom);
    }
    public void addReasonReference( String newReasonReference){
        reasonReference.add(newReasonReference);
    }
    public void addReasonNotTaken( String newReasonNotTaken){
        reasonReference.add(newReasonNotTaken);
    }
    public void addReasonCode( String newReasoncode){
        reasonCode.add(newReasoncode);
    }
    public void addNote( String newNote){
        note.add(newNote);
    }
    public void addDosage( String newDosage){
        dosage.add(newDosage);
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public void setSubject(boolean subject) {
        this.subject = subject;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMedication(boolean medication) {
        this.medication = medication;
    }

    public void setContext(boolean context) {
        this.context = context;
    }

    public void setEffective(boolean effective) {
        this.effective = effective;
    }

    public void setDateAsserted(int dateAsserted) {
        this.dateAsserted = dateAsserted;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public void setInformationSource(String informationSource) {
        this.informationSource = informationSource;
    }

    public boolean isCategory() {
        return category;
    }

    public boolean isTaken() {
        return taken;
    }

    public boolean isContext() {
        return context;
    }

    public boolean isSubject() {
        return subject;
    }

    public boolean isEffective() {
        return effective;
    }

    public boolean isMedication() {
        return medication;
    }

    public boolean isStatus() {
        return status;
    }

    public int getDateAsserted() {
        return dateAsserted;
    }

    public String getInformationSource() {
        return informationSource;
    }

    public List<Long> getIdentifier() {
        return identifier;
    }

    public List<String> getDerivedForm() {
        return derivedForm;
    }

    public void setReasonNotTaken(List<String> reasonNotTaken) {
        this.reasonNotTaken = reasonNotTaken;
    }

    public void setPartOf(List<String> partOf) {
        this.partOf = partOf;
    }

    public void setReasonReference(List<String> reasonReference) {
        this.reasonReference = reasonReference;
    }

    public void setBasedOn(List<String> basedOn) {
        this.basedOn = basedOn;
    }

    public void setReasonCode(List<String> reasonCode) {
        this.reasonCode = reasonCode;
    }

    public void setDerivedForm(List<String> derivedForm) {
        this.derivedForm = derivedForm;
    }

    public void setNote(List<String> note) {
        this.note = note;
    }

    public void setDosage(List<String> dosage) {
        this.dosage = dosage;
    }

    public void setIdentifier(List<Long> identifier) {
        this.identifier = identifier;
    }
}



