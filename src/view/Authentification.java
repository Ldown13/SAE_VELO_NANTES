package view;

import controller.AuthentificationListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Authentification extends VBox {

    // ---------------- Attributes ---------------- //

    private GridPane gridPane;
    private Label authentificationLabel;
    private Pane spacer;
    private Label loginLabel;
    private Label passwordLabel;
    private TextField login;
    private PasswordField password;
    private Label error;
    private Button validateButton;
    private AuthentificationListener listener;
    private LeftBar leftBar;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the Authentification class
     * @param leftBar the left bar of the application
     * @throws IllegalArgumentException if leftBar is null
     */
    public Authentification(LeftBar leftBar) throws IllegalArgumentException{
        if(leftBar == null){
            throw new IllegalArgumentException("LeftBar cannot be null");
        }

        this.leftBar = leftBar;
        this.initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the Authentification class
     */
    private void initializeComponents(){
        this.gridPane = new GridPane();
        this.authentificationLabel = new Label("Authentification");
        this.authentificationLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        this.spacer = new Pane();
        this.spacer.setMinHeight(20);
        this.loginLabel = new Label("Identifiant");
        this.loginLabel.setStyle("-fx-text-fill: white;");
        this.passwordLabel = new Label("Mot de passe");
        this.passwordLabel.setStyle("-fx-text-fill: white;");
        this.login = new TextField();
        this.password = new PasswordField();
        this.error = new Label();
        this.error.maxWidth(100);
        this.error.setWrapText(true);
        this.validateButton = new Button("Valider");
        
        this.listener = new AuthentificationListener(this);
        this.validateButton.setOnAction(this.listener);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(this.authentificationLabel, 0, 0, 2, 1);
        gridPane.add(this.loginLabel, 0, 2);
        gridPane.add(this.login, 1, 2);
        gridPane.add(this.passwordLabel, 0, 3);
        gridPane.add(this.password, 1, 3);
        gridPane.add(this.validateButton, 1, 5);

        this.getChildren().add(this.gridPane);
        this.getChildren().add(this.error);
    }

    // ---------------- Getters & Setters ---------------- //

    /**
     * Get the login TextField
     * @return the login TextField
     */
    public TextField getLogin(){
        return this.login;
    }

    /**
     * Get the password PasswordField
     * @return the password PasswordField
     */
    public PasswordField getPassword(){
        return this.password;
    }

    /**
     * Set the error label
     * @param error the error to set
     * @throws IllegalArgumentException if error is null
     */
    public void setError(String error) throws IllegalArgumentException{
        if(error == null){
            throw new IllegalArgumentException("Error cannot be null");
        }

        String newError = "";
        for(int i = 0; i < error.length(); i++){
            if(i % 40 == 0 && i != 0){
                newError += "\n";
            }
            newError += error.charAt(i);
        }
        this.error.setText(newError);
    }

    /**
     * Get the left bar
     * @return the left bar
     */
    public LeftBar getLeftBar(){
        return this.leftBar;
    }
}
