import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import javax.swing.ImageIcon;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.UIManager;

public class rdvCherche {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField annee;
    private JLabel lblAaaa;
    private JTextField mois;
    private JLabel lblMm;
    private JTextField jour;
    private JLabel lblJj;
    private JLabel lblDateDeNaissance;
    private JLabel lblNewLabel;
    private JTextField searchNomField;
    private JLabel lblPrenom;
    private JTextField searchPrenomField;
    private JLabel lblGroupeSanguin;
    private JComboBox<Object> comboBoxBloodGroup;
    private JButton Home;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    rdvCherche window = new rdvCherche();
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
    public rdvCherche() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
       

        JLabel titleLabel = new JLabel("Patient RDV Details");
        titleLabel.setFont(new Font("Agency FB", Font.ITALIC, 17));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, 784, 30);
        frame.getContentPane().add(titleLabel);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(UIManager.getColor("textHighlight"));
        searchButton.setFont(new Font("Agency FB", Font.PLAIN, 17));
        searchButton.setBounds(617, 85, 138, 36);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchPatientRdvs();
            }
        });
        frame.getContentPane().add(searchButton);

        List<PatientRdv> data = DataFetcher.fetchPatientRdvs();
        Collections.sort(data, Comparator.comparing(PatientRdv::getDateRdv));
        String[] columnNames = {"Nom", "Prenom", "Date de Naissance", "Adresse", "Groupe Sanguin", "Date de RDV", "Heure", "ID_RDV", "Action"};
        model = new DefaultTableModel(columnNames, 0);

        for (PatientRdv pr : data) {
            model.addRow(new Object[]{
                    pr.getNom(),
                    pr.getPrenom(),
                    pr.getDateNais(),
                    pr.getAdresse(),
                    pr.getGroupeSang(),
                    pr.getDateRdv(),
                    pr.getHeure(),
                    pr.getIdRdv(),
                    "Open"
            });
        }

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only the "Action" column is editable
            }
        };

        table.setFont(new Font("Agency FB", Font.PLAIN, 15));
        table.setRowHeight(30);
        table.setBackground(new Color(173, 216, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Agency FB", Font.BOLD, 17));
        header.setBackground(new Color(100, 149, 237));

        Action action= new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.valueOf(e.getActionCommand());
                
                
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, action,8,7);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 140, 764, 430);
        frame.getContentPane().add(scrollPane);
        
        annee = new JTextField();
        annee.setFont(new Font("Agency FB", Font.BOLD, 18));
        annee.setColumns(10);
        annee.setBounds(460, 81, 80, 30);
        frame.getContentPane().add(annee);
        
        lblAaaa = new JLabel("AAAA");
        lblAaaa.setForeground(SystemColor.windowBorder);
        lblAaaa.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblAaaa.setBounds(415, 76, 84, 36);
        frame.getContentPane().add(lblAaaa);
        
        mois = new JTextField();
        mois.setFont(new Font("Agency FB", Font.BOLD, 18));
        mois.setColumns(10);
        mois.setBounds(365, 81, 40, 30);
        frame.getContentPane().add(mois);
        
        lblMm = new JLabel("MM");
        lblMm.setForeground(SystemColor.windowBorder);
        lblMm.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblMm.setBounds(332, 76, 84, 36);
        frame.getContentPane().add(lblMm);
        
        jour = new JTextField();
        jour.setFont(new Font("Agency FB", Font.BOLD, 18));
        jour.setColumns(10);
        jour.setBounds(292, 79, 40, 30);
        frame.getContentPane().add(jour);
        
        lblJj = new JLabel("JJ");
        lblJj.setForeground(SystemColor.windowBorder);
        lblJj.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblJj.setBounds(271, 76, 84, 36);
        frame.getContentPane().add(lblJj);
        
        lblDateDeNaissance = new JLabel("Date de Naissance :");
        lblDateDeNaissance.setForeground(SystemColor.windowBorder);
        lblDateDeNaissance.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblDateDeNaissance.setBounds(108, 76, 153, 36);
        frame.getContentPane().add(lblDateDeNaissance);
        
        lblNewLabel = new JLabel("Nom : ");
        lblNewLabel.setForeground(SystemColor.windowBorder);
        lblNewLabel.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblNewLabel.setBounds(22, 29, 84, 36);
        frame.getContentPane().add(lblNewLabel);
        
        searchNomField = new JTextField();
        searchNomField.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
        searchNomField.setColumns(10);
        searchNomField.setBounds(71, 33, 148, 36);
        frame.getContentPane().add(searchNomField);
        
        lblPrenom = new JLabel("Prenom : ");
        lblPrenom.setForeground(SystemColor.windowBorder);
        lblPrenom.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblPrenom.setBounds(229, 29, 84, 36);
        frame.getContentPane().add(lblPrenom);
        
        searchPrenomField = new JTextField();
        searchPrenomField.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
        searchPrenomField.setColumns(10);
        searchPrenomField.setBounds(304, 33, 148, 36);
        frame.getContentPane().add(searchPrenomField);
        
        lblGroupeSanguin = new JLabel("Groupe Sanguin :");
        lblGroupeSanguin.setForeground(SystemColor.windowBorder);
        lblGroupeSanguin.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblGroupeSanguin.setBounds(460, 34, 131, 36);
        frame.getContentPane().add(lblGroupeSanguin);
        
        comboBoxBloodGroup = new JComboBox<Object>();
        comboBoxBloodGroup.setFont(new Font("Agency FB", Font.ITALIC, 18));
        comboBoxBloodGroup.setBounds(607, 34, 148, 36);
        frame.getContentPane().add(comboBoxBloodGroup);
        comboBoxBloodGroup.addItem("A+");
		comboBoxBloodGroup.addItem("A-");
		comboBoxBloodGroup.addItem("B+");
		comboBoxBloodGroup.addItem("B-");
		comboBoxBloodGroup.addItem("AB+");
		comboBoxBloodGroup.addItem("AB-");
		comboBoxBloodGroup.addItem("O+");
		comboBoxBloodGroup.addItem("O-");
		// Inside the initialize method of rdvCherche class

		JButton resetButton = new JButton("Reset ");
		resetButton.setBackground(SystemColor.textHighlight);
		resetButton.setFont(new Font("Agency FB", Font.PLAIN, 17));
		resetButton.setBounds(80, 0, 80, 30);
		resetButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Reset search fields
		        searchNomField.setText("");
		        searchPrenomField.setText("");
		        jour.setText("");
		        mois.setText("");
		        annee.setText("");
		        comboBoxBloodGroup.setSelectedIndex(0);

		        // Reload all patient records
		        List<PatientRdv> data = DataFetcher.fetchPatientRdvs();
		        Collections.sort(data, Comparator.comparing(PatientRdv::getDateRdv));
		        model.setRowCount(0); // Clear the table

		        for (PatientRdv pr : data) {
		            model.addRow(new Object[]{
		                    pr.getNom(),
		                    pr.getPrenom(),
		                    pr.getDateNais(),
		                    pr.getAdresse(),
		                    pr.getGroupeSang(),
		                    pr.getDateRdv(),
		                    pr.getHeure(),
		                    pr.getIdRdv(),
		                    "Open"
		            });
		        }
		    }
		});
		frame.getContentPane().add(resetButton);
		
		Home = new JButton("");
		Home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				AccueilP.main(null);
				
			}
		});
		Home.setForeground(SystemColor.menu);
		Home.setBackground(SystemColor.menu);
		Home.setBounds(0, 0, 80, 30);
		frame.getContentPane().add(Home);
		 ImageIcon icon2 = new ImageIcon("images\\icon.png");
		 Home.setIcon(icon2);

    }
    private void searchPatientRdvs() {
        String nom = searchNomField.getText().trim();
        String prenom = searchPrenomField.getText().trim();
        String jourText = jour.getText().trim();
        String moisText = mois.getText().trim();
        String anneeText = annee.getText().trim();

        String dateNais = "";
        if (!jourText.isEmpty() && !moisText.isEmpty() && !anneeText.isEmpty()) {
            dateNais = anneeText + "-" + moisText + "-" + jourText;
        }

        String groupeSang = (String) comboBoxBloodGroup.getSelectedItem();

        List<PatientRdv> data = fetchDataFromDatabase(nom, prenom, dateNais, groupeSang);
        populateTable(data);
    }


    private List<PatientRdv> fetchDataFromDatabase(String nom, String prenom, String dateNais, String groupeSang) {
        List<PatientRdv> list = new ArrayList<>();

        // Query the Patient table based on the search criteria
        String patientQuery = "SELECT ID_Patient FROM Patient WHERE Nom LIKE '%" + nom + "%' AND Prenom LIKE '%" + prenom + "%' AND Date_nais = TO_DATE('" + dateNais + "', 'YYYY-MM-DD') AND Groupe_Sang LIKE '%" + groupeSang + "%'";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(patientQuery)) {

            while (resultSet.next()) {
                String idPatient = resultSet.getString("ID_Patient");
                // Query the RDV table to fetch appointment details for the found patient
                String rdvQuery = "SELECT * FROM RDV WHERE ID_Patient = '" + idPatient + "'";
                try (Statement rdvStatement = connection.createStatement();
                     ResultSet rdvResultSet = rdvStatement.executeQuery(rdvQuery)) {

                    while (rdvResultSet.next()) {
                        PatientRdv patientRdv = new PatientRdv(
                                idPatient,
                                nom, // You can retrieve name and other common details from the patient table
                                prenom,
                                Date.valueOf(dateNais),
                                "", // You can fill address and other details from the patient table
                                groupeSang,
                                rdvResultSet.getString("ID_RDV"),
                                rdvResultSet.getDate("date_RDV"),
                                rdvResultSet.getString("heure")
                        );
                        list.add(patientRdv);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void populateTable(List<PatientRdv> data) {
        model.setRowCount(0); // Clear the table
        for (PatientRdv pr : data) {
            model.addRow(new Object[]{
                    pr.getNom(),
                    pr.getPrenom(),
                    pr.getDateNais(),
                    pr.getAdresse(),
                    pr.getGroupeSang(),
                    pr.getDateRdv(),
                    pr.getHeure(),
                    pr.getIdRdv(),
                    "Open"
            });
        }
    
}

class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "system";
    private static final String PASSWORD = "halwan";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class DataFetcher {

    public static List<PatientRdv> fetchPatientRdvs() {
        return fetchPatientRdvs("", "", "", "");
    }

    public static List<PatientRdv> fetchPatientRdvs(String nom, String prenom, String dateNais, String groupeSang) {
        List<PatientRdv> list = new ArrayList<>();

        String query = "SELECT p.ID_Patient, p.Nom, p.Prenom, p.Date_nais, p.Adresse, p.Groupe_Sang, r.ID_RDV, r.date_RDV, r.heure " +
                       "FROM Patient p " +
                       "JOIN RDV r ON p.ID_Patient = r.ID_Patient " +
                       "WHERE 1=1";

        if (!nom.isEmpty()) {
            query += " AND p.Nom LIKE '%" + nom + "%'";
        }
        if (!prenom.isEmpty()) {
            query += " AND p.Prenom LIKE '%" + prenom + "%'";
        }
        if (!dateNais.isEmpty()) {
            query += " AND TO_CHAR(p.Date_nais, 'YYYY-MM-DD') = '" + dateNais + "'";
        }
        if (!groupeSang.isEmpty()) {
            query += " AND p.Groupe_Sang LIKE '%" + groupeSang + "%'";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                PatientRdv patientRdv = new PatientRdv(
                        resultSet.getString("ID_Patient"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getDate("Date_nais"),
                        resultSet.getString("Adresse"),
                        resultSet.getString("Groupe_Sang"),
                        resultSet.getString("ID_RDV"),
                        resultSet.getDate("date_RDV"),
                        resultSet.getString("heure")
                );
                list.add(patientRdv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
}
class PatientRdv {
    private String idPatient;
    private String nom;
    private String prenom;
    private Date dateNais;
    private String adresse;
    private String groupeSang;
    private String idRdv;
    private Date dateRdv;
    private String heure;

    public PatientRdv(String idPatient, String nom, String prenom, Date dateNais, String adresse, String groupeSang, String idRdv, Date dateRdv, String heure) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNais = dateNais;
        this.adresse = adresse;
        this.groupeSang = groupeSang;
        this.idRdv = idRdv;
        this.dateRdv = dateRdv;
        this.heure = heure;
    }

    public String getIdPatient() { return idPatient; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public Date getDateNais() { return dateNais; }
    public String getAdresse() { return adresse; }
    public String getGroupeSang() { return groupeSang; }
    public String getIdRdv() { return idRdv; }
    public Date getDateRdv() { return dateRdv; }
    public String getHeure() { return heure; }
}




class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton renderButton;
    private JButton editButton;
    private JTable table;
    private Action action;
    private int idRdvColumnIndex;  // Index for the column containing 'ID_RDV'

    public ButtonColumn(JTable table, Action action, int buttonColumnIndex, int idRdvColumnIndex) {
        super();
        this.table = table;
        this.action = action;
        this.idRdvColumnIndex = idRdvColumnIndex;
        
        renderButton = new JButton("Open");
        editButton = new JButton("Open");
        editButton.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            if (row >= 0) { // Ensure a valid row is selected
                String idRdv = (String) table.getValueAt(row, idRdvColumnIndex);
                
                // Create the consultation window with the fetched id_rdv
                new Consultation(idRdv).setVisible();
                
            } else { 
                // Handle the case where no valid row is selected
                
            }
        });

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(buttonColumnIndex).setCellRenderer(this);
        columnModel.getColumn(buttonColumnIndex).setCellEditor(this);
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return editButton;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {}

    @Override
    public void addCellEditorListener(CellEditorListener l) {}

    @Override
    public void removeCellEditorListener(CellEditorListener l) {}
}
