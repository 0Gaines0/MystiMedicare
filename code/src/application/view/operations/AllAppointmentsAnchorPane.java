package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.DAL.AppointmentDAL;
import application.model.credentials.Appointment;
import application.model.credentials.Patient;
import application.viewModel.operations.AllAppointmentsViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * The Class AllAppointmentsAnchorPane.
 * @author Jeffrey Gaines
 */
public class AllAppointmentsAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Map<String, Object>> resultsTableView;

    @FXML
    private ListView<Appointment> appointmentsListView;

    @FXML
    private ComboBox<Patient> patientComboBox;
    
    private AllAppointmentsViewModel allAppointmentsViewModel;
    private AppointmentDAL apptDAL;

    @FXML
    void initialize() {
        this.validateFXMLComponents();
        this.bindToViewModel();
        this.setUpPatientTextField();
        this.setUpAppointmentsListView();
    }
    
    /**
     * Instantiates a new all appointments anchor pane.
     */
    public AllAppointmentsAnchorPane() {
    	this.allAppointmentsViewModel = new AllAppointmentsViewModel();
    	this.apptDAL = new AppointmentDAL();
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
			error.printStackTrace();
		}
	}
    
    private void bindToViewModel() {
    	this.allAppointmentsViewModel.getPatientAppointments().bindBidirectional(this.appointmentsListView.itemsProperty());
    }
    
    private void setUpPatientTextField() {
    	var patient = SearchPatientAnchorPane.getSelectedPatient();
		this.patientComboBox.getItems().clear();
    	this.patientComboBox.getItems().add(patient);
    	this.patientComboBox.setValue(patient);
    	this.patientComboBox.setEditable(false);
    }
    
    private void setUpAppointmentsListView() {
        this.allAppointmentsViewModel.pullAllPatientsCurrentAppointments();
        this.appointmentsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
    	        List<Map<String, Object>> queryResults;
				try {
					queryResults = this.apptDAL.getAppointmentDetailsForTable(newValue.getId());
	    	        this.populateTableView(queryResults);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
    }
    
    private void populateTableView(List<Map<String, Object>> queryResults) {
        this.resultsTableView.getColumns().clear();
        this.resultsTableView.getItems().clear();
        if (queryResults.isEmpty()) {
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


	private void validateFXMLComponents() {
        assert this.resultsTableView != null : "fx:id=\"resultsTableView\" was not injected: check your FXML file 'AdminQueryAnchorPage.fxml'.";
        assert this.appointmentsListView != null : "fx:id=\"appointmentsListView\" was not injected: check your FXML file 'AllAppointmentsAnchorPane.fxml'.";
        assert this.patientComboBox != null : "fx:id=\"patientComboBox\" was not injected: check your FXML file 'AllAppointmentsAnchorPane.fxml'.";
	}

}
