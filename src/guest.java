import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class guest extends JFrame {
    private JTable propertyTable;
    private String url = "jdbc:mysql://localhost:3306/project";
    private String user = "root";
    private String password = "13Lock02";

    public guest() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up the front page
        JLabel label = new JLabel("Guest Mode");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Arial", Font.BOLD, 45));
        label.setHorizontalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(21,27,41)); // Set background color
        panel.add(label, BorderLayout.CENTER);
        JButton startButton = new JButton("Show Properties");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 guestFunc();

                ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
            }
        });
        panel.add(startButton, BorderLayout.SOUTH);


        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RealEstateAppFrontPage obj=new RealEstateAppFrontPage();

                ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
            }
        });
        panel.add(backButton, BorderLayout.NORTH);


// Add the panel to the JFrame
        JFrame frame = new JFrame("Guest Window");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }
    void guestFunc()
    {
        // Column Names
        String[] columnNames = {"PropID", "Location", "BHK", "SqFtArea", "PurchaseType"};
        Object[][] data = getData(null, null, null);
        propertyTable = new JTable(data, columnNames);
        propertyTable.setBackground(new Color(165, 185, 115));

        propertyTable.setBounds(30, 40, 900, 700);

        // Center cell values
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < propertyTable.getColumnCount(); i++) {
            propertyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane sp = new JScrollPane(propertyTable);

        this.add(sp);

        // Filter button
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField bhkField = new JTextField(5);
                JTextField purchaseTypeField = new JTextField(5);
                JTextField locationField = new JTextField(5);

                JPanel filterPanel = new JPanel();

                filterPanel.add(new JLabel("BHK:"));
                filterPanel.add(bhkField);
                filterPanel.add(Box.createHorizontalStrut(15));
                filterPanel.add(new JLabel("Purchase Type:"));
                filterPanel.add(purchaseTypeField);
                filterPanel.add(Box.createHorizontalStrut(15));
                filterPanel.add(new JLabel("Location:"));
                filterPanel.add(locationField);

                int result = JOptionPane.showConfirmDialog(null, filterPanel,
                        "Enter Filter Criteria", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String bhk = bhkField.getText();
                    String purchaseType = purchaseTypeField.getText();
                    String location = locationField.getText();
                    updateTable(bhk, purchaseType, location);
                }
            }
        });

        // Clear Filter button
        JButton clearFilterButton = new JButton("Clear Filter");
        clearFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(null, null, null);
            }
        });

        // Agent Details button
        JButton agentDetailsButton = new JButton("Agent Details");
        agentDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String propID = JOptionPane.showInputDialog("Enter PropID:");
                if (propID != null && !propID.isEmpty()) {
                    showAgentDetails(propID);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RealEstateAppFrontPage obj=new RealEstateAppFrontPage();

                ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
            }
        });



        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(clearFilterButton);
        buttonPanel.add(agentDetailsButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setSize(900, 700);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);// Set Exit on Close
    }

    private void showAgentDetails(String propID) {
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {
            String query = "SELECT a.AgentID, a.Name, a.Rating, a.PhNumber, a.Email FROM Agent a JOIN PropAg pa ON pa.AgentID = a.AgentID JOIN Property p ON p.PropID = pa.PropID WHERE p.PropID = " + propID;
            try (ResultSet rs = st.executeQuery(query)) {
                if (rs.next()) {
                    int agentID = rs.getInt("AgentID");
                    String name = rs.getString("Name");
                    double rating = rs.getDouble("Rating");
                    long PhNumber = rs.getLong("Phnumber");
                    String Email = rs.getString("Email");
                    JOptionPane.showMessageDialog(this,
                            "AgentID: " + agentID + "\nName: " + name + "\nRating: " + rating + "\nPhnumber: " + PhNumber + "\nEmailId: " + Email,
                            "Agent Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No agent found for PropID: " + propID,
                            "Agent Details", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateTable(String bhk, String purchaseType, String location) {
        Object[][] data = getData(bhk, purchaseType, location);
        propertyTable.setModel(new DefaultTableModel(data,
                new String[]{"PropID", "Location", "BHK", "SqFtArea", "PurchaseType"}));
    }

    private Object[][] getData(String bhk, String purchaseType, String location) {
        ArrayList<Object[]> dataList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {
            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT p.PropID, p.Location, p.BHK, p.SqFtArea, p.PurchaseType FROM Property p ");
            if (bhk != null && !bhk.isEmpty()) {
                queryBuilder.append("WHERE p.BHK = ").append(bhk).append(" ");
            }
            if (purchaseType != null && !purchaseType.isEmpty()) {
                if (queryBuilder.toString().contains("WHERE")) {
                    queryBuilder.append("AND ");
                } else {
                    queryBuilder.append("WHERE ");
                }
                queryBuilder.append("p.PurchaseType = '").append(purchaseType).append("' ");
            }
            if (location != null && !location.isEmpty()) {
                if (queryBuilder.toString().contains("WHERE")) {
                    queryBuilder.append("AND ");
                } else {
                    queryBuilder.append("WHERE ");
                }
                queryBuilder.append("p.Location = '").append(location).append("' ");
            }
            try (ResultSet rs = st.executeQuery(queryBuilder.toString())) {
                while (rs.next()) {
                    Object[] rowData = new Object[5];
                    rowData[0] = rs.getInt("PropID");
                    rowData[1] = rs.getString("Location");
                    rowData[2] = rs.getInt("BHK");
                    rowData[3] = rs.getInt("SqFtArea");
                    rowData[4] = rs.getString("PurchaseType");
                    dataList.add(rowData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dataList.toArray(new Object[dataList.size()][]);
    }



}