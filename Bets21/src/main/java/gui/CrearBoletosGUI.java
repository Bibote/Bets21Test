package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import exceptions.BoletoNoExiste;
import exceptions.CodigoRepetido;
import exceptions.NotEnoughChuti;

import javax.swing.JLabel;
import java.awt.Font;


import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class CrearBoletosGUI extends JFrame {

	private JPanel contentPane;
	private JTextField codigo;
	private JTextField cantidad;
	private JTextField valor;
	private JFrame padreFrame;

	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	private JLabel lblNewLabel_4;

	public CrearBoletosGUI(JFrame padre) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Ini();
			this.padreFrame = padre;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public void Ini() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GestionBoletos"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(120, 22, 304, 25);
		contentPane.add(lblNewLabel);
		
		codigo = new JTextField();
		codigo.setBounds(10, 89, 86, 20);
		contentPane.add(codigo);
		codigo.setColumns(10);
		
		cantidad = new JTextField();
		cantidad.setBounds(10, 167, 86, 20);
		contentPane.add(cantidad);
		cantidad.setColumns(10);
		
		valor = new JTextField();
		valor.setBounds(208, 89, 86, 20);
		contentPane.add(valor);
		valor.setColumns(10);
		
		JButton Close = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				padreFrame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Close.setBounds(297, 209, 89, 23);
		contentPane.add(Close);
		
		JButton crear = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CrearBoleto"));
		crear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double cantida = Double.parseDouble(valor.getText());
				int max = Integer.parseInt(cantidad.getText());
				
				try {
					facade.crearBoleto(codigo.getText(),max,cantida);
					lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("Creado"));
					lblNewLabel_4.setVisible(true);
				} catch (NotEnoughChuti e) {
					lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("SinCG"));
					lblNewLabel_4.setVisible(true);
					//e.printStackTrace();
				}
				catch(CodigoRepetido e ) {
					lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("CodigoUso"));
					lblNewLabel_4.setVisible(true);
					//e.printStackTrace();
				}
				
				
				
			}
		});
		crear.setBounds(10, 209, 133, 23);
		contentPane.add(crear);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Codigo"));
		lblNewLabel_1.setBounds(10, 74, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Valor"));
		lblNewLabel_2.setBounds(208, 74, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Cantidad"));
		lblNewLabel_3.setBounds(10, 152, 86, 14);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Creado"));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(185, 167, 239, 31);
		contentPane.add(lblNewLabel_4);
		
		JButton eliminar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Eliminar"));
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					facade.eliminarBoleto(codigo.getText());
				} catch (BoletoNoExiste e) {
					lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("NoExisteBoleto"));
					lblNewLabel_4.setVisible(true);
					//e.printStackTrace();
				}
			}
		});
		eliminar.setBounds(153, 209, 122, 23);
		contentPane.add(eliminar);
		lblNewLabel_4.setVisible(false);
	}
}
