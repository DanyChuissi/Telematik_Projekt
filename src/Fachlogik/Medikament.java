package src.Fachlogik;

public class Medikament {

    private long identifier;
    private long basedOn;
    private long partOf;
    private long context;
    private long status;
    private long category;
    private int medication [];
    private int effective[];
    private long dateAsserted;
    private long informationSource;
    private long subject;
    private long derivedForm;
    private long taken;
    private long reasonNotTaken;
    private long reasonCode;
    private long reasonReference;
    private long note;
    private long dosage;

    public Medikament( long identifier, long basedOn, long partOf,long context, long status, long category, long dateAsserted, long informationSource, long subject, long derivedForm, long taken, long reasonNotTaken, long reasonCode,long reasonReference, long note, long dosage){
        this.identifier = identifier;
        this.basedOn= basedOn;
        this.partOf= partOf;
        this.context=context;
        this.status=status;
        this.category=category;
        this.dateAsserted=dateAsserted;
        this.informationSource=informationSource;
        this.subject=subject;
        this.derivedForm=derivedForm;
        this.taken=taken;
        this.reasonNotTaken=reasonNotTaken;
        this.reasonCode=reasonCode;
        this.reasonReference=reasonReference;
        this.note=note;
        this.dosage=dosage;


    }

    public long getIdentifier() {
        return identifier;
    }


    public long getBasedOn() {
        return basedOn;
    }


    public long getCategory() {
        return category;
    }

    public long getContext() {
        return context;
    }

    public long getDateAsserted() {
        return dateAsserted;
    }

    public long getDerivedForm() {
        return derivedForm;
    }

    public long getDosage() {
        return dosage;
    }

    public long getInformationSource() {
        return informationSource;
    }

    public long getNote() {
        return note;
    }

    public long getPartOf() {
        return partOf;
    }

    public long getReasonCode() {
        return reasonCode;
    }

    public long getReasonNotTaken() {
        return reasonNotTaken;
    }

    public long getReasonReference() {
        return reasonReference;
    }

    public long getStatus() {
        return status;
    }

    public long getSubject() {
        return subject;
    }

    public long getTaken() {
        return taken;
    }

    public int[] getEffective() {
        return effective;
    }

    public int[] getMedication() {
        return medication;
    }

    public void setBasedOn(long basedOn) {
        this.basedOn = basedOn;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public void setContext(long context) {
        this.context = context;
    }

    public void setDateAsserted(long dateAsserted) {
        this.dateAsserted = dateAsserted;
    }

    public void setDerivedForm(long derivedForm) {
        this.derivedForm = derivedForm;
    }

    public void setDosage(long dosage) {
        this.dosage = dosage;
    }

    public void setEffective(int[] effective) {
        this.effective = effective;
    }

    public void setInformationSource(long informationSource) {
        this.informationSource = informationSource;
    }

    public void setMedication(int[] medication) {
        this.medication = medication;
    }

    public void setNote(long note) {
        this.note = note;
    }

    public void setPartOf(long partOf) {
        this.partOf = partOf;
    }

    public void setReasonCode(long reasonCode) {
        this.reasonCode = reasonCode;
    }

    public void setReasonNotTaken(long reasonNotTaken) {
        this.reasonNotTaken = reasonNotTaken;
    }

    public void setReasonReference(long reasonReference) {
        this.reasonReference = reasonReference;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setSubject(long subject) {
        this.subject = subject;
    }

    public void setTaken(long taken) {
        this.taken = taken;
    }
}
