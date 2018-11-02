package src.PatientenGUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Patient;


public class PatientenverwaltungGUI extends Application {
    private TableView<Patient> tvPatient = new TableView();
    private GridPane grid ;
    private TableView<MedicationStatement> tvMedikamente = new TableView();
    private Controller control;
    private Button updateMed = new Button();
    private Button updatePatient = new Button();

    public void start(Stage primaryStage){
        control = new Controller(tvPatient);
        grid = new GridPane();

        BorderPane topBp = new BorderPane();
        BorderPane rechtBp = new BorderPane();
        BorderPane linkBp = new BorderPane();
        BorderPane mitteBp = new BorderPane();
        BorderPane bottomBp = new BorderPane();

        Button neuPatient = new Button("Patient Aufnehmen");
        Button suchen = new Button("Suchen");
        TextField suchenFied = new TextField("Name oder Id eingeben");

        //HauptBorderpane
        BorderPane hauptBp = new BorderPane();

        //Obere Borderpane
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

        // Mittlere Bordepane
        final Label labelp = new Label("Patienten");
        labelp.setFont(new Font("Arial", 20));

        final Label labelpd = new Label("Patientendaten");
        labelpd.setFont(new Font("Arial", 20));
        tvPatient.setEditable(true);

        TableColumn<Patient, String> firstNameCol = new TableColumn<Patient, String>("Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Patient, String> lastNameCol = new TableColumn<>("Vorname");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        TableColumn gebDatumCol = new TableColumn("Geburtsdatum");
        gebDatumCol.setMinWidth(75);
        gebDatumCol.setCellValueFactory(new PropertyValueFactory<>("Geburtsdatum"));

        TableColumn adresseCol = new TableColumn("Adresse");
        adresseCol.setMinWidth(200);
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("Adresse"));

        TableColumn aufnahmedatumCol = new TableColumn("Aufnahmedatum");
        aufnahmedatumCol.setMinWidth(75);
        aufnahmedatumCol.setCellValueFactory(new PropertyValueFactory<>("Aufnahmedatum"));

        tvPatient.getColumns().addAll(firstNameCol,lastNameCol,gebDatumCol, adresseCol, aufnahmedatumCol);

        final VBox vboxP = new VBox();
        vboxP.setSpacing(5);
        //vboxP.setPadding(new Insets(10, 0, 0, 10));
        vboxP.getChildren().addAll(labelp, tvPatient);

        final VBox vboxPd = new VBox();
        vboxPd.setSpacing(5);
        //vboxP.setPadding(new Insets(10, 0, 0, 10));
        vboxPd.getChildren().addAll(labelpd, grid);


        rechtBp.setCenter(vboxPd);
        linkBp.setCenter(vboxP);
        mitteBp.setLeft(linkBp);
        mitteBp.setRight(rechtBp);


        //rechtBp.setPrefSize(200, 500);


        // Untere BorderpAne
        final Label labelm = new Label("Medikamente Statment");
        labelm.setFont(new Font("Arial", 20));
        tvMedikamente.setEditable(true);

        TableColumn medNameCol = new TableColumn("Name");
        medNameCol.setMinWidth(100);
        medNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn dosageCol = new TableColumn("Dosage");
        dosageCol.setMinWidth(100);
        dosageCol.setCellValueFactory(new PropertyValueFactory<>("Dosage"));

        TableColumn periodeCol = new TableColumn("Periode");
        periodeCol.setMinWidth(160);
        periodeCol.setCellValueFactory(new PropertyValueFactory<>("periode"));

        TableColumn statutCol = new TableColumn("Status");
        statutCol.setMinWidth(100);
        statutCol.setCellValueFactory(new PropertyValueFactory<>("Status"));

        TableColumn formCol = new TableColumn("Form");
        formCol.setMinWidth(100);
        formCol.setCellValueFactory(new PropertyValueFactory<>("Form"));

        TableColumn prescriptCol = new TableColumn("Prescription");
        prescriptCol.setMinWidth(100);
        prescriptCol.setCellValueFactory(new PropertyValueFactory<>("prescription"));

        TableColumn manufacturerCol = new TableColumn("Manufacturer");
        manufacturerCol.setMinWidth(100);
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn noteCol = new TableColumn("Note");
        noteCol.setMinWidth(100);
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tvMedikamente.getColumns().addAll(medNameCol,statutCol,dosageCol,periodeCol, formCol, prescriptCol, manufacturerCol);

        final VBox vboxM = new VBox();
        vboxM.setSpacing(5);
        //vboxM.setPadding(new Insets(10, 0, 0, 10));
        vboxM.getChildren().addAll(labelm, tvMedikamente);


        topBp.setPrefSize(1000, 50);

        linkBp.setPrefSize(600, 400);
        mitteBp.setPadding(new Insets(15,12,15,12));

        bottomBp.setPrefSize(1000, 200);
        bottomBp.setPadding(new Insets(15,12,15,12));
        bottomBp.setCenter(vboxM);



        hauptBp.setTop(topBp);
        hauptBp.setCenter(mitteBp);
        hauptBp.setBottom(bottomBp);

        neuPatient.setOnAction(e -> {
           // MedikanmenteverwaltungGUI mv = new MedikanmenteverwaltungGUI(primaryStage);
           // mv.showView();

            PateintenDatenGUI pv = new PateintenDatenGUI(primaryStage, control);
            pv.showView();
        });

        // OnMouseCliked
        tvPatient.setOnMouseClicked(new ListViewHandler() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Object o = tvPatient.getSelectionModel().getSelectedItem();
                if(o instanceof Patient){

                    Patient neu = (Patient) tvPatient.getSelectionModel().getSelectedItem();
                    grid = getGrid(neu);
                    vboxPd.getChildren().setAll(labelpd, grid);
                    control.setTableview(tvMedikamente, neu);
                    //vboxM.getChildren().setAll()


                    updateMed.setOnAction(e -> {
                        MedikanmenteverwaltungGUI mv = new MedikanmenteverwaltungGUI(primaryStage, neu, control);
                        mv.showView(tvMedikamente);
                       ;
                    });


                }
            }

        });



            Scene scene = new Scene(hauptBp);
        primaryStage.setTitle("Patientenverwaltung");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public GridPane getGrid(Patient p) {
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10.0));
        grid.setVgap(5.0);
        grid.setHgap(10.0);

        Label name = new Label("Name");
        Label vorname = new Label("Vorname");
        Label gebDatum = new Label("GeburtsDatum");
        Label strasse = new Label("Strasse, H-nummer");
        Label stadt = new Label("Plz- Stadt");
        Label telefon = new Label("Telefon");
        Label gender = new Label("Gender");
        Label deseaded = new Label("Gestorben");
        Label activ = new Label("Daten in Benutzung");
        Label tel = new Label("Telefon");
        Label aufnahemeD = new Label("AufnahmeDatum");
        Label entlassd = new Label("Datum der Entlassung");
        //Label  = new Label("Ort");

        Label nameV = new Label(""+p.getName());
        Label vornameV = new Label(""+p.getVorname());
        Label gebDatumV = new Label(""+p.getGeburtsdatumS());
        Label strasseV = new Label(p.getStreet()+", "+p.getHousenumber());
        Label stadtV = new Label(p.getPostalcode()+", "+p.getLocation());
        Label telV = new Label(""+p.getTelefon());
        Label genderV = new Label(""+p.getGender());
        Label deseadedV = new Label(""+p.getDeseased());
        Label activV = new Label(""+p.getActive());
        Label aufnahemeDV = new Label(""+p.getAufnahmeDatum());
        Label entlassdV = new Label(""+p.getEntlassungsdatum());

        Button updateMed = new Button("Medicament Verwalten");
        //Button  = new Button("Neu");
        Button updateDaten = new Button("Daten Verwalten");

        this.updateMed = updateMed;
        this.updatePatient = updatePatient;

        HBox hb = new HBox();
        hb.setSpacing(10.0);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(updateMed, updateDaten);

        grid.add(name, 0, 0);
        grid.add(vorname, 0, 1);
        grid.add(gebDatum, 0, 2);
        grid.add(strasse, 0, 3);
        grid.add(stadt, 0, 4);
        grid.add(tel, 0, 5);
        grid.add(gender, 0, 6);
        grid.add(deseaded, 0, 7);
        grid.add(activ, 0, 8);
        //grid.add(tel, 0, 9);
        grid.add(aufnahemeD, 0, 9);
        grid.add(entlassd, 0, 10);

        //grid.add(status, 0, 4);

        grid.add(nameV, 1, 0);
        grid.add(vornameV, 1, 1);
        grid.add(gebDatumV, 1, 2);
        grid.add(strasseV, 1, 3);
        grid.add(stadtV, 1, 4);
        grid.add(telV, 1, 5);
        grid.add(genderV, 1, 6);
        grid.add(deseadedV, 1, 7);
        grid.add(activV, 1, 8);
        //grid.add(telV, 1, 9);
        grid.add(aufnahemeDV, 1, 9);
        grid.add(entlassdV, 1, 10);


        ComboBox<String> myComboBox = new ComboBox<String>();
        myComboBox.getItems().addAll("Dortmund","Hagen","Essen","Köln","Wuppertal");
        //grid.add(myComboBox, 1, 6);

        grid.add(hb, 1, 11);


       // GridPane.setHgrow(tfname, Priority.ALWAYS);
      //  GridPane.setHgrow(tfvor, Priority.NEVER);
      //  GridPane.setHgrow(tfgebD, Priority.ALWAYS);
        GridPane.setHgrow(name, Priority.ALWAYS);
        GridPane.setHgrow(hb, Priority.ALWAYS);
        //GridPane.setHalignment(b1, HPos.CENTER);
        GridPane.setValignment(updateDaten, VPos.BOTTOM);
        return grid;
    }

    public static void main(String[] args){
        launch(args);
    }


}
