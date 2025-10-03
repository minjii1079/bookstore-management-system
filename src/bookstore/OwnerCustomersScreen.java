/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class OwnerCustomersScreen extends BaseScreen {
    private final VBox root;
    private TableView<Customer> customerTable;
    private ObservableList<Customer> customerList;
    
    public OwnerCustomersScreen() {
        root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Manage Customers");
        titleLabel.setFont(new Font(20));
        
        // Create table
        setupTable();
        
        // Create form for adding customers
        HBox formBox = new HBox(10);
        formBox.setPadding(new Insets(10));
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter both username and password.");
                return;
            }
            
            // Check if customer with same username already exists
            boolean exists = false;
            for (Customer customer : Main.getCustomers()) {
                if (customer.getUsername().equals(username)) {
                    exists = true;
                    break;
                }
            }
            
            if (exists) {
                showAlert("Error", "A customer with this username already exists.");
                return;
            }
            
            Customer newCustomer = new Customer(username, password);
            Main.getCustomers().add(newCustomer);
            customerList.add(newCustomer);
            
            // Clear fields
            usernameField.clear();
            passwordField.clear();
        });
        
        formBox.getChildren().addAll(usernameField, passwordField, addButton);
        
        // Create buttons for delete and back
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                Main.getCustomers().remove(selectedCustomer);
                customerList.remove(selectedCustomer);
            } else {
                showAlert("Error", "Please select a customer to delete.");
            }
        });
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> Main.changeScreen(new OwnerStartScreen()));
        
        buttonBox.getChildren().addAll(deleteButton, backButton);
        
        root.getChildren().addAll(titleLabel, customerTable, formBox, buttonBox);
    }
    
    private void setupTable() {
        customerTable = new TableView<>();
        
        // Create columns
        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(200);
        
        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setPrefWidth(200);
        
        TableColumn<Customer, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsColumn.setPrefWidth(100);
        
        customerTable.getColumns().add(usernameColumn);
        customerTable.getColumns().add(passwordColumn);
        customerTable.getColumns().add(pointsColumn);
        
        // Add data to table
        customerList = FXCollections.observableArrayList(Main.getCustomers());
        customerTable.setItems(customerList);
        
        // Make table fill available space
        VBox.setVgrow(customerTable, Priority.ALWAYS);
    }
    
    @Override
    public Parent getRoot() {
        return root;
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
