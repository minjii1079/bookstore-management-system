/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

public class Main extends Application {
    // Static data structures to hold books and customers
    private static ArrayList<book> books = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Book Store Application");
        
        // Load data from files when starting the application
        loadBooksFromFile();
        loadCustomersFromFile();
        
        // Setup window close handler to save data when the application is closed
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            saveBooksToFile();
            saveCustomersToFile();
        });
        
        // Start with login screen
        LoginScreen loginScreen = new LoginScreen();
        Scene scene = new Scene(loginScreen.getRoot(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // Method to change screens
    public static void changeScreen(BaseScreen screen) {
        Scene scene = new Scene(screen.getRoot(), 600, 400);
        primaryStage.setScene(scene);
    }
    
    // Method to load books from file
    private void loadBooksFromFile() {
        try {
            File file = new File("books.txt");
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    books.add(new book(name, price));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
    
    // Method to save books to file
    private static void saveBooksToFile() {
        try {
            File file = new File("books.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            
            for (book book : books) {
                writer.write(book.getName() + "," + book.getPrice());
                writer.newLine();
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }
    
    // Method to load customers from file
    private void loadCustomersFromFile() {
        try {
            File file = new File("customers.txt");
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    int points = Integer.parseInt(parts[2]);
                    
                    Customer customer = new Customer(username, password);
                    customer.setPoints(points);
                    customers.add(customer);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
    
    // Method to save customers to file
    private static void saveCustomersToFile() {
        try {
            File file = new File("customers.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            
            for (Customer customer : customers) {
                writer.write(customer.getUsername() + "," + customer.getPassword() + "," + customer.getPoints());
                writer.newLine();
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }
    
    // Getters for books and customers lists
    public static ArrayList<book> getBooks() {
        return books;
    }
    
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}
