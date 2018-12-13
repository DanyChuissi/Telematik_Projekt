package src.Fachlogik;


        import src.PatientenGUI.NichtErlaubException;
        import java.text.ParseException;
        import java.util.Date;
        import java.text.SimpleDateFormat;
        import java.util.LinkedList;
        import java.util.List;


public class Patient {

    private int idServer;
    private int identifier;
    private boolean active = false;
    private String name;
    private String vorname;
    private String telefon;
    private java.sql.Date geburtsdatum ;
    private String geburtsdatumS ;
    private Date date = new Date();
    private String aufnahmeDatumS;
    private String entlassungsDatumS;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private boolean deseased;
    private String Street;
    private int Housenumber;
    private String location;
    private int postalcode;
    private java.sql.Date aufnahmeDatum;
    private java.sql.Date entlassungsDatum;
    private List<MedicationStatement> medicament;
    private  boolean enlassungStatus;
    private String gender;

    public Patient()
    {
        medicament = new LinkedList<MedicationStatement>();
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public void setGeburtsdatum(java.sql.Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public void setGeburtsdatumS(String geburtsdatumS) {
        this.geburtsdatumS = geburtsdatumS;
    }

    public void setAufnahmeDatumS(String aufnahmeDatumS) {
        this.aufnahmeDatumS = aufnahmeDatumS;
    }

    public void setEntlassungsDatumS(String entlassungsDatumS) {
    this.entlassungsDatumS = entlassungsDatumS;
    }
    public void setMedicament(List<MedicationStatement> medicament) {
        this.medicament = medicament;
    }

    public java.sql.Date stringToSqlDate(String st) throws ParseException {
        Date parsed = DATE_FORMAT.parse(st);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        return sql;
    }
    public void setGender(String gender) {
            this.gender = gender;
    }

    public String getGender(){
        return this.gender;
    }

    /*public void addmedicament(Medikament med, String taken, String status) {
        MedicationStatement neu = new MedicationStatement(this, med, taken, status);
        medicament.add(neu);
    }*/

    public void addMedStatment(MedicationStatement med){
        medicament.add(med);
    }

    public void setIdentifier(int id)
    {
        this.identifier = id;
    }

    public int getIdentifier()
    {
        return identifier;
    }

    public void setActive(boolean act)
    {
        this.active = act;
    }

    public boolean getActive()
    {
        return active;
    }

    public void setName(String name) {
        this.name= name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    public String getVorname()
    {
        return vorname;
    }

    public void setTelefon(String telefon) throws NumberFormatException {
           // long tel = Long.parseLong(telefon);
            this.telefon = telefon;
    }

    public String getTelefon()
    {
        return telefon;
    }

    public int getHousenumber() {
        return Housenumber;
    }

    public void setHousenumber(int housenumber) {
        Housenumber = housenumber;
    }

    public java.sql.Date getAufnahmeDatum() {
        return aufnahmeDatum;
    }

    public void setAufnahmeDatum(java.sql.Date aufnahmedatum) {
        this.aufnahmeDatum = aufnahmedatum;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getStreet() {
        return Street;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setEntlassungsDatum(java.sql.Date entlassungdatum) {
        this.entlassungsDatum = entlassungdatum;
    }

    public java.sql.Date getEntlassungsdatum() {
        return entlassungsDatum;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setDeseased(boolean deseased) {
        this.deseased = deseased;
    }

    public boolean getDeseased(){
        return  deseased;
    }

    public void setEnlassungStatus(boolean entlassungStatus)
    {
        this.enlassungStatus = entlassungStatus;
    }

    public boolean getEntlassungStatus()
    {
        return enlassungStatus;
    }

    public List<MedicationStatement> getMedicament() {
        return medicament;
    }

    public boolean isActive() {
        return active;
    }

    public java.sql.Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getGeburtsdatumS() {
        String erg = "";
        if(geburtsdatum != null){
            erg =  DATE_FORMAT.format(geburtsdatum);
        }
        return erg;
    }

    public boolean isDeseased() {
        return deseased;
    }

    public java.sql.Date getEntlassungsDatum() {
        return entlassungsDatum;
    }

    public String getAufnahmeDatumS() {
        String erg = "";
        if(aufnahmeDatum != null){
            erg = DATE_FORMAT.format(aufnahmeDatum);
        }
        return erg;
    }
    public String getEntlassungsDatumS() {
        String erg = "";
        if (entlassungsDatum != null) {
            erg = DATE_FORMAT.format(entlassungsDatum);
        }
        return erg;

    }
    public boolean isEnlassungStatus() {
        return enlassungStatus;
    }
    public String getAdresse(){
        return String.format("%s %d, %d %s", this.getStreet(), this.getHousenumber(),this.getPostalcode(),this.getLocation());
    }
}

