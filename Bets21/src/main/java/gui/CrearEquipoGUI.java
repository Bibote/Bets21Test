package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.persistence.RollbackException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.logic.BLFacade;
import domain.Equipo;
import exceptions.EmptyNames;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CrearEquipoGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame padreFrame;
	private JPanel contentPane;
	private BLFacade facade = PublicMainGUI.getBusinessLogic();
	private JTextField textNombre;
	private JTextField textTemporada;
	private JTextField textFundacion;
	private JTextField textSede;
	private JTextField textAforo;
	private JTextField textPresidente;
	private JTextField textEntrenador;
	private JTextField textWeb;
	
	
	public CrearEquipoGUI(JFrame padre) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Ini();
			this.padreFrame = padre;
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
		
		JButton btnNewButton_2 = new JButton("Volver atras");
		btnNewButton_2.setBounds(334, 8, 90, 21);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFather();
			}
		});
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 67, 80, 14);
		contentPane.add(lblNombre);
		
		JLabel lblTemporada = new JLabel("Temporada");
		lblTemporada.setBounds(10, 92, 80, 14);
		contentPane.add(lblTemporada);
		
		JLabel lblFundacion = new JLabel("Fundacion");
		lblFundacion.setBounds(10, 117, 80, 14);
		contentPane.add(lblFundacion);
		
		JLabel lblSede = new JLabel("Sede");
		lblSede.setBounds(10, 142, 80, 14);
		contentPane.add(lblSede);
		
		JLabel lblAforo = new JLabel("Aforo");
		lblAforo.setBounds(10, 167, 80, 14);
		contentPane.add(lblAforo);
		
		JLabel lblPresidente = new JLabel("Presidente");
		lblPresidente.setBounds(10, 192, 80, 14);
		contentPane.add(lblPresidente);
		
		JLabel lblEntrenador = new JLabel("Entrenador");
		lblEntrenador.setBounds(10, 217, 80, 14);
		contentPane.add(lblEntrenador);
		
		JLabel lblWeb = new JLabel("Pagina Web\r\n");
		lblWeb.setBounds(10, 242, 80, 14);
		contentPane.add(lblWeb);
		
		textNombre = new JTextField();
		textNombre.setBounds(80, 64, 86, 20);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textTemporada = new JTextField();
		textTemporada.setBounds(80, 92, 86, 20);
		contentPane.add(textTemporada);
		textTemporada.setColumns(10);
		
		textFundacion = new JTextField();
		textFundacion.setBounds(80, 117, 86, 20);
		contentPane.add(textFundacion);
		textFundacion.setColumns(10);
		
		textSede = new JTextField();
		textSede.setBounds(80, 142, 86, 20);
		contentPane.add(textSede);
		textSede.setColumns(10);
		
		textAforo = new JTextField();
		textAforo.setBounds(80, 167, 86, 20);
		contentPane.add(textAforo);
		textAforo.setColumns(10);
		
		textPresidente = new JTextField();
		textPresidente.setBounds(80, 192, 86, 20);
		contentPane.add(textPresidente);
		textPresidente.setColumns(10);
		
		textEntrenador = new JTextField();
		textEntrenador.setBounds(80, 217, 86, 20);
		contentPane.add(textEntrenador);
		textEntrenador.setColumns(10);
		
		textWeb = new JTextField();
		textWeb.setBounds(80, 242, 86, 20);
		contentPane.add(textWeb);
		textWeb.setColumns(10);
		
		JButton btnCrearEquipo = new JButton("Crear Equipo");
		btnCrearEquipo.setBounds(126, 366, 180, 23);
		contentPane.add(btnCrearEquipo);
		
		JLabel lblRellenaTodos = new JLabel("Tienes que rellenar todos los campos");
		lblRellenaTodos.setBounds(126, 325, 248, 14);
		contentPane.add(lblRellenaTodos);
		lblRellenaTodos.setVisible(false);
		
		JLabel lblNumeros = new JLabel("Temporada, Fundacion y Aforo tienen que ser numeros enteros");
		lblNumeros.setBounds(10, 300, 414, 14);
		contentPane.add(lblNumeros);
		lblNumeros.setVisible(false);
		
		JLabel lblYaCreado = new JLabel("El equipo ya esta creado");
		lblYaCreado.setBounds(199, 67, 208, 14);
		contentPane.add(lblYaCreado);
		lblYaCreado.setVisible(false);
		
		btnCrearEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String inputName = textNombre.getText();
				String inputTemporada = textTemporada.getText();
				String inputFundacion = textFundacion.getText();
				String inputSede = textSede.getText();
				String inputAforo = textAforo.getText();
				String inputPresidente = textPresidente.getText();
				String inputEntrenador = textEntrenador.getText();
				String inputWeb = textWeb.getText();
				
				try {
					lblYaCreado.setVisible(false);
					lblRellenaTodos.setVisible(false);
					lblNumeros.setVisible(false);
					
					if(inputName.isEmpty()) {throw new EmptyNames();}
					if(inputTemporada.isEmpty()) {throw new EmptyNames();}
					int temporada = Integer.parseInt(inputTemporada);
					if(inputFundacion.isEmpty()) {throw new EmptyNames();}
					int fundacion = Integer.parseInt(inputFundacion);
					if(inputSede.isEmpty()) {throw new EmptyNames();}
					if(inputAforo.isEmpty()) {throw new EmptyNames();}
					int aforo = Integer.parseInt(inputAforo);
					if(inputPresidente.isEmpty()) {throw new EmptyNames();}
					if(inputEntrenador.isEmpty()) {throw new EmptyNames();}
					if(inputWeb.isEmpty()) {throw new EmptyNames();}
					
					
					Equipo eq1 = facade.crearEquipo(inputName, temporada, fundacion, inputSede, aforo, inputPresidente, inputEntrenador, inputWeb);
					returnFather();
				}catch  (EmptyNames e1) {
					lblRellenaTodos.setVisible(true);
				} catch (NumberFormatException e1) {
					lblNumeros.setVisible(true);
				}catch (RollbackException e1) {
					lblYaCreado.setVisible(true);
				}
				
			}
			
			
			
		});
		
		
		
	}
}
