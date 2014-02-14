package controller;

import view.ViewController;
import model.CalculatorModel;

public class CalculatorController implements CalculateControllerInterface {

	CalculatorModel model;
	ViewController view;
	boolean call, put, american, european, check;
	
	public CalculatorController(CalculatorModel model) {
		this.model = model;
		this.view = new ViewController(this, model);
		
		view.createGui();
	}
	
	@Override
	public void setStockPrice(double stockPrice) { model.setStockPrice(stockPrice); }

	@Override
	public void setStrikePrice(double strikePrice) { model.setStrikePrice(strikePrice); }

	@Override
	public void setFreeRiskRate(double risk) { model.setFreeRiskRate(risk); }

	@Override
	public void setVolatility(double volatility) { model.setVolatility(volatility); }

	@Override
	public void setTime(double time) { model.setTime(time); }

	@Override
	public void setIntervals(int intervals) { model.setIntervals(intervals); }

	@Override
	public double getBinominalResult() { return model.getBinominalResult(); }

	@Override
	public double getBlackScholesResult() { return model.getBlackScholesResult(); }

	@Override
	public void isCall(boolean b) { model.isCall(b); call = b; }

	@Override
	public void isPut(boolean b) { model.isPut(b); put = b; }

	@Override
	public void isEuropean(boolean b) { model.isEuropean(b); american = b; }

	@Override
	public void isAmerican(boolean b) { model.isAmerican(b); european = b; }
	
	@Override
	public void calculate() {
		
		if(check == true)
			model.calculate();
	}
	
	public void checkOptions() {
		
		if(american == false && european == false) {
			view.callPutError();
			check = false;
			return;
		}
		
		if(put == false && call == false) {
			view.americanEuropeError();
			check = false;
			return;
		}
		
		check = true;
	}

}
