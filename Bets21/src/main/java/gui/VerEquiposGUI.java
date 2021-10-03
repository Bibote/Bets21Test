package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import domain.Equipo;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class VerEquiposGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame padreFrame;
	private JPanel contentPane;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();

	private String equipo;
	private Equipo eq1;
	private int temp;



	public VerEquiposGUI(JFrame padre, String equipo, int temp) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.equipo = equipo;
		this.temp = temp;
		try {
			Ini();
			this.padreFrame = padre;
			getContentPane().setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void returnFather() {
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}

	public void Ini() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Bets21");

		JButton btnVolverAtras = new JButton("Volver atras");
		btnVolverAtras.setBounds(298, 368, 125, 21);
		contentPane.add(btnVolverAtras);
		btnVolverAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});


		JLabel lblDatosEquipos = new JLabel("Datos del equipo");
		lblDatosEquipos.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosEquipos.setForeground(Color.BLACK);
		lblDatosEquipos.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		lblDatosEquipos.setBounds(10, 11, 181, 26);
		contentPane.add(lblDatosEquipos);

		JLabel lblDatosSede = new JLabel("Datos de la sede");
		lblDatosSede.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosSede.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		lblDatosSede.setBounds(225, 10, 183, 26);
		contentPane.add(lblDatosSede);

		JLabel lblEstadisticas = new JLabel("Estadisticas");
		lblEstadisticas.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstadisticas.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		lblEstadisticas.setBounds(117, 241, 181, 26);
		contentPane.add(lblEstadisticas);

		JLabel lblNombreEquipo = new JLabel("");
		lblNombreEquipo.setBounds(10, 48, 181, 14);
		contentPane.add(lblNombreEquipo);

		JLabel lblSede = new JLabel("");
		lblSede.setBounds(201, 47, 207, 14);
		contentPane.add(lblSede);

		JLabel lblTemporada = new JLabel("");
		lblTemporada.setBounds(10, 73, 181, 14);
		contentPane.add(lblTemporada);

		JLabel lblAforo = new JLabel("");
		lblAforo.setBounds(201, 72, 207, 14);
		contentPane.add(lblAforo);

		JLabel lblFundacion = new JLabel("");
		lblFundacion.setBounds(10, 98, 181, 14);
		contentPane.add(lblFundacion);

		JLabel lblPresidente = new JLabel("");
		lblPresidente.setBounds(10, 123, 181, 14);
		contentPane.add(lblPresidente);

		JLabel lblEntrenador = new JLabel("");
		lblEntrenador.setBounds(10, 148, 181, 14);
		contentPane.add(lblEntrenador);

		JLabel lblWeb = new JLabel("");
		lblWeb.setBounds(10, 173, 181, 14);
		contentPane.add(lblWeb);

		JLabel lblPartidosJugados = new JLabel("");
		lblPartidosJugados.setBounds(10, 273, 181, 14);
		contentPane.add(lblPartidosJugados);

		JLabel lblPartidosGanados = new JLabel("");
		lblPartidosGanados.setBounds(10, 298, 181, 14);
		contentPane.add(lblPartidosGanados);

		JLabel lblPartidosEmpatados = new JLabel("");
		lblPartidosEmpatados.setBounds(10, 323, 181, 14);
		contentPane.add(lblPartidosEmpatados);

		JLabel lblPartidosPerdidos = new JLabel("");
		lblPartidosPerdidos.setBounds(10, 348, 181, 14);
		contentPane.add(lblPartidosPerdidos);

		JLabel lblGanadosTot = new JLabel("");
		lblGanadosTot.setBounds(201, 298, 46, 14);
		contentPane.add(lblGanadosTot);

		JLabel lblEmpatadosTot = new JLabel("");
		lblEmpatadosTot.setBounds(201, 323, 46, 14);
		contentPane.add(lblEmpatadosTot);

		JLabel lblPerdidosTot = new JLabel("");
		lblPerdidosTot.setBounds(201, 348, 46, 14);
		contentPane.add(lblPerdidosTot);



		eq1 = facade.obtenerEquipo(equipo, temp);

		lblNombreEquipo.setText("Nombre: "+eq1.getNombre());
		lblSede.setText("Sede: "+eq1.getSede());
		lblTemporada.setText("Temporada: "+eq1.getTemporada());
		lblAforo.setText("Aforo: "+eq1.getAforo());
		lblFundacion.setText("Fundacion: "+eq1.getFundacion());
		lblPresidente.setText("Presidente: "+eq1.getPresidente());
		lblEntrenador.setText("Entrenador: "+eq1.getEntrenador());
		lblWeb.setText("Web: "+eq1.getWeb());
		double ganados = eq1.getEvGanados();
		double empatados = eq1.getEvEmpates();
		double perdidos = eq1.getEvPerdidos();
		double jugados =ganados+perdidos+empatados;
		
		lblPartidosJugados.setText("Partidos jugados: "+jugados);
		lblPartidosGanados.setText("Partidos ganados: "+ganados);
		lblPartidosEmpatados.setText("Partidos empatados: "+empatados);
		lblPartidosPerdidos.setText("Partidos perdidos: "+perdidos);
		
		try {
			
			double porcen = Math.round((ganados/jugados)*100);
			lblGanadosTot.setText( porcen + "%");
		}catch (ArithmeticException e1){
			lblGanadosTot.setText("");
		}
		try {
			double porcen1 = Math.round((empatados/jugados)*100);
			lblEmpatadosTot.setText(porcen1 + "%");
			
		}catch (ArithmeticException e1){
			lblEmpatadosTot.setText("");
		}
		try {
			double porcen2 = Math.round((perdidos/jugados) *100);
			lblPerdidosTot.setText(porcen2 + "%");
			
		}catch (ArithmeticException e1){
			lblPerdidosTot.setText("");

		}










	}
}
