import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class launcher implements ActionListener {

    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel success;

    public static void main(String[] args) {
        launcher gui = new launcher();
        gui.createGUI();
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(50);
        passwordText.setBounds(100, 50, 80, 25);
        panel.add(passwordText);

        JButton button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(this);
        panel.add(button);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);
        success.setText(null);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = new String(passwordText.getPassword());
        
        String URL = dbInfo.DB_URL;
        String DB_USER = dbInfo.DB_USER;
        String DB_PW = dbInfo.DB_PW;

        
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PW)) {
            String query = "SELECT * FROM person WHERE uNAME= ? AND pw = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, user);
                statement.setString(2, password);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        success.setText("Login successful!");

                        dashboardGUI dashboard = new dashboardGUI(user);
                        dashboard.setVisible(true);

                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(userText);
                        frame.dispose();
                    } else {
                        success.setText("Invalid username or password");
                    }
                }
            }
        } catch (SQLException ex) {
            success.setText("Error: " + ex.getMessage());
        }
    }
}