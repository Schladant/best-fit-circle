package matrixSolvers;

/**
 * This class can be used to: 
 * 		(1) find the inverse of a matrix 
 * 		(2) get the determinant of a 3 by 3 matrix.
 * 
 * 
 * @author Austin Schladant
 *
 */
public class Inverse {
	public Inverse() {
		
	}
	
	
	
	
	/**
	 * @param m the matrix that needs to the inverse found
	 * @return the inverse of the parameter matrix m
	 */
	public static Matrix solveIverse(Matrix m) {
		
		Inverse inv = new Inverse();
		
		Matrix coFactorMatrix = new Matrix(m.getNumberOfRows(),m.getNumberOfColumns());
				
		for (int i = 0; i < m.getNumberOfRows(); i++) {
			for (int j = 0; j < m.getNumberOfColumns(); j++) {
				
				Matrix subMatrix = inv.getSubMatrix(i,j,m);
				
				if(inv.getIsNegative(i, j))
					coFactorMatrix.setValue(i, j, -1*inv.solve2x2(subMatrix));
				else
					coFactorMatrix.setValue(i, j, inv.solve2x2(subMatrix));
			}
		}
		return inv.getAdjugateMatrix(coFactorMatrix);
	}
	
	
	
	
	/**
	 * Determines the adjugate matrix of the cofactor matrix.
	 * 
	 * @param coFactor the cofactor matrix
	 * @return the adjugate matrix
	 */
	public Matrix getAdjugateMatrix(Matrix coFactor) {
		
		Matrix adjugate = new Matrix(coFactor.getNumberOfRows(),coFactor.getNumberOfColumns());
		
		for(int i = 0; i < adjugate.getNumberOfRows(); i++) {
			for(int j = 0; j < adjugate.getNumberOfColumns(); j++) {
				adjugate.setValue(j, i, coFactor.get(i, j));
			}
		}
		return adjugate;
	}
	
	
	
	
	/**
	 * @param m a 3 by 3 matrix 
	 * @return the determinant of that matrix.
	 */
	public static double getDeterminant(Matrix m) {
		
		Inverse inv = new Inverse();
		
		double d = 0;
		
		d += inv.determinantHelper(m, 0, 0);
		d += inv.determinantHelper(m, 0, 1);
		d += inv.determinantHelper(m, 0, 2);
		
		return d;
		
	}
	
	
	
	
	/**
	 * @param m a 2 by 2 matrix
	 * @return the determinant of the parameter matrix 
	 */
	public double solve2x2(Matrix m) {
		return (m.get(0, 0)*m.get(1,1)) - (m.get(1,0)*m.get(0,1));
	}
	
	
	
	
	/**
	 * Gets the sub matrix of the parameter matrix m. The sub matrix is a matrix that
	 * doesn't include the ith row and jth column of the original matrix m.
	 * 
	 * @param i the row to not be included
	 * @param j the column to not be included
	 * @param m the original matrix
	 * @return the sub matrix, which is the original matrix excluding row i and column j
	 */
	public Matrix getSubMatrix(int i, int j, Matrix m) {
		
		Matrix sub = new Matrix(2,2);
		
		if (i == 0) {
			switch(j) {
				case 0:
					sub.setValue(0,0, m.get(1,1));
					sub.setValue(0,1, m.get(1,2));
					sub.setValue(1,0, m.get(2,1));
					sub.setValue(1,1, m.get(2,2));
					break;
				case 1:
					sub.setValue(0,0, m.get(1,0));
					sub.setValue(0,1, m.get(1,2));
					sub.setValue(1,0, m.get(2,0));
					sub.setValue(1,1, m.get(2,2));
					break;
				case 2:
					sub.setValue(0,0, m.get(1,0));
					sub.setValue(0,1, m.get(1,1));
					sub.setValue(1,0, m.get(2,0));
					sub.setValue(1,1, m.get(2,1));
					break;
			}
		} else if (i == 1) {
			switch(j) {
			
				case 0:
					sub.setValue(0,0, m.get(0,1));
					sub.setValue(0,1, m.get(0,2));
					sub.setValue(1,0, m.get(2,1));
					sub.setValue(1,1, m.get(2,2));
					break;
				case 1:
					sub.setValue(0,0, m.get(0,0));
					sub.setValue(0,1, m.get(0,2));
					sub.setValue(1,0, m.get(2,0));
					sub.setValue(1,1, m.get(2,2));
					break;
				case 2:
					sub.setValue(0,0, m.get(0,0));
					sub.setValue(0,1, m.get(0,1));
					sub.setValue(1,0, m.get(2,0));
					sub.setValue(1,1, m.get(2,1));
					break;
			}
		} else if (i == 2) {
			switch(j) {
				case 0:
					sub.setValue(0,0, m.get(0,1));
					sub.setValue(0,1, m.get(0,2));
					sub.setValue(1,0, m.get(1,1));
					sub.setValue(1,1, m.get(1,2));
					break;
				case 1:
					sub.setValue(0,0, m.get(0,0));
					sub.setValue(0,1, m.get(0,2));
					sub.setValue(1,0, m.get(1,0));
					sub.setValue(1,1, m.get(1,2));
					break;
				case 2:
					sub.setValue(0,0, m.get(0,0));
					sub.setValue(0,1, m.get(0,1));
					sub.setValue(1,0, m.get(1,0));
					sub.setValue(1,1, m.get(1,1));
					break;
				}
		}
		
		return sub;
	}
	
	
	
	
	/**
	 * Finds the determinant of the 2 by 2 matrix without row i and column j and multiplies
	 * it by the value at row i and column j.
	 * Depending on the position of i and j, the value will either be positive or negative.
	 * 
	 * @param m a 3 by 3 matrix
	 * @param i the ith row of the matrix
	 * @param j the jth column of the matrix
	 * @return the determinant of the matrix at row i and column j
	 */
	public double determinantHelper(Matrix m, int i, int j) {
	
		Inverse n = new Inverse();
		
		if (n.getIsNegative(i, j)) {
			return (-1*m.get(i,j)*n.solve2x2(n.getSubMatrix(i, j, m)));
		}
		return (m.get(i, j) * n.solve2x2(n.getSubMatrix(i, j, m)));
	}

	
	
	
	/**
	 * @param i the ith row 
	 * @param j the jth column
	 * @return true if the value is negative and false otherwise
	 */
	public boolean getIsNegative(int i, int j) {
		if(i == 0 && j == 1)
			return true;
		else if (i ==1 && j == 0) 
			return true;
		else if (i == 1 && j == 2) 
			return true;
		else if (i == 2 && j == 1)
			return true;
		else
			return false;
	}
	
}
