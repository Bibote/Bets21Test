package gui;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.logic.BLFacade;
import domain.Pronosticos;
import domain.Question;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PronosticosGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;
	private int q;
	private int ev;

	private JTable tablePronostico = new JTable();
	private DefaultTableModel tableModelPronostico;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private String[] columnNamesPronostico = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Number"), 
			ResourceBundle.getBundle("Etiquetas").getString("Forecast"),
			ResourceBundle.getBundle("Etiquetas").getString("Percentage"),

	};

	/**
	 * Launch the application.
	 */

	public PronosticosGUI(JFrame padre, int ev, int q) {
		try {
			padreFrame = padre;
			this.q =q;
			this.ev = ev;

			jbInit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}

	private void jbInit(){


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");

		JScrollPane scrollPanelPronostic = new JScrollPane();
		scrollPanelPronostic.setBounds(new Rectangle(10, 53, 332, 138));
		contentPane.add(scrollPanelPronostic);

		scrollPanelPronostic.setViewportView(tablePronostico);
		tableModelPronostico = new DefaultTableModel(null, columnNamesPronostico);

		Question question = facade.obtainQuestion(ev, q);
		JLabel jFrameLabelPregunta = new JLabel();
		jFrameLabelPregunta.setText(ResourceBundle.getBundle("Etiquetas").getString("PossibleForecasts")+question.getQuestion());
		jFrameLabelPregunta.setBounds(10, 28, 419, 14);
		contentPane.add(jFrameLabelPregunta);

		for(Pronosticos pronostico: question.getPronosticos()) {
			Vector<Object> row = new Vector<Object>();
			row.add(pronostico.getPrognosticNumber());
			row.add(pronostico.toString());
			row.add(pronostico.getPorcentaje());
			tableModelPronostico.addRow(row);
		}



		tablePronostico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tablePronostico.getSelectedRow();

				System.out.println(i);

			}
		});


		tablePronostico.setModel(tableModelPronostico);

		JButton btnCrearPronostico = new JButton(ResourceBundle.getBundle("Etiquetas").getString("NewForecast"));

		btnCrearPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Question q1;
				if((q1 = facade.obtainQuestion(ev, q)).isEquipo()) {
					CreatePronosticosConEquipoGUI crearPronos = new CreatePronosticosConEquipoGUI(padreFrame, ev, q);
					crearPronos.setVisible(true);
				}else {
					CreatePronosticosGUI crearPronos = new CreatePronosticosGUI(padreFrame, ev, q);
					crearPronos.setVisible(true);
				}
				
				setVisible(false);
				dispose();
			}
		});
		btnCrearPronostico.setBounds(258, 196, 171, 23);
		contentPane.add(btnCrearPronostico);
		tablePronostico.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablePronostico.getColumnModel().getColumn(1).setPreferredWidth(268);
		tablePronostico.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));

		btnNewButton.setBounds(312, 0, 122, 21);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});









	}
}
