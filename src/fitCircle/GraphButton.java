package fitCircle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * This class adds functionalities to the JButton class. Each point, or button, on the
 * grid is equal to the point at the center of the GraphButton. 
 * 
 * This class also holds the different icons used to make the points blue or grey.
 * 
 * This class can be used to determine if a button is currently selected, or grey, or if 
 * it is not selected, or blue.
 * 
 * @author Austin Schladant
 *
 */
public class GraphButton extends JButton {
	
	public final static int WIDTH = 32;
	public final static int HEIGHT = 29;
	
	private Point center;
	private Icon blue;
	private Icon grey;
	private boolean g = true;
	
	
	
	
	/**
	 * @param a the action listener for this button
	 */
	public GraphButton(ActionListener a) {
		super();
		
		this.addActionListener(a);
		
		initIcons();
		initUi();
	}
	
	
	/**
	 * @return the mid point between the top-left and bottom-right corners
	 */
	private void calculateCenter() {
		
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		
		int bottomX = (int)Math.round(x+WIDTH);
		int bottomY = (int)Math.round(y+HEIGHT);
		
		this.center = new Point((x+bottomX)/2, (y+bottomY)/2);
	}
	
	
	/**
	 * @return a boolean that is true if the button is grey and false if it is blue
	 */
	public boolean isGrey() {
		return g;
	}
	
	
	public void setBlue() {
		this.setIcon(blue);
		g = false;
	}
	
	
	public void setGrey() {
		this.setIcon(grey);
		g = true;
	}
	
	
	public void setButtonCenter() {
		this.calculateCenter();
	}
	
	public Point getCenter() {
		return this.center;
	}
	
	public int getCenterX() {
		return this.center.x;
	}
	
	public int getCenterY() {
		return this.center.y;
	}
	
	
	private void initIcons() {
		grey = new ImageIcon("src/resources/greySquare.png");
		blue = new ImageIcon("src/resources/blueSquare.png");
	}
	
	private void initUi() {
		this.setBackground(Color.white);
		this.setIcon(grey);
		this.setBorder(BorderFactory.createEmptyBorder(
				4, 4, 4, 4
			));
		this.setPreferredSize(new Dimension(16, 16));
		this.setMaximumSize(new Dimension(24, 24));
		this.setMinimumSize(new Dimension(12, 12));
	}
}
