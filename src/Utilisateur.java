import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

public class Utilisateur {
    private Connection connection ;
    private Statement statement ; 
 	private JFrame frame;
	private JTextField NOMU;
	private JTextField PRENOMU;
	private JTextField NUMU;
	private JTextField USERU;
	private JPasswordField PASSWORDU;
	private final Action action = new SwingAction();
	private final ButtonGroup typebtn = new ButtonGroup();
    
	 public static String generateRandom8DigitNumber() {
	        Random random = new Random();
	        
	        // Generate a random number between 0 and 99999999 (inclusive)
	        int randomNumber = random.nextInt(100000000); // Max value is exclusive, so it will generate numbers from 0 to 99999999
	        
	        // Format the random number to ensure it always has 8 digits
	        String formattedNumber = String.format("%08d", randomNumber); // Pad with leading zeros if necessary
	        
	        return formattedNumber;
	    }
	/**
	 * Launch the application.
	 */
	 public static void main(String[] args) {
		    EventQueue.invokeLater(() -> {
		        try {
		            Utilisateur window = new Utilisateur();
		            window.frame.setVisible(true);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		   
		});
	}

	/**
	 * Create the application.
	 */
	public Utilisateur() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(0, 64, 128));
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.getContentPane().setLayout(null);
		
		JLabel text1 = new JLabel("Nom : ");
		text1.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 11));
		text1.setBounds(31, 24, 46, 14);
		frame.getContentPane().add(text1);
		
		NOMU = new JTextField();
		NOMU.setBounds(104, 21, 120, 20);
		frame.getContentPane().add(NOMU);
		NOMU.setColumns(10);
		
		JLabel text2 = new JLabel("Prenom :");
		text2.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 11));
		text2.setBounds(31, 72, 63, 14);
		frame.getContentPane().add(text2);
		
		PRENOMU = new JTextField();
		PRENOMU.setColumns(10);
		PRENOMU.setBounds(104, 69, 120, 20);
		frame.getContentPane().add(PRENOMU);
		
		JLabel text3 = new JLabel("Telephone :");
		text3.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 11));
		text3.setBounds(31, 123, 74, 17);
		frame.getContentPane().add(text3);
		
		NUMU = new JTextField();
		NUMU.setColumns(10);
		NUMU.setBounds(104, 120, 120, 20);
		frame.getContentPane().add(NUMU);
		
		JLabel text4 = new JLabel("Username : ");
		text4.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 11));
		text4.setBounds(31, 175, 74, 17);
		frame.getContentPane().add(text4);
		
		USERU = new JTextField();
		USERU.setColumns(10);
		USERU.setBounds(104, 172, 120, 20);
		frame.getContentPane().add(USERU);
		
		JLabel text5 = new JLabel("Password : ");
		text5.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 11));
		text5.setBounds(31, 228, 74, 14);
		frame.getContentPane().add(text5);
		
		PASSWORDU = new JPasswordField();
		PASSWORDU.setBounds(104, 225, 120, 20);
		frame.getContentPane().add(PASSWORDU);
		
		JRadioButton ADMINU = new JRadioButton("Admin");
		typebtn.add(ADMINU);
		ADMINU.setBounds(86, 292, 109, 23);
		frame.getContentPane().add(ADMINU);
		
		JRadioButton S_ADMINU = new JRadioButton("Super admin");
		typebtn.add(S_ADMINU);
		S_ADMINU.setBounds(223, 292, 109, 23);
		frame.getContentPane().add(S_ADMINU);
	
		JButton confirmbtn = new JButton("Confirmer");
		confirmbtn.setBackground(new Color(0, 128, 255));
		confirmbtn.setForeground(new Color(255, 255, 255));
		confirmbtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
	             try { 
	            	 statement = connection.createStatement() ; 
	            	 	 String TYPE = null;

	                if (ADMINU.isSelected()) {
	                    TYPE = "ADMIN";
	                } else if (S_ADMINU.isSelected()) {
	                    TYPE = "SUPER ADMIN";
	                }

	                String NOMUS = NOMU.getText();
	                String PRENOMUS = PRENOMU.getText() ; 
	                String NUMUS = NUMU.getText() ; 
	                String USERNAMEU = USERU.getText();
	                String PASSWORDUS = PASSWORDU.getText(); 
	                String TYPEUS = TYPE ;
	                String IDUTIL=generateRandom8DigitNumber();
	                String sql = "INSERT INTO UTILISAT VALUES ('"+IDUTIL+"','"+NOMUS+"','"+PRENOMUS+"','"+NUMUS+"','"+USERNAMEU+"','"+PASSWORDUS+"','"+TYPEUS+"')" ;
	                statement.execute(sql);
	                JOptionPane.showMessageDialog(confirmbtn, "L'utilisateur a été bien créé") ; 
	                frame.dispose();
	                } catch (Exception e2) {
	            		e2.printStackTrace() ; 
	        		}
	             
	                
			}});
		confirmbtn.setBounds(376, 219, 120, 36);
		frame.getContentPane().add(confirmbtn);
		
		JLabel lblMerci = new JLabel("Merci");
		lblMerci.setHorizontalAlignment(SwingConstants.CENTER);
		lblMerci.setFont(new Font("Script MT Bold", Font.ITALIC, 35));
		lblMerci.setBounds(422, 313, 143, 52);
		frame.getContentPane().add(lblMerci);
		frame.setBounds(100, 100, 653, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver") ;
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system" ,"halwan") ;
			
		} catch (Exception e1) {
		e1.printStackTrace() ; 
		}
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
