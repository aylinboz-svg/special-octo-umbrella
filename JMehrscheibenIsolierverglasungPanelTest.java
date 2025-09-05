/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JMehrscheibenIsolierverglasungPanelTest extends JFrame implements ActionListener {
	
	public static void main(String[]args) {
	JMehrscheibenIsolierverglasungPanelTest	f = new JMehrscheibenIsolierverglasungPanelTest();
	f.setVisible(true);
	}
	
	private EMehrscheibenIsolierverglasungPanel zip = new HZweifachIsolierverglasungPanel();
	private EMehrscheibenIsolierverglasungPanel dip = new FDreifachIsolierverglasungPanel();
	private JButton applyB = new JButton("Apply");
	
	public JMehrscheibenIsolierverglasungPanelTest() {
		JPanel p1 = new JPanel();

		p1.add(this.zip);
		p1.add(this.dip);

		this.add(p1, BorderLayout.CENTER);
		this.add(this.applyB,BorderLayout.SOUTH);
		this.applyB.addActionListener(this);
		this.pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(this.zip.erstelleDMehrscheibenIsolierverglasung());
		System.out.println(this.dip.erstelleDMehrscheibenIsolierverglasung());
	}
}*/