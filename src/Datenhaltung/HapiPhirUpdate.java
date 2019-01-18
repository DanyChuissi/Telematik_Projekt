package src.Datenhaltung;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import org.hl7.fhir.instance.model.Patient;


import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.OperationOutcome;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.PatchTypeEnum;

public class HapiPhirUpdate {


    /*private FhirContext ctx = FhirContext.forDstu3();
    private String serverBase = "http://fhirtest.uhn.ca/baseDstu3";
    private IGenericClient client = ctx.newRestfulGenericClient(serverBase);
*/
    public  void updatePatient(){
        Patient patient = new Patient();
// ..populate the patient object..
        patient.addIdentifier().setSystem("urn:system").setValue("1154588");
        patient.addName().addFamily("Mayer").addGiven("Anna");

// To update a resource, it should have an ID set (if the resource
// object
// comes from the results of a previous read or search, it will already
// have one though)
      /*  patient.setId("Patient/1154588");

        // Invoke the server update method
        MethodOutcome outcome = client.update()
                .resource(patient)
                .execute();*/

        // The MethodOutcome object will contain information about the
// response from the server, including the ID of the created
// resource, the OperationOutcome response, etc. (assuming that
// any of these things were provided by the server! They may not
// always be)
       /* IdDt id = (IdDt) outcome.getId();
        System.out.println("Got ID: " + id.getValue());*/
    }


    public static void main(String[] args){
        HapiPhirUpdate o = new HapiPhirUpdate();
        o.updatePatient();
    }

    //START SNIPPET: patch
    @Patch
    public OperationOutcome patientPatch(@IdParam IdType theId, PatchTypeEnum thePatchType, @ResourceParam String theBody) {

        if (thePatchType == PatchTypeEnum.JSON_PATCH) {
            // do something
        }
        if (thePatchType == PatchTypeEnum.XML_PATCH) {
            // do something
        }

        OperationOutcome retVal = new OperationOutcome();
        retVal.getText().setDivAsString("<div>OK</div>");
        return retVal;
    }
    //END SNIPPET: patch
}

