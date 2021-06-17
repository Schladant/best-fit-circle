package matrixSolvers;


/**
 * This class is solely used to get the determinants of each column in the first row
 * of a matrix with 4 rows and 4 columns.
 * 
 * Here the matrix given in the parameter is a 4 by 4 matrix of the form:
 * 
 * 		|   (x^2 + y^2)    x    y    1   |
 * 		|  (x1^2 + y1^2)   x1   y1   1   |
 * 		|  (x2^2 + y2^2)   x2   y2   1   |
 * 		|  (x3^2 + y3^2)   x3   y3   1   |
 * 
 * After getting the determinant of each column of the first row, an equation for a circle 
 * is obtained in general form, which is: 
 * 
 * 		a * (x^2 + y^2) + (c * x) + (d * y) + e = 0
 * 
 * From here, the equation can be transformed into standard form, and the center and radius 
 * of the circle can be obtained.
 * 
 * @author Austin Schladant
 *
 */
public class Determinant {
	
	public final static int X2_PLUS_Y2 = 0;
	public final static int X = 1;
	public final static int Y = 2;
	public final static int CONST = 3;
	
	public Determinant() {
		
	}
	
	
	
	
	/**
	 * Takes a 4 by 4 matrix, and determines the determinant for each column in the 
	 * first row.
	 * 
	 * @param testPoints a 4 by 4 matrix
	 * @return a double array of size 4, where the ith index corresponds to a determinant of the
	 *  		1st row - ith column
	 */
	public static double[] testBundle(Matrix testPoints) {
		
	double[] arrSolution = new double[4];

	Matrix fourByFour = testPoints.getSolver().getSquare4();

	Matrix sub1 = new Matrix(3,3);

	Matrix sub2 = new Matrix(3,3);

	Matrix sub3 = new Matrix(3,3);

	Matrix sub4 = new Matrix(3,3);

	sub1.setValue(0,0, fourByFour.get(1,1));
	sub1.setValue(0,1, fourByFour.get(1,2));
	sub1.setValue(0,2, fourByFour.get(1,3));
	sub1.setValue(1,0, fourByFour.get(2,1));
	sub1.setValue(1,1, fourByFour.get(2,2));
	sub1.setValue(1,2, fourByFour.get(2,3));
	sub1.setValue(2,0, fourByFour.get(3,1));
	sub1.setValue(2,1, fourByFour.get(3,2));
	sub1.setValue(2,2, fourByFour.get(3,3));

	
	sub2.setValue(0,0, fourByFour.get(1,0));
	sub2.setValue(1,0, fourByFour.get(2,0));
	sub2.setValue(2,0, fourByFour.get(3,0));
	sub2.setValue(0,1, fourByFour.get(1,2));
	sub2.setValue(1,1, fourByFour.get(2,2));
	sub2.setValue(2,1, fourByFour.get(3,2));
	sub2.setValue(0,2, fourByFour.get(1,3));
	sub2.setValue(1,2, fourByFour.get(2,3));
	sub2.setValue(2,2, fourByFour.get(3,3));

	
	sub3.setValue(0,0, fourByFour.get(1,0));
	sub3.setValue(1,0, fourByFour.get(2,0));
	sub3.setValue(2,0, fourByFour.get(3,0));
	sub3.setValue(0,1, fourByFour.get(1,1));
	sub3.setValue(1,1, fourByFour.get(2,1));
	sub3.setValue(2,1, fourByFour.get(3,1));
	sub3.setValue(0,2, fourByFour.get(1,3));
	sub3.setValue(1,2, fourByFour.get(2,3));
	sub3.setValue(2,2, fourByFour.get(3,3));

	
	sub4.setValue(0,0, fourByFour.get(1,0));
	sub4.setValue(1,0, fourByFour.get(2,0));
	sub4.setValue(2,0, fourByFour.get(3,0));
	sub4.setValue(0,1, fourByFour.get(1,1));
	sub4.setValue(1,1, fourByFour.get(2,1));
	sub4.setValue(2,1, fourByFour.get(3,1));
	sub4.setValue(0,2, fourByFour.get(1,2));
	sub4.setValue(1,2, fourByFour.get(2,2));
	sub4.setValue(2,2, fourByFour.get(3,2));

	
	arrSolution[X2_PLUS_Y2] = sub1.getSolver().getMatrixDeterminant(sub1);

	arrSolution[X] = sub2.getSolver().getMatrixDeterminant(sub2)*-1;

	arrSolution[Y] = sub3.getSolver().getMatrixDeterminant(sub3);

	arrSolution[CONST] = sub4.getSolver().getMatrixDeterminant(sub4)*-1;
	
	EquationConverter.convertToStandardForm(arrSolution);
	
	new EquationConverter().printEquation(arrSolution);
 
	return arrSolution;
	
	}
}
