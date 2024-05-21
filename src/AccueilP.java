import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.SystemColor;

public class AccueilP extends Principale {
    public class Main {

		public Main() {
			// TODO Auto-generated constructor stub
		}

	}

	private JFrame frame;
    private String username; 
   
    public AccueilP(String username) {
        this.username = username;
        initialize(); 
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AccueilP window = new AccueilP("YourUsername"); 
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
    public AccueilP() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 908, 552);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        frame.setContentPane(layeredPane);

        // Set the background image
        String imagePath = "images\\back.png"; 
        ImageIcon backgroundImage = new ImageIcon(imagePath);
        JLabel lblNewLabel = new JLabel("", backgroundImage, SwingConstants.CENTER);
        lblNewLabel.setBackground(SystemColor.textHighlight);
        lblNewLabel.setBounds(0, 0, 953, 501);
        layeredPane.add(lblNewLabel, JLayeredPane.DEFAULT_LAYER);

        JButton btnNewButton = new JButton("Ajouter un patient ");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.setVisible(false);
        		Patient.main(null);
        		
        	}
        });
        btnNewButton.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 24));
        btnNewButton.setBackground(SystemColor.textHighlight);
        btnNewButton.setBounds(310, 83, 265, 59);
        layeredPane.add(btnNewButton, JLayeredPane.PALETTE_LAYER);
        
        JButton btnRdv = new JButton("RDV");
        btnRdv.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.setVisible(false);
        		
        		rdvCherche.main(null);
        	}
        });
        layeredPane.setLayer(btnRdv, 100);
        btnRdv.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 24));
        btnRdv.setBackground(SystemColor.textHighlight);
        btnRdv.setBounds(310, 195, 265, 59);
        layeredPane.add(btnRdv);
        
        JButton btnDossierMdical = new JButton("Dossier médical");
        btnDossierMdical.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.setVisible(false);
        		Dossiers.main(null);
        	}
        });
        layeredPane.setLayer(btnDossierMdical, 100);
        btnDossierMdical.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 24));
        btnDossierMdical.setBackground(SystemColor.textHighlight);
        btnDossierMdical.setBounds(310, 307, 265, 59);
        layeredPane.add(btnDossierMdical);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Paramètres");
        mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Mon profile");
        ImageIcon icon2 = new ImageIcon("images\\prof.png");
        mntmNewMenuItem.setIcon(icon2);
        mntmNewMenuItem.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        mntmNewMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Disconnect");
        mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        ImageIcon icon = new ImageIcon("images\\out.png");

        mntmNewMenuItem_1.setIcon(icon);

        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Principale.main(null);
            }
        });
        mnNewMenu.add(mntmNewMenuItem_1);
    }

    public void showFrame() {
        frame.setVisible(true); 
    }
}
