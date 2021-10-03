package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import com.toedter.calendar.JCalendar;

import business.logic.BLFacade;
import configuration.UtilDate;
import dependencies.DateLabelFormatter;
import exceptions.OldDateError;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class QueryEventGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame padreFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private JLabel jLabelDescripcion;

	private JCheckBox chechBoxEliminar;
	private JDatePickerImpl datePicker;
	private Date fechaEvento;

	private domain.Event ev;

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();

	private DefaultTableModel tableModelEvents;
	@SuppressWarnings("unused")
	private DefaultTableModel tableModelQueries;


	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"),
			""

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private final JLabel jLabelActualizarCampos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryEventGUI.jLabelActualizarCampos.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jLabelFechaNueva = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryEventGUI.jLabelFechaNueva.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jLabelEliminar = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
	private final JTextField textFieldDescripcion = new JTextField();
	private final JButton btnActualizar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Update"));

	public QueryEventGUI(JFrame padre)
	{
		
		textFieldDescripcion.setText("");
		textFieldDescripcion.setBounds(202, 278, 259, 20);
		textFieldDescripcion.setColumns(10);
		try
		{
			jbInit();
			padreFrame = padre;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}


	private void jbInit() throws Exception
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		setTitle("Bets21");

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 228, 406, 14);
		jLabelEvents.setBounds(292, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		JButton btnNewButton_3 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton_3.setBounds(562, 0, 122, 21);
		this.getContentPane().add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});



		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

	
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));



					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);


						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);



					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(4); // another column added to allocate ev objects


						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev.getEq1().getNombre()+" VS "+ev.getEq2().getNombre());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(100);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(3)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(302, 50, 346, 150));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				ev=(domain.Event)tableModelEvents.getValueAt(i,3); // obtain ev object
				jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SEvent")+ev.toString());
				jLabelActualizarCampos.setVisible(true);

				jLabelFechaNueva.setVisible(true);
				jLabelEliminar.setVisible(true);
				jLabelDescripcion.setVisible(true);
				chechBoxEliminar.setVisible(true);
				btnActualizar.setVisible(true);
				datePicker.setVisible(true);

				textFieldDescripcion.setVisible(true);


			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		this.getContentPane().add(scrollPaneEvents, null);

		jLabelDescripcion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EDesc"));
		jLabelDescripcion.setBounds(65, 278, 140, 20);
		getContentPane().add(jLabelDescripcion);
		jLabelDescripcion.setVisible(false);

		jLabelActualizarCampos.setBounds(40, 253, 598, 14);
		jLabelActualizarCampos.setVisible(false);

		getContentPane().add(jLabelActualizarCampos);
		jLabelFechaNueva.setBounds(65, 305, 115, 20);
		jLabelFechaNueva.setVisible(false);

		getContentPane().add(jLabelFechaNueva);
		jLabelEliminar.setBounds(65, 330, 115, 20);
		jLabelEliminar.setVisible(false);

		getContentPane().add(jLabelEliminar);
		jLabelEliminar.setVisible(false);

		getContentPane().add(textFieldDescripcion);
		textFieldDescripcion.setVisible(false);

		chechBoxEliminar = new JCheckBox("");
		chechBoxEliminar.setBounds(201, 329, 97, 23);
		getContentPane().add(chechBoxEliminar);
		chechBoxEliminar.setVisible(false);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(202, 305, 129, 20);
		getContentPane().add(datePicker);
		btnActualizar.setBounds(274, 382, 130, 26);
		datePicker.setVisible(false);

		JLabel jFrameFechaValida = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VDate"));
		jFrameFechaValida.setBounds(341, 305, 210, 20);
		getContentPane().add(jFrameFechaValida);
		jFrameFechaValida.setVisible(false);

		getContentPane().add(btnActualizar);

		btnActualizar.setVisible(false);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chechBoxEliminar.isSelected()) {
						facade.deleteEvent(ev);
						returnFather();
					}
					fechaEvento = (Date) datePicker.getModel().getValue();
					if (fechaEvento !=null) {
						fechaEvento = facade.makeDate(fechaEvento);
						facade.oldDate(fechaEvento);
						@SuppressWarnings("deprecation")
						Date nueva = UtilDate.newDate(fechaEvento.getYear()+1900,fechaEvento.getMonth(),fechaEvento.getDate());
						facade.changeEventDate(ev, nueva);}

					if (textFieldDescripcion.getText().length()>0) {
						facade.changeEventDescription(ev, textFieldDescripcion.getText());
					}
					

					returnFather();
				} catch (OldDateError e1) {
					jFrameFechaValida.setVisible(true);
				}

			}	
		});

	}
}
