package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.DAL.LabTestDAL;
import application.model.objects.LabTest;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class OrderTestsAnchorPane {

    @FXML
    private ResourceBundle resources;
    
    @FXML
    private AnchorPane baseAnchorPane;

    @FXML
    private URL location;

    @FXML
    private Button beginTestsBtn;

    @FXML
    private Button removeSelectedBtn;

    @FXML
    private Button selectTestsBtn;

    @FXML
    private ListView<LabTest> testToOrderListView;

    @FXML
    private ListView<LabTest> testsBeingOrderedListView;
    
    private static ArrayList<LabTest> currentLab;
    
    @FXML
    private Button finishAppointment;
    
    private FinalDiagnosisAnchorPane finalDiagnosisAnchorPane;
    private LabTestDAL labTestDAL;
    private static int currentTestIdx;
    
    @FXML
    void initialize() {
        this.validateFXMLComponents();
        this.setUpTestSelectionListView();
        this.setUpSelectTest();
        this.setUpRemoveSelectedTest();
        this.setUpBeginTest();
        this.setUpFinishAppointmentBtn();
        this.finishAppointment.setDisable(true);

    }
    
    /**
     * Instantiates a new order tests anchor pane.
     */
    public OrderTestsAnchorPane() {
    	this.finalDiagnosisAnchorPane = new FinalDiagnosisAnchorPane();
    	this.labTestDAL = new LabTestDAL();
    	currentLab = new ArrayList<LabTest>();
    	currentTestIdx = 0;
    }
    
    /**
     * Gets the current lab test.
     *
     * @return the current lab test
     */
    public static ArrayList<LabTest> getCurrentLabTest() {
    	return currentLab;
    }
    
    /**
     * Gets the current idx.
     *
     * @return the current idx
     */
    public static int getCurrentIdx() {
    	return currentTestIdx;
    }
    
    /**
     * Sets the current idx.
     *
     * @param idx the idx
     */
    public static void setCurrentIdx(int idx) {
    	currentTestIdx = idx;
    }
    
    private void setUpBeginTest() {
    	this.beginTestsBtn.setOnAction(((event)-> {
    		if (this.testsBeingOrderedListView.getItems() != null && !this.testsBeingOrderedListView.getItems().isEmpty()) {
    			for (var test : this.testsBeingOrderedListView.getItems()) {
    				currentLab.add(test);
    				var testModal = new LabTestModal();
    				testModal.openAnchorPane(Main.LAB_TEST_MODAL);
    			}
    			this.finishAppointment.setDisable(false);
    		}
    	}));
    }
    
    private void setUpFinishAppointmentBtn() {
    	this.finishAppointment.setOnAction(((event) -> {
    		this.finalDiagnosisAnchorPane.openAnchorPane((BorderPane) this.baseAnchorPane.getParent(), Main.FINAL_DIAGNOSIS_ANCHOR_PANE);
    	}));
    }
    
    private void setUpSelectTest() {
    	this.selectTestsBtn.setOnAction(((event) -> {
    		if (this.testToOrderListView.getSelectionModel().getSelectedItem() != null) {
        		var test = this.testToOrderListView.getSelectionModel().getSelectedItem();
        		this.testToOrderListView.getItems().remove(test);
        		this.testsBeingOrderedListView.getItems().add(test);
        		this.popUpConformation("Test Added");
        	} else {
        		this.popUpError("Please select a test");
        	}
    	}));
    }
    
    private void setUpRemoveSelectedTest() {
    	this.removeSelectedBtn.setOnAction(((event) -> {
    		if (this.testsBeingOrderedListView.getSelectionModel().getSelectedItem() != null) {
    			var test = this.testsBeingOrderedListView.getSelectionModel().getSelectedItem();
    			this.testsBeingOrderedListView.getItems().remove(test);
    			this.testToOrderListView.getItems().add(test);
        		this.popUpConformation("Test Removed");
    		} else {
        		this.popUpError("Please select a test");

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
    
    /**
     * Open anchor pane.
     *
     * @param parent the parent
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
    
    private void setUpTestSelectionListView() {
    	this.addTestsToOrderListView();
    }
    
    private void addTestsToOrderListView() {
		try {
			List<LabTest> tests = this.labTestDAL.getAllLabTests();
			this.testToOrderListView.getItems().addAll(FXCollections.observableArrayList(tests));

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	private void validateFXMLComponents() {
		assert this.beginTestsBtn != null : "fx:id=\"beginTestsBtn\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.removeSelectedBtn != null : "fx:id=\"removeSelectedBtn\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.selectTestsBtn != null : "fx:id=\"selectTestsBtn\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.testToOrderListView != null : "fx:id=\"testToOrderListView\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.testsBeingOrderedListView != null : "fx:id=\"testsBeingOrderedListView\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
	}

}
