package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.objects.LabTest;
import application.viewModel.operations.FinalDiagnosisAnchorPaneViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class FinalDiagnosisAnchorPane {

	@FXML
	private AnchorPane baseAnchorPane;

	@FXML
	private TextArea finalDiagnosisTextArea;

	@FXML
	private TextArea finalResultTextArea;

	@FXML
	private CheckBox isAbnormalCheckBox;

	@FXML
	private Button submitResultBtn;

	@FXML
	private Button finishAppointmentBtn;

	@FXML
	private ListView<LabTest> testBeingDone;

	@FXML
	private Button submitFinalDiagnosis;

	private FinalDiagnosisAnchorPaneViewModel viewModel;

	private SelectAppointmentAnchorPane selectAppointmentPage;

	@FXML
	void initialize() {
		this.validateFXMLComponents();
		this.bindToViewModel();
		this.setUpFinalResultsBtn();
		this.setUpFinalDiagnosis();
		this.setUpFinishAppointment();
		this.finishAppointmentBtn.setDisable(true);
		this.viewModel.populateTestList();
		this.selectAppointmentPage = new SelectAppointmentAnchorPane();

	}

	private void setUpFinalResultsBtn() {
		this.submitResultBtn.setOnAction(((event) -> {
			if (this.testBeingDone.getSelectionModel().getSelectedItem() != null) {
				if (this.viewModel.insertFinalResultWithTest()) {
					this.testBeingDone.getItems().remove(this.testBeingDone.getSelectionModel().getSelectedItem());
					this.popUpConformation("Test results added!");
				} else {
					this.popUpError("Test results not added, please try again");
				}
			} else {
				this.popUpError("Test not selected so Final Diagnosis can not be added");
			}
		}));
	}

	private void setUpFinalDiagnosis() {
		this.submitFinalDiagnosis.setOnAction(((event) -> {
			if (this.finalDiagnosisTextArea.getText() != null && !this.finalDiagnosisTextArea.getText().isBlank()) {
				this.viewModel.insertFinalDiagnosis();
				this.popUpConformation("Final Diagnosis Added");
				this.finishAppointmentBtn.setDisable(false);
			} else {
				this.popUpError("Please put data in the final diagnosis text area");
			}
		}));
	}

	private void setUpFinishAppointment() {
		this.finishAppointmentBtn.setOnAction(((event) -> {
			this.viewModel.finishAppointment();
			this.popUpConformation("The appointment is finished!");
			this.selectAppointmentPage.openAnchorPane((BorderPane) this.baseAnchorPane.getParent(),
					Main.SELECT_APPOINTMENT_ANCHOR_PANE);
		}));
	}

	private void popUpError(String reasonForError) {
		var errorPopUp = new Alert(AlertType.ERROR);
		errorPopUp.setContentText(reasonForError);
		errorPopUp.showAndWait();
	}

	private void popUpConformation(String confirm) {
		var errorPopUp = new Alert(AlertType.CONFIRMATION);
		errorPopUp.setContentText(confirm);
		errorPopUp.showAndWait();
	}

	/**
	 * Instantiates a new final diagnosis anchor pane.
	 */
	public FinalDiagnosisAnchorPane() {
		this.viewModel = new FinalDiagnosisAnchorPaneViewModel();
	}

	private void validateFXMLComponents() {
		assert this.finalDiagnosisTextArea != null
				: "fx:id=\"finalDiagnosisTextArea\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";
		assert this.testBeingDone != null
				: "fx:id=\"testBeingDone\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";
		assert this.finalResultTextArea != null
				: "fx:id=\"finalResultTextArea\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";
		assert this.isAbnormalCheckBox != null
				: "fx:id=\"isAbnormalCheckBox\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";
		assert this.submitResultBtn != null
				: "fx:id=\"submitResultBtn\" was not injected: check your FXML file 'FinalDiagnosisAnchorPane.fxml'.";

	}

	private void bindToViewModel() {
		this.finalDiagnosisTextArea.textProperty().bindBidirectional(this.viewModel.getFinalDiagnosisTextProperty());
		this.viewModel.getTestBeingListProperty().bindBidirectional(this.testBeingDone.itemsProperty());
		this.viewModel.getSelectedTestProperty().bind(this.testBeingDone.getSelectionModel().selectedItemProperty());
		this.isAbnormalCheckBox.selectedProperty().bindBidirectional(this.viewModel.getIsAbnormalProperty());
		this.finalResultTextArea.textProperty().bindBidirectional(this.viewModel.getFinalResultTextProperty());
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
