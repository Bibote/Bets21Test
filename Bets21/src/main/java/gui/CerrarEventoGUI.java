package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import business.logic.BLFacade;
import dependencies.DateLabelFormatter;
import domain.Event;
import domain.Question;

public class CerrarEventoGUI extends JFrame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;
	private JFrame mainFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private Date fechaEventoPas;
	private Date fechaEventoHoy;

	
	private DefaultTableModel tableModelEvents; 
	private JTable tableEvents;
	private JScrollPane scrollPaneEvents;


	private DefaultTableModel tableModelQueries; 
	private JTable tableQueries;
	private JScrollPane scrollPaneQueries;

	String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")

	};


	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Event"),
			ResourceBundle.getBundle("Etiquetas").getString("Date")

	};

	
	private final JLabel jLabelQueries = new JLabel(); 

	/**
	 * Create the frame.
	 */
	public CerrarEventoGUI(JFrame padre) {
		try {
			jbInitialize();
			padreFrame = padre;
			mainFrame = this;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}

	public void jbInitialize(){



		setBackground(Color.LIGHT_GRAY);
		setTitle("Bets21");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.getContentPane().add(jLabelQueries);
		jLabelQueries.setBounds(10, 321, 406, 14);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(10, 49, 129, 20);
		contentPane.add(datePicker);

		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.today", "Hoy");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2,p2);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker2.setBounds(10, 75, 129, 20);
		contentPane.add(datePicker2);



		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SEventDate"));
		lblNewLabel.setBounds(10, 24, 269, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(168, 81, 383, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);

		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SDate"));
		btnNewButton.setBounds(177, 46, 156, 23);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelEvent"));
		lblNewLabel_2.setBounds(10, 95, 564, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VDate"));
		lblNewLabel_4.setBounds(364, 49, 210, 20);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

		scrollPaneEvents = new JScrollPane();
		scrollPaneEvents.setBounds(20, 126, 531, 146);
		contentPane.add(scrollPaneEvents);

		scrollPaneQueries = new JScrollPane();
		scrollPaneQueries.setBounds(20, 346, 531, 146);
		contentPane.add(scrollPaneQueries);

		tableEvents = new JTable();
		scrollPaneEvents.setViewportView(tableEvents);

		JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PRestrict"));


		btnNewButton_1.setBounds(168, 283, 383, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setVisible(false);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tableModelEvents= new DefaultTableModel(null, columnNamesEvents);
					tableEvents.setModel(tableModelEvents);
					tableModelEvents.setDataVector(null, columnNamesEvents);
					tableModelEvents.setColumnCount(3);

					fechaEventoPas = facade.makeDate((Date) datePicker.getModel().getValue());
					fechaEventoHoy = facade.makeDate((Date) datePicker2.getModel().getValue());
					lblNewLabel_1.setVisible(true);
					lblNewLabel_1.setText("Fechas seleccionadas");
					lblNewLabel_4.setVisible(false);
					if (fechaEventoPas.after(fechaEventoHoy)) {
						lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("FechaMen"));
						lblNewLabel_1.setVisible(false);
						throw new NullPointerException();

					}else {lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("VDate"));}

				} catch (NullPointerException e1) {
					lblNewLabel_4.setVisible(true);
				}

				Vector<Event> eventos= facade.getEventsBetweenDates(fechaEventoPas, fechaEventoHoy);
				if(!eventos.isEmpty()) {
					for (domain.Event ev:eventos){

						if (ev.isEstado()){//si esta abierto
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events "+ev);
							row.add(ev.toString());
							row.add(ev.getEventDate());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);	
						}
					}

					tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
					tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
					tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2));




					tableEvents.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int i=tableEvents.getSelectedRow();
							Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
							jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SEvent")+ev.getDescription());
							
							defineTableQueries(ev);
							btnNewButton_1.setVisible(true);
							
							btnNewButton_1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									facade.restringirEventoPublico(ev);
																		
								}
							});
	
							
						}
					});
					
				}

			}
		});


		JButton btnNewButton_2 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton_2.setBounds(454, 10, 122, 21);
		contentPane.add(btnNewButton_2);
		


		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});




	}
	
	private void defineTableQueries(Event ev) {
		tableQueries = new JTable();
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableQueries.setModel(tableModelQueries);
		tableModelQueries.setDataVector(null, columnNamesQueries);
		tableModelQueries.setColumnCount(3);

		JLabel lblNewLabel_2_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectQuestion"));
		lblNewLabel_2_1.setBounds(10, 304, 564, 20);
		contentPane.add(lblNewLabel_2_1);

		Vector<Question> questions= facade.getQuestionsFromEvent(ev);
		if(!questions.isEmpty()) {
			for (domain.Question q:questions){

				if (q.isEstado()){//si esta abierto
					Vector<Object> row = new Vector<Object>();
					System.out.println("Question "+q);
					row.add(q.getQuestion());
					row.add(q.getBetMinimum());
					row.add(q); 
					tableModelQueries.addRow(row);	
				}
			}

			tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
			tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2));
		}if (tableModelQueries.getRowCount() ==0) {
			facade.restringirEvento(ev);
			facade.restringirEventoPublico(ev);
		}
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableQueries.getSelectedRow();
				Question q = (domain.Question)tableModelQueries.getValueAt(i,2);
				FinalizarPronosticoGUI f = new FinalizarPronosticoGUI(q, mainFrame);
				mainFrame.setVisible(false);
				f.setVisible(true);
				
				
				

			}
		});
	}
	
	
}
