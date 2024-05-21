import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.Box;
import java.awt.Canvas;
import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Dimension;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuBar;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Patient {

	private JFrame frame;
	private JTextField nomP;
	private JTextField prenomP;
	private JTextField adrP;
	private JTextField tlphP;
	private JTextField emailP;
	private JTextField assrP;
	private JTextField profP;
	private JTextField jour;
    private JTextField mois;
    private JTextField anne;
    private Connection connection ;
    private static Statement statement ; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patient window = new Patient();
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
	public Patient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.getContentPane().setLayout(null);
		 try { 
				Class.forName("oracle.jdbc.driver.OracleDriver") ;
				 connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system" ,"halwan") ;
				
			} catch (Exception e1) {
			e1.printStackTrace() ; 
			}
		 try {
				createPatientTable() ;
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			} 
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBounds(-10008, -10031, 145, 32);
		addPopup(frame.getContentPane(), popupMenu);
		
		JLabel lblNewLabel = new JLabel("Nom : ");
		lblNewLabel.setBounds(67, 39, 84, 36);
		lblNewLabel.setForeground(SystemColor.windowBorder);
		lblNewLabel.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblNewLabel);
		
		nomP = new JTextField();
		nomP.setBounds(195, 41, 148, 36);
		nomP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
		frame.getContentPane().add(nomP);
		nomP.setColumns(10);
		
		prenomP = new JTextField();
		prenomP.setBounds(195, 117, 148, 36);
		prenomP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
		prenomP.setColumns(10);
		frame.getContentPane().add(prenomP);
		
		JLabel lblPrenom = new JLabel("Prenom : ");
		lblPrenom.setBounds(67, 115, 84, 36);
		lblPrenom.setForeground(SystemColor.windowBorder);
		lblPrenom.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblPrenom);
		
		adrP = new JTextField();
		adrP.setBounds(195, 196, 148, 36);
		adrP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
		adrP.setColumns(10);
		frame.getContentPane().add(adrP);
		
		JLabel lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setBounds(67, 194, 84, 36);
		lblAdresse.setForeground(SystemColor.windowBorder);
		lblAdresse.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblAdresse);
		
		tlphP = new JTextField();
		tlphP.setBounds(195, 285, 148, 36);
		tlphP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
		tlphP.setColumns(10);
		frame.getContentPane().add(tlphP);
		
		JLabel lblNTelephone = new JLabel("Telephone : ");
		lblNTelephone.setBounds(56, 283, 80, 36);
		lblNTelephone.setForeground(SystemColor.windowBorder);
		lblNTelephone.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblNTelephone);
		
		emailP = new JTextField();
		emailP.setBounds(195, 379, 148, 36);
		emailP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 16));
		emailP.setColumns(10);
		frame.getContentPane().add(emailP);
		
		JLabel lblGroupeSang = new JLabel("Email :");
		lblGroupeSang.setBounds(67, 374, 118, 36);
		lblGroupeSang.setForeground(SystemColor.windowBorder);
		lblGroupeSang.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblGroupeSang);
		
		assrP = new JTextField();
		assrP.setBounds(195, 469, 148, 36);
		assrP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
		assrP.setColumns(10);
		frame.getContentPane().add(assrP);
		
		JLabel lblNAssurance = new JLabel("N° Assurance : ");
		lblNAssurance.setBounds(67, 464, 118, 36);
		lblNAssurance.setForeground(SystemColor.windowBorder);
		lblNAssurance.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblNAssurance);
		
		profP = new JTextField();
		profP.setBounds(688, 122, 148, 36);
		profP.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
		profP.setColumns(10);
		frame.getContentPane().add(profP);
		
		JLabel lblProfession = new JLabel("Profession: ");
		lblProfession.setBounds(541, 117, 84, 36);
		lblProfession.setForeground(SystemColor.windowBorder);
		lblProfession.setFont(new Font("Agency FB", Font.ITALIC, 22));
		frame.getContentPane().add(lblProfession);
		
		JComboBox<Object> comboBoxSit = new JComboBox<>();
		comboBoxSit.setFont(new Font("Agency FB", Font.ITALIC, 18));

		// Add options to the JComboBox directly
		comboBoxSit.addItem("Célibataire");
		comboBoxSit.addItem("Marié(e)");
		comboBoxSit.addItem("Divorcé(e)");
		comboBoxSit.addItem("Veuf/Veuve");
		comboBoxSit.setBounds(688, 196, 148, 36);
		frame.getContentPane().add(comboBoxSit);
		
		
		JLabel lblSituationFamiliale_1 = new JLabel("Situation familiale : ");
		lblSituationFamiliale_1.setForeground(SystemColor.windowBorder);
		lblSituationFamiliale_1.setFont(new Font("Agency FB", Font.ITALIC, 22));
		lblSituationFamiliale_1.setBounds(541, 196, 131, 36);
		frame.getContentPane().add(lblSituationFamiliale_1);
		JComboBox<Object> comboBoxBloodGroup = new JComboBox<>();
		comboBoxBloodGroup.setFont(new Font("Agency FB", Font.ITALIC, 18));

		// Add blood group options to the JComboBox
		comboBoxBloodGroup.addItem("A+");
		comboBoxBloodGroup.addItem("A-");
		comboBoxBloodGroup.addItem("B+");
		comboBoxBloodGroup.addItem("B-");
		comboBoxBloodGroup.addItem("AB+");
		comboBoxBloodGroup.addItem("AB-");
		comboBoxBloodGroup.addItem("O+");
		comboBoxBloodGroup.addItem("O-");

		// Set bounds and add the JComboBox to the frame content pane
		comboBoxBloodGroup.setBounds(688, 285, 148, 36);  // Adjust X and Y coordinates as needed
		frame.getContentPane().add(comboBoxBloodGroup);

		// Create a label for "Groupe Sanguin"
		JLabel lblGroupeSanguin = new JLabel("Groupe Sanguin :");
		lblGroupeSanguin.setForeground(SystemColor.windowBorder);
		lblGroupeSanguin.setFont(new Font("Agency FB", Font.ITALIC, 22));
		lblGroupeSanguin.setBounds(541, 285, 131, 36);  // Adjust X and Y coordinates as needed
		frame.getContentPane().add(lblGroupeSanguin);
		JLabel lblDateDeNaissance = new JLabel("Date de Naissance :");
        lblDateDeNaissance.setForeground(SystemColor.windowBorder);
        lblDateDeNaissance.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblDateDeNaissance.setBounds(541, 39, 153, 36);
        frame.getContentPane().add(lblDateDeNaissance);

        JLabel lblYear = new JLabel("Année :");
        lblYear.setForeground(SystemColor.windowBorder);
        lblYear.setFont(new Font("Agency FB", Font.ITALIC, 18));
        lblYear.setBounds(195, 65, 0, 0);
        frame.getContentPane().add(lblYear);

        JLabel lblMonth = new JLabel("Mois :");
        lblMonth.setForeground(SystemColor.windowBorder);
        lblMonth.setFont(new Font("Agency FB", Font.ITALIC, 18));
        lblMonth.setBounds(280, 65, 0, 0);
        frame.getContentPane().add(lblMonth);

        JLabel lblDay = new JLabel("Jour :");
        lblDay.setForeground(SystemColor.windowBorder);
        lblDay.setFont(new Font("Agency FB", Font.ITALIC, 18));
        lblDay.setBounds(365, 65, 0, 0);
        frame.getContentPane().add(lblDay);

        jour = new JTextField();
        jour.setBounds(704, 42, 40, 30);
        jour.setFont(new Font("Agency FB", Font.BOLD, 18));
        frame.getContentPane().add(jour);
        jour.setColumns(10);

        mois = new JTextField();
        mois.setBounds(777, 44, 40, 30);
        mois.setFont(new Font("Agency FB", Font.BOLD, 18));
        frame.getContentPane().add(mois);
        mois.setColumns(10);

        anne = new JTextField();
        anne.setBounds(872, 44, 80, 30);
        anne.setFont(new Font("Agency FB", Font.BOLD, 18));
        frame.getContentPane().add(anne);
        anne.setColumns(10);
        
        JLabel lblJj = new JLabel("JJ");
        lblJj.setForeground(SystemColor.windowBorder);
        lblJj.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblJj.setBounds(683, 39, 84, 36);
        frame.getContentPane().add(lblJj);
        
        JLabel lblMm = new JLabel(" MM");
        lblMm.setForeground(SystemColor.windowBorder);
        lblMm.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblMm.setBounds(741, 39, 84, 36);
        frame.getContentPane().add(lblMm);
        
        JLabel lblAaaa = new JLabel("AAAA");
        lblAaaa.setForeground(SystemColor.windowBorder);
        lblAaaa.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblAaaa.setBounds(827, 39, 84, 36);
        frame.getContentPane().add(lblAaaa);
       
        JButton btnNewButton = new JButton("Rendre un rdv");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String datp = jour.getText() + "/" + mois.getText() + "/" + anne.getText();
                String nom = nomP.getText();
                String prenom = prenomP.getText();
                String day = jour.getText();
                String year = anne.getText();
                String bloodGroup = (String) comboBoxBloodGroup.getSelectedItem();

                // Extracting parts for idpat
                String firstLetterNom = nom.length() > 0 ? String.valueOf(nom.charAt(0)) : "";
                String firstLetterPrenom = prenom.length() > 0 ? String.valueOf(prenom.charAt(0)) : "";
                String lastTwoDigitsYear = year.length() >= 2 ? year.substring(year.length() - 2) : "";

                // Constructing idpat
                String idpat = firstLetterNom + firstLetterPrenom + day + lastTwoDigitsYear + bloodGroup;
                String address = adrP.getText();
                String telephone = tlphP.getText();
                String email = emailP.getText();
                String assurance = assrP.getText();
                String profession = profP.getText();
                String situation = (String) comboBoxSit.getSelectedItem();
                
                

                String sql = "INSERT INTO Patient (ID_Patient, Nom, Prenom, Date_nais, Adresse, Telephone, Groupe_Sang, email, N_Assurance, Situation_famille, profession) VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?)";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, idpat);
                    preparedStatement.setString(2, nom);
                    preparedStatement.setString(3, prenom);
                    preparedStatement.setString(4, datp);  // Assuming date is already in DD/MM/YYYY format
                    preparedStatement.setString(5, address);
                    preparedStatement.setString(6, telephone);
                    preparedStatement.setString(7, bloodGroup);
                    preparedStatement.setString(8, email);
                    preparedStatement.setString(9, assurance);
                    preparedStatement.setString(10, situation);
                    preparedStatement.setString(11, profession);
                    preparedStatement.execute();
                    System.out.println("Patient information inserted successfully!");
                    preparedStatement.close();
                    Rdv rdv = new Rdv(idpat);
	                  rdv.showFrame() ;  
                } catch (SQLException e1) {
                    System.out.println("Error inserting patient information: " + e1.getMessage());
                     
                }
                frame.setVisible(false); 
            }
        });
               


           

        btnNewButton.setBackground(SystemColor.activeCaption);
        btnNewButton.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnNewButton.setBounds(741, 492, 184, 43);
        frame.getContentPane().add(btnNewButton);
        
        frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{comboBoxSit, lblSituationFamiliale_1, lblJj, lblMm, lblAaaa, btnNewButton}));
        
        frame.setBounds(100, 100, 978, 609);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	private void createPatientTable() throws SQLException {
	    String createTableSQL = "CREATE TABLE Patient (" +
	            "ID_Patient varchar2(8) PRIMARY KEY, " +  // Adjust data type based on your database system
	            "Nom VARCHAR2(50), " +
	            "Prenom VARCHAR2(50), " +
	            "Date_nais DATE, " +
	            "Adresse VARCHAR2(200), " +
	            "Telephone VARCHAR2(50), " +
	            "Groupe_Sang VARCHAR2(50), " +
	            "email VARCHAR2(50), " +
	            "N_Assurance VARCHAR2(50), " +
	            "Situation_famille VARCHAR2(50), " +
	            "profession VARCHAR2(50))";

	    Statement statement = connection.createStatement();

	    try {
	        statement.execute(createTableSQL);

	    } catch (SQLException e) {
	        // Handle potential errors
	        if (e.getErrorCode() == 955) {
	            
	        } else {
	            throw e;
	        }
	    } finally {
	        statement.close();
	    }
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
