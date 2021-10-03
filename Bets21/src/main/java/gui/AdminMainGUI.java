package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import business.logic.BLFacade;
import domain.Event;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class AdminMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	private JRadioButton rdbtnEnglish;
	private JRadioButton rdbtnEuskara;
	private JRadioButton rdbtnCastellano;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();


	private JButton botonPanelUsuario;
	private JButton btnDeleteUser;
	private JFrame mainGui;
	private JFrame padreFrame;
	private JButton btnLogOut;
	private JButton botonBuscarEvento;
	private JButton botonCrearEvento;
	private JButton boleto;

	private JButton btnCerrarEvento;
	private JButton btnCrearEquipo;
	private JButton getBtnEditarEquipo;


	/**
	 * This is the default constructor
	 */
	public AdminMainGUI(JFrame padreFrame) {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.padreFrame = padreFrame;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
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
		
		JMenuBar menuBarra = new JMenuBar();
		setJMenuBar(menuBarra);
		menuBarra.add(getBotonPanelUsuario());
		menuBarra.add(getBtnLogOut());
		this.setContentPane(getJContentPane());
		setTitle("Bets21");
		this.mainGui = this;
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			
			btnCerrarEvento = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));
			btnCerrarEvento.setBounds(8, 57, 157, 41);
			btnCerrarEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CerrarEventoGUI paUser = new CerrarEventoGUI(mainGui);
					paUser.setVisible(true);
					setVisible(false);
				}
			});
			jContentPane.setLayout(null);
			jContentPane.add(getQueryQueries());
			jContentPane.add(getCreateQuery());
			jContentPane.add(getbotonBuscarEvento());
			jContentPane.add(getbotonCrearEvento());
			jContentPane.add(getBoleto());
			jContentPane.add(btnCerrarEvento);
			jContentPane.add(getBtnDeleteUser());
			jContentPane.add(getPanel());
			jContentPane.add(getBtnCrearEquipo());
			jContentPane.add(getBtnEditarEquipo());


		}
		return jContentPane;
	}


	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCreateQuery() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(170, 5, 147, 41);
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));

			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>(), mainGui);
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonCreateQuery;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getQueryQueries() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(10, 5, 157, 41);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI(mainGui); 
					setVisible(false);
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
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
			panel.setBounds(10, 182, 459, 33);
			panel.add(getRdbtnEuskara());
			panel.add(getRdbtnCastellano());
			panel.add(getRdbtnEnglish());
		}
		return panel;
	}

	private void redibujar() {
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		botonBuscarEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("UpdateEvent"));
		botonCrearEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		btnCrearEquipo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateTeam"));
		btnDeleteUser.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnDeleteUser.text"));
		btnLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnLogOut.text"));
		boleto.setText(ResourceBundle.getBundle("Etiquetas").getString("CrearBoleto"));
		btnCerrarEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));
		getBtnEditarEquipo.setText(ResourceBundle.getBundle("Etiquetas").getString("EditTeam"));
	}


	

	
	
	
	
	
	private JButton getBtnDeleteUser() {
		if (btnDeleteUser == null) {
			btnDeleteUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnDeleteUser.text"));
			btnDeleteUser.setBounds(10, 107, 157, 41);
			btnDeleteUser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DeleteUserGUI dUser = new DeleteUserGUI(mainGui);
					setVisible(false);
					dUser.setVisible(true);
				}
			});
		}
		return btnDeleteUser;
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
			botonBuscarEvento = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SearchEvent"));
			botonBuscarEvento.setBounds(327, 5, 142, 41);
			botonBuscarEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					QueryEventGUI buscarEvento = new QueryEventGUI(mainGui);
					setVisible(false);
					buscarEvento.setVisible(true);

				}
			});
		}
		return botonBuscarEvento;
	}
	private JButton getbotonCrearEvento() {
		if (botonCrearEvento == null) {
			botonCrearEvento = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
			botonCrearEvento.setBounds(170, 57, 147, 41);
			botonCrearEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CrearEventoGUI crearEvento = new CrearEventoGUI(mainGui);
					setVisible(false);
					crearEvento.setVisible(true);
				}
			});
		}

		return botonCrearEvento;
	}
	
	private JButton getBoleto() {
		if (boleto == null) {
			boleto = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CrearBoleto"));
			boleto.setBounds(327, 57, 142, 41);
			boleto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CrearBoletosGUI c = new CrearBoletosGUI(mainGui);
					setVisible(false);
					c.setVisible(true);
				}
			});
		}
		return boleto;
	}
	private JButton getBtnCrearEquipo() {
		if (btnCrearEquipo == null) {
			btnCrearEquipo = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateTeam"));
			btnCrearEquipo.setBounds(170, 109, 147, 39);
			btnCrearEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CrearEquipoGUI e = new CrearEquipoGUI(mainGui);
					setVisible(false);
					e.setVisible(true);
				}
			});
		}
		return btnCrearEquipo;
	}
	
	private JButton getBtnEditarEquipo() {
		if (getBtnEditarEquipo == null) {
			getBtnEditarEquipo = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EditTeam"));
			getBtnEditarEquipo.setBounds(327, 109, 147, 39);
			getBtnEditarEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					EditarEquipoGUI e = new EditarEquipoGUI(mainGui);
					setVisible(false);
					e.setVisible(true);
				}
			});
		}
		return getBtnEditarEquipo;
	}
	private JButton getBotonPanelUsuario() {
		if (botonPanelUsuario == null) {
			botonPanelUsuario = new JButton("UserPanel");
		}
		return botonPanelUsuario;
	}

}
 // @jve:decl-index=0:visual-constraint="0,0"

