package src.Fachlogik;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicationStatement {


    private List<String> basedOn;
    private List<String> partOf;
    private String context;
    private String status;
    private String category;
    private String medication;
    private Date dateAsserted;
    private String effective;
    private String informationSource;
    private String subject;
    private List<String> derivedForm;
    private String taken;
    private List<String> reasonNotTaken;
    private List<String> reasonCode;
    private List<String> reasonReference;
    private List<String> note;
    private List<String> dosage;
    private List<Long> identifier;


    public MedicationStatement(String status, String medication, String subject, String taken, String context,String category,String effective){
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
        this.context=context;
        this.category=category;
        this.effective=effective;




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

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public void setDateAsserted(Date dateAsserted) {
        this.dateAsserted = dateAsserted;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setInformationSource(String informationSource) {
        this.informationSource = informationSource;
    }

    public String getCategory() {
        return category;
    }

    public String getContext() {
        return context;
    }

    public String getEffective() {
        return effective;
    }

    public String getMedication() {
        return medication;
    }

    public String getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public String getTaken() {
        return taken;
    }

    public Date getDateAsserted() {
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
   // public String getBesedOn (int i){
       // return basedOn.get(i);
    //}

    public List<String> getDosage() {
        return dosage;
    }

    public List<String> getBasedOn() {
        return basedOn;
    }

    public List<String> getPartOf() {
        return partOf;
    }

    public List<String> getNote() {
        return note;
    }

    public List<String> getReasonCode() {
        return reasonCode;
    }

    public List<String> getReasonNotTaken() {
        return reasonNotTaken;
    }

    public List<String> getReasonReference() {
        return reasonReference;
    }
    public void removeIdentifierIndex(int i){
        identifier.remove(i);
    }
    public void removeBasedOnIndex(int i){
        basedOn.remove(i);
    }
    public void removePartOfIndex(int i){
        partOf.remove(i);
    }
    public void removeDerivedFromIndex(int i){
        derivedForm.remove(i);
    }
    public void removeReasonNotTakenIndex(int i){
        reasonNotTaken.remove(i);
    }
    public void removeReasonCodeIndex(int i){
        reasonCode.remove(i);
    }
    public void removeReasonReferenceIndex(int i){
        reasonReference.remove(i);
    }
    public void removeNoteIndex(int i){
        note.remove(i);
    }
    public void removeDosageIndex(int i){
        dosage.remove(i);
    }
    public void removeIdentifierObject(Long identifier){
        this.identifier.remove(identifier);
    }
    public void removeBesedOnObject(String basedOn){
        this.basedOn.remove(basedOn);
    }
    public void removePartOfObject(String partOf){
        this.partOf.remove(partOf);
    }
    public void removeDerivedFromObject(String derivedForm){
        this.derivedForm.remove(derivedForm);
    }
    public void removeReasonNotTakenObject(String reasonNotTaken){
        this.reasonNotTaken.remove(reasonNotTaken);
    }
    public void removeReasonCodeObject(String reasonCode){
        this.reasonCode.remove(reasonCode);
    }
    public void removeReasopnReference(String reasonReference){
        this.reasonReference.remove(reasonReference);
    }public void removeNote(String note){
        this.note.remove(note);
    }
    public void removeDosage(String dosage){
        this.dosage.remove(dosage);
    }
}



