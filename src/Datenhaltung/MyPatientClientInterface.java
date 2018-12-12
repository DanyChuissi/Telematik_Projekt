package src.Datenhaltung;


import ca.uhn.fhir.context.FhirContext;

//import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
//import ca.uhn.fhir.rest.client.api.IRestfulClient;

import ca.uhn.fhir.rest.param.StringParam;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.instance.model.Identifier;


import java.util.List;

public interface MyPatientClientInterface extends IRestfulClient
{
    /** A FHIR search */
    @Search
public List<Patient> findPatientsByIdentifier(@RequiredParam(name=Patient.SP_IDENTIFIER) IdentifierDt theIdentifier);

    @Search
    public List<Patient> findPatientsByName(@RequiredParam(name=Patient.SP_FAMILY) StringParam theFamily) ;

    /** A FHIR create */
    @Create
    public MethodOutcome createPatient(@ResourceParam Patient thePatient);

}
