package src.Fachlogik;

public class Medikament {
    private int MedID;
    private String name;
    private String code; // SNOMED Code
    private boolean isOverCounter; // if require prescription
    private String form; // powder | tablets | capsule +
    private String manufacturer; // Organisation
    private String statusMed;// active | inactive | entered-in-error

    public Medikament(){
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMedID(int medID) {
        MedID = medID;
    }

    public int getMedID() {
        return MedID;
    }

  //  @Override
   // public String toString(){
       // return this.code;
  //  }
    public boolean isOverCounter() {
        return isOverCounter;
    }

    public String getCode() {
        return code;
    }

    public String getForm() {
        return form;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getStatusMed() {
        return statusMed;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setOverCounter(boolean overCounter) {
        isOverCounter = overCounter;
    }

    public void setStatusMed(String status) {
        this.statusMed = status;
    }
}
