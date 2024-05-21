import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.Vector;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import java.awt.SystemColor;

public class Dossiers {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField annee;
    private JTextField mois;
    private JTextField jour;
    private JTextField searchNomField;
    private JTextField searchPrenomField;
    private JComboBox<Object> comboBoxBloodGroup;
    /**
     * @wbp.nonvisual location=-38,129
     */
    private final JTextField textField = new JTextField();
    private JTextField Idfield;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Dossiers window = new Dossiers();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Dossiers() {
        initialize();
    }

 
   	private void initialize() {
   		textField.setColumns(10);
    	    frame = new JFrame();
    	    frame.setBounds(100, 100, 800, 679);
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.getContentPane().setLayout(null);

    	    JLabel titleLabel = new JLabel("Patient Dossier Details");
    	    titleLabel.setFont(new Font("Agency FB", Font.ITALIC, 17));
    	    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	    titleLabel.setBounds(108, 0, 692, 30);
    	    frame.getContentPane().add(titleLabel);

    	    JButton searchButton = new JButton("Search");
    	    searchButton.setBackground(UIManager.getColor("textHighlight"));
    	    searchButton.setFont(new Font("Agency FB", Font.PLAIN, 17));
    	    searchButton.setBounds(617, 85, 138, 36);
    	    searchButton.addActionListener(e -> searchPatientDossiers());
    	    frame.getContentPane().add(searchButton);

    	    List<PatientDossier> data = DataFetcher.fetchPatientDossiers();
    	    // Sort by dateConsult, handling null values by using a default date in the PatientDossier constructor
    	    data.sort(Comparator.comparing(PatientDossier::getDateConsult));
    	    String[] columnNames = {"Nom", "Prenom", "ID Patient", "Date Consult", "ID Dossier"};
    	    model = new DefaultTableModel(columnNames, 0);

    	    for (PatientDossier pd : data) {
    	        model.addRow(new Object[]{
    	                pd.getNom(),
    	                pd.getPrenom(),
    	                pd.getIdPatient(),
    	                pd.getDateConsult(),
    	                pd.getIdDoss()
    	                
    	        });
    	    }

    	    table = new JTable(model) {
    	        @Override
    	        public boolean isCellEditable(int row, int column) {
    	            return column == 4;
    	        }
    	    };

    	 

    	    table.setRowSelectionAllowed(true); // Allow row selection
    	    table.setColumnSelectionAllowed(true); // Allow column selection

    	    table.setFont(new Font("Agency FB", Font.PLAIN, 15));
    	    table.setRowHeight(30);
    	    table.setBackground(new Color(173, 216, 230));

    	    
    	    JTableHeader header = table.getTableHeader();
    	    header.setFont(new Font("Agency FB", Font.BOLD, 17));
    	    header.setBackground(new Color(100, 149, 237));

    	    Action action = new AbstractAction() {
    	        public void actionPerformed(ActionEvent e) {
    	            int modelRow = Integer.valueOf(e.getActionCommand());
    	            String idDoss = (String) table.getValueAt(modelRow, 4);
    	            String idpati = Idfield.getText().trim();
    	            displayPatientNotes(idpati);
    	            searchPatientDossiers();
    	        }
    	    };

    	   
    	    JScrollPane scrollPane = new JScrollPane(table);
    	    scrollPane.setBounds(10, 176, 764, 394);
    	    frame.getContentPane().add(scrollPane);

    	    createFormFields();
    	

    }

    private void createFormFields() {
        annee = new JTextField();
        annee.setFont(new Font("Agency FB", Font.BOLD, 18));
        annee.setColumns(10);
        annee.setBounds(460, 81, 80, 30);
        frame.getContentPane().add(annee);

        JLabel lblAaaa = new JLabel("AAAA");
        lblAaaa.setForeground(UIManager.getColor("textHighlight"));
        lblAaaa.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblAaaa.setBounds(415, 76, 84, 36);
        frame.getContentPane().add(lblAaaa);

        mois = new JTextField();
        mois.setFont(new Font("Agency FB", Font.BOLD, 18));
        mois.setColumns(10);
        mois.setBounds(365, 81, 40, 30);
        frame.getContentPane().add(mois);

        JLabel lblMm = new JLabel("MM");
        lblMm.setForeground(UIManager.getColor("textHighlight"));
        lblMm.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblMm.setBounds(332, 76, 84, 36);
        frame.getContentPane().add(lblMm);

        jour = new JTextField();
        jour.setFont(new Font("Agency FB", Font.BOLD, 18));
        jour.setColumns(10);
        jour.setBounds(292, 79, 40, 30);
        frame.getContentPane().add(jour);

        JLabel lblJj = new JLabel("JJ");
        lblJj.setForeground(UIManager.getColor("textHighlight"));
        lblJj.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblJj.setBounds(271, 76, 84, 36);
        frame.getContentPane().add(lblJj);

        JLabel lblDateDeNaissance = new JLabel("Date de Naissance :");
        lblDateDeNaissance.setForeground(UIManager.getColor("textHighlight"));
        lblDateDeNaissance.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblDateDeNaissance.setBounds(108, 76, 153, 36);
        frame.getContentPane().add(lblDateDeNaissance);

        JLabel lblNewLabel = new JLabel("Nom : ");
        lblNewLabel.setForeground(UIManager.getColor("textHighlight"));
        lblNewLabel.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblNewLabel.setBounds(22, 29, 84, 36);
        frame.getContentPane().add(lblNewLabel);

        searchNomField = new JTextField();
        searchNomField.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
        searchNomField.setColumns(10);
        searchNomField.setBounds(71, 33, 148, 36);
        frame.getContentPane().add(searchNomField);

        JLabel lblPrenom = new JLabel("Prenom : ");
        lblPrenom.setForeground(UIManager.getColor("textHighlight"));
        lblPrenom.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblPrenom.setBounds(229, 29, 84, 36);
        frame.getContentPane().add(lblPrenom);

        searchPrenomField = new JTextField();
        searchPrenomField.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
        searchPrenomField.setColumns(10);
        searchPrenomField.setBounds(304, 33, 148, 36);
        frame.getContentPane().add(searchPrenomField);

        JLabel lblGroupeSanguin = new JLabel("Groupe Sanguin :");
        lblGroupeSanguin.setForeground(UIManager.getColor("textHighlight"));
        lblGroupeSanguin.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblGroupeSanguin.setBounds(460, 34, 131, 36);
        frame.getContentPane().add(lblGroupeSanguin);

        comboBoxBloodGroup = new JComboBox<>();
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
        
        JButton btnNewButton = new JButton("Accueil");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AccueilP.main(null);
        		frame.setVisible(false);
        	}
        });
        btnNewButton.setBackground(SystemColor.inactiveCaption);
        btnNewButton.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnNewButton.setBounds(0, 7, 89, 23);
        frame.getContentPane().add(btnNewButton);
        
        JLabel lblEntrerLid = new JLabel("Entrer l'Id : ");
        lblEntrerLid.setForeground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
        lblEntrerLid.setFont(new Font("Agency FB", Font.ITALIC, 22));
        lblEntrerLid.setBounds(94, 123, 153, 36);
        frame.getContentPane().add(lblEntrerLid);
        
        Idfield = new JTextField();
        Idfield.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 18));
        Idfield.setColumns(10);
        Idfield.setBounds(187, 123, 233, 36);
        frame.getContentPane().add(Idfield);
        
        JButton btnNewButton_1 = new JButton("Confirmer");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idPatient = Idfield.getText().trim();
                if (!idPatient.isEmpty()) {
                    displayPatientNotes(idPatient);
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un ID Patient.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNewButton_1.setFont(new Font("Agency FB", Font.ITALIC, 18));
        btnNewButton_1.setBackground(SystemColor.textHighlight);
        btnNewButton_1.setBounds(439, 122, 131, 37);
        frame.getContentPane().add(btnNewButton_1);
    }

    private void searchPatientDossiers() {
        String nom = searchNomField.getText();
        String prenom = searchPrenomField.getText();
        String dateNaissance = annee.getText().trim()+ "-" + mois.getText().trim() + "-" + jour.getText().trim();
        String groupeSanguin = comboBoxBloodGroup.getSelectedItem().toString();

        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT p.Nom, p.Prenom, p.ID_Patient, c.date_consult, d.ID_DOSS " +
                             "FROM Patient p " +
                             "LEFT JOIN consultation c ON p.ID_Patient = c.ID_Patient " +
                             "LEFT JOIN DOSSIER_patient d ON p.ID_Patient = d.ID_Patient " +
                             "WHERE p.Nom LIKE ? AND p.Prenom LIKE ? AND p.Date_nais LIKE ? AND p.Groupe_Sang LIKE ?")) {
            
            statement.setString(1, "%" + nom + "%");
            statement.setString(2, "%" + prenom + "%");
            statement.setString(3, "%" + dateNaissance + "%");
            statement.setString(4, "%" + groupeSanguin + "%");

            ResultSet rs = statement.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("ID_Patient"),
                        rs.getDate("date_consult"),
                        rs.getString("ID_DOSS"),
                        "Notes"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void displayPatientNotes(String idPatient) {
        String notes = null;
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
             PreparedStatement statement = connection.prepareStatement("SELECT Note FROM DOSSIER_patient WHERE ID_Patient = ?")) {

            statement.setString(1, idPatient);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                notes = rs.getString("Note");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la récupération des notes.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        if (notes != null) {
            JTextArea textArea = new JTextArea(notes);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setCaretPosition(0);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300)); // Set the preferred size of the scroll pane

            JOptionPane.showMessageDialog(null, scrollPane, "Notes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Aucune note trouvée pour l'ID Patient : " + idPatient, "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
        private final JTable table;
        private final Action action;
        private final JButton renderButton;
        private final JButton editButton;
        private Object editorValue;

        public ButtonColumn(JTable table, Action action, int column) {
            this.table = table;
            this.action = action;
            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            renderButton.setText((value == null) ? "" : value.toString());
            return renderButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            editorValue = value; // Set the editorValue to the provided value
            editButton.setText((value == null) ? "" : value.toString());
            return editButton;
        }


        @Override
        public Object getCellEditorValue() {
            return editorValue;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
            int row = table.convertRowIndexToModel(table.getEditingRow());
            action.actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, String.valueOf(row)));
            table.getSelectionModel().setSelectionInterval(row, row); // Ensure the row remains selected
        }
    }

    static class DataFetcher {


    	    public static List<PatientDossier> fetchPatientDossiers() {
    	        List<PatientDossier> data = new ArrayList<>();
    	        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "halwan");
    	             Statement statement = connection.createStatement();
    	             ResultSet rs = statement.executeQuery(
    	                     "SELECT p.Nom, p.Prenom, p.ID_Patient, c.date_consult, d.ID_DOSS " +
    	                     "FROM Patient p " +
    	                     "LEFT JOIN consultation c ON p.ID_Patient = c.ID_Patient " +
    	                     "LEFT JOIN DOSSIER_patient d ON p.ID_Patient = d.ID_Patient")) {

    	            while (rs.next()) {
    	                data.add(new PatientDossier(
    	                        rs.getString("Nom"),
    	                        rs.getString("Prenom"),
    	                        rs.getString("ID_Patient"),
    	                        rs.getDate("date_consult"),
    	                        rs.getString("ID_DOSS")
    	                ));
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	        return data;
    	    }
    	}


    static class PatientDossier {
  
    	    private final String nom;
    	    private final String prenom;
    	    private final String idPatient;
    	    private final Date dateConsult;
    	    private final String idDoss;

    	    public PatientDossier(String nom, String prenom, String idPatient, Date dateConsult, String idDoss) {
    	        this.nom = nom;
    	        this.prenom = prenom;
    	        this.idPatient = idPatient;
    	        this.dateConsult = (dateConsult != null) ? dateConsult : Date.valueOf("1970-01-01");
    	        this.idDoss = idDoss;
    	    }

    	    public String getNom() {
    	        return nom;
    	    }

    	    public String getPrenom() {
    	        return prenom;
    	    }

    	    public String getIdPatient() {
    	        return idPatient;
    	    }

    	    public Date getDateConsult() {
    	        return dateConsult;
    	    }

    	    public String getIdDoss() {
    	        return idDoss;
    	    }
    	}
    }
