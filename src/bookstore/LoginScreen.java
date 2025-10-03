/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LoginScreen extends BaseScreen {
    private final VBox root;
    
    public LoginScreen() {
        root = new VBox(20);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Book Store Login");
        titleLabel.setFont(new Font(24));
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (username.equals("admin") && password.equals("admin")) {
                Main.changeScreen(new OwnerStartScreen());
            } else {
                // Check if the user is a registered customer
                Customer foundCustomer = null;
                for (Customer customer : Main.getCustomers()) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        foundCustomer = customer;
                        break;
                    }
                }
                
                if (foundCustomer != null) {
                    Main.changeScreen(new CustomerStartScreen(foundCustomer));
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                }
            }
        });
        
        root.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);
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
