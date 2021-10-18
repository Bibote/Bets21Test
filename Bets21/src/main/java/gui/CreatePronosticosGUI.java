package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import domain.Question;
import exceptions.PrognosticAlreadyExist;
import exceptions.StringIsEmpty;
import exceptions.WrongParameters;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CreatePronosticosGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;

	private int q;
	private int ev;
	private JTextField textFieldPronostico;
	private JTextField textFieldPorcentaje;
	private JLabel jFrameLabelErrorCamposl;
	private JLabel jFrameLabelErrorNums;
	
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	public CreatePronosticosGUI(JFrame padre, int ev, int q) {
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

		JLabel jFLabelPronostico = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Forecast"));
		jFLabelPronostico.setBounds(10, 68, 158, 20);
		contentPane.add(jFLabelPronostico);

		JLabel jFlabelPorcentaje = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Percentage"));
		jFlabelPorcentaje.setBounds(10, 114, 158, 20);
		contentPane.add(jFlabelPorcentaje);

		textFieldPronostico = new JTextField();
		textFieldPronostico.setBounds(135, 68, 131, 20);
		contentPane.add(textFieldPronostico);
		textFieldPronostico.setColumns(10);

		textFieldPorcentaje = new JTextField();
		textFieldPorcentaje.setBounds(135, 114, 131, 20);
		contentPane.add(textFieldPorcentaje);
		textFieldPorcentaje.setColumns(10);

		JButton btnCrearPronos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("NewForecast"));
		btnCrearPronos.setBounds(102, 188, 216, 23);
		contentPane.add(btnCrearPronos);

		jFrameLabelErrorCamposl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FillFields"));
		jFrameLabelErrorCamposl.setBounds(276, 71, 131, 14);
		contentPane.add(jFrameLabelErrorCamposl);
		jFrameLabelErrorCamposl.setVisible(false);

		jFrameLabelErrorNums = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("OnlyIntegers"));
		jFrameLabelErrorNums.setBounds(276, 117, 131, 14);
		contentPane.add(jFrameLabelErrorNums);
		jFrameLabelErrorNums.setVisible(false);

		btnCrearPronos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String pronostico = textFieldPronostico.getText();
				String porcentaje = textFieldPorcentaje.getText();
				jFrameLabelErrorCamposl.setVisible(false);
				jFrameLabelErrorNums.setVisible(false);
				try {
					
					if(pronostico.length()<0) throw new StringIsEmpty();
					if(porcentaje.length()<0) throw new StringIsEmpty();
					float porcen = Float.parseFloat(porcentaje);
					Question question = facade.obtainQuestion(ev, q);
					try {
						facade.createPrognostic(ev,question, pronostico, porcen, null);
					} catch (WrongParameters e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					returnFather();
				} catch (StringIsEmpty e1) {
					jFrameLabelErrorCamposl.setVisible(true);
				} catch (NumberFormatException e1) {
					jFrameLabelErrorNums.setVisible(true);
				} catch (PrognosticAlreadyExist e1) {

				}

			}
		});
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
