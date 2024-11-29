/*
 * 
 */
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class LabTestModal.
 * 
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

	/** The submit test btn. */
	@FXML
	private Button submitTestBtn;

	@FXML
	private Label labTestName;

	private LabTestModalViewModel viewModel;

	private LabTest currentTest;

	/**
	 * Initialize.
	 */
	@FXML
	void initialize() {
		this.currentTest = OrderTestsAnchorPane.getCurrentLabTest().get(OrderTestsAnchorPane.getCurrentIdx());
		OrderTestsAnchorPane.setCurrentIdx(OrderTestsAnchorPane.getCurrentIdx() + 1);

		this.validateFXMLComponents();
		this.setUpName(this.currentTest);
		this.setUpSubmitTestBtn();
	}

	/**
	 * Instantiates a new lab test modal.
	 */
	public LabTestModal() {
		this.viewModel = new LabTestModalViewModel();
	}

	private void setUpSubmitTestBtn() {
		this.submitTestBtn.setOnAction(((event) -> {

			this.viewModel.setTestName(this.labTestName.getText());
			var test = OrderTestsAnchorPane.getCurrentLabTest()
					.remove(OrderTestsAnchorPane.getCurrentLabTest().size() - 1);
			this.viewModel.submitTestData(test);
			this.popUpConformation("Test Ordered");
			var stage = (Stage) this.submitTestBtn.getScene().getWindow();
			stage.close();

		}));
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
		assert this.cancelBtn != null
				: "fx:id=\"cancelBtn\" was not injected: check your FXML file 'LabTestModal.fxml'.";
		assert this.submitTestBtn != null
				: "fx:id=\"submitTestBtn\" was not injected: check your FXML file 'LabTestModal.fxml'.";
		assert this.labTestName != null
				: "fx:id=\"labTestName\" was not injected: check your FXML file 'LabTestModal.fxml'.";

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

	/**
	 * Gets the current test.
	 *
	 * @return the current test
	 */
	public LabTest getCurrentTest() {
		return this.currentTest;
	}

	/**
	 * Sets the current test.
	 *
	 * @param currentTest the new current test
	 */
	public void setCurrentTest(LabTest currentTest) {
		this.currentTest = currentTest;
	}

}
