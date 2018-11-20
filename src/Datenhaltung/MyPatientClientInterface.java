package src.Datenhaltung;


import ca.uhn.fhir.context.FhirContext;

//import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import org.hl7.fhir.dstu3.model.Identifier;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
//import ca.uhn.fhir.rest.client.api.IRestfulClient;

import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.StringType;



import java.util.List;

public interface MyPatientClientInterface extends IRestfulClient
{
    /** A FHIR search */
    @Search
public List<Patient> findPatientsByIdentifier(@RequiredParam(name="identifier") IdentifierDt theIdentifier);

    @Search
    public List<Patient> findPatientsByName(@RequiredParam(name="Name") StringType name);

    /** A FHIR create */
    @Create
    public MethodOutcome createPatient(@ResourceParam Patient thePatient);

    /**
     * This is translated into a URL similar to the following:
     * http://fhir.healthintersections.com.au/open/Patient?identifier=urn:oid:1.2.36.146.595.217.0.1%7C12345
     */
    @Search
    List<Patient> findPatientsForMrn(@RequiredParam(name = Patient.SP_IDENTIFIER) IdentifierDt theIdentifier);

    @Search
    List<Patient> ListPatient();
}
