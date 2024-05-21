import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Certificat {
	   private JFrame frame;
	    private Connection connection;
	    private String idConsult;
	    private JLabel lblNomValue;
	    private JLabel lblPrenomValue;
	    private JLabel lblDateTime;
	    private JTextArea Observ;
	    private JTextField textField ; 
	    private JButton btnDossier;
	    private JButton btnNewButton;
	    public static void main(String[] args) {
	        String idConsult = "id"; // Replace with the actual ID_consult
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    Certificat window = new Certificat(idConsult);
	                    window.frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }

	    public Certificat(String idConsult) {
	        this.idConsult = idConsult;
	        initialize();
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
	            fetchPatientDetails();
	            
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    }

	    private void initialize() {
	        frame = new JFrame();
	        frame.setBounds(100, 100, 765, 795);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);

	        lblDateTime = new JLabel();
	        lblDateTime.setFont(new Font("Agency FB", Font.ITALIC, 16));
	        lblDateTime.setBounds(10, 10, 200, 20);
	        frame.getContentPane().add(lblDateTime);
	        displayDateTime();

	        JLabel lblNom = new JLabel("Nom :");
	        lblNom.setFont(new Font("Agency FB", Font.ITALIC, 18));
	        lblNom.setBounds(10, 57, 232, 20);
	        frame.getContentPane().add(lblNom);

	        lblNomValue = new JLabel("");
	        lblNomValue.setFont(new Font("Agency FB", Font.ITALIC, 18));
	        lblNomValue.setBounds(58, 57, 142, 20);
	        frame.getContentPane().add(lblNomValue);

	        JLabel lblPrenom = new JLabel("Prenom :");
	        lblPrenom.setFont(new Font("Agency FB", Font.ITALIC, 18));
	        lblPrenom.setBounds(229, 57, 219, 20);
	        frame.getContentPane().add(lblPrenom);

	        lblPrenomValue = new JLabel("");
	        lblPrenomValue.setFont(new Font("Agency FB", Font.ITALIC, 18));
	        lblPrenomValue.setBounds(283, 57, 142, 20);
	        frame.getContentPane().add(lblPrenomValue);

	        JLabel lblAge = new JLabel("Age :");
	        lblAge.setFont(new Font("Agency FB", Font.ITALIC, 17));
	        lblAge.setBounds(520, 57, 159, 20);
	        frame.getContentPane().add(lblAge);

	        Observ = new JTextArea();
	        Observ.setFont(new Font("Agency FB", Font.ITALIC, 18));
	        Observ.setBounds(10, 118, 720, 447);
	        frame.getContentPane().add(Observ);

	        JLabel lblOrdonnance = new JLabel("Certificat");
	        lblOrdonnance.setFont(new Font("Agency FB", Font.ITALIC, 24));
	        lblOrdonnance.setBounds(318, 10, 159, 20);
	        frame.getContentPane().add(lblOrdonnance);
	        
	        textField = new JTextField();
	        textField.setFont(new Font("Agency FB", Font.ITALIC, 18));
	        textField.setBounds(562, 60, 117, 27);
	        frame.getContentPane().add(textField);
	        textField.setColumns(10);
	        
	        btnNewButton = new JButton("Dossier");
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		frame.dispose();
	        		Dossier dos = new Dossier(idConsult);
	        		dos.showFrame();
	        	}
	        });
	        btnNewButton.setFont(new Font("Agency FB", Font.PLAIN, 18));
	        btnNewButton.setBackground(SystemColor.textHighlight);
	        btnNewButton.setBounds(590, 576, 129, 36);
	        frame.getContentPane().add(btnNewButton);
	        
	}
	    private void fetchPatientDetails() throws SQLException {
	        String query = "SELECT p.Nom, p.Prenom, p.Date_nais FROM consultation c JOIN patient p ON c.ID_Patient = p.ID_Patient WHERE c.ID_consult = '" + idConsult + "'";
	        try (Statement statement = connection.createStatement()) {
	            ResultSet resultSet = statement.executeQuery(query);
	            if (resultSet.next()) {
	                String nom = resultSet.getString("Nom");
	                String prenom = resultSet.getString("Prenom");
	                lblNomValue.setText(nom);
	                lblPrenomValue.setText(prenom);

	               
	                
	            }
	        }
	    }
	private void displayDateTime() {
        // Display current date and time in the top-left corner of the frame
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = now.format(formatter);
        lblDateTime.setText(dateTime);
    }
	public void showFrame() {
        frame.setVisible(true);
        displayDateTime();
    }
}
