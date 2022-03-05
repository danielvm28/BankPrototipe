package control;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Activity;
import model.ExpensesData;
import model.IncomeData;

public class MainController implements Initializable{
	@FXML
    private TableView<Activity> incomeTable;

    @FXML
    private TableColumn<Activity, LocalDate> incomeDateCol;

    @FXML
    private TableColumn<Activity, String> incomeDescCol;

    @FXML
    private TableColumn<Activity, String> incomeQuantCol;

    @FXML
    private TableView<Activity> expensesTable;

    @FXML
    private TableColumn<Activity, LocalDate> expensesDateCol;

    @FXML
    private TableColumn<Activity, String> expensesDescCol;

    @FXML
    private TableColumn<Activity, String> expensesQuantCol;

    @FXML
    private Text moneyTXT;

    @FXML
    private DatePicker beginDP;

    @FXML
    private DatePicker endDP;

    @FXML
    private Button restoreBTN;

    @FXML
    private MenuItem newActivityBTN;
    
    @FXML
    private Button updateBTN;
    
    @FXML
    private Text incomeTXT;

    @FXML
    private Text expensesTXT;
    
    @FXML
    private Button deleteBTN;
    
    @FXML
    private Button applyFilterBTN;
    
    private Activity incomeActivityClicked;
    
    private Activity expenseActivityClicked;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	// Sets the default values for the table
		incomeDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		incomeDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		incomeQuantCol.setCellValueFactory(new PropertyValueFactory<>("quantityStr"));
		
		expensesDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		expensesDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		expensesQuantCol.setCellValueFactory(new PropertyValueFactory<>("quantityStr"));
		
		incomeTable.setItems(IncomeData.data);
		expensesTable.setItems(ExpensesData.data);
		
		incomeTable.setOnMouseClicked(event -> {
			incomeActivityClicked = incomeTable.getSelectionModel().getSelectedItem();
		});
		
		expensesTable.setOnMouseClicked(event -> {
			expenseActivityClicked = expensesTable.getSelectionModel().getSelectedItem();
		});
	}
    
    @FXML
    void filterDate(ActionEvent event) {
    	// Creates two new observable lists to store the filtered items
    	ObservableList<Activity> incomeFiltered = FXCollections.observableArrayList();
    	ObservableList<Activity> expensesFiltered = FXCollections.observableArrayList();
    	Alert alert = new Alert(AlertType.WARNING);
    	
    	try {
    		for (Activity activity : IncomeData.data) {
    			if (activity.getDate().isAfter(beginDP.getValue()) && activity.getDate().isBefore(endDP.getValue())) {
    				incomeFiltered.add(activity);
    			}
    		}
    		
    		for (Activity activity : ExpensesData.data) {
    			if (activity.getDate().isAfter(beginDP.getValue()) && activity.getDate().isBefore(endDP.getValue())) {
    				expensesFiltered.add(activity);
    			}
    		}

        	
        	incomeTable.setItems(incomeFiltered);
    		expensesTable.setItems(expensesFiltered);
    		
    		updateBTN.fire();
		} catch (NullPointerException e) {
			alert.setTitle("Warning");
			alert.setHeaderText("Incomplete fields");
			
			if (beginDP.getValue() == null) {
				alert.setContentText("Please fill the begin date filter");
			} else {
				alert.setContentText("Please fill the end date filter");
			}
			
			alert.show();
		}
    }
    
    @FXML
    void restoreFilter(ActionEvent event) {
    	// Returns the table back to how it was without filters
    	beginDP.setValue(null);
		endDP.setValue(null);
    	incomeTable.setItems(IncomeData.data);
		expensesTable.setItems(ExpensesData.data);
		
		updateBTN.fire();
    }

    @FXML
    void registerActivity(ActionEvent event) throws IOException {
    	// Goes to the register activity window
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("../ui/RegisterActivity.fxml"));
		loader.setController(new RegisterActivity());
		Parent parent = (Parent) loader.load();
		Stage stage = new Stage();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
    }

	@FXML
    void updateTotals(ActionEvent event) {
		// Updates the total values for every counter of the window
		double totalIncome = 0.0;
		double totalExpenses = 0.0;
		double remaining = 0.0;
		
		for (Activity a : incomeTable.getItems()) {
			totalIncome += a.getQuantity();
		}
		
		for (Activity a : expensesTable.getItems()) {
			totalExpenses += a.getQuantity();
		}
		
		remaining = totalIncome - totalExpenses;
		
		incomeTXT.setText("$" + totalIncome);
		expensesTXT.setText("$" + totalExpenses);
		moneyTXT.setText("$" + remaining);
    }
	
	@FXML
    void eliminateActivity(ActionEvent event) {
		// Eliminates a selected activity frome the table
		ExpensesData.data.remove(expenseActivityClicked);
		IncomeData.data.remove(incomeActivityClicked);
    }
}
