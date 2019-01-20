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
import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Medikament;
import src.Fachlogik.Patient;
import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.List;

public class MedikanmenteverwaltungGUI extends Stage {
    private Stage primaryStage;
    private Patient patient;
    private Controller control;
    private MedicationStatement medS;



    public MedikanmenteverwaltungGUI(Stage primaryStage, Patient patient, Controller control, MedicationStatement medS ) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.control = control;
        this.medS = medS;

    }


        public void showView (TableView tv) {
            GridPane grid = new GridPane();

            grid.setPadding(new Insets(10.0));
            grid.setVgap(5.0);
            grid.setHgap(10.0);

            Label namePatient = new Label("Patient");
            Label idPatient = new Label("Id Patient");
            Label code = new Label("Medikament Code");
            Label dose = new Label("Dose");
            Label von = new Label("Annehmen von");
            Label bis = new Label("Annehmen bis");
            Label taken = new Label ("Taken");
            Label status = new Label("Status");
            Label note  = new Label("Note");


            Label pName = new Label(patient.getName()+", "+patient.getVorname());
            Label idV = new Label (""+patient.getIdentifier());
            ComboBox<Medikament> codeComboBox = new ComboBox<Medikament>();
            List<Medikament> tmp = control.getMedikamentList();
            for(int i = 0; i < tmp.size(); i++){
                codeComboBox.getItems().add(tmp.get(i));
            }
            ComboBox<String> dosageComboBox = new ComboBox<String>();
            dosageComboBox.setEditable(true);
            dosageComboBox.getItems().addAll( "1 x täglich" , "2 x täglich" , "3 x täglich" , "4 x täglich");
            DatePicker vonV = new DatePicker(LocalDate.now());
            DatePicker bisV = new DatePicker(LocalDate.now());

            ComboBox<String> takenComboBox = new ComboBox<String>();
            takenComboBox.getItems().addAll( "y" , "n" , "unk" , "na");

            ComboBox<String> statusComboBox = new ComboBox<String>();
            statusComboBox.getItems().addAll( "active" , "completed" , "entered-in-error" , "intended" , "stopped" , "on-hold");
            TextField noteV = new TextField();

            Button save = new Button("Änderungen Speichern");
            Button saveServer = new Button("Hochladen");

            Button abbrechen = new Button("Abbrechen");
            HBox hb = new HBox();
            hb.setSpacing(10.0);
            hb.setPadding(new Insets(15, 12, 15, 12));
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(save, abbrechen);


            grid.add(namePatient, 0, 0);
            grid.add(idPatient, 0, 1);
            grid.add(code, 0, 2);
            grid.add(dose, 0, 3);
            grid.add(von, 0, 4);
            grid.add(bis, 0, 5);
            grid.add(taken, 0, 6);
            grid.add(status, 0, 7);
            grid.add(note, 0, 9);

            grid.add(pName, 1, 0);
            grid.add(idV, 1, 1);
            grid.add(codeComboBox, 1, 2);
            grid.add(dosageComboBox, 1, 3);
            grid.add(vonV, 1, 4);
            grid.add(bisV, 1, 5);
            grid.add(takenComboBox, 1, 6);
            grid.add(statusComboBox, 1, 7);
            grid.add(noteV, 1, 9);

            grid.add(hb, 1, 10);

            // Beim Clik auf "Medikamente Verwalten" oder auf eien MedikamenteStatement wird diese Klasse aufgeruft
            //der If Anweisung prüf ob es auf eine Medikament geclickt worden ist
            if(medS != null){
                Button remove = new Button("löschen");
                remove.setStyle("-fx-border-color: #ff0000; -fx-border-width: 3px");
                HBox hb2 = new HBox();
                hb2.setPadding(new Insets(15, 12, 15, 12));
                hb2.setAlignment(Pos.CENTER);
                hb2.getChildren().add(remove);

                grid.add(hb2,1,11);
                if(medS.getMedikament() != null){codeComboBox.setValue(medS.getMedikament());};

                dosageComboBox.setValue(medS.getDosage());
                //vonV.setValue(medS.getVon(medS.getPeriode()));
                //bisV.setValue(medS.getBis(medS.getPeriode()));
                takenComboBox.setValue(medS.getTaken());
                statusComboBox.setValue(medS.getStatusStmt());
                noteV.setText(medS.getNote());

                //Lokale Medikament
                if(medS.getIdServer() == null && patient.getIdServer() != null){
                    saveServer.setText("Medication Hochladen");
                    grid.add(saveServer, 0, 10);
                    saveServer.setOnAction(e -> {
                        try {
                            control.medikamentSPost(medS, patient);
                        } catch (Exception es) {
                            es.printStackTrace();
                        }
                    });
                }

                // Mit diese setOnAction kann ein Medikation Statement gelöscht werden
                remove.setOnAction(e -> {
                    try {
                        control.loescheMedStatement(medS);
                        Formatter formatter = new Formatter();
                        formatter.format("Medikamente Statement wurde gelöscht");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    } catch (SQLException e1) {
                        Formatter formatter = new Formatter();
                        formatter.format("Fehler beim Löschen");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }
                    close();
                });

                // Die angegebene Änderungen werden gespeichert
                save.setOnAction(e -> {
                    Medikament med = new Medikament();
                    if(codeComboBox.getValue() != null ) {
                    /*for(int i = 0; i< tmp.size(); i++){
                        if(codeComboBox.getValue().equals(tmp.get(i).getCode())){
                            med = tmp.get(i);
                        }
                    }*/
                  /*  if(patient.getIdentifier() == 0 && med == null){
                        med.setName(medS.getName());

                    }*/
                   med = codeComboBox.getValue();
                    if(patient != null && med != null  && !takenComboBox.getSelectionModel().isEmpty() && !statusComboBox.getSelectionModel().isEmpty()) {


                            medS.setForm(med.getForm());
                            medS.setManufacturer(med.getManufacturer());
                            medS.setName(med.getName());
                            medS.setPrescription(med.isOverTheCounter());

                        medS.setPeriodevonbis(vonV.getValue().toString(), bisV.getValue().toString());
                        medS.setDosage(dosageComboBox.getValue());
                        medS.setNote(noteV.getText());
                        medS.setStatusStmt(statusComboBox.getValue());
                        medS.setMedikament(med);
                        medS.setTaken(takenComboBox.getValue());
                        //control.setMedtabelviewBD(tv, medS);

                        try {
                             MedicationStatement me = control.updateMedDaten(medS);
                             control.setMedtabelviewBD(tv, patient);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }


                        close();
                    }else{
                        Formatter formatter = new Formatter();
                        formatter.format("Medikament, taken und Status müssen eingegeben werden");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }
                   }else{
                        Formatter formatter = new Formatter();
                        formatter.format("Medikament Code ist Leer");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }
                });
            }
            //Falls Medikament Stateemnt == null wird ein neue Medikament Statement erstellt
            else {
                save.setOnAction(e -> {
                    Medikament med = new Medikament();
                     if(!codeComboBox.getSelectionModel().isEmpty()) {
                    /*for (int i = 0; i < tmp.size(); i++) {
                        if (codeComboBox.getValue().equals(tmp.get(i).getCode())) {
                            med = tmp.get(i);
                        }
                    }*/
                        med = codeComboBox.getValue();
                        if(patient != null && med != null && !takenComboBox.getSelectionModel().isEmpty() && !statusComboBox.getSelectionModel().isEmpty() ) {
                            MedicationStatement medS = new MedicationStatement();
                            medS.setPeriodevonbis(vonV.getValue().toString(), bisV.getValue().toString());
                            try {
                                control.addMedikamenteDB(patient, med, takenComboBox.getValue(), statusComboBox.getValue(), medS.getPeriode(),noteV.getText(), dosageComboBox.getValue(), medS.getIdServer() );
                                control.setMedtabelviewBD(tv, patient);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                            close();
                        }else{
                            Formatter formatter = new Formatter();
                            formatter.format("Medikament, taken und Status müssen eingegeben werden");

                            JOptionPane.showMessageDialog(null, formatter.toString());
                            formatter.close();
                        }
                    }else{
                        Formatter formatter = new Formatter();
                        formatter.format("kein Medikament gewählt");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }
                });
                if(patient.getIdServer() != null) {
                    saveServer.setText("Daten Hochladen");
                    grid.add(saveServer, 0, 10);
                    saveServer.setOnAction(e -> {
                        Medikament med = new Medikament();
                        if (!codeComboBox.getSelectionModel().isEmpty()) {

                            med = codeComboBox.getValue();
                            if (patient != null && med != null && !takenComboBox.getSelectionModel().isEmpty() && !statusComboBox.getSelectionModel().isEmpty()) {
                                MedicationStatement medS = new MedicationStatement();

                                medS.setPeriodevonbis(vonV.getValue().toString(), bisV.getValue().toString());
                                medS.setStatusStmt(statusComboBox.getValue());
                                medS.setName(codeComboBox.getValue().getName());
                                medS.setTaken(takenComboBox.getValue());
                                medS.setDosage(dosageComboBox.getValue());
                                medS.setForm(med.getForm());
                                medS.setCode(med.getCode());
                                medS.setMedikament(med);

                                    try {
                                        String id = control.medikamentSPost(medS, patient);
                                        medS.setIdServer(id);
                                        control.updateMedDaten(medS);
                                        close();
                                    } catch (Exception es) {
                                        es.printStackTrace();
                                        close();
                                    }


                                close();
                            } else {
                                Formatter formatter = new Formatter();
                                formatter.format("Medikament, taken und Status müssen eingegeben werden");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            }
                        } else {
                            Formatter formatter = new Formatter();
                            formatter.format("kein Medikament gewählt");

                            JOptionPane.showMessageDialog(null, formatter.toString());
                            formatter.close();
                        }
                    });
                }

            }
            abbrechen.setOnAction(e -> {
                close();
                    });
            GridPane.setHgrow(pName, Priority.ALWAYS);
            GridPane.setHgrow(code, Priority.NEVER);
            GridPane.setHgrow(vonV, Priority.ALWAYS);
            GridPane.setHgrow(namePatient, Priority.ALWAYS);
            GridPane.setHgrow(hb, Priority.ALWAYS);
            GridPane.setValignment(save, VPos.BOTTOM);
            Scene scene = new Scene(grid);
            setTitle("Medikament Verwaltung");
            setScene(scene);
            initOwner(primaryStage);
            initModality(Modality.WINDOW_MODAL);
            show();

        }


}
