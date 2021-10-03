package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import exceptions.DifferentPasswords;
import exceptions.UserDoesntExist;
import exceptions.WrongDNI;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class LogInGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JFrame logIn;
	
	/**
	 * Create the frame.
	 */
	public LogInGUI(JFrame padre) {
		setTitle("LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			suInit();
			this.padreFrame = padre;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}
	private void suInit() {
		this.logIn = this;
		BLFacade facade = PublicMainGUI.getBusinessLogic();
		setBounds(100, 100, 506, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton.setBounds(354, 6, 122, 21);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});

		JButton btnNewButton_2 = new JButton("SignUp");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUpGUI signUp = new SignUpGUI(padreFrame); 
				setVisible(false);
				signUp.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setBounds(10, 264, 466, 21);
		contentPane.add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("O");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(220, 241, 45, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("DNI");
		lblNewLabel_1.setBounds(40, 135, 82, 20);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(106, 135, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Introduce un DNI correcto");
		lblNewLabel_3.setBounds(213, 135, 201, 20);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		JLabel lblNewLabel_4 = new JLabel("El usuario o la contrase�a son incorrectas");
		lblNewLabel_4.setBounds(213, 160, 263, 20);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

		JLabel lblNewLabel_2 = new JLabel("PassWord");
		lblNewLabel_2.setBounds(40, 160, 82, 20);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(106, 160, 96, 21);
		contentPane.add(passwordField);


		JButton btnNewButton_1 = new JButton("LogIn");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String inputDNI = textField.getText();
				String inputPassword = new String(passwordField.getPassword());
				int DNI;
				try {
					DNI = facade.verifyDNI(inputDNI);
					lblNewLabel_3.setVisible(false);
					lblNewLabel_4.setVisible(false);
					facade.userLogin(DNI, inputPassword);
					if (facade.nlPrivilegesUser(facade.obtainCurrentUsr())) 
					{	
						
						AdminMainGUI mainGUI = new AdminMainGUI(padreFrame);
						mainGUI.setVisible(true);
						dispose();
					}
					else {
						if(facade.obtainCurrentUsr().isBan()) 
						{
							BanMessageGUI banM = new BanMessageGUI(logIn,facade.obtainCurrentUsr().getMessage());
							setVisible(false);
							banM.setVisible(true);
							facade.logOutUser();
						}
						else 
						{
		
							UserMainGUI mainGUI = new UserMainGUI(padreFrame);
							mainGUI.setVisible(true);
							dispose();
						}
					}

				} catch (WrongDNI e1) {
					lblNewLabel_3.setVisible(true);

				} catch (UserDoesntExist | DifferentPasswords e2) {

					lblNewLabel_4.setVisible(true);
				} 
			}
		});
		btnNewButton_1.setBounds(10, 210, 466, 21);
		contentPane.add(btnNewButton_1);








	}
}