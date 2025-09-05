import java.awt.*;
import javax.swing.*;

public class HZweifachIsolierverglasungPanel extends EMehrscheibenIsolierverglasungPanel{
	
		private JTextField ersteScheibeTF = new JTextField("0.006",5);
		private JTextField zweiteScheibeTF = new JTextField("0.006",5);

		private JTextField ScheibenZwischenraum1TF = new JTextField("0.012",5);

		private JTextField heighATF = new JTextField("1.2",5);
		private JTextField widthBTF = new JTextField("1.4",5);

		public HZweifachIsolierverglasungPanel() {
			
			this.setLayout(new BorderLayout());
			int columns = 5;
			
			JPanel p1 = new JPanel(new GridLayout(columns, 1));
			
			p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>1</sub> =</span></html>"), BorderLayout.WEST);
			p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>2</sub> =</span></html>"), BorderLayout.WEST);
			
			p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>d</i><sub>SZR,1</sub> =</span></html>"), BorderLayout.WEST);
			
			p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>a</i> =</span></html>"), BorderLayout.WEST);
			p1.add(new JLabel("<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>b</i> =</span></html>"), BorderLayout.WEST);


			JPanel p2 = new JPanel(new GridLayout(columns, 1));
			p2.add(this.ersteScheibeTF, BorderLayout.CENTER);
			p2.add(this.zweiteScheibeTF, BorderLayout.CENTER);
			p2.add(this.ScheibenZwischenraum1TF, BorderLayout.CENTER);
			p2.add(this.heighATF, BorderLayout.CENTER);
			p2.add(this.widthBTF, BorderLayout.CENTER);


			JPanel p3 = new JPanel(new GridLayout(columns, 1));
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
		    double szr1 = Double.parseDouble(this.ScheibenZwischenraum1TF.getText());
		    double a = Double.parseDouble(this.heighATF.getText());
		    double b = Double.parseDouble(this.widthBTF.getText());

		    return new IZweifachIsolierverglasung(d1, d2, szr1, a, b);
		}
}