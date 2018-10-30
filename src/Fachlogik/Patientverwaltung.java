/**
 *
 * @author Dany Chuissi
 */

package src.Fachlogik;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patientverwaltung {
    private List<Patient> patientenList;
    private Date today = new Date();
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("DD-MM-YYYY");


    public Patientverwaltung(){
        patientenList = new ArrayList<>();
    }

    public void addPatient(Patient newPatient){
        setAufnahmedatum(newPatient);
        patientenList.add(newPatient);
    }

    public Patient getPatient(int i){
        return patientenList.get(i);
    }

    public void removePatientIndex(int i){
        patientenList.remove(i);
    }

    public void removePatientObjekt(Patient patient){
        patientenList.remove(patient);
    }

    public Patient suchePatientMitID(int id){
        Patient p = null;
        for(int i = 0; i< patientenList.size(); i++)
            if (patientenList.get(i).getIdentifier() == id) {
                p = patientenList.get(i);
                break;
            }
        return p;
    }

    public List<Patient> suchePatientMitname(String name){
        List<Patient> gefundenList = new ArrayList<>();
        for(int i = 0; i< patientenList.size(); i++){
            if(patientenList.get(i).getName() == name){
                gefundenList.add(patientenList.get(i));
            }
        }
        return gefundenList;
    }

    public void patientEntlassen(Patient p){
        p.setEntlassungsDatum(today);
        p.setEnlassungStatus(true);
    }

    public void setAufnahmedatum(Patient p){
        String date = DATE_FORMAT.format(today);
        p.setAufnahmeDatum(today);
    }

}
