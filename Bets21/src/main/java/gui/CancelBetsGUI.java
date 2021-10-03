package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

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
import domain.Apuesta;
import domain.Event;
import domain.Question;
import exceptions.NotEnoughChuti;

public class CancelBetsGUI extends JFrame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;
	private JFrame mainFrame;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	
	private DefaultTableModel tableModelBets; 
	private JTable tableBets;
	private JScrollPane scrollPaneBets;


	private String[] columnNamesBets = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Event"),
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Bet"),
			ResourceBundle.getBundle("Etiquetas").getString("Amount")

	};

	/**
	 * Create the frame.
	 */
	public CancelBetsGUI(JFrame padre) {
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
		this.setSize(new Dimension(604, 370));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(168, 81, 383, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);

		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CancelBet"));
		lblNewLabel_2.setBounds(20, 10, 315, 20);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblBetCanceled = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetCanceled")); //$NON-NLS-1$ //$NON-NLS-2$
		lblBetCanceled.setBounds(350, 282, 129, 13);
		contentPane.add(lblBetCanceled);
		lblBetCanceled.setVisible(false);
		
		JLabel lblWarning = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CancelWarning"));
		lblWarning.setBounds(20, 268, 375, 13);
		contentPane.add(lblWarning);

		scrollPaneBets = new JScrollPane();
		scrollPaneBets.setBounds(20, 58, 531, 200);
		contentPane.add(scrollPaneBets);

		tableBets = new JTable();
		scrollPaneBets.setViewportView(tableBets);

		JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PressCancel"));

		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ViewBets"));
		btnNewButton.setBounds(217, 27, 238, 21);
		contentPane.add(btnNewButton);
		
		btnNewButton_1.setBounds(168, 300, 383, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setVisible(false);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					tableModelBets= new DefaultTableModel(null, columnNamesBets);
					tableBets.setModel(tableModelBets);
					tableModelBets.setDataVector(null, columnNamesBets);
					tableModelBets.setColumnCount(5);

				Vector<Apuesta> apuestas= facade.getBetsFromUserOpen();
				if(!apuestas.isEmpty()) {
					for (domain.Apuesta a:apuestas){

							Vector<Object> row = new Vector<Object>();
							System.out.println("Apuesta "+a);
							row.add(a.getEvento().toString());
							row.add(a.getPronostico().getQuestion().getQuestion());
							row.add(a.getPronostico().toString());
							row.add(a.getChutiGoles());
							row.add(a);
							tableModelBets.addRow(row);	
					}

					tableBets.getColumnModel().getColumn(0).setPreferredWidth(100);
					tableBets.getColumnModel().getColumn(1).setPreferredWidth(25);
					tableBets.getColumnModel().getColumn(2).setPreferredWidth(25);
					tableBets.getColumnModel().getColumn(3).setPreferredWidth(1);
					tableBets.getColumnModel().removeColumn(tableBets.getColumnModel().getColumn(4));

					tableBets.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int i=tableBets.getSelectedRow();
							Apuesta a=(domain.Apuesta)tableModelBets.getValueAt(i,4);

							btnNewButton_1.setVisible(true);
							
							btnNewButton_1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									facade.cancelarApuesta(a);
									lblBetCanceled.setVisible(true);
																		
								}
							});
	
							
						}
					});
					
				}

			}
		});


		JButton btnNewButton_2 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton_2.setBounds(465, 10, 111, 21);
		contentPane.add(btnNewButton_2);
		

		

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});




	}
}
