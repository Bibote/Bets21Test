package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.logic.BLFacade;
import domain.Equipo;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class EditarEquipoGUI extends JFrame {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame padreFrame;
	private JPanel contentPane;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private DefaultTableModel tableModelEq1; 
	private JTable tableEq1;
	private JScrollPane scrollPaneEq1;

	private String[] columnNamesEq1 = new String[] {
			"Equipo",
			"Temporada"

	};

	private Equipo eq1;
	private int temp;
	private JTextField textTemporada;




	public EditarEquipoGUI(JFrame padre) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Ini();
			this.padreFrame = padre;
			getContentPane().setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}


	public void Ini() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");

		JButton btnNewButton_2 = new JButton("Volver atras");
		btnNewButton_2.setBounds(298, 368, 125, 21);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});

		scrollPaneEq1 = new JScrollPane();
		scrollPaneEq1.setBounds(75, 87, 269, 146);
		contentPane.add(scrollPaneEq1);

		tableEq1 = new JTable();
		scrollPaneEq1.setViewportView(tableEq1);

		JLabel lblTemporada = new JLabel("Temporada");
		lblTemporada.setBounds(10, 11, 93, 14);
		getContentPane().add(lblTemporada);

		textTemporada = new JTextField();
		textTemporada.setBounds(113, 8, 64, 20);
		getContentPane().add(textTemporada);
		textTemporada.setColumns(10);

		JLabel lblNumeros = new JLabel("Temporada tiene que ser numero entero");
		lblNumeros.setBounds(10, 50, 314, 14);
		contentPane.add(lblNumeros);
		lblNumeros.setVisible(false);
		

		JLabel lblResultadoActual = new JLabel("New label");
		lblResultadoActual.setBounds(10, 346, 356, 14);
		contentPane.add(lblResultadoActual);
		lblResultadoActual.setVisible(false);

		JButton btnAnadirVictoria = new JButton("Agregar Victoria");
		btnAnadirVictoria.setBounds(29, 244, 167, 23);
		contentPane.add(btnAnadirVictoria);
		btnAnadirVictoria.setVisible(false);
		btnAnadirVictoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eq1 = facade.editarPartidosEquipo(eq1, 0);
				lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");
			}
		});

		JButton btnAnadirEmpate = new JButton("Agregar Empate");
		btnAnadirEmpate.setBounds(29, 278, 167, 23);
		contentPane.add(btnAnadirEmpate);
		btnAnadirEmpate.setVisible(false);
		btnAnadirEmpate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eq1 = facade.editarPartidosEquipo(eq1, 1);
				lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");

			}
		});

		JButton btnAnadirDerrota = new JButton("Agregar Derrota");
		btnAnadirDerrota.setBounds(29, 312, 167, 23);
		contentPane.add(btnAnadirDerrota);
		btnAnadirDerrota.setVisible(false);
		btnAnadirDerrota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eq1 = facade.editarPartidosEquipo(eq1, 2);
				lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");

			}
		});

		JButton btnEliminarVictoria = new JButton("Eliminar Victoria");
		btnEliminarVictoria.setBounds(218, 244, 167, 23);
		contentPane.add(btnEliminarVictoria);
		btnEliminarVictoria.setVisible(false);
		btnEliminarVictoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (eq1.getEvGanados()>0) {
				eq1 = facade.editarPartidosEquipo(eq1, 3);
				}
				lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");

			}
		});

		JButton btnEliminarEmpate = new JButton("Eliminar Empate");
		btnEliminarEmpate.setBounds(218, 278, 167, 23);
		contentPane.add(btnEliminarEmpate);
		btnEliminarEmpate.setVisible(false);
		btnEliminarEmpate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(eq1.getEvEmpates()>0) {
				eq1 = facade.editarPartidosEquipo(eq1, 4);
				}
				lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");

			}
		});

		JButton btnEliminarDerrota = new JButton("Eliminar Derrota");
		btnEliminarDerrota.setBounds(218, 312, 167, 23);
		contentPane.add(btnEliminarDerrota);
		btnEliminarDerrota.setVisible(false);
		btnEliminarDerrota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (eq1.getEvPerdidos()>0) {
				eq1 = facade.editarPartidosEquipo(eq1, 5);
				}
				lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");

			}
		});


		JButton btnSeleccionarTemporada = new JButton("Seleccionar");
		btnSeleccionarTemporada.setBounds(199, 7, 125, 23);
		getContentPane().add(btnSeleccionarTemporada);
		btnSeleccionarTemporada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					btnAnadirVictoria.setVisible(false);
					btnAnadirEmpate.setVisible(false);
					btnAnadirDerrota.setVisible(false);
					btnEliminarVictoria.setVisible(false);
					btnEliminarEmpate.setVisible(false);
					btnEliminarDerrota.setVisible(false);
					lblNumeros.setVisible(false);
					temp = Integer.parseInt(textTemporada.getText());
					

					tableModelEq1= new DefaultTableModel(null, columnNamesEq1);
					tableEq1.setModel(tableModelEq1);
					tableModelEq1.setDataVector(null, columnNamesEq1);
					tableModelEq1.setColumnCount(2);

					List<Equipo> equipo= facade.obtenerEquipos(temp);
					if(!equipo.isEmpty()) {
						for (domain.Equipo eq:equipo){


							Vector<Object> row = new Vector<Object>();
							System.out.println("Equipo "+eq);
							row.add(eq.toString());
							row.add(eq); // eq object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEq1.addRow(row);	

						}

						tableEq1.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEq1.getColumnModel().removeColumn(tableEq1.getColumnModel().getColumn(1));

						tableEq1.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								int i=tableEq1.getSelectedRow();
								eq1=(domain.Equipo)tableModelEq1.getValueAt(i,1); // obtain eq1 object
								System.out.println(eq1.getNombre());

								btnAnadirVictoria.setVisible(true);
								btnAnadirEmpate.setVisible(true);
								btnAnadirDerrota.setVisible(true);
								btnEliminarVictoria.setVisible(true);
								btnEliminarEmpate.setVisible(true);
								btnEliminarDerrota.setVisible(true);
								
								lblResultadoActual.setVisible(true);
								lblResultadoActual.setText("El equipo consta de: "+eq1.getEvGanados()+" Ganados, "+ eq1.getEvEmpates() +" Empatados, " + eq1.getEvPerdidos()+ " Perdidos.");

							}


						});

					}
				}catch (NumberFormatException e1) {
					lblNumeros.setVisible(true);
				}
			}
		});





	}
}
