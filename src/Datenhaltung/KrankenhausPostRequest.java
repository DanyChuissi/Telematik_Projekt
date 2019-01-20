package src.Datenhaltung;


import ca.uhn.fhir.model.dstu2.resource.MessageHeader;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class KrankenhausPostRequest {

    public static void main(String[] args) {

        /*Patient p1 = new Patient();
        p1.setName("Dany");
        p1.setVorname("Martin");
        p1.setStreet("Meitnerweg 3");
       p1.setLocation("Dortmund");
       p1.setPostalcode(12345);

       Patient P3 = p1;

       P3.setName("rrrrr");


       System.out.println(p1.getName());


        Patient p2 = new Patient();
        p2.setName("Dany");
        p2.setVorname("Martin");
        p2.setStreet("Meitnerweg 3");
        p2.setLocation("Dortmund");
        p2.setPostalcode(12345);

       p2.setVorname("test");
       p2.setLocation("Essen");
      p2.setPostalcode(543221);*/

       //changeCheck(p1, p2);


    }


    public void patchPatient(Patient orig, Patient alt) throws Exception {

        String payload = changeCheck(orig, alt);

        System.out.println(payload);

        if(orig.getIdServer() != null  ) {
            if(payload.length() > 20) {
                StringEntity entity = new StringEntity(payload);

                HttpClient httpClient = HttpClients.createDefault();
                HttpPatch request = new HttpPatch("http://fhirtest.uhn.ca/baseDstu3/Patient/" + orig.getIdServer() + "?_format=json");
                request.setHeader("Content-type", "application/json-patch+json");
                request.setHeader("If-Match", orig.getVersionId());
                request.setEntity(entity);

                HttpResponse response = httpClient.execute(request);
                System.out.println(response.getStatusLine().getStatusCode());
            }else{
                System.out.println("keine Änderung");
            }
        }
        else{
            System.out.println("Patient nicht auf dem Server");
        }
    }


    public String getPatchJson() {

        String erg = new String(
                "[" +
                        "{" +
                        "\"op\": \"replace\"," +
                        "\"path\": \"/name/0/family\"," +
                        "  \"value\": \"Anne\"" +
                        " }" +
                        "]"


        );

        System.out.println(erg);
        return erg;
    }

    public  String postPatient(Patient patient) throws Exception{

        String erg = "fehler";
        String generatedUrl = "";
        String payload = this.getJsonPatient(patient);

        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://fhirtest.uhn.ca/baseDstu3/Patient");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
        Header[] headers = response.getAllHeaders();

        for (Header header : headers) {
            if(header.getName().equals("Location")){
                generatedUrl = header.getValue();
            }
            /*System.out.println("Key : " + header.getName()
                    + " ,Value : " + header.getValue());*/
        }
        if(response.getStatusLine().getStatusCode() == 201){
            erg = this.getIDfromInserted(generatedUrl);
            System.out.println("create ID: "+erg);
        }

       /* if(response.getStatusLine().getStatusCode() == 201){
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            JSONObject myObject = new JSONObject(result);

            return myObject.getString("id");
        }*/

      return erg;
    }

    public String getIDfromInserted(String url){
        //Key : Location ,Value : http://hapi.fhir.org/baseDstu3/Patient/1226852/_history/1
        int count = 0;
        String id = "";
        for(int i = 0; i < url.length(); i++){
            if(url.charAt(i) == '/'){
                count ++;
            }
            if(count == 5){
                id = id+""+url.charAt(i);
            }
        }
        String copyErg = id;
        copyErg = id.substring( 1, id.length());
        System.out.println("ID ohne Substring: "+id);
        return copyErg;
    }

    public  String postMedStatement(MedicationStatement medSt, Patient patient) throws Exception{
        String payload = this.JsonMesdikamentStatementPost(medSt, patient);
        String erg = "fehler";
        String generatedUrl = "";

        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://fhirtest.uhn.ca/baseDstu3/MedicationStatement");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if(header.getName().equals("Location")){
                generatedUrl = header.getValue();
            }
            /*System.out.println("Key : " + header.getName()
                    + " ,Value : " + header.getValue());*/
        }
        if(response.getStatusLine().getStatusCode() == 201){
            erg = this.getIDfromInserted(generatedUrl);
            System.out.println("create ID: "+erg);
        }


        System.out.println(response.getStatusLine().getStatusCode());
        return erg;
    }

    public  void removePatient(String id) throws Exception{
        String payload = "[" +
                "{" +
                "\"op\": \"remove\"," +
                "\"path\": \"/name/0/family\"," +
                "  \"value\": \"Anne\"" +
                " }" +
                "]";

        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);


        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://fhirtest.uhn.ca/baseDstu3/Patient/"+id);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }


    public void patchMedikamentStatement(MedicationStatement medicationStatement , Patient patient) throws Exception
    {
        String payloadMe = JsonMesdikamentStatementPost(medicationStatement,patient );

        StringEntity entityMe = new StringEntity(payloadMe);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPatch request = new HttpPatch("http://fhirtest.uhn.ca/baseDstu3/MedicationStatement?patient="+medicationStatement.getIdServer()+"&_format=json");
        request.setHeader("Content-type", "application/json-patch+json");
        request.setHeader("If-Match",medicationStatement.getVersionID());
        request.setEntity(entityMe);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    public String JsonMesdikamentStatementPost(MedicationStatement medicationStatement, Patient patient){

        String JsonMedStatment = String.format("{\n" +
                        "  \"resourceType\": \"MedicationStatement\",\n" +
                        "  \"status\": \"%s\",\n" +
                        "  \"medicationCodeableConcept\": {\n" +
                        "   \"coding\": [\n"+
                        "      {\n" +
                        "           \"code\": \"%s\"\n" +
                        "       }\n" +
                        "   ],\n"+
                        "    \"text\": \"%s\"\n" +
                        "  },\n" +
                        "  \"subject\": {\n" +
                        "    \"reference\": \"Patient/%s\"\n" +
                        "  },\n" +
                        "  \"taken\": \"%s\",\n" +
                        "  \"dosage\": [\n" +
                        "    {\n" +
                        "      \"text\": \"%s\",\n" +
                        "      \"doseQuantity\": {\n" +
                        "        \"unit\": \"%s\"\n" +
                        "          }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}", medicationStatement.getStatusStmt(), medicationStatement.getCode() ,medicationStatement.getName(),patient.getIdServer(), medicationStatement.getTaken(),medicationStatement.getDosage(),
                medicationStatement.getForm());
        return JsonMedStatment;
    }

    public  String getJsonPatient(Patient patient){
        String jsonOj = String.format(
                "{\n" +
                        "  \"resourceType\": \"Patient\",\n" +
                        "  \"active\": %b,\n" +
                        "  \"name\": [\n" +
                        "    {\n" +
                        "      \"use\": \"official\",\n" +
                        "      \"family\":\"%s\",\n"+
                        "      \"given\": [\n" +
                        "        \"%s\"\n"+
                        "      ]\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"telecom\": [\n" +
                        "    {\n" +
                        "      \"use\": \"home\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"system\": \"phone\",\n" +
                        "      \"value\": \"%s\",\n" +
                        "      \"use\": \"work\",\n" +
                        "      \"rank\": 1\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"gender\": \"%s\",\n" +
                        "  \"birthDate\": \"%s\",\n" +
                        "  \"deceasedBoolean\": %b,\n" +
                        "  \"address\": [\n" +
                        "    {\n" +
                        "      \"use\": \"home\",\n" +
                        "      \"type\": \"both\",\n" +
                        "      \"text\": \"%s\",\n" +
                        "      \"line\": [\n" +
                        "        \"%s\"\n" +
                        "      ],\n" +
                        "      \"city\": \"%s\",\n" +
                        "      \"postalCode\": \"%s\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"maritalStatus\": {\n" +
                        "    \"coding\": [\n" +
                        "      {\n" +
                        "        \"code\": \"M\",\n" +
                        "        \"display\": \"Married\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}",patient.getActive(),patient.getName(),patient.getVorname(),patient.getTelefon(),patient.getGender(),
                patient.getGeburtsdatum(),patient.getDeseased(), patient.getAdresseText(), patient.getStreet(),patient.getLocation(),patient.getPostalcode()
        );
        return jsonOj;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getJson() {
        String jsonOj = new String(
                "{\n" +
                        "  \"resourceType\": \"Patient\",\n" +
                        "  \"active\": true,\n" +
                        "  \"name\": [\n" +
                        "    {\n" +
                        "      \"use\": \"official\",\n" +
                        "      \"family\": \"Meyer\",\n" +
                        "      \"given\": [\n" +
                        "        \"Anna\",\n" +
                        "        \"Grazia\"\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"telecom\": [\n" +
                        "    {\n" +
                        "      \"use\": \"home\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"system\": \"phone\",\n" +
                        "      \"value\": \"(04) 45454 3343\",\n" +
                        "      \"use\": \"work\",\n" +
                        "      \"rank\": 1\n" +
                        "    },\n" +
                        "  ],\n" +
                        "  \"gender\": \"female\",\n" +
                        "  \"birthDate\": \"1980-08-13\",\n" +
                        "  \"deceasedBoolean\": false,\n" +
                        "  \"address\": [\n" +
                        "    {\n" +
                        "      \"use\": \"home\",\n" +
                        "      \"type\": \"both\",\n" +
                        "      \"text\": \"288 Carnia Milano, Lombardia, 35432\",\n" +
                        "      \"line\": [\n" +
                        "        \"534 Erewhon St\"\n" +
                        "      ],\n" +
                        "      \"city\": \"Milano\",\n" +
                        "      \"state\": \"Lombardia\",\n" +
                        "      \"postalCode\": \"35432\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"maritalStatus\": {\n" +
                        "    \"coding\": [\n" +
                        "      {\n" +
                        "        \"code\": \"M\",\n" +
                        "        \"display\": \"Married\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}"
        );

        return jsonOj;
    }

    public  String changeCheck(Patient orig , Patient copy){

        String erg ="[ \n";
        if(!orig.getName().equals("null") && orig.getName()!= null && orig.getName() != null && copy.getName()!= null && !orig.getName().equals(copy.getName())){
            String replaceName = String.format("{ \"op\": \"replace\",\"path\": \"/name/0/family\",\"value\": \"%s\"},\n",copy.getName());
            erg = erg + " "+ replaceName ;
        }
        if(! orig.getVollName().equals("null") && orig.getVollName()!= null && orig.getVorname() != null && copy.getVorname()!= null && !orig.getVorname().equals(copy.getVorname())){
            String replaceVorname = String.format("{ \"op\": \"replace\",\"path\": \"/name/0/given/0\",\"value\": \"%s\"},\n",copy.getVorname());
            erg = erg + " "+ replaceVorname ;
        }
        if(   orig.getActive() != copy.getActive()) {
            String replaceActive= String.format("{ \"op\": \"replace\",\"path\": \"/active\",\"value\": \"%b\"},\n", copy.getActive());
            erg = erg + " " + replaceActive;
        }
        if(!orig.getGeburtsdatumS().equals("null") && orig.getGeburtsdatum() != null && orig.getGeburtsdatumS() != null && copy.getGeburtsdatumS()!= null &&  !orig.getGeburtsdatumS().equals(copy.getGeburtsdatumS())){
            String replaceGebDatum= String.format("{ \"op\": \"replace\",\"path\": \"/birthDate\",\"value\": \"%s\"},\n", copy.getGeburtsdatumS());
            erg = erg + " " + replaceGebDatum;
        }

        if(!orig.getTelefon().equals("null") && orig.getTelefon() != null && orig.getTelefon() != copy.getTelefon()){
            String replaceTelefon= String.format("{ \"op\": \"replace\",\"path\": \"/telecom/1/value\",\"value\": \"%s\"},\n", copy.getTelefon());
            erg = erg + " " + replaceTelefon;
        }
        if( !orig.getGender().equals("null") && orig.getGender() != null && orig.getGender() != null && copy.getGender() != null &&  !orig.getGender().equals(copy.getGender())){
            String replaceGender= String.format("{ \"op\": \"replace\",\"path\": \"/gender\",\"value\": \"%s\"},\n", copy.getGender());
            erg = erg + " " + replaceGender;
        }
        if( !orig.getStreet().equals("null") && orig.getStreet() != null && orig.getStreet() != null && copy.getStreet()!= null && !orig.getStreet().equals(copy.getStreet())){
            String replaceStreet= String.format("{ \"op\": \"replace\",\"path\": \"/address/0/line/0\",\"value\": \"%s\"},\n", copy.getStreet());
            erg = erg + " " + replaceStreet;
        }
        if(  orig.getPostalcode() != 0 && orig.getPostalcode() != copy.getPostalcode()){
            String replacePostal= String.format("{ \"op\": \"replace\",\"path\": \"/address/0/postalCode\",\"value\": \"%d\"},\n", copy.getPostalcode());
            erg = erg + " " + replacePostal;
        }
        if( !orig.getLocation().equals("null") && orig.getLocation() != null && orig.getLocation() != null && copy.getLocation()!= null && !orig.getLocation().equals(copy.getLocation())){
            String replaceLocation= String.format("{ \"op\": \"replace\",\"path\": \"/address/0/city\",\"value\": \"%s\"},\n", copy.getLocation());
            erg = erg + " " + replaceLocation;
        }
        if(orig.getDeseased() != copy.getDeseased()){
            String replaceDeseased= String.format("{ \"op\": \"replace\",\"path\": \"/deceasedBoolean\",\"value\": \"%b\"},\n", copy.getDeseased());
            erg = erg + " " + replaceDeseased;
        }
        String copyErg = erg;
        copyErg= erg.substring( 0, erg.length() - 2 );

        copyErg = copyErg + "\n ]";

        return copyErg;

    }
}

