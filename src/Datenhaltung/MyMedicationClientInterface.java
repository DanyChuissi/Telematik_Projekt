package src.Datenhaltung;

import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.CodeType;

import java.util.List;

public interface MyMedicationClientInterface extends IRestfulClient {

    /** A FHIR search */
    @Search
    public List<Medication> findMedikationsByIdentifier(@RequiredParam(name="identifier") IdentifierDt theIdentifier);

    @Search
    public List<Medication> findMedicationsByCode(@RequiredParam(name="Name") CodeType code);
    /**
     * This is translated into a URL similar to the following:
     * http://fhir.healthintersections.com.au/open/Patient?identifier=urn:oid:1.2.36.146.595.217.0.1%7C12345
     */
    @Search
    List<Medication> getAlleMedikation();


}

