

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {

    	try {
    		
    		double distMedia = Double.parseDouble(this.distanzaMinima.getText());
    		model.creaGrafo(distMedia);
    		this.cmbBoxAeroportoPartenza.getItems().addAll(model.vertex());
    		
    	} catch (NumberFormatException e) {
    		
    		e.printStackTrace();
    		txtResult.appendText("Inserisci un input numerico corretto!");
    		
    	}
    	
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {

    	txtResult.clear();
    	Airport airport = this.cmbBoxAeroportoPartenza.getValue();
    	
    	if(airport != null) {
    		
    		for(Airport airport2 : model.cercaConnessi(airport))
    			txtResult.appendText(airport2.toString()+"\n");
    		
    	} else txtResult.appendText("Seleziona almeno un aeroporto di partenza!");
    			
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {

    	txtResult.clear();
    	
    	try {
    		
    		double miglia = Double.parseDouble(numeroVoliTxtInput.getText());
    		Airport airport = this.cmbBoxAeroportoPartenza.getValue();
    		for(Airport airport2 : model.trovaBest(airport,miglia))
    			txtResult.appendText(airport2.toString()+"\n");
    		txtResult.appendText("Miglia totali percorse: "+model.getMiglia(model.trovaBest(airport,miglia),null));
    		
    	}catch (NumberFormatException e) {
			// TODO: handle exception
    		e.printStackTrace();
    		txtResult.appendText("Inserisci dati esatti in input!");
		}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
		
	}
}

