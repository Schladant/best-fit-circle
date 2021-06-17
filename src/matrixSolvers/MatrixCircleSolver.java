package matrixSolvers;


/**
 * This class has the basic matrix operations transpose and matrix multiplication.
 * 
 * This class also formats a set of N points to form that can be used to solve the 
 * problem of finding the best fitting circle. The form of this matrix is:
 * 
 * 		|   (x^2 + y^2)    x    y    1   |
 * 		|  (x1^2 + y1^2)   x1   y1   1   |
 * 		|  (x2^2 + y2^2)   x2   y2   1   |
 * 		|  (x3^2 + y3^2)   x3   y3   1   |
 * 
 * where the first row are variable, and the following for specific points.
 * 
 * @author Austin Schladant
 *
 */
public class MatrixCircleSolver {

	private Matrix matrixOfPoints;
	private Matrix squareTranspose; // M-transpose
	private Matrix mTransposeXM;
	private Matrix mTransposeXMInverse;
	private Matrix square4 = null;
	private Matrix yMatrix;
	
	
	
	
	/**
	 * @param orgMatrix a N by 2 matrix, where N is any integer greater than 1.
	 */
	public MatrixCircleSolver(Matrix orgMatrix) {
		this.matrixOfPoints = orgMatrix;
	}

	
	
	
	/**
	 * Initializes and finds all the transformation matrices needed.
	 */
	private void initMatrixTransformations() {
		
		this.yMatrix = new Matrix(this.matrixOfPoints.getNumberOfRows(), 1);
		
		this.getYMatrix();
						
		this.squareTranspose = transpose(this.square4);
				
		this.mTransposeXM = crossMultip(squareTranspose, square4);
						
		this.mTransposeXMInverse = Inverse.solveIverse(mTransposeXM);
	}
	
	
	
	
	/**
	 * Solves the matrix this.M for a matrix of unknown variable for a circle that 
	 * best fits the point given from the matrix this.M.
	 * 
	 * @return solution M.getColumnLength() by 1 matrix of solutions for each unknown.
	 */
	public Matrix solveCircle() {
		
		Matrix mTransposeM2;
				
		this.initSquare4(this.matrixOfPoints);
		
		this.initMatrixTransformations();
				
		mTransposeM2 = this.crossMultip(mTransposeXMInverse, squareTranspose);
		
		return formattedSolution(this.crossMultip(mTransposeM2, this.yMatrix));
	}
	
	
	
	
	/**
	 * Formats the final solution matrix.
	 * 
	 * @param a 4 by 1 matrix that contains the solution for the best-fitted circle.
	 * @return solution a 4 by 1 matrix where the array at index...
	 * 			1. Constant that needs to be made to equal 1.
	 * 			2. The x-coordinate for the center of the circle.
	 * 			3. The y-coordinate for the center of the circle.
	 * 			4. The radius of the circle.
	 */
	private Matrix formattedSolution(Matrix solution) {
		
		double d = this.getDeterminant();
		
		for (int i = 0; i < solution.getNumberOfRows(); i++) {
			solution.setValue(i, 0, solution.get(i, 0)/d);
		}
		
		return solution;
	}
	
	
	
	
	/**
	 * Transposes a matrix.
	 * 
	 * @param data : a  n by m matrix of points to be transposed
	 * @return a m by n matrix that is the transposed version of the input parameter.
	 */
	private Matrix transpose(Matrix data) {
		
		Matrix dataTranspose = new Matrix(data.getNumberOfColumns(),data.getNumberOfRows());
		
		for (int i = 0; i < data.getNumberOfRows(); i++) {
			for(int j = 0; j < data.getNumberOfColumns(); j++) {
				dataTranspose.setValue(j,i, data.get(i,j));
			}
		}
		return dataTranspose;
	}
	
	
	
	
	/**
	 * Matrix multiplication of a and b. 
	 * 
	 * @param a - n by m matrix
	 * @param b - m by k matrix
	 * @return n by k matrix
	 */
	private Matrix crossMultip(Matrix a, Matrix b) {
		
		if (a.getNumberOfColumns() != b.getNumberOfRows()) {
			System.out.printf("ERROR: a.numOfColumns => %d != %d => b.numOfRows\n",
					a.getNumberOfColumns(),b.getNumberOfRows());
			return null;
		}
		
		Matrix product = new Matrix(a.getNumberOfRows(), b.getNumberOfColumns());
		
		double cell = 0;
		
		for (int row = 0; row < product.getNumberOfRows(); row++) {
			for (int column = 0; column < product.getNumberOfColumns(); column++) {
				for(int i = 0; i < b.getNumberOfRows(); i++) {
					cell += a.get(row,  i) * b.get(i, column);
				}
				product.setValue(row, column, cell);
				cell = 0;
			}
		}
		return product;
	}
	
	
	
	
	/**
	 * Given a N by 2 matrix that holds the x-coordinates in the 0th column and y-coordinates
	 * in the 1st column, initializes a matrix that can be used to solve for the solution Matrix 
	 * that holds | constant, x value for circle center, y value for circle center, radius |
	 * 
	 * After initialized, this.square 4 will be a 4 by 4 matrix where the first column is calculated 
	 * using x^2+y^2 , the second column is the x-coordinate, the third column is the y-coordinate, 
	 * the last column are all equal to 1.
	 * 
	 * @param m : a N by 2 matrix that holds Cartesian coordinates in the 0th and 1st column.
	 */
	public void initSquare4(Matrix m) {
		
		this.square4 = new Matrix(m.getNumberOfRows()+1,4);
		double x, y;
		
		for (int i =1; i < square4.getNumberOfRows(); i++) {
			
			x = m.getXAtRow(i-1);
			y = m.getYAtRow(i-1);
			
			this.square4.setValue(i, 0, (Math.pow(x, 2)+Math.pow(y, 2))); // Column 1
			this.square4.setValue(i, 1, x); // Column 2
			this.square4.setValue(i, 2, y); // Column 3
			this.square4.setValue(i, 3, 1); // Column 4
		}
	}
	
	
	
	
	/**
	 * Initializes the yMatrix variable that holds the y-coordinates from the points
	 * given.
	 */
	private void getYMatrix() {
		
		for(int i = 0; i < yMatrix.getNumberOfRows(); i++) {
			this.yMatrix.setValue(i, 0, this.matrixOfPoints.getYAtRow(i));
		}
	}
	
	
	
	
	/**
	 * @return a matrix that is formatted is a way that can be solved for the best
	 * 		   fitted circle.
	 */
	public Matrix getSquare4()  { 
		
		if (this.square4 == null) {
			this.initSquare4(this.matrixOfPoints);
		}
		return this.square4;
	}
	
	
	
	
	/**
	 * The determinant of a matrix A can be found by multiplying a matrix by its transpose AT.
	 * In other words, det(A AT) = n, where n in a real number.
	 * 
	 * @return the determinant of the square4 matrix.
	 */
	public double getDeterminant()  {
		return Inverse.getDeterminant(crossMultip(transpose(this.square4), this.square4));
	}
	
	
	
	
	/**
	 * @param m a n by m matrix
	 * @return the determinant of the parameter m
	 */
	public double getMatrixDeterminant(Matrix m) {
		return Inverse.getDeterminant(m);
	}
}
