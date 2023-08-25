import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyGUI extends JFrame {

    public MyGUI(int id) {
        setTitle("My GUI");
        setSize(400, 300); // set size of the window
        setLocationRelativeTo(null); // center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.RED);

        // create panel and layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // add padding
        panel.setBackground(new Color(21,27,41));

        JButton button1 = new JButton("Show Listed Property");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(button1, constraints);

        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button1.setBackground(new Color(241,255,92)); // Change background color when mouse enters
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button1.setBackground(UIManager.getColor("control")); // Reset background color when mouse exits
            }
        });

// create "Show Profile" button
        JButton button2 = new JButton("Show Profile");
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(button2, constraints);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setBackground(Color.WHITE); // Change background color when mouse enters
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setBackground(UIManager.getColor("control")); // Reset background color when mouse exits
            }
        });

        JButton button3 = new JButton("Log out (|) ");
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(button3, constraints);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //dispose();
                AgentPropertyUI obj =new AgentPropertyUI(id);
                obj.setVisible(true);
                dispose();
            }

        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //dispose();
                AgentStatusPropertyUI obj =new AgentStatusPropertyUI(id);
                obj.setVisible(true);
                dispose();
            }

        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //dispose();
                DAgentLoginGUI obj =new DAgentLoginGUI();
                obj.setVisible(true);
                dispose();
            }

        });

        // add panel to the frame
        add(panel);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            MyGUI gui = new MyGUI(5);
//            gui.setVisible(true);
//        });
//    }
}
