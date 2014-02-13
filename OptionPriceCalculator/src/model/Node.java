package model;

public class Node {

	private int id;
	private double underlyingPrice;
	private double value;
	
	public Node(int id){ this.id = id; }
	
	public double getUnderlyingPrice() { return this.underlyingPrice; }
	public double getPrice() { return this.value; }
	public int getId() { return this.id; }
	
	public void setUnderlyingPrice(double aprice) { this.underlyingPrice = aprice; }
	public void setPrice(double value) { this.value = value; }
}
