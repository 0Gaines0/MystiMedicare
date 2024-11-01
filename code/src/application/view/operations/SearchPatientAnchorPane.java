package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SearchPatientAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private ListView<?> patientListview;

    @FXML
    private AnchorPane searchAnchorPane;

    @FXML
    private Button searchButton;
    
	/**
	 * Open anchor pane.
	 *
	 * @param parent        the parent
	 * @param newAnchorPath the new anchor path
	 */
	public void openAnchorPane(BorderPane parent, String newAnchorPath) {
		try {
			AnchorPane currentAnchor = (AnchorPane) parent.getCenter();
			var loader = new FXMLLoader(getClass().getResource(newAnchorPath));
			AnchorPane newAnchor = loader.load();
			parent.setCenter(newAnchor);
			parent.getChildren().remove(currentAnchor);
		} catch (IOException error) {
			error.getMessage();
		}
	}

    @FXML
    void initialize() {
        assert this.dobPicker != null : "fx:id=\"dobPicker\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.fnameField != null : "fx:id=\"fnameField\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.lnameField != null : "fx:id=\"lnameField\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.patientListview != null : "fx:id=\"patientListview\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.searchAnchorPane != null : "fx:id=\"searchAnchorPane\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";

    }

}
