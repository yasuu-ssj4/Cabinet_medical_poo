import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Rdv {
	private String id_pat ; 
    private JFrame frame;
    private Connection connection ;
    private static Statement statement ;
    private JTextField textField;
  
    public static void main(String[] args) {
        String id_pat = "some_patient_id"; // Replace with actual patient ID retrieval logic
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Rdv window = new Rdv(id_pat);
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
    public Rdv(String id_pat) {
        this.id_pat = id_pat; // Store the id_pat in an instance variable
        initialize();
        
        try { 
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            createrdvTable();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } 
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.menu);
        frame.getContentPane().setLayout(null);

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

        JTextField jour = new JTextField();
        jour.setBounds(139, 79, 40, 30);
        jour.setFont(new Font("Agency FB", Font.BOLD, 18));
        frame.getContentPane().add(jour);
        jour.setColumns(10);

        JTextField mois = new JTextField();
        mois.setBounds(212, 81, 40, 30);
        mois.setFont(new Font("Agency FB", Font.BOLD, 18));
        frame.getContentPane().add(mois);
        mois.setColumns(10);

        JTextField anne = new JTextField();
        anne.setBounds(307, 81, 80, 30);
        anne.setFont(new Font("Agency FB", Font.BOLD, 18));
        frame.getContentPane().add(anne);
        anne.setColumns(10);

        JLabel lblJj = new JLabel("JJ");
        lblJj.setForeground(SystemColor.windowBorder);
        lblJj.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblJj.setBounds(118, 76, 84, 36);
        frame.getContentPane().add(lblJj);

        JLabel lblMm = new JLabel("MM");
        lblMm.setForeground(SystemColor.windowBorder);
        lblMm.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblMm.setBounds(179, 76, 84, 36);
        frame.getContentPane().add(lblMm);

        JLabel lblAaaa = new JLabel("AAAA");
        lblAaaa.setForeground(SystemColor.windowBorder);
        lblAaaa.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblAaaa.setBounds(262, 76, 84, 36);
        frame.getContentPane().add(lblAaaa);

        JLabel lblEntrerLeDate = new JLabel("Date de RDV");
        lblEntrerLeDate.setForeground(SystemColor.windowBorder);
        lblEntrerLeDate.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblEntrerLeDate.setBounds(228, 45, 159, 36);
        frame.getContentPane().add(lblEntrerLeDate);

        JLabel lblALheur = new JLabel("à l'heure :");
        lblALheur.setForeground(SystemColor.windowBorder);
        lblALheur.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblALheur.setBounds(222, 123, 159, 36);
        frame.getContentPane().add(lblALheur);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Agency FB", Font.ITALIC, 22));
        comboBox.setBounds(212, 170, 111, 30);
        frame.getContentPane().add(comboBox);
        comboBox.addItem("08:00");  
        comboBox.addItem("09:00");
        comboBox.addItem("10:00");
        comboBox.addItem("11:00");
        comboBox.addItem("12:00");
        comboBox.addItem("13:00");
        comboBox.addItem("14:00");
        comboBox.addItem("15:00");
        JLabel lblObservation = new JLabel("Observation : ");
        lblObservation.setForeground(SystemColor.windowBorder);
        lblObservation.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblObservation.setBounds(63, 234, 159, 36);
        frame.getContentPane().add(lblObservation);
        String heure = (String) comboBox.getSelectedItem();
         JButton btnNewButton = new JButton("Valider");
         
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  String observ = textField.getText();
                  String datr = jour.getText() + "/" + mois.getText() + "/" + anne.getText();
                  String id_rdv = generateIDRdv(jour.getText(), mois.getText(), anne.getText(), heure);
                  String sql = "INSERT INTO RDV (id_patient, id_rdv, date_rdv, heure, observation) VALUES (?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?)";

                  try {
                      PreparedStatement preparedStatement = connection.prepareStatement(sql);
                      preparedStatement.setString(1, id_pat);
                      preparedStatement.setString(2, id_rdv);
                      preparedStatement.setString(3, datr);
                      preparedStatement.setString(4, heure);
                      preparedStatement.setString(5, observ);

                      preparedStatement.execute();
                      System.out.println("RDV bien ajouté");
                      preparedStatement.close();
                  } catch (SQLException e1) {
                      System.out.println("Error inserting RDV information: " + e1.getMessage());
                  }
                  frame.setVisible(false);
                  AccueilP.main(null);
                  
              }
          });
        btnNewButton.setBackground(SystemColor.textHighlight);
        btnNewButton.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
        btnNewButton.setBounds(434, 268, 134, 41);
        frame.getContentPane().add(btnNewButton);
        
        textField = new JTextField();
        textField.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 15));
        textField.setBounds(179, 239, 222, 36);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        
        
        
       
        
        
        frame.setBounds(100, 100, 594, 373);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    private String generateIDRdv(String jour, String mois, String anne, String heure) {
        // Extract the last two digits of the year
        String yearLastTwoDigits = anne.substring(anne.length() - 2);

       
        String hourOnly = heure.split(":")[0];
        Random random = new Random();
        int randomchfr = random.nextInt(90) + 10; // Ensures the number is between 10 and 99

        // Combine the parts to form the ID
        return jour + mois + yearLastTwoDigits + hourOnly + randomchfr;
    }
    private void createrdvTable() throws SQLException {
	    String createTableSQL = "CREATE TABLE RDV (" +
	    			    "ID_RDV varchar2 (10),"+
	    			    "ID_Patient varchar(8),"+
	    			    "date_RDV date,"+
	    			    "heure  VARCHAR2(15),"+
	    			    "observation VARCHAR2(50),"+
	    			    "CONSTRAINT FK_RDV_Patient FOREIGN KEY (ID_Patient) REFERENCES patient(ID_Patient) ON DELETE set NULL,"+
	    			    "CONSTRAINT PK_RDV PRIMARY KEY (ID_RDV,ID_Patient))" ;
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


	public void showFrame() {
		frame.setVisible(true); 
		
	}
}
