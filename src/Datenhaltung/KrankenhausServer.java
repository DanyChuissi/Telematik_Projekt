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
        System.out.println("Anzahl Medikamente"+medications.size());
        for(int i = 0 ; i< medications.size(); i++) {
            if (medications.get(i).getCode().getText() != null) {
                System.out.println("Medikament " + i + "Code: " + medications.get(i).getCode().getText());
                System.out.println("Medikament " + i + "Form: " + medications.get(i).getForm().getText());
                System.out.println("Medikament " + i + "Prescription Nötig: " + medications.get(i).hasIsOverTheCounter());
            }
        }
    }
}
