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

public class OwnerBooksScreen extends BaseScreen {
    private final VBox root;
    private TableView<book> bookTable;
    private ObservableList<book> bookList;
    
    public OwnerBooksScreen() {
        root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Manage Books");
        titleLabel.setFont(new Font(20));
        
        // Create table
        setupTable();
        
        // Create form for adding books
        HBox formBox = new HBox(10);
        formBox.setPadding(new Insets(10));
        
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            
            if (name.isEmpty() || priceText.isEmpty()) {
                showAlert("Error", "Please enter both name and price.");
                return;
            }
            
            try {
                double price = Double.parseDouble(priceText);
                if (price <= 0) {
                    showAlert("Error", "Price must be positive.");
                    return;
                }
                
                // Check if book with same name already exists
                boolean exists = false;
                for (book book : Main.getBooks()) {
                    if (book.getName().equalsIgnoreCase(name)) {
                        exists = true;
                        break;
                    }
                }
                
                if (exists) {
                    showAlert("Error", "A book with this name already exists.");
                    return;
                }
                
                book newBook = new book(name, price);
                Main.getBooks().add(newBook);
                bookList.add(newBook);
                
                // Clear fields
                nameField.clear();
                priceField.clear();
                
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid price.");
            }
        });
        
        formBox.getChildren().addAll(nameField, priceField, addButton);
        
        // Create buttons for delete and back
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                Main.getBooks().remove(selectedBook);
                bookList.remove(selectedBook);
            } else {
                showAlert("Error", "Please select a book to delete.");
            }
        });
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> Main.changeScreen(new OwnerStartScreen()));
        
        buttonBox.getChildren().addAll(deleteButton, backButton);
        
        root.getChildren().addAll(titleLabel, bookTable, formBox, buttonBox);
    }
    
    private void setupTable() {
        bookTable = new TableView<>();
        
        // Create columns
        TableColumn<book, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(300);
        
        TableColumn<book, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(100);
        
        bookTable.getColumns().add(nameColumn);
        bookTable.getColumns().add(priceColumn);
        
        // Add data to table
        bookList = FXCollections.observableArrayList(Main.getBooks());
        bookTable.setItems(bookList);
        
        // Make table fill available space
        VBox.setVgrow(bookTable, Priority.ALWAYS);
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
