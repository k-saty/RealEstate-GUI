import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AgentStatusPropertyUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private JLabel agentLabel, agentLabel2;
    private ArrayList<JPanel> propertyPanelList = new ArrayList<>();
    private JButton[] contactSellerButtonArray;
    private ArrayList<Image> imageList = new ArrayList<>();

    public AgentStatusPropertyUI(int agentID) {
        setTitle("Agent Properties");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(21,27,41));


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/project", "root", "13Lock02");

            PreparedStatement ag = con.prepareStatement("SELECT * FROM Agent WHERE AgentID=?");
            ag.setInt(1, agentID);
            ResultSet agen = ag.executeQuery();
            JPanel panel = new JPanel();

            JPanel agentPanel = new JPanel(new BorderLayout());
            agentPanel.setBackground(new Color(21,27,41));

// Create a new JLabel with the text "Your profile"
            // Create a new JLabel with the text "Your profile"
            JLabel titleLabel = new JLabel("Your profile");
            titleLabel.setText("<html><span style='font-weight:bold; font-size:24px; color:white;'>Sold/Rented Properties for Agent " + agentID + "</span></html>");
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // center alignment
            agentPanel.add(titleLabel, BorderLayout.NORTH);


// Retrieve the agent's data from the MySQL database using the AgentI
            String query = "SELECT Rating, Commission, Name, PhNumber, Email FROM Agent WHERE AgentID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, agentID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Create a new JLabel for each field of the agent's data
                JLabel ratingLabel = new JLabel("Rating: " + resultSet.getDouble("Rating"));
                JLabel commissionLabel = new JLabel("Commission: " + resultSet.getDouble("Commission"));
                JLabel nameLabel = new JLabel("Name: " + resultSet.getString("Name"));
                JLabel phNumberLabel = new JLabel("Phone Number: " + resultSet.getLong("PhNumber"));
                JLabel emailLabel = new JLabel("Email: " + resultSet.getString("Email"));

                ratingLabel.setBackground(Color.BLUE);
                commissionLabel.setBackground(Color.BLUE);
                nameLabel.setBackground(Color.BLUE);
                phNumberLabel.setBackground(Color.BLUE);
                emailLabel.setBackground(Color.BLUE);

                ratingLabel.setForeground(Color.WHITE);
                commissionLabel.setForeground(Color.WHITE);
                nameLabel.setForeground(Color.WHITE);
                phNumberLabel.setForeground(Color.WHITE);
                emailLabel.setForeground(Color.WHITE);

                ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                commissionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                phNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);




// Add the JLabels to the panel's CENTER position
                JPanel centerPanel = new JPanel();
                centerPanel.setBackground(new Color(21,27,41));
                BoxLayout boxLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
                centerPanel.setLayout(boxLayout);


                centerPanel.add(nameLabel);
                centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                centerPanel.add(phNumberLabel);
                centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                centerPanel.add(emailLabel);
                agentPanel.add(centerPanel, BorderLayout.CENTER);
                centerPanel.add(ratingLabel);
                centerPanel.add(Box.createRigidArea(new Dimension(0, 5))); // add spacing between components
                centerPanel.add(commissionLabel);
                centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            JPanel buttonPanelx = new JPanel(new FlowLayout());
            buttonPanelx.setBackground(new Color(21,27,41));

            JButton addRentedPropertyButton = new JButton("Add rented property");
            JButton addSoldPropertyButton = new JButton("Add sold property");
            JButton Backbutton =new JButton("<---- back");

            buttonPanelx.add(Backbutton);
            buttonPanelx.add(addRentedPropertyButton);
            buttonPanelx.add(addSoldPropertyButton);
            Backbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    //dispose();
                    MyGUI obj =new MyGUI(agentID);
                    obj.setVisible(true);
                    dispose();
                }

            });
            agentPanel.add(buttonPanelx, BorderLayout.SOUTH);

            mainPanel.add(agentPanel, BorderLayout.NORTH);

            addRentedPropertyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame popupFrame = new JFrame("Add rented property");
                    popupFrame.setSize(200, 200);
                    popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel popupPanel = new JPanel();
                    popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

                    JLabel propIdLabel = new JLabel("PropID:");
                    JTextField propIdTextField = new JTextField(10);
                    JLabel finalRentLabel = new JLabel("Final rent:");
                    JTextField finalRentTextField = new JTextField(10);
                    JLabel RentYear = new JLabel("Year:");
                    JTextField RentYearField = new JTextField(10);

                    popupPanel.add(propIdLabel);
                    popupPanel.add(propIdTextField);
                    popupPanel.add(finalRentLabel);
                    popupPanel.add(finalRentTextField);
                    popupPanel.add(RentYear);
                    popupPanel.add(RentYearField);

                    JButton saveButton = new JButton("Save");
                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int propID = Integer.parseInt(propIdTextField.getText());
                            int finalRent = Integer.parseInt(finalRentTextField.getText());
                            int rentYear = Integer.parseInt(RentYearField.getText());
                            String addR = "Insert into statusprop values("+propID+","+agentID+",NULL,"+finalRent+","+rentYear+");";
                            Statement stmt = null;
                            try {
                                stmt = con.createStatement();
                                stmt.executeUpdate(addR);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }



                            // Do something with the input data
                            popupFrame.dispose();
                        }
                    });
                    popupPanel.add(saveButton);

                    popupFrame.add(popupPanel);
                    popupFrame.setVisible(true);
                }
            });

            addSoldPropertyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame popupFrame = new JFrame("Add Sold property");
                    popupFrame.setSize(200, 200);
                    popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel popupPanel = new JPanel();
                    popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

                    JLabel propIdLabel = new JLabel("PropID:");
                    JTextField propIdTextField = new JTextField(10);
                    JLabel finalRentLabel = new JLabel("Final Selling price:");
                    JTextField finalRentTextField = new JTextField(10);
                    JLabel RentYear = new JLabel("Year:");
                    JTextField RentYearField = new JTextField(10);

                    popupPanel.add(propIdLabel);
                    popupPanel.add(propIdTextField);
                    popupPanel.add(finalRentLabel);
                    popupPanel.add(finalRentTextField);
                    popupPanel.add(RentYear);
                    popupPanel.add(RentYearField);

                    JButton saveButton = new JButton("Save");
                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int propID = Integer.parseInt(propIdTextField.getText());
                            int finalRent = Integer.parseInt(finalRentTextField.getText());
                            int rentYear = Integer.parseInt(RentYearField.getText());
                            String addR = "Insert into statusprop values("+propID+","+agentID+","+finalRent+",NULL,"+rentYear+");";
                            Statement stmt = null;
                            try {
                                stmt = con.createStatement();
                                stmt.executeUpdate(addR);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }



                            // Do something with the input data
                            popupFrame.dispose();
                        }
                    });
                    popupPanel.add(saveButton);

                    popupFrame.add(popupPanel);
                    popupFrame.setVisible(true);
                }
            });

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM statusProp WHERE AgentID=?");
            pstmt.setInt(1, agentID);
            ResultSet rs1 = pstmt.executeQuery();


            int rowCount = 0;
            int maxRows = 8;
            contactSellerButtonArray = new JButton[maxRows];

            while (rs1.next() && rowCount < maxRows) {
                int propID = rs1.getInt("PropID");
                int FinalSP = rs1.getInt("FinalSP");
                int FinalRent = rs1.getInt("FinalRent");
                int SoldDate = rs1.getInt("SoldDate");

                PreparedStatement pstmt2 = con.prepareStatement("SELECT * FROM Property WHERE PropID=?");
                PreparedStatement pstmt3 = con.prepareStatement("SELECT YEAR(YearOC) AS Year FROM Property WHERE PropID=?");
                pstmt2.setInt(1, propID);
                pstmt3.setInt(1, propID);
                ResultSet rs2 = pstmt2.executeQuery();
                ResultSet rs3 = pstmt3.executeQuery();

                if (rs2.next() && rs3.next()) {
                    String location = rs2.getString("Location");
                    int bhk = rs2.getInt("BHK");
                    int rent = rs2.getInt("Rent");
                    int sqFtArea = rs2.getInt("SqFtArea");
                    String purchaseType = rs2.getString("PurchaseType");
                    int sellingPrice = rs2.getInt("SellingPrice");
                    byte[] imageData = rs2.getBytes("Image");
                    int yearOC = rs3.getInt("Year");
                    Image image = null;

                    if (imageData != null) {
                        ImageIcon icon = new ImageIcon(imageData);
                        image = icon.getImage();
                        imageList.add(image);
                    } else {
                        imageList.add(null);
                    }

                    int sellerID = rs2.getInt("SellerID");

                    JPanel propertyPanel = new JPanel();
                    propertyPanel.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    c.insets = new Insets(5, 5, 5, 5);

                    JLabel imageLabel = new JLabel();
                    imageLabel.setPreferredSize(new Dimension(250, 250));
                    if (imageData != null) {
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);
                    } else {
                        // Show default image if no image found
                        ImageIcon icon = new ImageIcon(getClass().getResource("default-image.png"));
                        imageLabel.setIcon(icon);
                    }
                    c.gridx = 0;
                    c.gridy = 0;
                    propertyPanel.add(imageLabel, c);

                    JPanel detailsPanel = new JPanel();
                    detailsPanel.setBackground(new Color(21,27,41));
                    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
                    detailsPanel.setPreferredSize(new Dimension(400, 200));

                    JLabel locationLabel = new JLabel("Location: " + location);
                    locationLabel.setForeground(Color.WHITE);
                    detailsPanel.add(locationLabel);

                    JLabel RentPriceLabel = new JLabel("Rent: " + rent);
                    RentPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(RentPriceLabel);

                    JLabel sellingPriceLabel = new JLabel("Selling Price: " + sellingPrice);
                    sellingPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(sellingPriceLabel);

                    JLabel FinalSellingPriceLabel = new JLabel("Final Selling Price " + FinalSP);
                    FinalSellingPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(FinalSellingPriceLabel);

                    JLabel FinalRentPriceLabel = new JLabel("FinalRent: " + FinalRent);
                    FinalRentPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(FinalRentPriceLabel);

                    JLabel YearOC = new JLabel("Year of Creation: " + yearOC);
                    YearOC.setForeground(Color.WHITE);
                    detailsPanel.add(YearOC);

                    JLabel YearsellingPriceLabel = new JLabel("Sold Year: " + SoldDate);
                    YearsellingPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(YearsellingPriceLabel);

                    JLabel YM = new JLabel("Year on Market: " + (SoldDate - yearOC));
                    YM.setForeground(Color.WHITE);
                    detailsPanel.add(YM);

                    contactSellerButtonArray[rowCount] = new JButton("Contact Seller");
                    contactSellerButtonArray[rowCount].putClientProperty("propID", propID);

                    contactSellerButtonArray[rowCount].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int propID = (int) ((JButton) e.getSource()).getClientProperty("propID");

                            try {
                                PreparedStatement pstmt3 = con.prepareStatement("SELECT Name, PhNumber, Email FROM Seller WHERE SellerID=(SELECT SellerID FROM Property WHERE PropID=?)");
                                pstmt3.setInt(1, propID);
                                ResultSet rs3 = pstmt3.executeQuery();

                                if (rs3.next()) {
                                    String sellerName = rs3.getString("Name");
                                    long sellerPhNumber = rs3.getLong("PhNumber");
                                    String sellerEmail = rs3.getString("Email");

                                    JOptionPane.showMessageDialog(mainPanel,
                                            "Seller Name: " + sellerName + "\n"
                                                    + "Phone Number: " + sellerPhNumber + "\n"
                                                    + "Email: " + sellerEmail);
                                }

                                rs3.close();
                                pstmt3.close();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setBackground(new Color(21,27,41));
                    buttonPanel.add(contactSellerButtonArray[rowCount]);

                    detailsPanel.add(buttonPanel);

                    c.gridx = 1;
                    c.gridy = 0;
                    propertyPanel.add(detailsPanel, c);
                    propertyPanel.setBackground(new Color(21,27,41));
                    propertyPanelList.add(propertyPanel);
                    rowCount++;

                }

                rs2.close();
                pstmt2.close();
            }

            rs1.close();
            pstmt.close();

            // Display the property panels in a scrollable pane
            JPanel propertyScrollPanePanel = new JPanel(new GridLayout(0, 1));
            for (JPanel propertyPanel : propertyPanelList) {
                propertyScrollPanePanel.add(propertyPanel);
            }
            JScrollPane propertyScrollPane = new JScrollPane(propertyScrollPanePanel);
            mainPanel.add(propertyScrollPane, BorderLayout.CENTER);

            //con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton btnAddImage = new JButton("Add Image");
        //mainPanel.add(btnAddImage, BorderLayout.SOUTH);
        btnAddImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = 0;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Image File");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
                int userSelection = fileChooser.showOpenDialog(mainPanel);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File imageFile = fileChooser.getSelectedFile();

                    try {
                        Image image = ImageIO.read(imageFile);
                        imageList.add(image);

                        JPanel propertyPanel = new JPanel();
                        propertyPanel.setLayout(new GridBagLayout());
                        GridBagConstraints c = new GridBagConstraints();
                        c.insets = new Insets(5, 5, 5, 5);

                        JLabel imageLabel = new JLabel();
                        imageLabel.setPreferredSize(new Dimension(100, 100));
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);
                        c.gridx = 0;
                        c.gridy = 0;
                        propertyPanel.add(imageLabel, c);

                        JPanel detailsPanel = new JPanel();
                        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
                        detailsPanel.setPreferredSize(new Dimension(400, 100));

                        contactSellerButtonArray[rowCount] = new JButton("Contact Seller");
                        contactSellerButtonArray[rowCount].putClientProperty("propID", 0);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.add(contactSellerButtonArray[rowCount]);

                        detailsPanel.add(buttonPanel);

                        c.gridx = 1;
                        c.gridy = 0;
                        propertyPanel.add(detailsPanel, c);

                        propertyPanelList.add(propertyPanel);
                        rowCount++;

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        setContentPane(mainPanel);
    }

//  public static void main(String[] args) {
//    AgentStatusPropertyUI ui = new AgentStatusPropertyUI(202);
//      ui.setVisible(true);
//   }

}

