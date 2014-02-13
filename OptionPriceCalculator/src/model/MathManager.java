package model;

import java.text.NumberFormat;

public class MathManager {
	
	double optionPrice;
	double strikePrice;
	double freeRisk;
	double changes;
	double time;
	int intervals;
	
	double deltaTime;
	double up;
	double down;
	double x;
	double probability;
	double lossProbability;
	
	double bsCallPrice;
	double bsPutPrice;
	
	NumberFormat nf = NumberFormat.getInstance();
	
	public double getBlackScholesCallPrice() { return this.bsCallPrice; }
	public double getBlackScholesPutPrice() { return this.bsPutPrice; }
	
 	public MathManager(double futurePrice, double strikePrice, double freeRisk, double changes, double time, int intervals) {
		
		this.optionPrice = futurePrice;
		this.strikePrice = strikePrice;
		this.freeRisk = freeRisk;
		this.changes = changes;
		//this.time = (time/12);
		this.time = time;
		this.intervals = intervals;
		
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		deltaTime = this.time/this.intervals;
		
		//deltaTime = 0.0833;
		up = Math.exp(changes * Math.sqrt(deltaTime));
		down = Math.exp(-changes * Math.sqrt(deltaTime));
		x = Math.exp(freeRisk * deltaTime);
		probability = (x - down)/(up - down);
		lossProbability = 1 - probability;
	}
	
	public void optionUnderlyingPrice(Node[][] arrayNode) {
		
		System.out.println("Time: " + time);
		System.out.println("deltaTime: " + deltaTime);
		System.out.println("up: " + up);
		System.out.println("down: " + down);
		for(int i=0; i<arrayNode.length; i++) {
			//arrayNode[i] = new Node[i+1];
			for(int j=0; j<=i ;j++){
				double price = optionPrice * Math.pow(up, j) * Math.pow(down, i-j);
				arrayNode[i][j].setUnderlyingPrice( price );
			}
		}
	}
	
	public void callPriceLast(Node[][] arrayNode) {
		
		int lastIndex = arrayNode.length-1;
		
		for(int j=0; j<=arrayNode.length-1; j++) {
			double value = Math.max(arrayNode[lastIndex][j].getUnderlyingPrice() - strikePrice, 0);
			arrayNode[arrayNode.length-1][j].setPrice( value );
		}
	}
	
	public void putPriceLast(Node[][] arrayNode) {
		
		int lastIndex = arrayNode.length-1;
		double value;
		
		for(int j=0; j<=arrayNode.length-1; j++) {
			value = Math.max(strikePrice - arrayNode[lastIndex][j].getUnderlyingPrice(), 0);
			arrayNode[arrayNode.length-1][j].setPrice( value );
		}
	}
	
	public void europeanPutCallOptionPrice(Node[][] arrayNode) {
		
		//przedostatni
		int penultimate = arrayNode.length -2;	
		double upPrice;
		double downPrice;
		
		for(int i=penultimate; i>=0; i--) {
			for(int j=0; j<=i; j++) {
				upPrice = arrayNode[i+1][j+1].getPrice();
				downPrice = arrayNode[i+1][j].getPrice();
				double price = Math.exp(-freeRisk * deltaTime) * (probability * upPrice + lossProbability * downPrice);
				arrayNode[i][j].setPrice(price);
			}
		}
	}
	
	public void americanCallOptionPrice(Node[][] arrayNode) {
		int penultimate = arrayNode.length -2;
		double upPrice;
		double downPrice;
		double underlyingPrice;
		
		for(int i=penultimate; i>=0; i--) {
			for(int j=0; j<=i; j++) {
				underlyingPrice = arrayNode[i][j].getUnderlyingPrice();
				upPrice = arrayNode[i+1][j+1].getPrice();
				downPrice = arrayNode[i+1][j].getPrice();
				double price = Math.max(underlyingPrice - strikePrice, Math.exp(-freeRisk * deltaTime) * (probability * upPrice + lossProbability * downPrice));
				arrayNode[i][j].setPrice(price);
			}
		}
	}
	
	public void americanPutOptionPrice(Node[][] arrayNode) {
		int penultimate = arrayNode.length -2;
		double upPrice;
		double downPrice;
		double underlyingPrice;
		
		for(int i=penultimate; i>=0; i--) {
			for(int j=0; j<=i; j++) {
				underlyingPrice = arrayNode[i][j].getUnderlyingPrice();
				upPrice = arrayNode[i+1][j+1].getPrice();
				downPrice = arrayNode[i+1][j].getPrice();
				double price = Math.max(strikePrice - underlyingPrice , Math.exp(-freeRisk * deltaTime) * (probability * upPrice + lossProbability * downPrice));
				arrayNode[i][j].setPrice(price);
			}
		}
	}
	
	public void BlackScholes(char CallPutFlag)
	{
		double d1, d2;

		d1=(Math.log(optionPrice/strikePrice)+(freeRisk+changes*changes/2)*time)/(changes*Math.sqrt(time));
		d2=d1-changes*Math.sqrt(time);

		if (CallPutFlag=='c') {
			bsCallPrice = optionPrice*CND(d1)-strikePrice*Math.exp(-freeRisk*time)*CND(d2);
		}
		else {
			bsPutPrice = strikePrice*Math.exp(-freeRisk*time)*CND(-d2)-optionPrice*CND(-d1);
		}
	}
			
	private double CND(double X) {
		double L, K, w ;
		double a1 = 0.31938153, a2 = -0.356563782, a3 = 1.781477937, a4 = -1.821255978, a5 = 1.330274429;
	
		L = Math.abs(X);
		K = 1.0 / (1.0 + 0.2316419 * L);
		w = 1.0 - 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-L *L / 2) * (a1 * K + a2 * K *K + a3 * Math.pow(K,3) + a4 * Math.pow(K,4) + a5 * Math.pow(K,5));
	
		if (X < 0.0) 
		{
			w= 1.0 - w;
		}
		return w;
	}
}
