package src.Datenhaltung;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class KrankenhausPostRequest {

    public static void main(String[] args){

        try {
       patchPatient();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void patchPatient() throws Exception {
        String payload = getPatchJson();

        StringEntity entity = new StringEntity(payload);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPatch request = new HttpPatch("http://fhirtest.uhn.ca/baseDstu3/Patient/1154588?_format=json");
        request.setHeader("Content-type", "application/json-patch+json");
        request.setHeader("If-Match","2");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }




    public static String getPatchJson(){
        /*String erg = new String(
                "[" +
                        "{" +
                        "\"op\": \"remove\"," +
                        "\"path\": \"/active\"" +
                        " }," +
                        " {" +
                        "  \"op\": \"add\"," +
                        "  \"path\": \"/active\"," +
                        "  \"value\": \"false\"" +
                        " }" +
                        "]"
        );*/
        String erg = new String(
"["+
                        "{" +
                        "\"op\": \"replace\"," +
                        "\"path\": \"/name/0/family\"," +
                        "  \"value\": \"Anne\"" +
                        " }"+
        "]"



        );

         System.out.println(erg);
        return erg;
    }


    public static String getJson(){
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

}

