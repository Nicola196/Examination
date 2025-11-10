
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SoundEquipmentSalesAnalyzer extends JFrame {
    private static final double SALES_LIMIT = 500.00;
    private DecimalFormat currencyFormat = new DecimalFormat("R#,##0.00");
    private DecimalFormat numberFormat = new DecimalFormat("#,##0.00");
    
    // GUI Components
    private JTextArea salesDataArea;
    private JLabel totalSalesLabel;
    private JLabel averageSalesLabel;
    private JLabel salesLimitLabel;
    private JTable overLimitTable;
    private JTable underLimitTable;
    private DefaultTableModel overLimitModel;
    private DefaultTableModel underLimitModel;
    
    // Data storage
    private ArrayList<Product> products;
    
    public SoundEquipmentSalesAnalyzer() {
        products = new ArrayList<>();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Sound Equipment Sales Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(900, 700);
        
        // Top Panel - Input Area
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - Results
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom Panel - Buttons
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel inputLabel = new JLabel("Enter Sales Data (Format: ProductName,Price):");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        salesDataArea = new JTextArea(6, 40);
        salesDataArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        salesDataArea.setText("Microphone,450.00\nSpeakers,750.00\nAmplifier,1200.00\nMixer,650.00\nHeadphones,350.00\nCable Set,150.00");
        JScrollPane scrollPane = new JScrollPane(salesDataArea);
        
        panel.add(inputLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Summary Panel
        JPanel summaryPanel = createSummaryPanel();
        panel.add(summaryPanel, BorderLayout.NORTH);
        
        // Tables Panel
        JPanel tablesPanel = createTablesPanel();
        panel.add(tablesPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            "Sales Summary",
            0, 0,
            new Font("Arial", Font.BOLD, 14)
        ));
        panel.setBackground(new Color(240, 248, 255));
        
        totalSalesLabel = new JLabel("Total Sales: R0.00");
        totalSalesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalSalesLabel.setForeground(new Color(0, 100, 0));
        
        averageSalesLabel = new JLabel("Average Sales: R0.00");
        averageSalesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        averageSalesLabel.setForeground(new Color(0, 0, 139));
        
        salesLimitLabel = new JLabel("Sales Limit: " + currencyFormat.format(SALES_LIMIT));
        salesLimitLabel.setFont(new Font("Arial", Font.BOLD, 16));
        salesLimitLabel.setForeground(new Color(139, 0, 0));
        
        panel.add(totalSalesLabel);
        panel.add(averageSalesLabel);
        panel.add(salesLimitLabel);
        
        return panel;
    }
    
    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Over Limit Table
        JPanel overPanel = new JPanel(new BorderLayout());
        overPanel.setBorder(BorderFactory.createTitledBorder("Sales Over Limit (Success)"));
        
        String[] columnNames = {"Product", "Sales Amount"};
        overLimitModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        overLimitTable = new JTable(overLimitModel);
        overLimitTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        overLimitTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane overScroll = new JScrollPane(overLimitTable);
        overPanel.add(overScroll, BorderLayout.CENTER);
        
        // Under Limit Table
        JPanel underPanel = new JPanel(new BorderLayout());
        underPanel.setBorder(BorderFactory.createTitledBorder("Sales Under Limit"));
        
        underLimitModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        underLimitTable = new JTable(underLimitModel);
        underLimitTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        underLimitTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane underScroll = new JScrollPane(underLimitTable);
        underPanel.add(underScroll, BorderLayout.CENTER);
        
        panel.add(overPanel);
        panel.add(underPanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton processButton = new JButton("Process Sales Data");
        processButton.setFont(new Font("Arial", Font.BOLD, 14));
        processButton.setBackground(new Color(34, 139, 34));
        processButton.setForeground(Color.WHITE);
        processButton.setFocusPainted(false);
        processButton.addActionListener(e -> processSalesData());
        
        JButton loadFileButton = new JButton("Load from File");
        loadFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadFileButton.setBackground(new Color(30, 144, 255));
        loadFileButton.setForeground(Color.WHITE);
        loadFileButton.setFocusPainted(false);
        loadFileButton.addActionListener(e -> loadFromFile());
        
        JButton clearButton = new JButton("Clear All");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearAll());
        
        panel.add(loadFileButton);
        panel.add(processButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void processSalesData() {
        products.clear();
        String[] lines = salesDataArea.getText().split("\n");
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length == 2) {
                try {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    products.add(new Product(name, price));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Invalid price format in line: " + line,
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No valid sales data found!",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        calculateAndDisplay();
    }
    
    private void calculateAndDisplay() {
        double totalSales = 0;
        ArrayList<Product> overLimit = new ArrayList<>();
        ArrayList<Product> underLimit = new ArrayList<>();
        
        for (Product product : products) {
            totalSales += product.getPrice();
            
            if (product.getPrice() > SALES_LIMIT) {
                overLimit.add(product);
            } else {
                underLimit.add(product);
            }
        }
        
        double averageSales = totalSales / products.size();
        
        // Update labels
        totalSalesLabel.setText("Total Sales: " + currencyFormat.format(totalSales));
        averageSalesLabel.setText("Average Sales: " + currencyFormat.format(averageSales));
        
        // Update tables
        overLimitModel.setRowCount(0);
        for (Product p : overLimit) {
            overLimitModel.addRow(new Object[]{p.getName(), currencyFormat.format(p.getPrice())});
        }
        
        underLimitModel.setRowCount(0);
        for (Product p : underLimit) {
            underLimitModel.addRow(new Object[]{p.getName(), currencyFormat.format(p.getPrice())});
        }
        
        JOptionPane.showMessageDialog(this,
            "Sales data processed successfully!\n" +
            "Total Products: " + products.size() + "\n" +
            "Over Limit: " + overLimit.size() + "\n" +
            "Under Limit: " + underLimit.size(),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Sales Data File");
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                salesDataArea.setText(content.toString());
                JOptionPane.showMessageDialog(this,
                    "File loaded successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error reading file: " + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearAll() {
        salesDataArea.setText("");
        products.clear();
        totalSalesLabel.setText("Total Sales: R0.00");
        averageSalesLabel.setText("Average Sales: R0.00");
        overLimitModel.setRowCount(0);
        underLimitModel.setRowCount(0);
    }
    
    // Inner class for Product
    static class Product {
        private String name;
        private double price;
        
        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
        
        public String getName() {
            return name;
        }
        
        public double getPrice() {
            return price;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SoundEquipmentSalesAnalyzer app = new SoundEquipmentSalesAnalyzer();
            app.setVisible(true);
        });
    }
}