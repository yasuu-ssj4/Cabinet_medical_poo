
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ordonnance {
    private JFrame frame;
    private Connection connection;
    private String idConsult;
    private JLabel lblNomValue;
    private JLabel lblPrenomValue;
    private JLabel lblDateTime;
    private JTextArea Observ;
    private JTextField textField;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private int rowCount = 0;
    private JButton certificat;
    private JButton btnDossier;
    public static void main(String[] args) {
        String idConsult = "id"; // Replace with the actual ID_consult
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ordonnance window = new Ordonnance(idConsult);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ordonnance(String idConsult) {
        this.idConsult = idConsult;
        initialize();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
            fetchPatientDetails();
            createOrdonnanceligneTable();
            createOrdonnanceligneTable();
            insertOrdonnanceData();
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

        JLabel lblObservation = new JLabel("Observation : ");
        lblObservation.setFont(new Font("Agency FB", Font.ITALIC, 18));
        lblObservation.setBounds(10, 88, 159, 20);
        frame.getContentPane().add(lblObservation);

        Observ = new JTextArea();
        Observ.setFont(new Font("Agency FB", Font.ITALIC, 18));
        Observ.setBounds(10, 118, 720, 100);
        frame.getContentPane().add(Observ);

        JLabel lblOrdonnance = new JLabel("Ordonnance");
        lblOrdonnance.setFont(new Font("Agency FB", Font.ITALIC, 24));
        lblOrdonnance.setBounds(283, 10, 159, 20);
        frame.getContentPane().add(lblOrdonnance);
        
        textField = new JTextField();
        textField.setFont(new Font("Agency FB", Font.ITALIC, 18));
        textField.setBounds(562, 60, 117, 27);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("Ajouter une ligne");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRowToTable();
            }
        });
        btnNewButton.setBackground(SystemColor.textHighlight);
        btnNewButton.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnNewButton.setBounds(10, 229, 151, 32);
        frame.getContentPane().add(btnNewButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nom Médicament");
        tableModel.addColumn("Dose");
        tableModel.addColumn("Nombre de Fois");
        tableModel.addColumn("Quantité");
        tableModel.addColumn("Durée de Traitement");
        table = new JTable(tableModel);
        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        table.setBackground(SystemColor.inactiveCaption);
        table.setFont(new Font("Agency FB", Font.ITALIC, 16));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 259, 720, 338);
        frame.getContentPane().add(scrollPane);
        
        certificat = new JButton("Certificat");
        certificat.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					insertLigneOrdonnanceData();
					frame.setVisible(false);
					Certificat Cert = new Certificat(idConsult);
					Cert.showFrame(); 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
        	}
        });
        certificat.setFont(new Font("Agency FB", Font.ITALIC, 18));
        certificat.setBackground(SystemColor.textHighlight);
        certificat.setBounds(297, 229, 151, 32);
        frame.getContentPane().add(certificat);
        
        btnDossier = new JButton("Dossier");
        btnDossier.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.setVisible(false);
        		Dossier dos = new Dossier(idConsult);
        		dos.showFrame();
        	}
        });
        btnDossier.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnDossier.setBackground(SystemColor.textHighlight);
        btnDossier.setBounds(579, 229, 151, 32);
        frame.getContentPane().add(btnDossier);
    }

    private void addRowToTable() {
        // Add a new row to the table with blank fields for medication info
    	 Object[] rowData = new Object[]{"", "", "", "", ""}; // Add button text directly
    	    tableModel.addRow(rowData);
    	    rowCount++;
    }
    private void saveMedicationInfo(Object[] rowData) {
        // Extract medication information from the row data
        String nomMedic = (String) rowData[0];
        String Dose = (String) rowData[1];
        String nbrFois = (String) rowData[2];
        String quantite = (String) rowData[3];
        String dureeTraitement = (String) rowData[4];
        	
        // Implement saving to the database here
        // You can use the medication information variables
        // Then use JDBC to insert the data into the database
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

    private void createOrdonnanceligneTable() throws SQLException {
        String createTableSQL = "CREATE TABLE ligne_ordonance (" +
                "ID_LigneOrd NUMBER(8)," +
        		"nomMedic varchar2(30),"+
                "ID_ord Number(8)," +
                "Dose VARCHAR2(50)," +
                "nbr_fois NUMBER(4)," +
                "Quantite NUMBER(4)," +
                "Durree_de_traitement VARCHAR2(50)," +
                "CONSTRAINT FK_ligne_ordonance_ordonance FOREIGN KEY (ID_ord) REFERENCES ordonnance(ID_ord) ON DELETE SET NULL," +
                "CONSTRAINT PK_ligne_ordonance PRIMARY KEY (ID_ord, ID_LigneOrd))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("created");
        } catch (SQLException e) {
            // Handle potential errors
            if (e.getErrorCode() == 955) {
                // Table already exists, do nothing or handle accordingly
            } else {
                throw e;
            }
        }
    }

    private void createOrdonnanceTable() throws SQLException {
        
        String createTableSQL = "CREATE TABLE ordonnance (" +
                "ID_ord NUMBER(8) UNIQUE," +
                "ID_consult VARCHAR2(8)," +
                "date_Ordonance DATE," +
                "heure VARCHAR2(15)," +
                "observation VARCHAR2(250)," +
                "CONSTRAINT FK_ordonance_consult FOREIGN KEY (ID_consult) REFERENCES consultation(ID_consult) ON DELETE SET NULL," +
                "CONSTRAINT PK_ordonance PRIMARY KEY (ID_ord,ID_consult))";

            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableSQL);
                
            } catch (SQLException e) {
                // Handle potential errors
                if (e.getErrorCode() == 955) {
                   
                } else {
                    throw e;
                }
            }
        }

    private void insertOrdonnanceData() throws SQLException {
        // Get current date and time
        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = currentDate.format(dateFormatter);
        String time = currentTime.format(timeFormatter);
        String id_ord = generateRandom8DigitNumber();
        // Get observation from the JTextArea
        String observation = Observ.getText().trim();
        String insertSQL = "INSERT INTO ordonnance (ID_ord, ID_consult, date_Ordonance, heure, observation) " +
                "VALUES ('" + id_ord + "', '" + idConsult + "', TO_DATE('" + date + "', 'YYYY-MM-DD'), '" + time + "', '" + observation + "')";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertSQL);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



        public static String generateRandom8DigitNumber() {
            Random random = new Random();
            // Generate a random number between 0 and 99999999 (inclusive)
            int randomNumber = random.nextInt(100000000); // Max value is exclusive, so it will generate numbers from 0 to 99999999

            // Format the random number to ensure it always has 8 digits
            String formattedNumber = String.format("%08d", randomNumber); // Pad with leading zeros if necessary

            return formattedNumber;
        }

        public void showFrame() {
            frame.setVisible(true);
            displayDateTime();
        }
        private void insertLigneOrdonnanceData() throws SQLException {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                
            	String idLigneOrd = generateRandom8DigitNumber() ; 
                String idOrd = getCurrentOrdId(); // Retrieve the current ordonnance ID
                String nomMedic = (String) tableModel.getValueAt(i, 0); 
                String dose = (String) tableModel.getValueAt(i, 1);
                String nbrFois = (String) tableModel.getValueAt(i, 2);
                String quantite = (String) tableModel.getValueAt(i, 3);
                String dureeTraitement = (String) tableModel.getValueAt(i, 4);

                String insertSQL = "INSERT INTO ligne_ordonance (ID_LigneOrd,nomMedic, ID_ord, Dose, nbr_fois, Quantite, Durree_de_traitement) " +
                        "VALUES (" + idLigneOrd + ",'"+ nomMedic + "', " + idOrd + ", '" + dose + "', " + nbrFois + ", " + quantite + ", '" + dureeTraitement + "')";

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(insertSQL);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private String getCurrentOrdId() throws SQLException {
            String idOrd = null;
            String query = "SELECT ID_ord FROM (" +
                           "SELECT ID_ord FROM ordonnance WHERE ID_consult = '" + idConsult + "' ORDER BY date_Ordonance DESC, heure DESC" +
                           ") WHERE ROWNUM = 1";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    idOrd = resultSet.getString("ID_ord");
                }
            }
            return idOrd;
        }

        private void displayDateTime() {
            // Display current date and time in the top-left corner of the frame
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = now.format(formatter);
            lblDateTime.setText(dateTime);
        }
    }

