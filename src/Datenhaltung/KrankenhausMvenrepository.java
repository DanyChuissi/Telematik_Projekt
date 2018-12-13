package src.Datenhaltung;


        import java.io.*;
        import java.net.URL;
        import java.net.URLConnection;
        import java.nio.charset.Charset;
        import java.text.ParseException;
        import java.util.ArrayList;
        import java.util.List;


        import org.hl7.fhir.dstu3.model.Medication;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;
        import src.Fachlogik.MedicationStatement;
        import src.Fachlogik.Medikament;
        import src.Fachlogik.Patient;

public class KrankenhausMvenrepository {

    public static void main(String[] args) {


        //testPatient(286595);
        //testMedStat(286595);

       // String jsonStringbyId = callURLbyname("http://hapi.fhir.org/baseDstu3/Medication?_format=json");


        //löschen le } a la fin du String a utilisé seulekemnt avec le Call by NAme
      //  String jsonStringbyName;
        //  jsonStringbyName = jsonStringbyId.substring( 0, jsonStringbyId.length() - 1 );
        //System.out.println("\n\njsonString: " + jsonString1);

// Replace this try catch block for all below subsequent examples
       /*try {
            JSONObject jo = new JSONObject("["+jsonString+"]");
            JSONArray jsonArray = new JSONArray(jo);
            System.out.println("\n\njsonArray: " + jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

      /* try {
            JSONArray jsonArray = new JSONArray(jsonStringbyName);

            JSONObject jsonObj= new JSONObject();  // these 4 files add jsonObject to jsonArray

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for(int i=0 ; i< count; i++){   // iterate through jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                System.out.println("jsonObject " + i + ": " + jsonObject);
               // JSONArray jsonarr = jsonObject.getJSONArray("name");
              //  System.out.println(jsonObject.getString("id"));
               // System.out.println(jsonarr.getJSONObject(0).getString("family"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    public  void testPatient(int Id){
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
        System.out.println("Hausnummer: "+patient.getHousenumber());
        System.out.println("Location: "+patient.getLocation());
        System.out.println("PostalCode: "+patient.getPostalcode());
        System.out.println("gender: "+patient.getGender());
        System.out.println("MedList");
        //testMedStat(patient.getIdServer());


    }
    public void testMedStat(int Id){
        List<MedicationStatement> med = getMedikamentStatementbyPatient(Id);

        for(int i = 0; i < med.size(); i++){
            System.out.println("MedicationStatement: " + i);
            System.out.println("Patient Id: " +med.get(i).getPatientId());
            System.out.println("taken: "+med.get(i).getTaken());
            System.out.println("status: "+med.get(i).getStatusStmt());
            System.out.println("dosage: "+med.get(i).getDosage());
            System.out.println("idServer: "+med.get(i).getIdServer());
            System.out.println("form: "+med.get(i).getForm());
            System.out.println("Name: "+med.get(i).getName());
            System.out.println("");
        }
    }

    //Die Methode sucht die Patienten im Server nach Nane
    public List<Patient> getPatientbyName(String name){
            return new ArrayList<>();
    }

    // Die Methodee sucht die PAtienten im Server nach ID
    public Patient getPatientbyId(int Id){
        Patient p = new Patient();
        String url = "http://fhirtest.uhn.ca/baseDstu3/Patient/"+Id+"?_format=json";
        String jsonString = callURLbyId(url);

        //System.out.println("\n\njsonArray: " + jsonString);
        try {
            JSONObject jo = new JSONObject(jsonString);
            //JSONArray jsonArray = new JSONArray(jo);
           // System.out.println("\n\njsonArray: " + jo);
            int id= Integer.parseInt(jo.getString("id")); // IdServer
            p.setIdServer(id);

            if(jo.has("active")) {
                p.setActive(jo.getBoolean("active")); // active
            }
            if(jo.has("name")) {
                JSONArray nameArray = jo.getJSONArray("name");
                p.setName(nameArray.getJSONObject(0).getString("family")); //name
                JSONArray vornameArray = nameArray.getJSONObject(0).getJSONArray("given");
                p.setVorname(vornameArray.toString());
                /*for(int i=0; i<vornameArray.length();i++){
                    JSONObject vj = vornameArray.getJSONObject(i);
                    String vorname = vj.toString();
                    vorn = vorn + " "+ vorname;
                }// vorname
                p.setVorname(vorn);*/
            }
            if(jo.has("telecom")) {
                JSONArray telefonArray = jo.getJSONArray("telecom");
                if(jo.has("value")) {
                    p.setTelefon(telefonArray.getJSONObject(1).getString("value")); // telefon
                }
            }
            if(jo.has("birthday")) {
                p.setGeburtsdatum(p.stringToSqlDate(jo.getString("birthday"))); // Geburtsdatum
            }
            if(jo.has("deceasedBoolean")) {
                p.setDeseased(Boolean.valueOf(jo.getString("deceasedBoolean"))); // deaseaded
            }
            if(jo.has("adress")) {
                JSONArray adressearray = jo.getJSONArray("address");
                if(jo.has("line")) {
                    p.setStreet(adressearray.getJSONObject(0).getJSONArray("line").getJSONObject(0).toString());// Streeet
                }
                if(jo.has("city")) {
                    // p.setHousenumber(adressearray.getJSONObject(0).);
                    p.setLocation(adressearray.getJSONObject(0).getString("city")); // location
                }
                if(jo.has("postalcode")) {
                    p.setPostalcode(Integer.parseInt(adressearray.getJSONObject(0).getString("postalCode"))); // PostalCode
                }
            }
            if(jo.has("gender")) {
                p.setGender(jo.getString("gender")); // gender
            }
            p.setMedicament(getMedikamentStatementbyPatient(p.getIdServer())); // MedS List
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return p;
    }

    // Die Methode sucht die MedicamentStetement von einem Patienten
    public List<MedicationStatement> getMedikamentStatementbyPatient(int Id){
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
                    if(jsonObject.getJSONObject("resource").has("taken")) {
                        med.setTaken(jsonObject.getJSONObject("resource").getString("taken"));   // taken
                    }
                    if(jsonObject.getJSONObject("resource").has("status")) {
                        med.setStatusStmt(jsonObject.getJSONObject("resource").getString("status")); // status
                    }
                    if(jsonObject.getJSONObject("resource").has("medicationCodeableConcept") && jsonObject.getJSONObject("resource").getJSONObject("medicationCodeableConcept").has("text")) {
                        med.setName(jsonObject.getJSONObject("resource").getJSONObject("medicationCodeableConcept").getString("text"));
                    }
                    if(jsonObject.getJSONObject("resource").has("id")) {
                        med.setIdServer(jsonObject.getJSONObject("resource").getInt("id")); // status
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
            e.printStackTrace();
        }
            return erg;
    }



    // Die Methode gibt die Liste von Alle Medikamente vom Server al Liste zurpck
    public List<Medikament> getMedinakentList(){

        List<Medikament> erg = new ArrayList<>();
        String url = "http://hapi.fhir.org/baseDstu3/Medication?_format=json";
        String jsonString = callURLbyname(url);
        String jsonStringbyName;
        jsonStringbyName = jsonString.substring( 0, jsonString.length() - 1 );
        //System.out.println("\n\njsonArray: " + jsonStringbyName);

        try {
            JSONArray jsonArray = new JSONArray(jsonStringbyName);

            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for(int i=0 ; i< count; i++){   // iterate through jsonArray
               Medikament med = new Medikament();
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                // System.out.println("jsonObject " + i + ": " + jsonObject);
                // JSONArray jsonarr = jsonObject.getJSONArray("name");
                //  System.out.println(jsonObject.getString("id"));
                // System.out.println(jsonarr.getJSONObject(0).getString("family"));


                erg.add(med);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return erg;
    }


    // Die Methode soll bei suchen im Server wo eine Liste zurückgegeben werden soll
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

    // Die Methode soll bei der Suche im Server wo nur ein Element zurück gegeben werden soll
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
}