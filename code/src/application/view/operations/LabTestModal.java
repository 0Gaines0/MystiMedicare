package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.objects.LabTest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class LabTestModal.
 * @author Jeffrey Gaines
 */
public class LabTestModal {

    /** The resources. */
    @FXML
    private ResourceBundle resources;

    /** The location. */
    @FXML
    private URL location;

    /** The cancel btn. */
    @FXML
    private Button cancelBtn;

    /** The high value. */
    @FXML
    private TextField highValue;

    /** The low value. */
    @FXML
    private TextField lowValue;

    /** The submit test btn. */
    @FXML
    private Button submitTestBtn;
    
    @FXML
    private Label labTestName;
    
  

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        this.validateFXMLComponents();
        this.setUpName(OrderTestsAnchorPane.getCurrentLabTest());
    }
    
    /**
     * Instantiates a new lab test modal.
     */
    public LabTestModal() {
    	
    }
    
	/**
	 * Validate FXML components.
	 */
	private void validateFXMLComponents() {
		assert this.cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'LabTestModal.fxml'.";
        assert this.highValue != null : "fx:id=\"highValue\" was not injected: check your FXML file 'LabTestModal.fxml'.";
        assert this.lowValue != null : "fx:id=\"lowValue\" was not injected: check your FXML file 'LabTestModal.fxml'.";
        assert this.submitTestBtn != null : "fx:id=\"submitTestBtn\" was not injected: check your FXML file 'LabTestModal.fxml'.";
        assert this.labTestName != null : "fx:id=\"labTestName\" was not injected: check your FXML file 'LabTestModal.fxml'.";

	}
	
	
    /**
     * Open anchor pane.
     *
     * @param newAnchorPath the new anchor path
     */
    public void openAnchorPane(String newAnchorPath) {
		var newStage = new Stage();
		var loader = new FXMLLoader(getClass().getResource(newAnchorPath));
		Parent parent;
		try {
			parent = loader.load();
			var scene = new Scene(parent);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(((Stage) (parent.getScene().getWindow())));
			newStage.setTitle(Main.WINDOW_TITLE);
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    

	private void setUpName(LabTest test) {
		this.labTestName.setText(test.getName());
	}

}
