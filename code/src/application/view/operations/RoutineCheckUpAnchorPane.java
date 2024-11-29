package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import application.viewModel.operations.RoutineCheckUpAnchorPaneViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RoutineCheckUpAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private AnchorPane baseAnchorPane;
    
    @FXML
    private ComboBox<Doctor> doctorComboBox;

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private TextField diastolicBloodPressureTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField pulseTextField;

    @FXML
    private TextField systolicPressureTextField;

    @FXML
    private Button uploadResultBtn;

    @FXML
    private TextField weightTextField;
    
    @FXML
    private TextArea initialDiagnosisTextArea;
    
    @FXML
    private TextField symptomsTextField;
    
    @FXML
    private TextField tempTextField;
    
    @FXML
    private Button orderTestsBtn;
    
    private RoutineCheckUpAnchorPaneViewModel routineViewModel;
    private OrderTestsAnchorPane orderTestCodeBehind;
    
    

    @FXML
    void initialize() {
        this.validateFXMLComponents();
        this.bindToViewModel();
        this.setUpUploadResultsBtn();
        this.setUpOrderTestBtn();
        this.setUpPatientComboBox(SelectAppointmentAnchorPane.getSelectedAppointment().getPatient());
        this.setUpDoctorsComboBox(SelectAppointmentAnchorPane.getSelectedAppointment().getDoctor());
    }
    
    private void setUpOrderTestBtn() {
    	this.orderTestsBtn.setDisable(true);
    	this.orderTestsBtn.setOnAction(((event)-> {
    		this.orderTestCodeBehind.openAnchorPane((BorderPane) this.baseAnchorPane.getParent(), Main.ORDER_TEST_ANCHOR_PANE);
    	}));
	}

	/**
     * Instantiates a new routine check up anchor pane.
     */
    public RoutineCheckUpAnchorPane() {
    	this.routineViewModel = new RoutineCheckUpAnchorPaneViewModel();
    	this.orderTestCodeBehind = new OrderTestsAnchorPane();
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
    
    private void setUpUploadResultsBtn() {
    	this.uploadResultBtn.setOnAction(((event) -> {
    		var result = this.validateAllFields();
    		if (!result.isBlank()) {
    			this.popUpError(result);
    		} else {
    			var uploaded = this.routineViewModel.uploadRoutineCheckUp();
    			if (uploaded) {
    				this.popUpConformation("Visit data uploaded");
    				this.orderTestsBtn.setDisable(false);
    			} else {
    				this.popUpError("Visit data was not uploaded, try again");
    			}
    		}
    	}));
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
    
    private String validateAllFields() {
        var result = "";
        result = this.validatePressures(result);
        if (this.pulseTextField.getText() == null || this.pulseTextField.getText().isBlank()) {
            result += "Pulse must be inputted and valid." + System.lineSeparator();
        } else if (!this.isInteger(this.pulseTextField.getText())) {
            result += "Pulse must be an integer." + System.lineSeparator();
        }
        if (this.weightTextField.getText() == null || this.weightTextField.getText().isBlank()) {
            result += "Weight must be inputted and valid." + System.lineSeparator();
        } else if (!this.isDecimal(this.weightTextField.getText())) {
            result += "Weight must be a decimal number." + System.lineSeparator();
        }
        if (this.heightTextField.getText() == null || this.heightTextField.getText().isBlank()) {
            result += "Height must be inputted and valid." + System.lineSeparator();
        } else if (!this.isDecimal(this.heightTextField.getText())) {
            result += "Height must be a decimal number." + System.lineSeparator();
        }
        if (this.symptomsTextField.getText() == null || this.symptomsTextField.getText().isBlank()) {
        	result += "Symptoms must not be empty" + System.lineSeparator();
        }
        if (this.tempTextField.getText() == null || this.tempTextField.getText().isBlank()) {
            result += "Temp must be inputted and valid." + System.lineSeparator();
        } else if (!this.isDecimal(this.tempTextField.getText())) {
            result += "Temp must be a decimal number." + System.lineSeparator();
        }
        if (this.initialDiagnosisTextArea.getText() != null) {
        	if (!this.initialDiagnosisTextArea.getText().matches("[a-zA-Z\\s]*") && !this.initialDiagnosisTextArea.getText().isBlank()) {
                result += "Inital Diagnosis must be text." + System.lineSeparator();
            }
        }
        return result;
    }

	private String validatePressures(String result) {
		if (this.systolicPressureTextField.getText() == null || this.systolicPressureTextField.getText().isBlank()) {
            result += "Systolic pressure must be inputted and valid." + System.lineSeparator();
        } else if (!this.isInteger(this.systolicPressureTextField.getText())) {
            result += "Systolic pressure must be an integer." + System.lineSeparator();
        }
        if (this.diastolicBloodPressureTextField.getText() == null || this.diastolicBloodPressureTextField.getText().isBlank()) {
            result += "Diastolic pressure must be inputted and valid." + System.lineSeparator();
        } else if (!this.isInteger(this.diastolicBloodPressureTextField.getText())) {
            result += "Diastolic pressure must be an integer." + System.lineSeparator();
        }
		return result;
	}
    
	private void setUpPatientComboBox(Patient patient) {
		this.patientComboBox.getItems().clear();
		this.patientComboBox.getItems().add(patient);
		this.patientComboBox.setValue(patient);
	}

	private void setUpDoctorsComboBox(Doctor doctor) {
		this.doctorComboBox.setValue(doctor);
	}
    
    private void bindToViewModel() {
        this.systolicPressureTextField.textProperty().bindBidirectional(this.routineViewModel.getSystolicPressureProperty());
        this.diastolicBloodPressureTextField.textProperty().bindBidirectional(this.routineViewModel.getDiastolicBloodPressureProperty());
        this.pulseTextField.textProperty().bindBidirectional(this.routineViewModel.getPulseProperty());
        this.heightTextField.textProperty().bindBidirectional(this.routineViewModel.getHeightProperty());
        this.weightTextField.textProperty().bindBidirectional(this.routineViewModel.getWeightProperty());
        this.symptomsTextField.textProperty().bindBidirectional(this.routineViewModel.getSymptomsProperty());
        this.tempTextField.textProperty().bindBidirectional(this.routineViewModel.getTempProperty());
        this.initialDiagnosisTextArea.textProperty().bindBidirectional(this.routineViewModel.getInitalDiagnosis());
    }


    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDecimal(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	private void validateFXMLComponents() {
		assert this.diastolicBloodPressureTextField != null : "fx:id=\"diastolicBloodPressureTextField\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.heightTextField != null : "fx:id=\"heightTextField\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.pulseTextField != null : "fx:id=\"pulseTextField\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.doctorComboBox != null : "fx:id=\"doctorComboBox\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.patientComboBox != null : "fx:id=\"patientComboBox\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.systolicPressureTextField != null : "fx:id=\"systolicPressureTextField\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.uploadResultBtn != null : "fx:id=\"uploadResultBtn\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
        assert this.weightTextField != null : "fx:id=\"weightTextField\" was not injected: check your FXML file 'RoutineCheckUpAnchorPane.fxml'.";
	}

}
