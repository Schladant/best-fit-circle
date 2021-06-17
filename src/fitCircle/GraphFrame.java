package fitCircle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class sets up the UI for the application, which includes the borders, 
 * the "Generate" and "Reset" buttons, and a JPanel for the GraphPanel class.
 * 
 * @author Austin Schladant
 *
 */
public class GraphFrame extends JFrame {

	private JButton generateButton;
	private JButton resetButton;
	
	public GraphFrame() {
		super();
		initUi();
	}
	
	private void initUi() {
		
		BorderLayout border = new BorderLayout();
				
		setLayout(border);
		
		setSize(700,700);
		
		addWhiteBorders();
		
		addGenerateButton();
		
		GraphPanel p = new GraphPanel(generateButton, resetButton);
		
		this.add(p, BorderLayout.CENTER);
				
		setResizable(false);
		
		setTitle("Graph");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		validate();
		
	}
	
	private void addWhiteBorders() {
		JPanel white = new JPanel();
		JPanel white2 = new JPanel();
		JPanel white3 = new JPanel();
		
		white.setBackground(Color.WHITE);
		white2.setBackground(Color.WHITE);
		white3.setBackground(Color.WHITE);
		white.setPreferredSize(new Dimension(30, this.getHeight()));
		white2.setPreferredSize(new Dimension(this.getWidth(), 30));
		white3.setPreferredSize(new Dimension(30,this.getHeight()));
		this.add(white, BorderLayout.WEST);
		this.add(white2, BorderLayout.NORTH);
		this.add(white3, BorderLayout.EAST);
	}
	
	private void addGenerateButton() {
		JPanel generate = new JPanel();
		
		generate.setLayout(new GridLayout());
		
		generate.add(new JButton("Generate"), 0);
		generate.add(new JButton("Reset"),1);
		
		generateButton = (JButton)generate.getComponent(0);
		resetButton = (JButton)generate.getComponent(1);
		
		generateButton.setBackground(Color.LIGHT_GRAY);
		resetButton.setBackground(Color.LIGHT_GRAY);
		
		generateButton.setPreferredSize(new Dimension(100, 40));
		resetButton.setPreferredSize(new Dimension(100, 40));
		generate.setBackground(Color.white);
		
		generate.setPreferredSize(new Dimension(this.getWidth(), 50));
		
		this.add(generate, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			JFrame x = new GraphFrame();
			x.setVisible(true);
		});
	}

}
