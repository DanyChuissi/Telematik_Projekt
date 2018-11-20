package src.Datenhaltung;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
//import org.hl7.fhir.instance.model.Identifier;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Medication;
//import org.hl7.fhir.instance.model.Patient;
import ca.uhn.fhir.jpa.dao.DaoConfig;


import java.util.List;

public class KrankenhausServer  {

    @SuppressWarnings("unused")
    public static void main(String[] args){
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
        List<Patient> patients = client.findPatientsForMrn(new IdentifierDt("urn:oid:1.2.36.146.595.217.0.1", "12345"));
        //List<Patient> patients = client.ListPatient();
        List<Medication> medications = client2.getAlleMedikation();

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
        System.out.println("Anzahl Medikamente" + patients.size());
        for(int i = 0 ; i< patients.size(); i++) {
            if (patients.get(i) != null) {
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


        System.out.println("Anzahl Medikamente"+medications.size());

            for(int i = 0; i< medications.size();i++) {
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
                if(med.getStatus() != null) {
                    System.out.println("Medikament " + i + " status getDefinition: " + med.getStatus().getDefinition());
                    System.out.println("Medikament " + i + " status getDisplay: " + med.getStatus().getDisplay());
                }
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

    public Medikament MedFhirtoMyMed(Medication medicament){
      Medikament myMed = new Medikament();
            myMed.setCode(medicament.getCode().getCoding().get(0).getCode());
            myMed.setName(medicament.getCode().getText());
            myMed.setOverCounter(medicament.getIsOverTheCounter());
            myMed.setForm(medicament.getForm().getText());
            myMed.setManufacturer(medicament.getManufacturerTarget().getName());
        myMed
                myMed
        return myMed;
    }
}
