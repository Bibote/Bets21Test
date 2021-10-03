package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import business.logic.BLFacade;
import configuration.UtilDate;
import domain.Apuesta;
import domain.Event;
import domain.Question;
import exceptions.CodigoRepetido;
import exceptions.StringIsEmpty;

import java.awt.Font;
import javax.swing.JCheckBox;

public class CreateApuestaGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JFrame padreFrame;
	private JFrame mainGui;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JComboBox<Question> jComboBoxQuestions= new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	private JTextField cantidad;
	private final JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Amount")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MakeBet")); //$NON-NLS-1$ //$NON-NLS-2$

	private JComboBox comboBoxPronosticos= new JComboBox<String>();
	DefaultComboBoxModel<String> modelPronosticos = new DefaultComboBoxModel<String>();

	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	
	private Event event;
	private final JLabel lblSeleccionaEquipo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateApuestaGUI.lblSeleccionaEquipo.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblSeleccionaUna = new JLabel("Selecciona un solo equipo");
	private final JLabel lblApuestaMin = new JLabel("Min: "); 

	public CreateApuestaGUI(JFrame padreGUI) {
		this.padreFrame = padreGUI;
		this.mainGui = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void returnFather() 
	{
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(670, 460));
		setTitle("Bets21");
		
		JLabel realizada = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Realizada")); //$NON-NLS-1$ //$NON-NLS-2$
		realizada.setFont(new Font("Tahoma", Font.PLAIN, 16));
		realizada.setBounds(374, 200, 214, 30);
		getContentPane().add(realizada);
		realizada.setVisible(false);
		
		JCheckBox chckbxEq1 = new JCheckBox("");
		chckbxEq1.setSelected(true);
		chckbxEq1.setBounds(40, 313, 153, 23);
		getContentPane().add(chckbxEq1);
		
		JCheckBox chckbxEq2 = new JCheckBox("");
		chckbxEq2.setBounds(193, 313, 187, 23);
		getContentPane().add(chckbxEq2);
		
		chckbxEq1.setVisible(false);
		chckbxEq2.setVisible(false);
		
		JButton btnVerEstadisticasEquipo = new JButton("Ver Estadisticas \r\n");
		btnVerEstadisticasEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblSeleccionaUna.setVisible(false);
				try {
				if (chckbxEq1.isSelected() && chckbxEq2.isSelected() || !chckbxEq1.isSelected() && !chckbxEq2.isSelected() ) {
					throw new CodigoRepetido();
				}else if(chckbxEq1.isSelected()) {
					VerEquiposGUI ve = new VerEquiposGUI(mainGui, event.getEq1().getNombre(),event.getEq1().getTemporada() );
					setVisible(false);
					ve.setVisible(true);
				
				}else if(chckbxEq2.isSelected()) {
					VerEquiposGUI ve = new VerEquiposGUI(mainGui, event.getEq2().getNombre(),event.getEq2().getTemporada() );
					setVisible(false);
					ve.setVisible(true);
				}
				
				}catch(CodigoRepetido e1) {
					lblSeleccionaUna.setVisible(true);
				}
			}
		});
		
		btnVerEstadisticasEquipo.setBounds(40, 346, 250, 23);
		getContentPane().add(btnVerEstadisticasEquipo);
		btnVerEstadisticasEquipo.setVisible(false);
		lblSeleccionaEquipo.setBounds(40, 292, 340, 14);
		lblSeleccionaEquipo.setVisible(false);
		
		
		
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					domain.Question question = ((domain.Question) jComboBoxQuestions.getSelectedItem());
					lblApuestaMin.setText("Min: "+question.getBetMinimum()+" �");

					Vector<domain.Pronosticos> pronosticos = facade.getPronosticosFromQuestion(question);

					if (pronosticos.isEmpty())
						jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
								+ ": " );
					else
						jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
								);
					comboBoxPronosticos.removeAllItems();
					for (domain.Pronosticos q : pronosticos) {
						modelPronosticos.addElement(q.toString());
						System.out.println(q);
					}


					jComboBoxQuestions.repaint();



				} catch (Exception e1) {
					System.out.println("esto no va");
				}


			}
		});
		jComboBoxQuestions.setModel(modelQuestions);
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					event = ((domain.Event) jComboBoxEvents.getSelectedItem());

					Vector<domain.Question> Questions = facade.getQuestionsFromEvent(event);

					if (Questions.isEmpty())
						jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
								+ ": " );
					else
						jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
								);
					jComboBoxQuestions.removeAllItems();
					for (domain.Question q : Questions) {
						if(q.isEstado()) {
						modelQuestions.addElement(q);
						System.out.println(q);
						}
					}


					jComboBoxQuestions.repaint();
					chckbxEq1.setVisible(true);
					chckbxEq2.setVisible(true);
					lblSeleccionaEquipo.setVisible(true);
					btnVerEstadisticasEquipo.setVisible(true);
					chckbxEq1.setText(event.getEq1().getNombre());
					chckbxEq2.setText(event.getEq2().getNombre());
					
					


				} catch (Exception e1) {
					System.out.println("esto no va");
				}
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(352, 52, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(367, 23, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setBounds(new Rectangle(329, 380, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);



		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);



		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);


		jComboBoxQuestions.setBounds(352, 154, 250, 22);
		getContentPane().add(jComboBoxQuestions);

		cantidad = new JTextField();

		cantidad.setBounds(439, 241, 86, 20);
		getContentPane().add(cantidad);
		cantidad.setColumns(10);
		lblNewLabel.setBounds(330, 244, 99, 14);

		getContentPane().add(lblNewLabel);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNewLabel_5.setVisible(false);
				domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
				try {
				String ganador=((String) comboBoxPronosticos.getSelectedItem());
				String dinerito=cantidad.getText();
				if (dinerito.isEmpty()) {
					throw new StringIsEmpty();
				}
				if (dinerito.contains(",")) {
					dinerito=dinerito.substring(0,dinerito.indexOf(','))+"."+dinerito.substring((dinerito.indexOf(',')+1), dinerito.length());
				}
				
				double dinero= Double.parseDouble(dinerito);

				Question q = (domain.Question) jComboBoxQuestions.getSelectedItem();
				
				if(ganador!=null && dinero<=facade.obtainCurrentUsr().getChutiGoles() && dinero>=q.getBetMinimum()) {

					Apuesta apuesta= new Apuesta(facade.obtainCurrentUsr(),facade.getPronostico(q, ganador), dinero, event,  q);
					facade.addApuesta(apuesta);
					realizada.setVisible(true);
					
				}else if (dinero>facade.obtainCurrentUsr().getChutiGoles()) {
					lblNewLabel_4.setVisible(true);
				}else if (dinero<((domain.Question) jComboBoxQuestions.getSelectedItem()).getBetMinimum()) {
					lblNewLabel_5.setVisible(true);
				}
				}catch(StringIsEmpty e1) {
					lblNewLabel_5.setVisible(true);
				}

			}
		});

		btnNewButton.setBounds(140, 380, 136, 30);

		getContentPane().add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ChooseQuestion")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_3.setBounds(367, 129, 235, 14);
		getContentPane().add(lblNewLabel_3);

		comboBoxPronosticos = new JComboBox();
		comboBoxPronosticos.setBounds(40, 236, 250, 22);
		getContentPane().add(comboBoxPronosticos);

		comboBoxPronosticos.setModel(modelPronosticos);
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MakeBet")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_1.setBounds(40, 211, 194, 14);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SinCG"));
		lblNewLabel_4.setBounds(50, 268, 184, 13);
		getContentPane().add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

		lblNewLabel_5 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BelowMin"));
		lblNewLabel_5.setBounds(280, 270, 322, 13);
		getContentPane().add(lblNewLabel_5);
		
		
		
		
		getContentPane().add(lblSeleccionaEquipo);
		lblSeleccionaUna.setBounds(300, 343, 214, 14);
		lblSeleccionaUna.setVisible(false);
		getContentPane().add(lblSeleccionaUna);
		lblApuestaMin.setBounds(329, 294, 273, 14);
		
		getContentPane().add(lblApuestaMin);
		

		

		lblNewLabel_5.setVisible(false);



		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
				//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);



						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {


						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events) {
							if (ev.isEstadoPublico()) {
								modelEvents.addElement(ev);
							}
						}
						jComboBoxEvents.repaint();




					} catch (Exception e1) {

					}





				}
			}
		});
	}


	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);
			System.out.println(d);



			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		returnFather();
	}
}