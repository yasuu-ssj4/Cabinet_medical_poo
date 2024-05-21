import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dossier {

    private JFrame frame;
    private Connection connection;
    private String idConsult;
    private JTextArea textArea;

    public static void main(String[] args) {
        String idConsult = "id"; // Remplacez par l'ID_consult réel
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dossier window = new Dossier(idConsult);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Dossier(String idConsult) {
        this.idConsult = idConsult;
        initialize();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
            createDossierTable();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.menu);
        frame.setBounds(100, 100, 792, 452);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Dossier");
        lblNewLabel.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 24));
        lblNewLabel.setBounds(316, 11, 88, 29);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblRsum = new JLabel("Note:");
        lblRsum.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblRsum.setBounds(31, 58, 88, 29);
        frame.getContentPane().add(lblRsum);

        textArea = new JTextArea();
        textArea.setFont(new Font("Agency FB", Font.ITALIC, 20));
        textArea.setBounds(41, 98, 665, 266);
        frame.getContentPane().add(textArea);

        JButton btnSave = new JButton("Sauvergarder");
        btnSave.setBackground(SystemColor.textHighlight);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    insertDossierData();
                    AccueilP.main(null) ; 
                    frame.setVisible(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnSave.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnSave.setBounds(650, 375, 120, 30);
        frame.getContentPane().add(btnSave);
    }

    private void createDossierTable() throws SQLException {
        String createTableSQL = "CREATE TABLE DOSSIER_patient (" +
                "ID_DOSS varchar2(8), " +
                "ID_Patient varchar2(8), " +
                "Note VARCHAR2(500), " +
                "CONSTRAINT FK_patient FOREIGN KEY (ID_Patient) REFERENCES patient(ID_Patient) ON DELETE SET NULL," +
                "CONSTRAINT PK_DOSSIER_patient PRIMARY KEY (ID_DOSS))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table DOSSIER_patient created successfully.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 955) {
                // Table already exists, do nothing or handle accordingly
                System.out.println("Table DOSSIER_patient already exists.");
            } else {
                throw e;
            }
        }
    }

    private void insertDossierData() throws SQLException {
        String note = textArea.getText().trim();
        String idDoss = idConsult ; 
        String idPatient = getPatientIdFromConsultId(idConsult);

        String insertSQL = "INSERT INTO DOSSIER_patient (ID_DOSS, ID_Patient, Note) " +
                "VALUES ('" + idDoss + "', '" +idPatient+  "', '" + note + "')";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertSQL);
            System.out.println("Data inserted into DOSSIER_patient successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getPatientIdFromConsultId(String idConsult) throws SQLException {
        String idPatient = null;
        String query = "SELECT ID_Patient FROM consultation WHERE ID_consult = '" + idConsult + "'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                idPatient = resultSet.getString("ID_Patient");
            }
        }
        return idPatient;
    }

    private static String generateRandom8DigitNumber() {
        int randomNumber = (int) (Math.random() * 100000000);
        return String.format("%08d", randomNumber);
    }
    public void showFrame() {
        frame.setVisible(true);
    }
}
