package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;




import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



import controller.CalculateControllerInterface;
import model.CalculatorModel;
import model.CalculatorModelInterface;


public class ViewController implements ActionListener, ItemListener, ResultObserverInterface {

	boolean americanOption = false;
	boolean europeanOption = false;
	boolean call = false;
	boolean put = false;
	//view
	//JTextField[] dataFromView;
	HashMap<String, JTextField> dataFromView;
	Gui gui;
	
	//model
	//MathManager mathManager;
	//Node[][] treenode;
	//Tree tree;
	CalculatorModelInterface model;
	CalculateControllerInterface controller;
	
	//for formating numbers
	java.text.DecimalFormat df=new java.text.DecimalFormat();
	
	
	public ViewController(CalculateControllerInterface controller, CalculatorModelInterface model) {
		this.model = model;
		this.controller = controller;
		model.registerObserver(this);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		String option = action.getActionCommand();
		switch(option) {
		
			case "calculate" :
				if(americanOption == false && europeanOption == false) {
					JOptionPane.showMessageDialog(gui.getContentPane(), "Please,  select one of european or american option.");
					return;
				}
				
				if(put == false && call == false) {
					JOptionPane.showMessageDialog(gui.getContentPane(), "Please,  select call or put.");
					return;
				}
				
				getDateFromView();
				
				try {
					controller.setStockPrice(Double.parseDouble(dataFromView.get("Stock price").getText()));
					controller.setStrikePrice(Double.parseDouble(dataFromView.get("Strike price").getText()));
					controller.setFreeRiskRate(Double.parseDouble(dataFromView.get("Free-risk rate").getText()));
					controller.setVolatility(Double.parseDouble(dataFromView.get("Volatility").getText()));
					controller.setTime(Double.parseDouble(dataFromView.get("Time").getText()));
					controller.setIntervals(Integer.parseInt(dataFromView.get("Intervals").getText()));
					
					controller.isCall(call);
					controller.isPut(put);
					controller.isAmerican(americanOption);
					controller.isEuropean(europeanOption);
					
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(gui.getParent(), "Please validate your data" + "\n" + ex);
					return;
				} catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(gui.getParent(), "Please validate your data" + "\n" + ex);
					return;
				}
				controller.calculate();
				/*
				try {
					int intervals = Integer.parseInt(dataFromView.get("Intervals").getText());
					System.out.println("Intervals: " + intervals);
					tree = new Tree(intervals);
					treenode = tree.createTree();
					mathManager = new MathManager(Double.parseDouble(dataFromView.get("Stock price").getText()), Double.parseDouble(dataFromView.get("Strike price").getText()), Double.parseDouble(dataFromView.get("Free-risk rate").getText()), Double.parseDouble(dataFromView.get("Volatility").getText()), Double.parseDouble(dataFromView.get("Time").getText()), intervals);
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(gui.getParent(), "Please validate your data" + "\n" + ex);
					return;
				} catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(gui.getParent(), "Please validate your data" + "\n" + ex);
					return;
				}
				
				doCalculate();
				break;
			*/
			case "clear" :
	
				clearSettings();
				break;

		}
		
	}
	
	public void createGui() {
		gui = new Gui(this, this);
	}
	
	/*public void printUnderlyingPrice() {
		
		for(int i=0; i<treenode.length; i++) {
			for(int j=0; j<i+1; j++) {
				System.out.print(df.format(treenode[i][j].getUnderlyingPrice())+" ");
			}
			System.out.println();
		}
	}
	
	public void printPrice() {
		java.text.DecimalFormat df=new java.text.DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		for(int i=0; i<treenode.length; i++) {
			for(int j=0; j<i+1; j++) {
				System.out.print(df.format(treenode[i][j].getPrice())+" ");
			}
			System.out.println();
		}
	}*/
	
	private void getDateFromView() { this.dataFromView = gui.getTextFields(); }
	
	private void clearSettings() {
		europeanOption = false;
		americanOption = false;
		call = false;
		put = false;
		gui.clearTextFields();
		/*try {
			if(!dataFromView.isEmpty()) {
				dataFromView.clear();
			}
			
		} catch (java.lang.NullPointerException ex) {
			return;
		}*/
		
	}
	
	/*private void doCalculate() {
		mathManager.optionUnderlyingPrice(treenode);
		
		if(call == true) {
			mathManager.callPriceLast(treenode);
			System.out.println("Call");
			
			if(americanOption == true) {
				mathManager.americanCallOptionPrice(treenode);
				System.out.println("American");
			}
			else {
				mathManager.europeanPutCallOptionPrice(treenode);
				mathManager.BlackScholes('c');
				
				gui.setPriceLabels(df.format(treenode[0][0].getPrice()));
				gui.setBSPrice(df.format(mathManager.getBlackScholesCallPrice()));
				
				System.out.println(mathManager.getBlackScholesCallPrice());
				System.out.println("European");
			}
		}
		if(put == true) {
			mathManager.putPriceLast(treenode);
			System.out.println("Put");
			
			if(americanOption == true) {
				mathManager.americanPutOptionPrice(treenode);
				System.out.println("American");
			}
			else {
				mathManager.europeanPutCallOptionPrice(treenode);
				mathManager.BlackScholes('p');
				
				gui.setPriceLabels(df.format(treenode[0][0].getPrice()));
				gui.setBSPrice(df.format(mathManager.getBlackScholesPutPrice()));
				
				System.out.println(mathManager.getBlackScholesPutPrice());
				System.out.println("European");
			}
		}
		//printUnderlyingPrice();
		//printPrice();
		//clearSettings();
	}*/

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String choose = ((JRadioButton) e.getSource()).getActionCommand();
			switch(choose) {
			case "European option":
				europeanOption = true;
				break;
			case "American option":
				americanOption = true;
				break;
			case "PUT":
				put = true;
				call = false;
				break;
			case "CALL":
				call = true;
				put = false;
				break;
			}
		}
		/*else {
			String choose = ((JRadioButton) e.getSource()).getActionCommand();
			switch(choose) {
			case "European option":
				europeanOption = false;
				break;
			case "American option":
				americanOption = false;
				break;
			case "PUT":
				put = false;
				break;
			case "CALL":
				call = false;
				break;
			}
		}*/
		
	}


	@Override
	public void uptadeResult() {
		gui.setBSPrice(df.format(model.getBlackScholesResult()));
		gui.setPriceLabels(df.format(model.getBinominalResult()));
	}
}
