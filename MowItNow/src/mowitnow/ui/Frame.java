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
	
	private JPanel panelTop;
	private JPanel panelBottom;
	private JTextField textField;
	private JButton btnOuvrir;
	private JSplitPane splitPane;
	private JTextArea txtOutput;
	private JPanel panelCenterBtn;
	private JButton btnLancer;
	private JPanel panelCenterTxt;
	private JTextArea txtInput;
	private JFileChooser fc;
	
	private Controller controller;
	
	/**
	 * Ouvrir & lancer le fichier des commandes
	 */
	private void runFile() {
		int returnVal = getFileChooser().showDialog(Frame.this, "Ouvrir");
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			getTextField().setText(getFileChooser().getSelectedFile().getAbsolutePath());
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			PrintStream writer = null;
			try {
				writer = new PrintStream(stream);
				if(getTxtOutput().getText().length() > 1000)
					getTxtOutput().setText("");
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
	
	/**
	 * Lancer le text des commandes
	 */
	private void runText() {
		if( !getTxtInput().getText().isEmpty() ) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			PrintStream writer = null;
			try {
				writer = new PrintStream(stream);
				
				if(getTxtOutput().getText().length() > 1000)
					getTxtOutput().setText("");
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
	
	private void initialize() {
		setMinimumSize(new Dimension(440, 400));
		setPreferredSize(new Dimension(440, 440));
		getContentPane().add(getPanelTop(), BorderLayout.NORTH);
		getContentPane().add(getSplitPane(), BorderLayout.CENTER);
	}
	
	private JPanel getPanelTop() {
		if(panelTop == null) {
			
			panelTop = new JPanel();
			panelTop.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
			panelTop.setMaximumSize(new Dimension(32767, 65));
			panelTop.setPreferredSize(new Dimension(10, 65));
			panelTop.setMinimumSize(new Dimension(10, 65));
			panelTop.setLayout(null);
			panelTop.add( getTextField() );
			panelTop.add(getBtnOuvrir());
		}
		return panelTop;
	}
	
	private JTextField getTextField() {
		if(textField == null) {
			textField = new JTextField();
			textField.setMinimumSize(new Dimension(200, 20));
			textField.setPreferredSize(new Dimension(200, 20));
			textField.setBounds(10, 35, 400, 20);
			textField.setEditable(false);
		}			
		return textField;
	}
	
	private JButton getBtnOuvrir() {
		if(btnOuvrir == null) {
			btnOuvrir = new JButton("Lancer le fichier");
			btnOuvrir.setBounds(10, 10, 130, 23);
			btnOuvrir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					runFile();
				}
			});
			
		}
		return btnOuvrir;
	}
	
	private JSplitPane getSplitPane() {
		if(splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setTopComponent( getPanelCenterTxt() );
			splitPane.setBottomComponent( getPanelBottom() );
			splitPane.setDividerLocation(150);
		}
		return splitPane;
	}
	
	public JPanel getPanelCenterBtn() {
		if(panelCenterBtn == null) {
			panelCenterBtn = new JPanel();
			panelCenterBtn.setMaximumSize(new Dimension(32767, 40));
			panelCenterBtn.setMinimumSize(new Dimension(10, 40));
			panelCenterBtn.setPreferredSize(new Dimension(90, 40));
			panelCenterBtn.setLayout(null);
			panelCenterBtn.add(getBtnLancer());
		}
		return panelCenterBtn;
	}
	
	private JButton getBtnLancer() {
		if(btnLancer == null) {
			btnLancer = new JButton("Lancer le texte");
			btnLancer.setBounds(10, 10, 130, 23);
			btnLancer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					runText();
				}
			});
		}
		return btnLancer;
	}
	
	private JPanel getPanelBottom() {
		if(panelBottom == null) {
			JScrollPane scrollPane = new JScrollPane( getTxtOutput() );
			
			panelBottom = new JPanel();
			panelBottom.setPreferredSize(new Dimension(100, 100));
			panelBottom.setMinimumSize(new Dimension(100, 100));
			panelBottom.setLayout(new BorderLayout(0, 0));	
			panelBottom.add(scrollPane, BorderLayout.CENTER);
		}
		return panelBottom;
	}
	
	private JTextArea getTxtOutput() {
		if(txtOutput == null) {
			txtOutput = new JTextArea();
			txtOutput.setLineWrap(true);
			txtOutput.setEditable(false);
		}
		return txtOutput;
	}
	
	private JPanel getPanelCenterTxt() {
		if(panelCenterTxt == null) {
			JScrollPane scrollPane = new JScrollPane( getTxtInput() );
			
			panelCenterTxt = new JPanel();
			panelCenterTxt.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
			panelCenterTxt.setLayout(new BorderLayout(0, 0));
			panelCenterTxt.add(getPanelCenterBtn(), BorderLayout.NORTH);
			panelCenterTxt.add(scrollPane, BorderLayout.CENTER);
		}
		return panelCenterTxt;
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
	
	/**
	 * Créer simple MowItNow interface
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
	}
} 
