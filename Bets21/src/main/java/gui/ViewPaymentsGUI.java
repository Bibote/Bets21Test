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

public class ViewPaymentsGUI extends JFrame {

	private JPanel contentPane;
	private JFrame padreFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private String[] columnNamesPayments = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Date"),
			ResourceBundle.getBundle("Etiquetas").getString("Amount"),
			ResourceBundle.getBundle("Etiquetas").getString("Card")

	};
	private DefaultTableModel tableModelPayments; 
	private JTable tablePayments;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ViewPaymentsGUI(JFrame padreFrame) {
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

		tablePayments = new JTable();
		scrollPane.setViewportView(tablePayments);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MadePayments"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(134, 9, 115, 20);
		contentPane.add(lblNewLabel);
		tableModelPayments= new DefaultTableModel(null, columnNamesPayments);
		tablePayments.setModel(tableModelPayments);
		tablePayments.getColumnModel().getColumn(0).setPreferredWidth(30);
		tablePayments.getColumnModel().getColumn(1).setPreferredWidth(30);
		tablePayments.getColumnModel().getColumn(2).setPreferredWidth(30);



		Vector<domain.Payment> pagos= facade.getPaymentsFromUser();
		System.out.println(pagos);
		if(!pagos.isEmpty()) {
			for (domain.Payment p:pagos){
				Vector<Object> row = new Vector<Object>();

				row.add(p.getFecha());
				row.add(p.getChutigoles());
				row.add(p.getCard().getCardNumber());
				row.add(p);
				tableModelPayments.addRow(row);	


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
