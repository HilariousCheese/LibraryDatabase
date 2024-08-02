import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class dashboardGUI extends JFrame implements ActionListener {

    //left panel
    JPanel leftPanel = new JPanel();
    JTextField searchField = new JTextField(20);
    JButton searchButton = new JButton("Book Search");
    JButton profileButton = new JButton("Profile");
    JButton popularBooksButton = new JButton("Popular Books");
    JButton friendsInfoButton = new JButton("Friends Info");
    JButton settingsButton = new JButton("Settings");
    JButton logoutButton = new JButton("Logout");

    //right panel
    JPanel rightPanel = new JPanel();
    JTextArea resultArea = new JTextArea(20, 30);

    // SQL database connection
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public dashboardGUI() {

        // Set up left panel
        leftPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(profileButton);
        buttonsPanel.add(popularBooksButton);
        buttonsPanel.add(friendsInfoButton);
        buttonsPanel.add(settingsButton);
        buttonsPanel.add(logoutButton);
        leftPanel.add(buttonsPanel, BorderLayout.CENTER);

        // Set up right panel
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Set up SQL database connection
        String URL = dbInfo.DB_URL;
        String DB_USER = dbInfo.DB_USER;
        String DB_PW = dbInfo.DB_PW;
        try {
            conn = DriverManager.getConnection(URL, DB_USER, DB_PW);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }

        // Add action listeners to buttons
        searchButton.addActionListener(new SearchButtonListener());
        // profileButton.addActionListener(new ProfileButtonListener());
        // popularBooksButton.addActionListener(new PopularBooksButtonListener());
        // friendsInfoButton.addActionListener(new FriendsInfoButtonListener());
        // settingsButton.addActionListener(new SettingsButtonListener());
        // logoutButton.addActionListener(new LogoutButtonListener());

        // Set up frame
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Search Button Listener
    private class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String searchQuery = searchField.getText();
            try {
                rs = stmt.executeQuery("SELECT * FROM library WHERE bookName LIKE '%" + searchQuery + "%'");
                while (rs.next()) {
                    resultArea.append(
                            "Title: " + rs.getString("bookName") + "\nAuthor: " + rs.getString("author") + "\n\n");
                }
            } catch (SQLException ex) {
                System.out.println("Error querying database: " + ex.getMessage());
            }
        }
    }

    //profile button listener


    //popular book listener

    //friends info listener

    //logout listener



    public static void main(String[] args) {
        new dashboardGUI();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
