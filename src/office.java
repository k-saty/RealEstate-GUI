import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Dimension;


class office extends JFrame implements ActionListener {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;


    public office() {
        super("Agent Sales Report ");

        // Set up the JTable
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setBackground(new Color(165, 185, 115));
        add(scrollPane, BorderLayout.CENTER);

        // Set up the back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton, BorderLayout.NORTH);

        // Set up the exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton, BorderLayout.SOUTH);


        // Set up the JFrame
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setSize(900, 700);
        setLocationRelativeTo(null);

        setVisible(true);
    }
    void query1 ()
    {

        //selling performance
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project", "root", "13Lock02");

            Statement stmt = con.createStatement();
            // Get the data from the StatusProp and Agent tables
            String sql ="SELECT a.AgentID, a.Name, a.Rating, a.Commission, sp.FinalSP, sp.SoldDate AS SoldYear, p.Location, YEAR(p.yearOC) as YearOfConstruction "
                    + "FROM StatusProp as sp "
                    + "JOIN Agent as a ON sp.AgentID = a.AgentID "
                    + "JOIN Property as p ON p.PropID = sp.PropID "
                    + "WHERE sp.FinalRent IS NULL";
            ResultSet rs = stmt.executeQuery(sql);

            // Add the data to the table model
            tableModel.addColumn("Agent ID");
            tableModel.addColumn("Agent Name");
            tableModel.addColumn("Agent Rating");
            tableModel.addColumn("Agent Commission");
            tableModel.addColumn("Final Selling Price");

            tableModel.addColumn("Sold Year");
            tableModel.addColumn("Location");
            tableModel.addColumn("YearOfConstruction");
            while (rs.next()) {
                Object[] row = new Object[9];

                row[0] = rs.getInt("AgentID");
                row[1] = rs.getString("Name");
                row[2] = rs.getDouble("Rating");
                row[3] = rs.getDouble("Commission");
                row[4] = rs.getInt("FinalSP");

                row[5] = rs.getInt("SoldYear");
                row[6] = rs.getString("Location");
                row[7] = rs.getInt("YearOfConstruction");
                tableModel.addRow(row);

            }
            // Close the database connection
            con.close();
            rs.close();
            stmt.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    void query2 ()
    {
        //renting performance

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project", "root", "13Lock02");

            Statement stmt = con.createStatement();
            // Get the data from the StatusProp and Agent tables
            String sql = "SELECT a.AgentID, a.Name, a.Rating, a.Commission, sp.FinalRent, sp.SoldDate AS SoldYear, p.Location, YEAR(p.yearOC) as YearOfConstruction "
                    + "FROM StatusProp as sp "
                    + "JOIN Agent as a ON sp.AgentID = a.AgentID "
                    + "JOIN Property as p ON p.PropID = sp.PropID "
                    + "WHERE sp.FinalSP IS NULL";
            ResultSet rs = stmt.executeQuery(sql);

            // Add the data to the table model
            tableModel.addColumn("Agent ID");
            tableModel.addColumn("Agent Name");
            tableModel.addColumn("Agent Rating");
            tableModel.addColumn("Agent Commission");

            tableModel.addColumn("Final Rent");
            tableModel.addColumn("Sold Year");
            tableModel.addColumn("Location");
            tableModel.addColumn("YearOfConstruction");
            while (rs.next()) {
                Object[] row = new Object[9];

                row[0] = rs.getInt("AgentID");
                row[1] = rs.getString("Name");
                row[2] = rs.getDouble("Rating");
                row[3] = rs.getDouble("Commission");

                row[4] = rs.getInt("FinalRent");
                row[5] = rs.getInt("SoldYear");
                row[6] = rs.getString("Location");
                row[7] = rs.getInt("YearOfConstruction");
                tableModel.addRow(row);

            }
            // Close the database connection
            con.close();
            rs.close();
            stmt.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }



    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            // Create a new instance of the front page and make it visible
            JFrame frontPage = new JFrame("Real Estate Office Window");
            JLabel label = new JLabel("Real Estate Office");
            label.setForeground(new Color(255, 255, 255));
            label.setFont(new Font("Arial", Font.BOLD, 45));
            label.setHorizontalAlignment(JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(21,27,41));
            panel.add(label, BorderLayout.CENTER);
            JButton startButton = new JButton("Go to Agent Sales Report in Selling");
            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    office agentGUI = new office();
                    agentGUI.query1();
                    agentGUI.setVisible(true);
                    frontPage.setVisible(false);
                }
            });
            panel.add(startButton, BorderLayout.SOUTH);

            // Add the "Show Agents By Rating" button
            JButton showByRatingButton = new JButton("Go to Agent Sales Report in Renting");
            showByRatingButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    office agentGUI = new office();
                    agentGUI.query2();
                    agentGUI.setVisible(true);
                    ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
                }
            });
            panel.add(showByRatingButton, BorderLayout.NORTH);

            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RealEstateAppFrontPage obj=new RealEstateAppFrontPage();

                    ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
                }
            });

            panel.add(backButton, BorderLayout.WEST);
            frontPage.getContentPane().add(panel, BorderLayout.CENTER);
            frontPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frontPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frontPage.setSize(900, 700);
            frontPage.setLocationRelativeTo(null);
            frontPage.setVisible(true);
        }
    }

   static void REOffice()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up the front page
        JLabel label = new JLabel("Real Estate Office");
        label.setFont(new Font("Arial", Font.BOLD, 45));
        label.setForeground(new Color(255, 255, 255));
        label.setHorizontalAlignment(JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(21,27,41)); // Set background color
        panel.add(label, BorderLayout.CENTER);
        JButton startButton = new JButton("Go to Agent Sales Report in Selling");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                office agentGUI = new office();
                agentGUI.query1();
                agentGUI.setVisible(true);

                ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
            }
        });
        panel.add(startButton, BorderLayout.SOUTH);


        JButton showByRatingButton = new JButton("Go to Agent Sales Report in Renting");
        showByRatingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                office agentGUI = new office();
                agentGUI.query2();
                agentGUI.setVisible(true);
//                    guest g =new guest();
                ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
            }


        });
        panel.add(showByRatingButton, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RealEstateAppFrontPage obj=new RealEstateAppFrontPage();

                ((JButton) e.getSource()).getTopLevelAncestor().setVisible(false);
            }
        });

        panel.add(backButton, BorderLayout.WEST);

// Add the panel to the JFrame
        JFrame frame = new JFrame("Real Estate Office Window");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }



    public static void main(String[] args) {


    REOffice();


    }
}