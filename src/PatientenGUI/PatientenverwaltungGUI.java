package src.PatientenGUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.Fachlogik.MedicationStatement;
import src.Fachlogik.Patient;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.*;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;


public class PatientenverwaltungGUI extends Application {
    private TableView<Patient> tvPatient = new TableView();
    private GridPane grid ;
    private TableView<MedicationStatement> tvMedikamente = new TableView();
    private Controller control;
    private Button updateMed = new Button();
    private Button updatePatient = new Button();
    private Button entlassen = new Button();
    private Button remove = new Button();
    private Patient patient = null;
    private List<Patient> list;
    private Date today = new  Date();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
        Button listPatient = new Button ("Patienten List");
        TextField suchenFied = new TextField("Name oder Id eingeben");

        //HauptBorderpane
        BorderPane hauptBp = new BorderPane();

        //Obere Borderpane
        HBox hb = new HBox();
        HBox hb2 = new HBox();
        HBox hb3 = new HBox();

        hb2.setPadding(new Insets(15, 12, 15, 12));
        hb.setPadding(new Insets(15, 12, 15, 12));
        hb3.setPadding(new Insets(15, 12, 15, 12));

        hb.setSpacing(10.0);
        hb2.setSpacing(10.0);
        hb3.setSpacing(10.0);

        File file = new File("src/medLogo.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        topBp.setRight(hb);
        topBp.setLeft(hb2);

        hb.setAlignment(Pos.CENTER_LEFT);
        hb.getChildren().addAll(suchenFied, suchen);

        hb2.setAlignment(Pos.CENTER);
        hb2.getChildren().addAll(neuPatient, listPatient);

        hb3.setAlignment(Pos.CENTER_LEFT);
        hb3.getChildren().addAll(imageView);

        // Mittlere Bordepane
        final Label labelp = new Label("Patienten");
        labelp.setFont(new Font("Arial", 20));

        final Label labelpd = new Label("Patientendaten");
        labelpd.setFont(new Font("Arial", 20));
        tvPatient.setEditable(true);

        // Tabel colum für die Patienten tableview
        TableColumn identifierCol = new TableColumn("ID");
        identifierCol.setMinWidth(10);
        identifierCol.setCellValueFactory(new PropertyValueFactory<>("identifier"));

        TableColumn<Patient, String> firstNameCol = new TableColumn<Patient, String>("Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> lastNameCol = new TableColumn<>("Vorname");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        TableColumn<Patient, String> gebDatumCol = new TableColumn<>("Geburtsdatum");
        gebDatumCol.setMinWidth(75);
        gebDatumCol.setCellValueFactory(new PropertyValueFactory<>("geburtsdatumS"));

        TableColumn<Patient, String> adresseCol = new TableColumn<>("Adresse");
        adresseCol.setMinWidth(200);
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("Adresse"));

        TableColumn<Patient, String> aufnahmedatumCol = new TableColumn<>("Aufnahmedatum");
        aufnahmedatumCol.setMinWidth(75);
        aufnahmedatumCol.setCellValueFactory(new PropertyValueFactory<>("aufnahmeDatumS"));

        tvPatient.getColumns().addAll(identifierCol,firstNameCol,lastNameCol,gebDatumCol, adresseCol, aufnahmedatumCol);

        // Vbox für die Patienten Table view und Titel
        final VBox vboxP = new VBox();
        vboxP.setSpacing(5);
        //vboxP.setPadding(new Insets(10, 0, 0, 10));
        vboxP.getChildren().addAll(labelp, tvPatient);

        // Vbox fpr Patienten Daten und Titel
        final VBox vboxPd = new VBox();
        vboxPd.setSpacing(5);
        //vboxP.setPadding(new Insets(10, 0, 0, 10));
        vboxPd.getChildren().addAll(labelpd, grid);


        rechtBp.setCenter(vboxPd);
        linkBp.setCenter(vboxP);
        mitteBp.setLeft(linkBp);
        mitteBp.setRight(rechtBp);


        // Untere BorderpAne
        final Label labelm = new Label("Medikamente Statment");
        labelm.setFont(new Font("Arial", 20));
        tvMedikamente.setEditable(true);


        // Column für die Medikamente Table view
        TableColumn medNameCol = new TableColumn("Name");
        medNameCol.setMinWidth(100);
        medNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn dosageCol = new TableColumn("Dosage");
        dosageCol.setMinWidth(100);
        dosageCol.setCellValueFactory(new PropertyValueFactory<>("Dosage"));

        TableColumn periodeCol = new TableColumn("Periode");
        periodeCol.setMinWidth(160);
        periodeCol.setCellValueFactory(new PropertyValueFactory<>("periode"));

        TableColumn statutCol = new TableColumn("Status");
        statutCol.setMinWidth(100);
        statutCol.setCellValueFactory(new PropertyValueFactory<>("statusStmt"));

        TableColumn takenCol = new TableColumn("Taken");
        takenCol.setMinWidth(30);
        takenCol.setCellValueFactory(new PropertyValueFactory<>("taken"));


        TableColumn formCol = new TableColumn("Form");
        formCol.setMinWidth(100);
        formCol.setCellValueFactory(new PropertyValueFactory<>("form"));

        TableColumn prescriptCol = new TableColumn("Prescription");
        prescriptCol.setMinWidth(100);
        prescriptCol.setCellValueFactory(new PropertyValueFactory<>("prescription"));

        TableColumn manufacturerCol = new TableColumn("Manufacturer");
        manufacturerCol.setMinWidth(100);
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn noteCol = new TableColumn("Note");
        noteCol.setMinWidth(200);
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tvMedikamente.getColumns().addAll(medNameCol,statutCol,dosageCol,periodeCol,takenCol, formCol, prescriptCol, manufacturerCol, noteCol);

      //  Vbox füdie Medikamente Tableview und Titel
        final VBox vboxM = new VBox();
        vboxM.setSpacing(5);
        //vboxM.setPadding(new Insets(10, 0, 0, 10));
        vboxM.getChildren().addAll(labelm, tvMedikamente);


        topBp.setPrefSize(1000, 50);

        linkBp.setPrefSize(650, 400);
        mitteBp.setPadding(new Insets(15,12,15,12));
        rechtBp.setPrefSize(350,400);

        bottomBp.setPrefSize(1000, 200);
        bottomBp.setPadding(new Insets(15,12,15,12));
        bottomBp.setCenter(vboxM);



        hauptBp.setTop(topBp);
        hauptBp.setCenter(mitteBp);
        hauptBp.setBottom(bottomBp);
        //topBp.setCenter(hb3);

        //  neue Patiente Aufnehmen
        neuPatient.setOnAction(e -> {
            PatientenDatenGUI pv = new PatientenDatenGUI(primaryStage, control, null);
            pv.showView();
        });


        // OnMouseCliked ( Auf Patiente in Tabelview
        tvPatient.setOnMouseClicked(new ListViewHandler() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Object o = tvPatient.getSelectionModel().getSelectedItem();
                if (o instanceof Patient) {

                    patient = (Patient) o;
                    grid = getGrid(patient);
                    vboxPd.getChildren().setAll(labelpd, grid);
                    rechtBp.setCenter(vboxPd);
                    control.setMedtabelviewBD(tvMedikamente, patient);

                    //clik on Button Medikamente Verwalten
                    updateMed.setOnAction(e -> {
                        if(patient.getEntlassungStatus() != true) {
                            MedikanmenteverwaltungGUI mv = new MedikanmenteverwaltungGUI(primaryStage, patient, control, null);
                            mv.showView(tvMedikamente);
                        }
                        else{
                            Formatter formatter = new Formatter();
                            formatter.format("Patient wurde Entlassen");

                            JOptionPane.showMessageDialog(null, formatter.toString());
                            formatter.close();
                        }
                        ;
                    });

                    // Patiente Löschen
                    remove.setOnAction(e -> {
                        if(control.getMedStat(patient).size() >= 0) {
                            try {
                                control.loeschePatientFromDB(patient);
                                control.setPatTableview();
                                Formatter formatter = new Formatter();
                                formatter.format("Patient wurde gelöscht");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            } catch (SQLException e1) {
                                Formatter formatter = new Formatter();
                                formatter.format("Fehler beim Löschen");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            }
                        }
                        else{
                            Formatter formatter = new Formatter();
                            formatter.format("Patient kann nicht gelöscht werden, da er Medicamnet am laufen hat");

                            JOptionPane.showMessageDialog(null, formatter.toString());
                            formatter.close();
                        }
                    });

                    // Clik on Button Patienten Daten verwalten
                    updatePatient.setOnAction(e -> {
                        PatientenDatenGUI pv = new PatientenDatenGUI(primaryStage, control, patient);
                        pv.showView();
                    });

                    // Clik on ein Medikamante in Medikamente Tableview
                    tvMedikamente.setOnMouseClicked(new ListViewHandler() {
                        @Override
                        public void handle(javafx.scene.input.MouseEvent event) {
                            Object o = tvMedikamente.getSelectionModel().getSelectedItem();
                            if(o instanceof MedicationStatement){

                                MedicationStatement medS = (MedicationStatement) o;
                                MedikanmenteverwaltungGUI mv= new MedikanmenteverwaltungGUI(primaryStage,patient, control, medS);
                                mv.showView(tvMedikamente);
                            }
                        }

                    });

                    //Patiente Entalssen
                    entlassen.setOnAction(e -> {
                        int reply = JOptionPane.showConfirmDialog(null, "Der Patient wird Jetzt Entlassen", "Hinweis", JOptionPane.YES_NO_OPTION);
                        if (reply == YES_OPTION) {
                            try {
                                patient.setEnlassungStatus(true);
                                patient.setEntlassungsDatum(patient.stringToSqlDate(df.format(today)));
                                control.updatePatient(patient);
                                grid = getGrid(patient);
                                vboxPd.getChildren().setAll(labelpd, grid);
                                rechtBp.setCenter(vboxPd);

                            } catch (SQLException | ParseException ex) {
                                Formatter formatter = new Formatter();
                                formatter.format("Fehler beim Entlassen");

                                JOptionPane.showMessageDialog(null, formatter.toString());
                                formatter.close();
                            }
                        } else if (reply == NO_OPTION) {

                        }
                    });
                }
            }
        });



        // Patiente Suchen
       suchen.setOnAction(e -> {
            try {
                int id = Integer.parseInt(suchenFied.getText());
                List erg = control.suchMitId(id);
                if(!erg.isEmpty()) {
                    control.setPatTabelview(erg);
                    grid = getGrid(null);
                    vboxPd.getChildren().setAll(labelpd, grid);
                    rechtBp.setCenter(vboxPd);
                    control.setMedtabelviewBD(tvMedikamente, (Patient) null);
                }else{
                    Formatter formatter = new Formatter();
                    formatter.format("ID " +id+ "nicht gefunden");

                    JOptionPane.showMessageDialog(null, formatter.toString());
                    formatter.close();
                }

                // Fall Formatieren eine Exeption wirf soll der Patetien nach der Name gesucht
            }catch (NumberFormatException ee) {

                try {
                    List erg = control.suchMitName(suchenFied.getText());
                    if(!erg.isEmpty()) {
                        control.setPatTabelview(erg);
                        grid = getGrid(null);
                        vboxPd.getChildren().setAll(labelpd, grid);
                        rechtBp.setCenter(vboxPd);
                        control.setPatTabelview(erg);
                        control.setMedtabelviewBD(tvMedikamente,(Patient) null);
                    }
                    else{
                        Formatter formatter = new Formatter();
                        formatter.format("Der Name " +suchenFied.getText()+ " ist nicht gefunden");

                        JOptionPane.showMessageDialog(null, formatter.toString());
                        formatter.close();
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (NichtErlaubException e1) {
                    e1.printStackTrace();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (NichtErlaubException e1) {
                e1.printStackTrace();
            }


           // String name = suchenFied.getText();

        });

       // Nach dem eine Patient gesucht worden ist. kann es mit diese Button auf die Pateiten Liste zurüch
        listPatient.setOnAction(e -> {
            control.setPatTableview();
                });

            tvPatient.getItems().addAll(control.ladePatientDB());

            Scene scene = new Scene(hauptBp);
        primaryStage.setTitle("Patientenverwaltung");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    // Diese Methode wird beim click auf eine Patiente gerufen und zeig deiene Grid mit die Patiente  Datenb auf der recte Seite
    public GridPane getGrid(Patient p) {
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10.0));
        grid.setVgap(5.0);
        grid.setHgap(10.0);


        Button updateMed = new Button("Medicament Verwalten");
        Button updateDaten = new Button("Daten Verwalten");
        Button remove = new Button("Patient löschen");

        this.updateMed = updateMed;
        this.updatePatient = updateDaten;
        this.remove = remove;

        HBox hb = new HBox();
        hb.setSpacing(10.0);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(updateMed, updateDaten);

        if(p != null) {
        Label name = new Label("Name");
        Label vorname = new Label("Vorname");
        Label idnummer = new Label("Id Nummer");
        Label gebDatum = new Label("GeburtsDatum");
        Label strasse = new Label("Strasse, H-nummer");
        Label stadt = new Label("Plz- Stadt");
        Label gender = new Label("Gender");
        Label deseaded = new Label("Gestorben");
        Label activ = new Label("Daten in Benutzung");
        Label tel = new Label("Telefon");
        Label aufnahemeD = new Label("AufnahmeDatum");
        Label entlassd = new Label("Datum der Entlassung");



            Label nameV = new Label("" + p.getName());
            Label vornameV = new Label("" + p.getVorname());
            Label idnummerV = new Label("" + p.getIdentifier());
            Label gebDatumV = new Label("" + p.getGeburtsdatumS());
            Label strasseV = new Label(p.getStreet() + ", " + p.getHousenumber());
            Label stadtV = new Label(p.getPostalcode() + ", " + p.getLocation());
            Label telV = new Label("" + p.getTelefon());
            Label genderV = new Label("" + p.getGender());
            Label deseadedV = new Label("" + p.getDeseased());
            Label activV = new Label("" + p.getActive());
            Label aufnahemeDV = new Label("" + p.getAufnahmeDatum());
            Label entlassdV = new Label("" + p.getEntlassungsdatum());

            grid.add(name, 0, 0);
            grid.add(vorname, 0, 1);
            grid.add(idnummer, 0, 2);
            grid.add(gebDatum, 0, 3);
            grid.add(strasse, 0, 4);
            grid.add(stadt, 0, 5);
            grid.add(tel, 0, 6);
            grid.add(gender, 0, 7);
            grid.add(deseaded, 0, 8);
            grid.add(activ, 0, 9);
            grid.add(aufnahemeD, 0, 10);
            grid.add(entlassd, 0, 11);


            grid.add(nameV, 1, 0);
            grid.add(vornameV, 1, 1);
            grid.add(idnummerV, 1, 2);
            grid.add(gebDatumV, 1, 3);
            grid.add(strasseV, 1, 4);
            grid.add(stadtV, 1, 5);
            grid.add(telV, 1, 6);
            grid.add(genderV, 1, 7);
            grid.add(deseadedV, 1, 8);
            grid.add(activV, 1, 9);
            grid.add(aufnahemeDV, 1, 10);
            grid.add(entlassdV, 1, 11);

            grid.add(hb, 1, 12);
            grid.add(remove, 1,13);

            if(p.getEntlassungStatus() == false){
                Button entlassen = new Button("Entlassen");
                this.entlassen = entlassen;
                grid.add(entlassen, 0, 12);


            }
        }

        GridPane.setValignment(updateDaten, VPos.BOTTOM);
        return grid;
    }

    public static void main(String[] args){

        launch(args);
        //this.control.conClose();
    }


}
