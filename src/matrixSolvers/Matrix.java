package matrixSolvers;

import java.awt.Point;

/**
 * This implements a matrix using a 2D array of double. Each matrix has array.length rows
 * and array[row].length columns. Each matrix has a MatrixSolver, which can be used to 
 * solve the Matrix as a system of equations with matrix.getRows() number of unknown parameters.
 * 
 * @author Austin Schladant
 * 
 */
public class Matrix {
	
	private final int X_COLUMN = 0;
	private final int Y_COLUMN = 1;
	
	private double[][] m; // pairs of point, each row is a pair
	
	private double determinant;
	
	private MatrixCircleSolver s;
	
	
	
	
	/**
	 * @param i number of rows
	 * @param j number of columns
	 * @throws Exception for formatting
	 */
	public Matrix(int rows, int columns) {
		this.m = new double[rows][columns];
		
		s = new MatrixCircleSolver(this);
	}
	 
	
	
	
	/**
	 * For each row in the matrix, makes array[0] equal to the 
	 * x-coordinate and array[1] equal to the y-coordinate
	 * 
	 * @param p an array of points to be stored in a matrix
	 */
	public Matrix(Point[] p) {
		this.m = new double[p.length][2];
		for (int i = 0; i < p.length; i++) {
			
		}
	}
	
	
	
	
	/**
	 * @param i row in matrix
	 * @param j column in matrix
	 * @param value to be stored in the ith row and jth column
	 */
	public void setValue(int i, int j, double value) {
		if (i >= m.length || j >= m[0].length) {
			System.out.println("ERROR: Out of Bounds");
			return;
		} 
		m[i][j] = value;
	}
	
	
	
	
	/**
	 * Sets the 0th index in the row equal to the x-coordinate.
	 * Sets the 1st index in the row equal to the y-coordinate
	 * 
	 * @param row where the point is to be saved.
	 * @param p point to be saved
	 */
	public void setPoint(int row, Point p) {
		this.m[row][0] = p.getX();
		this.m[row][1] = p.getY();
	}
	
	
	
	
	/**
	 * @param i row of the matrix
	 * @param j column of the matrix
	 * @return value stored in row i and column j
	 */
	public double get(int i, int j) {
		return m[i][j];
	}
	
	
	
	
	/**
	 * Gets all the x values stored in the matrix iff there
	 * are two columns in the matrix, i.e. the matrix was initialized
	 * using N rows and 2 columns, where N is an integer greater than 1.
	 * 
	 * @return an array of all the values stored in the first column
	 */
	public double[] getXValues() throws Exception {

		double[] x = new double[this.m.length];
		for (int i = 0; i < this.m[0].length; i++) {
			x[i] = m[i][0];
		}
		
		return x;
	}
	
	
	
	
	/**
	 * Gets all the y values stored in the matrix iff there
	 * are two columns in the matrix, i.e. the matrix was initialized
	 * using N rows and 2 columns, where N is an integer greater than 1.
	 * 
	 * @return array of all the values stored in the second column
	 */
	public double[] getYValues() throws Exception {
		
		double[] y = new double[this.m.length];
		
		for (int i = 0; i < this.m[1].length; i++) {
			y[i] = m[i][1];
		}
			
		return y;
	}
	
	
	
	/**
	 * @param row the row of the point needed
	 * @return the x value in that row
	 */
	public double getXAtRow(int row) {
		return this.m[row][X_COLUMN];
	}
	
	
	
	
	/**
	 * @param row the row of the point needed
	 * @return the y value in that row
	 */
	public double getYAtRow(int row) {
		return this.m[row][Y_COLUMN];
	}
	
	
	
	
	/**
	 * @return the number of rows in the matrix
	 */
	public int getNumberOfRows() {
		return m.length;
	}
	
	
	
	
	/**
	 * @return the number of columns in the matrix
	 */
	public int getNumberOfColumns() {
		return m[0].length;
	}
	
	
	
	
	/**
	 * Prints the matrix in a specified format.
	 */
	public void printMatrix() {
		
		System.out.println("\n");
		
		if (m.length == 1) {
			for (int i = 0; i < m.length; i++) {
				System.out.printf(" | %10.2f | %d\n", m[i][0], i);
			}
		}
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (j == m[i].length-1)
					System.out.printf(" %20.2f | %d\n\n", m[i][j], i);
				else if (j == 0)
					System.out.printf(" | %20.2f", m[i][j]); 
				else 
					System.out.printf(" %20.2f ", m[i][j]);
			}
		}
		System.out.println("\n");
	}
	
	public void setDeterminant(double d) { this.determinant = d; }
	
	public double getDeterminant() { return this.determinant; }
	
	public Matrix solve() { return s.solveCircle(); }
	
	public MatrixCircleSolver getSolver() { return this.s; }

}
