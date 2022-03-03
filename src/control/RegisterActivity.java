package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Activity;
import model.ActivityType;
import model.ExpensesData;
import model.IncomeData;

public class RegisterActivity implements Initializable{
	@FXML
	private Button addBTN;

	@FXML
	private TextField quantityTF;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextField descriptionTF;

	@FXML
	private ComboBox<ActivityType> typeBX;

	@FXML
	private Button cancelBTN;

	@FXML
	void add(ActionEvent event) {
		String errorMessage = "";
		Alert alert = new Alert(AlertType.WARNING);
		
		if (quantityTF.getText().trim().isEmpty()) {
			errorMessage += "- Please provide the quantity of the activity.\n";
		}
		if (datePicker.getValue() == null) {
			errorMessage += "- Please provide a date for the activity.\n";
		}
		if (descriptionTF.getText().trim().isEmpty()) {
			errorMessage += "- Please provide a description for the activity.\n";
		}
		if (typeBX.getValue() == null) {
			errorMessage += "- Please specify the type of activity";
		}
		
		if (errorMessage.length() > 0) {
			alert.setTitle("Warning");
			alert.setHeaderText("Incomplete fields");
			alert.setContentText(errorMessage);
			
			alert.show();
		} else {
			try {
				Activity addedActivity = new Activity(descriptionTF.getText(), datePicker.getValue(), Double.parseDouble(quantityTF.getText()), typeBX.getValue());
				
				if (typeBX.getValue() == ActivityType.EXPENSE) {
					ExpensesData.data.add(addedActivity);
				} else {
					IncomeData.data.add(addedActivity);
				}
				
		    	Stage stage = (Stage) this.addBTN.getScene().getWindow();
		    	stage.close();
			} catch (NumberFormatException e) {
				alert.setTitle("Warning");
				alert.setHeaderText("Wrong format");
				alert.setContentText("Please provide the correct format for the quantity");
				
				alert.show();
			}
		}
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) this.addBTN.getScene().getWindow();
    	stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		typeBX.getItems().setAll(ActivityType.values());
	}
}
