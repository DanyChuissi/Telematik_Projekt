package src.Datenhaltung;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpGet;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hl7.fhir.dstu3.model.Patient;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

public class KrankenhausPostRequest {
    private static DefaultHttpClient http = new DefaultHttpClient();
    public static void main(String[] args){
        try {
            postJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void postJson() throws Exception{
        String payload = getJson();

        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://fhirtest.uhn.ca/baseDstu3/Patient");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    public static void patchPatient() {

    }
    private HttpResponse httpGET(String url, int expectedStatus) throws Exception{
        HttpRequest request = new HttpGet(url);
        return httpSend(request, expectedStatus);
    }
    private HttpResponse httpSend(HttpRequest request, int expectedStatus) throws Exception{
        HttpResponse response = http.execute(getLocalhost(), request);

        return response;
    }
    private JsonNode getJSONNode(HttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response.getEntity().getContent());
        return node;
    }
    public void testUpdateEntity() throws Exception {
        String payload = "{" + "  \"Emails\":[" + "     \"Krista@example.com\"," + "     \"Krista@gmail.com\"" + "         ]" + "}";
        HttpPatch updateRequest = new HttpPatch("http://fhirtest.uhn.ca/baseDstu3/Patient/1154588");
        updateRequest.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
        httpSend(updateRequest, 204);
        HttpResponse response = httpGET("http://fhirtest.uhn.ca/baseDstu3/Patient/1154588", 200);
        JsonNode node = getJSONNode(response);

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
                        "        \"Maria\",\n" +
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

