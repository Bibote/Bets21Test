package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class BanMessageGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	private JFrame padreFrame;

	private void returnFather() 
	{
		padreFrame.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	public BanMessageGUI(JFrame padre, String m) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			suInit(m);
			this.padreFrame = padre;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void suInit(String message) 
	{
		setTitle("Bets21");
		getContentPane().setLayout(null);
		setBounds(100, 100, 506, 339);
		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnBack.setBounds(391, 266, 89, 23);
		getContentPane().add(btnBack);
		JTextArea textAreaBan = new JTextArea(ResourceBundle.getBundle("Etiquetas").getString("BExpl") + message);
		textAreaBan.setBounds(10, 11, 470, 250);
		getContentPane().add(textAreaBan);
		btnBack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				returnFather();
			}
		});
	}
}
