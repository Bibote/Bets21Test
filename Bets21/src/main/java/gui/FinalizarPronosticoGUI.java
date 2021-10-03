package gui;


import java.util.*;

import javax.swing.*;

import business.logic.BLFacade;

import java.awt.*;
import java.awt.event.*;

import domain.Pronosticos;
import domain.Question;


public class FinalizarPronosticoGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Pronosticos> jComboBoxPronosticos = new JComboBox<Pronosticos>();
	DefaultComboBoxModel<Pronosticos> modelPronosticos = new DefaultComboBoxModel<Pronosticos>();
	
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private JLabel jLabelListOfPronosticos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FinalizarPronosticoGUI.jLabelListOfPronosticos.text")); //$NON-NLS-1$ //$NON-NLS-2$


	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton("Definir Resultado");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	
	private JFrame padreFrame;

	public FinalizarPronosticoGUI(Question q, JFrame padre) {
		try {
			jbInit(q);
			this.padreFrame = padre;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		((CerrarEventoGUI) padreFrame).jbInitialize();
		dispose();
	}

	private void jbInit(Question q) throws Exception {
		
		for (Pronosticos p : q.getPronosticos()) {
			modelPronosticos.addElement(p);
		}

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		setTitle("Bets21");

		jComboBoxPronosticos.setModel(modelPronosticos);
		jComboBoxPronosticos.setBounds(new Rectangle(29, 45, 250, 20));
		jLabelListOfPronosticos.setBounds(new Rectangle(29, 14, 277, 20));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pronosticos p = (Pronosticos) modelPronosticos.getSelectedItem();
				if(p !=null) {
				facade.definirResultado(q, p);
				returnFather();	
				}
			}
		});

		jButtonCreate.setBounds(new Rectangle(100, 275, 165, 30));
		jButtonCreate.setEnabled(true);


		jButtonClose.setBounds(new Rectangle(275, 275, 158, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelListOfPronosticos, null);
		this.getContentPane().add(jComboBoxPronosticos, null);
		
		
		
	}

	



	private void jButtonClose_actionPerformed(ActionEvent e) {
		returnFather();
	}
}