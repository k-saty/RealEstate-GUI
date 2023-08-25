import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class RealEstateAppFrontPage extends JFrame {
    private JButton officeLoginButton, agentLoginButton, guestLoginButton, adminLoginButton;
    private JLabel headingLabel;

    public RealEstateAppFrontPage() {
        // Set the frame properties
        setTitle("GharKhojo");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);



        setLocationRelativeTo(null);


        // Create the components
        officeLoginButton = new JButton("Real estate office login");
        agentLoginButton = new JButton("Agent login");
        guestLoginButton = new JButton("Guest login");
        adminLoginButton = new JButton("Admin login");
        officeLoginButton.setPreferredSize(new Dimension(200, 30));
        agentLoginButton.setPreferredSize(new Dimension(200, 30));
        guestLoginButton.setPreferredSize(new Dimension(200, 30));
        adminLoginButton.setPreferredSize(new Dimension(200, 30));
        headingLabel = new JLabel("GharKhojo", SwingConstants.CENTER);
        headingLabel.setForeground(new Color(255, 255, 255));
        headingLabel.setFont(new Font("Arial", Font.BOLD, 60));

        // Add components to container with padding and margin
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(21,27,41)); // Set the background color to light blue
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20,40,0,40);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER; // Set anchor constraint value to CENTER
        c.weightx = 1.0;
        c.weighty = 0.1;
        contentPane.add(headingLabel, c);
        c.insets = new Insets(10,40,10,40);
        c.gridy = 1;
        contentPane.add(officeLoginButton, c);
        c.gridy = 2;
        contentPane.add(agentLoginButton, c);
        c.gridy = 3;
        contentPane.add(guestLoginButton, c);

        c.gridy = 4;
        contentPane.add(adminLoginButton, c);

        // Add action listeners to buttons
        officeLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                office.REOffice();
            }
        });
        agentLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DAgentLoginGUI obj =new DAgentLoginGUI();
                obj.setVisible(true);
                dispose();
                // TODO: Add agent login functionality
            }
        });
        guestLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guest obj3 =new guest();
            }
        });
        adminLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Open the command prompt terminal and start the mysql client
                    Runtime.getRuntime().exec(new String[] {"osascript", "-e", "tell application \"Terminal\" to do script \"mysql -u root -p13Lock02 project\"", "-e", "tell application \"Terminal\" to activate", "-e", "tell application \"System Events\" to keystroke \"f\" using {control down, command down}"});
                    //dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // TODO: Add agent login functionality
            }
        });

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create a new instance of the front page
        RealEstateAppFrontPage app = new RealEstateAppFrontPage();
    }
}
