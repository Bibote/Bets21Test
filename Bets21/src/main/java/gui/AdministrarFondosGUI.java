package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import domain.Card;
import exceptions.ErrorCreditCard;
import exceptions.NoCardsStored;
import exceptions.NotEnoughChuti;

import javax.swing.JTextField;

public class AdministrarFondosGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	private JFrame padreFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	private JComboBox<String> jComboBoxTarjetas = new JComboBox<String>();
	DefaultComboBoxModel<String> modelTarjetas = new DefaultComboBoxModel<String>();
	private JTextField textoNumTarjeta;
	private JLabel lblIntroducirTarjeta;
	private JButton btnGuardarTarjeta;
	private JLabel lblErrorTarjetasVacias;
	private JLabel lblTarjetaErronea;
	private JTextField textFieldCantidad;
	private JLabel lblSoloEnteros;
	private void returnFather() 
	{
		facade.reloadUser();
		
		((PanelUsuarioGUI) padreFrame).suInit();
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public AdministrarFondosGUI(JFrame padre) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			suInit();
			this.padreFrame = padre;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void suInit() 
	{

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
		
		JLabel sinTarjeta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NoTarSel"));
		sinTarjeta.setBounds(266, 354, 199, 13);
		contentPane.add(sinTarjeta);
		sinTarjeta.setVisible(false);
		
		JLabel lblSeleccioneTarjeta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelCard"));
		lblSeleccioneTarjeta.setBounds(34, 103, 166, 14);
		contentPane.add(lblSeleccioneTarjeta);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});
		
		jComboBoxTarjetas.setModel(modelTarjetas);
		jComboBoxTarjetas.setBounds(new Rectangle(149, 100, 250, 20));
		contentPane.add(jComboBoxTarjetas, null);
		
		lblErrorTarjetasVacias = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NoSavedCard")); 
		lblErrorTarjetasVacias.setBounds(149, 128, 316, 14);
		contentPane.add(lblErrorTarjetasVacias);
		
		
		
		btnGuardarTarjeta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SaveCard"));
		btnGuardarTarjeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String tarjeta = textoNumTarjeta.getText();
				try {
					facade.comprobarTarjeta(tarjeta);
					facade.añadirTarjetaUsr(tarjeta);
					suInit();
				} catch (ErrorCreditCard e) {
					lblTarjetaErronea.setVisible(false);
					
				}

				
			}
		});
		btnGuardarTarjeta.setBounds(35, 199, 142, 23);
		contentPane.add(btnGuardarTarjeta);
		
		
		textoNumTarjeta = new JTextField();
		textoNumTarjeta.setText(""); 
		textoNumTarjeta.setBounds(191, 169, 250, 20);
		contentPane.add(textoNumTarjeta);
		textoNumTarjeta.setColumns(10);
		
		
		lblIntroducirTarjeta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IntroCard")); 
		lblIntroducirTarjeta.setBounds(37, 170, 140, 18);
		contentPane.add(lblIntroducirTarjeta);
		
		lblTarjetaErronea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WrongCard"));
		lblTarjetaErronea.setBounds(232, 203, 167, 14);
		contentPane.add(lblTarjetaErronea);
		
		JButton btnAñadirFondos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddFunds")); 
		btnAñadirFondos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				lblSoloEnteros.setVisible(false);
				
				String cantidad = textFieldCantidad.getText();
				try {
					if (modelTarjetas.getSize()==0) {
						sinTarjeta.setVisible(true);
					}else {
						Date hoy = new Date();
						sinTarjeta.setVisible(false);
						Integer dinero = Integer.parseInt(cantidad);
						double chutis = (double) dinero;
						facade.makePayment(chutis,hoy,(String) modelTarjetas.getSelectedItem());
						returnFather();
					}
				}catch (NumberFormatException e1) {
					lblSoloEnteros.setVisible(true); 
				}
			}
		});
		btnAñadirFondos.setBounds(35, 398, 142, 23);
		contentPane.add(btnAñadirFondos);
		
		JButton btnExtraerFondos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WithdrawFunds")); 
		btnExtraerFondos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSoloEnteros.setText(ResourceBundle.getBundle("Etiquetas").getString("OnlyIntegers"));
				lblSoloEnteros.setVisible(false);
				
				
				String cantidad = textFieldCantidad.getText();
				try {
					if (modelTarjetas.getSize()==0) {
						sinTarjeta.setVisible(true);
					}else {
						sinTarjeta.setVisible(false);
						Integer dinero = Integer.parseInt(cantidad);
						double chutis = (double) dinero;
						facade.changeChutiUs(-chutis);
						returnFather();
					}
				}catch (NumberFormatException e1) {
					lblSoloEnteros.setVisible(true); 
				} catch (NotEnoughChuti e) {
					lblSoloEnteros.setVisible(true);
					lblSoloEnteros.setText(ResourceBundle.getBundle("Etiquetas").getString("SinCG"));
				}
			}
		});
		btnExtraerFondos.setBounds(266, 398, 142, 23);
		contentPane.add(btnExtraerFondos);
		
		JLabel lblIntroduzcaCantidad = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Amount"));
		lblIntroduzcaCantidad.setBounds(35, 302, 166, 14);
		contentPane.add(lblIntroduzcaCantidad);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setText(""); 
		textFieldCantidad.setBounds(210, 299, 86, 20);
		contentPane.add(textFieldCantidad);
		textFieldCantidad.setColumns(10);
		
		lblSoloEnteros = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("OnlyIntegers"));
		lblSoloEnteros.setBounds(210, 330, 231, 14);
		contentPane.add(lblSoloEnteros);
		
		lblSoloEnteros.setVisible(false);
		
		lblTarjetaErronea.setVisible(false);
		
		
		desbloquearTarjeta(false);
		lblIntroducirTarjeta.setVisible(true);
		textoNumTarjeta.setVisible(true);
		btnGuardarTarjeta.setVisible(true);
		
		Vector<Card> tarjetas;
		try {
			
			tarjetas = facade.obtenerTarjetasUsr();
			
			
			for (Card t : tarjetas) {
				modelTarjetas.addElement(t.getCardNumber());		
			}
			jComboBoxTarjetas.repaint();
			
		} catch (NoCardsStored e1){
			desbloquearTarjeta(true);
		}	
	}
	
	public void desbloquearTarjeta(boolean bo) {

		lblErrorTarjetasVacias.setVisible(bo);
	}
}
