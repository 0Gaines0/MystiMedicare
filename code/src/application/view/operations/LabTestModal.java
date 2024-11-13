package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.objects.LabTest;
import application.viewModel.operations.LabTestModalViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    
    private LabTestModalViewModel viewModel;
    
  

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        this.validateFXMLComponents();
        this.bindToViewModel();
        this.setUpName(OrderTestsAnchorPane.getCurrentLabTest());
        this.setUpSubmitTestBtn();
    }
    
    private void bindToViewModel() {
    	this.lowValue.textProperty().bindBidirectional(this.viewModel.getLowValueProperty());
    	this.highValue.textProperty().bindBidirectional(this.viewModel.getHighValueProperty());
    }
    
    /**
     * Instantiates a new lab test modal.
     */
    public LabTestModal() {
    	this.viewModel = new LabTestModalViewModel();
    }
    
    private void setUpSubmitTestBtn() {
    	this.submitTestBtn.setOnAction(((event) -> {
    		var result = this.validateFields();
    		if (result.isBlank()) {
    	    	this.viewModel.setTestName(this.labTestName.getText());
    			this.viewModel.submitTestData();
    			this.popUpConformation("Test Submitted");
    			var stage = (Stage) this.submitTestBtn.getScene().getWindow();
    			stage.close();
    		} else {
    			this.popUpError(result);
    		}
    	}));
    }
    
	private String validateFields() {
		var result = "";

	    if (this.lowValue.getText() == null || this.lowValue.getText().isBlank()) {
	        result += "Low value must be inputted and valid." + System.lineSeparator();
	    } else if (!this.isDecimal(this.lowValue.getText())) {
	        result += "Low value must be a decimal number." + System.lineSeparator();
	    }

	    if (this.highValue.getText() == null || this.highValue.getText().isBlank()) {
	        result += "High value must be inputted and valid." + System.lineSeparator();
	    } else if (!this.isDecimal(this.highValue.getText())) {
	        result += "High value must be a decimal number." + System.lineSeparator();
	    }

	    if (this.lowValue.getText() != null && this.highValue.getText() != null && this.isDecimal(this.lowValue.getText()) && this.isDecimal(this.highValue.getText())) {
	        double low = Double.parseDouble(this.lowValue.getText());
	        double high = Double.parseDouble(this.highValue.getText());
	        if (low >= high) {
	            result += "Low value must be less than high value." + System.lineSeparator();
	        }
	    }

	    return result;
	}
	
	private boolean isDecimal(String text) {
	    try {
	        Double.parseDouble(text);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	private void popUpError(String reasonForError) {
		var errorPopUp = new Alert(AlertType.ERROR);
		errorPopUp.setContentText(reasonForError);
		errorPopUp.showAndWait();
	}

	private void popUpConformation(String reasonForConfirm) {
		var errorPopUp = new Alert(AlertType.CONFIRMATION);
		errorPopUp.setContentText(reasonForConfirm);
		errorPopUp.showAndWait();
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
