import javax.swing.*;

public class dashboardGUI extends JFrame {
    public dashboardGUI() {
        super("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard!");
        welcomeLabel.setBounds(100, 100, 200, 25);

        add(welcomeLabel);

        setVisible(true);
    }
}