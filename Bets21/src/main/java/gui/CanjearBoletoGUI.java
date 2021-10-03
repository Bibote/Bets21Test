package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import exceptions.BoletoNoExiste;
import exceptions.BoletoUsado;
import exceptions.MaxUsed;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CanjearBoletoGUI extends JFrame {

	private JPanel contentPane;
	private JTextField codigo;
	private JFrame padreFrame;
	private JFrame mainFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	private JLabel errores;

	public CanjearBoletoGUI(JFrame padre) {
		try {
			Ini();
			padreFrame = padre;
			mainFrame = this;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the frame.
	 * @return 
	 */
	public void Ini() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EtiquetaBoletos"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(118, 24, 149, 22);
		contentPane.add(lblNewLabel);
		
		codigo = new JTextField();
		codigo.setToolTipText("");
		codigo.setBounds(30, 106, 158, 20);
		contentPane.add(codigo);
		codigo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IntroCode"));
		lblNewLabel_1.setBounds(30, 91, 128, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Canjear"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					facade.useBoleto(codigo.getText(), facade.returnCurrentUsr());
					errores.setText("Boleto Canjeado");
				
					errores.setForeground(Color.BLACK);
					errores.setVisible(true);
				} catch (MaxUsed e) {
					errores.setVisible(true);
					errores.setText("Boleto Caducado");
					//e.printStackTrace();
				}
				catch (BoletoNoExiste e) {
					errores.setVisible(true);
					errores.setText("Codigo Erroneo");
					//e.printStackTrace();
					
				}
				catch (BoletoUsado e) {
					errores.setVisible(true);
					errores.setText("Boleto ya usado");
					//e.printStackTrace();
					
				}
			}
		});
		btnNewButton.setBounds(69, 208, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				padreFrame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnNewButton_1.setBounds(267, 208, 89, 23);
		contentPane.add(btnNewButton_1);
		
		errores = new JLabel("New label");
		errores.setForeground(Color.RED);
		errores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		errores.setBounds(245, 109, 179, 40);
		contentPane.add(errores);
		errores.setVisible(false);
	}
}
