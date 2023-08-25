import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class DAgentLoginGUI extends JFrame {
    JLabel headerLabel, idLabel, passLabel, errorLabel;
    JTextField idTextField, passTextField;
    JButton loginButton;

    public DAgentLoginGUI()
    {


        // Set window properties
        setTitle("Welcome Agent !");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create components
        headerLabel = new JLabel("Enter your credentials below:");
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        idLabel = new JLabel("Agent ID:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setHorizontalAlignment(JLabel.RIGHT); // Set anchor constraint value to EAST
        idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
        idLabel.setSize(new Dimension(20,100));
        passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setHorizontalAlignment(JLabel.RIGHT); // Set anchor constraint value to EAST
        passLabel.setFont(passLabel.getFont().deriveFont(Font.BOLD));
        idTextField = new JTextField();
        idTextField.setBackground(Color.WHITE);
        passTextField = new JPasswordField();
        passTextField.setBackground(Color.WHITE);
        loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(70,130,180));
        loginButton.setForeground(Color.BLACK);
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(errorLabel.getFont().deriveFont(Font.BOLD));

        JButton backButton = new JButton("<--Go Back to Interface");
        backButton.setBackground(new Color(70,130,180));
        backButton.setForeground(Color.BLACK);

// Add components to container with padding and margin
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(21,27,41));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20,40,0,40);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER; // Set anchor constraint value to EAST
        contentPane.add(idLabel, c);
        c.gridy = 1;
        contentPane.add(passLabel, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER; // Set anchor constraint value to WEST
        contentPane.add(idTextField, c);
        c.gridy = 1;
        contentPane.add(passTextField, c);
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 40, 20, 40);
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 30; // set the internal padding of the button
        c.ipady = 5;
        contentPane.add(loginButton, c);
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 40, 20, 40);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 30; // set the internal padding of the button
        c.ipady = 5;
        contentPane.add(backButton, c);

        c.insets = new Insets(5,40,20,40);
        c.gridy = 4;
        contentPane.add(errorLabel, c);

        // Set font style for content pane
        Font font = new Font("Arial", Font.PLAIN, 14);
        contentPane.setFont(font);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                RealEstateAppFrontPage obj =new RealEstateAppFrontPage ();
                obj.setVisible(true);
            }

        });

        // Register event handlers
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String id = idTextField.getText().trim();
                String pass = passTextField.getText().trim();
                if (id.equals("")) {
                    errorLabel.setText("Enter your user ID");
                    return;
                }
                if (pass.equals("")) {
                    errorLabel.setText("Enter your password");
                    return;
                }
                boolean isValid = validateCredentials(id, pass);
                if (isValid) {
                    //JOptionPane.showMessageDialog(null,"Logged in successfully!");
                    // Add code here to open next page or perform other actions
                    loginButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            dispose();
                            MyGUI obj =new MyGUI(Integer.parseInt(id));
                            obj.setVisible(true);
                        }

                    });

                } else {
                    errorLabel.setText("Invalid credentials. Please try again.");
                }
            }
        });
    }

    private boolean validateCredentials(String id, String pass) {
        try {
            // Connect to database
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","13Lock02");
            Statement stmt = con.createStatement();

            // Query for agent with matching id and password
            String query = "SELECT * FROM agent WHERE AgentID=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            // If results found, credentials are valid
            if (rs.next()) {
                return true;

            }
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return false; // Credentials not found in database

    }

//    public static void main(String[] args) {
//        DAgentLoginGUI frame = new DAgentLoginGUI();
//
//        // Set window background color and visibility
//        frame.setVisible(true);
//    }
}