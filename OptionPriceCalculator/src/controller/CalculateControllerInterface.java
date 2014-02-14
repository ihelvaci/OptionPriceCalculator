package controller;

public interface CalculateControllerInterface {

	public void setStockPrice(double stockPrice);
	public void setStrikePrice(double strikePrice);
	public void setFreeRiskRate(double risk);
	public void setVolatility(double volatility);
	public void setTime(double time);
	public void setIntervals(int intervals);
	
	public void isCall(boolean b);
	public void isPut(boolean b);
	public void isEuropean(boolean b);
	public void isAmerican(boolean b);
	
	public double getBinominalResult();
	public double getBlackScholesResult();
	
	public void calculate();
}
