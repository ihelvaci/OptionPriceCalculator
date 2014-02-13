package mrfSource;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class Gui extends JFrame {

	private String[] labelsNames = {"Stock price", "Strike price", "Free-risk rate", "Volatility", "Time", "Intervals"};
	private String[] settings = {"European option", "American option", "CALL", "PUT"};
	//private String[] settings = {"European option", "", "CALL", "PUT"};
	//private JTextField[] textFields = new JTextField[labelsNames.length];
	private HashMap<String, JTextField> mapTextField = new HashMap<String, JTextField>();
	private JLabel[] labels = new JLabel[labelsNames.length];
	private JButton button;
	private JButton button1;
	private JLabel labelPrice;
	private JLabel labelBSPrice;
	
	private ActionListener aListener;
	private ItemListener iListener;
	
	public Gui(ActionListener a, ItemListener i) {
		aListener = a;
		iListener = i;
		try{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					createGui();
				}
			});
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(this, "CreateGui Error:" + ex);
		}
	}
	
	private void createGui() {
		
		JPanel settingPanel = new JPanel();
		JPanel formPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel labelPanel = new JPanel();
		
		ButtonGroup bg1 = new ButtonGroup();
		ButtonGroup bg2 = new ButtonGroup();
		
		setTitle("Binominal tree price calculator");
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		settingPanel.setLayout(new GridLayout(0,2));
		settingPanel.setBorder(new TitledBorder("Settings"));
		
		for(int i=0; i<settings.length; i++) {
			JRadioButton rbutton = new JRadioButton(settings[i]);
			//rbutton.addActionListener(aListener);
			rbutton.addItemListener(iListener);
			if(i==0 || i==1) {
				//rbutton.setSelected(true);
				bg1.add(rbutton);
			}
			else {
				//rbutton.setSelected(true);
				bg2.add(rbutton);
			}
			settingPanel.add(rbutton);
		}
		//bg2.clearSelection();
		
		formPanel.setLayout(new GridLayout(0,2));
		formPanel.setBorder(new TitledBorder("Data"));
		
		for(int i=0; i<labelsNames.length; i++) {
			labels[i] = new JLabel(labelsNames[i]);
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			formPanel.add(labels[i]);
			//textFields[i] = new JTextField(20);
			//add(textFields[i]);
			mapTextField.put(labelsNames[i], new JTextField(20));
			formPanel.add(mapTextField.get(labelsNames[i]));
		}
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//buttonPanel.setLayout(new GridLayout(0,2));
		buttonPanel.setPreferredSize(new Dimension(200, 50));
		button = new JButton("Calculate");
		button.setActionCommand("calculate");
		button.addActionListener(aListener);
		button.setHorizontalAlignment(JButton.CENTER);
		buttonPanel.add(button);
		
		button1 = new JButton("Clear");
		button1.setActionCommand("clear");
		button1.addActionListener(aListener);
		button1.setHorizontalAlignment(JButton.CENTER);
		buttonPanel.add(button1);
		
		labelPanel.setPreferredSize(new Dimension(200, 50));
		labelPanel.setLayout(new GridLayout(2,0));
		labelPanel.setBorder(new TitledBorder("Results"));
		labelPrice = new JLabel("Binominal tree method: ");
		labelBSPrice = new JLabel("Black-Scholes method: ");
		labelPrice.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		labelBSPrice.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		labelPanel.add(labelPrice);
		labelPanel.add(labelBSPrice);

		add(settingPanel);
		add(formPanel);
		add(buttonPanel);
		add(labelPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		pack();	
		
		setLocationRelativeTo(null);
				
		setVisible(true);
		
	}
	
	public void setPriceLabels(String str) { 
		labelPrice.setText("Binominal tree method: ");
		labelPrice.setText(labelPrice.getText() + str); 
	}
	public void setBSPrice(String str) {
		labelBSPrice.setText("Black-Scholes method: ");
		labelBSPrice.setText(labelBSPrice.getText() + str);
	}
	
	public HashMap<String, JTextField> getTextFields() { return this.mapTextField; }
	
	public void clearTextFields() {
		
		try{
			for(Map.Entry<String, JTextField> entry : mapTextField.entrySet()) {
				((JTextField)entry.getValue()).setText("");
			}
		} catch (NullPointerException ex) {
			return;
		}
	}

}
