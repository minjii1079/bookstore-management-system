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

public class OwnerStartScreen extends BaseScreen {
    private final VBox root;
    
    public OwnerStartScreen() {
        root = new VBox(20);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Owner Menu");
        titleLabel.setFont(new Font(24));
        
        Button booksButton = new Button("Books");
        booksButton.setPrefWidth(200);
        booksButton.setOnAction(e -> Main.changeScreen(new OwnerBooksScreen()));
        
        Button customersButton = new Button("Customers");
        customersButton.setPrefWidth(200);
        customersButton.setOnAction(e -> Main.changeScreen(new OwnerCustomersScreen()));
        
        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(200);
        logoutButton.setOnAction(e -> Main.changeScreen(new LoginScreen()));
        
        root.getChildren().addAll(titleLabel, booksButton, customersButton, logoutButton);
    }
    
    @Override
    public Parent getRoot() {
        return root;
    }
}

