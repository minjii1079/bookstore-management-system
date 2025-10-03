/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

public class Customer {
    private String username;
    private String password;
    private int points;
    private CustomerState state;
    
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
        this.state = new SilverState(this); // Default state is Silver
    }
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
        updateState(); // Update state when points change
    }
    
    // Update state based on points
    private void updateState() {
        if (points >= 1000) {
            state = new GoldState(this);
        } else {
            state = new SilverState(this);
        }
    }
    
    // Add points after a purchase
    public void addPoints(double purchaseAmount) {
        // For every 1 CAD spent, customer earns 10 points
        this.points += (int)(purchaseAmount * 10);
        updateState();
    }
    
    // Calculate cost after redeeming points
    public double calculateCost(double totalCost) {
        return state.calculateCost(totalCost);
    }
    
    // Get status (Silver or Gold)
    public String getStatus() {
        return state.getStatus();
    }
}
