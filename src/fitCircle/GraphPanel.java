package fitCircle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import matrixSolvers.*;

/**
 * This class sets up a JPanel with a GridView Layout. The GridView acts as a grid where
 * each square corresponds to a point. 
 * 
 * This class implements a best-fitted line program, where given a selected number of 
 * points, or squares, the program will plot the best fitting line with those given points.
 * 
 * This is accomplished using a matrix to solve a system of equations that corresponds to a 
 * circle equation in general form.
 * 
 * @author Austin Schladant
 *
 */

public class GraphPanel extends JPanel implements ActionListener {
	
	public GraphFunctions function = new GraphFunctions();

	private JButton generate;
	private JButton reset;
	
	private HashMap<GraphButton,Point> allButtons = new HashMap<GraphButton,Point>();
	private HashMap<Point,GraphButton> selectedButtons = new HashMap<Point,GraphButton>();
	
	private boolean isSelected = false;
	
	private double[] solution;
	
	Ellipse2D circle;
	
	
	
	
	/**
	 * @param generate JButton object that is used for the "Generate" button
	 * @param reset JButton object that is used for the "Reset" button
	 */
	public GraphPanel(JButton generate, JButton reset) {
		super();
		this.generate = generate;
		this.reset = reset;
		
		this.generate.addActionListener(this);
		this.reset.addActionListener(this);
		
		initUi();
	}
	
	
	
	
	/**
	 * Performs an action depending on what is selected.
	 * 
	 * If "Generate" is selected, then the program will find the best-fitted circle for the
	 * selected points in the grid.
	 * If "Reset" is selected, then the program will reset the UI and reset the saved points.
	 * If a grey point is selected, then the program will make the point blue and add it to the
	 * selected points list.
	 * If a blue point is selected, then the program will make the point grey and remove it from
	 * the selected points list.
 	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		JButton b = (JButton) e.getSource();
		
		if (b.equals(this.generate)) {
			
			isSelected = true;
			
			findSolution();
			
			repaint();
			
		} else if (b.equals(this.reset)){
			
			doReset();
			
		} else {
			GraphButton selected = (GraphButton) b;
			
			if (selected.isGrey()) {
				addBlue(selected);
			} else {
				removeBlue(selected);
			}
		}
	}
	
	
	
	
	/**
	 * Finds a solution for the best-fit circle depending on the number of points selected.
	 */
	public void findSolution() {
		
		solution = new double[4];
		
		if (selectedButtons.size() == 2) {
			findSolutionForTwoPoints();
		} else if (selectedButtons.size() >= 3) {
			findSolutionForThreeOrMore();
		}
	}
	
	
	
	
	/**
	 * If only two point are selected, the best-fitted circle will be a circle with the
	 * center being the midpoint between the two points and the radius will be the distances
	 * from the points to the center of the circle.
	 */
	private void findSolutionForTwoPoints() {
		
		GraphButton g1 = (GraphButton)selectedButtons.values().toArray()[0];
		GraphButton g2 = (GraphButton)selectedButtons.values().toArray()[1];
		
		solution[0] = 1;
		solution[1] = function.midpoint(
				g1.getCenterX(), g1.getCenterY(), g2.getCenterX(), g2.getCenterY()).getX();
		solution[2] = function.midpoint(
				g1.getCenterX(), g1.getCenterY(), g2.getCenterX(), g2.getCenterY()).getY();
		solution[3] = function.distance(g1.getCenter(), g2.getCenter()) / 2;
		
		System.out.printf("%f   |   %f   |   %f   |   %f\n", 
				solution[0],solution[1],solution[2],solution[3]);
	}
	
	
	
	
	/**
	 * Finds the best fitting circle when 3 or more points are selected.
	 * 
	 * For each subset of size 3 from the set of all points selected, finds the best fitting
	 * circle for each subset, then takes the average of all variables in each best fitting 
	 * circle. 
	 */
	private void findSolutionForThreeOrMore() {
		
		
		Point[] arrayOfPoints = getArrayOfSelectedButtons();
		Point[] subArray = new Point[3];
		
		ArrayList<double[]> listOfSolutions = new ArrayList<double[]>();
		
		for (int i = 0; i < arrayOfPoints.length-2; i++) {
			
			for (int j = i+1; j < arrayOfPoints.length-1; j++) {
				
				for (int k = j+1; k < arrayOfPoints.length; k++) {
					
					subArray[0] = arrayOfPoints[i];
					subArray[1] = arrayOfPoints[j]; 
					subArray[2] = arrayOfPoints[k];
					
					listOfSolutions.add(findSolutionForThreePoints(subArray));
				}
			}
		}
		findAverageSolution(listOfSolutions);
	}
	
	
	
	
	/**
	 * @param p an array of 3 points
	 * @return the solution in the form of a double[] where the 0th index is a constant
	 * 		    the 1st index is the x-value of the center, the 2nd index is the y-value
	 * 			of the center, and the 3rd index in the radius of the circle. 
	 */
	private double[] findSolutionForThreePoints(Point[] p) {
		
		double[] arr = new double[4];

		if ((p[0].getX() == p[1].getX() && p[0].getX() == p[2].getX())
				|| (p[0].getY() == p[1].getY() && p[0].getY() == p[2].getY())) {

			arr[0] = 1;
			arr[1] = function.findCentroid(p).getX();
			arr[2] = function.findCentroid(p).getY();
			arr[3] = function.findAverageRadius(function.findCentroid(p), p);

		} else {
			arr = Determinant.testBundle(pointArrayToMatrix(p));
		}
		
		return arr;
	}
	
	
	
	
	/**
	 * Takes the average of each variable to find the best fitting circle for all selected points.
	 * Then saves those value in the final solution array called solution.
	 * @param a an ArrayList of double[], where each double[] is of size 4.
	 */
	private void findAverageSolution(ArrayList<double[]> a) {
		
		double sum0 = 0, sum1 = 0, sum2 = 0, sum3 = 0;
		
		for(int i = 0; i < a.size(); i++) {
			sum0 += a.get(i)[0];
			sum1 += a.get(i)[1];
			sum2 += a.get(i)[2];
			sum3 += a.get(i)[3];
		}
		
		sum0 /= a.size();
		sum1 /= a.size();
		sum2 /= a.size();
		sum3 /= a.size();
		
		solution[0] = sum0;
		solution[1] = sum1;
		solution[2] = sum2;
		solution[3] = sum3;
	}
	
	
	
	
	/**
	 * Sets the point selected to blue, adds the selected button to the list of selected buttons
	 * and finds the center point of the selected button.
	 * 
	 * @param b GraphButton object that is selected
	 */
	private void addBlue(GraphButton b) {
		b.setBlue();
		selectedButtons.put(allButtons.get(b), b);
		b.setButtonCenter();
	}
	
	
	
	
	/**
	 * Sets the point selected to grey and removes the selected button from the list of selected
	 * buttons.
	 * 
	 * @param b GraphButton object that is selected
	 */
	private void removeBlue(GraphButton b) {
		b.setGrey();
		selectedButtons.remove(allButtons.get(b));
	}
	
	
	
	
	/**
	 * Sets all points selected to grey.
	 */
	private void doReset() {
		for(Point p : selectedButtons.keySet()) {
			selectedButtons.get(p).setGrey();
		}
		
		selectedButtons = new HashMap<Point, GraphButton>();
		
		invalidate();
		validate();
		repaint();
	}
	
	
	
	
	/**
	 * Depending on if the generate button was selected or the reset button was selected,
	 * the program either graphs the best-fit circle or erases the board completely.
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		if (isSelected) { 
			
			circle = new Ellipse2D.Double(solution[1],solution[2], 0, 0);
			
			circle.setFrameFromCenter(solution[1],solution[2],
					solution[1]-solution[3], solution[2]-solution[3]);
			g2.draw(circle);
			
			isSelected = false;
		}			
	}
	
	
	
	
	/**
	 * Takes the center-coordinates of each button selected and saves them into an 
	 * array of Points.
	 * 
	 * @return an array of points.
	 */
	private Point[] getArrayOfSelectedButtons() {
		
		Point[] p = new Point[selectedButtons.size()];
		int i = 0;
		
		for (GraphButton b : selectedButtons.values()) {
			p[i++] = b.getCenter();
		}
		
		return p;
	}

	
	
		
	/**
	 * @return a matrix where each row in the matrix corresponds to a point at an index.
	 */
	private Matrix pointArrayToMatrix(Point[] p) {
		
		Matrix m = new Matrix(p.length, 2);
		
		for (int i = 0; i < p.length; i++) {
			m.setPoint(i, p[i]);
		}
		
		return m;
	}
	
	
	
	
	/**
	 * Initializes the UI by setting the layer, the size, and the background color.
	 */
	private void initUi() {
		this.setLayout(new GridLayout(20,20));
		this.setPreferredSize(new Dimension(516,516));
		this.setMaximumSize(new Dimension(516,516));
		this.setMinimumSize(new Dimension(516,516));
		this.setBackground(Color.WHITE);
		
		initGrid();
	}
	
	
	
	
	/**
	 * Initializes the grid by adding a 20 by 20 grid of GraphButtons and adding those
	 * buttons to a list of all buttons.
	 */
	private void initGrid() {
		
		for(int column = 0; column < 20; column++) {
			for(int row = 0; row < 20; row++) {
				GraphButton b = new GraphButton(this);
				add(b);
				allButtons.put(b,new Point(row, column));
			}
		}
	}
}
