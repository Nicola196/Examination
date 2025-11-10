package exam;

import java.util.ArrayList;
import java.util.List;

/**
 * Product Sales Report Application
 * @author RC_Student_Lab
 */
public class Exam {

    // Inner class to represent sales data for a quarter
    static class QuarterSales {
        private int year;
        private int quarter;
        private double sales;
        
        public QuarterSales(int year, int quarter, double sales) {
            this.year = year;
            this.quarter = quarter;
            this.sales = sales;
        }
        
        // Getters
        public int getYear() { 
            return year; 
        }
        
        public int getQuarter() { 
            return quarter; 
        }
        
        public double getSales() { 
            return sales; 
        }
        
        @Override
        public String toString() {
            return String.format("Year %d, Q%d: $%.2f", year, quarter, sales);
        }
    }
    
    private List<QuarterSales> salesData;
    
    public Exam() {
        salesData = new ArrayList<>();
        initializeSalesData();
    }
    
    // Initialize the sales data as provided
    private void initializeSalesData() {
        // Year 1 data
        salesData.add(new QuarterSales(1, 1, 300.0));
        salesData.add(new QuarterSales(1, 2, 150.0));
        salesData.add(new QuarterSales(1, 3, 700.0));
        
        // Year 2 data
        salesData.add(new QuarterSales(2, 1, 250.0));
        salesData.add(new QuarterSales(2, 2, 200.0));
        salesData.add(new QuarterSales(2, 3, 600.0));
    }
    
    // Calculate total sales
    public double calculateTotal() {
        double total = 0;
        for (QuarterSales quarter : salesData) {
            total += quarter.getSales();
        }
        return total;
    }
    
    // Calculate average sales
    public double calculateAverage() {
        return calculateTotal() / salesData.size();
    }
    
    // Find maximum sales
    public double findMaximum() {
        double max = salesData.get(0).getSales();
        for (QuarterSales quarter : salesData) {
            if (quarter.getSales() > max) {
                max = quarter.getSales();
            }
        }
        return max;
    }
    
    // Find minimum sales
    public double findMinimum() {
        double min = salesData.get(0).getSales();
        for (QuarterSales quarter : salesData) {
            if (quarter.getSales() < min) {
                min = quarter.getSales();
            }
        }
        return min;
    }
    
    // Get quarter with maximum sales
    public QuarterSales getQuarterWithMaxSales() {
        QuarterSales maxQuarter = salesData.get(0);
        for (QuarterSales quarter : salesData) {
            if (quarter.getSales() > maxQuarter.getSales()) {
                maxQuarter = quarter;
            }
        }
        return maxQuarter;
    }
    
    // Get quarter with minimum sales
    public QuarterSales getQuarterWithMinSales() {
        QuarterSales minQuarter = salesData.get(0);
        for (QuarterSales quarter : salesData) {
            if (quarter.getSales() < minQuarter.getSales()) {
                minQuarter = quarter;
            }
        }
        return minQuarter;
    }
    
    // Generate and display the complete report
    public void generateReport() {
        System.out.println("=====================================");
        System.out.println("   PRODUCT SALES REPORT");
        System.out.println("   Local Retail Company");
        System.out.println("=====================================");
        System.out.println();
        
        // Display quarterly data
        System.out.println("QUARTERLY SALES DATA:");
        System.out.println("---------------------");
        for (QuarterSales quarter : salesData) {
            System.out.println(quarter);
        }
        System.out.println();
        
        // Display summary statistics
        System.out.println("SALES SUMMARY (Two-Year Period):");
        System.out.println("--------------------------------");
        System.out.printf("Total Sales:     $%,.2f%n", calculateTotal());
        System.out.printf("Average Sales:   $%,.2f%n", calculateAverage());
        System.out.printf("Maximum Sales:   $%,.2f%n", findMaximum());
        System.out.printf("Minimum Sales:   $%,.2f%n", findMinimum());
        System.out.println();
        
        // Display additional insights
        System.out.println("PERFORMANCE INSIGHTS:");
        System.out.println("--------------------");
        QuarterSales maxQuarter = getQuarterWithMaxSales();
        QuarterSales minQuarter = getQuarterWithMinSales();
        
        System.out.printf("Best Performance:   %s%n", maxQuarter);
        System.out.printf("Lowest Performance: %s%n", minQuarter);
        
        // Year-wise totals
        System.out.println();
        System.out.println("YEAR-WISE BREAKDOWN:");
        System.out.println("-------------------");
        
        double year1Total = 0;
        double year2Total = 0;
        
        for (QuarterSales quarter : salesData) {
            if (quarter.getYear() == 1) {
                year1Total += quarter.getSales();
            } else if (quarter.getYear() == 2) {
                year2Total += quarter.getSales();
            }
        }
        
        System.out.printf("Year 1 Total: $%,.2f%n", year1Total);
        System.out.printf("Year 2 Total: $%,.2f%n", year2Total);
        
        double yearChange = year2Total - year1Total;
        double yearChangePercent = (yearChange / year1Total) * 100;
        
        System.out.printf("Year-over-Year Change: $%,.2f (%.1f%%)%n", 
                         yearChange, yearChangePercent);
        
        System.out.println("=====================================");
    }
    
    /**
     * Main method to run the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting Product Sales Report Generation...");
        System.out.println();
        
        Exam report = new Exam();
        report.generateReport();
        
        System.out.println();
        System.out.println("Report generation completed successfully!");
    }
}