package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import domain.Equipo;
import domain.Question;
import exceptions.PreferencesNotChecked;
import exceptions.PrognosticAlreadyExist;
import exceptions.StringIsEmpty;
import exceptions.WrongParameters;

import javax.swing.JCheckBox;

public class CreatePronosticosConEquipoGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padreFrame;

	private int q;
	private int ev;
	private JTextField textFieldPorcentaje;
	private JLabel jFrameLabelErrorNums;

	private Equipo eq1;
	private Equipo eq2;

	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	public CreatePronosticosConEquipoGUI(JFrame padre, int ev, int q) {
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


		Vector<Equipo> equipos = facade.getEquipoFromQuestion(this.q);
		eq1 = equipos.firstElement();
		eq2 = equipos.lastElement();

		JCheckBox chckbxEq1 = new JCheckBox(eq1.getNombre());
		chckbxEq1.setBounds(10, 38, 97, 23);
		contentPane.add(chckbxEq1);
		chckbxEq1.setSelected(true);

		JCheckBox chckbxEq2 = new JCheckBox(eq2.getNombre());
		chckbxEq2.setBounds(10, 64, 97, 23);
		contentPane.add(chckbxEq2);

		JLabel lblElegirEquipo = new JLabel("Elige el equipo para definir el pronostico");
		lblElegirEquipo.setBounds(10, 17, 256, 14);
		contentPane.add(lblElegirEquipo);

		JLabel jFlabelPorcentaje = new JLabel("Introduce el porcentaje: ");
		jFlabelPorcentaje.setBounds(10, 114, 158, 20);
		contentPane.add(jFlabelPorcentaje);

		textFieldPorcentaje = new JTextField();
		textFieldPorcentaje.setBounds(154, 114, 97, 20);
		contentPane.add(textFieldPorcentaje);
		textFieldPorcentaje.setColumns(10);

		JButton btnCrearPronos = new JButton("Crear Pronostico");
		btnCrearPronos.setBounds(102, 188, 216, 23);
		contentPane.add(btnCrearPronos);

		jFrameLabelErrorNums = new JLabel("Introduzca solo numeros");
		jFrameLabelErrorNums.setBounds(276, 117, 148, 14);
		contentPane.add(jFrameLabelErrorNums);
		jFrameLabelErrorNums.setVisible(false);

		JLabel lblNoseleccionesDos = new JLabel("No puedes seleccionar los dos equipos");
		lblNoseleccionesDos.setBounds(154, 59, 270, 14);
		contentPane.add(lblNoseleccionesDos);
		lblNoseleccionesDos.setVisible(false);

		JLabel lblPronosticosCreados = new JLabel("El pronostico ya ha sido creado anteriormente");
		lblPronosticosCreados.setBounds(77, 163, 276, 14);
		contentPane.add(lblPronosticosCreados);
		lblPronosticosCreados.setVisible(false);

		JCheckBox chckbxEmpate = new JCheckBox("Empate\r\n");
		chckbxEmpate.setBounds(10, 90, 97, 23);
		contentPane.add(chckbxEmpate);


		btnCrearPronos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String porcentaje = textFieldPorcentaje.getText();

				jFrameLabelErrorNums.setVisible(false);
				try {
					jFrameLabelErrorNums.setVisible(false);
					lblPronosticosCreados.setVisible(false);
					lblNoseleccionesDos.setVisible(false);
					if(porcentaje.length()<0)throw new StringIsEmpty();
					float porcen = Float.parseFloat(porcentaje);
					Question question = facade.obtainQuestion(ev, q);
					if(chckbxEq1.isSelected() && chckbxEq2.isSelected() ) {
						throw  new PreferencesNotChecked();

					}else if(chckbxEmpate.isSelected() && !chckbxEq1.isSelected() && !chckbxEq2.isSelected()) {
						Question question1 = facade.obtainQuestion(ev, q);
						facade.createPrognostic(ev, question1, "Empate", porcen, null);
					}
					else if (chckbxEq1.isSelected() && !chckbxEmpate.isSelected()) {
						facade.createPrognostic(ev,question, null, porcen, eq1);
					}else if(chckbxEq2.isSelected() && !chckbxEmpate.isSelected()) {
						facade.createPrognostic(ev,question, null, porcen, eq2);
					}
					returnFather();
				} catch (NumberFormatException e1) {
					jFrameLabelErrorNums.setVisible(true);
				} catch (PrognosticAlreadyExist e1) {
					lblPronosticosCreados.setVisible(true);
				} catch (StringIsEmpty e1) {
					jFrameLabelErrorNums.setVisible(true);
				}catch (PreferencesNotChecked e1) {

					lblNoseleccionesDos.setVisible(true);

				} catch (WrongParameters e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		JButton btnNewButton = new JButton("Volver atras");
		btnNewButton.setBounds(312, 0, 122, 21);
		contentPane.add(btnNewButton);





		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});



	}
}
