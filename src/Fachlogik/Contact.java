/**
 *
 * @author Dany Chuissi
 */

package src.Fachlogik;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private List<String> relationship; // Soll mehrfach Eintrag möglich sein
    private String name;
    private List<String> telecom;
    private String adresse;
    private String gender;
    private String organisation;
    private String period; //  Soll Period ein String sein?

    public Contact(){
        relationship = new ArrayList<>();
        telecom = new ArrayList<>();
    }

    public String getAdresse() {
        return adresse;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getRelationship() {
        return relationship;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getPeriod() {
        return period;
    }

    public List<String> getTelecom() {
        return telecom;
    }
    public void addTelecom(String telecom){
        this.telecom.add(telecom);
    }
    public void remoeTelefon(int i){
        this.telecom.remove(i);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void addRelationship(String relationship) {
        this.relationship.add(relationship);
    }
    public void removeRelationship(int i){
        this.relationship.remove(i);
    }
/*
    public void setTelefon(String telecom) {
        this.telecom = telecom;
    }
*/
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}
