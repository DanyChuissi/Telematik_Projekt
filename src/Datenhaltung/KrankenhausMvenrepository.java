package src.Datenhaltung;


        import java.io.*;
        import java.net.URL;
        import java.net.URLConnection;
        import java.nio.charset.Charset;
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

    public static void main(String[] args) {

        String jsonStringbyId = callURLbyname("http://hapi.fhir.org/baseDstu3/Medication?_format=json");


        //löschen le } a la fin du String a utilisé seulekemnt avec le Call by NAme
        String jsonStringbyName;
        jsonStringbyName = jsonStringbyId.substring( 0, jsonStringbyId.length() - 1 );
        //System.out.println("\n\njsonString: " + jsonString1);

// Replace this try catch block for all below subsequent examples
       /*try {
            JSONObject jo = new JSONObject("["+jsonString+"]");
            JSONArray jsonArray = new JSONArray(jo);
            System.out.println("\n\njsonArray: " + jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

       try {
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
        }
    }
    //Die Methode sucht die Patienten im Server nach Nane
    public List<Patient> getPatientbyName(String URL){
            return new ArrayList<>();
    }

    // Die Methodee sucht die PAtienten im Server nach ID
    public Patient getPatientbyId(String URL){
        return new Patient();
    }

    // Die Methode sucht die MedicamentStetement von einem Patienten
    public List<MedicationStatement> getMedikamentStatementbyPatient(String Url){
        return new ArrayList<>();
    }
    // Die Methode gibt die Liste von Alle Medikamente vom Server al Liste zurpck
    public List<Medikament> getMedinakentList(){
        return new ArrayList<>();
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