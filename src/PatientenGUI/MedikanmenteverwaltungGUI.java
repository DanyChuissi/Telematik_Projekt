package src.PatientenGUI;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Fachlogik.MedicationStatement;

public class MedikanmenteverwaltungGUI extends Stage {
    private Stage primaryStage;

    public MedikanmenteverwaltungGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

        public void showView () {
            GridPane grid = new GridPane();

            grid.setPadding(new Insets(10.0));
            grid.setVgap(5.0);
            grid.setHgap(10.0);

            Label name = new Label("Name");
            Label dose = new Label("Dose");
            Label von = new Label("Annehmen von");
            Label bis = new Label("Annehmen bis");
            Label status = new Label("Status");
            //Label  = new Label("Ort");

            TextField tfname = new TextField();
            TextField tfdose = new TextField();
            TextField tfvon = new TextField();
            TextField tfbis = new TextField();
            TextField tfstatus = new TextField();
            //TextField tfvon = new TextField();

            Button save = new Button("Save Change");
            //Button  = new Button("Neu");
            Button abbrechen = new Button("Abbrechen");

            HBox hb = new HBox();
            hb.getChildren().addAll(save, abbrechen);

            grid.add(name, 0, 0);
            grid.add(dose, 0, 1);
            grid.add(von, 0, 2);
            grid.add(bis, 0, 3);
            grid.add(status, 0, 4);
            grid.add(tfname, 1, 0);
            grid.add(tfdose, 1, 1);
            grid.add(tfvon, 1, 2);
            grid.add(tfbis, 1, 3);
            grid.add(tfstatus, 1, 4);
            grid.add(hb, 1, 5);

            GridPane.setHgrow(tfname, Priority.ALWAYS);
            GridPane.setHgrow(tfdose, Priority.NEVER);
            GridPane.setHgrow(tfvon, Priority.ALWAYS);
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
