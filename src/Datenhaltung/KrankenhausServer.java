package src.Datenhaltung;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
//import org.hl7.fhir.instance.model.Identifier;
import org.hl7.fhir.dstu3.model.*;
//import org.hl7.fhir.instance.model.Patient;
import ca.uhn.fhir.jpa.dao.DaoConfig;
import src.Fachlogik.Medikament;



import java.util.List;

public class KrankenhausServer {

    private Krankenhaus krankenhaus = new Krankenhaus();

    public Medikament MedFhirtoMyMed(Medication medicament) {
        Medikament myMed = new Medikament();
        myMed.setCode(medicament.getCode().getCoding().get(0).getCode());
        myMed.setName(medicament.getCode().getText());
        myMed.setOverCounter(medicament.getIsOverTheCounter());
        myMed.setForm(medicament.getForm().getText());
        myMed.setManufacturer(medicament.getManufacturerTarget().getName());
        myMed.setOverCounter(medicament.getIsOverTheCounter());
        myMed.setStatusMed(medicament.getStatus().getDisplay());

        return myMed;
    }

    public void MedSpeicher(List<Medikament> medi){

    }

   /* public src.Fachlogik.Patient PatFhirMyPat(Patient pa){
        src.Fachlogik.Patient pat = new src.Fachlogik.Patient();
        pat.setIdentifier();
        pat.setActive(pa.getActive());
        pat.setName(pa.getName().get(0).getFamily());
        pat.setName(pa.getName());
        pat.setTelefon(pa.getTelecom().get(0).getValue());
        pat.setGeburtsdatum(pa.getBirthDate());
        pat.setDeseased(pa.getDeceased());
        pa

    }*/



    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // DaoConfig dao = new DaoConfig();
        //dao.setAllowInlineMatchUrlReferences(true);

        FhirContext ctx = FhirContext.forDstu3();
        String classpath = "http://hapi.fhir.org/baseDstu3";
        MyPatientClientInterface client = ctx.newRestfulClient(MyPatientClientInterface.class, classpath);
        MyMedicationClientInterface client2 = ctx.newRestfulClient(MyMedicationClientInterface.class, classpath);
        //IRestfulClient client = ctx.newRestfulClient(IRestfulClient.class,classpath);

        //IdDt searchParam = new Identifier().setValue("Max");
        // IdentifierDt searchParam = new IdentifierDt().setValue("163086");

        //List<Patient> clients = client.findPatientsByIdentifier(searchParam);

       /* List<Patient> clients = client.getPatient(new StringDt("Smith"));
 System.out.println("Found " + patients.size() + " patients");
         for(int i = 0 ; i< patients.size(); i++){
            System.out.println(patients.get(i).getId());
        }

*/
      //  List<Patient> patients = client.findPatientsForMrn(new IdentifierDt("urn:oid:1.2.36.146.595.217.0.1", "12345"));
        List<Patient> patients = client.ListPatient();
        List<Medication> medications = client2.getAlleMedikation();

        Observation observation = new Observation();
        List<Identifier> identifierList = observation.getIdentifier();


        /*Patient patient = patients.get(0);

        System.out.println("Patient Name: " + patient.getName().get(0).getNameAsSingleString());
        System.out.println("Geburtsdatum: "+patient.getBirthDate().toString());
        System.out.println("Patient Id: " +patient.getIdentifier().get(0).getValue());
        System.out.println("Patient Gender: "+ patient.getGender().name());
        System.out.println("Patietn Contry: " +patient.getAddress().get(0).getCity());

/*
        for(int i =0; i< medications.size(); i++){
            System.out.println(medications.get(i).getCode().getCodingFirstRep().getCode());
        }
*/
        System.out.println("Anzahl Patient" + patients.size());
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i) != null) {
                System.out.println("Patient " + i + " Identifier: " + patients.get(i).getIdentifier().get(0).getValue());
                System.out.println("Patient " + i + " Identifier: " + patients.get(i).getIdentifier().get(0).getSystem());

                System.out.println("Patient " + i + " Active: " + patients.get(i).getActive());
                if(patients.get(i).hasName()) {
                    System.out.println("Patient " + i + " Name: " + patients.get(i).getName().get(0).getFamily());
                    //System.out.println("Patient " + i + " Vorname " + patients.get(i).getName().get(0).getGiven());
                }
                if(patients.get(i).hasTelecom()) {
                    System.out.println("Patient " + i + " Telefon: " + patients.get(i).getTelecom().get(0).getRank());
                    //System.out.println("Patient " + i + " Telefon " + patients.get(i).getTelecom().get(0).getValue());
                }
                System.out.println("Patient " + i + " Geburstag: " + patients.get(i).getBirthDate());
                // System.out.println("Patient " + i + "AufnahmeDatum " + patients.get(i).get);
                // System.out.println("Patient " + i + "EntlassungsDatum " + patients.get(i).getName());
                System.out.println("Patient " + i + " Deseased: " + patients.get(i).getDeceased());
                if(patients.get(i).hasAddress()) {
                    System.out.println("Patient " + i + "Street: " + patients.get(i).getAddress().get(0).getDistrict());
                    System.out.println("Patient " + i + " Hausnummer: " + patients.get(i).getAddress().get(0).getLine());
                    System.out.println("Patient " + i + " Location: " + patients.get(i).getAddress().get(0).getState());
                    System.out.println("Patient " + i + " PostalCode: " + patients.get(i).getAddress().get(0).getPostalCode());
                }
                    //System.out.println("Patient " + i + "Entlassungstatus " + patients.get(i).getName());
                if(patients.get(i).hasGender()) {
                    System.out.println("Patient " + i + " Gender: " + patients.get(i).getGender().getDefinition());
                }


               // System.out.println("Anzahl Medikamente" + medications.size());

              /*  for (int y = 0; i < medications.size(); i++) {
                    Medication med = medications.get(i);

                    System.out.println("Medikament " + i + " IsEmpty: " + med.isEmpty());
                    System.out.println("Medikament " + i + " firstType: " + med.fhirType());
                    System.out.println("Medikament " + i + " Code GetText: " + med.getCode().getText());
                    System.out.println("Medikament " + i + " Code firstType: " + med.getCode().fhirType());
                    System.out.println("Medikament " + i + " Code Coding: " + med.getCode().getCoding().get(0).getCode());
                    System.out.println("Medikament " + i + " Code Coding Display: " + med.getCode().getCoding().get(0).getDisplay());
                    System.out.println("Medikament " + i + " Code toString: " + med.getCode().toString());
                    System.out.println("Medikament " + i + " Form gettext: " + med.getForm().getText());
                    System.out.println("Medikament " + i + " Form firstType: " + med.getForm().fhirType());
                    System.out.println("Medikament " + i + " Form toString: " + med.getForm().toString());
                    System.out.println("Medikament " + i + " hastOverTheCounter: " + med.hasIsOverTheCounter());
                    System.out.println("Medikament " + i + " hastOverTheCounter: " + med.getIsOverTheCounter());
                    System.out.println("Medikament " + i + " Manufacturer getName " + med.getManufacturerTarget().getName());
                    if (med.getStatus() != null) {
                        System.out.println("Medikament " + i + " status getDefinition: " + med.getStatus().getDefinition());
                        System.out.println("Medikament " + i + " status getDisplay: " + med.getStatus().getDisplay());
                    }*/
                /*
                System.out.println("Medikament " + i + "Form: " + medications);
                System.out.println("Medikament " + i + "Prescription Nötig: " + medications);
                System.out.println("Medikament " + i + "Code: " + medications);
                System.out.println("Medikament " + i + "Form: " + medications);
                System.out.println("Medikament " + i + "Prescription Nötig: " + medications);
                System.out.println("Medikament " + i + "Code: " + medications);
                System.out.println("Medikament " + i + "Form: " + medications);
                System.out.println("Medikament " + i + "Prescription Nötig: " + medications);
                System.out.println("Medikament " + i + "Code: " + medications);
                System.out.println("Medikament " + i + "Form: " + medications);
                System.out.println("Medikament " + i + "Prescription Nötig: " + medications);
*/
                    System.out.println("------------------------------------------------------------");
                }
            }


        }
    }




