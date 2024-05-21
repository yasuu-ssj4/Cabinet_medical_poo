import java.awt.EventQueue;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Consultation {
    private Connection connection;
    private static Statement statement;
    private String id_rdv;
    private String id_patient; // Variable to store ID_Patient
    private JFrame frame;
    private String idConsult;
    private JTextField Mont;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Consultation window = new Consultation("id");
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
    public Consultation(String id_rdv) {
        this.id_rdv = id_rdv;
        initialize();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
            // Create the consultation table
            createTableCons();
            // Fetch ID_Patient using the provided ID_RDV
            fetchIDPatient();
            System.out.println("ID_Patient: " + id_patient);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.inactiveCaption);
        frame.setBounds(100, 100, 913, 647);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblConsultation = new JLabel("Consultation : ");
        lblConsultation.setForeground(SystemColor.windowBorder);
        lblConsultation.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblConsultation.setBounds(377, 11, 159, 36);
        frame.getContentPane().add(lblConsultation);
        
        JLabel lblDiagnostique = new JLabel("Diagnostique : ");
        lblDiagnostique.setForeground(SystemColor.windowBorder);
        lblDiagnostique.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblDiagnostique.setBounds(27, 80, 159, 36);
        frame.getContentPane().add(lblDiagnostique);
        
        JTextArea Diag = new JTextArea();
        Diag.setFont(new Font("Agency FB", Font.ITALIC, 18));
        Diag.setBounds(141, 91, 679, 117);
        frame.getContentPane().add(Diag);
        
        JLabel lblObservation = new JLabel("Observation : ");
        lblObservation.setForeground(SystemColor.windowBorder);
        lblObservation.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblObservation.setBounds(27, 237, 159, 36);
        frame.getContentPane().add(lblObservation);
        
        JTextArea Observ = new JTextArea();
        Observ.setFont(new Font("Agency FB", Font.ITALIC, 18));
        Observ.setBounds(141, 248, 679, 117);
        frame.getContentPane().add(Observ);
        
        JTextArea Resu = new JTextArea();
        Resu.setFont(new Font("Agency FB", Font.ITALIC, 18));
        Resu.setBounds(141, 400, 679, 117);
        frame.getContentPane().add(Resu);
        
        JLabel lblResultat = new JLabel("Resultat : ");
        lblResultat.setForeground(SystemColor.windowBorder);
        lblResultat.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblResultat.setBounds(27, 389, 159, 36);
        frame.getContentPane().add(lblResultat);
        
        JLabel lblMontant = new JLabel("Montant : ");
        lblMontant.setForeground(SystemColor.windowBorder);
        lblMontant.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblMontant.setBounds(10, 548, 159, 36);
        frame.getContentPane().add(lblMontant);
        
        Mont = new JTextField();
        Mont.setFont(new Font("Agency FB", Font.ITALIC, 16));
        Mont.setBounds(96, 553, 125, 36);
        frame.getContentPane().add(Mont);
        Mont.setColumns(10);
        
        JButton btnNewButton = new JButton("Ordonnance");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    idConsult = generateRandom8DigitNumber(); // Set the value of idConsult
                    String diagnostique = Diag.getText().trim();
                    String observation = Observ.getText().trim();
                    String resultat = Resu.getText().trim();
                    String montantConsult = Mont.getText().trim();

                    insertConsultation(idConsult, diagnostique, observation, resultat, montantConsult);
                    System.out.println("idConsult in Consultation: " + idConsult); // Add this line to check idConsult value
                    Ordonnance ordon = new Ordonnance(idConsult);
                    System.out.println("idConsult passed to Ordonnance: " + idConsult); // Add this line to check idConsult value after passing
                    ordon.showFrame();
                    frame.setVisible(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnNewButton.setBackground(SystemColor.textHighlight);
        btnNewButton.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnNewButton.setBounds(721, 536, 134, 36);
        frame.getContentPane().add(btnNewButton);
    }

    public static String generateRandom8DigitNumber() {
        Random random = new Random();
        
        // Generate a random number between 0 and 99999999 (inclusive)
        int randomNumber = random.nextInt(100000000); // Max value is exclusive, so it will generate numbers from 0 to 99999999
        
        // Format the random number to ensure it always has 8 digits
        String formattedNumber = String.format("%08d", randomNumber); // Pad with leading zeros if necessary
        
        return formattedNumber;
    }

    /**
     * Insert a new consultation.
     */
    private void insertConsultation(String idConsult, String diagnostique, String observation, String resultat, String montantConsult) throws SQLException {
        String insertSQL = "INSERT INTO consultation (ID_consult, ID_Patient, date_consult, diagnostique, observation, resultat, montant_consult) " +
                "VALUES ('" + idConsult + "', '" + id_patient + "', SYSDATE, '" + diagnostique + "', '" + observation + "', '" + resultat + "', '" + montantConsult + "')";
        Statement statement = connection.createStatement();
        try {
            statement.executeUpdate(insertSQL);
            System.out.println("Consultation record inserted successfully with ID_consult: " + idConsult);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    /**
     * Fetch ID_Patient using ID_RDV.
     */
    private void fetchIDPatient() throws SQLException {
        String query = "SELECT ID_Patient FROM RDV WHERE ID_RDV = '" + id_rdv + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            id_patient = resultSet.getString("ID_Patient");
        } else {
            System.out.println("No patient found for ID_RDV: " + id_rdv);
        }
        resultSet.close();
        statement.close();
    }

    /**
     * Create the consultation table.
     */
    private void createTableCons() throws SQLException {
        String createTableSQL = "CREATE TABLE consultation (" +
                "ID_consult VARCHAR2(8) UNIQUE," +
                "ID_Patient VARCHAR2(8)," +
                "date_consult DATE," +
                "diagnostique VARCHAR2(250)," +
                "observation VARCHAR2(250)," +
                "resultat VARCHAR2(250)," +
                "montant_consult VARCHAR2(10)," +
                "CONSTRAINT FK_consult_patient FOREIGN KEY (ID_Patient) REFERENCES patient(ID_Patient) ON DELETE SET NULL," +
                "CONSTRAINT PK_consult PRIMARY KEY (ID_Patient, ID_consult))";

        Statement statement = connection.createStatement();
        try {
            statement.execute(createTableSQL);
            System.out.println("Table 'consultation' created successfully.");
        } catch (SQLException e) {
            // Handle potential errors
            if (e.getErrorCode() == 955) {
                System.out.println("Table 'consultation' already exists.");
            } else {
                throw e;
            }
        } finally {
            statement.close();
        }
    }

    public void setVisible() {
        frame.setVisible(true);
    }
}
