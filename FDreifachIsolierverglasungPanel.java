import java.awt.*;
import javax.swing.*;

public class FDreifachIsolierverglasungPanel extends EMehrscheibenIsolierverglasungPanel{
	
	private JTextField ersteScheibeTF = new JTextField("0.006",5);
	private JTextField zweiteScheibeTF = new JTextField("0.004",5);
	private JTextField dritteScheibeTF = new JTextField("0.006",5);

	private JTextField ScheibenZwischenraum1TF = new JTextField("0.012",5);
	private JTextField ScheibenZwischenraum2TF = new JTextField("0.012",5);

	private JTextField heighATF = new JTextField("1.2",5);
	private JTextField widthBTF = new JTextField("1.4",5);

	public FDreifachIsolierverglasungPanel() {
		
		this.setLayout(new BorderLayout());
		int columns = 7;
	
		JPanel p1 = new JPanel(new GridLayout(columns, 1));
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>1</sub> =</span></html>"), BorderLayout.WEST);
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>2</sub> =</span></html>"), BorderLayout.WEST);
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>3</sub> =</span></html>"), BorderLayout.WEST);
		
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>SZR,1</sub> =</span></html>"), BorderLayout.WEST);
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>SZR,2</sub> =</span></html>"), BorderLayout.WEST);
		
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>a</i> =</span></html>"), BorderLayout.WEST);
		p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>b</i> =</span></html>"), BorderLayout.WEST);

		
		JPanel p2 = new JPanel(new GridLayout(columns, 1));
		p2.add(this.ersteScheibeTF, BorderLayout.CENTER);
		p2.add(this.zweiteScheibeTF, BorderLayout.CENTER);
		p2.add(this.dritteScheibeTF, BorderLayout.CENTER);
		
		p2.add(this.ScheibenZwischenraum1TF, BorderLayout.CENTER);
		p2.add(this.ScheibenZwischenraum2TF, BorderLayout.CENTER);

		p2.add(this.heighATF, BorderLayout.CENTER);
		p2.add(this.widthBTF, BorderLayout.CENTER);

		
		JPanel p3 = new JPanel(new GridLayout(columns, 1));
		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);
		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);
		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);
		
		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);
		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);

		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);
		p3.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>"), BorderLayout.EAST);
		
		this.add(p1, BorderLayout.WEST);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.EAST);
	}

	@Override
	public DMehrscheibenIsolierverglasung erstelleDMehrscheibenIsolierverglasung() {
	    double d1 = Double.parseDouble(this.ersteScheibeTF.getText());
	    double d2 = Double.parseDouble(this.zweiteScheibeTF.getText());
	    double d3 = Double.parseDouble(this.dritteScheibeTF.getText());
	    double szr1 = Double.parseDouble(this.ScheibenZwischenraum1TF.getText());
	    double szr2 = Double.parseDouble(this.ScheibenZwischenraum2TF.getText());
	    double a = Double.parseDouble(this.heighATF.getText());
	    double b = Double.parseDouble(this.widthBTF.getText());

	    return new GDreifachIsolierverglasung(d1, d2, d3, szr1, szr2, a, b);
	}
}