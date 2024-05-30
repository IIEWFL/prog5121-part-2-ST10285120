import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
/* this class implements aa user and registration and login application using Swing GUI
 * code adapted and aided by 
 * Java GUI: Full Course(FREE): https://www.youtube.com/watch?v=Kmgo00avvEw
 * Video create and uploaded by: Bro Code : https://www.youtube.com/@BroCodez 
*/
public class UserRegistrationLogin extends JFrame {
    private JTextField firstNameField, lastNameField, usernameField;
    private JPasswordField passwordField;
    private JLabel registerStatusLabel;
    private JButton registerButton, loginLinkButton;

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JLabel loginStatusLabel;
    private JButton loginButton;

    private ArrayList<Task> tasks = new ArrayList<>();
    private boolean loggedIn = false;

    public UserRegistrationLogin() {
        setTitle("Registration");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Username"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        registerStatusLabel = new JLabel();
        panel.add(registerStatusLabel);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterListener());
        panel.add(registerButton);

        loginLinkButton = new JButton("Already registered? Login here.");
        loginLinkButton.addActionListener(new LoginLinkListener());
        panel.add(loginLinkButton);

        add(panel);
        setVisible(true);
    }

    private class LoginLinkListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            openLoginFrame();
        }
    }

    private void openLoginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField();
        panel.add(loginUsernameField);

        panel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField();
        panel.add(loginPasswordField);

        loginStatusLabel = new JLabel();
        panel.add(loginStatusLabel);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginListener());
        panel.add(loginButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }
     /*
       These boolean expression checks to see if the username and password complexity matches 
        Code adapted and aided by: Stephan Muller at https://stackoverflow.com/users/124238/stephan-muller code found at: https://stackoverflow.com/questions/30012770/regex-for-username
         also by MChaker :https://stackoverflow.com/users/4851900/mchaker at https://stackoverflow.com/questions/30012770/regex-for-username
         and Boolean Java Tutorial#15 :https://www.youtube.com/watch?v=CHVVEGRGiJU&t=584s by:https://www.youtube.com/@alexlorenlee
     */
    boolean checkUsername(String username) {
        return Pattern.matches("^\\w{1,5}_\\w+$", username);
    }

    boolean checkPasswordComplexity(String password) {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", password);
    }

    String registerUser(String firstName, String lastName, String username, String password) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
            writer.write(firstName + "," + lastName + "," + username + "," + password + "\n");
            writer.close();
            return "User registered successfully!";
        } catch (IOException e) {
            return "Error occurred during registration.";
        }
    }

    boolean loginUser(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo[2].equals(username) && userInfo[3].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String returnLoginStatus(boolean success) {
        if (success) {
            String[] userInfo = getUserInfo(loginUsernameField.getText());
            return "Welcome " + userInfo[0] + " " + userInfo[1] + ". It's great to see you again.";
        } else {
            return "Username or password incorrect.";
        }
    }
    /*
      The below code checks a text file that stored user registration information so that they can log in 
     code adapted from  coding with John: https://www.youtube.com/@CodingWithJohn video link: https://www.youtube.com/watch?v=ScUJx4aWRi0
    */
    private String[] getUserInfo(String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo[2].equals(username)) {
                    reader.close();
                    return userInfo;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
     /*
    *this code is adapted form a user and login tutorial it checks user inputed information and displays JOption panes depending on the outcome
    *Author: David KrouKamp:https://stackoverflow.com/users/1133011/david-kroukamp
    *found at: https://stackoverflow.com/questions/13055107/joptionpane-check-user-input-and-prevent-from-closing-until-conditions-are-met
    */
    private class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!checkUsername(username)) {
                JOptionPane.showMessageDialog(UserRegistrationLogin.this,
                        "Username is not correctly formatted. Please ensure that it contains an underscore and is no more than 5 characters long.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else if (!checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(UserRegistrationLogin.this,
                        "Password is not correctly formatted. Please ensure that it contains at least 8 characters, a capital letter, a number, and a special character.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = registerUser(firstName, lastName, username, password);
                JOptionPane.showMessageDialog(UserRegistrationLogin.this, result, "Registration Status", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());

            if (loginUser(username, password)) {
                loggedIn = true;
                JOptionPane.showMessageDialog(UserRegistrationLogin.this, returnLoginStatus(true));
                openTaskMenu();
            } else {
                JOptionPane.showMessageDialog(UserRegistrationLogin.this, returnLoginStatus(false), "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openTaskMenu() {
        String[] options = {"Add Tasks", "Show Report", "Quit"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null, "Welcome to EasyKanban\nChoose an option:",
                    "Task Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0:
                    addTasks();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Coming Soon", "Show Report", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Goodbye!", "Quit", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    break;
            }
        } while (choice != 2);
    }

    private void addTasks() {
        int numTasks = Integer.parseInt(JOptionPane.showInputDialog("How many tasks would you like to add?"));
        for (int i = 0; i < numTasks; i++) {
            String taskName = JOptionPane.showInputDialog("Enter Task Name:");
            String taskDescription = JOptionPane.showInputDialog("Enter Task Description:");
            String developerDetails = JOptionPane.showInputDialog("Enter Developer Details:");
            int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter Task Duration (in hours):"));
            String[] statuses = {"To Do", "Doing", "Done"};
            String taskStatus = (String) JOptionPane.showInputDialog(null, "Select Task Status:", "Task Status",
                    JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);

            Task newTask = new Task(taskName, taskDescription, developerDetails, taskDuration, taskStatus);
            if (!newTask.checkTaskDescription()) {
                JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                tasks.add(newTask);
                JOptionPane.showMessageDialog(null, newTask.printTaskDetails(), "Task Added", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(null, "Total hours of all tasks: " + Task.returnTotalHours(), "Total Hours", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserRegistrationLogin::new);
    }
}
