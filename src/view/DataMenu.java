package view;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DataMenu extends HBox {

    // ---------------- Attributes ---------------- //

    private String mode;
    private String table;
    private VBox menuList;

    private UpdateQuartier updateQuartier;
    private UpdateCompteur updateCompteur;
    private UpdateDate updateDate;
    private UpdateComptage updateComptage;
    private InsertQuartier insertQuartier;
    private InsertCompteur insertCompteur;
    private InsertDate insertDate;
    private InsertComptage insertComptage;
    private RemoveQuartier removeQuartier;
    private RemoveCompteur removeCompteur;
    private RemoveDate removeDate;
    private RemoveComptage removeComptage;

    private HashMap<String, Node> actionMap;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the DataMenu class
     */
    public DataMenu() {
        initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the DataMenu class
     */
    private void initializeComponents() {
        menuList = new VBox();
        menuList.setSpacing(8); 
        menuList.setPadding(new Insets(10)); 

        String[] actions = new String[]{"Modification", "Saisie", "Suppression"};
        String[] tables = new String[]{"Quartier", "Compteur", "DateInfo", "Comptage"};

        this.actionMap = new HashMap<String, Node>();

        for (String action : actions) {
            Label categoryLabel = new Label(action + " des données existantes");
            categoryLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            menuList.getChildren().add(categoryLabel);

            for (String table : tables) {
                Button button;

                if(action.equals("Modification")){
                    button = new Button("Modifier " + table);
                } else if(action.equals("Saisie")){
                    button = new Button("Ajouter à " + table);
                } else {
                    button = new Button("Supprimer de " + table);
                }

                button.setOnAction(e -> {
                    this.mode = action;
                    this.table = table;
                    updateForm();
                });

                button.setMaxWidth(512);
                button.setAlignment(Pos.CENTER);

                menuList.getChildren().add(button);
            }
        }

        this.updateQuartier = new UpdateQuartier();
        this.updateCompteur = new UpdateCompteur();
        this.updateDate = new UpdateDate();
        this.updateComptage = new UpdateComptage();
        this.insertQuartier = new InsertQuartier();
        this.insertCompteur = new InsertCompteur();
        this.insertDate = new InsertDate();
        this.insertComptage = new InsertComptage();
        this.removeQuartier = new RemoveQuartier();
        this.removeCompteur = new RemoveCompteur();
        this.removeDate = new RemoveDate();
        this.removeComptage = new RemoveComptage();

        this.actionMap = new HashMap<String, Node>();
        this.actionMap.put("ModificationQuartier", this.updateQuartier);
        this.actionMap.put("ModificationCompteur", this.updateCompteur);
        this.actionMap.put("ModificationDateInfo", this.updateDate);
        this.actionMap.put("ModificationComptage", this.updateComptage);
        this.actionMap.put("SaisieQuartier", this.insertQuartier);
        this.actionMap.put("SaisieCompteur", this.insertCompteur);
        this.actionMap.put("SaisieDateInfo", this.insertDate);
        this.actionMap.put("SaisieComptage", this.insertComptage);
        this.actionMap.put("SuppressionQuartier", this.removeQuartier);
        this.actionMap.put("SuppressionCompteur", this.removeCompteur);
        this.actionMap.put("SuppressionDateInfo", this.removeDate);
        this.actionMap.put("SuppressionComptage", this.removeComptage);

        this.getChildren().addAll(menuList);
    }

    /**
     * Update the form of the DataMenu class
     */
    public void updateForm(){
        this.getChildren().clear();
        this.getChildren().add(this.menuList);
        Node action = actionMap.get(this.mode + this.table);
        if (action != null) {
            this.getChildren().add(action);
        }
    }
}
