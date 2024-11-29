package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.DAL.UserDAL;
import application.model.credentials.ActiveUser;
import application.model.credentials.UserRole;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AdminQueryAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Button genButton;

    @FXML
    private Button exeQueryButton;

    @FXML
    private TextArea queryTextBox;

    @FXML
    private TableView<Map<String, Object>> resultsTableView;

    @FXML
    private AnchorPane searchAnchorPane;
    
    private UserDAL userDAL;
    /**
     * adminqueryanchorpane
     */
    public AdminQueryAnchorPane() {
    	this.userDAL = new UserDAL();
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

    @FXML
    void initialize() {
    	this.validateFXML();
    	this.setupButtons();
    }
    
    private void setupButtons() {
        this.exeQueryButton.setOnAction((event) -> {
            if (ActiveUser.getActiveUser().getRole() == UserRole.ADMIN) {
                List<Map<String, Object>> queryResults = this.userDAL.exeAdminQuery(this.queryTextBox.getText());
                this.populateTableView(queryResults);
            } else {
                this.popUpError("Must be Admin to Run Query");
            }
        });
        this.genButton.setOnAction((event) -> {
        	if (this.startDatePicker.valueProperty().getValue() == null || this.endDatePicker.valueProperty().getValue() == null) {
        		this.popUpError("Both Dates must have a valid Selection");
        	} else {
                if (ActiveUser.getActiveUser().getRole() == UserRole.ADMIN) {
                	this.userDAL.generateReport(this.startDatePicker.valueProperty().getValue(), this.endDatePicker.valueProperty().getValue());
                } else {
                    this.popUpError("Must be Admin to Run Reports");
                }
        	}
        });
    }
    
    private void populateTableView(List<Map<String, Object>> queryResults) {
        this.resultsTableView.getColumns().clear();
        this.resultsTableView.getItems().clear();
        if (queryResults.isEmpty()) {
            this.popUpError("No results found.");
            return;
        }
        Map<String, Object> firstRow = queryResults.get(0);

        for (String columnName : firstRow.keySet()) {
            TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
            column.setCellValueFactory(data -> {
                Map<String, Object> row = data.getValue();
                return new SimpleObjectProperty<>(row.get(columnName));
            });
            this.resultsTableView.getColumns().add(column);
        }
        this.resultsTableView.getItems().addAll(queryResults);
    }
    
	private void popUpError(String reasonForError) {
		var errorPopUp = new Alert(AlertType.ERROR);
		errorPopUp.setContentText(reasonForError);
		errorPopUp.showAndWait();
	}
    
    private void validateFXML() {
        assert this.exeQueryButton != null : "fx:id=\"exeQueryButton\" was not injected: check your FXML file 'AdminQueryAnchorPage.fxml'.";
        assert this.endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'AdminQueryAnchorPane.fxml'.";
        assert this.genButton != null : "fx:id=\"genButton\" was not injected: check your FXML file 'AdminQueryAnchorPane.fxml'.";
        assert this.queryTextBox != null : "fx:id=\"queryTextBox\" was not injected: check your FXML file 'AdminQueryAnchorPage.fxml'.";
        assert this.resultsTableView != null : "fx:id=\"resultsTableView\" was not injected: check your FXML file 'AdminQueryAnchorPage.fxml'.";
        assert this.startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'AdminQueryAnchorPane.fxml'.";
        assert this.searchAnchorPane != null : "fx:id=\"searchAnchorPane\" was not injected: check your FXML file 'AdminQueryAnchorPage.fxml'.";
    }

}
