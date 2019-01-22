package src.PatientenGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Medikament;
import src.Fachlogik.Patient;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;


public class PatientenDatenGUI extends Stage {
    private Stage primaryStage;
    private Controller control;
    private Patient patient;
    private Patient origPatient;
    private Date today = new Date();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public PatientenDatenGUI(Stage primaryStage, Controller control, Patient patient) {
        this.primaryStage = primaryStage;
        this.control = control;
        this.patient = patient;
        this.origPatient = patient;
    }

    public void showView() {
        GridPane grid = new GridPane();
        Patient neu= new Patient();
        grid.setPadding(new Insets(10.0));
        grid.setVgap(5.0);
        grid.setHgap(10.0);

        Label name = new Label("Name");
        Label vorname = new Label("Vorname");
        Label gebDatum = new Label("Geburstdatum");
        Label gender = new Label("Gender");
        Label tel = new Label("Telefon");
        Label deseaded = new Label("Gestorben?");
        Label street = new Label("HausNr, Straße");
        Label postalcode = new Label("Postleitzahl");
        Label location = new Label("Stadt");


        TextField tfname = new TextField();
        TextField tfvor = new TextField();
        DatePicker tfgebD = new DatePicker(LocalDate.now());
        TextField tftel = new TextField("0");
        TextField tfstreet = new TextField("");
        TextField tfhaussn = new TextField("0");
        TextField tfpostalc = new TextField("0");
        TextField tflocation = new TextField();

        Button save = new Button("Änderungen Speichern");
        Button abbrechen = new Button("Abbrechen");
        Button saveServer = new Button();

        HBox hb = new HBox();
        hb.setSpacing(10.0);
        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(save, abbrechen);

        grid.add(name, 0, 0);
        grid.add(vorname, 0, 1);
        grid.add(gebDatum, 0, 2);
        grid.add(tel, 0, 3);
        grid.add(gender, 0, 4);
        grid.add(street, 0, 5);
        grid.add(postalcode, 0, 6);
        grid.add(location, 0, 7);
        grid.add(deseaded, 0, 8);


        grid.add(tfname, 1, 0);
        grid.add(tfvor, 1, 1);
        grid.add(tfgebD, 1, 2);
        grid.add(tftel, 1, 3);
        ComboBox<String> genderComboBox = new ComboBox<String>();
        genderComboBox.getItems().addAll("male", "female", "other", "unknown");
        grid.add(genderComboBox, 1, 4);
        grid.add(tfstreet, 1, 5);
        grid.add(tfpostalc, 1, 6);
        grid.add(tflocation, 1, 7);

        CheckBox ja = new CheckBox("Ja");
        CheckBox nein = new CheckBox("Nein");
        HBox hbCheck = new HBox();
        hbCheck.setSpacing(10);
        hbCheck.getChildren().addAll(ja, nein);

        grid.add(hbCheck, 1, 8);
        grid.add(hb, 1, 9);

        // Die Methode wird aufgerufen wenn es auf "neuPAtient geclikt wird oder auf einen Patient in der Patienten List
        //Hier wird geprüft, ob es auf einen Patient geclickt worden ist
        if (patient != null) {
            if(patient.getIdentifier() != 0){
                // Patient lokal, wird nur Mofifiziert

            }
            else{
                //Patient vom Server Kann neu gespeichert werden
            }
            tfname.setText(patient.getName());
            tfvor.setText(patient.getVorname());
            if (patient.getGeburtsdatum() != null) {
                LocalDate localDate = patient.getGeburtsdatum().toLocalDate();
                tfgebD.setValue(localDate);
            }
            tftel.setText("" + patient.getTelefon());
            genderComboBox.setValue(patient.getGender());
            tfstreet.setText(patient.getStreet());
            tfpostalc.setText("" + patient.getPostalcode());
            tflocation.setText(patient.getLocation());

            if (patient.getDeseased()) {
                ja.setSelected(true);
            } else {
                nein.setSelected(true);
            }
            if (patient.getIdentifier() == 0) { // Falls Patient vom Server ist
                if(patient.getIdentifier() == 0){
                    save = new Button("Patient im DB Speichern");

                    save.setOnAction(e -> {
                        if (!tfname.getText().isEmpty() && !tfvor.getText().isEmpty()) {
                            try {
                                int plz = Integer.parseInt(tfpostalc.getText());
                                boolean speicherungOK = true;
                                patient.setName(tfname.getText());
                                patient.setVorname(tfvor.getText());
                                patient.setGeburtsdatum(patient.stringToSqlDate(tfgebD.getValue().toString()));
                                patient.setGeburtsdatumS(tfgebD.getValue().toString());
                                patient.setTelefon(tftel.getText());
                                patient.setGender(genderComboBox.getValue());
                                patient.setStreet(tfstreet.getText());
                                patient.setPostalcode(plz);
                                patient.setLocation(tflocation.getText());
                                if (ja.isSelected())
                                    patient.setDeseased(true);
                                if (patient.getIdentifier() != 0) {
                                    control.updatePatient(patient);
                                } else {
                                    speicherungOK =AddPAtientVomServerInDB(primaryStage,control,patient);
                                }
                                if(!speicherungOK){
                                    Formatter formatter = new Formatter();
                                    formatter.format("Patient schon Vorhanden");

                                    JOptionPane.showMessageDialog(null, formatter.toString());
                                    formatter.close();
                                    close();
                                }
                                else {
                                    control.setPatTableview();
                                    close();
                                }
                            } catch (ParseException exx) {
                                Formatter formatter = new Formatter();
                                formatter.format("Bitte gültiges Datum-Format eingeben :yyyy-MM-dd");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            } catch (NumberFormatException ex) {
                                Formatter formatter = new Formatter();
                                formatter.format("Bitte gültiger Zahl eingeben");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();

                            } catch (SQLException e1) {
                                Formatter formatter = new Formatter();
                                formatter.format("Fehler beim Updaten");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            }
                        } else {
                            Formatter formatter = new Formatter();
                            formatter.format("Name oder Vorname ist Leer");

                            JOptionPane.showMessageDialog(null, formatter.toString());
                            formatter.close();
                        }


                    });
                }

                saveServer.setText("Update Daten");
                hb.getChildren().setAll(save, abbrechen);
                grid.add(saveServer, 0, 9);
                saveServer.setOnAction(e -> {
                    // Patient im Server Updaten mit Patch Methode


                    neu.setIdServer(patient.getIdServer());
                    neu.setVersionId(patient.getVersionId());
                    neu.setMedicament(patient.getMedicament());
                    neu.setIdentifier(patient.getIdentifier());
                    neu.setAufnahmeDatum(patient.getAufnahmeDatum());
                    neu.setEntlassungsDatum(patient.getEntlassungsDatum());
                    neu.setEnlassungStatus(patient.getEntlassungStatus());

                    int plz = Integer.parseInt(tfpostalc.getText());
                    boolean speicherungOK = true;
                    neu.setName(tfname.getText());
                    neu.setVorname(tfvor.getText());
                    try {
                        neu.setGeburtsdatum(neu.stringToSqlDate(tfgebD.getValue().toString()));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    neu.setGeburtsdatumS(tfgebD.getValue().toString());
                    neu.setTelefon(tftel.getText());
                    neu.setGender(genderComboBox.getValue());
                    neu.setStreet(tfstreet.getText());
                    neu.setPostalcode(plz);
                    neu.setLocation(tflocation.getText());
                    //neu.setGeburtsdatum(patient.getGeburtsdatum());

                    if (ja.isSelected())
                        neu.setDeseased(true);
                    try {
                        int  anzahlMed =  this.patientUpdaten( patient , neu);
                            control.updatePatient(neu);
                            control.setPatTableview();
                            String text = "Patient wurde im Server modifiziert";
                            if(anzahlMed > 0) {
                                text = text + " " + "und " + anzahlMed + " Medication wurden hochgeladen";
                            }
                            if(anzahlMed > 0){
                                Formatter formatter = new Formatter();
                                formatter.format(text);

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            }

                            close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });

            }
            else{ // falls Patient Lokal ist
                    saveServer.setText("Daten Hochladen");
                    grid.add(saveServer, 0, 9);

                neu.setIdServer(patient.getIdServer());
                neu.setVersionId(patient.getVersionId());
                neu.setMedicament(patient.getMedicament());
                neu.setIdentifier(patient.getIdentifier());


                int plz = Integer.parseInt(tfpostalc.getText());
                boolean speicherungOK = true;
                neu.setName(tfname.getText());
                neu.setVorname(tfvor.getText());
                try {
                    neu.setGeburtsdatum(neu.stringToSqlDate(tfgebD.getValue().toString()));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                neu.setGeburtsdatumS(tfgebD.getValue().toString());
                neu.setTelefon(tftel.getText());
                neu.setGender(genderComboBox.getValue());
                neu.setStreet(tfstreet.getText());
                neu.setPostalcode(plz);
                neu.setLocation(tflocation.getText());
                neu.setGeburtsdatum(patient.getGeburtsdatum());
                neu.setAufnahmeDatum(patient.getAufnahmeDatum());
                neu.setEntlassungsDatum(patient.getEntlassungsDatum());
                neu.setEnlassungStatus(patient.getEntlassungStatus());
                neu.setDeseased(patient.getDeseased());

                if (ja.isSelected()) {
                    neu.setDeseased(true);
                }

                    // Patient Posten
                    saveServer.setOnAction(e -> {
                        try {
                            int anzahlmed = this.patientPosten(neu);
                            control.setPatTableview();
                            close();

                            Formatter formatter = new Formatter();
                            formatter.format("Patient " + neu.getVollName() + " mit " + anzahlmed + " Medikation im Server gespeichert");

                            JOptionPane.showMessageDialog(null, formatter.toString());
                            formatter.close();
                            close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            close();
                        }
                    });
                    close();
                   save.setOnAction(e -> {
                       neu.setIdServer(patient.getIdServer());
                       neu.setVersionId(patient.getVersionId());
                       neu.setMedicament(patient.getMedicament());
                       neu.setIdentifier(patient.getIdentifier());


                       int plzz = Integer.parseInt(tfpostalc.getText());

                       neu.setName(tfname.getText());
                       neu.setVorname(tfvor.getText());
                       try {
                           neu.setGeburtsdatum(neu.stringToSqlDate(tfgebD.getValue().toString()));
                       } catch (ParseException e1) {
                           e1.printStackTrace();
                       }
                       neu.setGeburtsdatumS(tfgebD.getValue().toString());
                       neu.setTelefon(tftel.getText());
                       neu.setGender(genderComboBox.getValue());
                       neu.setStreet(tfstreet.getText());
                       neu.setPostalcode(plzz);
                       neu.setLocation(tflocation.getText());
                       neu.setGeburtsdatum(patient.getGeburtsdatum());
                       neu.setAufnahmeDatum(patient.getAufnahmeDatum());
                       neu.setEntlassungsDatum(patient.getEntlassungsDatum());
                       neu.setEnlassungStatus(patient.getEntlassungStatus());
                       neu.setDeseased(patient.getDeseased());

                       if (ja.isSelected()) {
                           neu.setDeseased(true);
                       }


                       try {
                          control.updatePatient(neu);
                            control.setPatTableview();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        close();
              });
                }





        }
        // Falls der Patient == null wird ein neuen Patient erstellt
        else {
            save.setText("Patient im DB Speichern");
            save.setOnAction(e -> {
                patient = new Patient();
                if (!tfname.getText().isEmpty() && !tfvor.getText().isEmpty()) {
                    try {
                        //int tl = Integer.parseInt(tftel.getText());

                        int plz = Integer.parseInt(tfpostalc.getText());

                        patient.setName(tfname.getText());
                        patient.setVorname(tfvor.getText());
                        // System.out.println(tfgebD.getValue().toString());
                        //System.out.println(df.format(today));
                        patient.setGeburtsdatum(patient.stringToSqlDate(tfgebD.getValue().toString()));
                        patient.setGeburtsdatumS(tfgebD.getValue().toString());
                        patient.setTelefon(tftel.getText());
                        patient.setGender(genderComboBox.getValue());
                        patient.setStreet(tfstreet.getText());
                        patient.setPostalcode(plz);
                        patient.setLocation(tflocation.getText());
                        patient.setAufnahmeDatum(patient.stringToSqlDate(df.format(today)));
                        patient.setAufnahmeDatumS(df.format(today));

                        if (ja.isSelected())
                            patient.setDeseased(true);
                        try {
                            boolean b = control.addPatientDB(patient);
                            if (!b) {
                                Formatter formatter = new Formatter();
                                formatter.format("Patient schon Vorhanden");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();

                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        close();
                    } catch (ParseException exx) {
                        Formatter formatter = new Formatter();
                        formatter.format("Bitte gültiges Datum-Format eingeben: yyyy-MM-dd");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    } catch (NumberFormatException ex) {
                        Formatter formatter = new Formatter();
                        formatter.format("Bitte gültiger Zahl eingeben");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }
                } else {
                    Formatter formatter = new Formatter();
                    formatter.format("Name oder Vorname ist Leer");

                    JOptionPane.showMessageDialog(null, formatter.toString());
                    formatter.close();
                }


            });

        }
        abbrechen.setOnAction(e -> {
            close();
        });
        GridPane.setHgrow(tfname, Priority.ALWAYS);
        GridPane.setHgrow(tfvor, Priority.NEVER);
        GridPane.setHgrow(tfgebD, Priority.ALWAYS);
        GridPane.setHgrow(name, Priority.ALWAYS);
        GridPane.setHgrow(hb, Priority.ALWAYS);
        //GridPane.setHalignment(b1, HPos.CENTER);
        GridPane.setValignment(save, VPos.BOTTOM);
        Scene scene = new Scene(grid);
        setTitle("Patienten Daten Verwaltung");
        setScene(scene);
        initOwner(primaryStage);
        initModality(Modality.WINDOW_MODAL);
        show();

    }


    /*public static String inverseDate(String date) {
        System.out.println("eingegeben date" + date);
        String erg = "";
        String day = date.charAt(8) + "" + date.charAt(9);
        String month = date.charAt(6) + "" + date.charAt(7);
        String year = date.charAt(0) + "" + date.charAt(1) + "" + date.charAt(2) + "" + date.charAt(3);
        ;
        erg = day + "-" + month + "-" + year;
        System.out.println("Datum test 2 " + erg);
        return erg;
    }
*/

    /**
     * die Methode prüft ob der Patient Medikament Statement hat, falls das der Fall ist, werden die Medikamente Stateemtn der Patient
     * in DB eingefugt.
     * Fall der Medikament im DB nicht vorhanden ist, wird der Medikament zuerst im DB ein gefügt und dann
     * der Medikament Statement
     * @param primaryStage
     * @param control
     * @param patient
     * @return Boolean
     * @throws SQLException
     */
    public static boolean AddPAtientVomServerInDB(Stage primaryStage, Controller control, Patient patient) throws SQLException {
        List<MedicationStatement> medSList = control.getMedSteServer(patient);
        List<Medikament> medicaments = control.getMedikamentList();
        //Medikament med2 = null;
        boolean b = control.addPatientDB(patient);
        if(b) {
            Patient neuPatient = null;
            try {
                neuPatient = control.getLastInsertedDB();
            } catch (NichtErlaubException e) {
                e.printStackTrace();
            }
            if (medSList != null && medSList.size() > 0) {
                for (int i = 0; i < medSList.size(); i++) {

                    Medikament med = null;

                    for (int j = 0; j < medicaments.size(); j++) {
                        if (medSList.get(i).getCode() != null && medSList.get(i).getCode().equals(medicaments.get(j).getCode())) {
                            med = medicaments.get(j);
                            j = medicaments.size();
                        }
                    }
                    if (med == null) {
                        med = new Medikament();
                        med.setCode(medSList.get(i).getCode());
                        med.setName(medSList.get(i).getName());
                        if(med.getCode() != null && med.getName() != null){
                            control.addMedicamentDB(med);
                            try {
                                med = control.getLastMedInserted();
                                 //System.out.println(med2.getName());
                                // System.out.println(med2.getCode());
                            } catch (NichtErlaubException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if ( med != null && med.getCode() != null && med.getName() != null && neuPatient != null) {
                        control.addMedikamenteDB(neuPatient, med, medSList.get(i).getTaken(), medSList.get(i).getStatusStmt(), "", "", medSList.get(i).getDosage(), medSList.get(i).getIdServer());
                    }
                }
            }
        }

        return  b;
    }

    public int patientPosten(Patient patient) throws Exception {
        String id = control.patientPost(patient);
        patient.setIdServer(id);
        control.updatePatient(patient);
        List<MedicationStatement> med = control.getMedStat(patient);
        if(med.size() >0 ){
            for(int i = 0; i < med.size(); i++ ){

                String medid = control.medikamentSPost(med.get(i), patient);
                med.get(i).setIdServer(medid);
                control.updateMedDaten(med.get(i));
            }
        }
        return med.size();
    }

    public int  patientUpdaten( Patient orig, Patient alt) throws Exception {
            control.patientPatch(orig, alt);
            List<MedicationStatement> med = control.getMedStat(patient);
            int count = 0;

            for(int i = 0; i < med.size(); i++){
                if(med.get(i).getIdServer() == null){
                    String medid = control.medikamentSPost(med.get(i), patient);
                    med.get(i).setIdServer(medid);
                    control.updateMedDaten(med.get(i));
                    count++;
                }
            }
           return count;
    }
}
