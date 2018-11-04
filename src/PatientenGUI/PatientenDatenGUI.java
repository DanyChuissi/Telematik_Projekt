package src.PatientenGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Fachlogik.Patient;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;

public class PatientenDatenGUI extends Stage {
    private Stage primaryStage;
    private Controller control;
    private Patient patient;
    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("YYYY-MM-DD");

    public PatientenDatenGUI(Stage primaryStage, Controller control, Patient patient){
        this.primaryStage = primaryStage;
        this.control = control;
        this.patient = patient;
    }

    public void showView(){
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10.0));
        grid.setVgap(5.0);
        grid.setHgap(10.0);

        Label name = new Label("Name");
        Label vorname = new Label("Vorname");
        Label gebDatum = new Label("Geburst-Datum");
        Label gender = new Label("Gender");
        Label tel = new Label("Telephon");
        Label deseaded = new Label("Gestorben");
        Label street = new Label("Straße");
        Label haussn = new Label("Hausnuummer");
        Label postalcode = new Label("Postleizahl");
        Label location = new Label("Stadt");
        //Label  = new Label("Ort");

        TextField tfname = new TextField();
        TextField tfvor = new TextField();
        DatePicker tfgebD = new DatePicker(LocalDate.now());
        TextField tftel = new TextField();
        //TextField tfdeaseaded = new TextField();
        TextField tfstreet = new TextField();
        TextField tfhaussn = new TextField();
        TextField tfpostalc = new TextField();
        TextField tflocation = new TextField();

        //TextField tfstatus = new TextField();
        //TextField tfvon = new TextField();

        Button save = new Button("Änderungen Speichern");
        //Button  = new Button("Neu");
        Button abbrechen = new Button("Abbrechen");

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
        grid.add(haussn, 0, 6);
        grid.add(postalcode, 0, 7);
        grid.add(location, 0, 8);
        grid.add(deseaded, 0, 9);
        //grid.add(status, 0, 4);

        grid.add(tfname, 1, 0);
        grid.add(tfvor, 1, 1);
        grid.add(tfgebD, 1, 2);
        grid.add(tftel, 1, 3);
        ComboBox<String> genderComboBox = new ComboBox<String>();
        genderComboBox.getItems().addAll("male","female","other","unknown");
        grid.add(genderComboBox, 1, 4);
        grid.add(tfstreet, 1, 5);
        grid.add(tfhaussn, 1, 6);
        grid.add(tfpostalc, 1, 7);
        grid.add(tflocation, 1, 8);

        CheckBox ja = new CheckBox("Ja");
        CheckBox nein = new CheckBox("Nein");
        HBox hbCheck = new HBox();
        hbCheck.setSpacing(10);
        hbCheck.getChildren().addAll(ja, nein);

        grid.add(hbCheck, 1, 9);
        grid.add(hb, 1, 10);

        if(patient != null){
            tfname.setText(patient.getName());
            tfvor.setText(patient.getVorname());
            //tfgebD.setValue(LocalDate.parse(patient.getGeburtsdatumS(),dateTimeFormatter));
            tfgebD.setValue(patient.getGeburtsdatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            tftel.setText(""+patient.getTelefon());
            genderComboBox.setValue(patient.getGender());
            tfstreet.setText(patient.getStreet());
            tfhaussn.setText(""+patient.getHousenumber());
            tfpostalc.setText(""+patient.getPostalcode());
            tflocation.setText(patient.getLocation());
           //tfname.setText(patient.getName());

            save.setOnAction(e -> {
                if(!tfname.getText().isEmpty()  && !tfvor.getText().isEmpty()) {
                    try {
                        int tl = Integer.parseInt(tftel.getText());
                        int haus = Integer.parseInt(tfhaussn.getText());
                        int plz = Integer.parseInt(tfpostalc.getText());

                        patient.setName(tfname.getText());
                        patient.setVorname(tfvor.getText());
                        patient.setGeburtsdatum(patient.stringToDate(tfgebD.getValue().toString()));
                        patient.setGeburtsdatumS(tfgebD.getValue().toString());
                        patient.setTelefon(tl);
                        patient.setGender(genderComboBox.getValue());
                        patient.setStreet(tfstreet.getText());
                        patient.setHousenumber(haus);
                        patient.setPostalcode(plz);
                        patient.setLocation(tflocation.getText());
                        if (ja.isSelected())
                            patient.setDeseased(true);
                        control.setPatTableview();
                        close();
                    } catch (ParseException exx) {
                        Formatter formatter = new Formatter();
                        formatter.format("Bitte gültiger Datum Format eingeben  TT-MM-YYYY");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    } catch (NumberFormatException ex) {
                        Formatter formatter = new Formatter();
                        formatter.format("Bitte gültiger Zahl eingeben");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }catch (NichtErlaubException ne) {
                        Formatter formatter = new Formatter();
                        formatter.format("Nicht zulläzige Eingabe");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }
                }else{
                    Formatter formatter = new Formatter();
                    formatter.format("Name oder Vorname ist Leer");

                    JOptionPane.showMessageDialog(null, formatter.toString());
                    formatter.close();
                }


            });

        }else {
            save.setOnAction(e -> {
                patient = new Patient();
                if (!tfname.getText().isEmpty() && !tfvor.getText().isEmpty()) {
                    try {
                        int tl = Integer.parseInt(tftel.getText());
                        int haus = Integer.parseInt(tfhaussn.getText());
                        int plz = Integer.parseInt(tfpostalc.getText());

                        patient.setName(tfname.getText());
                        patient.setVorname(tfvor.getText());
                        patient.setGeburtsdatum(patient.stringToDate(tfgebD.getValue().toString()));
                        patient.setGeburtsdatumS(tfgebD.getValue().toString());
                        patient.setTelefon(tl);
                        patient.setGender(genderComboBox.getValue());
                        patient.setStreet(tfstreet.getText());
                        patient.setHousenumber(haus);
                        patient.setPostalcode(plz);
                        patient.setLocation(tflocation.getText());
                        if (ja.isSelected())
                            patient.setDeseased(true);
                        control.addPatient(patient);
                        close();
                    } catch (ParseException exx) {
                        Formatter formatter = new Formatter();
                        formatter.format("Bitte gültiger Datum Format eingeben  TT-MM-YYYY");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    } catch (NumberFormatException ex) {
                        Formatter formatter = new Formatter();
                        formatter.format("Bitte gültiger Zahl eingeben");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }catch (NichtErlaubException ne) {
                    Formatter formatter = new Formatter();
                    formatter.format("Nicht zulläzige Eingabe");

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



}
