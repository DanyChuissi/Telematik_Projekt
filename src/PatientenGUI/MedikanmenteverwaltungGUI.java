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

import java.time.LocalDate;

public class MedikanmenteverwaltungGUI extends Stage {
    private Stage primaryStage;
    private Patient patient;
    private Controller control;
    private Medikament med = new Medikament();

    public MedikanmenteverwaltungGUI(Stage primaryStage, Patient patient, Controller control) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.control = control;
    }


        public void showView (TableView tv) {
            GridPane grid = new GridPane();

            grid.setPadding(new Insets(10.0));
            grid.setVgap(5.0);
            grid.setHgap(10.0);

            Label namePatient = new Label("Patient");
            Label code = new Label("Code");
            Label dose = new Label("Dose");
            Label von = new Label("Annehmen von");
            Label bis = new Label("Annehmen bis");
            Label taken = new Label ("Taken");
            Label status = new Label("Status");
            Label note  = new Label("Note");


            Label pName = new Label(patient.getName()+", "+patient.getVorname());
            ComboBox<String> codeComboBox = new ComboBox<String>();
            for(int i = 0; i < control.MedikamentList().length; i++){
                codeComboBox.getItems().add(control.MedikamentList()[i].toString());
            }
            ComboBox<String> dosageComboBox = new ComboBox<String>();
            dosageComboBox.getItems().addAll( "1 x täglich" , "2 x täglich" , "3 x täglich" , "4 x täglich");
            DatePicker vonV = new DatePicker(LocalDate.now());
            DatePicker bisV = new DatePicker(LocalDate.now());

            ComboBox<String> takenComboBox = new ComboBox<String>();
            takenComboBox.getItems().addAll( "y" , "n" , "unk" , "na");

            ComboBox<String> statusComboBox = new ComboBox<String>();
            statusComboBox.getItems().addAll( "active" , "completed" , "entered-in-error" , "intended" , "stopped" , "on-hold");
            TextField noteV = new TextField();

            Button save = new Button("Änderungen Speichern");
            //Button  = new Button("Neu");

            Button abbrechen = new Button("Abbrechen");
            HBox hb = new HBox();
            hb.setSpacing(10.0);
            hb.setPadding(new Insets(15, 12, 15, 12));
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(save, abbrechen);


            grid.add(namePatient, 0, 0);
            grid.add(code, 0, 1);
            grid.add(dose, 0, 2);
            grid.add(von, 0, 3);
            grid.add(bis, 0, 4);
            grid.add(taken, 0, 5);
            grid.add(status, 0, 6);
            grid.add(note, 0, 7);

            grid.add(pName, 1, 0);
            grid.add(codeComboBox, 1, 1);

            grid.add(dosageComboBox, 1, 2);
            grid.add(vonV, 1, 3);
            grid.add(bisV, 1, 4);
            grid.add(takenComboBox, 1, 5);
            grid.add(statusComboBox, 1, 6);
            grid.add(noteV, 1, 7);

            grid.add(hb, 1, 8);


            save.setOnAction(e -> {
                for(int i = 0; i< control.MedikamentList().length; i++){
                    if(codeComboBox.getValue().toString().equals(control.MedikamentList()[i])){
                        med = control.MedikamentList()[i];
                    }
                }

                MedicationStatement medS = new MedicationStatement(patient,med, takenComboBox.getValue().toString(), statusComboBox.getValue().toString());
                medS.setForm(med.getForm());
                medS.setManufacturer(med.getManufacturer());
                medS.setName(med.getName());
                medS.setPeriode(vonV.getValue().toString(), bisV.getValue().toString());
                medS.setDosage(dosageComboBox.getValue());
                patient.addMedStatment(medS);
                control.setTableview( tv, patient);
                close();
                    });


            GridPane.setHgrow(pName, Priority.ALWAYS);
            GridPane.setHgrow(code, Priority.NEVER);
            GridPane.setHgrow(vonV, Priority.ALWAYS);
            GridPane.setHgrow(namePatient, Priority.ALWAYS);
            GridPane.setHgrow(hb, Priority.ALWAYS);
            //GridPane.setHalignment(b1, HPos.CENTER);
            GridPane.setValignment(save, VPos.BOTTOM);
            Scene scene = new Scene(grid);
            setTitle("Medikamente Verwaltung");
            setScene(scene);
            initOwner(primaryStage);
            initModality(Modality.WINDOW_MODAL);
            show();

        }


}
