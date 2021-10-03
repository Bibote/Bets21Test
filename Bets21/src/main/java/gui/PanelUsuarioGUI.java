package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JButton;

public class PanelUsuarioGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;
	private JFrame thisFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	/**
	 * Create the frame.
	 */
	public PanelUsuarioGUI(JFrame padre) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		facade.reloadUser();
		try {
			suInit();
			this.padreFrame = padre;
			this.thisFrame = this;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}
	public void suInit() {


		setBounds(100, 100, 500, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsrPanel"));
		lblNewLabel.setBounds(10, 10, 286, 13);
		contentPane.add(lblNewLabel);

		String nombre = facade.returnCurrentUsr().getNombre();

		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Hi")+ " " + nombre);
		lblNewLabel_1.setBounds(10, 34, 94, 13);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton.setBounds(354, 6, 122, 21);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("YouHave") + " " +facade.returnCurrentUsr().getChutiGoles()+" Chutigoles");
		lblNewLabel_2.setBounds(35, 78, 166, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnAdministrarFondos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PanelUsuarioGUI.btnNewButton_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAdministrarFondos.setBounds(211, 78, 166, 14);
		contentPane.add(btnAdministrarFondos);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});
		
		btnAdministrarFondos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarFondosGUI a = new AdministrarFondosGUI(thisFrame);
				a.setVisible(true);
				thisFrame.setVisible(false);
			}
		});
		
		
	}
}
