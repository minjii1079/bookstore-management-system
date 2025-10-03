/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

public class CustomerStartScreen extends BaseScreen {
    private final VBox root;
    private final Customer customer;
    private TableView<BookTableItem> bookTable;
    private ObservableList<BookTableItem> bookItems;
    
    public CustomerStartScreen(Customer customer) {
        this.customer = customer;
        root = new VBox(10);
        root.setPadding(new Insets(20));
        
        // Welcome label
        Label welcomeLabel = new Label(String.format(
            "Welcome %s. You have %d points. Your status is %s.",
            customer.getUsername(), customer.getPoints(), customer.getStatus()
        ));
        welcomeLabel.setFont(new Font(16));
        
        // Book table
        setupBookTable();
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        
        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> handleBuy(false));
        
        Button redeemButton = new Button("Redeem points and Buy");
        redeemButton.setOnAction(e -> handleBuy(true));
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> Main.changeScreen(new LoginScreen()));
        
        buttonBox.getChildren().addAll(buyButton, redeemButton, logoutButton);
        
        root.getChildren().addAll(welcomeLabel, bookTable, buttonBox);
    }
    
    private void setupBookTable() {
        bookTable = new TableView<>();
        
        // Create columns
        TableColumn<BookTableItem, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(250);
        
        TableColumn<BookTableItem, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(100);
        
        TableColumn<BookTableItem, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(param -> param.getValue().selectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setPrefWidth(100);
        
        bookTable.getColumns().add(nameColumn);
        bookTable.getColumns().add(priceColumn);
        bookTable.getColumns().add(selectColumn);
        
        // Add data to table
        bookItems = FXCollections.observableArrayList();
        for (book book : Main.getBooks()) {
            bookItems.add(new BookTableItem(book));
        }
        bookTable.setItems(bookItems);
        
        // Enable checkbox selection
        bookTable.setEditable(true);
        
        // Make table fill available space
        VBox.setVgrow(bookTable, Priority.ALWAYS);
    }
    
    private void handleBuy(boolean redeem) {
        List<book> selectedBooks = new ArrayList<>();
        double totalCost = 0;
        
        for (BookTableItem item : bookItems) {
            if (item.isSelected()) {
                selectedBooks.add(new book(item.getName(), item.getPrice()));
                totalCost += item.getPrice();
            }
        }
        
        if (selectedBooks.isEmpty()) {
            showAlert("Error", "Please select at least one book to buy.");
            return;
        }
        
        // Calculate final cost
        double finalCost = totalCost;
        if (redeem) {
            finalCost = customer.calculateCost(totalCost);
        } else {
            // Add points for purchase
            customer.addPoints(totalCost);
        }
        
        // Show cost screen
        Main.changeScreen(new CustomerCostScreen(customer, finalCost));
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
    
    // Helper class for book items in the table
    public static class BookTableItem {
        private final String name;
        private final double price;
        private final SimpleBooleanProperty selected;
        
        public BookTableItem(book book) {
            this.name = book.getName();
            this.price = book.getPrice();
            this.selected = new SimpleBooleanProperty(false);
        }
        
        public String getName() {
            return name;
        }
        
        public double getPrice() {
            return price;
        }
        
        public boolean isSelected() {
            return selected.get();
        }
        
        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }
        
        public SimpleBooleanProperty selectedProperty() {
            return selected;
        }
    }
}
