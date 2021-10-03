package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import business.logic.BLFacade;
import domain.User;
import exceptions.UserDoesntExist;
import exceptions.WrongDNI;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class DeleteUserGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5683327188637448536L;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	private JFrame padreFrame;
	private User selectedUser;
	private JTextField dniSearchField;
	
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}

	public DeleteUserGUI(JFrame padre) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			suInit();
			this.padreFrame = padre;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void suInit() {

		setTitle("Bets21");
		getContentPane().setLayout(null);
		setBounds(100, 100, 506, 339);
		JCheckBox chcConfirmation = new JCheckBox(ResourceBundle.getBundle("Etiquetas").getString("undone"));
		chcConfirmation.setBounds(129, 99, 266, 23);
		getContentPane().add(chcConfirmation);

		JButton btnDeleteUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteUser"));
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDeleteUser.setBounds(127, 32, 134, 41);
		getContentPane().add(btnDeleteUser);

		JButton btnBanUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BanUser"));
		btnBanUser.setBounds(262, 32, 133, 41);
		getContentPane().add(btnBanUser);

		JButton back = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		back.setBounds(391, 266, 89, 23);
		getContentPane().add(back);

		btnDeleteUser.setEnabled(false);
		btnBanUser.setEnabled(false);

		dniSearchField = new JTextField();
		dniSearchField.setBounds(223, 195, 102, 23);
		getContentPane().add(dniSearchField);
		dniSearchField.setColumns(10);

		JLabel lblNewLabel = new JLabel("DNI:");
		lblNewLabel.setBounds(181, 199, 32, 14);
		getContentPane().add(lblNewLabel);

		JTextArea txtrBanMotive = new JTextArea();
		txtrBanMotive.setText(ResourceBundle.getBundle("Etiquetas").getString("BExpl"));
		txtrBanMotive.setBounds(129, 129, 266, 55);
		getContentPane().add(txtrBanMotive);

		JLabel lblException = new JLabel("");
		lblException.setBounds(129, 229, 266, 26);
		getContentPane().add(lblException);
		//lblException.setVisible(false);


		chcConfirmation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chcConfirmation.isSelected()) 
				{
					btnDeleteUser.setEnabled(true);
					btnBanUser.setEnabled(true);
				}
				else 
				{
					btnDeleteUser.setEnabled(false);
					btnBanUser.setEnabled(false);
				}
			}
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});

		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					selectedUser = facade.getUser(facade.verifyDNI(dniSearchField.getText()));
				} catch (WrongDNI e1) {
					lblException.setText("The DNI must consists only of the seven numbers without the letter");
				} catch (UserDoesntExist e2) {
					lblException.setText("User doesn't exist");
				}
				if(!selectedUser.getPrivilegiado() && chcConfirmation.isSelected()) 
				{
					try {
						facade.deleteUser(selectedUser);
					} catch (UserDoesntExist e1) {
						lblException.setText("User doesn't exists");
					}
					dniSearchField.setText("");
				}
			}
		});

		btnBanUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			

				try {
					selectedUser = facade.getUser(facade.verifyDNI(dniSearchField.getText()));
					if(!selectedUser.getPrivilegiado() && chcConfirmation.isSelected()) 
					{
						facade.banUser(selectedUser, txtrBanMotive.getText());
						dniSearchField.setText("");
					}
				} catch (WrongDNI e1) {
					lblException.setText("This is not a valid DNI");
				} catch (UserDoesntExist e2) {
					lblException.setText("User doesn't exist");
				}
				catch (NullPointerException e2) {
					lblException.setText("User doesn't exist");
				}
			}
		});










	}
}
