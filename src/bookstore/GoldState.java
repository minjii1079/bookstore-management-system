/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstore;

public class GoldState implements CustomerState {
    private Customer customer;
    
    public GoldState(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public double calculateCost(double totalCost) {
        int points = customer.getPoints();
        double discount = points / 100.0; // 100 points = $1 discount
        
        if (discount > totalCost) {
            discount = totalCost; // Ensure cost doesn't go below 0
        }
        
        // Update points after redemption
        customer.setPoints(points - (int)(discount * 100));
        
        return totalCost - discount;
    }
    
    @Override
    public String getStatus() {
        return "Gold";
    }
}
