package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.credentials.Appointment;
import application.model.credentials.Patient;
import application.viewModel.operations.AllAppointmentsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
    private ListView<Appointment> appointmentsListView;

    @FXML
    private ComboBox<Patient> patientComboBox;
    
    private AllAppointmentsViewModel allAppointmentsViewModel;

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
    }

	private void validateFXMLComponents() {
		assert this.appointmentsListView != null : "fx:id=\"appointmentsListView\" was not injected: check your FXML file 'AllAppointmentsAnchorPane.fxml'.";
        assert this.patientComboBox != null : "fx:id=\"patientComboBox\" was not injected: check your FXML file 'AllAppointmentsAnchorPane.fxml'.";
	}

}
