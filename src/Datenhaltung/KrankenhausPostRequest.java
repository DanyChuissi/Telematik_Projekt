package src.Datenhaltung;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import src.Fachlogik.Patient;



public class KrankenhausPostRequest {

    public static void main(String[] args) {

        Patient p1 = new Patient();
        p1.setName("Dany");
        p1.setVorname("Martin");
        p1.setStreet("Meitnerweg 3");
       p1.setLocation("Dortmund");
       p1.setPostalcode(12345);

        Patient p2 = new Patient();
        p2.setName("Dany");
        p2.setVorname("Martin");
        p2.setStreet("Meitnerweg 3");
        p2.setLocation("Dortmund");
        p2.setPostalcode(12345);

       p2.setVorname("test");
       p2.setLocation("Essen");
      p2.setPostalcode(543221);

       changeCheck(p1, p2);


    }


    public static void patchPatient(String versionId, String payload) throws Exception {

        StringEntity entity = new StringEntity(payload);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPatch request = new HttpPatch("http://fhirtest.uhn.ca/baseDstu3/Patient/1154588?_format=json");
        request.setHeader("Content-type", "application/json-patch+json");
        request.setHeader("If-Match", versionId);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }


    public static String getPatchJson() {

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


    public static String getJson() {
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
        System.out.println(jsonOj);
        return jsonOj;
    }

    public static String changeCheck(Patient orig , Patient copy){

        String erg ="[ \n";
        if(orig.getName() != null && copy.getName()!= null && !orig.getName().equals(copy.getName())){
            String replaceName = String.format("{ \"op\": \"replace\",\"path\": \"/name/0/family\",\"value\": \"%s\"},\n",copy.getName());
            erg = erg + " "+ replaceName ;
        }
        if(orig.getVorname() != null && copy.getVorname()!= null && !orig.getVorname().equals(copy.getVorname())){
            String replaceVorname = String.format("{ \"op\": \"replace\",\"path\": \"/name/0/given/0\",\"value\": \"%s\"},\n",copy.getVorname());
            erg = erg + " "+ replaceVorname ;
        }
        if(  orig.getActive() != copy.getActive()) {
            String replaceActive= String.format("{ \"op\": \"replace\",\"path\": \"/active\",\"value\": \"%b\"},\n", copy.getActive());
            erg = erg + " " + replaceActive;
        }
        if(orig.getGeburtsdatumS() != null && copy.getGeburtsdatumS()!= null &&  !orig.getGeburtsdatumS().equals(copy.getGeburtsdatumS())){
            String replaceGebDatum= String.format("{ \"op\": \"replace\",\"path\": \"/birthDate\",\"value\": \"%s\"},\n", copy.getGeburtsdatumS());
            erg = erg + " " + replaceGebDatum;
        }
        if( orig.getTelefon() != null && copy.getTelefon()!= null && !orig.getTelefon().equals(copy.getTelefon())){
            String replaceTelefon= String.format("{ \"op\": \"replace\",\"path\": \"/telecom/1/value\",\"value\": \"%s\"},\n", copy.getTelefon());
            erg = erg + " " + replaceTelefon;
        }
        if(orig.getGender() != null && copy.getGender() != null &&  !orig.getGender().equals(copy.getGender())){
            String replaceGender= String.format("{ \"op\": \"replace\",\"path\": \"/gender\",\"value\": \"%s\"},\n", copy.getGender());
            erg = erg + " " + replaceGender;
        }
        if( orig.getStreet() != null && copy.getStreet()!= null && !orig.getStreet().equals(copy.getStreet())){
            String replaceStreet= String.format("{ \"op\": \"replace\",\"path\": \"/address/0/line/0\",\"value\": \"%s\"},\n", copy.getStreet());
            erg = erg + " " + replaceStreet;
        }
        if(orig.getPostalcode() != copy.getPostalcode()){
            String replacePostal= String.format("{ \"op\": \"replace\",\"path\": \"/address/0/postalCode\",\"value\": \"%d\"},\n", copy.getPostalcode());
            erg = erg + " " + replacePostal;
        }
        if( orig.getLocation() != null && copy.getLocation()!= null && !orig.getLocation().equals(copy.getLocation())){
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


        System.out.println(copyErg);
        return copyErg;

    }
}

