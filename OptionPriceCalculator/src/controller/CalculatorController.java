package controller;

import view.ViewController;
import model.CalculatorModelInterface;

public class CalculatorController implements CalculateControllerInterface {

	CalculatorModelInterface model;
	ViewController view;
	
	public CalculatorController(CalculatorModelInterface model) {
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
	public void calculate() { model.calculate(); }

	@Override
	public void isCall(boolean b) { model.isCall(b); }

	@Override
	public void isPut(boolean b) { model.isPut(b); }

	@Override
	public void isEuropean(boolean b) { model.isEuropean(b); }

	@Override
	public void isAmerican(boolean b) { model.isAmerican(b); }

}
