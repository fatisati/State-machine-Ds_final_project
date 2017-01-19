import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import GraphViz.GraphDrawer;
import GraphViz.GraphViz;

public class UI extends JFrame implements ActionListener{

	public JTextArea textArea, enter;
	JButton build, show, remove, hasLoop;
	JScrollPane scroll;
	JTextField search;

	int border;
	int scrx, scry;
	Font font;

	Vector<StateMachine> machines;

	public UI() {
		// TODO Auto-generated constructor stub
		machines = new Vector<>();

		border = 50;
		scrx = 1000;
		scry = 1000;
		font = new Font("Serif", Font.PLAIN, 30);
		
		search = new JTextField();
		search.setSize(850, 50);
		search.setLocation(border, 750);
		search.setFont(font);
		search.addActionListener((ActionListener) this);
		//search.addKeyListener((KeyListener) this);

		textArea = new JTextArea();
		textArea.setSize(425, 450);
		textArea.setLocation(50+425, 200);
		textArea.setFont(font);
		
		enter = new JTextArea();
		enter.setSize(400, 450);
		enter.setLocation(border, 200);
		enter.setFont(font);
		
		scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setSize(425, 450);
		scroll.setLocation(50+425, 200);

		build = new JButton("Build");
		build.setSize(200, 50);
		build.setLocation(border, 850);
		build.setFont(font);
		build.addActionListener((ActionListener) this);
		
		show = new JButton("show");
		show.setSize(200, 50);
		show.setLocation(border+225, 850);
		show.setFont(font);
		show.addActionListener((ActionListener) this);
		
		remove = new JButton("rem loop");
		remove.setSize(200, 50);
		remove.setLocation(border+450, 850);
		remove.setFont(font);
		remove.addActionListener((ActionListener) this);
		
		hasLoop = new JButton("has loop");
		hasLoop.setSize(200, 50);
		hasLoop.setLocation(border+675, 850);
		hasLoop.setFont(font);
		hasLoop.addActionListener((ActionListener) this);
		

		add(scroll);
		add(build);
		add(show);
		add(remove);
		add(hasLoop);
		add(search);
		add(enter);
		setLayout(null);
		setSize(scrx, scry);
		setVisible(true);

	}

	public static int index(String inp) {

		int ans = 0;
		for (int i = 0; i < inp.length(); i++) {
			ans += inp.charAt(i);
		}
		return ans;
	}

	public void build(String inp) {

		int table[][];
		int row, coloumn;

		String lines[] = inp.split("\\r?\\n");

		row = lines.length;

		coloumn = lines[0].split(" ").length - 1;

		table = new int[coloumn][128];// first is -
		
		for(int i=0; i<coloumn; i++){
			for(int j=0; j<128; j++){
				table[i][j] = -1;
			}
		}

		for (int i = 1; i < row-1; i++) {

			String parts[] = lines[i].split(" ");

			for (int j = 1; j <= coloumn; j++) {
						
				table[j - 1][index(parts[j])] = i - 1;

			}
		}
		
		String parts[] = lines[row-1].split(" ");
		
		boolean finalStates[] = new boolean[coloumn];
		
		for(int i=0; i<coloumn; i++){
			finalStates[i] = false;
		}
		for(int i=0; i<parts.length; i++){
			
			finalStates[Integer.parseInt(parts[i])] = true;
		}
		
		StateMachine sm = new StateMachine(machines.size() ,this, coloumn, table, 0, finalStates);
		textArea.append("\nmachine "+sm.name+" created\n");
		machines.addElement(sm);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == build){
			build(enter.getText());

		}
		
		else if(arg0.getSource() == show){
			
			for(StateMachine sm: machines){
				sm.show();
			}
		}
		
		else if(arg0.getSource() == remove){
			
			for(StateMachine sm: machines){
				sm.removeLoop();
			}
		}
		
		else if(arg0.getSource() == hasLoop){
			
			for(StateMachine sm: machines){
				
				textArea.append("state machine "+sm.name+"->");
				if(sm.hasLoop()){
					textArea.append("has loop\n");
				}
				else{
					textArea.append("doesn't have loop\n");
				}
			}
		}
		
		else if (arg0.getSource() == search) {
			
			String x = search.getText();
			textArea.append("this string is:\n");
			
			for(StateMachine sm: machines){
				
				if(sm.isValid(x)){
					textArea.append("valid ");
				}
				else{
					textArea.append("is not valid ");
				}
				textArea.append("in state machine "+sm.name+"\n");
				
			}
		}

	}

	public static void main(String[] args) {
		
		new UI();
	}

}
