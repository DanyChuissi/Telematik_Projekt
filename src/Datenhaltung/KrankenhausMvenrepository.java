package src.Datenhaltung;


        import java.io.*;
        import java.net.URL;
        import java.net.URLConnection;
        import java.nio.charset.Charset;
        import java.sql.SQLException;
        import java.text.ParseException;
        import java.util.ArrayList;
        import java.util.List;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;
        import src.Fachlogik.MedicationStatement;
        import src.Fachlogik.Medikament;
        import src.Fachlogik.Patient;

public class KrankenhausMvenrepository {


    /**
     * Main Methode nur für (test).
     * @param args
     */
    public static void main(String[] args) {

        KrankenhausMvenrepository krSer = new KrankenhausMvenrepository();
        //JSONObject jsonObj = getJson();
       // System.out.println(jsonObj);



          krSer.testPatient("129272");
        //krSer.testMedStat("286595");
       // krSer.testPatientList("Chalmers");
        //krSer.testMedikamente();
        //AddMedDB();


    }
   /* public static JSONObject getJson(){
        JSONObject jsonOj = new JSONObject(
                "{\n" +
                        "  \"resourceType\": \"Patient\",\n" +
                        "  \"id\": \"129272\",\n" +
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
                        "  \"gender\": \"Female\",\n" +
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
                        "}\""


        "{"
           +" \"active\": true,"
             +"   \"name\": ["
           +" {"
            +"    \"use\": \"official\","
                +"        \"family\": \"Paul\","
                +"  \"given\": ["
                +"  \"Meyer\""
                +"          \"Jam\""
                +"  ]"
                +"      }"
                +" ],"
                +"   \"telecom\": ["
                +"   {"
                +"      \"use\": \"home\""
                +"   },"
                +"   {"
                +"    \"system\": \"phone\","
                +"        \"value\": \"(03) 6666 6473\","
                +"       \"use\": \"work\","
                +"      \"rank\": 1"
                +"    },"
                +"  ],"
                +"   \"gender\": \"male\","
                +"      \"birthDate\": \"2002-11-18\","
                +" \"deceasedBoolean\": false,"
                +" \"address\": ["
                +"    {"
                +"       \"use\": \"home\","
                +"         \"type\": \"both\","
                +"         \"text\": \"53 Erewhon St PeasantVille, Rainbow, Vic  3999\","
                +"         \"line\": ["
                +"     \"534 Erewhon St\""
                +"  ],"
                +"        \"city\": \"PleasantVille\","
                +"          \"district\": \"Rainbow\","
                +"         \"state\": \"Vic\","
                +"        \"postalCode\": \"3999\""
                +"  }"
                +" ],"
                +"       \"maritalStatus\": {"
                +"        \"coding\": ["
                +"      {"
                +"         \"code\": \"M\","
                +"             \"display\": \"Married\""
                +"    }"
                +"   ]"
                +"    }"
                +"   }"


        );

    System.out.println(jsonOj);
        return jsonOj;
    }*/
    public static void AddMedDB(){
        KrankenhausMvenrepository krSer = new KrankenhausMvenrepository();
        Krankenhaus kr = new Krankenhaus();
        kr.connect();

        List<Medikament> med = krSer.getMedikamentPatient();
        for(int i = 0; i < med.size(); i++){
            if(med.get(i).getCode() != null && med.get(i).getName() != null){
                try {
                    kr.addMedikamentDB(med.get(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            kr.connclose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test Methode für die Methode getPatientbyId
     * @param Id
     */
    public  void testPatient(String Id){
        Patient patient = getPatientbyId(Id);

        System.out.println("idServer: "+patient.getIdServer());
        System.out.println("Identifier: "+ patient.getIdentifier());
        System.out.println("active: "+patient.getActive());
        System.out.println("name: "+patient.getName());
        System.out.println("Vorname: "+patient.getVorname());
        System.out.println("telefon: "+patient.getTelefon());
        System.out.println("geburtsdatum: "+patient.getGeburtsdatum());
        System.out.println("deseaded: "+patient.getDeseased());
        System.out.println("Street: "+patient.getStreet());
        System.out.println("Location: "+patient.getLocation());
        System.out.println("PostalCode: "+patient.getPostalcode());
        System.out.println("gender: "+patient.getGender());
        System.out.println("MedList");
        testMedStat(patient.getIdServer());


    }

    /**
     * Test Methode für die Methode getPatientbyName
     * @param name
     */
    public  void testPatientList(String name){
        List<Patient> patient = getPatientbyName(name);
        for(int i = 0; i<patient.size();i++) {
            System.out.println("Patient: "+i+"--------------");
            System.out.println("idServer: " + patient.get(i).getIdServer());
            System.out.println("Identifier: " + patient.get(i).getIdentifier());
            System.out.println("active: " + patient.get(i).getActive());
            System.out.println("name: " + patient.get(i).getName());
            System.out.println("Vorname: " + patient.get(i).getVorname());
            System.out.println("telefon: " + patient.get(i).getTelefon());
            System.out.println("geburtsdatum: " + patient.get(i).getGeburtsdatum());
            System.out.println("deseaded: " + patient.get(i).getDeseased());
            System.out.println("Street: " + patient.get(i).getStreet());
            //System.out.println("Hausnummer: " + patient.get(i).getHousenumber());
            System.out.println("Location: " + patient.get(i).getLocation());
            System.out.println("PostalCode: " + patient.get(i).getPostalcode());
            System.out.println("gender: " + patient.get(i).getGender());
            System.out.println("MedList");
        }
        //testMedStat(patient.getIdServer());


    }

    /**
     * Test Methode für die Methode getMedicationStatementbypatient
     * @param Id
     */
    public void testMedStat(String Id){
        List<MedicationStatement> med = getMedikamentStatementbyPatient(Id);

        if(med!= null) {
            for (int i = 0; i < med.size(); i++) {
                System.out.println("MedicationStatement: " + i);
                System.out.println("Patient Id: " + med.get(i).getPatientId());
                System.out.println("taken: " + med.get(i).getTaken());
                System.out.println("status: " + med.get(i).getStatusStmt());
                System.out.println("code: " +med.get(i).getCode());
                System.out.println("dosage: " + med.get(i).getDosage());
                System.out.println("idServer: " + med.get(i).getIdServer());
                System.out.println("form: " + med.get(i).getForm());
                System.out.println("Name: " + med.get(i).getName());
                System.out.println("");
            }
        }
    }

    /**
     * test für die Methode getMedikament
     */
    public void testMedikamente(){
        List<Medikament> med = getMedikamentPatient();
        if(med!= null) {
            for (int i = 0; i < med.size(); i++) {
                System.out.println("Medication: " + i);
                System.out.println("med Server Id: " + med.get(i).getIdServer());
                System.out.println("name: " + med.get(i).getName());
                System.out.println("code: " + med.get(i).getCode());
                System.out.println("status: " +med.get(i).getStatusMed());

                System.out.println("-----------------------------");
            }
        }
    }

    /**
     *  Die Methode nimmt einen Name als String und sucht im Server nach alle Patiente mit deisem Name.
     *  Gitb ene Liste mit Patient zurüch falls Patiente gefunden sind. sonst null
     * @param name
     * @return List<Patient>
     * @throws JSONException
     */
    public List<Patient> getPatientbyName(String name)throws JSONException{
        name = loescheLeerzeichen(name);
        List<Patient> erg = new ArrayList<>();
        String url = "http://fhirtest.uhn.ca/baseDstu3/Patient?family="+name+"&_format=jsonn";
        String jsonString = callURLbyname(url);
        String jsonStringbyName;
        jsonStringbyName = jsonString.substring( 0, jsonString.length() - 1 );
            JSONArray jsonArray = new JSONArray(jsonStringbyName);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for (int i = 0; i < count; i++){
            JSONObject jo = jsonArray.getJSONObject((i));  // iterate through jsonArray
                Patient p = new Patient();

                if(jo.has("resource")) {
                    if(jo.getJSONObject("resource").has("meta") && jo.getJSONObject("resource").getJSONObject("meta").has("versionId")){
                        p.setVersionId(jo.getJSONObject("resource").getJSONObject("meta").getString("versionId"));
                    }
                    if (jo.getJSONObject("resource").has("id")) {
                            p.setIdServer(jo.getJSONObject("resource").getString("id"));
                    }
                    else{
                        return null;
                    }

                    if (jo.getJSONObject("resource").has("active")) {
                        p.setActive(jo.getJSONObject("resource").getBoolean("active")); // active
                    }
                    if (jo.getJSONObject("resource").has("name")) {
                        JSONArray nameArray = jo.getJSONObject("resource").getJSONArray("name");
                        if(nameArray.getJSONObject(0).has("family")) {
                            p.setName(nameArray.getJSONObject(0).getString("family")); //name
                        }
                        if(nameArray.getJSONObject(0).has("given")) {
                            JSONArray vornameArray = nameArray.getJSONObject(0).getJSONArray("given");
                            String vorn = "";
                            for(int j=0; i<vornameArray.length();i++){
                                String vorname = vornameArray.get(i).toString();
                                vorn = vorn + " "+ vorname;
                            }// vorname
                            p.setVorname(vorn);
                        }
                    }
                    if (jo.getJSONObject("resource").has("telecom")) {
                        JSONArray telefonArray = jo.getJSONObject("resource").getJSONArray("telecom");
                        if (telefonArray.length()>1 && telefonArray.getJSONObject(1).has("value")) {
                            p.setTelefon(telefonArray.getJSONObject(1).getString("value")); // telefon
                        }
                    }
                    if (jo.getJSONObject("resource").has("birthDate")) {
                        try {
                            p.setGeburtsdatum(p.stringToSqlDate(jo.getJSONObject("resource").getString("birthDate"))); // Geburtsdatum
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (jo.getJSONObject("resource").has("deceasedBoolean")) {
                        p.setDeseased(jo.getJSONObject("resource").getBoolean("deceasedBoolean")); // deaseaded
                    }
                    if (jo.getJSONObject("resource").has("address")) {
                        JSONArray adressearray = jo.getJSONObject("resource").getJSONArray("address");
                        if (adressearray.getJSONObject(0).has("line")) {
                            JSONArray streetarray = adressearray.getJSONObject(0).getJSONArray(("line"));
                            p.setStreet(streetarray.get(0).toString());// Streeet
                        }
                        if (adressearray.getJSONObject(0).has("city")) {
                            // p.setHousenumber(adressearray.getJSONObject(0).);
                            p.setLocation(adressearray.getJSONObject(0).getString("city")); // location
                        }
                        if (adressearray.getJSONObject(0).has("postalCode")) {
                            try {
                                p.setPostalcode(Integer.parseInt(adressearray.getJSONObject(0).getString("postalCode"))); // PostalCode
                            }catch (NumberFormatException e){
                                p.setPostalcode(0000);
                            }
                        }
                    }
                    if (jo.getJSONObject("resource").has("gender")) {
                        p.setGender(jo.getJSONObject("resource").getString("gender")); // gender
                    }
                    erg.add(p);
                }
            }

        return erg;
    }

    /**
     * Die Methode nimmt ein ID als String unf sucht im Server nach dem Patient mit diesem ID.
     * git den Patient zurück falls der Patient gefunden wird. sont null
     * @param Id
     * @return Patient
     * @throws JSONException
     * @throws RuntimeException
     */
    public Patient getPatientbyId(String Id) throws JSONException , RuntimeException{
        loescheLeerzeichen(Id);
        Patient p = new Patient();
        String url = "http://fhirtest.uhn.ca/baseDstu3/Patient/"+Id+"?_format=json";
        String jsonString = callURLbyId(url);
        System.out.println(jsonString);

        System.out.println("\n\njsonArray: " + jsonString);
            JSONObject jo = new JSONObject(jsonString);

            p.setIdServer(jo.getString("id"));

            if(jo.has("meta") && jo.getJSONObject("meta").has("versionId")){
                p.setVersionId(jo.getJSONObject("meta").getString("versionId"));
            }

            if(jo.has("active")) {
                p.setActive(jo.getBoolean("active")); // active
            }
            if(jo.has("name")) {
                JSONArray nameArray = jo.getJSONArray("name");
                p.setName(nameArray.getJSONObject(0).getString("family")); //name
                JSONArray vornameArray = nameArray.getJSONObject(0).getJSONArray("given");
                p.setVorname(vornameArray.toString());
                String vorn = "";
                for(int i=0; i<vornameArray.length();i++){
                    String vorname = vornameArray.get(i).toString();
                   vorn = vorn + " "+ vorname;
                }// vorname
                p.setVorname(vorn);
            }
            if(jo.has("telecom")){
                JSONArray telefonArray = jo.getJSONArray("telecom");
                if(telefonArray.getJSONObject(1).has("value")) {
                    p.setTelefon(telefonArray.getJSONObject(1).getString("value")); // telefon
                }
            }
            if(jo.has("birthDate")) {
                try {
                    p.setGeburtsdatum(p.stringToSqlDate(jo.getString("birthDate"))); // Geburtsdatum
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(jo.has("deceasedBoolean")) {
                p.setDeseased(jo.getBoolean("deceasedBoolean")); // deaseaded
            }
            if(jo.has("address")) {
                JSONArray adressearray = jo.getJSONArray("address");
                if(adressearray.getJSONObject(0).has("line")) {
                    JSONArray streetarray = adressearray.getJSONObject(0).getJSONArray(("line"));
                    p.setStreet(streetarray.get(0).toString());// Streeet
            }
                if(adressearray.getJSONObject(0).has("city")) {
                    // p.setHousenumber(adressearray.getJSONObject(0).);
                    p.setLocation(adressearray.getJSONObject(0).getString("city")); // location
                }
                if(adressearray.getJSONObject(0).has("postalCode")) {
                    p.setPostalcode(Integer.parseInt(adressearray.getJSONObject(0).getString("postalCode"))); // PostalCode
                }
            }
            if(jo.has("gender")) {
                p.setGender(jo.getString("gender")); // gender
            }
           // p.setMedicament(getMedikamentStatementbyPatient(p.getIdServer())); // MedS List

        return p;
    }

    /**
     *  Die Methode die die ID einem Patient als String und sucht im Server nach alle MedicamentStatemt für diesem Patient
     *  gitb Die liste der MedikamentStatemtn zurück sonst null falls keine gefunden wird
     * @param Id
     * @return List<MedicationStatement>
     */
    public List<MedicationStatement> getMedikamentStatementbyPatient(String Id){
        loescheLeerzeichen(Id);
        List<MedicationStatement> erg = new ArrayList<>();
        String url = "http://fhirtest.uhn.ca/baseDstu3/MedicationStatement?patient=" +Id+"&_format=json";
        String jsonString = callURLbyname(url);
        String jsonStringbyName;
        jsonStringbyName = jsonString.substring( 0, jsonString.length() - 1 );
        //System.out.println("\n\njsonArray: " + jsonStringbyName);

        try {
            JSONArray jsonArray = new JSONArray(jsonStringbyName);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for(int i=0 ; i< count; i++){   // iterate through jsonArray
                MedicationStatement med = new MedicationStatement();
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
               // System.out.println("jsonObject " + i + ": " + jsonObject);
                if(jsonObject.has("resource")) {
                    if(jsonObject.getJSONObject("resource").has("meta") && jsonObject.getJSONObject("resource").getJSONObject("meta").has("versionId")){
                        med.setVersionID(jsonObject.getJSONObject("resource").getJSONObject("meta").getString("versionId"));
                    }
                    if(jsonObject.getJSONObject("resource").has("taken")) {
                        med.setTaken(jsonObject.getJSONObject("resource").getString("taken"));   // taken
                    }
                    if(jsonObject.getJSONObject("resource").has("status")) {
                        med.setStatusStmt(jsonObject.getJSONObject("resource").getString("status")); // status
                    }
                    if(jsonObject.getJSONObject("resource").has("medicationCodeableConcept") && jsonObject.getJSONObject("resource").getJSONObject("medicationCodeableConcept").has("text")) {
                        med.setName(jsonObject.getJSONObject("resource").getJSONObject("medicationCodeableConcept").getString("text"));
                    }
                    if(jsonObject.getJSONObject("resource").has("medicationCodeableConcept") && jsonObject.getJSONObject("resource").getJSONObject("medicationCodeableConcept").has("coding")) {
                        JSONArray codearray = jsonObject.getJSONObject("resource").getJSONObject("medicationCodeableConcept").getJSONArray("coding");
                        if(codearray.getJSONObject(0).has("code")){
                            med.setCode(codearray.getJSONObject(0).getString("code"));
                        }
                    }
                    if(jsonObject.getJSONObject("resource").has("id")) {
                        med.setIdServer(jsonObject.getJSONObject("resource").getString("id")); // status
                    }
                    if(jsonObject.getJSONObject("resource").getJSONObject("subject").has("reference")) {
                        String StringId = jsonObject.getJSONObject("resource").getJSONObject("subject").getString("reference");
                        String StringId2 = StringId.substring(8);
                        int PatientId = Integer.parseInt(StringId2);
                        med.setPatientId(PatientId);
                    }
                    if(jsonObject.getJSONObject("resource").has("dosage")) {
                        JSONArray jsondosage = jsonObject.getJSONObject("resource").getJSONArray("dosage");
                        if(jsondosage.getJSONObject(0) != null && jsondosage.getJSONObject(0).has("text")) {
                            med.setDosage(jsondosage.getJSONObject(0).getString("text")); //Dosage
                        }
                        if(jsondosage.getJSONObject(0) != null && jsondosage.getJSONObject(0).has("dosageQuantity") && jsondosage.getJSONObject(0).getJSONObject("dosageQuantity").has("unit")) {
                            med.setForm(jsondosage.getJSONObject(0).getJSONObject("doseQuantity").getString("unit")); // Form
                        }
                    }

                }
                erg.add(med);
                // JSONArray jsonarr = jsonObject.getJSONArray("name");
                //  System.out.println(jsonObject.getString("id"));
                // System.out.println(jsonarr.getJSONObject(0).getString("family"));
            }

        } catch (JSONException e) {
            if(e.getMessage().contains("JSONArray text must start with '[' at 0")){
                System.out.println("Patient hat keien MedikamentStateemnt");
                return null;
            }
            else{
                e.printStackTrace();
                return null;
            }
        }
            return erg;
    }


    /**
     * Die Methode Gibt die Liste von Medikamnt die im Server gefunden werden.
     * @return
     */
    public List<Medikament> getMedikamentPatient(){
        List<Medikament> erge = new ArrayList<>();
        boolean doppel=false;
        String url = "http://fhirtest.uhn.ca/baseDstu3/Medication?_format=json";
        //String url = "http://fhirtest.uhn.ca/baseDstu3/Medication/?_formatl=json";
        String jsonString = callURLbyname(url);
        String jsonStringbyname;
        jsonStringbyname = jsonString.substring(0, jsonString.length() -1);
        // System.out.println(jsonStringbyname);
        try {
            JSONArray jsonArray = new JSONArray(jsonStringbyname);


            int anzahl = jsonArray.length();
            for(int i=0 ; i<anzahl; i++) {

                //  JSONObject jsonObject = new JSONObject();
                Medikament medi = new Medikament();
                JSONObject jsonobj_1 = (JSONObject)jsonArray.get(i);
                if (jsonobj_1.has("resource")) {
                    if (jsonobj_1.getJSONObject("resource").has("code") && jsonobj_1.getJSONObject("resource").getJSONObject("code").has("coding")) {
                        //System.out.println(jsonobj_1.getJSONObject("resource").getJSONObject("code").has("coding"));
                        JSONArray codearray = jsonobj_1.getJSONObject("resource").getJSONObject("code").getJSONArray("coding");
                        if (codearray.getJSONObject(0).has("code")) {
                            medi.setCode(codearray.getJSONObject(0).getString("code"));
                            doppel = pruefedoppel(erge, medi.getCode());
                        }
                    }

                    if(jsonobj_1.getJSONObject("resource").has("code") && jsonobj_1.getJSONObject("resource").getJSONObject("code").has("text")) {
                        medi.setName(jsonobj_1.getJSONObject("resource").getJSONObject("code").getString("text"));
                    }else if (jsonobj_1.getJSONObject("resource").has("form") && jsonobj_1.getJSONObject("resource").getJSONObject("form").has("coding")) {
                        // System.out.println(jsonobj_1.getJSONObject("resource").has("form") && jsonobj_1.getJSONObject("resource").getJSONObject("form").has("display"));
                        JSONArray codearray = jsonobj_1.getJSONObject("resource").getJSONObject("form").getJSONArray("coding");
                        if (codearray.getJSONObject(0).has("form")) {
                            medi.setForm(codearray.getJSONObject(0).getString("display"));
                        }
                    }

                    if (jsonobj_1.getJSONObject("resource").has("id")) {
                        medi.setIdServer(jsonobj_1.getJSONObject("resource").getString("id"));
                    }

                    if (jsonobj_1.getJSONObject("resource").getJSONObject("text").has("status")) {
                        medi.setStatusMed(jsonobj_1.getJSONObject("resource").getJSONObject("text").getString("status"));
                    }

                }
                if (!doppel) {
                    erge.add(medi);
                }



            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return erge;
    }


    /**
     * Die methode nimmt die Url gibt ein JSon als String zurüch
     * wird benutzt nur für ergebnisse die mehr als einen Ojekt enthalten
     * @param myURL
     * @return
     */
    public static String callURLbyname(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    int count=0;
                    sb.append("[");
                    while ((cp = bufferedReader.read()) != -1) {
                        if(count != 2 && (char)cp == '['){
                            count++;
                        } else if (count == 2) {
                            sb.append((char) cp);
                        }
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        return sb.toString();
    }

    /**
     * Die methode nimmt die Url gibt ein JSon als String zurüch
     * wird benutzt nur für ergebnisse die ein Objekt enthalten
     * @param myURL
     * @return
     */
    public static String callURLbyId(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                            sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        return sb.toString();
    }

    public static String loescheLeerzeichen(String name){
        String erg = "";
        for(int i = 0; i < name.length(); i++){
            if(name.charAt(i) != ' ' ){
                erg = erg+ ""+ name.charAt(i);
            }
        }
        return erg;
    }

    public static boolean pruefedoppel(List<Medikament> list ,String code){
        boolean b = false;
        for(int i = 0; i < list.size(); i++){
            if(code.equals(list.get(i).getCode())){
                return true;
            }
        }
        return false;
    }
}