package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modele.dao.*;
import modele.database.Database;

import java.sql.SQLException;

public class VeloNantes extends Application {

    // ---------------- Attributes ---------------- //

    private Stage primaryStage;
    private BorderPane root;
    private LeftBar leftBar;
    private AnchorPane rightPane;
    private MenuButton menu;
    private Graph graph;
    private DataMenu dataMenu;
    private Map map;
    private Graphiques graphiques;

    public static Scene scene;
    public static Database database;
    public static QuartierDao quartierDao;
    public static CompteurDao compteurDao;
    public static DateInfoDao dateInfoDao;
    public static ComptageDao comptageDao;


    // ---------------- Methods ---------------- //

    /**
     * The main method
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the application
     * @param primaryStage the primary stage
     * @throws IllegalArgumentException if primaryStage is null
     */
    @Override
    public void start(Stage primaryStage) throws IllegalArgumentException{
        if(primaryStage == null){
            throw new IllegalArgumentException("Primary stage cannot be null");
        }

        this.primaryStage = primaryStage;
        this.initializeData();
        primaryStage.setTitle("Velo de Nantes");
        this.initializeComponents();

    }

    /**
     * Initialize the components of the view
     */
    private void initializeComponents(){
        this.menu = new MenuButton();
        this.menu.setText("Menu");
        this.menu.getItems().add(new MenuItem("Graphes"));
        this.menu.getItems().add(new MenuItem("Graphiques"));
        this.menu.getItems().add(new MenuItem("Map"));
        this.menu.getItems().add(new MenuItem("Données"));
        this.menu.getItems().add(new MenuItem("Quitter"));

        this.rightPane = new AnchorPane();

        this.menu.getItems().get(0).setOnAction(e -> {
            System.out.println("VeloNantes : Graphes opened");
            this.rightPane.getChildren().clear();
            this.rightPane.getChildren().addAll(menu, graph); 
            this.leftBar.toGraph();
        });

        this.menu.getItems().get(1).setOnAction(e -> {
            System.out.println("Velonantes : Graphiques opened");
            this.rightPane.getChildren().clear(); 
            this.rightPane.getChildren().addAll(menu, graphiques);
            this.leftBar.toGraphiques();
        });

        this.menu.getItems().get(2).setOnAction(e -> {
            System.out.println("Velonantes : Map opened");
            this.rightPane.getChildren().clear(); 
            this.rightPane.getChildren().addAll(menu, map);
            this.leftBar.toGraph();
        });

        this.menu.getItems().get(3).setOnAction(e -> {
            System.out.println("Velonantes : Donnees opened");
            this.rightPane.getChildren().clear(); 
            this.rightPane.getChildren().addAll(menu, dataMenu); 
            this.leftBar.toAuthentification();
        });

        this.menu.getItems().get(4).setOnAction(e -> {
            System.out.println("Velonantes : Quitting");
            this.primaryStage.close();
        });

        this.graph = new Graph();
        this.leftBar = new LeftBar(graph);
        this.dataMenu = new DataMenu();
        this.map = new Map(this.leftBar.getFilters());
        this.graphiques = new Graphiques();

        this.rightPane.setPadding(new Insets(15, 12, 15, 12));
        this.rightPane.getChildren().addAll(menu, map);

        AnchorPane.setTopAnchor(menu, 0.0);
        AnchorPane.setRightAnchor(menu, 0.0);
        AnchorPane.setTopAnchor(graph, 30.0);
        AnchorPane.setTopAnchor(dataMenu, 30.0);

        this.root = new BorderPane();
        this.root.setLeft(this.leftBar);
        this.root.setCenter(rightPane);
        this.root.getStyleClass().add("background");

        scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(this.getClass().getResource("/data/style.css").toExternalForm());
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    /**
     * Initialize the data
     */
    private void initializeData(){
        try{
            database = new Database("jdbc:mariadb://localhost:3306/bd_velo_4b2");
            database.openReadConnection("read_4b2", "read_4b2");
        } catch (SQLException mariadbException) {
            try {
                database = new Database("jdbc:mysql://localhost:3306/bd_velo_4b2");
                database.openReadConnection("read_4b2", "read_4b2");
            } catch (SQLException mysqlException) {
                System.out.println("Impossible de se connecter à la base de données" + mysqlException.getMessage());
            }
        }

        quartierDao = new QuartierDao(database);
        compteurDao = new CompteurDao(database, quartierDao);
        dateInfoDao = new DateInfoDao(database);
        comptageDao = new ComptageDao(database, compteurDao, dateInfoDao);

        try {
            quartierDao.readAll();
            compteurDao.readAll();
            dateInfoDao.readAll();
            comptageDao.readAll();
        } catch (SQLException e) {
            System.out.println("Impossible de lire les données : " + e.getMessage());
        }
    }

    // ---------------- Getters & Setters ---------------- //
    
    /**
     * Get the graph
     * @return the graph
     */
    public Graph getGraph(){
        return this.graph;
    }
}
