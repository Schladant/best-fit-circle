package matrixSolvers;

/**
 * This class is used to convert an equation for a circle in general form to an
 * equation for a circle in standard form.
 * 
 * The general form equation is: 
 * 
 * 		(a * x^2) + (b * y^2) + (c * x) + (d * y) + e = 0
 * 
 * In this class, it is assumed that a = b, therefore the general form is:
 * 
 * 		a * (x^2 + y^2) + (c * x) + (d * y) + e = 0
 * 
 * By completing the square and factoring, the standard form for a circle is obtained:
 * 
 * 		(x^2 - h) + (y^2 - k) = r^2
 * 
 * Here, the point (h,k) is the center of the circle and r is the radius.
 * 
 * 
 * @author Austin Schladant
 */
public class EquationConverter {
	
	/**
	 * Takes the an array the corresponds to a circle in general form, and 
	 * converts it to a circle in standard form.
	 * 
	 * @param arr an array that corresponds to coefficients of variables in a 
	 * 		  circle standard form equation.
	 */
	public static void convertToStandardForm(double[] arr) {
		EquationConverter e = new EquationConverter();
		
		e.moveConstantToOtherSide(arr);
		
		e.isolateXYSquare(arr);
		
		e.completeTheSquare(arr, Determinant.X);
		
		e.completeTheSquare(arr, Determinant.Y);
		
		e.convertToPointForm(arr);
	}
	
	
	
	
	/**
	 * Moves constant over to other side, therefore, isolating the variables.
	 * Moves constant over to other side by changing the sign of the constant.
	 * 
	 * @param arr
	 */
	private void moveConstantToOtherSide(double[] arr) {
		arr[3] *= -1;
	}
	
	
	
	
	/**
	 * Isolates the variable with the biggest terms by dividing by their coefficient.
	 * 
	 * @param arr
	 */
	private void isolateXYSquare(double[] arr) {
		
		for (int i =1; i < arr.length; i++) {
			arr[i] /= arr[Determinant.X2_PLUS_Y2];
		}
		arr[0] /= arr[0];
	}
	
	
	
	
	/**
	 * Completes the square for the x variable by computing what
	 * is needed to make a perfect square, and adding it to the variable
	 * coefficient and the constant.
	 * 
	 * @param arr
	 */
	private void completeTheSquare(double[] arr, int variable) {
		
		double temp = arr[variable],  completedSquare;
		
		completedSquare = Math.pow(temp / 2, 2);
		
		arr[Determinant.CONST] += completedSquare;
		
		arr[variable] /= 2;
	}
	
	
	/**
	 * Takes the numbers from the standard form equation and converts them
	 * to a form that can be used for graphing.
	 * 
	 * @param arr
	 */
	private void convertToPointForm(double[] arr) {
		arr[Determinant.X] *= -1;
		arr[Determinant.Y] *= -1;
		arr[Determinant.CONST] = Math.sqrt(arr[Determinant.CONST]);
	}
	
	
	
	/**
	 * Prints out the equation in standard form.
	 * 
	 * @param arr
	 */
	public void printEquation(double[] arr) {
		System.out.printf("( x - %.1f )^2 + ( y - %.1f )^2 = %.1f\n\n",
				arr[Determinant.X], arr[Determinant.Y], arr[Determinant.CONST]);
	}
}
