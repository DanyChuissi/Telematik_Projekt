package src.PatientenGUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class PatientenverwaltungGUI extends Application {
    private TableView tvPatient = new TableView();

    private TableView tvMedikamente = new TableView();
    private Controller control;

    public void start(Stage primaryStage){
        final Label labelp = new Label("Patienten");
        labelp.setFont(new Font("Arial", 20));

        final Label labelm = new Label("Medikamente");
        labelm.setFont(new Font("Arial", 20));

        tvMedikamente.setEditable(true);
        tvMedikamente.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Name");
        TableColumn lastNameCol = new TableColumn("Vorname");
        TableColumn gebDatumCol = new TableColumn("Geburtsdatum");

        TableColumn medNameCol = new TableColumn("Medikament");
        TableColumn stautCol = new TableColumn("Status");
        TableColumn wieCol = new TableColumn("Wann wird genommen");

        tvPatient.getColumns().addAll(firstNameCol,lastNameCol,gebDatumCol);
        tvMedikamente.getColumns().addAll(medNameCol,stautCol,wieCol);


        control = new Controller(tvPatient);

        tvPatient.getItems().addAll("Frühling", "Sommer", "Herbst", "Winter");
        tvMedikamente.getItems().addAll("Medikamente1","Medikamente2","Medikamente3","Medikamente4");

        BorderPane hauptBp = new BorderPane();
        BorderPane topBp = new BorderPane();
        BorderPane mitteBp = new BorderPane();
        BorderPane bottomBp = new BorderPane();

        Button neuPatient = new Button("Patient Aufnehmen");
        Button suchen = new Button("Suchen");
        TextField suchenFied = new TextField("Name oder Id eingeben");

        HBox hb = new HBox();
        HBox hb2 = new HBox();

        hb2.setPadding(new Insets(15, 12, 15, 12));
        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setSpacing(10.0);

        topBp.setRight(hb);
        topBp.setLeft(hb2);


        hb.setAlignment(Pos.CENTER_LEFT);
        hb.getChildren().addAll(suchenFied, suchen);

        hb2.setAlignment(Pos.CENTER);
        hb2.getChildren().add(neuPatient);

        topBp.setPrefSize(800, 50);

        mitteBp.setPadding(new Insets(15,12,15,12));

        bottomBp.setPrefSize(800, 200);
        bottomBp.setPadding(new Insets(15,12,15,12));

        final VBox vboxP = new VBox();
        vboxP.setSpacing(5);
        //vboxP.setPadding(new Insets(10, 0, 0, 10));
        vboxP.getChildren().addAll(labelp, tvPatient);

        final VBox vboxM = new VBox();
        vboxM.setSpacing(5);
        //vboxM.setPadding(new Insets(10, 0, 0, 10));
        vboxM.getChildren().addAll(labelm, tvMedikamente);

        bottomBp.setCenter(vboxM);
        mitteBp.setCenter(vboxP);



        hauptBp.setTop(topBp);
        hauptBp.setCenter(mitteBp);
        hauptBp.setBottom(bottomBp);

        neuPatient.setOnAction(e -> {
            MedikanmenteverwaltungGUI mv = new MedikanmenteverwaltungGUI(primaryStage);
            mv.showView();
        });


        Scene scene = new Scene(hauptBp);
        primaryStage.setTitle("Patientenverwaltung");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
