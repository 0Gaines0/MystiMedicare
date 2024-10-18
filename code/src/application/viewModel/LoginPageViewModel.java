package application.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginPageViewModel {
	
	private StringProperty userIdProperty;
	private StringProperty passwordProperty;
	
	
	public LoginPageViewModel() {
		this.userIdProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
	}
	
	public boolean userLoginIsSuccessful() {
		var userId = this.userIdProperty.getValue().trim();
		var password = this.passwordProperty.getValue().trim();
		
		//TODO 
		// Implement ability to check if user exist in database
		
		return false;
	}
	
}
