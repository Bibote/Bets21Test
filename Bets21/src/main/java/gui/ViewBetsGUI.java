package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.logic.BLFacade;

public class ViewBetsGUI extends JFrame {

	private JPanel contentPane;
	private JFrame padreFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	private DefaultTableModel tableModelBets; 
	private JTable tableBets;
	private JScrollPane scrollPane;


	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Event"),
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Bet"),
			ResourceBundle.getBundle("Etiquetas").getString("Amount")

	};
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ViewBetsGUI(JFrame padreFrame) {
		this.padreFrame = padreFrame;
		initialize();


	}

	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}

	public void initialize(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 34, 414, 146);
		contentPane.add(scrollPane);

		tableBets = new JTable();
		scrollPane.setViewportView(tableBets);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("YourBets"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(134, 9, 115, 20);
		contentPane.add(lblNewLabel);
		tableModelBets= new DefaultTableModel(null, columnNamesEvents);
		tableBets.setModel(tableModelBets);
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableBets.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableBets.getColumnModel().getColumn(2).setPreferredWidth(30);
		tableBets.getColumnModel().getColumn(3).setPreferredWidth(1);



		Vector<domain.Apuesta> bets= facade.getBetsFromUser();
		if(!bets.isEmpty()) {
			for (domain.Apuesta ev:bets){
				Vector<Object> row = new Vector<Object>();

				System.out.println("Events "+ev);

				row.add(ev.getEvento().toString());
				row.add(ev.getPronostico().getQuestion().getQuestion());
				row.add(ev.getPronostico().toString());
				row.add(ev.getChutiGoles());
				row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
				tableModelBets.addRow(row);	


			}
		}
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton.setBounds(302, 229, 122, 21);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});
	}
}
