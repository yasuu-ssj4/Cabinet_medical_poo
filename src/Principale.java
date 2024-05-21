import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import javax.swing.JFormattedTextField;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principale {
    private Connection connection ;
    private Statement statement ; 
	private JFrame frame;
	private JTextField UsernameT;
	private JTextField PasswordT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principale window = new Principale();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 638, 446);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenue");
		lblNewLabel.setFont(new Font("French Script MT", Font.ITALIC, 90));
		lblNewLabel.setBounds(166, 11, 403, 85);
		frame.getContentPane().add(lblNewLabel);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(182, 91, 253, 276);
		frame.getContentPane().add(desktopPane);
		
		UsernameT = new JTextField();
		UsernameT.setBounds(26, 34, 205, 35);
		desktopPane.add(UsernameT);
		UsernameT.setForeground(SystemColor.windowBorder);
		UsernameT.setHorizontalAlignment(SwingConstants.CENTER);
		UsernameT.setFont(new Font("Tahoma", Font.ITALIC, 13));
		UsernameT.setToolTipText("");
		UsernameT.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("User Name");
		lblNewLabel_1.setFont(new Font("Sitka Small", Font.ITALIC, 16));
		lblNewLabel_1.setForeground(SystemColor.windowBorder);
		lblNewLabel_1.setBounds(89, 11, 120, 21);
		desktopPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("PassWord");
		lblNewLabel_1_1.setForeground(SystemColor.windowBorder);
		lblNewLabel_1_1.setFont(new Font("Sitka Small", Font.ITALIC, 16));
		lblNewLabel_1_1.setBounds(89, 80, 120, 21);
		desktopPane.add(lblNewLabel_1_1);
		
		PasswordT = new JTextField();
		PasswordT.setToolTipText("");
		PasswordT.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordT.setForeground(SystemColor.windowBorder);
		PasswordT.setFont(new Font("Tahoma", Font.ITALIC, 13));
		PasswordT.setColumns(10);
		PasswordT.setBounds(26, 103, 205, 35);
		desktopPane.add(PasswordT);
		
		JButton btnNewButton = new JButton("Inscrire");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = UsernameT.getText().trim() ; 
				String password = PasswordT.getText().trim() ; 
				 if (validerLogin(username, password)) {
	                    
					 AccueilP accueilP = new AccueilP(username);
	                  accueilP.showFrame(); 
	                    frame.setVisible(false);
	                } else {
	                    
	                    JOptionPane.showMessageDialog(frame, "Nom d'utilisateur ou mot de passe incorrect", " Veuillez réessayer", JOptionPane.ERROR_MESSAGE);
	                }
				
			}
		});
		btnNewButton.setFont(new Font("Rockwell", Font.ITALIC, 14));
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.menu);
		btnNewButton.setBounds(82, 164, 89, 23);
		desktopPane.add(btnNewButton);
		
		JButton btnCrerUnCompte = new JButton("Créer un compte");
		btnCrerUnCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					statement = connection.createStatement() ;
					Utilisateur.main(null) ; 
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		btnCrerUnCompte.setForeground(SystemColor.menu);
		btnCrerUnCompte.setFont(new Font("Papyrus", Font.PLAIN, 14));
		btnCrerUnCompte.setBackground(SystemColor.textHighlight);
		btnCrerUnCompte.setBounds(46, 216, 163, 35);
		desktopPane.add(btnCrerUnCompte);
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver") ;
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system" ,"halwan") ;
			createUtilisateurTable();
		} catch (Exception e2) {
		e2.printStackTrace() ; 
		}
	}
	public String getUsername() {
		return UsernameT.getText() ;
		
	}
	private void createUtilisateurTable() throws SQLException {
        String createTableSQL = "CREATE TABLE utilisat (" +
                "IDUT VARCHAR2(8), " +
                "nom VARCHAR2(20), " +
                "prenom VARCHAR2(20), " +
                "telephone VARCHAR2(10), " +
                "username VARCHAR2(20), " +
                "password VARCHAR2(20), " +
                "type VARCHAR2(20))";

        Statement statement = connection.createStatement();

        try {
            statement.execute(createTableSQL);
            
        } catch (SQLException e) {
           
            if (e.getErrorCode() == 955) {
                
            } else {
                throw e; 
            }
        } finally {
            statement.close();
        }
    }
    private boolean validerLogin(String username, String password) {
        String sql = "SELECT * FROM utilisat WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

