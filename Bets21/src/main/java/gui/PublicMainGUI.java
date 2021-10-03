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

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;


import com.toedter.calendar.JCalendar;

import business.logic.BLFacade;
import configuration.UtilDate;

import domain.Event;
import domain.Question;


import javax.swing.JMenuBar;

import javax.swing.JRadioButton;

public class PublicMainGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;

	}
	private JFrame MainGUI;
	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JLabel jLabelMsg = new JLabel();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JComboBox<Question> jComboBoxQuestions= new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();

	private JComboBox comboBoxPronosticos= new JComboBox<String>();
	DefaultComboBoxModel<String> modelPronosticos = new DefaultComboBoxModel<String>();
	private JLabel lblNewLabel_3;
	private final JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnSignUp.text")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnEnglish;
	private JRadioButton rdbtnEuskara;
	private JRadioButton rdbtnCastellano;
	private JLabel lblNewLabel;

	public PublicMainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnLogIn.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LogInGUI logIn = new LogInGUI(MainGUI);
				setVisible(false);
				logIn.setVisible(true);
			}
		});
		menuBar.add(btnNewButton);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SignUpGUI logIn = new SignUpGUI(MainGUI);
				setVisible(false);
				logIn.setVisible(true);
			}
		});
		
		menuBar.add(btnNewButton_1);
	
		//try {	jbInit();		} catch (Exception e) {	e.printStackTrace();}
	
	}

	public void jbInit() throws Exception {
		this.MainGUI=this;
		jComboBoxQuestions.setVisible(false);
		
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		setTitle("Bets21");
		
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					domain.Question Question = ((domain.Question) jComboBoxQuestions.getSelectedItem());

					Vector<domain.Pronosticos> pronosticos = appFacadeInterface.getPronosticosFromQuestion(Question);

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
				comboBoxPronosticos.setVisible(true);
				lblNewLabel.setVisible(true);



			}
		});
		jComboBoxQuestions.setModel(modelQuestions);
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					jComboBoxQuestions.setVisible(true);
					lblNewLabel_3.setVisible(true);
					domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

					Vector<domain.Question> Questions = appFacadeInterface.getQuestionsFromEvent(event);

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



				} catch (Exception e1) {
					System.out.println("esto no va");
				}
			}
		});
		

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);



		datesWithEventsCurrentMonth=appFacadeInterface.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);



		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);


		jComboBoxQuestions.setBounds(275, 103, 250, 22);
		getContentPane().add(jComboBoxQuestions);

		lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ChooseQuestion"));
		lblNewLabel_3.setBounds(290, 78, 235, 14);
		getContentPane().add(lblNewLabel_3);

		comboBoxPronosticos = new JComboBox();
		comboBoxPronosticos.setBounds(275, 169, 250, 22);
		getContentPane().add(comboBoxPronosticos);
		comboBoxPronosticos.setVisible(false);

		comboBoxPronosticos.setModel(modelPronosticos);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PublicMainGUI.lblNewLabel.text"));
		lblNewLabel.setBounds(290, 144, 164, 14);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setVisible(false);

		lblNewLabel_3.setVisible(false);

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



						datesWithEventsCurrentMonth=appFacadeInterface.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {


						Vector<domain.Event> events = appFacadeInterface.getEvents(firstDay);

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
}
