package src.Datenhaltung;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
//import org.hl7.fhir.instance.model.Identifier;
import ca.uhn.fhir.rest.param.StringParam;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Medication;
//import org.hl7.fhir.instance.model.Patient;
import ca.uhn.fhir.jpa.dao.DaoConfig;
import src.Fachlogik.Medikament;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KrankenhausServer {

    private Krankenhaus krankenhaus = new Krankenhaus();

    /* Diese Methode wandelt eine Fhir MEdication in eine Lokale Medikament*/
    public List<Medikament> MedFhirtoMyMed(List<Medication> medications) {
        List<Medikament> myMedikamente = new ArrayList<>();
        for (int i = 0; i < medications.size(); i++) {
            Medikament myMed = new Medikament();
            if (medications.get(i).getCode().getCoding().size() > 0) {
                myMed.setCode(medications.get(i).getCode().getCoding().get(0).getCode());
            }
            myMed.setName(medications.get(i).getCode().getText());
            myMed.setOverCounter(medications.get(i).getIsOverTheCounter());
            if (medications.get(i).getCode().getCoding().size() > 0) {
                myMed.setForm(medications.get(i).getCode().getCoding().get(0).getDisplay());
            }
            myMed.setManufacturer(medications.get(i).getManufacturerTarget().getName());
            if (medications.get(i).getStatus() != null) {
                myMed.setStatusMed(medications.get(i).getStatus().getDisplay());
            }
            myMedikamente.add(myMed);
        }

        return myMedikamente;
    }

    /* Diese Methode speichern die Medikamente List von Server in den Lokale DatenBank*/
    public void MedicamenteSpeichern(List<Medikament> medi) {
        krankenhaus.connect();
        for (int i = 0; i < medi.size(); i++) {
            System.out.println("einfug versucht: " + i);
            try {
                if (medi.get(i).getName() != null) {
                    krankenhaus.addMedikamentDB(medi.get(i));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            krankenhaus.connclose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        KrankenhausServer krSer = new KrankenhausServer();
        DaoConfig dao = new DaoConfig();
        dao.setAllowInlineMatchUrlReferences(true);

        FhirContext ctx = FhirContext.forDstu3();
        String classpath = "http://hapi.fhir.org/baseDstu3/";

        MyPatientClientInterface client = ctx.newRestfulClient(MyPatientClientInterface.class, classpath);
        MyMedicationClientInterface client2 = ctx.newRestfulClient(MyMedicationClientInterface.class, classpath);

        // try with ID
        List<Patient> patients = client.findPatientsByIdentifier(new IdentifierDt("urn:oid:1.2.36.146.595.217.0.1", "12345"));

        // try with name
        //StringParam kriterien = new StringParam("SMITH", true);
        //List<Patient> patients = client.findPatientsByName(kriterien);










        List<Medication> medications = client2.getAlleMedikation();











        //IRestfulClient client = ctx.newRestfulClient(IRestfulClient.class,classpath);

        //IdDt searchParam = new Identifier().setValue("Max");
        // IdentifierDt searchParam = new IdentifierDt().setValue("163086");

        //List<Patient> clients = client.findPatientsByIdentifier(searchParam);

                            /*
                               List<Patient> clients = client.getPatient(new StringDt("Smith"));
                              System.out.println("Found " + patients.size() + " patients");
                                 for(int i = 0 ; i< patients.size(); i++){
                                    System.out.println(patients.get(i).getId());
                                }
                        */
       // List<Patient> patients = client.findPatientsForMrn(new IdentifierDt("urn:oid:1.2.36.146.595.217.0.1", "12345"));
    /*
                      List<Medication> medications = client2.getAlleMedikation();
                        List<Medikament> myMedikamente = krSer.MedFhirtoMyMed(medications);
                        for (int i = 0; i < myMedikamente.size(); i++) {
                            System.out.println(myMedikamente.get(i).getCode() + " " + myMedikamente.get(i).getName());
                        }

                        krSer.MedicamenteSpeichern(myMedikamente);
                     */

                    /*
                              System.out.println("Patient Name: " + patient.getName().get(0).getNameAsSingleString());
                              System.out.println("Geburtsdatum: "+patient.getBirthDate().toString());
                              System.out.println("Patient Id: " +patient.getIdentifier().get(0).getValue());
                              System.out.println("Patient Gender: "+ patient.getGender().name());
                              System.out.println("Patietn Contry: " +patient.getAddress().get(0).getCity());
                    */


                                System.out.println("Anzahl Patienten" + patients.size());
                                for (int i = 0; i < patients.size(); i++) {
                                if (patients.get(i) != null) {
                                    System.out.println("Patient " + i + " Identifier IDPart: " + patients.get(i).getIdElement().getIdPart());
                                    System.out.println("Patient " + i + " Identifier: " + patients.get(i).getIdentifier().get(0).getValue());
                                    System.out.println("Patient " + i + " Identifier: " + patients.get(i).getIdentifier().get(0).getSystem());

                                    System.out.println("Patient " + i + " Active " + patients.get(i).getActive());
                                    System.out.println("Patient " + i + " Name " + patients.get(i).getName().get(0).getFamily());
                                    System.out.println("Patient " + i + " Vorname " + patients.get(i).getName().get(0).getGiven());
                                    //System.out.println("Patient " + i + " Telefon " + patients.get(i).getTelecom().get(0).getValue());
                                    //System.out.println("Patient " + i + " Telefon " + patients.get(i).getTelecom().get(0).getValue());

                                    System.out.println("Patient " + i + " Geburstag " + patients.get(i).getBirthDate());
                                    // System.out.println("Patient " + i + "AufnahmeDatum " + patients.get(i).get);
                                    // System.out.println("Patient " + i + "EntlassungsDatum " + patients.get(i).getName());
                                    System.out.println("Patient " + i + " Deseased " + patients.get(i).getDeceased());
                                    // System.out.println("Patient " + i + "Street " + patients.get(i).getAddress().get(0).getDis);
                                    System.out.println("Patient " + i + " Hausnummer " + patients.get(i).getAddress().get(0).getLine());
                                    System.out.println("Patient " + i + " Location " + patients.get(i).getAddress().get(0).getState());
                                    System.out.println("Patient " + i + " PostalCode " + patients.get(i).getAddress().get(0).getPostalCode());
                                    //System.out.println("Patient " + i + "Entlassungstatus " + patients.get(i).getName());
                                    System.out.println("Patient " + i + " Gender " + patients.get(i).getGender().getDefinition());




                        /*
                                    System.out.println("Anzahl Medikamente" + medications.size());

                                    for (int i = 0; i < medications.size(); i++) {
                                        Medication med = medications.get(i);

                                        System.out.println("Medikament " + i + " IsEmpty: " + med.isEmpty());
                                        System.out.println("Medikament " + i + " Code GetText: " + med.getCode().getText());
                                        if(med.getCode().getCoding().size()>0) {
                                            System.out.println("Medikament " + i + " Code Coding: " + med.getCode().getCoding().get(0).getCode());
                                        }
                                        System.out.println("Medikament " + i + " Code Coding Display: " + med.getCode().getCoding().get(0).getDisplay());
                                        System.out.println("Medikament " + i + " Form gettext: " + med.getForm().getText());
                                        System.out.println("Medikament " + i + " hastOverTheCounter: " + med.hasIsOverTheCounter());
                                        System.out.println("Medikament " + i + " gettOverTheCounter: " + med.getIsOverTheCounter());
                                        System.out.println("Medikament " + i + " Manufacturer getName " + med.getManufacturerTarget().getName());
                                        if (med.getStatus() != null) {
                                            System.out.println("Medikament " + i + " status getDefinition: " + med.getStatus().getDefinition());
                                            System.out.println("Medikament " + i + " status getDisplay: " + med.getStatus().getDisplay());
                                       }
                             */

                          System.out.println("------------------------------------------------------------");
                  }

              }


    }
}




