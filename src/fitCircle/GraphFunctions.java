package fitCircle;

import java.awt.Point;

/**
 * This class implements basic functions, which includes:
 * 		(1) Finding the centroid of n points, where n is an integer.
 * 		(2) Finding the midpoint between 2 points.
 * 		(3) Finding the distance between 2 points.
 * 		(4) Finding the mean of n numbers.
 * 		(5) Finding the average radius between n points and the center.
 * 		(6) The choose function.
 * 		(7) The factorial function.
 * 
 * @author Austin Schladant
 *
 */

public class GraphFunctions {
	
	public GraphFunctions() {
		
		
	}
	
	
	
	
	/**
	 * @param p an array of points
	 * @return the centroid between all points in the parameter p.
	 */
	public Point findCentroid(Point[] p) {
		
		int[] x = new int[p.length], y = new int[p.length];

		for (int a = 0; a < p.length; a++) {
			x[a] = p[a].x;
			y[a] = p[a].y;
			System.out.printf("(%d, %d)\n", x[a],y[a]);
		}

		return new Point(mean(x), mean(y));
	}
	
	
	
	
	/**
	 * @param center the center point
	 * @param p an array of points
	 * @return the average of all the distances between the center and the points in parameter p
	 */
	public double findAverageRadius(Point center, Point[] p) {
		
		int sum = 0;
		
		for (int i = 0; i < p.length; i++) {
			sum += this.distance(center, p[i]);
		}
		return sum / p.length;
	}
	
	
	
	
	/**
	 * @param n the total number of elements in a set
	 * @param k the number of elements wanted in a subset
	 * @return the total number of ways you can have a subset with k elements from a set
	 * 		    of n elements
	 */
	public int choose(int n, int k) {
	
		int nFactorial = factorial(n);
		int kFactorial = factorial(k);
		int nMinuskFactorial = factorial(n-k);
		
		return (nFactorial / (kFactorial * nMinuskFactorial));
	}
	
	
	
	
	/**
	 * @param n number to find factorial
	 * @return the factorial of the parameter
	 */
	public int factorial(int n) {
		if (n >= 1)
            return n * factorial(n - 1);
        else
            return 1;
	}

	
	
	
	/**
	 * @param data array of ints
	 * @return the mean of the array of ints
	 */
	public int mean(int[] data) {
		int sum = 0;
		for(int i = 0; i < data.length; i++) {
			sum += data[i];
		}
		return (int)Math.round(sum/data.length);
	}
	
	
	
	
	/**
	 * @param x1 x-coordinate for Point A
	 * @param y1 y-coordinate for Point A
	 * @param x2 x-coordinate for Point B
	 * @param y2 y-coordinate for Point B
	 * @return the point half way between Point A and Point B
	 */
	public Point midpoint(int x1, int y1, int x2, int y2) {
		return new Point(((x1+x2)/2), ((y1+y2)/2));
	}
	
	
	
	
	/**
	 * @param x1 x-coordinate for Point A
	 * @param y1 y-coordinate for Point A
	 * @param x2 x-coordinate for Point B
	 * @param y2 y-coordinate for Point B
	 * @return The distance between Point A and Point B
	 */
	public double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	
	
	
	/**
	 * @param a Point in Cartesian coordinates
	 * @param b Point in Cartesian coordinates
	 * @return The distance between Point a and Point b
	 */
	public double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY()-b.getY(), 2));
	}

}
