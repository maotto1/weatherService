package de.htw.ai.vs.weatherServiceClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.event.InputMethodListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.awt.event.InputMethodEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JTextPane;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private String message;
	private JSpinner spinner;
	private boolean changed = false;
	private ClientMain main;
	private JTextPane textPane;
	
	final Lock lock = new ReentrantLock();
	final Condition condition = lock.newCondition();
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public Frame(ClientMain main) {
		this.main = main;
		message = null;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 839, 669);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblInput = new JLabel("Input");
		
		textField = new JTextField();
		textField.setText("30.10.17");

		textField.setColumns(10);
		
		JLabel lblServer = new JLabel("Server");
		
		textArea = new JTextArea();
		
		JLabel lblPort = new JLabel("Port:");
		
		JButton btnSenden = new JButton("Senden");
		btnSenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				message = textField.getText();	
				changeMessage(message);
			}
		});
		
		spinner = new JSpinner();
		spinner.setValue(6002);
		
		textPane = new JTextPane();
		textPane.setText("localhost");
		
		JButton btnndern = new JButton("\u00C4ndern");
		btnndern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			    	 neueVerbindung();	
				} catch (IOException e1) {
					// TODO Automatisch generierter Erfassungsblock
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblAdresse = new JLabel("Adresse");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblServer)
							.addPreferredGap(ComponentPlacement.RELATED, 680, Short.MAX_VALUE)
							.addComponent(lblAdresse))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblInput))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(120)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
											.addComponent(btnSenden)))))
							.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPort)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnndern))))
					.addGap(45))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblInput)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSenden))
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServer)
						.addComponent(lblAdresse))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(lblPort)
							.addGap(18)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(77)
							.addComponent(btnndern)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void neueVerbindung() throws IOException {
		Thread thread = new Thread(new Runnable() {
    		@Override public void run() {
    			try {
					main.neueVerbindung(main.client.getF());
				} catch (IOException e) {
					// TODO Automatisch generierter Erfassungsblock
					e.printStackTrace();
				}
    		}
    	});
    	 thread.start(); 
    	 //main.neueVerbindung(this);		
	}
	
	public void println(String string) {
		// TODO Automatisch generierter Methodenstub
		textArea.append(string+ "\n" );
	}
	
	void changeMessage(String mes) {
		// TODO Automatisch generierter Methodenstub
		message = mes;
		changed = true;
		textArea.setText("");
		//readLine();
	}
	
	public String readLine() {
		// TODO Automatisch generierter Methodenstub
		//System.out.println(message);
		if (changed) {
			String m = message;
			message = null;
			changed = false;
			return m;
		}
		return null;
	}

	public int askPort() {
		// TODO Automatisch generierter Methodenstub
		return (int) this.spinner.getValue();
	}

	public String askHostName() {
		// TODO Automatisch generierter Methodenstub
		return textPane.getText();
	}
}
