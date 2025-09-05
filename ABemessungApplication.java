import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import inf.v3d.view.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ABemessungApplication extends JFrame implements ActionListener {
	private static final int GAP = 2; // alles links: Standardabstand

	
	private JPanel makeValueRow(String labelHtml, JLabel valueLabel, String unitHtml) {
		
		
	    JPanel row = new JPanel(new BorderLayout(6, 0));

	    JLabel left = new JLabel(labelHtml);               // Bezeichnung
	    JLabel unit = new JLabel(unitHtml);                // Einheit rechts

	    styleValueBox(valueLabel);                         // Kästchen-Look für den Wert

	    row.add(left, BorderLayout.WEST);
	    row.add(valueLabel, BorderLayout.CENTER);
	    row.add(unit, BorderLayout.EAST);
	    return row;
	}
	
	
	// Gibt einem JLabel den Box-/Ausgabefeld-Look
	private void styleValueBox(JLabel l) {
	    l.setHorizontalAlignment(SwingConstants.RIGHT);
	    l.setOpaque(true);                          // Hintergrund sichtbar
	    l.setBackground(new Color(200, 200, 200));    // dunkelgrau (alternativ: Color.DARK_GRAY)
	    l.setForeground(Color.BLACK);              // Text gut lesbar

	    l.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(new Color(150,150, 150)), // dezente Umrandung
	        BorderFactory.createEmptyBorder(1, 8, 1, 8)
	    ));
	}
	
	private static void pad(JComponent c, int top, int left, int bottom, int right) {
	    // fügt außen Abstand hinzu, ohne die bestehende TitledBorder zu verlieren
	    c.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createEmptyBorder(top, left, bottom, right),
	        c.getBorder()
	    ));
	}
	
	public static void main(String[] args) {
        System.out.println("Programm gestartet");
        ABemessungApplication ba = new ABemessungApplication();
        ba.setVisible(true);
    }
	
    private static void growWide(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension pref = c.getPreferredSize();
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));
        c.setMinimumSize(new Dimension(0, pref.height));
    }
    
    private static void addWithGap(JPanel parentYBox, Component c, int gapPx) {
        parentYBox.add(c);
        if (gapPx > 0) parentYBox.add(Box.createVerticalStrut(gapPx));
    }
    
    private static JPanel titledBox(String titleHtml) {
        JPanel p = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE, pref.height);
            }
        };
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder(titleHtml));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }
    
    private ViewerPanel viewerPanel = new ViewerPanel();

    private static final String[] CONSTRUCTION_TYPES = {  "DreifachIsolierverglasung","ZweifachIsolierverglasung" };

    private JPanel sectionPanel = new JPanel(new BorderLayout());
    
    private JComboBox MehrscheibenIsolierverglasung = new JComboBox(CONSTRUCTION_TYPES);
    private JCheckBox pSternAlsNullCheckbox = new JCheckBox("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>p* = 0 (Plattentheorie)</span></i></html>");

    private EMehrscheibenIsolierverglasungPanel mehrscheibenIsolierverglasungPanel = new FDreifachIsolierverglasungPanel();

    private JTextField E = new JTextField("70000000", 10);
    private JTextField fk = new JTextField("45.0", 10);

    private JTextField Lastfall2 = new JTextField("7,20");
    private JTextField Lastfall3 = new JTextField("8,80");
    private JTextField Lastfall4 = new JTextField("-3,60");
    private JTextField Lastfall5 = new JTextField("-12,50");
    private JTextField WinddruckWk = new JTextField("0,75");
    private JTextField WindsogWk = new JTextField("-1,05");

    private JButton applyB = new JButton("Apply");
    private JButton exitB = new JButton("Exit");

    private JPanel ausgabePanel2 = new JPanel();
    private JPanel ausgabePanel3 = new JPanel(new BorderLayout());
 
    private JPanel teil5Container;
    private JPanel teil3;
    private JPanel gztPanel3; 
    private JPanel gztPanel6; 
    private JPanel berechneteWertePanel1;
    private JPanel leeresPanel;  
    private JPanel panelInnen;
    private JPanel inneresPanel;
    private JPanel nachweisGztPanel;
    private JPanel panelInnenNachweis;
    private JPanel ausgabePanelGZG;
    private JPanel gzgPanel3;
    private JPanel gzgPanel6;
    private JPanel panelInnen1;
    private JPanel panelInnen2;
    private JPanel panelInnen3;
    private JPanel panelInnen4;
    private JPanel panelInnen5;
    private JPanel panelInnen6;
    private JPanel nachweisPanelInnenGZG1;
    private JPanel nachweisPanelInnenGZG2;
    private JPanel nachweisPanelInnenGZG3;
    private JPanel nachweisPanelInnenGZG4;
	private JPanel nachweisPanelInnenGZG5;
    private JPanel nachweisPanelInnenGZG6;

    private JPanel gzgNachweisPanel;
    private JPanel nachweisPanelInnenGZG;
    private JPanel nachweisGzgPanel;
    private JPanel panelInnenNachweisGzg;

    // Anfangswerte
    private JLabel Bv = new JLabel("Bv=");
    private JLabel A = new JLabel("A=");
    
    private JLabel berechneVolumenD1 = new JLabel("Vq,d1 =");
    private JLabel berechneVolumenD2 = new JLabel("Vq,d2 =");
    private JLabel berechneVolumenD3 = new JLabel("Vq,d3 =");

    private JLabel Scheibenzwischenraumvolumen1 = new JLabel("SZR 1 =");
    private JLabel Scheibenzwischenraumvolumen2 = new JLabel("SZR 2 =");

    private JLabel alpha1 = new JLabel("alpha1 =");
    private JLabel alpha1plus = new JLabel("alpha1plus =");
    
    private JLabel alpha2 = new JLabel("alpha2 =");
    private JLabel alpha2plus = new JLabel("alpha2plus =");
    
    private JLabel phi1 = new JLabel("phi1 =");
    private JLabel phi2 = new JLabel("phi2 =");

    private JLabel beta = new JLabel("beta =");
    
    // Druckdifferenzen
    private JLabel deltaP12 = new JLabel("deltaP12 =");
    private JLabel deltaP13 = new JLabel("deltaP13 =");
    private JLabel deltaP14 = new JLabel("deltaP14 =");
    private JLabel deltaP15 = new JLabel("deltaP15 =");
    private JLabel deltaP17 = new JLabel("deltaP17 =");
    private JLabel deltaP18 = new JLabel("deltaP18 =");
    
    private JLabel deltaP22 = new JLabel("deltaP22 =");
    private JLabel deltaP23 = new JLabel("deltaP23 =");
    private JLabel deltaP24 = new JLabel("deltaP24 =");
    private JLabel deltaP25 = new JLabel("deltaP25 =");
    private JLabel deltaP27 = new JLabel("deltaP27 ="); 
    private JLabel deltaP28 = new JLabel("deltaP28 =");
    
    // Lastanteile
    private JLabel pres12 = new JLabel("pres12 =");
    private JLabel pres13 = new JLabel("pres13 =");
    private JLabel pres14 = new JLabel("pres14 =");
    private JLabel pres15 = new JLabel("pres15 =");
    private JLabel pres17 = new JLabel("pres17 =");
    private JLabel pres18 = new JLabel("pres18 =");
    
    private JLabel pres22 = new JLabel("pres22 =");
    private JLabel pres23 = new JLabel("pres23 =");
    private JLabel pres24 = new JLabel("pres24 =");
    private JLabel pres25 = new JLabel("pres25 =");
    private JLabel pres27 = new JLabel("pres27 =");
    private JLabel pres28 = new JLabel("pres28 =");
    
    private JLabel pres32 = new JLabel("pres32 =");
    private JLabel pres33 = new JLabel("pres33 =");
    private JLabel pres34 = new JLabel("pres34 =");
    private JLabel pres35 = new JLabel("pres35 =");
    private JLabel pres37 = new JLabel("pres37 =");
    private JLabel pres38 = new JLabel("pres38 =");
    
    // Lastkombination GZT
    private JLabel qGzt1Labelaußen = new JLabel();
    private JLabel qGzt2Labelaußen = new JLabel();
    private JLabel qGzt3Labelaußen = new JLabel();
    private JLabel qGzt4Labelaußen = new JLabel();
    private JLabel qGzt5Labelaußen = new JLabel();
    private JLabel qGzt6Labelaußen = new JLabel();
    private JLabel qGzt7Labelaußen = new JLabel();
    private JLabel qGzt8Labelaußen = new JLabel();
    
    private JLabel qGzt1Labelmitte = new JLabel();
    private JLabel qGzt2Labelmitte = new JLabel();
    private JLabel qGzt3Labelmitte = new JLabel();
    private JLabel qGzt4Labelmitte = new JLabel();
    private JLabel qGzt5Labelmitte = new JLabel();
    private JLabel qGzt6Labelmitte = new JLabel();
    private JLabel qGzt7Labelmitte = new JLabel();
    private JLabel qGzt8Labelmitte = new JLabel();
    
    private JLabel qGzt1Labelinnen = new JLabel();
    private JLabel qGzt2Labelinnen = new JLabel();
    private JLabel qGzt3Labelinnen = new JLabel();
    private JLabel qGzt4Labelinnen = new JLabel();
    private JLabel qGzt5Labelinnen = new JLabel();
    private JLabel qGzt6Labelinnen = new JLabel();
    private JLabel qGzt7Labelinnen = new JLabel();
    private JLabel qGzt8Labelinnen = new JLabel();
    
    // Bemessungslast GZT
    private JLabel qGztLabelaußenMax = new JLabel();
    private JLabel qGztLabelmitteMax = new JLabel();
    private JLabel qGztLabelinnenMax = new JLabel();
    
    // Lamda GZT
    private JLabel lamda1 = new JLabel();
    private JLabel lamda2 = new JLabel();
    private JLabel lamda3 = new JLabel();
    
    // Normierte Flächenlast GZT
    private JLabel normierteFlächenlastAußenGzt = new JLabel();
    private JLabel normierteFlächenlastInnenGzt = new JLabel();
    private JLabel normierteFlächenlastMitteGzt = new JLabel();
    
    // Spannungsfaktor GZT
    private JLabel spannungsfaktorAußenGzt = new JLabel();
    private JLabel spannungsfaktorMitteGzt = new JLabel();
    private JLabel spannungsfaktorInnenGzt = new JLabel();
    
    // Bemessungsspannung GZT
    private JLabel bemessungsspannungAußenGzt = new JLabel();
    private JLabel bemessungsspannungMitteGzt = new JLabel();
    private JLabel bemessungsspannungInnenGzt = new JLabel();
    
    // Maximale Beanspruchung GZT
    private JLabel maximaleBeanspruchungAußenGzt = new JLabel();
    private JLabel maximaleBeanspruchungMitteGzt = new JLabel();
    private JLabel maximaleBeanspruchungInnenGzt = new JLabel();
    
    // Nachweiswert GZT
    private JLabel NachweisGZTaußen = new JLabel();
    private JLabel NachweisGZTmitte = new JLabel();
    private JLabel NachweisGZTinnen = new JLabel();

    // Lastkombination GZG
    private JLabel qGzg1Labelaußen = new JLabel();
    private JLabel qGzg2Labelaußen = new JLabel();
    private JLabel qGzg3Labelaußen = new JLabel();
    private JLabel qGzg4Labelaußen = new JLabel();

    private JLabel qGzg1Labelmitte = new JLabel();
    private JLabel qGzg2Labelmitte = new JLabel();
    private JLabel qGzg3Labelmitte = new JLabel();
    private JLabel qGzg4Labelmitte = new JLabel();

    private JLabel qGzg1Labelinnen = new JLabel();
    private JLabel qGzg2Labelinnen = new JLabel();
    private JLabel qGzg3Labelinnen = new JLabel();
    private JLabel qGzg4Labelinnen = new JLabel();

    // Bemessungslast GZG
    private JLabel qGzgLabelaußenMax = new JLabel();
    private JLabel qGzgLabelmitteMax = new JLabel();
    private JLabel qGzgLabelinnenMax = new JLabel();
    
    // Lamda GZG
    private JLabel lamda4 = new JLabel();
    private JLabel lamda5 = new JLabel();
    private JLabel lamda6 = new JLabel();
    
    // Normierte Flächenlast GZG
    private JLabel normierteFlächenlastAußenGzg = new JLabel();
    private JLabel normierteFlächenlastInnenGzg = new JLabel();
    private JLabel normierteFlächenlastMitteGzg = new JLabel();
    
    // Spannungsfaktor GZG
    private JLabel verformungsfaktorAußenGzg = new JLabel();
    private JLabel verformungsfaktorMitteGzg = new JLabel();
    private JLabel verformungsfaktorInnenGzg = new JLabel();
    
    // Bemessungsspannung GZG
    private JLabel bemessungsverformungAußenGzg = new JLabel();
    private JLabel bemessungsverformungMitteGzg = new JLabel();
    private JLabel bemessungsverformungInnenGzg = new JLabel();
   
    // Grenzwert der Verformung GZG
    private JLabel grenzwertDerVerformungAußenGzg = new JLabel();
    private JLabel grenzwertDerVerformungMitteGzg = new JLabel();
    private JLabel grenzwertDerVerformungInnenGzg = new JLabel();
    
    // Nachweiswert GZG
    private JLabel NachweisGZGaußen = new JLabel();
    private JLabel NachweisGZGmitte = new JLabel();
    private JLabel NachweisGZGinnen = new JLabel();
    
    private void setupUntereTabelle(JPanel zielPanel) {
        String[] spalten = {"", "","",""};
        String[][] daten = {
            {"", "<html><b><span style='font-family:Lucida Console; font-size:12pt'>(ständig)</span></b></html>","<html><b><span style='font-family:Lucida Console; font-size:12pt'>(mittel)</span></b></html>","<html><b><span style='font-family:Lucida Console; font-size:12pt'>(kurz)</span></b></html>"},
            {"kmod", "0,25","0,40","0,70"},
        };
        JTable t = new JTable(daten, spalten) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(SwingConstants.LEFT);
        t.getColumnModel().getColumn(0).setCellRenderer(left);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < t.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        t.setRowHeight(22);
        t.setShowGrid(true);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(true);
        t.setGridColor(Color.GRAY);
        t.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

        zielPanel.setLayout(new BorderLayout());
        zielPanel.add(t, BorderLayout.CENTER);
    }
    
    private JTable werteTabelle;
    private void setupObereTabelle(JPanel zielPanel) {
        String[] spalten = {"","",""};
        String[][] daten = {
            {"", "<html><b><span style='font-family:Lucida Console; font-size:12pt'>f<sub>k</sub></span></b></html>", "<html><b><span style='font-family:Lucida Console; font-size:12pt'>E-Modul</span></b></html>"},  
            {"", "<html>kN/mm<sup>2</sup></html>", "<html>kN/mm<sup>2</sup></html>"},      
            {"Floatglas", "45.0","700000"},
            {"TVG", "70.0","700000"},
            {"ESG", "120.0","700000"},
        };
        JTable t = new JTable(daten, spalten) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(SwingConstants.LEFT);
        t.getColumnModel().getColumn(0).setCellRenderer(left);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        t.getColumnModel().getColumn(1).setCellRenderer(center);
        t.getColumnModel().getColumn(2).setCellRenderer(center);
        t.setRowHeight(22);
        t.setShowGrid(true);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(true);
        t.setGridColor(Color.BLACK);
        t.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        DefaultTableCellRenderer headerRenderer =
        	    (DefaultTableCellRenderer) t.getTableHeader().getDefaultRenderer();
        	headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        zielPanel.setLayout(new BorderLayout());
        zielPanel.add(t, BorderLayout.CENTER); 
    }

    private void setupTabelleVertikal(JPanel zielPanel) {
        String[][] daten = {
            {"<html><b><span style='font-family:Lucida Console; font-size:11pt'>B<sub>v</sub></span></b></html>",
             "<html><b><span style='font-family:Lucida Console; font-size:12pt'>a/b</span></b></html>"},
            {"1.0", "0.0194"},
            {"0.9", "0.0237"},
            {"0.8", "0.0288"},
            {"0.7", "0.0350"},
            {"0.6", "0.0421"},
            {"0.5", "0.0501"},
            {"0.4", "0.0587"},
            {"0.3", "0.0676"},
            {"0.2", "0.0767"},
            {"0.1", "0.0857"}
        };
        String[] spalten = {"", ""};

        werteTabelle = new JTable(daten, spalten) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        // === NEU: Zellen zentrieren wie bei der anderen Tabelle ===
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        werteTabelle.getColumnModel().getColumn(0).setCellRenderer(center);
        werteTabelle.getColumnModel().getColumn(1).setCellRenderer(center);

        // === NEU: Kopfzeile zentrieren ===
        DefaultTableCellRenderer headerCenter =
            (DefaultTableCellRenderer) werteTabelle.getTableHeader().getDefaultRenderer();
        headerCenter.setHorizontalAlignment(SwingConstants.CENTER);

        werteTabelle.setRowHeight(16);
        werteTabelle.setPreferredSize(new Dimension(200, 160));
        werteTabelle.setShowGrid(true);
        werteTabelle.setShowHorizontalLines(true);
        werteTabelle.setShowVerticalLines(true);
        werteTabelle.setGridColor(Color.BLACK);
        werteTabelle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        zielPanel.setLayout(new BorderLayout());
        zielPanel.add(werteTabelle, BorderLayout.CENTER);
    }
    
   
    private void setupLastkombinationenTabelle(JPanel zielPanel) {
        String[] spaltenNamen = {"p*", "λ = 1.00", "λ = 1.25", "λ = 1.50", "λ = 1.75", "λ = 2.00", "λ = 3.00"};
        
        int[] pSternWerte = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100,120, 140, 160, 180, 200, 250, 300, 350, 400, 450, 500};

        double[][] tabellenWerte = {
            {0.272, 0.383, 0.475, 0.548, 0.603, 0.711},
            {0.271, 0.382, 0.470, 0.538, 0.586, 0.706},
            {0.270, 0.378, 0.450, 0.512, 0.557, 0.694},
            {0.267, 0.355, 0.414, 0.470, 0.532, 0.676},
            {0.261, 0.331, 0.384, 0.437, 0.486, 0.654},
            {0.251, 0.310, 0.356, 0.405, 0.453, 0.628},
            {0.244, 0.291, 0.336, 0.379, 0.426, 0.603},
            {0.239, 0.278, 0.320, 0.354, 0.401, 0.580},
            {0.234, 0.271, 0.306, 0.334, 0.380, 0.559},
            {0.231, 0.267, 0.296, 0.323, 0.360, 0.538},
            {0.227, 0.263, 0.290, 0.318, 0.346, 0.519},
            {0.222, 0.256, 0.283, 0.312, 0.326, 0.482},
            {0.218, 0.252, 0.277, 0.306, 0.319, 0.452},
            {0.213, 0.247, 0.272, 0.302, 0.315, 0.426},
            {0.209, 0.243, 0.268, 0.298, 0.312, 0.403},
            {0.206, 0.239, 0.264, 0.294, 0.308, 0.382},
            {0.201, 0.234, 0.258, 0.289, 0.302, 0.353},
            {0.197, 0.230, 0.254, 0.285, 0.298, 0.346},
            {0.193, 0.226, 0.250, 0.282, 0.294, 0.343},
            {0.190, 0.223, 0.247, 0.279, 0.289, 0.341},
            {0.188, 0.220, 0.244, 0.276, 0.287, 0.338},
            {0.182, 0.215, 0.237, 0.271, 0.281, 0.332},
            {0.178, 0.211, 0.232, 0.266, 0.275, 0.326},
            {0.175, 0.208, 0.228, 0.261, 0.270, 0.322},
            {0.172, 0.206, 0.225, 0.257, 0.266, 0.318},
            {0.170, 0.204, 0.222, 0.253, 0.263, 0.315},
            {0.167, 0.202, 0.219, 0.250, 0.259, 0.312}
        };

        String[][] daten = new String[pSternWerte.length][7];
        for (int i = 0; i < pSternWerte.length; i++) {
            daten[i][0] = String.valueOf(pSternWerte[i]);
            for (int j = 0; j < 6; j++) {
                daten[i][j + 1] = String.format("%.3f", tabellenWerte[i][j]);
            }
        }

        JTable tabelle = new JTable(daten, spaltenNamen);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tabelle.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabelle.getTableHeader().setDefaultRenderer(headerRenderer);
        DefaultTableCellRenderer zentriert = new DefaultTableCellRenderer();
        zentriert.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabelle.getColumnCount(); i++) {
            tabelle.getColumnModel().getColumn(i).setCellRenderer(zentriert);
        }
        
        tabelle.setShowGrid(true);
        tabelle.setGridColor(Color.BLACK);
        
        zielPanel.setLayout(new BorderLayout());
        zielPanel.add(tabelle.getTableHeader(), BorderLayout.NORTH);
        zielPanel.add(tabelle, BorderLayout.CENTER);
    }
    
    private void setupLastkombinationenTabelle2(JPanel zielPanel) {
        String[] spaltenNamen = {
            "p*", "λ = 1.00", "λ = 1.25", "λ = 1.50", "λ = 1.75", "λ = 2.00", "λ = 3.00"
        };

        int[] pSternWerte = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100,120, 140, 160, 180, 200, 250, 300, 350, 400, 450, 500};

        double[][] tabellenWerte = {
        		 {0.0464, 0.0685, 0.0878, 0.1030, 0.1150, 0.1390},
        		 {0.0457, 0.0672, 0.0861, 0.0993, 0.1140, 0.1380},
        		 {0.0445, 0.0643, 0.0820, 0.0957, 0.1100, 0.1360},
        		 {0.0430, 0.0607, 0.0763, 0.0893, 0.1010, 0.1330},
        		 {0.0413, 0.0568, 0.0708, 0.0832, 0.0953, 0.1290},
        		 {0.0397, 0.0533, 0.0662, 0.0784, 0.0898, 0.1250},
        		 {0.0376, 0.0503, 0.0623, 0.0738, 0.0848, 0.1200},
        		 {0.0360, 0.0477, 0.0588, 0.0698, 0.0805, 0.1150},
        		 {0.0347, 0.0455, 0.0559, 0.0663, 0.0767, 0.1110},
        		 {0.0332, 0.0434, 0.0533, 0.0632, 0.0733, 0.1070},
        		 {0.0320, 0.0417, 0.0512, 0.0608, 0.0702, 0.1040},
        		 {0.0298, 0.0385, 0.0472, 0.0563, 0.0651, 0.0975},
        		 {0.0280, 0.0360, 0.0441, 0.0524, 0.0608, 0.0923},
        		 {0.0264, 0.0338, 0.0414, 0.0493, 0.0573, 0.0874},
        		 {0.0251, 0.0320, 0.0392, 0.0467, 0.0543, 0.0833},
        		 {0.0239, 0.0304, 0.0373, 0.0443, 0.0516, 0.0795},
        		 {0.0221, 0.0278, 0.0342, 0.0406, 0.0474, 0.0733},
        		 {0.0204, 0.0258, 0.0316, 0.0376, 0.0438, 0.0682},
        		 {0.0191, 0.0241, 0.0295, 0.0351, 0.0409, 0.0639},
        		 {0.0179, 0.0227, 0.0278, 0.0330, 0.0386, 0.0605},
        		 {0.0171, 0.0215, 0.0263, 0.0313, 0.0365, 0.0573},
        		 {0.0153, 0.0191, 0.0234, 0.0278, 0.0324, 0.0510},
        		 {0.0139, 0.0173, 0.0211, 0.0252, 0.0294, 0.0463},
        		 {0.0128, 0.0161, 0.0195, 0.0233, 0.0270, 0.0426},
        		 {0.0119, 0.0155, 0.0181, 0.0228, 0.0251, 0.0396},
        		 {0.0112, 0.0151, 0.0170, 0.0224, 0.0235, 0.0371},
        		 {0.0106, 0.0147, 0.0160, 0.0200, 0.0222, 0.0349}
        };

        String[][] daten = new String[pSternWerte.length][7];
        for (int i = 0; i < pSternWerte.length; i++) {
            daten[i][0] = String.valueOf(pSternWerte[i]);
            for (int j = 0; j < 6; j++) {
                daten[i][j + 1] = String.format("%.3f", tabellenWerte[i][j]);
            }
        }

        JTable tabelle = new JTable(daten, spaltenNamen);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tabelle.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabelle.getTableHeader().setDefaultRenderer(headerRenderer);
        DefaultTableCellRenderer zentriert = new DefaultTableCellRenderer();
        zentriert.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabelle.getColumnCount(); i++) {
            tabelle.getColumnModel().getColumn(i).setCellRenderer(zentriert);
        }
        tabelle.setShowGrid(true);
        tabelle.setGridColor(Color.BLACK);

        zielPanel.setLayout(new BorderLayout());
        zielPanel.add(tabelle.getTableHeader(), BorderLayout.NORTH);
        zielPanel.add(tabelle, BorderLayout.CENTER);
    }
    
    private void setupBV2KlimaTabelle(JPanel zielPanel) {
    	String[] spalten = { "", "", "", "" };
    	String[][] daten = {{ "", "<html><b>&Delta;T</b></html>",
    						"<html><b>&Delta;p<sub>met</sub></b></html>",
    						"<html><b>&Delta;H</b></html>" },
    						{ "", "K", "<html>kN/m<sup>2</sup></html>", "m" },		
    						{ "„Sommer“", "+20",  "-2,0", "+600" },
    						{ "„Winter“", "−25",  "+4,0", "−300" }
    	};
    	JTable tabelle = new JTable(daten, spalten) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(SwingConstants.LEFT);
        tabelle.getColumnModel().getColumn(0).setCellRenderer(left);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < tabelle.getColumnCount(); i++) {
            tabelle.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        tabelle.setRowHeight(22);
        tabelle.setShowGrid(true);
        tabelle.setGridColor(Color.BLACK);
        tabelle.getColumnModel().getColumn(0).setPreferredWidth(170);
        tabelle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        
        zielPanel.setLayout(new BorderLayout());
        zielPanel.add(tabelle, BorderLayout.CENTER);
    }
    
    public ABemessungApplication() {
        this.setTitle("Mehrscheiben-Isolierverglasung Bemessung");
        
        
        //Überschrift 1
        JPanel abstandPanelAusgangswerte = new JPanel(new GridLayout(4, 1));
        abstandPanelAusgangswerte.add(new JLabel(""));
        abstandPanelAusgangswerte.add(new JLabel(""));
        abstandPanelAusgangswerte.add(new JLabel("<html><b><span style='font-family:Lucida Console; font-size:18pt'>Ausgangswerte</span></b></html>", SwingConstants.CENTER));
        abstandPanelAusgangswerte.add(new JLabel(""));
       
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
       
        JPanel eingabeContainer = new JPanel(new BorderLayout());
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        contentPanel.add(abstandPanelAusgangswerte, BorderLayout.NORTH);
        abstandPanelAusgangswerte.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
       
        //-------------------Linksspalte: Materialkennwerte-------------------
        JPanel propertiesPanel = new JPanel(new BorderLayout());
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:15pt'>Materialkennwerte</span></html>"));
        pad(propertiesPanel, 0, 0, GAP, 0); // unten Abstand zum nächsten Block
        eingabeContainer.add(propertiesPanel, BorderLayout.NORTH);

        JPanel systemPanel = new JPanel(new BorderLayout());
        JPanel p1 = new JPanel(new GridLayout(2, 1));
        p1.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>E-Modul =</span></i></html>"));
        p1.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>f<sub>k</sub> =</span></i></html>"));

        JPanel p2 = new JPanel(new GridLayout(2, 1));
        p2.add(this.E);
        p2.add(this.fk);

        JPanel p3 = new JPanel(new GridLayout(2, 1));
        p3.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/mm<sup>2</sup></html></span></i></html>"));
        p3.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/mm<sup>2</sup></html></span></i></html>"));
        
        systemPanel.add(p1, BorderLayout.WEST);
        systemPanel.add(p2, BorderLayout.CENTER);
        systemPanel.add(p3, BorderLayout.EAST);
        propertiesPanel.add(systemPanel, BorderLayout.NORTH);

        //-------------------Linksspalte: Aufbau-------------------
        sectionPanel.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:15pt'>Aufabu</span></html>"));
        pad(sectionPanel, 0, 0, GAP, 0);

        this.sectionPanel.add(this.MehrscheibenIsolierverglasung, BorderLayout.NORTH);
        this.sectionPanel.add(this.mehrscheibenIsolierverglasungPanel, BorderLayout.CENTER);
        eingabeContainer.add(this.sectionPanel, BorderLayout.CENTER);

        //-------------------Linksspalte: klimatische Einwirkungen-------------------
        JPanel klimaPanel = new JPanel(new BorderLayout());
        klimaPanel.setBorder(BorderFactory.createTitledBorder(
        		"<html><span style='font-family:Lucida Console; font-size:15pt'>klimatische Einwirkungen</span></html>"
        ));
        pad(klimaPanel, 0, 0, GAP, 0);

        JPanel kLeft = new JPanel(new GridLayout(4, 1));
        kLeft.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 2:</span></i></html>"));
        kLeft.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 3:</span></i></html>"));
        kLeft.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 4:</span></i></html>"));
        kLeft.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 5:</span></i></html>"));

        JPanel kCenter = new JPanel(new GridLayout(4, 1));
        kCenter.add(this.Lastfall2);
        kCenter.add(this.Lastfall3);
        kCenter.add(this.Lastfall4);
        kCenter.add(this.Lastfall5);

        JPanel kRight = new JPanel(new GridLayout(4, 1));
        kRight.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>"));
        kRight.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>"));
        kRight.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>"));
        kRight.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>"));

        klimaPanel.add(kLeft, BorderLayout.WEST);
        klimaPanel.add(kCenter, BorderLayout.CENTER);
        klimaPanel.add(kRight, BorderLayout.EAST);

        //-------------------Linksspalte: Windeinwirkungen-------------------
        JPanel windPanel = new JPanel(new BorderLayout());
        windPanel.setBorder(BorderFactory.createTitledBorder(
        		"<html><span style='font-family:Lucida Console; font-size:15pt'>Windeinwirkungen</span></html>"
        ));
        pad(windPanel, 0, 0, GAP, 0);

        JPanel wLeft = new JPanel(new GridLayout(2, 1));
        wLeft.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 7:</span></i></html>"));
        wLeft.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 8:</span></i></html>"));

        JPanel wCenter = new JPanel(new GridLayout(2, 1));
        wCenter.add(this.WinddruckWk);
        wCenter.add(this.WindsogWk);

        JPanel wRight = new JPanel(new GridLayout(2, 1));
        wRight.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>"));
        wRight.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>"));

        windPanel.add(wLeft, BorderLayout.WEST);
        windPanel.add(wCenter, BorderLayout.CENTER);
        windPanel.add(wRight, BorderLayout.EAST);
        
        //-------------------Abstand Wind Klima-------------------
        JPanel valuesPanel = new JPanel();
        valuesPanel.setLayout(new BoxLayout(valuesPanel, BoxLayout.Y_AXIS));
        valuesPanel.add(klimaPanel);
        valuesPanel.add(Box.createVerticalStrut(GAP));
        valuesPanel.add(windPanel);
        eingabeContainer.add(valuesPanel, BorderLayout.SOUTH);
        
        //-------------------Linksspalte: Normierte Flächenlast-------------------
        JPanel pSternOptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pSternOptionPanel.setBorder(BorderFactory.createTitledBorder(
        		"<html><span style='font-family:Lucida Console; font-size:15pt'>normierte Flächenlast</span></html>"
        ));
        pad(pSternOptionPanel, GAP, 0, 0, 0); // einheitlicher Abstand NACH OBEN zur Windeinwirkung
        pSternOptionPanel.add(pSternAlsNullCheckbox);
        //-------------------------------------------------------------------------
        JPanel westStack = new JPanel(new BorderLayout());
        westStack.add(eingabeContainer, BorderLayout.CENTER);
        westStack.add(pSternOptionPanel, BorderLayout.SOUTH);
        
        //Linke spalte in hauptlayout
        JPanel leftWrap = new JPanel(new BorderLayout());
        leftWrap.add(westStack, BorderLayout.CENTER);
        leftWrap.setPreferredSize(new Dimension(220, 10));
        contentPanel.add(leftWrap, BorderLayout.WEST);


        // Beiwert Bv
        ausgabePanel3.setLayout(new BorderLayout());
        ausgabePanel3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:15pt'>Beiwert Bv</span></html>"));
        setupTabelleVertikal(ausgabePanel3);
        growWide(ausgabePanel3);
    
        // Tabelle Anfang
        JPanel centerColumn = new JPanel();
        centerColumn.setLayout(new BoxLayout(centerColumn, BoxLayout.Y_AXIS));

        // obere tabelle
        JPanel ausgabePanelOben = new JPanel(new BorderLayout());
        ausgabePanelOben.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:15pt'>Materialkennwerte</span></html>"));
        setupObereTabelle(ausgabePanelOben);
        growWide(ausgabePanelOben);
        centerColumn.add(ausgabePanelOben);
        centerColumn.add(Box.createVerticalStrut(9));
        
        // Bv tabelle
        growWide(ausgabePanel3);
        centerColumn.add(ausgabePanel3);
        centerColumn.add(Box.createVerticalStrut(9));

        // Klima Tabelle
        JPanel ausgabePanelBV2 = new JPanel(new BorderLayout());
        ausgabePanelBV2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:15pt'>klimatische Einwirkungen</span></html>"));
        setupBV2KlimaTabelle(ausgabePanelBV2);
        growWide(ausgabePanelBV2);
        centerColumn.add(ausgabePanelBV2);
        centerColumn.add(Box.createVerticalStrut(9));
        centerColumn.add(Box.createVerticalGlue());
       
        // untere Tabelle
        JPanel ausgabePanelUnten = new JPanel(new BorderLayout());
        ausgabePanelUnten.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:15pt'>Modifikationsbeiwert</span></html>"));
        setupUntereTabelle(ausgabePanelUnten);
        growWide(ausgabePanelUnten);
        centerColumn.add(ausgabePanelUnten);
        JPanel eastPanel2 = new JPanel(new BorderLayout());

        // Eine Spalte, Höhe nach Inhalt (nicht alle gleich hoch)
        JPanel grid = new JPanel();
        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
        eastPanel2.add(grid, BorderLayout.CENTER);
       
       
        
        // Rechte Spalte einpacken und Breite setzen
        JPanel rightWrap = new JPanel(new BorderLayout());
        rightWrap.add(eastPanel2, BorderLayout.CENTER);
        rightWrap.setPreferredSize(new Dimension(260, 10));

        // In das BorderLayout des contentPanel einfügen
        contentPanel.add(centerColumn, BorderLayout.CENTER);
        contentPanel.add(rightWrap, BorderLayout.EAST);

        // 1) Bv (allein)
        JPanel boxBv = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>Beiwert Bv</span></html>");
        boxBv.add(this.Bv);
        growWide(boxBv);                  
        grid.add(boxBv);
        grid.add(Box.createVerticalStrut(2));

        // 2) A (allein)
        JPanel boxA = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>Scheibenflächeninhalt</span></html>");
        boxA.add(this.A);
        growWide(boxA);                   
        grid.add(boxA);
        grid.add(Box.createVerticalStrut(2));

        // 3) Vq,d (vq,d1/2/3 zusammen)
        JPanel boxVqd = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>aufgespanntes Volumen</span></html>");
        boxVqd.add(this.berechneVolumenD1);
        boxVqd.add(this.berechneVolumenD2);
        boxVqd.add(this.berechneVolumenD3);
        growWide(boxVqd);                 
        grid.add(boxVqd);
        grid.add(Box.createVerticalStrut(2));

        // 4) V_SZ R (1 und 2 zusammen)
        JPanel boxSZR = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>Scheibenzwischenraumvolumen</span></html>");
        boxSZR.add(this.Scheibenzwischenraumvolumen1);
        boxSZR.add(this.Scheibenzwischenraumvolumen2);
        growWide(boxSZR);                 
        grid.add(boxSZR);
        grid.add(Box.createVerticalStrut(2));

        // 5) alpha (alle α-Werte)
        JPanel boxAlpha = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>relative Volumenänderung</span></html>");
        boxAlpha.add(this.alpha1);
        boxAlpha.add(this.alpha2);
        growWide(boxAlpha);          
        grid.add(boxAlpha);
        grid.add(Box.createVerticalStrut(2));

        // 5) alpha (alle α-Werte)
        JPanel boxAlphaplus = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>relative Volumenänderung</span></html>");
        boxAlphaplus.add(this.alpha1plus);
        boxAlphaplus.add(this.alpha2plus);
        growWide(boxAlphaplus);             
        grid.add(boxAlphaplus);
        grid.add(Box.createVerticalStrut(2));	
        
        // 6) phi (alle φ-Werte)
        JPanel boxPhi = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>Isolierglasfaktor</span></html>");
        boxPhi.add(this.phi1);
        boxPhi.add(this.phi2);
        growWide(boxPhi);              
        grid.add(boxPhi);
        grid.add(Box.createVerticalStrut(2));
        grid.add(Box.createVerticalGlue());
        
        // 7) beta (allein)
        JPanel boxBeta = titledBox("<html><span style='font-family:Lucida Console; font-size:15pt'>Hilfswert</span></html>");
        boxBeta.add(this.beta);
        growWide(boxBeta);
        grid.add(boxBeta);
 
        JPanel viewerWrapper = new JPanel(new BorderLayout());
        viewerWrapper.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Schnittdarstellung</span></b></html>"));
        viewerWrapper.add(this.viewerPanel, BorderLayout.CENTER);
        this.viewerPanel.setPreferredSize(new Dimension(500, 400));
     
        JPanel abstandPanel11 = new JPanel(new GridLayout(5, 1));
        abstandPanel11.add(new JLabel(""));
        abstandPanel11.add(new JLabel(""));

        abstandPanel11.add(new JLabel("<html><b><span style='font-family:Lucida Console; font-size:18pt'>Druckdifferenzen</span></b></html>", SwingConstants.CENTER));
        abstandPanel11.add(new JLabel(""));
        abstandPanel11.add(new JLabel(""));

       
        JPanel bottomContent = new JPanel(new BorderLayout());
        JPanel berechneteWertePanel1 = new JPanel(new GridLayout(1, 2, 15, 15)); 
       
        String einheit = "<html><i><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></i></html>";

        this.berechneteWertePanel1 = berechneteWertePanel1;
        JPanel teil4Container = new JPanel(new BorderLayout());
        teil4Container.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Druckdifferenzen - Scheibenzischenraum 1</span></b></html>"));
        JPanel teil4 = new JPanel(new GridLayout(6, 1, 5, 5));
        
        teil4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 2: <html>&Delta;p<sub>1,2</sub></html></span></html>", deltaP12, einheit));
        teil4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 3: <html>&Delta;p<sub>1,3</sub></html></span></html>", deltaP13, einheit));
        teil4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 4: <html>&Delta;p<sub>1,4</sub></html></span></html>", deltaP14, einheit));
        teil4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 5: <html>&Delta;p<sub>1,5</sub></html></span></html>", deltaP15, einheit));
        teil4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 7: <html>&Delta;p<sub>1,7</sub></html></span></html>", deltaP17, einheit));
        teil4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 8: <html>&Delta;p<sub>1,8</sub></html></span></html>", deltaP18, einheit));
     
        teil4Container.add(teil4, BorderLayout.CENTER);
        berechneteWertePanel1.add(teil4Container);

        JPanel teil5Container = new JPanel(new BorderLayout());
        teil5Container.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Druckdifferenzen - Scheibenzischenraum 2</span></b></html>"));
        JPanel teil5 = new JPanel(new GridLayout(6, 1, 5, 5));
        teil5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 2: <html>&Delta;p<sub>2,2</sub></html></span></html>", deltaP22, einheit));
        teil5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 3: <html>&Delta;p<sub>2,3</sub></html></span></html>", deltaP23, einheit));
        teil5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 4: <html>&Delta;p<sub>2,4</sub></html></span></html>", deltaP24, einheit));
        teil5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 5: <html>&Delta;p<sub>2,5</sub></html></span></html>", deltaP25, einheit));
        teil5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 7: <html>&Delta;p<sub>2,7</sub></html></span></html>", deltaP27, einheit));
        teil5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 8: <html>&Delta;p<sub>2,8</sub></html></span></html>", deltaP28, einheit));
        teil5Container.add(teil5, BorderLayout.CENTER);
        berechneteWertePanel1.add(teil5Container);
        this.teil5Container = teil5Container;
       
        this.leeresPanel = new JPanel(new GridLayout(1, 3, 15, 15));

       // String einheit = "<html><span style='font-family:Lucida Console; font-size:12pt'><html><i>m</i></html></span></html>";

        JPanel teil1 = new JPanel(new GridLayout(6, 1, 5, 5));
        teil1.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Lastanteile - Scheibe 1</span></b></html>"));
        //String einheit = "<html>kN/m<sup>2</sup></html>";
        
        teil1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 2: <html>p<sub>res&nbsp;1,2</sub></html></span></html>", pres12, einheit));
        teil1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 3: <html>p<sub>res&nbsp;1,3</sub></html></span></html>", pres13, einheit));
        teil1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 4: <html>p<sub>res&nbsp;1,4</sub></html></span></html>", pres14, einheit));
        teil1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 5: <html>p<sub>res&nbsp;1,5</sub></html></span></html>", pres15, einheit));
        teil1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 7: <html>p<sub>res&nbsp;1,7</sub></html></span></html>", pres17, einheit));
        teil1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 8: <html>p<sub>res&nbsp;1,8</sub></html></span></html>", pres18, einheit));
        
        JPanel teil2 = new JPanel(new GridLayout(6, 1, 5, 5));
        teil2.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Lastanteile - Scheibe 2</span></b></html>"));
        //String einheit = "<html>kN/m<sup>2</sup></html>";
        teil2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 2: <html>p<sub>res&nbsp;2,2</sub></html></span></html>", pres22, einheit));
        teil2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 3: <html>p<sub>res&nbsp;2,3</sub></html></span></html>", pres23, einheit));
        teil2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 4: <html>p<sub>res&nbsp;2,4</sub></html></span></html>", pres24, einheit));
        teil2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 5: <html>p<sub>res&nbsp;2,5</sub></html></span></html>", pres25, einheit));
        teil2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 7: <html>p<sub>res&nbsp;2,7</sub></html></span></html>", pres27, einheit));
        teil2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 8: <html>p<sub>res&nbsp;2,8</sub></html></span></html>", pres28, einheit));

        JPanel teil3 = new JPanel(new GridLayout(6, 1, 5, 5));
        teil3.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Lastanteile - Scheibe 3</span></b></html>"));
        //String einheit = "<html>kN/m<sup>2</sup></html>";
        teil3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 2: <html>p<sub>res&nbsp;3,2</sub></html></span></html>", pres32, einheit));
        teil3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 3: <html>p<sub>res&nbsp;3,3</sub></html></span></html>", pres33, einheit));
        teil3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 4: <html>p<sub>res&nbsp;3,4</sub></html></span></html>", pres34, einheit));
        teil3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 5: <html>p<sub>res&nbsp;3,5</sub></html></span></html>", pres35, einheit));
        teil3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 7: <html>p<sub>res&nbsp;3,7</sub></html></span></html>", pres37, einheit));
        teil3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Lastfall 8: <html>p<sub>res&nbsp;3,8</sub></html></span></html>", pres38, einheit));
        this.teil3 = teil3;
        this.leeresPanel.add(teil1);
        this.leeresPanel.add(teil2);
        this.leeresPanel.add(teil3);

        JPanel abstandPanelLastanteile = new JPanel(new GridLayout(5, 1));
        abstandPanelLastanteile.add(new JLabel(""));
        abstandPanelLastanteile.add(new JLabel(""));
        abstandPanelLastanteile.add(new JLabel("<html><b><span style='font-family:Lucida Console; font-size:18pt'>Lastanteile</span></b></html>", SwingConstants.CENTER));
        abstandPanelLastanteile.add(new JLabel(""));
        abstandPanelLastanteile.add(new JLabel(""));
        abstandPanelLastanteile.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel ausgabeRechtsContainer = new JPanel();
        ausgabeRechtsContainer.setLayout(new BoxLayout(ausgabeRechtsContainer, BoxLayout.Y_AXIS));
        ausgabeRechtsContainer.add(abstandPanel11);         
        ausgabeRechtsContainer.add(berechneteWertePanel1);  
        ausgabeRechtsContainer.add(abstandPanelLastanteile); 
        ausgabeRechtsContainer.add(leeresPanel); 										
        bottomContent.add(ausgabeRechtsContainer, BorderLayout.CENTER);

        JPanel abstandPanelGZT = new JPanel(new GridLayout(5, 1));
        abstandPanelGZT.add(new JLabel(""));
        abstandPanelGZT.add(new JLabel(""));
        abstandPanelGZT.add(new JLabel("<html><b><span style='font-family:Lucida Console; font-size:18pt'>Nachweis im Grenzzustand der Tragfähigkeit</span></b></html>", SwingConstants.CENTER));
        abstandPanelGZT.add(new JLabel(""));
        abstandPanelGZT.add(new JLabel(""));

        
        JPanel southPanelContainer = new JPanel(new BorderLayout());
        ausgabePanel2.setLayout(new GridLayout(2, 3, 15, 15));
        ausgabePanel2.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Lastkombinationen im GZT</span></b></html>"));

        JPanel gztPanel1 = new JPanel(new GridLayout(7, 1, 5, 5));
        gztPanel1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 1: (Sommer+Windsog)</span></html>"));
        gztPanel1.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(ständig)</span></i></html>"));
        
        gztPanel1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;1: <html>q<sub>GZT,1</sub> = </span></html>", qGzt1Labelaußen, einheit));
        
        gztPanel1.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(mittig)</span></i></html>"));
        
        gztPanel1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;2: <html>q<sub>GZT,2</sub> = </span></html>", qGzt2Labelaußen, einheit));
       
        gztPanel1.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(kurz)</span></i><html>"));
       
        gztPanel1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;3: <html>q<sub>GZT,3</sub> = </span></html>", qGzt3Labelaußen, einheit));
        gztPanel1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;4: <html>q<sub>GZT,4</sub> = </span></html>", qGzt4Labelaußen, einheit));
        ausgabePanel2.add(gztPanel1);
        
        JPanel gztPanel2 = new JPanel(new GridLayout(7, 1, 5, 5));
        gztPanel2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 2: (Sommer+Windsog)</span></html>"));
        gztPanel2.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(ständig)</span></i></html>"));
        
        gztPanel2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;1: <html>q<sub>GZT,1</sub> = </span></html>", qGzt1Labelmitte, einheit));
        
        gztPanel2.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(mittig)</span></i></html>"));
        
        gztPanel2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;2: <html>q<sub>GZT,2</sub> = </span></html>", qGzt2Labelmitte, einheit));
        
        gztPanel2.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(kurz)</span></i><html>"));
        
        gztPanel2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;3: <html>q<sub>GZT,3</sub> = </span></html>", qGzt3Labelmitte, einheit));
        gztPanel2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;4: <html>q<sub>GZT,4</sub> = </span></html>", qGzt4Labelmitte, einheit));
        ausgabePanel2.add(gztPanel2);

        JPanel gztPanel3 = new JPanel(new GridLayout(7, 1, 5, 5));
        gztPanel3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 3: (Sommer+Windsog)</span></html>"));

        gztPanel3.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(ständig)</span></i></html>"));
        
        gztPanel3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;1: <html>q<sub>GZT,1</sub> = </span></html>", qGzt1Labelinnen, einheit));
        
        gztPanel3.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(mittig)</span></i></html>"));
        
        gztPanel3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;2: <html>q<sub>GZT,2</sub> = </span></html>", qGzt2Labelinnen, einheit));
        
        gztPanel3.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(kurz)</span></i><html>"));
        
        gztPanel3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;3: <html>q<sub>GZT,3</sub> = </span></html>", qGzt3Labelinnen, einheit));
        gztPanel3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;4: <html>q<sub>GZT,4</sub> = </span></html>", qGzt4Labelinnen, einheit));
        ausgabePanel2.add(gztPanel3);
        this.gztPanel3 = gztPanel3;

        
        JPanel gztPanel4 = new JPanel(new GridLayout(7, 1, 5, 5));
        gztPanel4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 1: (Winter+Winddruck)</span></html>"));

        gztPanel4.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(ständig)</span></i></html>"));
        
        gztPanel4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;5: <html>q<sub>GZT,5</sub> = </span></html>", qGzt5Labelaußen, einheit));
        
        gztPanel4.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(mittig)</span></i></html>"));
        
        gztPanel4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;6: <html>q<sub>GZT,6</sub> = </span></html>", qGzt6Labelaußen, einheit));
        
        gztPanel4.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(kurz)</span></i><html>"));
        
        gztPanel4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;7: <html>q<sub>GZT,7</sub> = </span></html>", qGzt7Labelaußen, einheit));
        gztPanel4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;8: <html>q<sub>GZT,8</sub> = </span></html>", qGzt8Labelaußen, einheit));
        ausgabePanel2.add(gztPanel4);

        JPanel gztPanel5 = new JPanel(new GridLayout(7, 1, 5, 5));
        gztPanel5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 2: (Winter+Winddruck)</span></html>"));

        gztPanel5.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(ständig)</span></i></html>"));
        
        gztPanel5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;5: <html>q<sub>GZT,5</sub> = </span></html>", qGzt5Labelmitte, einheit));
        
        gztPanel5.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(mittig)</span></i></html>"));
        
        gztPanel5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;6: <html>q<sub>GZT,6</sub> = </span></html>", qGzt6Labelmitte, einheit));
        
        gztPanel5.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(kurz)</span></i><html>"));
        
        gztPanel5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;7: <html>q<sub>GZT,7</sub> = </span></html>", qGzt7Labelmitte, einheit));
        gztPanel5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;8: <html>q<sub>GZT,8</sub> = </span></html>", qGzt8Labelmitte, einheit));
        ausgabePanel2.add(gztPanel5);

        JPanel gztPanel6 = new JPanel(new GridLayout(7, 1, 5, 5));
        gztPanel6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 3: (Winter+Winddruck)</span></html>"));

        gztPanel6.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(ständig)</span></i></html>"));
        
        gztPanel6.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;5: <html>q<sub>GZT,5</sub> = </span></html>", qGzt5Labelinnen, einheit));
        
        gztPanel6.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(mittig)</span></i></html>"));
        
        gztPanel6.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;6: <html>q<sub>GZT,6</sub> = </span></html>", qGzt6Labelinnen, einheit));
        
        gztPanel6.add(new JLabel("<html><i><span style='font-family:Lucida Console; font-size:11pt'>(kurz)</span></i><html>"));
        
        gztPanel6.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;7: <html>q<sub>GZT,7</sub> = </span></html>", qGzt7Labelinnen, einheit));
        gztPanel6.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;8: <html>q<sub>GZT,8</sub> = </span></html>", qGzt8Labelinnen, einheit));
        ausgabePanel2.add(gztPanel6);
        this.gztPanel6 = gztPanel6;


        JPanel gztMainPanel = new JPanel();
        gztMainPanel.setLayout(new BoxLayout(gztMainPanel, BoxLayout.Y_AXIS));
        gztMainPanel.add(abstandPanelGZT);
        gztMainPanel.add(ausgabePanel2);
        
        JPanel abstandPanelGZT2 = new JPanel(new GridLayout(2, 1));
        abstandPanelGZT2.add(new JLabel(" "));
        abstandPanelGZT2.add(new JLabel(" "));
        gztMainPanel.add(abstandPanelGZT2);


        JPanel lastkombinationen1Panel = new JPanel(new BorderLayout());
        lastkombinationen1Panel.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Beiwert k<sub>&sigma;</sub> zur Berechnung der Spannung</span></b></html>"));

        setupLastkombinationenTabelle(lastkombinationen1Panel);
        gztMainPanel.add(lastkombinationen1Panel);
        
        JPanel abstandPanelGZT4 = new JPanel(new GridLayout(2, 1));
        abstandPanelGZT4.add(new JLabel(" "));
        abstandPanelGZT4.add(new JLabel(" "));
        gztMainPanel.add(abstandPanelGZT4);


        JPanel nachweiswerteGztPanel = new JPanel(new BorderLayout());
        nachweiswerteGztPanel.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Nachweiswerte</span></b></html>"));

        JPanel inneresPanel = new JPanel(new GridLayout(6, 3, 15, 15));
        this.inneresPanel = inneresPanel; 
        
        JPanel panelAußen1 = new JPanel(new GridLayout(1, 1,5,5));
        panelAußen1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungslast im GZT</span></html>"));
        panelAußen1.add(qGztLabelaußenMax);
        inneresPanel.add(panelAußen1);
        JPanel panelMitte1 = new JPanel(new GridLayout(1, 1,5,5));
        panelMitte1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungslast im GZT</span></html>"));
        panelMitte1.add(qGztLabelmitteMax);
        inneresPanel.add(panelMitte1);
        JPanel panelInnen1 = new JPanel(new GridLayout(1, 1,5,5));
        panelInnen1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungslast im GZT</span></html>"));
        panelInnen1.add(qGztLabelinnenMax);
        inneresPanel.add(panelInnen1);
        this.panelInnen1 = panelInnen1;

        
        
        JPanel panelAußen2 = new JPanel(new GridLayout(1, 1,5,5));
        panelAußen2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Seitenverhältnis</span></html>"));
        panelAußen2.add(lamda1);
        inneresPanel.add(panelAußen2);
        JPanel panelMitte2 = new JPanel(new GridLayout(1, 1,5,5));
        panelMitte2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Seitenverhältnis</span></html>"));
        panelMitte2.add(lamda2);
        inneresPanel.add(panelMitte2);
        JPanel panelInnen2 = new JPanel(new GridLayout(1, 1,5,5));
        panelInnen2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Seitenverhältnis</span></html>"));
        panelInnen2.add(lamda3);
        inneresPanel.add(panelInnen2);
        this.panelInnen2 = panelInnen2;

        JPanel panelAußen3 = new JPanel(new GridLayout(1, 1,5,5));
        panelAußen3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Normierte Flächenlast</span></html>"));
        panelAußen3.add(normierteFlächenlastAußenGzt);
        inneresPanel.add(panelAußen3);
        JPanel panelMitte3 = new JPanel(new GridLayout(1, 1,5,5));
        panelMitte3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Normierte Flächenlast</span></html>"));
        panelMitte3.add(normierteFlächenlastMitteGzt);
        inneresPanel.add(panelMitte3);
        JPanel panelInnen3 = new JPanel(new GridLayout(1, 1,5,5));
        panelInnen3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Normierte Flächenlast</span></html>"));
        panelInnen3.add(normierteFlächenlastInnenGzt);
        inneresPanel.add(panelInnen3);
       this.panelInnen3 = panelInnen3;

        
        JPanel panelAußen4 = new JPanel(new GridLayout(1, 1,5,5));
        panelAußen4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Spannungsfaktor</span></html>"));
        panelAußen4.add(spannungsfaktorAußenGzt);
        inneresPanel.add(panelAußen4);
        JPanel panelMitte4 = new JPanel(new GridLayout(1, 1,5,5));
        panelMitte4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Spannungsfaktor</span></html>"));
        panelMitte4.add(spannungsfaktorMitteGzt);
        inneresPanel.add(panelMitte4);
        JPanel panelInnen4 = new JPanel(new GridLayout(1, 1,5,5));
        panelInnen4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Spannungsfaktor</span></html>"));
        panelInnen4.add(spannungsfaktorInnenGzt);
        inneresPanel.add(panelInnen4);
        this.panelInnen4 = panelInnen4;

   
         
        JPanel panelAußen5 = new JPanel(new GridLayout(1, 1,5,5));
        panelAußen5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungsspannung</span></html>"));
        panelAußen5.add(bemessungsspannungAußenGzt);
        inneresPanel.add(panelAußen5);
        JPanel panelMitte5 = new JPanel(new GridLayout(1, 1,5,5));
        panelMitte5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungsspannung</span></html>"));
        panelMitte5.add(bemessungsspannungMitteGzt);
        inneresPanel.add(panelMitte5);
        JPanel panelInnen5 = new JPanel(new GridLayout(1, 1,5,5));
        panelInnen5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungsspannung</span></html>"));
        panelInnen5.add(bemessungsspannungInnenGzt);
        inneresPanel.add(panelInnen5);
        this.panelInnen5 = panelInnen5;

        
        JPanel panelAußen6 = new JPanel(new GridLayout(1, 1,5,5));
        panelAußen6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Maximale Beanspruchung</span></html>"));
        panelAußen6.add(maximaleBeanspruchungAußenGzt);
        inneresPanel.add(panelAußen6);
        JPanel panelMitte6 = new JPanel(new GridLayout(1, 1,5,5));
        panelMitte6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Maximale Beanspruchung</span></html>"));
        panelMitte6.add(maximaleBeanspruchungMitteGzt);
        inneresPanel.add(panelMitte6);
        JPanel panelInnen6 = new JPanel(new GridLayout(1, 1,5,5));
        panelInnen6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Maximale Beanspruchung</span></html>"));
        panelInnen6.add(maximaleBeanspruchungInnenGzt);
        inneresPanel.add(panelInnen6);
        this.panelInnen6 = panelInnen6;

        nachweiswerteGztPanel.add(inneresPanel, BorderLayout.CENTER);
        gztMainPanel.add(nachweiswerteGztPanel);

        
        JPanel abstandPanelGZT5 = new JPanel(new GridLayout(2, 1));
        abstandPanelGZT5.add(new JLabel(" "));
        abstandPanelGZT5.add(new JLabel(" "));
        gztMainPanel.add(abstandPanelGZT5);

        
        JPanel nachweisGztPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        nachweisGztPanel.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Nachweis</span></b></html>"));
        this.nachweisGztPanel = nachweisGztPanel;
        
        JPanel panelAußenNachweis = new JPanel(new GridLayout(1, 1,5,5));
        panelAußenNachweis.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 1</span></html>"));
        panelAußenNachweis.add(NachweisGZTaußen);
        nachweisGztPanel.add(panelAußenNachweis);
        
        JPanel panelMitteNachweis = new JPanel(new GridLayout(1, 1,5,5));
        panelMitteNachweis.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 2</span></html>"));
        panelMitteNachweis.add(NachweisGZTmitte);
        nachweisGztPanel.add(panelMitteNachweis);
        
        JPanel panelInnenNachweis = new JPanel(new GridLayout(1, 1,5,5));
        panelInnenNachweis.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 3</span></html>"));
        panelInnenNachweis.add(NachweisGZTinnen);
        nachweisGztPanel.add(panelInnenNachweis);
        this.panelInnenNachweis = panelInnenNachweis;
        gztMainPanel.add(nachweisGztPanel);

        JPanel abstandPanel = new JPanel(new GridLayout(5, 1));
        abstandPanel.add(new JLabel(""));
        abstandPanel.add(new JLabel(""));
        abstandPanel.add(new JLabel("<html><b><span style='font-family:Lucida Console; font-size:18pt'>Nachweis im Grenzzustand der Gebrauchstauglichkeit</span></b></html>",SwingConstants.CENTER));
        abstandPanel.add(new JLabel(""));
        abstandPanel.add(new JLabel(""));

        gztMainPanel.add(abstandPanel);
        southPanelContainer.add(gztMainPanel, BorderLayout.CENTER);
        
        JPanel ausgabePanelGZG = new JPanel(new GridLayout(2, 3, 15, 15));
        ausgabePanelGZG.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Lastkombinationen im GZG</span></b></html>"));
        this.ausgabePanelGZG = ausgabePanelGZG;

        JPanel gzgPanel1 = new JPanel(new GridLayout(2, 1, 5, 5));
        gzgPanel1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 1: (Sommer+Windsog)</span></html>"));
        gzgPanel1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;1: <html>q<sub>GZG,1</sub> = </span></html>", qGzg1Labelaußen, einheit));
        gzgPanel1.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;2: <html>q<sub>GZG,2</sub> = </span></html>", qGzg2Labelaußen, einheit));
        ausgabePanelGZG.add(gzgPanel1);
        
        JPanel gzgPanel2 = new JPanel(new GridLayout(2, 1, 5, 5));
        gzgPanel2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 2: (Sommer+Windsog)</span></html>"));
        gzgPanel2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;1: <html>q<sub>GZG,1</sub> = </span></html>", qGzg1Labelmitte, einheit));
        gzgPanel2.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;2: <html>q<sub>GZG,2</sub> = </span></html>", qGzg2Labelmitte, einheit));
        ausgabePanelGZG.add(gzgPanel2);

        JPanel gzgPanel3 = new JPanel(new GridLayout(2, 1, 5, 5));
        gzgPanel3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 3: (Sommer+Windsog)</span></html>"));
        gzgPanel3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;1: <html>q<sub>GZG,1</sub> = </span></html>", qGzg1Labelinnen, einheit));
        gzgPanel3.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;2: <html>q<sub>GZG,2</sub> = </span></html>", qGzg2Labelinnen, einheit));
        ausgabePanelGZG.add(gzgPanel3);
        this.gzgPanel3 = gzgPanel3;
        
        JPanel gzgPanel4 = new JPanel(new GridLayout(2, 1, 5, 5));
        gzgPanel4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 1: (Winter+Winddruck)</span></html>"));
        gzgPanel4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;3: <html>q<sub>GZG,3</sub> = </span></html>", qGzg3Labelaußen, einheit));
        gzgPanel4.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;4: <html>q<sub>GZG,4</sub> = </span></html>", qGzg4Labelaußen, einheit));
        ausgabePanelGZG.add(gzgPanel4);

        JPanel gzgPanel5 = new JPanel(new GridLayout(2, 1, 5, 5));
        gzgPanel5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 2: (Winter+Winddruck)</span></html>"));
        gzgPanel5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;3: <html>q<sub>GZG,3</sub> = </span></html>", qGzg3Labelmitte, einheit));
        gzgPanel5.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;4: <html>q<sub>GZG,4</sub> = </span></html>", qGzg4Labelmitte, einheit));
        ausgabePanelGZG.add(gzgPanel5);

        JPanel gzgPanel6 = new JPanel(new GridLayout(2, 1, 5, 5));
        gzgPanel6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 3: (Winter+Winddruck)</span></html>"));
        gzgPanel6.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;3: <html>q<sub>GZG,3</sub> = </span></html>", qGzg3Labelinnen, einheit));
        gzgPanel6.add(makeValueRow("<html><span style='font-family:Lucida Console; font-size:12pt'>Kombination&nbsp;4: <html>q<sub>GZG,4</sub> = </span></html>", qGzg4Labelinnen, einheit));
        ausgabePanelGZG.add(gzgPanel6);
        this.gzgPanel6 = gzgPanel6;  
        gztMainPanel.add(ausgabePanelGZG);
        
        JPanel abstandPanelGZT3 = new JPanel(new GridLayout(2, 1));
        abstandPanelGZT3.add(new JLabel(" "));
        abstandPanelGZT3.add(new JLabel(" "));
        gztMainPanel.add(abstandPanelGZT3);
       
        JPanel kwTabellePanel = new JPanel(new BorderLayout());

        kwTabellePanel.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Beiwert  k<sub>w</sub> zur Berechnung der Verformung</span></b></html>"));
        setupLastkombinationenTabelle2(kwTabellePanel);
        gztMainPanel.add(kwTabellePanel);
        
        JPanel abstandPanelGZT6 = new JPanel(new GridLayout(2, 1));
        abstandPanelGZT6.add(new JLabel(" "));
        abstandPanelGZT6.add(new JLabel(" "));
        gztMainPanel.add(abstandPanelGZT6);
     

        JPanel nachweiswerteGzgPanel = new JPanel(new BorderLayout());
        nachweiswerteGzgPanel.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Nachweiswerte</span></b></html>"));
       
        JPanel gzgNachweisPanel = new JPanel(new GridLayout(6, 3, 15, 15));
        this.gzgNachweisPanel = gzgNachweisPanel;
       
        JPanel nachweisPanelAußenGZG1 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelAußenGZG1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungslast im GZG</span></html>"));
        nachweisPanelAußenGZG1.add(qGzgLabelaußenMax);
        gzgNachweisPanel.add(nachweisPanelAußenGZG1);
        JPanel nachweisPanelMitteGZG1 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelMitteGZG1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungslast im GZG</span></html>"));
        nachweisPanelMitteGZG1.add(qGzgLabelmitteMax);
        gzgNachweisPanel.add(nachweisPanelMitteGZG1);
        JPanel nachweisPanelInnenGZG1 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelInnenGZG1.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungslast im GZG</span></html>"));
        nachweisPanelInnenGZG1.add(qGzgLabelinnenMax);
        gzgNachweisPanel.add(nachweisPanelInnenGZG1);
        this.nachweisPanelInnenGZG1 = nachweisPanelInnenGZG1;


        JPanel nachweisPanelAußenGZG2 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelAußenGZG2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Seitenverhältnis</span></html>"));
        nachweisPanelAußenGZG2.add(lamda4);
        gzgNachweisPanel.add(nachweisPanelAußenGZG2);
        JPanel nachweisPanelMitteGZG2 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelMitteGZG2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Seitenverhältnis</span></html>"));
        nachweisPanelMitteGZG2.add(lamda5);
        gzgNachweisPanel.add(nachweisPanelMitteGZG2);
        JPanel nachweisPanelInnenGZG2 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelInnenGZG2.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Seitenverhältnis</span></html>"));
        nachweisPanelInnenGZG2.add(lamda6);
        gzgNachweisPanel.add(nachweisPanelInnenGZG2);
        this.nachweisPanelInnenGZG2 = nachweisPanelInnenGZG2;

        JPanel nachweisPanelAußenGZG3 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelAußenGZG3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Normierte Flächenlast</span></html>"));
        nachweisPanelAußenGZG3.add(normierteFlächenlastAußenGzg);
        gzgNachweisPanel.add(nachweisPanelAußenGZG3);
        JPanel nachweisPanelMitteGZG3 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelMitteGZG3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Normierte Flächenlast</span></html>"));
        nachweisPanelMitteGZG3.add(normierteFlächenlastMitteGzg);
        gzgNachweisPanel.add(nachweisPanelMitteGZG3);
        JPanel nachweisPanelInnenGZG3 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelInnenGZG3.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Normierte Flächenlast</span></html>"));
        nachweisPanelInnenGZG3.add(normierteFlächenlastInnenGzg);
        gzgNachweisPanel.add(nachweisPanelInnenGZG3);
        this.nachweisPanelInnenGZG3 = nachweisPanelInnenGZG3;

        JPanel nachweisPanelAußenGZG4 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelAußenGZG4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Verformungsfaktor</span></html>"));
        nachweisPanelAußenGZG4.add(verformungsfaktorAußenGzg);
        gzgNachweisPanel.add(nachweisPanelAußenGZG4);
        JPanel nachweisPanelMitteGZG4 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelMitteGZG4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Verformungsfaktor</span></html>"));
        nachweisPanelMitteGZG4.add(verformungsfaktorMitteGzg);
        gzgNachweisPanel.add(nachweisPanelMitteGZG4);
        JPanel nachweisPanelInnenGZG4 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelInnenGZG4.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Verformungsfaktor</span></html>"));
        nachweisPanelInnenGZG4.add(verformungsfaktorInnenGzg);
        gzgNachweisPanel.add(nachweisPanelInnenGZG4);
        this.nachweisPanelInnenGZG4 = nachweisPanelInnenGZG4;

        JPanel nachweisPanelAußenGZG5 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelAußenGZG5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungsspannung</span></html>"));
        nachweisPanelAußenGZG5.add(bemessungsverformungAußenGzg);
        gzgNachweisPanel.add(nachweisPanelAußenGZG5);
        JPanel nachweisPanelMitteGZG5 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelMitteGZG5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungsspannung</span></html>"));
        nachweisPanelMitteGZG5.add(bemessungsverformungMitteGzg);
        gzgNachweisPanel.add(nachweisPanelMitteGZG5);
        JPanel nachweisPanelInnenGZG5 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelInnenGZG5.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Bemessungsspannung</span></html>"));
        nachweisPanelInnenGZG5.add(bemessungsverformungInnenGzg);
        gzgNachweisPanel.add(nachweisPanelInnenGZG5);
        this.nachweisPanelInnenGZG5 = nachweisPanelInnenGZG5;

      
        JPanel nachweisPanelAußenGZG6 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelAußenGZG6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Grenzwert der Verformung</span></html>"));
        nachweisPanelAußenGZG6.add(grenzwertDerVerformungAußenGzg);
        gzgNachweisPanel.add(nachweisPanelAußenGZG6);
        JPanel nachweisPanelMitteGZG6 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelMitteGZG6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Grenzwert der Verformung</span></html>"));
        nachweisPanelMitteGZG6.add(grenzwertDerVerformungMitteGzg);
        gzgNachweisPanel.add(nachweisPanelMitteGZG6);
        JPanel nachweisPanelInnenGZG6 = new JPanel(new GridLayout(1, 1,5,5));
        nachweisPanelInnenGZG6.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Grenzwert der Verformung</span></html>"));
        nachweisPanelInnenGZG6.add(grenzwertDerVerformungInnenGzg);
        gzgNachweisPanel.add(nachweisPanelInnenGZG6);
        this.nachweisPanelInnenGZG6 = nachweisPanelInnenGZG6;

        nachweiswerteGzgPanel.add(gzgNachweisPanel, BorderLayout.CENTER);
        gztMainPanel.add(nachweiswerteGzgPanel);

        JPanel abstandPanelGZT7 = new JPanel(new GridLayout(2, 1));
        abstandPanelGZT7.add(new JLabel(" "));
        abstandPanelGZT7.add(new JLabel(" "));
        gztMainPanel.add(abstandPanelGZT7);
        
        JPanel nachweisGzgPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        nachweisGzgPanel.setBorder(BorderFactory.createTitledBorder("<html><b><span style='font-family:Lucida Console; font-size:15pt'>Nachweis</span></b></html>"));
        this.nachweisGzgPanel = nachweisGzgPanel;

        JPanel panelAußenNachweisGzg = new JPanel(new GridLayout(1, 1,5,5));
        panelAußenNachweisGzg.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 1</span></html>"));
        panelAußenNachweisGzg.add(NachweisGZGaußen);
        nachweisGzgPanel.add(panelAußenNachweisGzg);
        
        JPanel panelMitteNachweisGzg = new JPanel(new GridLayout(1, 1,5,5));
        panelMitteNachweisGzg.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 2</span></html>"));
        panelMitteNachweisGzg.add(NachweisGZGmitte);
        nachweisGzgPanel.add(panelMitteNachweisGzg);
        
        JPanel panelInnenNachweisGzg = new JPanel(new GridLayout(1, 1,5,5));
        panelInnenNachweisGzg.setBorder(BorderFactory.createTitledBorder("<html><span style='font-family:Lucida Console; font-size:14pt'>Scheibe 3</span></html>"));
        panelInnenNachweisGzg.add(NachweisGZGinnen);
        nachweisGzgPanel.add(panelInnenNachweisGzg);
        this.panelInnenNachweisGzg = panelInnenNachweisGzg;
        gztMainPanel.add(nachweisGzgPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(this.applyB);
        buttonPanel.add(this.exitB);
        southPanelContainer.add(buttonPanel, BorderLayout.SOUTH);
        bottomContent.add(southPanelContainer, BorderLayout.SOUTH);
     
        this.MehrscheibenIsolierverglasung.addActionListener(this);
        this.applyB.addActionListener(this);
        this.exitB.addActionListener(this);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.add(contentPanel, BorderLayout.CENTER);
        rootPanel.add(bottomContent, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(rootPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setContentPane(scrollPane);
 
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(viewerWrapper, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
        this.setSize(900, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.apply();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	Object quelle = e.getSource();
        if (quelle == applyB) {
            apply();
        } else if (quelle == exitB) {
            System.exit(0);
        } else if (quelle == MehrscheibenIsolierverglasung) {
            setSectionPanel(MehrscheibenIsolierverglasung.getSelectedItem());
        }
    }
    
    private void setSectionPanel(Object st) {
        if (this.mehrscheibenIsolierverglasungPanel != null) {
            this.sectionPanel.remove(this.mehrscheibenIsolierverglasungPanel);
        }

        if (st.equals("DreifachIsolierverglasung")) {
            this.mehrscheibenIsolierverglasungPanel = new FDreifachIsolierverglasungPanel();

            if (!berechneteWertePanel1.isAncestorOf(teil5Container)) {
                berechneteWertePanel1.add(teil5Container);
            }
            if (!leeresPanel.isAncestorOf(teil3)) {
                leeresPanel.add(teil3);
            }
            if (!ausgabePanel2.isAncestorOf(gztPanel3)) {
                ausgabePanel2.add(gztPanel3, 2);
            }
            if (!ausgabePanel2.isAncestorOf(gztPanel6)) {
                ausgabePanel2.add(gztPanel6, 5);
            }
            if (!inneresPanel.isAncestorOf(panelInnen)) {
                inneresPanel.add(panelInnen, 2);
            }
            if (!nachweisGztPanel.isAncestorOf(panelInnenNachweis)) {
                nachweisGztPanel.add(panelInnenNachweis, 2);
            }
            if (!ausgabePanelGZG.isAncestorOf(gzgPanel3)) {
                ausgabePanelGZG.add(gzgPanel3, 2);
            }
            if (!ausgabePanelGZG.isAncestorOf(gzgPanel6)) {
                ausgabePanelGZG.add(gzgPanel6, 5);
            }
            if (!gzgNachweisPanel.isAncestorOf(nachweisPanelInnenGZG)) {
                gzgNachweisPanel.add(nachweisPanelInnenGZG, 2);
            }
            if (!nachweisGzgPanel.isAncestorOf(panelInnenNachweisGzg)) {
                nachweisGzgPanel.add(panelInnenNachweisGzg, 2);
            }

        } else {
            this.mehrscheibenIsolierverglasungPanel = new HZweifachIsolierverglasungPanel(); // ❗ dein Zweifach-Panel

            berechneteWertePanel1.remove(teil5Container);
            leeresPanel.remove(teil3);
            ausgabePanel2.remove(gztPanel3);
            ausgabePanel2.remove(gztPanel6);
            inneresPanel.remove(panelInnen1);
            inneresPanel.remove(panelInnen2);
            inneresPanel.remove(panelInnen3);
            inneresPanel.remove(panelInnen4);
            inneresPanel.remove(panelInnen5);
            inneresPanel.remove(panelInnen6);

            nachweisGztPanel.remove(panelInnenNachweis);
            ausgabePanelGZG.remove(gzgPanel3);
            ausgabePanelGZG.remove(gzgPanel6);
            gzgNachweisPanel.remove(nachweisPanelInnenGZG1);
            gzgNachweisPanel.remove(nachweisPanelInnenGZG2);
            gzgNachweisPanel.remove(nachweisPanelInnenGZG3);
            gzgNachweisPanel.remove(nachweisPanelInnenGZG4);
            gzgNachweisPanel.remove(nachweisPanelInnenGZG5);
            gzgNachweisPanel.remove(nachweisPanelInnenGZG6);

            nachweisGzgPanel.remove(panelInnenNachweisGzg);
        }

        this.sectionPanel.add(this.mehrscheibenIsolierverglasungPanel, BorderLayout.CENTER);
        this.sectionPanel.revalidate();
        this.sectionPanel.repaint();

        berechneteWertePanel1.revalidate(); berechneteWertePanel1.repaint();
        leeresPanel.revalidate(); leeresPanel.repaint();
        ausgabePanel2.revalidate(); ausgabePanel2.repaint();
        inneresPanel.revalidate(); inneresPanel.repaint();
        nachweisGztPanel.revalidate(); nachweisGztPanel.repaint();
        ausgabePanelGZG.revalidate(); ausgabePanelGZG.repaint();
        gzgNachweisPanel.revalidate(); gzgNachweisPanel.repaint();
        nachweisGzgPanel.revalidate(); nachweisGzgPanel.repaint();
    }
    
    private void apply() {
    	 try {
    	 String isolierverglasungAuswahl = (String) this.MehrscheibenIsolierverglasung.getSelectedItem();
    	 double elastizitaetsmodul = bestimmeEModul(); 
    	 double festigkeit = bestimmefk(); 
    	 double lastfall2 = Double.parseDouble(this.Lastfall2.getText().replace(",", "."));
    	 double lastfall3 = Double.parseDouble(this.Lastfall3.getText().replace(",", "."));
    	 double lastfall4 = Double.parseDouble(this.Lastfall4.getText().replace(",", "."));
    	 double lastfall5 = Double.parseDouble(this.Lastfall5.getText().replace(",", "."));
    	 double winddruck = Double.parseDouble(this.WinddruckWk.getText().replace(",", "."));
    	 double windsog = Double.parseDouble(this.WindsogWk.getText().replace(",", "."));
  
    	 DMehrscheibenIsolierverglasung verglasung = this.mehrscheibenIsolierverglasungPanel.erstelleDMehrscheibenIsolierverglasung();

    	 if (verglasung == null) {
    		 JOptionPane.showMessageDialog(this,"Die Isolierverglasung konnte nicht erstellt werden.","Fehler",JOptionPane.ERROR_MESSAGE);
    	     return;
    	     }

    	 if (elastizitaetsmodul == 0 || verglasung.getD1() == 0) {
    	     JOptionPane.showMessageDialog(this,"Elastizitätsmodul oder d1 darf nicht 0 sein!","Fehlerhafte Eingabe",JOptionPane.ERROR_MESSAGE);
    	     return; 
    	     }
    	      
    	 BBemessung bemessung = new BBemessung(verglasung, elastizitaetsmodul, festigkeit);
    	     
    	 bemessung.setLastfall2(lastfall2);
    	 bemessung.setLastfall3(lastfall3);
    	 bemessung.setLastfall4(lastfall4); 
    	 bemessung.setLastfall5(lastfall5); 
    	 bemessung.setWinddruckWk(winddruck);
    	 bemessung.setWindsogWk(windsog);

    	 // Anfangswerte
    	 double berechneterBv = bemessung.berechneBv(); 
    	 double berechnetesA = bemessung.berechneA();
    	 
    	 double berechneVolumenD1 = bemessung.berechneVolumenD1();
    	 double berechneVolumenD2 = bemessung.berechneVolumenD2();
    	 double berechneVolumenD3 = bemessung.berechneVolumenD3();
    	 
    	 double berechneSZR1 = bemessung.Scheibenzwischenraumvolumen1();
    	 double berechneSZR2 = bemessung.Scheibenzwischenraumvolumen2();
    	 
    	 double berechnealpha1 = bemessung.alpha1();
    	 double berechnealpha1plus = bemessung.alpha1plus();
    	 double berechnealpha2 = bemessung.alpha2();
    	 double berechnealpha2plus = bemessung.alpha2plus();
    	 
    	 double berechnephi1 = bemessung.phi1();
    	 double berechnephi2 = bemessung.phi2();
    	 
    	 double berechnebeta = bemessung.beta();
   
     	 // Druckdifferenzen
    	 double berechneterP12 = bemessung.deltaP12();
    	 double berechneterP13 = bemessung.deltaP13();
    	 double berechneterP14 = bemessung.deltaP14();
    	 double berechneterP15 = bemessung.deltaP15();
    	 double berechneterP17 = bemessung.deltaP17();
    	 double berechneterP18 = bemessung.deltaP18();
    	 
    	 double berechneterP22 = bemessung.deltaP22();
    	 double berechneterP23 = bemessung.deltaP23();
    	 double berechneterP24 = bemessung.deltaP24();
    	 double berechneterP25 = bemessung.deltaP25();
    	 double berechneterP27 = bemessung.deltaP27();
    	 double berechneterP28 = bemessung.deltaP28();
    	  
     	 // Lastanteile
    	 double berechneterPres12 = bemessung.pres12();
    	 double berechneterPres13 = bemessung.pres13();
    	 double berechneterPres14 = bemessung.pres14();
    	 double berechneterPres15 = bemessung.pres15();
    	 double berechneterPres17 = bemessung.pres17();
    	 double berechneterPres18 = bemessung.pres18();
    	        
    	 double berechneterPres22 = bemessung.pres22();
    	 double berechneterPres23 = bemessung.pres23();
    	 double berechneterPres24 = bemessung.pres24();
    	 double berechneterPres25 = bemessung.pres25();
    	 double berechneterPres27 = bemessung.pres27();
    	 double berechneterPres28 = bemessung.pres28();
    	        
    	 double berechneterPres32 = bemessung.pres32();
    	 double berechneterPres33 = bemessung.pres33();
    	 double berechneterPres34 = bemessung.pres34();
    	 double berechneterPres35 = bemessung.pres35();
    	 double berechneterPres37 = bemessung.pres37();
    	 double berechneterPres38 = bemessung.pres38();
    	    
    	 // Lastkombination GZT
    	 double berechneqGzt1Labelaußen = bemessung.bestimmeQGzt1außen();
    	 double berechneqGzt2Labelaußen = bemessung.bestimmeQGzt2außen();
    	 double berechneqGzt3Labelaußen = bemessung.bestimmeQGzt3außen();
    	 double berechneqGzt4Labelaußen = bemessung.bestimmeQGzt4außen();
    	 double berechneqGzt5Labelaußen = bemessung.bestimmeQGzt5außen();
    	 double berechneqGzt6Labelaußen = bemessung.bestimmeQGzt6außen();
    	 double berechneqGzt7Labelaußen = bemessung.bestimmeQGzt7außen();
    	 double berechneqGzt8Labelaußen = bemessung.bestimmeQGzt8außen();
    	        
    	 double berechneqGzt1Labelmitte = bemessung.bestimmeQGzt1mitte();
    	 double berechneqGzt2Labelmitte = bemessung.bestimmeQGzt2mitte();
    	 double berechneqGzt3Labelmitte = bemessung.bestimmeQGzt3mitte();
    	 double berechneqGzt4Labelmitte = bemessung.bestimmeQGzt4mitte();
    	 double berechneqGzt5Labelmitte = bemessung.bestimmeQGzt5mitte();
    	 double berechneqGzt6Labelmitte = bemessung.bestimmeQGzt6mitte();
    	 double berechneqGzt7Labelmitte = bemessung.bestimmeQGzt7mitte();
    	 double berechneqGzt8Labelmitte = bemessung.bestimmeQGzt8mitte();
    	        
    	 double berechneqGzt1Labelinnen = bemessung.bestimmeQGzt1innen();
    	 double berechneqGzt2Labelinnen = bemessung.bestimmeQGzt2innen();
    	 double berechneqGzt3Labelinnen = bemessung.bestimmeQGzt3innen();
    	 double berechneqGzt4Labelinnen = bemessung.bestimmeQGzt4innen();
    	 double berechneqGzt5Labelinnen = bemessung.bestimmeQGzt5innen();
    	 double berechneqGzt6Labelinnen = bemessung.bestimmeQGzt6innen();
    	 double berechneqGzt7Labelinnen = bemessung.bestimmeQGzt7innen();
    	 double berechneqGzt8Labelinnen = bemessung.bestimmeQGzt8innen();
    	 
    	 // Bemessungslast GZT
    	 double bestimmeMAXqGZTaußen = bemessung.bestimmeMAXqGZTaußen();
    	 double bestimmeMAXqGZTmitte = bemessung.bestimmeMAXqGZTmitte();
    	 double bestimmeMAXqGZTinnen = bemessung.bestimmeMAXqGZTinnen();

    	 // Lamda
     	 double bestimmeLamda = bemessung.berechneLamda(); // muss nicht 3 mal!
     	 
     	 // Normierte Flächenlast GZT
     	 double bestimmeNormierteFlächenlastAußenGzt = pSternAlsNullCheckbox.isSelected() ? 0.0 : bemessung.berechnePSternaußen();	
     	 double bestimmeNormierteFlächenlastMitteGzt = pSternAlsNullCheckbox.isSelected() ? 0.0 : bemessung.berechnePSternmitte();
     	 double bestimmeNormierteFlächenlastInnenGzt = pSternAlsNullCheckbox.isSelected() ? 0.0 : bemessung.berechnePSterninnen();
     	 
     	 // Spannungsfaktor GZT
     	 double bestimmeSpannungsfaktorAußenGzt = bemessung.interpolateKSigmaZweischrittaußen();
     	 double bestimmeSpannungsfaktorMitteGzt = bemessung.interpolateKSigmaZweischrittmitte();
     	 double bestimmeSpannungsfaktorInnenGzt = bemessung.interpolateKSigmaZweischrittinnen();
     	 
     	 // Bemessungsspannung GZT
     	 double bestimmeBemessungsspannungAußenGzt = bemessung.SigmaDaußen();
     	 double bestimmeBemessungsspannungMitteGzt = bemessung.SigmaDmitte();
     	 double bestimmeBemessungsspannungInnenGzt = bemessung.SigmaDinnen();
     	 
     	 // Maximale Beanspruchung GZT
     	 double bestimmeMaximaleBeanspruchungAußenGzt = bemessung.Rdaußen();
     	 double bestimmeMaximaleBeanspruchungMitteGzt = bemessung.Rdmitte();
     	 double bestimmeMaximaleBeanspruchungInnenGzt = bemessung.Rdinnen();

     	 // eta GZT
      	 double etaAußenGZT = bemessung.NachweisGZTaußen();
     	 double etaMitteGZT = bemessung.NachweisGZTmitte();
     	 double etaInnenGZT = bemessung.NachweisGZTinnen();

    	 // Lastkombination GZG
    	 double berechneqGzg1Labelaußen = bemessung.bestimmeQGzg1außen();
     	 double berechneqGzg2Labelaußen = bemessung.bestimmeQGzg2außen();
      	 double berechneqGzg3Labelaußen = bemessung.bestimmeQGzg3außen();
     	 double berechneqGzg4Labelaußen = bemessung.bestimmeQGzg4außen();
          	        
     	 double berechneqGzg1Labelmitte = bemessung.bestimmeQGzg1mitte();
     	 double berechneqGzg2Labelmitte = bemessung.bestimmeQGzg2mitte();
     	 double berechneqGzg3Labelmitte = bemessung.bestimmeQGzg3mitte();
     	 double berechneqGzg4Labelmitte = bemessung.bestimmeQGzg4mitte();
     	        
     	 double berechneqGzg1Labelinnen = bemessung.bestimmeQGzg1innen();
     	 double berechneqGzg2Labelinnen = bemessung.bestimmeQGzg2innen();
     	 double berechneqGzg3Labelinnen = bemessung.bestimmeQGzg3innen();
     	 double berechneqGzg4Labelinnen = bemessung.bestimmeQGzg4innen();
     	 
    	 // Bemessungslast GZG
     	 double bestimmeMAXqGZGaußen = bemessung.bestimmeMAXqGZGaußen();
     	 double bestimmeMAXqGZGmitte = bemessung.bestimmeMAXqGZGmitte();
     	 double bestimmeMAXqGZGinnen = bemessung.bestimmeMAXqGZGinnen();
     	 
     	 // Normierte Flächenlast GZG
     	 double bestimmeNormierteFlächenlastAußenGzg = bemessung.berechnePSternaußenGZG();
     	 double bestimmeNormierteFlächenlastMitteGzg = bemessung.berechnePSternmitteGZG();
     	 double bestimmeNormierteFlächenlastInnenGzg = bemessung.berechnePSterninnenGZG();
     	 
     	 // Verfotmungsfaktor GZG
     	 double bestimmeVerformungsfaktorAußenGzg = bemessung.interpolateKSigmaZweischritt1außen();
     	 double bestimmeVerformungsfaktorMitteGzg = bemessung.interpolateKSigmaZweischritt1mitte();
     	 double bestimmeVerformungsfaktorInnenGzg = bemessung.interpolateKSigmaZweischritt1innen();
     	 
     	 // Bemessungsverformung GZG
     	 double bestimmeBemessungsverformungAußenGzg = bemessung.wdAußen();
     	 double bestimmeBemessungsverformungMitteGzg = bemessung.wdMitte();
     	 double bestimmeBemessungsverformungInnenGzg = bemessung.wdInnen();

     	 // Grenzwert der Verformung GZG
     	 double bestimmeGrenzwertDerVerformungAußenGzg = bemessung.cD();
     	 double bestimmeGrenzwertDerVerformungMitteGzg = bemessung.cD();
     	 double bestimmeGrenzwertDerVerformungInnenGzg = bemessung.cD();

     	// GZG-Werte
       	double etaAußenGZG = bemessung.NachweisGZGaußen();
      	double etaMitteGZG = bemessung.NachweisGZGmitte(); 
      	double etaInnenGZG = bemessung.NachweisGZGinnen();
     	 
    	 
    	 // Anfangswerte
     	 this.Bv.setText("<html>B<sub>v</sub> = " + String.format("%.4f", berechneterBv));
     	 this.A.setText("A = " + String.format("%.4f", berechnetesA));
     	 
     	 this.berechneVolumenD1.setText("<html>v<sub>q,d1</sub> = " + String.format("%.6f", berechneVolumenD1));
     	 this.berechneVolumenD2.setText("<html>v<sub>q,d2</sub> = " + String.format("%.6f", berechneVolumenD2));
     	 this.berechneVolumenD3.setText("<html>v<sub>q,d3</sub> = " + String.format("%.6f", berechneVolumenD3));

     	 this.Scheibenzwischenraumvolumen1.setText("<html>V<sub>SZR,1</sub> = " + String.format("%.4f", berechneSZR1));
     	 this.Scheibenzwischenraumvolumen2.setText("<html>V<sub>SZR,2</sub> = " + String.format("%.4f", berechneSZR2));
    	        
     	 this.alpha1.setText("<html>&alpha;<sub>1</sub> = " + String.format("%.4f", berechnealpha1));
     	 this.alpha1plus.setText("<html>&alpha;<sub>1</sub><sup>+</sup> = " + String.format("%.4f", berechnealpha1plus));
     	 this.alpha2.setText("<html>&alpha;<sub>2</sub> = " + String.format("%.4f", berechnealpha2));
     	 this.alpha2plus.setText("<html>&alpha;<sub>2</sub><sup>+</sup> = " + String.format("%.4f", berechnealpha2plus));
    	        
     	 this.phi1.setText("<html>&phi;<sub>1</sub> ="  + String.format("%.4f", berechnephi1));
     	 this.phi2.setText("<html>&phi;<sub>2</sub> =" + String.format("%.4f", berechnephi2));
     	 
     	 this.beta.setText("<html>&beta; = " + String.format("%.4f", berechnebeta));

     	 // Druckdifferenzen 
     	deltaP12.setText(String.format("%.3f", berechneterP12));
     	deltaP13.setText(String.format("%.3f", berechneterP13));
     	deltaP14.setText(String.format("%.3f", berechneterP14));
     	deltaP15.setText(String.format("%.3f", berechneterP15));
     	deltaP17.setText(String.format("%.3f", berechneterP17));
     	deltaP18.setText(String.format("%.3f", berechneterP18));

     	deltaP22.setText(String.format("%.3f", berechneterP22));
     	deltaP23.setText(String.format("%.3f", berechneterP23));
     	deltaP24.setText(String.format("%.3f", berechneterP24));
     	deltaP25.setText(String.format("%.3f", berechneterP25));
     	deltaP27.setText(String.format("%.3f", berechneterP27));
     	deltaP28.setText(String.format("%.3f", berechneterP28));
     	
     	 // Lastanteile
     	pres12.setText(String.format("%.3f", berechneterPres12));
     	pres13.setText(String.format("%.3f", berechneterPres13));
     	pres14.setText(String.format("%.3f", berechneterPres14));
     	pres15.setText(String.format("%.3f", berechneterPres15));
     	pres17.setText(String.format("%.3f", berechneterPres17));
     	pres18.setText(String.format("%.3f", berechneterPres18));

     	pres22.setText(String.format("%.3f", berechneterPres22));
     	pres23.setText(String.format("%.3f", berechneterPres23));
     	pres24.setText(String.format("%.3f", berechneterPres24));
     	pres25.setText(String.format("%.3f", berechneterPres25));
     	pres27.setText(String.format("%.3f", berechneterPres27));
     	pres28.setText(String.format("%.3f", berechneterPres28));

     	pres32.setText(String.format("%.3f", berechneterPres32));
     	pres33.setText(String.format("%.3f", berechneterPres33));
     	pres34.setText(String.format("%.3f", berechneterPres34));
     	pres35.setText(String.format("%.3f", berechneterPres35));
     	pres37.setText(String.format("%.3f", berechneterPres37));
     	pres38.setText(String.format("%.3f", berechneterPres38));
     	
    	 // Lastkombination GZT
     // GZT – Sommer (1–4)
     	qGzt1Labelaußen.setText(String.format("%.3f", berechneqGzt1Labelaußen));
     	qGzt2Labelaußen.setText(String.format("%.3f", berechneqGzt2Labelaußen));
     	qGzt3Labelaußen.setText(String.format("%.3f", berechneqGzt3Labelaußen));
     	qGzt4Labelaußen.setText(String.format("%.3f", berechneqGzt4Labelaußen));

     	qGzt1Labelmitte.setText(String.format("%.3f", berechneqGzt1Labelmitte));
     	qGzt2Labelmitte.setText(String.format("%.3f", berechneqGzt2Labelmitte));
     	qGzt3Labelmitte.setText(String.format("%.3f", berechneqGzt3Labelmitte));
     	qGzt4Labelmitte.setText(String.format("%.3f", berechneqGzt4Labelmitte));

     	qGzt1Labelinnen.setText(String.format("%.3f", berechneqGzt1Labelinnen));
     	qGzt2Labelinnen.setText(String.format("%.3f", berechneqGzt2Labelinnen));
     	qGzt3Labelinnen.setText(String.format("%.3f", berechneqGzt3Labelinnen));
     	qGzt4Labelinnen.setText(String.format("%.3f", berechneqGzt4Labelinnen));

     	// GZT – Winter (5–8)
     	qGzt5Labelaußen.setText(String.format("%.3f", berechneqGzt5Labelaußen));
     	qGzt6Labelaußen.setText(String.format("%.3f", berechneqGzt6Labelaußen));
     	qGzt7Labelaußen.setText(String.format("%.3f", berechneqGzt7Labelaußen));
     	qGzt8Labelaußen.setText(String.format("%.3f", berechneqGzt8Labelaußen));

     	qGzt5Labelmitte.setText(String.format("%.3f", berechneqGzt5Labelmitte));
     	qGzt6Labelmitte.setText(String.format("%.3f", berechneqGzt6Labelmitte));
     	qGzt7Labelmitte.setText(String.format("%.3f", berechneqGzt7Labelmitte));
     	qGzt8Labelmitte.setText(String.format("%.3f", berechneqGzt8Labelmitte));

     	qGzt5Labelinnen.setText(String.format("%.3f", berechneqGzt5Labelinnen));
     	qGzt6Labelinnen.setText(String.format("%.3f", berechneqGzt6Labelinnen));
     	qGzt7Labelinnen.setText(String.format("%.3f", berechneqGzt7Labelinnen));
     	qGzt8Labelinnen.setText(String.format("%.3f", berechneqGzt8Labelinnen));
     	
     	
        String einheit = "<html><span style='font-family:Lucida Console; font-size:12pt'><html>kN/m<sup>2</sup></html></span></html>";

    	 // Bemessungslast GZT
     	 this.qGztLabelaußenMax.setText("<html><span style='font-family:Lucida Console; font-size:12pt'><html>q<sub>GZT,max,außen</sub></span> =" + String.format("%.3f", bestimmeMAXqGZTaußen) + einheit);
     	 this.qGztLabelmitteMax.setText("<html><span style='font-family:Lucida Console; font-size:12pt'><html>q<sub>GZT,max,mitte</sub></span> =" + String.format("%.3f", bestimmeMAXqGZTmitte) + einheit);
     	 this.qGztLabelinnenMax.setText("<html><span style='font-family:Lucida Console; font-size:12pt'><html>q<sub>GZT,max,innen</sub></span> =" + String.format("%.3f", bestimmeMAXqGZTinnen) + einheit);
     	 
     	 // Lamda GZT
     	 this.lamda1.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&lambda;</sub></span> =" + String.format("%.3f", bestimmeLamda));  
     	 this.lamda2.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&lambda;</sub></span> =" + String.format("%.3f", bestimmeLamda));  
     	 this.lamda3.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&lambda;</sub></span> =" + String.format("%.3f", bestimmeLamda));
     	 
     	 // Spannungsfaktor GZT
     	 this.spannungsfaktorAußenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>k<sub>&sigma;,1</span> =" + String.format("%.3f", bestimmeSpannungsfaktorAußenGzt));  
     	 this.spannungsfaktorMitteGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>k<sub>&sigma;,2</span> =" + String.format("%.3f", bestimmeSpannungsfaktorMitteGzt));  
     	 this.spannungsfaktorInnenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>k<sub>&sigma;,3</span> =" + String.format("%.3f", bestimmeSpannungsfaktorInnenGzt));  
     	 
     	 // Bemmesungsspannung GZT
     	 this.bemessungsspannungAußenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&sigma;<sub>d,1</span> =" + String.format("%.3f", bestimmeBemessungsspannungAußenGzt));  
     	 this.bemessungsspannungMitteGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&sigma;<sub>d,2</span> =" + String.format("%.3f", bestimmeBemessungsspannungMitteGzt));  
     	 this.bemessungsspannungInnenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&sigma;<sub>d,3</span> =" + String.format("%.3f", bestimmeBemessungsspannungInnenGzt));  

     	 // Maximale Beanspruchung GZT
      	 this.maximaleBeanspruchungAußenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>R<sub>d</span> =" + String.format("%.3f", bestimmeMaximaleBeanspruchungAußenGzt));
      	 this.maximaleBeanspruchungMitteGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>R<sub>d</span> =" + String.format("%.3f", bestimmeMaximaleBeanspruchungMitteGzt));
      	 this.maximaleBeanspruchungInnenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>R<sub>d</span> =" + String.format("%.3f", bestimmeMaximaleBeanspruchungInnenGzt));

     	 // Normierte Flächenlast GZG
     	 this.normierteFlächenlastAußenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>p<sup>*</sup><sub>2</span> ="  + String.format("%.3f", bestimmeNormierteFlächenlastAußenGzt));  
     	 this.normierteFlächenlastMitteGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>p<sup>*</sup><sub>2</span> =" + String.format("%.3f", bestimmeNormierteFlächenlastMitteGzt));  
     	 this.normierteFlächenlastInnenGzt.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>p<sup>*</sup><sub>2</span> =" + String.format("%.3f", bestimmeNormierteFlächenlastInnenGzt));  

     	 // Lastkombination GZG
     	qGzg1Labelaußen.setText(String.format("%.3f", berechneqGzg1Labelaußen));
     	qGzg2Labelaußen.setText(String.format("%.3f", berechneqGzg2Labelaußen));
     	qGzg3Labelaußen.setText(String.format("%.3f", berechneqGzg3Labelaußen));
     	qGzg4Labelaußen.setText(String.format("%.3f", berechneqGzg4Labelaußen));

     	qGzg1Labelmitte.setText(String.format("%.3f", berechneqGzg1Labelmitte));
     	qGzg2Labelmitte.setText(String.format("%.3f", berechneqGzg2Labelmitte));
     	qGzg3Labelmitte.setText(String.format("%.3f", berechneqGzg3Labelmitte));
     	qGzg4Labelmitte.setText(String.format("%.3f", berechneqGzg4Labelmitte));

     	qGzg1Labelinnen.setText(String.format("%.3f", berechneqGzg1Labelinnen));
     	qGzg2Labelinnen.setText(String.format("%.3f", berechneqGzg2Labelinnen));
     	qGzg3Labelinnen.setText(String.format("%.3f", berechneqGzg3Labelinnen));
     	qGzg4Labelinnen.setText(String.format("%.3f", berechneqGzg4Labelinnen));
     	 
    	 // Bemessungslast GZG
     	 this.qGzgLabelaußenMax.setText("<html><span style='font-family:Lucida Console; font-size:12pt'><html>q<sub>GZG,max,außen</sub></span> =" + String.format("%.3f", bestimmeMAXqGZGaußen)+ einheit);  
     	 this.qGzgLabelmitteMax.setText("<html><span style='font-family:Lucida Console; font-size:12pt'><html>q<sub>GZG,max,mitte</sub></span> =" + String.format("%.3f", bestimmeMAXqGZGmitte)+ einheit);  
     	 this.qGzgLabelinnenMax.setText("<html><span style='font-family:Lucida Console; font-size:12pt'><html>q<sub>GZG,max,innen</sub></span> =" + String.format("%.3f", bestimmeMAXqGZGinnen)+ einheit);  
    	     
     	 // Lamda GZG
     	 this.lamda4.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&lambda;</sub></span> =" + String.format("%.3f", bestimmeLamda));  
     	 this.lamda5.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&lambda;</sub></span> =" + String.format("%.3f", bestimmeLamda));  
     	 this.lamda6.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>&lambda;</sub></span> =" + String.format("%.3f", bestimmeLamda));  
     	 
       	 // Normierte Flächenlast GZG
     	 this.normierteFlächenlastAußenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>p<sup>*</sup><sub>1</span> =" + String.format("%.3f", bestimmeNormierteFlächenlastAußenGzg));  
     	 this.normierteFlächenlastMitteGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>p<sup>*</sup><sub>2</span> =" + String.format("%.3f", bestimmeNormierteFlächenlastMitteGzg));  
     	 this.normierteFlächenlastInnenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>p<sup>*</sup><sub>3</span> =" + String.format("%.3f", bestimmeNormierteFlächenlastInnenGzg));  

     	 // Verfotmungsfaktor GZG
     	 this.verformungsfaktorAußenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>k<sub>w,1</span> =" + String.format("%.3f", bestimmeVerformungsfaktorAußenGzg));  
     	 this.verformungsfaktorMitteGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>k<sub>w,2</span> =" + String.format("%.3f", bestimmeVerformungsfaktorMitteGzg));  
     	 this.verformungsfaktorInnenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>k<sub>w,3</span> =" + String.format("%.3f", bestimmeVerformungsfaktorInnenGzg));  

     	 // Bemessungsverformung GZG
     	 this.bemessungsverformungAußenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>w<sub>d,1</span> =" + String.format("%.3f", bestimmeBemessungsverformungAußenGzg));  
     	 this.bemessungsverformungMitteGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>w<sub>d,2</span> =" + String.format("%.3f", bestimmeBemessungsverformungMitteGzg));  
     	 this.bemessungsverformungInnenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>w<sub>d,3</span> ="  + String.format("%.3f", bestimmeBemessungsverformungInnenGzg));  

     	 // Grenzwert der Verformung GZG
     	 this.grenzwertDerVerformungAußenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>C<sub>d</span> =" + String.format("%.3f", bestimmeGrenzwertDerVerformungAußenGzg));  
     	 this.grenzwertDerVerformungMitteGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>C<sub>d</span> =" + String.format("%.3f", bestimmeGrenzwertDerVerformungMitteGzg));  
     	 this.grenzwertDerVerformungInnenGzg.setText("<html><span style='font-family:Lucida Console; font-size:12pt'>C<sub>d</span> =" + String.format("%.3f", bestimmeGrenzwertDerVerformungInnenGzg));  

   
     	 // Außen GZT
     	 String zeichenAußenGZT = etaAußenGZT <= 1.0 ? "&lt;" : "&gt;";
     	 String statusAußenGZT = etaAußenGZT <= 1.0 ? "Nachweis erfüllt" : "Nachweis nicht erfüllt";
     	 String textAußenGZT = String.format("<html><b>&eta;<sub>1</sub> = %.3f&nbsp;%s&nbsp;1,0 – %s</b></html>", etaAußenGZT, zeichenAußenGZT, statusAußenGZT);
     	 NachweisGZTaußen.setText(textAußenGZT);

     	 // Mitte GZT
     	 String zeichenMitteGZT = etaMitteGZT <= 1.0 ? "&lt;" : "&gt;";
     	 String statusMitteGZT = etaMitteGZT <= 1.0 ? "Nachweis erfüllt" : "Nachweis nicht erfüllt";
     	 String textMitteGZT = String.format("<html><b>&eta;<sub>2</sub> = %.3f&nbsp;%s&nbsp;1,0 – %s</b></html>", etaMitteGZT, zeichenMitteGZT, statusMitteGZT);
     	 NachweisGZTmitte.setText(textMitteGZT);

     	 // Innen GZT
     	 String zeichenInnenGZT = etaInnenGZT <= 1.0 ? "&lt;" : "&gt;";
     	 String statusInnenGZT = etaInnenGZT <= 1.0 ? "Nachweis erfüllt" : "Nachweis nicht erfüllt";
     	 String textInnenGZT = String.format("<html><b>&eta;<sub>3</sub> = %.3f&nbsp;%s&nbsp;1,0 – %s</b></html>", etaInnenGZT, zeichenInnenGZT, statusInnenGZT);
     	 NachweisGZTinnen.setText(textInnenGZT);

     	 // Außen GZG
     	 String zeichenAußenGZG = etaAußenGZG <= 1.0 ? "&lt;" : "&gt;";
     	 String statusAußenGZG = etaAußenGZG <= 1.0 ? "Nachweis erfüllt" : "Nachweis nicht erfüllt";
     	 String textAußenGZG = String.format("<html><b>&eta;<sub>1</sub> = %.3f&nbsp;%s&nbsp;1,0 – %s</b></html>", etaAußenGZG, zeichenAußenGZG, statusAußenGZG);
     	 NachweisGZGaußen.setText(textAußenGZG);

     	 // Mitte GZG
     	 String zeichenMitteGZG = etaMitteGZG <= 1.0 ? "&lt;" : "&gt;";
     	 String statusMitteGZG = etaMitteGZG <= 1.0 ? "Nachweis erfüllt" : "Nachweis nicht erfüllt";
     	 String textMitteGZG = String.format("<html><b>&eta;<sub>2</sub> = %.3f&nbsp;%s&nbsp;1,0 – %s</b></html>", etaMitteGZG, zeichenMitteGZG, statusMitteGZG);
     	 NachweisGZGmitte.setText(textMitteGZG);

     	 // Innen GZG
     	 String zeichenInnenGZG = etaInnenGZG <= 1.0 ? "&lt;" : "&gt;";
     	 String statusInnenGZG = etaInnenGZG <= 1.0 ? "Nachweis erfüllt" : "Nachweis nicht erfüllt";
     	 String textInnenGZG = String.format("<html><b>&eta;<sub>3</sub> = %.3f&nbsp;%s&nbsp;1,0 – %s</b></html>", etaInnenGZG, zeichenInnenGZG, statusInnenGZG);
     	 NachweisGZGinnen.setText(textInnenGZG);
     	 
    	 // Grafik erzeugen
    	 LGraphics grafik = new LGraphics(verglasung,bemessung);
    	
    	 this.viewerPanel.removeAllObjects3D();
    	 
    	 grafik.moveTo(0, 0);
    	 grafik.drawVerglasung(this.viewerPanel);
    	 grafik.drawD1(this.viewerPanel);
    	 grafik.drawD2(this.viewerPanel);
    	 grafik.drawD3(this.viewerPanel);
    	 grafik.drawDSzr2(this.viewerPanel);
    	 grafik.drawDSzr1(this.viewerPanel);
    	 grafik.draw1(this.viewerPanel);
    	 grafik.draw2(this.viewerPanel);
    	 grafik.draw3(this.viewerPanel);
    	 grafik.draw4(this.viewerPanel);
    	 grafik.draw5(this.viewerPanel);
    	 
    	 this.viewerPanel.render();
     
     	
    	 }
    	 catch (NumberFormatException ex) {
    	 JOptionPane.showMessageDialog(this,"Bitte gültige Zahlen eingeben.","Eingabefehler",JOptionPane.ERROR_MESSAGE);
    	 } 
    	 catch (Exception ex) {
    	 ex.printStackTrace();
    	 JOptionPane.showMessageDialog(this,"Fehler bei der Berechnung: " + ex.getMessage(),"Berechnungsfehler", 
    	 JOptionPane.ERROR_MESSAGE);
    	 }
    	 this.revalidate();
    	 this.repaint();
    	 }
    
    public double bestimmeEModul() {
        String eingabe = this.E.getText().replace(",", ".");
        double EModul = Double.parseDouble(eingabe);
        return EModul;
    }
    
    public double bestimmefk() {
        String eingabe = this.fk.getText().replace(",", ".");
        double fk = Double.parseDouble(eingabe);
        return fk;
    }  
 }