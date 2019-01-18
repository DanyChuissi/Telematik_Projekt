package src.Datenhaltung;

import ca.uhn.fhir.rest.api.Constants;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import java.net.HttpURLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hl7.fhir.dstu3.model.Patient;
import org.json.JSONObject;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KrankenhausPostRequest {

    public static void main(String[] args){

        try {
       patchPatient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Map<String, String> map = new HashMap<>();
        map.put("Content-type", "application/fhir+json");

        String pa = getPatchJson();

        try {
            sendPATCH("http://hapi.fhir.org/baseDstu3/Patient/1154588", pa, map);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /*public static void postJson() throws Exception{
        String payload = getJson();

        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://fhirtest.uhn.ca/baseDstu3/Patient");
        request.setEntity(entity);


        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }
*/
  /*  public static void putTest1() throws IOException {
        String data = getJson();
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut("http://hapi.fhir.org/baseDstu3/Patient/1154588");
            httpPut.setEntity(new StringEntity(data));

            System.out.println("Executing request " + httpPut.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpPut, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }*/

    /*public static void putTest2 () throws IOException {

                String data = getJson();
                Random random = new Random();
                URL url = new URL("http://hapi.fhir.org/baseDstu3/Patient/1154588/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(String.format(data, random.nextInt(30), random.nextInt(20)));
                osw.flush();
                osw.close();
                System.err.println(connection.getResponseCode());

    }
*/
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

    /*public static  void test3() throws IOException {
        HttpClient httpclient = HttpClientBuilder.create().build();
        JSONObject jsonData = getJsondata();
        // Prepare a request object
        HttpPatch req = null;
        req = new HttpPatch("http://hapi.fhir.org/baseDstu3/Patient/1154588?_format=json");
        req.setHeader("Content-type", "application/fhir+json");
        if (jsonData != null) {
            final StringEntity stringData = new StringEntity(jsonData.toString());
            req.setEntity(stringData);
        }
        HttpResponse httpResponse = httpclient.execute(req);
        System.out.println(httpResponse);
    }
*/
   /* public static JSONObject getJsondata (){
        JSONObject j = new JSONObject(
                "{" +
                "\"op\": \"remove\"," +
                "\"path\": \"/maritalStatus\"," +
                " }"

        );
        return j;
    }*/

   /* public static void test4 () throws IOException {
        URL url;
        url = new
                URL("http://fhirtest.uhn.ca/baseDstu3/Patient/1154588?_format=json");

        HttpURLConnection hc;
        hc = (HttpURLConnection) url.openConnection();
        hc.setDoInput(true);
        hc.setDoOutput(true);
        hc.setRequestMethod("POST");
        hc.setRequestProperty("X-HTTP-Method-Override", "PATCH");

        hc.setRequestProperty("Content-Type","application/hal+json");
        hc.setRequestProperty("Accept","application/hal+json");
        hc.setInstanceFollowRedirects(false);
        hc.setRequestProperty("If-Match","5967c28f42483b1c3dbedc54");
        hc.setUseCaches(false);
        hc.connect();

        byte[] opB = " {'active':false}".getBytes("UTF-8");
        OutputStream os = hc.getOutputStream();
        os.write(opB);
        System.out.println(hc.getResponseCode());
    }
*/


    /*public static void sendPATCH(String endpoint, String content, Map<String, String> headers) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(endpoint);
        for (String headerType : headers.keySet()) {
            httpPatch.setHeader(headerType, headers.get(headerType));
        }
        if (null != content) {
            HttpEntity httpEntity = new ByteArrayEntity(content.getBytes("UTF-8"));
            if (headers.get("Content-Type") == null) {
                httpPatch.setHeader("Content-Type", "application/json-patch+json");
                httpPatch.setHeader("If-Match","1");
            }
            httpPatch.setEntity(httpEntity);
        }
        HttpResponse httpResponse = httpClient.execute(httpPatch);
        System.out.println(httpResponse.getStatusLine().getStatusCode());
    }*/

   /* public static void test2Patch () {
        BufferedReader rd = null;
        StringBuffer result = new StringBuffer();
        String line = "";

        HttpClient httpclient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch("http://hapi.fhir.org/baseDstu3/Patient/1154588");

        JsonArrayBuilder Abuilder = Json.createArrayBuilder();
        JsonObjectBuilder oBuilder = Json.createObjectBuilder();
        for(int i=0;i<48;i++){
            Abuilder.add(i+1);
        }
        oBuilder.add("family", "Boris");
        oBuilder.add("values",Abuilder);
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("active", false);

        //JsonObject jo = Json.createObjectBuilder().add("puissance", Json.createObjectBuilder().add("curves",Json.createArrayBuilder().add(oBuilder))).build();
        JsonObject jo = job.build();


        try{
            //Execute and get the response.

            StringEntity params =new StringEntity(jo.toString());

            params.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPatch.setEntity(params);

            HttpResponse response = httpclient.execute(httpPatch);

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                result.append(line);
            }

        }catch(Exception e){

        }
    }*/

   /* public static void testPatchValidJson() throws Exception {
        String requestContents = "[ { \"op\": \"replace\", \"path\": \"/status\", \"value\":  \"false\" } ]";
        HttpPatch httpPatch = new HttpPatch("http://hapi.fhir.org/baseDstu3/Patient/1154588");
        httpPatch.setEntity(new StringEntity(requestContents, ContentType.parse(Constants.CT_JSON_PATCH)));
        HttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse status = httpClient.execute(httpPatch);
        System.out.println(status);
    }*/

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

