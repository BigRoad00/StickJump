import java.awt.*;
import javax.swing.*;

public class Num extends JFrame{
public Num() {
	super("asdf");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container cp = getContentPane();
	
	cp.setLayout(new FlowLayout());
	
	JTextField tf1 = new JTextField(20);
	JTextField tf2 = new JTextField(30);
	
	cp.add(tf1);
	cp.add(tf2);
	
	setSize(300,200);
	setVisible(true);
	
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Num();
	}

}