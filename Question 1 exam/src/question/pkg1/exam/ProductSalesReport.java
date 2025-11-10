package question.pkg1.exam;

import java.util.ArrayList;
import java.util.List;

public class ProductSalesReport {

    // Inner class to represent sales data for a quarter
    static class QuarterSales {
        private int year;      // Year of the sales data
        private int quarter;   // Quarter of the year (1, 2, 3, 4)
        private double sales;  // Sales for that quarter

        // Constructor to initialize the quarter sales data
        public QuarterSales(int year, int quarter, double sales) {
            this.year = year;
            this.quarter = quarter;
            this.sales = sales;
        }

        // Getter methods to access the private fields
        public int getYear() { return year; }
        public int getQuarter() { return quarter; }
        public double getSales() { return sales; }

        // String representation of a QuarterSales object
        @Override
        public String toString() {
            return String.format("Year %d, Q%d: $%.2f", year, quarter, sales);
        }
    }

    // List to store all the quarterly sales data
    private List<QuarterSales> salesData;

    // Constructor to initialize the report and populate sales data
    public ProductSalesReport() {
        salesData = new ArrayList<>();  // Initialize the salesData list
        initializeSalesData();  // Populate the sales data
    }

    // Method to initialize sales data for two years
    private void initializeSalesData() {
        // Year 1 data
        salesData.add(new QuarterSales(1, 1, 300.0));  // Q1 of Year 1
        salesData.add(new QuarterSales(1, 2, 150.0));  // Q2 of Year 1
        salesData.add(new QuarterSales(1, 3, 700.0));  // Q3 of Year 1

        // Year 2 data
        salesData.add(new QuarterSales(2, 1, 250.0));  // Q1 of Year 2
        salesData.add(new QuarterSales(2, 2, 200.0));  // Q2 of Year 2
        salesData.add(new QuarterSales(2, 3, 600.0));  // Q3 of Year 2
    }

    // Method to calculate total sales for the two-year period
    public double calculateTotal() {
        return salesData.stream()
                       .mapToDouble(QuarterSales::getSales)  // Extract sales data for each quarter
                       .sum();  // Sum up all the sales for both years
    }

    // Method to calculate average sales for the two-year period
    public double calculateAverage() {
        return calculateTotal() / salesData.size();  // Total sales divided by the number of entries
    }

    // Method to find the maximum sales value for the two-year period
    public double findMaximum() {
        return salesData.stream()
                       .mapToDouble(QuarterSales::getSales)  // Extract sales for each quarter
                       .max()  // Find the maximum sales value
                       .orElse(0.0);  // If no data is available, return 0.0
    }

    // Method to find the minimum sales value for the two-year period
    public double findMinimum() {
        return salesData.stream()
                       .mapToDouble(QuarterSales::getSales)  // Extract sales for each quarter
                       .min()  // Find the minimum sales value
                       .orElse(0.0);  // If no data is available, return 0.0
    }

    // Method to get the quarter with the highest sales
    public QuarterSales getQuarterWithMaxSales() {
        return salesData.stream()
                       .max((q1, q2) -> Double.compare(q1.getSales(), q2.getSales()))  // Compare sales
                       .orElse(null);  // Return the quarter with the maximum sales, or null if no data
    }

    // Method to get the quarter with the lowest sales
    public QuarterSales getQuarterWithMinSales() {
        return salesData.stream()
                       .min((q1, q2) -> Double.compare(q1.getSales(), q2.getSales()))  // Compare sales
                       .orElse(null);  // Return the quarter with the minimum sales, or null if no data
    }

    // Method to generate and display the complete sales report
    public void generateReport() {
        // Print header for the report
        System.out.println("=====================================");
        System.out.println("   PRODUCT SALES REPORT");
        System.out.println("   Local Retail Company");
        System.out.println("=====================================");
        System.out.println();

        // Display quarterly sales data
        System.out.println("QUARTERLY SALES DATA:");
        System.out.println("---------------------");
        for (QuarterSales quarter : salesData) {
            System.out.println(quarter);  // Display each quarter's sales data
        }
        System.out.println();

        // Display summary statistics for the two-year period
        System.out.println("SALES SUMMARY (Two-Year Period):");
        System.out.println("--------------------------------");
        System.out.printf("Total Sales (2 Years):  $%,.2f%n", calculateTotal());  // Display total sales for two years
        System.out.printf("Average Sales (2 Years):$%,.2f%n", calculateAverage());  // Display average sales for two years
        System.out.printf("Maximum Sales (2 Years):$%,.2f%n", findMaximum());  // Display maximum sales for two years
        System.out.printf("Minimum Sales (2 Years):$%,.2f%n", findMinimum());  // Display minimum sales for two years
        System.out.println();

        // Display insights based on performance
        System.out.println("PERFORMANCE INSIGHTS:");
        System.out.println("--------------------");
        QuarterSales maxQuarter = getQuarterWithMaxSales();  // Get quarter with maximum sales
        QuarterSales minQuarter = getQuarterWithMinSales();  // Get quarter with minimum sales

        // Print the best and lowest performance quarters
        if (maxQuarter != null) {
            System.out.printf("Best Performance:  %s%n", maxQuarter);
        }
        if (minQuarter != null) {
            System.out.printf("Lowest Performance: %s%n", minQuarter);
        }

        // Display year-wise breakdown of sales
        System.out.println();
        System.out.println("YEAR-WISE BREAKDOWN:");
        System.out.println("-------------------");

        // Calculate total sales for Year 1
        double year1Total = salesData.stream()
                                   .filter(q -> q.getYear() == 1)  // Filter data for Year 1
                                   .mapToDouble(QuarterSales::getSales)  // Extract sales data for Year 1
                                   .sum();  // Sum up the sales for Year 1

        // Calculate total sales for Year 2
        double year2Total = salesData.stream()
                                   .filter(q -> q.getYear() == 2)  // Filter data for Year 2
                                   .mapToDouble(QuarterSales::getSales)  // Extract sales data for Year 2
                                   .sum();  // Sum up the sales for Year 2

        // Display the year-wise total sales
        System.out.printf("Year 1 Total: $%,.2f%n", year1Total);
        System.out.printf("Year 2 Total: $%,.2f%n", year2Total);

        // Calculate and display year-over-year change
        double yearChange = year2Total - year1Total;  // Difference in total sales between Year 2 and Year 1
        double yearChangePercent = (yearChange / year1Total) * 100;  // Percentage change between the two years

        // Print the year-over-year change
        System.out.printf("Year-over-Year Change: $%,.2f (%.1f%%)%n", 
                         yearChange, yearChangePercent);

        // Print footer for the report
        System.out.println("=====================================");
    }

    // Main method to run the application
    public static void main(String[] args) {
        System.out.println("Starting Product Sales Report Generation...");
        System.out.println();

        // Create a report object and generate the report
        ProductSalesReport report = new ProductSalesReport();
        report.generateReport();

        System.out.println();
        System.out.println("Report generation completed successfully!");
    }
}
