package es.ideas;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class PrimaryController implements Initializable {

    @FXML
    private ChoiceBox<String> cbSemana;
    @FXML
    private ToggleButton tgbIt;
    @FXML
    private ToggleButton tgbEnUs;
    @FXML
    private ToggleButton tgbSp;
    @FXML
    private ToggleButton tgbFr;
    @FXML
    private ToggleButton tgbEnUk;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnSalir;
    
    private MultiLenguajeUI aplicacionPrincipal;


    public void setMainWindow(MultiLenguajeUI main){
        this.aplicacionPrincipal= main;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Inicialización del ComboBox con los días de la semana
        //Obtenemos los string del paquete de recursos mediante su "key"
        String dias_semana[] = {rb.getString("lunes"), 
                                rb.getString("martes"),
                                rb.getString("miercoles"),
                                rb.getString("jueves"),
                                rb.getString("viernes"),
                                rb.getString("sabado"),
                                rb.getString("domingo")};
        cbSemana.setItems(FXCollections.observableArrayList(dias_semana));

        //Crear un ToggleGroup para agrupar los ToggleButton
        //Sólo puede haber uno seleccionado cada vez.
        ToggleGroup tg= new ToggleGroup();
        tg.getToggles().addAll(tgbIt,tgbEnUs,tgbSp,tgbFr,tgbEnUk);
        
        /**
         * Listener para cambiar el idioma obteniendo el texto de los botones
         **/        
        tg.selectedToggleProperty().addListener((obs,oldValue,newValue)->{
            if (newValue != null ){
               ToggleButton tb = (ToggleButton) newValue.getToggleGroup()
                       .getSelectedToggle();               
               //Se comprueba el valor del Texto del ToggleButton        
               switch (tb.getText()){
                   case "Italiano":
                       Locale.setDefault(Locale.ITALIAN);
                       System.out.println("Has seleccionado idioma ITALIANO");
                       break;  
                   case "Inglés(en-Us)":
                       Locale.setDefault(Locale.US);
                       System.out.println("Has seleccionado idioma INGLÉS - "
                               + "ESTADOS UNIDOS");
                       break;  
                   case "Francés":
                       Locale.setDefault(Locale.FRENCH);
                       System.out.println("Has seleccionado idioma FRANCÉS");
                       break;
                   case "Inglés(en-Gb)":
                       Locale.setDefault(Locale.UK);
                       System.out.println("Has seleccionado idioma INGLÉS -"
                               + "REINO UNIDO");
                       break;
                   default:
                       Locale.setDefault(new Locale("es"));
                       System.out.println("Has seleccionado idioma ESPAÑOL");                       
               }
               try{
                Parent pane = cargadorFXML().load();
                MultiLenguajeUI.getPrimaryStage().getScene().setRoot(pane);
               }catch(IOException ieo){                   
               }               
               MultiLenguajeUI.getPrimaryStage().show();
            }
               
        });        
    }
    
    private FXMLLoader cargadorFXML(){
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("es.ideas.i18n/cadenas",
                Locale.getDefault()));
        loader.setLocation(getClass().getResource("primary.fxml"));
        return loader;
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
}
