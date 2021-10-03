package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import business.logic.BLFacade;
import dependencies.DateLabelFormatter;
import domain.User;
import exceptions.DifferentEmails;
import exceptions.DifferentPasswords;
import exceptions.EmptyNames;
import exceptions.NeedMoreThan18y;
import exceptions.PasswordMustBeLarger;
import exceptions.PreferencesNotChecked;
import exceptions.UserAlreadyExist;
import exceptions.WrongDNI;
import exceptions.WrongEmailPattern;



public class SignUpGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private JFrame padreFrame;

	/**
	 * Create the frame.
	 */
	public SignUpGUI(JFrame padre) {
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





		setBackground(Color.LIGHT_GRAY);
		setTitle("Bets21");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tienes todo para GANAR?");
		lblNewLabel.setFont(new Font("Gill Sans MT Condensed", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(10, 9, 421, 50);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(10, 70, 100, 20);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(130, 70, 100, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel jFLabelErrorStringVacios = new JLabel("No se permiten campos vacios.");
		jFLabelErrorStringVacios.setBounds(240, 70, 280, 70);
		contentPane.add(jFLabelErrorStringVacios);
		jFLabelErrorStringVacios.setVisible(false);

		JLabel lblNewLabel_2 = new JLabel("1ºApellido");
		lblNewLabel_2.setBounds(10, 95, 100, 20);
		contentPane.add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(130, 95, 100, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);


		JLabel lblNewLabel_3 = new JLabel("2º Apellido");
		lblNewLabel_3.setBounds(10, 120, 100, 20);
		contentPane.add(lblNewLabel_3);

		textField_2 = new JTextField();
		textField_2.setBounds(130, 120, 100, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);


		JLabel lblNewLabel_4 = new JLabel("@ email");
		lblNewLabel_4.setBounds(10, 145, 100, 20);
		contentPane.add(lblNewLabel_4);

		textField_3 = new JTextField();
		textField_3.setBounds(130, 145, 100, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_19 = new JLabel("Introduzca una direccion de correo valida");
		lblNewLabel_19.setBounds(240, 145, 300, 20);
		contentPane.add(lblNewLabel_19);
		lblNewLabel_19.setVisible(false);


		JLabel lblNewLabel_5 = new JLabel("Repita el email");
		lblNewLabel_5.setBounds(10, 170, 100, 20);
		contentPane.add(lblNewLabel_5);

		textField_4 = new JTextField();
		textField_4.setBounds(130, 170, 100, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblNewLabel_20 = new JLabel("Los emails no coinciden");
		lblNewLabel_20.setBounds(240, 170, 280, 20);
		contentPane.add(lblNewLabel_20);
		lblNewLabel_20.setVisible(false);


		JLabel lblNewLabel_6 = new JLabel("DNI");
		lblNewLabel_6.setBounds(10, 195, 100, 20);
		contentPane.add(lblNewLabel_6);

		textField_5 = new JTextField();
		textField_5.setBounds(130, 195, 100, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("Introduzca un DNI valido");
		lblNewLabel_12.setBounds(240, 195, 191, 20);
		contentPane.add(lblNewLabel_12);
		lblNewLabel_12.setVisible(false);

		JLabel lblNewLabel_18 = new JLabel("Ya existe un usuario con este DNI");
		lblNewLabel_18.setBounds(240, 195, 300, 20);
		contentPane.add(lblNewLabel_18);
		lblNewLabel_18.setVisible(false);



		JLabel lblNewLabel_7 = new JLabel("Telefono Movil");
		lblNewLabel_7.setBounds(10, 220, 100, 20);
		contentPane.add(lblNewLabel_7);

		textField_6 = new JTextField();
		textField_6.setBounds(130, 220, 100, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);

		JLabel lblNewLabel_17 = new JLabel("Introduzca un numero de telefono valido");
		lblNewLabel_17.setBounds(240, 220, 300, 20);
		contentPane.add(lblNewLabel_17);
		lblNewLabel_17.setVisible(false);


		JLabel lblNewLabel_8 = new JLabel("Contrasena");
		lblNewLabel_8.setBounds(10, 245, 100, 20);
		contentPane.add(lblNewLabel_8);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(130, 245, 100, 20);
		contentPane.add(passwordField);

		JLabel lblNewLabel_16 = new JLabel("La contrasena debe tener mas de 6 caracteres.");
		lblNewLabel_16.setBounds(240, 245, 336, 20);
		contentPane.add(lblNewLabel_16);
		lblNewLabel_16.setVisible(false);


		JLabel lblNewLabel_9 = new JLabel("Repita la contrasena");
		lblNewLabel_9.setBounds(10, 270, 120, 20);
		contentPane.add(lblNewLabel_9);

		passwordField_1 = new JPasswordField();
		passwordField_1.setEchoChar('*');
		passwordField_1.setBounds(130, 270, 100, 20);
		contentPane.add(passwordField_1);

		JLabel lblNewLabel_13 = new JLabel("Las contrasenas no coinciden");
		lblNewLabel_13.setBounds(240, 270, 220, 20);
		contentPane.add(lblNewLabel_13);
		lblNewLabel_13.setVisible(false);


		JLabel lblNewLabel_10 = new JLabel("Fecha de nacimiento");
		lblNewLabel_10.setBounds(10, 295, 120, 20);
		contentPane.add(lblNewLabel_10);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(130, 295, 129, 20);
		contentPane.add(datePicker);

		JLabel lblNewLabel_14 = new JLabel("Necesitas ser mayor de edad");
		lblNewLabel_14.setBounds(269, 295, 191, 20);
		contentPane.add(lblNewLabel_14);
		lblNewLabel_14.setVisible(false);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Autorizo la comunicacion de mis datos de registro a empresas del Grupo Sinking Soft, para personalizar mi publicidad en webs propias y de terceros.");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 8));
		chckbxNewCheckBox.setBounds(10, 320, 600, 20);
		contentPane.add(chckbxNewCheckBox);


		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("He leido y acepto los terminos y condiciones y la Politica de privacidad");
		chckbxNewCheckBox_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		chckbxNewCheckBox_1.setBounds(10, 345, 300, 20);
		contentPane.add(chckbxNewCheckBox_1);

		JLabel lblNewLabel_15 = new JLabel("Tienes que autorizar las dos casillas.");
		lblNewLabel_15.setBounds(309, 348, 267, 13);
		contentPane.add(lblNewLabel_15);
		lblNewLabel_15.setVisible(false);

		JButton btnNewButton_2 = new JButton("Volver atras");
		btnNewButton_2.setBounds(454, 10, 122, 21);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});

		JButton btnNewButton = new JButton("Registrate ahora!");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnNewButton.setBounds(49, 415, 459, 20);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//jButtonClose_actionPerformed(e);
				String inputName = textField.getText();
				String input1Apellido = textField_1.getText();
				String input2Apellido = textField_2.getText();
				String input1Email = textField_3.getText();
				String input2Email = textField_4.getText();
				String inputDNI = textField_5.getText();

				String input1Password = new String(passwordField.getPassword());				
				String input2Password = new String(passwordField_1.getPassword());
				Date fechaNac = (Date) datePicker.getModel().getValue();
				boolean checkDatos = chckbxNewCheckBox.isSelected();
				boolean checkTerminos = chckbxNewCheckBox_1.isSelected();

				try {
					
					lblNewLabel_12.setVisible(false);
					lblNewLabel_19.setVisible(false);
					lblNewLabel_20.setVisible(false);
					lblNewLabel_16.setVisible(false);
					lblNewLabel_13.setVisible(false);
					lblNewLabel_14.setVisible(false);
					lblNewLabel_15.setVisible(false);
					lblNewLabel_17.setVisible(false);
					jFLabelErrorStringVacios.setVisible(false);
					lblNewLabel_18.setVisible(false);
					int dni = facade.verifyDNI(inputDNI);
					

					facade.validateEmail(input1Email);
					

					facade.emailVerify(input1Email, input2Email);
					

					facade.passwordLenght(input1Password);
					

					facade.passwordVerify(input1Password, input2Password);
					

					facade.ageVerify(fechaNac);
					

					facade.checkPreferences(checkDatos, checkTerminos);
					

					Integer inputMovil = Integer.parseInt(textField_6.getText());
					

					User newUsr = new User(inputMovil, dni, input1Password, inputName, input1Apellido, input2Apellido, input1Email, fechaNac);
					facade.checkEmptyUsers(newUsr);
					
					facade.createUser(newUsr);
					
					returnFather();
				

				} catch (WrongDNI e1) {
					lblNewLabel_12.setVisible(true);
				} catch (DifferentPasswords e1) {
					lblNewLabel_13.setVisible(true);
				} catch (NeedMoreThan18y e1) {
					lblNewLabel_14.setVisible(true);
				} catch (PreferencesNotChecked e1) {
					lblNewLabel_15.setVisible(true);
				} catch (PasswordMustBeLarger e1) {
					lblNewLabel_16.setVisible(true);
				} catch (UserAlreadyExist e1) {
					lblNewLabel_12.setVisible(false);
					lblNewLabel_18.setVisible(true);
				} catch (NumberFormatException e1) {
					lblNewLabel_17.setVisible(true);
				} catch (WrongEmailPattern e1) {
					lblNewLabel_19.setVisible(true);
				} catch (DifferentEmails e1) {
					lblNewLabel_20.setVisible(true);
				} catch (EmptyNames e1) {
					jFLabelErrorStringVacios.setVisible(true);
				}

				//System.out.println(checkDatos);

				//System.out.println(fechaNac);


			}
		});

		JLabel lblNewLabel_11 = new JLabel("O");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_11.setBounds(272, 447, 20, 20);
		contentPane.add(lblNewLabel_11);


		JButton btnNewButton_1 = new JButton("Inicia sesion");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnNewButton_1.setBounds(49, 471, 459, 20);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				LogInGUI logIn = new LogInGUI(padreFrame);
				setVisible(false);
				logIn.setVisible(true);
			}

		});





















	}
}
