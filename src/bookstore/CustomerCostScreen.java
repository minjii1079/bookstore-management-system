/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CustomerCostScreen extends BaseScreen {
    private final VBox root;
    
    public CustomerCostScreen(Customer customer, double cost) {
        root = new VBox(20);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.CENTER);
        
        Label costLabel = new Label(String.format("Total Cost: $%.2f", cost));
        costLabel.setFont(new Font(18));
        
        Label pointsLabel = new Label(String.format("Points: %d, Status: %s", 
            customer.getPoints(), customer.getStatus()));
        pointsLabel.setFont(new Font(16));
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(100);
        logoutButton.setOnAction(e -> Main.changeScreen(new LoginScreen()));
        
        root.getChildren().addAll(costLabel, pointsLabel, logoutButton);
    }
    
    @Override
    public Parent getRoot() {
        return root;
    }
}