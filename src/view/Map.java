package view;

import java.sql.Date;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.InfoWindow;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.Marker;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import com.dlsc.gmapsfx.javascript.object.MarkerOptions;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modele.entities.Compteur;
import modele.entities.DateInfo;
import modele.entities.Quartier;
import netscape.javascript.JSObject;

public class Map extends HBox implements MapComponentInitializedListener {

    // ---------------- Attributes ---------------- //

    private GoogleMapView mapView;
    private GoogleMap map;
    private MapOptions mapOptions;
    private Filters filters;
    private Pane hSpacer;
    private VBox mapBox;
    private Pane vSpacer;
    private InfoWindow lastOpenWindow;

    // ---------------- Constructor ---------------- //

    /**
     * Constructor of the Map class
     * @param filters the filters of the application
     * @throws IllegalArgumentException if filters is null
     */
    public Map(Filters filters) throws IllegalArgumentException{
        super();

        if(filters == null){
            throw new IllegalArgumentException("Filters cannot be null");
        }

        this.filters = filters;
        this.initializeComponents();
    }

    // ---------------- Methods ---------------- //

    /**
     * Initialize the components of the Map class
     */
    private void initializeComponents(){
        this.mapView = new GoogleMapView("", "AIzaSyAMxQ0PIfYEa1z9lHV-rOOFoKOEk2N26zw");
        this.mapView.addMapInitializedListener(this);
        this.hSpacer = new Pane();
        this.hSpacer.setMinWidth(50);
        this.vSpacer = new Pane();
        this.vSpacer.setMinHeight(60);
        this.mapBox = new VBox();
        this.mapBox.getChildren().addAll(this.vSpacer, this.mapView);
        HBox.setHgrow(this.hSpacer, Priority.ALWAYS); // This will make the spacer grow horizontally
        this.getChildren().addAll(this.hSpacer, this.mapBox); // add the spacer before the mapView
    }

    /**
     * Initialize the map
     */
    @Override
    public void mapInitialized() {
        this.mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.218371d, -1.553621d))
            .mapType(MapTypeIdEnum.ROADMAP)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .fullscreenControl(false)
            .mapTypeControl(false)
            .clickableIcons(false)
            .zoom(11);

        this.map = this.mapView.createMap(mapOptions);

        for(Compteur compteur : VeloNantes.compteurDao.getAll()){
            LatLong latLong = new LatLong(compteur.getCoordX(), compteur.getCoordY());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLong);
            Marker marker = new Marker(markerOptions);
            this.map.addMarker(marker);

            DateInfo start = VeloNantes.dateInfoDao.get(Date.valueOf(filters.getStartDate()));
            DateInfo end = VeloNantes.dateInfoDao.get(Date.valueOf(filters.getEndDate()));

            InfoWindow infoWindow = new InfoWindow();
            String content = "<h2>" + compteur.getNomCompteur() + " " + compteur.getSens() + "</h2>";
            content += "<p>Id : " + compteur.getIdCompteur() + "</p>";
            content += "<p>Coordonnées : " + compteur.getCoordX() + ", " + compteur.getCoordY() + "</p>";
            content += "<p>Quartier : " + compteur.getLeQuartier().getNomQuartier() + "</p>";
            content += "<p>Total de passages : " + compteur.totalPassages(start, end) + "</p>";
            content += "<p>Moyenne de passages : " + compteur.averagePassages(start, end) + "</p>";
            infoWindow.setContent(content);
            
            this.map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                if(this.lastOpenWindow != null){
                    this.lastOpenWindow.close();
                }
                this.lastOpenWindow = infoWindow;
                infoWindow.open(this.map, marker);
                Quartier quartier = compteur.getLeQuartier();
                this.filters.setQuartierField(quartier.getNomQuartier() + " " + quartier.getIdQuartier());
                this.filters.setCompteurField(compteur.getNomCompteur() + " " + compteur.getSens() + " " + compteur.getIdCompteur());
            });
        }
    }
}