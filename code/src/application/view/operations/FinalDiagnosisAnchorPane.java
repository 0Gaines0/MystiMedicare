package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.objects.LabTest;
import application.viewModel.operations.FinalDiagnosisAnchorPaneViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class FinalDiagnosisAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea finalDiagnosisTextArea;

    @FXML
    private ListView<LabTest> testBeingDone;

    private FinalDiagnosisAnchorPaneViewModel viewModel;
    
    @FXML
    void initialize() {
        this.validateFXMLComponents();
        this.bindToViewModel();
    	this.viewModel.populateTestList();


    }
    
    /**
     * Instantiates a new final diagnosis anchor pane.
     */
    public FinalDiagnosisAnchorPane() {
    	this.viewModel = new FinalDiagnosisAnchorPaneViewModel();
    }

	private void validateFXMLComponents() {
		assert this.finalDiagnosisTextArea != null : "fx:id=\"finalDiagnosisTextArea\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";
        assert this.testBeingDone != null : "fx:id=\"testBeingDone\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";
	}
	
	private void bindToViewModel() {
		this.finalDiagnosisTextArea.textProperty().bindBidirectional(this.viewModel.getFinalDiagnosisTextProperty());
		this.viewModel.getTestBeingListProperty().bindBidirectional(this.testBeingDone.itemsProperty());
	}
	

	
	
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

}
