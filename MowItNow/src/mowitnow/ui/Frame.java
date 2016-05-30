package mowitnow.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import mowitnow.controller.Controller;

/**
 * @author Y.Tchirikov
 *
 * Simple MowItNow interface
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField textField;
	private JTextArea txtOutput;
	private JTextArea txtInput;
	private JFileChooser fc;
	
	private Controller controller;
	
	public Frame() {
		setMinimumSize(new Dimension(440, 400));
		setPreferredSize(new Dimension(300, 300));
		
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panelTop.setMaximumSize(new Dimension(32767, 55));
		panelTop.setPreferredSize(new Dimension(10, 55));
		panelTop.setMinimumSize(new Dimension(10, 55));
		getContentPane().add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(null);
		
		textField = new JTextField();
		textField.setMinimumSize(new Dimension(200, 20));
		textField.setPreferredSize(new Dimension(200, 20));
		textField.setBounds(10, 25, 300, 20);
		panelTop.add(textField);
		textField.setColumns(10);
		
		JLabel lblLancerParFichier = new JLabel("Ouvrir le fichier");
		lblLancerParFichier.setBounds(10, 5, 100, 14);
		panelTop.add(lblLancerParFichier);
		
		JButton btnOuvrir = new JButton("Ouvrir");
		btnOuvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = getFileChooser().showDialog(Frame.this, "Ouvrir");
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					textField.setText(getFileChooser().getSelectedFile().getAbsolutePath());
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					PrintStream writer = null;
					try {
						writer = new PrintStream(stream);
						getController().execute(fc.getSelectedFile(), writer);
					}
					catch(Exception e){
						writer.print(e);
					}
					finally {
						writer.close();
					}
					getTxtOutput().append( stream.toString() );
					getTxtOutput().append("\n");
				}
			}
		});
		btnOuvrir.setBounds(320, 23, 89, 23);
		panelTop.add(btnOuvrir);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setMinimumSize(new Dimension(100, 100));
		panel.setLayout(new BorderLayout(0, 0));		
		
		JScrollPane scrollPane = new JScrollPane( getTxtOutput() );
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane( getTxtInput() );
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(32767, 40));
		panel_2.setMinimumSize(new Dimension(10, 40));
		panel_2.setPreferredSize(new Dimension(90, 40));
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);
		
		JButton btnLancer = new JButton("Lancer le texte");
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( !getTxtInput().getText().isEmpty() ) {
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					PrintStream writer = null;
					try {
						writer = new PrintStream(stream);
						getController().execute(getTxtInput().getText(), writer);
					}
					catch(Exception e){
						writer.print(e);
					}
					finally {
						writer.close();
					}
					getTxtOutput().append( stream.toString() );
					getTxtOutput().append("\n");

				}
			}
		});
		btnLancer.setBounds(10, 11, 130, 23);
		panel_2.add(btnLancer);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setTopComponent(panel_1);
		splitPane.setBottomComponent(panel);
		splitPane.setDividerLocation(150);
	}
	
	private JTextArea getTxtOutput() {
		if(txtOutput == null) {
			txtOutput = new JTextArea();
			txtOutput.setLineWrap(true);
			txtOutput.setEditable(false);
		}
		return txtOutput;
	}
	
	private JTextArea getTxtInput() {
		if(txtInput == null)
			txtInput = new JTextArea();
		return txtInput;
	}
	
	private JFileChooser getFileChooser() {
		if(fc == null)
			fc = new JFileChooser( new File(".").getAbsolutePath() );
		return fc;
	}
	
	/**
	 * Créer un instance {@link Controller} 
	 * @return
	 */
	private Controller getController() {
		if(controller == null)
			controller = new Controller();
		return controller;
	}
} 
