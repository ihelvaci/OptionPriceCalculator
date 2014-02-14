package mrfSource;

import model.CalculatorModel;
import controller.CalculatorController;
import view.ViewController;

public class PriceByBinomialTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CalculatorModel model = new CalculatorModel();
		CalculatorController controller = new CalculatorController(model);
		//controller.printUnderlyingPrice();
	}

}
