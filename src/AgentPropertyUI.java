import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AgentPropertyUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private JLabel agentLabel;
    private ArrayList<JPanel> propertyPanelList = new ArrayList<>();
    private JButton[] contactSellerButtonArray;
    private ArrayList<Image> imageList = new ArrayList<>();

    public AgentPropertyUI(int agentID) {
        setTitle("Agent Properties");
        setSize(800, 600);
        setBackground(new Color(21,27,41));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(21,27,41));

//        agentLabel = new JLabel("Properties for Agent " + agentID);
//        agentLabel.setText("<html><span style='font-weight:bold; font-size:24px; color:white;'>Listed Properties for Agent " + agentID + "</span></html>");
//        agentLabel.setForeground(Color.WHITE);
//        agentLabel.setHorizontalAlignment(JLabel.CENTER);
//        mainPanel.add(agentLabel, BorderLayout.NORTH);
        // Create a back button


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/project", "root", "13Lock02");

            PreparedStatement pstmt = con.prepareStatement("SELECT PropID FROM PropAg WHERE AgentID=?");
            PreparedStatement pstmt5 = con.prepareStatement("SELECT * FROM Agent WHERE AgentID=?");
            pstmt.setInt(1, agentID);
            pstmt5.setInt(1, agentID);
            ResultSet rs1 = pstmt.executeQuery();
            ResultSet rs5 = pstmt5.executeQuery();
            String nameAg="";
            if(rs5.next())
            {
                nameAg =rs5.getString("Name");
            }
            agentLabel = new JLabel("Properties for Agent " + agentID);
            agentLabel.setText("<html><span style='font-weight:bold; font-size:24px; color:white;'>Listed Properties for Agent " + nameAg + "</span></html>");
            agentLabel.setForeground(Color.WHITE);
            agentLabel.setHorizontalAlignment(JLabel.CENTER);
            JPanel agentPanel = new JPanel(new BorderLayout());
            agentPanel.setBackground(new Color(21,27,41));

            JPanel buttonPanelx = new JPanel(new FlowLayout());
            buttonPanelx.setBackground(new Color(21,27,41));
            JButton backButton = new JButton("<------Back");
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Close the current window and go back to the previous window
                    MyGUI obj=new MyGUI(agentID);
                    obj.setVisible(true);
                    dispose();
                }
            });

            buttonPanelx.add(backButton);
            buttonPanelx.add(agentLabel);
            //agentPanel.add(agentLabel, BorderLayout.NORTH);
            agentPanel.add(buttonPanelx);

            mainPanel.add(agentPanel, BorderLayout.NORTH);
            int rowCount = 0;
            int maxRows = 8;
            contactSellerButtonArray = new JButton[maxRows];

            while (rs1.next() && rowCount < maxRows) {
                int propID = rs1.getInt("PropID");

                PreparedStatement pstmt2 = con.prepareStatement("SELECT * FROM Property WHERE PropID=?");
                pstmt2.setInt(1, propID);
                ResultSet rs2 = pstmt2.executeQuery();

                if (rs2.next()) {
                    String location = rs2.getString("Location");
                    int bhk = rs2.getInt("BHK");
                    int rent =rs2.getInt("Rent");
                    int sqFtArea = rs2.getInt("SqFtArea");
                    String purchaseType = rs2.getString("PurchaseType");
                    int sellingPrice = rs2.getInt("SellingPrice");

                    byte[] imageData = rs2.getBytes("Image");
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
                    propertyPanel.setBackground(new Color(21,27,41));
                    propertyPanel.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    c.insets = new Insets(5, 5, 5, 5);

                    JLabel imageLabel = new JLabel();
                    imageLabel.setPreferredSize(new Dimension(150, 150));
                    if (imageData != null) {
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
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
                    detailsPanel.setForeground(Color.WHITE);
                    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
                    detailsPanel.setPreferredSize(new Dimension(400, 150));

                    JLabel locationLabel = new JLabel("Location: " + location);
                    locationLabel.setForeground(Color.WHITE);
                    detailsPanel.add(locationLabel);

                    JLabel bhkLabel = new JLabel("BHK: " + bhk);
                    bhkLabel.setForeground(Color.WHITE);
                    detailsPanel.add(bhkLabel);

                    JLabel sqFtAreaLabel = new JLabel("SqFt Area: " + sqFtArea);
                    sqFtAreaLabel.setForeground(Color.WHITE);
                    detailsPanel.add(sqFtAreaLabel);

                    JLabel purchaseTypeLabel = new JLabel("Purchase Type: " + purchaseType);
                    purchaseTypeLabel.setForeground(Color.WHITE);
                    detailsPanel.add(purchaseTypeLabel);

                    JLabel RentPriceLabel = new JLabel("Rent: " + rent);
                    RentPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(RentPriceLabel);

                    JLabel sellingPriceLabel = new JLabel("Selling Price: " + sellingPrice);
                    sellingPriceLabel.setForeground(Color.WHITE);
                    detailsPanel.add(sellingPriceLabel);

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
                    contactSellerButtonArray[rowCount].setBackground(new Color(21,27,41));
                    buttonPanel.add(contactSellerButtonArray[rowCount]);

                    detailsPanel.add(buttonPanel);

                    c.gridx = 1;
                    c.gridy = 0;
                    propertyPanel.add(detailsPanel, c);

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
                int rowCount =0;
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

//   public static void main(String[] args)
//   {
//       AgentPropertyUI ui = new AgentPropertyUI(201); // Replace 123 with the actual agent ID
//      ui.setVisible(true);
//   }

}
