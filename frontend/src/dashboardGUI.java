import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class dashboardGUI extends JFrame implements ActionListener {
    private static String user;

    // left panel
    JPanel leftPanel = new JPanel();
    JTextField searchField = new JTextField(20);
    JButton searchButton = new JButton("Book Search");
    JTextField borrowField = new JTextField(20);
    JButton borrowButton = new JButton("Borrow");

    JButton branchButton = new JButton("Branches");
    JButton popularBooksButton = new JButton("Popular Books");
    JButton bookLibraryButton = new JButton("Book Library");
    JButton profileButton = new JButton("Profile");
    JButton settingButton = new JButton("Settings - Admin only");
    JButton logoutButton = new JButton("Logout");

    // right panel
    JPanel rightPanel = new JPanel();
    JTextArea resultArea = new JTextArea(20, 30);

    // SQL database connection
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public dashboardGUI(String user) {
        this.user = user;

        // Set up left panel
        leftPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel borrowPanel = new JPanel();
        borrowPanel.add(borrowField);
        borrowPanel.add(borrowButton);
        leftPanel.add(borrowPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonsPanel.add(bookLibraryButton);
        buttonsPanel.add(branchButton);
        buttonsPanel.add(popularBooksButton);
        buttonsPanel.add(profileButton);
        buttonsPanel.add(settingButton);
        buttonsPanel.add(logoutButton);
        leftPanel.add(buttonsPanel, BorderLayout.SOUTH);

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
        borrowButton.addActionListener(new BorrowButtonListener());

        branchButton.addActionListener(new BranchButtonListener());
        popularBooksButton.addActionListener(new PopularBooksButtonListener());
        bookLibraryButton.addActionListener(new BookLibraryButtonListener());
        profileButton.addActionListener(new ProfileButtonListener());
        settingButton.addActionListener(new SettingButtonListener());
        logoutButton.addActionListener(new LogoutButtonListener());

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
                rs = stmt.executeQuery("SELECT * FROM item WHERE title LIKE '%" + searchQuery + "%'");
                resultArea.setText("");
                while (rs.next()) {
                    resultArea.append(
                            "Title: " + rs.getString("title")
                                    + "\nAuthor: " + rs.getString("author")
                                    + "\n\n");
                }
            } catch (SQLException ex) {
                System.out.println("Error querying database: " + ex.getMessage());
            }
        }
    }

    // Borrow Button Listener
    private class BorrowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get selected book's itemid
            int itemid = 0;

            String borrowQuery = borrowField.getText();
            try {
                rs = stmt.executeQuery("SELECT * FROM item WHERE title LIKE '%" + borrowQuery + "%'");
                while (rs.next()) {
                    itemid = rs.getInt("ItemID");
                }
            } catch (Exception ex) {
                System.out.println("error");
            }

            // Get user's pid
            int pid = getPersonId();

            // Borrow book
            borrowBook(pid, itemid);
        }
    }

    // Method to get user's pid
    private int getPersonId() {
        // String user = "admin";
        try {
            rs = stmt.executeQuery("SELECT PersonId FROM person WHERE uNAME = '" + user + "'");
            if (rs.next()) {
                return rs.getInt("PersonId");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return -1;
    }

    // Method to borrow book
    private void borrowBook(int pid, int itemid) {
        try {
            stmt.executeUpdate("INSERT INTO loan (Pid, Itemid, loanDate, returnDate) VALUES (" + pid + ", " + itemid
                    + ", NOW(), NOW() + INTERVAL 30 DAY)");
            stmt.executeUpdate("UPDATE item SET copies = copies - 1 WHERE ItemId = " + itemid);
            stmt.executeUpdate("UPDATE person SET TotalLoansMade = TotalLoansMade +1 WHERE PersonId = " + pid);
            JOptionPane.showMessageDialog(null, "Book borrowed successfully\nPlease pickup at the branch.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private class BookLibraryButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                rs = stmt.executeQuery("SELECT i.title, i.author, i.copies, i.ItemType, lb.BranchName\r\n"
                        + "FROM item i\r\n"
                        + "JOIN librarybranch lb\r\n"
                        + "ON i.LibraryBranchID = lb.LibraryBranchID;");
                resultArea.setText("");
                while (rs.next()) {
                    resultArea.append(
                            "Title: " + rs.getString("title")
                                    + "\nAuthor: " + rs.getString("author")
                                    + "\nAvaliable Branch: " + rs.getString("BranchName")
                                    + "\nCopies: " + rs.getString("copies")
                                    + "\nGenre: " + rs.getString("ItemType")
                                    + "\n\n");
                }
            } catch (SQLException ex) {
                System.out.println("Error querying database: " + ex.getMessage());
            }
        }

    }

    // Branch button listener
    private class BranchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                rs = stmt.executeQuery("SELECT * FROM librarybranch");
                resultArea.setText("");
                while (rs.next()) {
                    resultArea.append(
                            "BranchID: " + rs.getString("LibraryBranchID")
                                    + "\nName: " + rs.getString("BranchName")
                                    + "\nAddress: " + rs.getString("Address")
                                    + "\n\n");
                }
            } catch (SQLException ex) {
                System.out.println("Error" + ex.getMessage());
            }
        }
    }

    // popular book listener
    private class PopularBooksButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                rs = stmt.executeQuery("SELECT i.title, COUNT(r.ratingid) as totalRatings\r\n"
                        + "FROM item i\r\n"
                        + "JOIN rating r ON i.ItemId = r.ItemId\r\n"
                        + "GROUP BY i.title\r\n"
                        + "ORDER BY totalRatings DESC\r\n"
                        + "LIMIT 1;");
                resultArea.setText("");
                while (rs.next()) {
                    resultArea.append(
                            "__________Trending__________"
                            + "\n\nTitle: " + rs.getString("title")
                            + "\nRatings: " + rs.getString("totalRatings"));
                }
            } catch (Exception ex) {
                System.out.println("Error" + ex.getMessage());
            }
        }
    }

    //setting button listener
    private class SettingButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int pid = getPersonId();
            if (pid == 1) {
                // Open settings window
                settingsGUI settings = new settingsGUI();
                settings.setVisible(true);
            } else {
                // Show error message
                JOptionPane.showMessageDialog(null, "You are not allowed to access the settings option");
            }
        }
    }

    public class settingsGUI extends JFrame {
        public settingsGUI() {
            setSize(400, 300);

            JPanel userPanel = new JPanel();
            userPanel.setLayout(new BorderLayout());

            JTextArea userArea = new JTextArea(20,30);
            userArea.setEditable(false);

            try {
                rs = stmt.executeQuery("SELECT * FROM person");
                while (rs.next()){
                    userArea.append(
                        "person ID: " + rs.getString("personid")
                        + "\nUsername: " + rs.getString("uNAME")
                        + "\nPassword: " + rs.getString("pw")
                        + "\nUser Type: " + rs.getString("usertype")
                        + "\nPreferred Branch: " + rs.getString("preferredbranch")
                        + "\nNo. of book borrowed: " + rs.getString("TotalLoansMade")
                        + "\n\n"
                    );
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            JScrollPane usersScrollPane = new JScrollPane(userArea);
            userPanel.add(usersScrollPane, BorderLayout.CENTER);
            add(userPanel);
        }
    }



    // profile button listener
    private class ProfileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int pid = getPersonId();
                resultArea.setText("");
                rs = stmt.executeQuery("SELECT p.uNAME, p.TotalLoansMade, i.title, l.loanDate, l.returnDate "
                        + "FROM person p "
                        + "JOIN loan l ON p.PersonId = l.Pid "
                        + "JOIN item i ON l.Itemid = i.ItemId "
                        + "WHERE p.PersonId = " + pid);

                boolean hasRows = false;
                while (rs.next()) {
                    hasRows = true;
                    resultArea.append(
                        "Name: " + rs.getString("uNAME")
                        + "\nNumber of Books Borrowed: " + rs.getString("TotalLoansMade")
                        + "\nBook: " + rs.getString("title")
                        + "\nloadndate: " + rs.getString("loanDate")
                        + "\nreturndate: " + rs.getString("returnDate")
                        + "\n\n"
                        );
                
                }
                if (hasRows == false){
                    resultArea.append("You did not borrow any books");
                }

            } catch (Exception ex) {
                System.out.println("error: " + ex.getMessage());
            }
        }
    }

    // logout listener

    public class LogoutButtonListener implements ActionListener {
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            dashboardGUI.this.disable();
            launcher gui = new launcher();
            gui.createGUI();
            dashboardGUI.this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new dashboardGUI(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
