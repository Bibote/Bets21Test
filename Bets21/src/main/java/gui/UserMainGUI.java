package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import business.logic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UserMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton botonVerApuestas = null;

	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnEnglish;
	private JRadioButton rdbtnEuskara;
	private JRadioButton rdbtnCastellano;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JMenuBar menuBar;

	private JButton botonPanelUsuario;

	private JFrame mainGui;
	private JFrame padreFrame;
	private JButton btnLogOut;
	private JButton botonBuscarEvento;
	private JButton btnCancelarApuesta;
	private JButton boletos;

	/**
	 * This is the default constructor
	 */
	public UserMainGUI(JFrame padreFrame) {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.padreFrame = padreFrame;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}



	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle("---User-Bets21---");
		this.mainGui = this;
		setTitle("Bets21");
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBotonVerApuestas());
			jContentPane.add(getPanel());
			jContentPane.add(getbotonBuscarEvento());
			jContentPane.add(getBtnCancelarApuesta());
			jContentPane.add(getBoletos());
			setJMenuBar(getMenuBar_1());

		}
		return jContentPane;
	}




	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 26, 479, 37);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	
	private JRadioButton getRdbtnEnglish() {
		if (rdbtnEnglish == null) {
			rdbtnEnglish = new JRadioButton("English");
			rdbtnEnglish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnEnglish);
		}
		return rdbtnEnglish;
	}
	
	private JRadioButton getRdbtnEuskara() {
		if (rdbtnEuskara == null) {
			rdbtnEuskara = new JRadioButton("Euskara");
			rdbtnEuskara.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnEuskara);
		}
		return rdbtnEuskara;
	}
	
	private JRadioButton getRdbtnCastellano() {
		if (rdbtnCastellano == null) {
			rdbtnCastellano = new JRadioButton("Castellano");
			rdbtnCastellano.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnCastellano);
		}
		return rdbtnCastellano;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 187, 479, 62);
			panel.add(getRdbtnEuskara());
			panel.add(getRdbtnCastellano());
			panel.add(getRdbtnEnglish());
		}
		return panel;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		botonVerApuestas.setText(ResourceBundle.getBundle("Etiquetas").getString("ViewBets"));
		botonBuscarEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("SearchEvent"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		botonBuscarEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateApuesta"));
		boletos.setText(ResourceBundle.getBundle("Etiquetas").getString("UserMainGUI.btnNewButton.text"));
		btnCancelarApuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("UserMainGUI.btnCancelarApuesta.text"));
	}
	
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();

			botonPanelUsuario = new JButton("UserPanel");
			botonPanelUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PanelUsuarioGUI paUser = new PanelUsuarioGUI(mainGui);
					paUser.setVisible(true);
					setVisible(false);
				}
			});
			menuBar.add(botonPanelUsuario);
			
			menuBar.add(getBtnLogOut());
			
			JButton btnPagos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MadePayments")); //$NON-NLS-1$ //$NON-NLS-2$
			btnPagos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ViewPaymentsGUI viewpa = new ViewPaymentsGUI(mainGui);
					viewpa.setVisible(true);
					setVisible(false);
				}
			});
			menuBar.add(btnPagos);


		}
		return menuBar;
	}
	

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBotonVerApuestas() {
		if (botonVerApuestas == null) {
			botonVerApuestas = new JButton();
			botonVerApuestas.setBounds(0, 63, 240, 62);
			botonVerApuestas.setText(ResourceBundle.getBundle("Etiquetas").getString("ViewBets"));
			botonVerApuestas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ViewBetsGUI a = new ViewBetsGUI(mainGui); 
					setVisible(false);
					a.setVisible(true);
				}
			});
		}
		return botonVerApuestas;
	}
	

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnLogOut.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					facade.logOutUser();
					returnFather();
					//bloquearBotones(false);
					//bloquearSignUp(true);
				}
			});
		}
		return btnLogOut;
	}
	
	private JButton getbotonBuscarEvento() {
		if (botonBuscarEvento == null) {
			botonBuscarEvento = new JButton();
			botonBuscarEvento.setBounds(239, 63, 240, 62);
			botonBuscarEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateApuesta"));
			botonBuscarEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CreateApuestaGUI a = new CreateApuestaGUI(mainGui); 
					setVisible(false);
					a.setVisible(true);
				
				}
			});
		}
		return botonBuscarEvento;
	}
	
	

	private JButton getBtnCancelarApuesta() {
		if (btnCancelarApuesta == null) {
			btnCancelarApuesta = new JButton();
			btnCancelarApuesta.setBounds(0, 122, 240, 62);
			btnCancelarApuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("UserMainGUI.btnCancelarApuesta.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnCancelarApuesta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CancelBetsGUI a = new CancelBetsGUI(mainGui); 
					setVisible(false);
					a.setVisible(true);
				
				}
			});
		}
		return btnCancelarApuesta;
	}
	private JButton getBoletos() {
		if (boletos == null) {
			boletos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserMainGUI.btnNewButton.text"));
			boletos.setBounds(239, 122, 240, 62);
			boletos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CanjearBoletoGUI a = new CanjearBoletoGUI(mainGui); 
					setVisible(false);
					a.setVisible(true);
				}
			});
		}
		return boletos;
	}
} // @jve:decl-index=0:visual-constraint="0,0"