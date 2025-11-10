package question.pkg1.exam;

/**
 * ProductSales class that implements the IProduct interface
 * for calculating total, average, max, and min sales.
 */
public class ProductSales implements IProduct {

    // Method to calculate the total sales
    @Override
    public int TotalSales(int[][] productSales) {
        int total = 0;
        // Loop through each quarter and sum the sales
        for (int i = 0; i < productSales.length; i++) {
            for (int j = 0; j < productSales[i].length; j++) {
                total += productSales[i][j];
            }
        }
        return total;
    }

    // Method to calculate the average sales
    @Override
    public double AverageSales(int[][] productSales) {
        int totalSales = TotalSales(productSales);  // Get the total sales using the TotalSales method
        int totalEntries = 0;
        // Count the total number of entries (quarters) in the productSales array
        for (int i = 0; i < productSales.length; i++) {
            totalEntries += productSales[i].length;
        }
        return (double) totalSales / totalEntries;  // Return the average
    }

    // Method to find the maximum sale value
    @Override
    public int MaxSale(int[][] productSales) {
        int maxSale = Integer.MIN_VALUE;  // Start with the smallest possible value
        // Loop through the productSales array to find the maximum sale
        for (int i = 0; i < productSales.length; i++) {
            for (int j = 0; j < productSales[i].length; j++) {
                if (productSales[i][j] > maxSale) {
                    maxSale = productSales[i][j];
                }
            }
        }
        return maxSale;  // Return the maximum sale
    }

    // Method to find the minimum sale value
    @Override
    public int MinSale(int[][] productSales) {
        int minSale = Integer.MAX_VALUE;  // Start with the largest possible value
        // Loop through the productSales array to find the minimum sale
        for (int i = 0; i < productSales.length; i++) {
            for (int j = 0; j < productSales[i].length; j++) {
                if (productSales[i][j] < minSale) {
                    minSale = productSales[i][j];
                }
            }
        }
        return minSale;  // Return the minimum sale
    }

    public static void main(String[] args) {
        // Example sales data for a two-year period (3 quarters per year)
        int[][] productSales = {
            {300, 150, 700}, // Year 1 sales (Q1, Q2, Q3)
            {250, 200, 600}  // Year 2 sales (Q1, Q2, Q3)
        };

        // Create an instance of ProductSales
        ProductSales salesReport = new ProductSales();

        // Display the results
        System.out.println("Total Sales: " + salesReport.TotalSales(productSales));
        System.out.println("Average Sales: " + salesReport.AverageSales(productSales));
        System.out.println("Maximum Sale: " + salesReport.MaxSale(productSales));
        System.out.println("Minimum Sale: " + salesReport.MinSale(productSales));
    }
}

/**
 * IProduct Interface defining the methods for product sales calculations
 */
interface IProduct {
    // Method to calculate the total sales
    int TotalSales(int[][] productSales);

    // Method to calculate the average sales
    double AverageSales(int[][] productSales);

    // Method to find the maximum sale value
    int MaxSale(int[][] productSales);

    // Method to find the minimum sale value
    int MinSale(int[][] productSales);
}
