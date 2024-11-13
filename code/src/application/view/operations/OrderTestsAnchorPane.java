package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.model.objects.LabTest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class OrderTestsAnchorPane {

    @FXML
    private ResourceBundle resources;

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

    @FXML
    void initialize() {
        this.validateFXMLComponents();

    }
    
    /**
     * Instantiates a new order tests anchor pane.
     */
    public OrderTestsAnchorPane() {
    	
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
    	List<LabTest> tests = new ArrayList<>();

        tests.add(new LabTest("White Blood Cell (WBC)", 4.0, 11.0));
        tests.add(new LabTest("Low Density Lipoproteins (LDL)", 0.0, 3.0));
        tests.add(new LabTest("Hepatitis A Test", 0.0, 0.0));
        tests.add(new LabTest("Hepatitis B Test", 0.0, 0.0));
        this.testToOrderListView.getItems().addAll(tests);
    }

	private void validateFXMLComponents() {
		assert this.beginTestsBtn != null : "fx:id=\"beginTestsBtn\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.removeSelectedBtn != null : "fx:id=\"removeSelectedBtn\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.selectTestsBtn != null : "fx:id=\"selectTestsBtn\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.testToOrderListView != null : "fx:id=\"testToOrderListView\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
        assert this.testsBeingOrderedListView != null : "fx:id=\"testsBeingOrderedListView\" was not injected: check your FXML file 'OrderTestsAnchorPane.fxml'.";
	}

}
