package model;

import java.util.ArrayList;

import view.ResultObserverInterface;

public class CalculatorModel implements CalculatorModelInterface {

	private double stockPrice, strikePrice, risk, volatility, time;
	private int intervals;
	private double binominalResult;
	private double BlackScholesResult;
	
	boolean call, put, american, european;
	
	ArrayList<ResultObserverInterface> observersList = new ArrayList<ResultObserverInterface>();
	
	MathManager mathManager;
	Node[][] treenode;
	Tree tree;
	
	public CalculatorModel() {}
	
	public CalculatorModel(double stockPrice, double strikePrice, double risk, double volatility, double time, int intervals) {
		this.stockPrice = stockPrice;
		this.strikePrice = strikePrice;
		this.risk = risk;
		this.volatility = volatility;
		this.time = time;
		this.intervals = intervals;
	}
	
	@Override
	public void setStockPrice(double stockPrice) { this.stockPrice = stockPrice; }

	@Override
	public void setStrikePrice(double strikePrice) { this.strikePrice = strikePrice; }

	@Override
	public void setFreeRiskRate(double risk) { this.risk = risk; }

	@Override
	public void setVolatility(double volatility) { this.volatility = volatility; }

	@Override
	public void setTime(double time) { this.time = time; }

	@Override
	public void setIntervals(int intervals) { this.intervals = intervals; }

	@Override
	public double getBinominalResult() { return binominalResult; }

	@Override
	public double getBlackScholesResult() { return BlackScholesResult; }

	@Override
	public void calculate() {
		
		tree = new Tree(intervals);
		treenode = tree.createTree();
		mathManager = new MathManager(stockPrice, strikePrice, risk, volatility, time, intervals);
		
		mathManager.optionUnderlyingPrice(treenode);
		
		if(call == true) {
			mathManager.callPriceLast(treenode);
			//System.out.println("Call");
			
			if(american == true) {
				mathManager.americanCallOptionPrice(treenode);
				
				binominalResult = treenode[0][0].getPrice();
				BlackScholesResult = 0;
				
				updateObserver();
				//System.out.println("American");
			}
			else {
				mathManager.europeanPutCallOptionPrice(treenode);
				mathManager.BlackScholes('c');
				
				binominalResult = treenode[0][0].getPrice();
				BlackScholesResult = mathManager.getBlackScholesCallPrice();
				
				updateObserver();
				//System.out.println(mathManager.getBlackScholesCallPrice());
				//System.out.println("European");
			}
		}
		if(put == true) {
			mathManager.putPriceLast(treenode);
			//System.out.println("Put");
			
			if(american == true) {
				mathManager.americanPutOptionPrice(treenode);
				
				binominalResult = treenode[0][0].getPrice();
				BlackScholesResult = 0;
				
				updateObserver();
				//System.out.println("American");
			}
			else {
				mathManager.europeanPutCallOptionPrice(treenode);
				mathManager.BlackScholes('p');
				
				binominalResult = treenode[0][0].getPrice();
				BlackScholesResult = mathManager.getBlackScholesCallPrice();
				
				updateObserver();
				//System.out.println(mathManager.getBlackScholesPutPrice());
				//System.out.println("European");
			}
		}

	}

	@Override
	public void registerObserver(ResultObserverInterface o) { observersList.add(o); }

	@Override
	public void removeObserver(ResultObserverInterface o) { 
		int i = observersList.indexOf(o);
		if( i>= 0)
			observersList.remove(i);
	}
	
	public void updateObserver() {
		for(int i=0; i<observersList.size(); i++) {
			ResultObserverInterface o = (ResultObserverInterface) observersList.get(i);
			o.uptadeResult();
		}
		
	}

	@Override
	public void isCall(boolean b) { call = b; }

	@Override
	public void isPut(boolean b) { put = b; }

	@Override
	public void isEuropean(boolean b) { european = b; }

	@Override
	public void isAmerican(boolean b) { american = b; }

}
