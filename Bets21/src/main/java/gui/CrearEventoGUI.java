package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

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
import configuration.UtilDate;
import dependencies.DateLabelFormatter;
import domain.Equipo;
import exceptions.OldDateError;
import exceptions.StringIsEmpty;
import javax.swing.JButton;
import javax.swing.JTextField;

public class CrearEventoGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private Date fechaEvento;
	private JTextField textField;

	private DefaultTableModel tableModelEq1; 
	private JTable tableEq1;
	private JScrollPane scrollPaneEq1;


	private DefaultTableModel tableModelEq2; 
	private JTable tableEq2;
	private JScrollPane scrollPaneEq2;

	private Equipo eq1;
	private Equipo eq2;


	private String[] columnNamesEq1 = new String[] {
			"Equipo",
			"Temporada"

	};

	/**
	 * Create the frame.
	 */
	public CrearEventoGUI(JFrame padre) {
		try {
			jbInit();
			padreFrame = padre;
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



		setBackground(Color.LIGHT_GRAY);
		setTitle("Bets21");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(10, 49, 129, 20);
		contentPane.add(datePicker);



		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SEventDate"));
		lblNewLabel.setBounds(10, 24, 269, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(92, 81, 383, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);

		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SDate"));
		btnNewButton.setBounds(177, 46, 156, 23);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IDesc"));
		lblNewLabel_2.setBounds(10, 95, 564, 20);
		contentPane.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(10, 132, 564, 40);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		btnNewButton_1.setBounds(218, 440, 141, 23);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VDesc"));
		lblNewLabel_3.setBounds(10, 183, 269, 14);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		JLabel lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VDate"));
		lblNewLabel_4.setBounds(364, 49, 210, 20);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

		scrollPaneEq1 = new JScrollPane();
		scrollPaneEq1.setBounds(10, 252, 269, 146);
		contentPane.add(scrollPaneEq1);

		scrollPaneEq2 = new JScrollPane();
		scrollPaneEq2.setBounds(289, 252, 269, 146);
		contentPane.add(scrollPaneEq2);

		tableEq1 = new JTable();
		scrollPaneEq1.setViewportView(tableEq1);

		tableEq2 = new JTable();
		scrollPaneEq2.setViewportView(tableEq2);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					fechaEvento = facade.makeDate((Date) datePicker.getModel().getValue());
					lblNewLabel_1.setVisible(true);
					lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("SDateIS")+ fechaEvento);
					lblNewLabel_4.setVisible(false);

					tableModelEq1= new DefaultTableModel(null, columnNamesEq1);
					tableEq1.setModel(tableModelEq1);
					tableModelEq1.setDataVector(null, columnNamesEq1);
					tableModelEq1.setColumnCount(2);

					List<Equipo> equipo= facade.obtenerEquipos(fechaEvento.getYear()+1900);
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
								///

								tableModelEq2= new DefaultTableModel(null, columnNamesEq1);
								tableEq2.setModel(tableModelEq2);
								tableModelEq2.setDataVector(null, columnNamesEq1);
								tableModelEq2.setColumnCount(2);

								List<Equipo> equipo= facade.obtenerEquipos(2021);//fechaEvento.getYear()+1900);
								if(!equipo.isEmpty()) {
									for (domain.Equipo eq:equipo){

										if (!eq.getNombre().equals(eq1.getNombre())) {
											Vector<Object> row = new Vector<Object>();
											System.out.println("Equipo "+eq);
											row.add(eq.toString());
											row.add(eq); // eq object added in order to obtain it with tableModelEvents.getValueAt(i,2)
											tableModelEq2.addRow(row);	

										}

									}

									tableEq2.getColumnModel().getColumn(0).setPreferredWidth(25);
									tableEq2.getColumnModel().removeColumn(tableEq2.getColumnModel().getColumn(1));

									tableEq2.addMouseListener(new MouseAdapter() {
										@Override
										public void mouseClicked(MouseEvent e) {
											int i=tableEq2.getSelectedRow();
											eq2=(domain.Equipo)tableModelEq2.getValueAt(i,1); // obtain eq2 object
										}
									});

								}
							}
						});
					}

				} catch (NullPointerException e1) {
					lblNewLabel_4.setVisible(true);
				}
			}
		});

		btnNewButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				try {
					fechaEvento = facade.makeDate((Date) datePicker.getModel().getValue());
					facade.oldDate(fechaEvento);
					lblNewLabel_4.setVisible(false);
					String descripcion = textField.getText();
					
					if(descripcion.length()<0) throw new StringIsEmpty();
					lblNewLabel_3.setVisible(false);
					/**Event ev = new Event(descripcion,UtilDate.newDate(fechaEvento.getYear()+1900,fechaEvento.getMonth(),fechaEvento.getDate()));**/

					facade.createEvent(descripcion, UtilDate.newDate(fechaEvento.getYear()+1900,fechaEvento.getMonth(),fechaEvento.getDate()), eq1, eq2);
					returnFather();
				} catch (StringIsEmpty e1) {
					lblNewLabel_3.setVisible(true);

				} catch (NullPointerException | OldDateError e1) {
					lblNewLabel_4.setVisible(true);
				}
			}
		});

		JButton btnNewButton_2 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton_2.setBounds(454, 10, 122, 21);
		contentPane.add(btnNewButton_2);

		JLabel lblSeleccionaEquipos = new JLabel("Selecciona los dos equipos del evento");
		lblSeleccionaEquipos.setBounds(10, 222, 269, 14);
		contentPane.add(lblSeleccionaEquipos);



		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});

	}
}
