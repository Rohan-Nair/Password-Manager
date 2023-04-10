import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Password {
    public static void main(String[] args) {
        LoginWindow login = new LoginWindow();
        login.setVisible(true);
    }
}

class LoginWindow extends JFrame implements ActionListener {
    JLabel userLabel, passLabel;
    JTextField username;
    JPasswordField password;
    JButton loginButton, changePassButton;
    JPanel loginPanel;

    LoginWindow() {
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(400, 200));
        loginPanel.setLayout(new GridLayout(3, 2, 10, 30));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        userLabel = new JLabel("Username:");
        username = new JTextField();
        passLabel = new JLabel("Password:");
        password = new JPasswordField();
        // userLabel.setPreferredSize(new Dimension(300, 10));

        loginButton = new JButton("Login");
        changePassButton = new JButton("Change Password");
        loginPanel.add(userLabel);
        loginPanel.add(username);
        loginPanel.add(passLabel);
        loginPanel.add(password);
        loginPanel.add(loginButton);
        loginPanel.add(changePassButton);
        add(loginPanel);

        loginButton.addActionListener(this);
        changePassButton.addActionListener(this);

        setTitle("Login");
        setSize(new Dimension(400, 200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        String usernamechk = "";
        String passwordchk = "";
        // fetching the username and password from the file
        File userFile = new File("user.txt");
        if (userFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(userFile));
                usernamechk += reader.readLine();
                passwordchk += reader.readLine();
                reader.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error loading details.");
            }
        }
        String usernameIP = username.getText();
        String passwordIP = new String(password.getPassword());

        try {
            if (usernameIP.equals(usernamechk) && passwordIP.equals(passwordchk)) {
                // login successful
                JOptionPane.showMessageDialog(null, "Login Successful");
                // create passwords window
                MainPage mainPageWindow = new MainPage();
                mainPageWindow.setVisible(true);
                dispose(); // to delete the current window
            } else {
                throw new Exception("error");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Wrong Credentials");
        }

        if (e.getSource() == changePassButton) {
            // create change password window
            ChangePasswords changePasswordsWindow = new ChangePasswords();
            changePasswordsWindow.setVisible(true);
        }
    }
}

class ChangePasswords extends JFrame implements ActionListener {
    JLabel newUserLabel, newPassLabel;
    JTextField newUserField;
    JPasswordField newPassField;
    JButton updateButton;
    JPanel changePassPanel;

    ChangePasswords() {
        changePassPanel = new JPanel();
        changePassPanel.setLayout(new GridLayout(3, 3, 10, 20));
        changePassPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        newUserLabel = new JLabel("New Username:");
        newPassLabel = new JLabel("New Password:");
        newUserField = new JTextField();
        newPassField = new JPasswordField();
        updateButton = new JButton("Update");
        updateButton.setFocusable(false);

        updateButton.addActionListener(this);

        changePassPanel.add(newUserLabel);
        changePassPanel.add(newUserField);
        changePassPanel.add(newPassLabel);
        changePassPanel.add(newPassField);
        changePassPanel.add(updateButton);
        add(changePassPanel);

        setTitle("Change Password");
        setSize(new Dimension(400, 200));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            String newUserToAdd = newUserField.getText();
            String newPassToAdd = new String(newPassField.getPassword());
            File userFile = new File("user.txt");
            if (userFile.exists()) {
                try {
                    FileWriter writer = new FileWriter("user.txt", false);
                    writer.write(newUserToAdd + "\n" + newPassToAdd);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Password Updated Successfully");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Couldn't Update");
                }
            }
        }
    }
}

class MainPage extends JFrame implements ActionListener {

    JButton listPasswords, generatePassword, addPassword, closeButton;
    JPanel mainPanel;

    MainPage() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 0, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        listPasswords = new JButton("List Passwords");
        generatePassword = new JButton("Generate Password");
        addPassword = new JButton("Add New Password");
        closeButton = new JButton("Close");

        mainPanel.add(listPasswords);
        mainPanel.add(generatePassword);
        mainPanel.add(addPassword);
        mainPanel.add(closeButton);

        // action listeners
        listPasswords.addActionListener(this);
        generatePassword.addActionListener(this);
        addPassword.addActionListener(this);
        closeButton.addActionListener(this);

        add(mainPanel);
        setTitle("Home");
        setSize(new Dimension(300, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generatePassword) {
            // generating the password
            char[] pwd = genPassword(8);
            String newPwd = "";
            for (int i = 0; i < 8; i++) {
                newPwd += Character.toString(pwd[i]);
            }
            // adding it to the passwords.txt
            try {
                FileWriter writer = new FileWriter("pwd.txt", true);
                writer.write(newPwd + "\n");
                writer.close();
                JOptionPane.showMessageDialog(null, "Password Generated");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Password file does not exist.");
            }
        }

        if (e.getSource() == addPassword) {
            AddPassword addPwdFrame = new AddPassword();
            addPwdFrame.setVisible(true);
        }

        if (e.getSource() == listPasswords) {
            ListPasswords listPasswordsFrame = new ListPasswords();
            listPasswordsFrame.setVisible(true);
        }

        if (e.getSource() == closeButton) {
            dispose();
        }
    }

    // password generator
    private char[] genPassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

}

class AddPassword extends JFrame implements ActionListener {
    JLabel passwordLabel;
    JTextField pwdField;
    JButton addPwd;
    JPanel addPwdPanel;

    AddPassword() {
        addPwdPanel = new JPanel();
        addPwdPanel.setLayout(new GridLayout(2, 2, 0, 30));
        addPwdPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        passwordLabel = new JLabel("Password:");
        pwdField = new JTextField();
        addPwd = new JButton("Add Password");

        addPwdPanel.add(passwordLabel);
        addPwdPanel.add(pwdField);
        addPwdPanel.add(addPwd);

        addPwd.addActionListener(this);

        add(addPwdPanel);
        setTitle("Add Password");
        setSize(new Dimension(300, 200));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPwd) {
            String pwdToAdd = pwdField.getText();
            try {
                FileWriter writer = new FileWriter("pwd.txt", true);
                writer.write(pwdToAdd + "\n");
                writer.close();
                JOptionPane.showMessageDialog(null, "Password Added");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Password file not found!");
            }
        }
    }
}

class ListPasswords extends JFrame implements ActionListener {
    JTextArea listPwd;
    JPanel listPwdPanel;
    JButton delPwds;

    ListPasswords() {
        listPwdPanel = new JPanel();
        listPwdPanel.setLayout(new GridLayout(2, 1, 0, 30));
        listPwdPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        delPwds = new JButton("Delete Passwords");
        listPwd = new JTextArea();
        try {
            FileReader reader = new FileReader("pwd.txt");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                listPwd.append(line + "\n");
            }
            br.close();
            reader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading Passwords");
        }
        delPwds.addActionListener(this);

        listPwdPanel.add(listPwd);
        listPwdPanel.add(delPwds);
        add(listPwdPanel);
        setTitle("List Passwords");
        setSize(new Dimension(300, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delPwds) {
            listPwd.setText("");
            try {
                FileWriter writer = new FileWriter("pwd.txt", false);
                writer.flush();
                writer.close();
                JOptionPane.showMessageDialog(null, "All Passwords Deleted");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Nothing to Delete here.");
            }
        }
    }
}