import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;




public class Blutzucker extends JFrame implements ActionListener {
    Properties prop = new Properties();
    String url = "jdbc:sqlite:identifier.sqlite";
    Connection con = DriverManager.getConnection(url);
    Statement statement = con.createStatement();
    NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    JFormattedTextField ftf1, ftf2, ftf3;
    JButton b1, b2;
    JMenuBar mb1;
    JMenu m1, m2;
    JMenuItem mi1, mi2, mi3;
    JTextField tf1, tf2, tf3, tf4, tf5;
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JFrame tabelle;
    JPanel panel1;
    String[] Einheit = {"mmol/l", "mg/dl"};
    String[] Optionen = {"Ja", "Nein"};
    int defaultUnit = -1;
    String savedValue = null;

    public Blutzucker() throws SQLException {

        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel1 = new JPanel();
        panel1.setLayout(gbl);
        this.setTitle("Dokumentation der Blutzuckerwerte");
        this.setPreferredSize(new Dimension(500, 300));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mb1 = new JMenuBar();
        l1 = new JLabel("Name: ");
        l2 = new JLabel("Vorname: ");
        l3 = new JLabel("Geburtsdatum: ");
        l4 = new JLabel("Blutzuckerwerte: ");
        l5 = new JLabel("Kohlenhydrate: ");
        l6 = new JLabel("Medikation: ");
        l7 = new JLabel("Zusaetzliche angaben: ");
        tf1 = new JTextField(10);
        tf2 = new JTextField(10);
        tf3 = new JTextField(10);
        tf3.setColumns(10);
        ftf1 = new JFormattedTextField(dateFormat);
        ftf1.setColumns(10);
        ftf2 = new JFormattedTextField(format);
        ftf2.setColumns(10);
        ftf3 = new JFormattedTextField(format);
        ftf3.setColumns(10);
        tf4 = new JTextField(10);
        tf5 = new JTextField(4);
        tf5.setEditable(false);
        tf5.setBorder(javax.swing.BorderFactory.createEmptyBorder());


        b1 = new JButton("Eingaben Bestaetigen");
        b2 = new JButton("Auswerten");
        b1.setForeground(Color.BLUE);
        b1.setContentAreaFilled(false);
        b2.setForeground(Color.BLUE);
        b2.setContentAreaFilled(false);
        m1 = new JMenu("Datei");
        m2 = new JMenu("Einheit festlegen");
        mi1 = new JMenuItem("Beenden");
        mi1.addActionListener(this);
        mi2 = new JRadioButtonMenuItem("mmol/l");
        mi3 = new JRadioButtonMenuItem("mg/dl");
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        m1.add(mi1);
        m2.add(mi2);
        m2.add(mi3);
        mb1.add(m1);
        mb1.add(m2);
        try (InputStream input = new FileInputStream("Einheiten")) {
            prop.load(input);
            savedValue = prop.getProperty("default-Einheit");
            tf5.setText(savedValue);
            if(savedValue.equals("mmol/l")) {
                mi2.setSelected(true);
            }else if(savedValue.equals("mg/dl")) {
                mi3.setSelected(true);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if(savedValue == null) {
            tf5.setText(Einheit[0]);
        }
        this.setJMenuBar(mb1);


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l4, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l5, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l6, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(l7, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(tf1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(tf2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(ftf1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(ftf2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(ftf3, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(tf3, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(tf4, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(tf5, gbc);
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(b2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel1.add(b1, gbc);


        this.add(panel1); //drittes Menu
        b1.addActionListener(this);
        b2.addActionListener(this);
    }

    public static void main(String[] args) throws SQLException {

        Blutzucker bl = new Blutzucker();
        bl.statement.execute("""
                create table if not exists Dokumentation(
                    Name Varchar(30),
                    Vorname Varchar(30),
                    Geburtstag Varchar(30),
                    Blutzuckerwert Varchar(30),
                    Kohlenhydrate  Varchar(30),
                    Medikation   Varchar(30),
                    ZusätzlicheAngaben Varchar(50),
                    Datum Varchar(30),
                    Uhrzeit Varchar(30)
                );""");
        SwingUtilities.invokeLater(() -> bl.setVisible(true)); //on startup menu

    }

    public int Auswertung(double wert) {
        if (wert < 70) { //Unterzuckerung
            return 0;
        } else if (wert < 125 && wert > 90) { //Normalwert
            return 1;
        } else if (wert > 160) { //Überzuckerung
            return 2;
        } else {
            return -1;
        }
    }

    public static void toPdf(JTable table) {
        try{
            String file = "Tabelle.pdf";
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            Table pdfTable = new Table(table.getColumnCount());
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addHeaderCell(table.getColumnName(i));
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    pdfTable.addCell(new Cell().add(new com.itextpdf.layout.element.Paragraph(table.getValueAt(i, j).toString())));
                }
            }
            document.add(pdfTable);
            document.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                DatenEntry entry = new DatenEntry(tf1.getText(), tf2.getText(), ftf1.getText(), Double.parseDouble(ftf3.getText()),
                        ftf2.getText() + " " + tf5.getText(), tf3.getText(), tf4.getText(), "" + java.time.LocalDate.now(),
                        "" + java.time.LocalTime.now());

                String sql = "INSERT INTO Dokumentation (Name, Vorname, Geburtstag, Blutzuckerwert, Kohlenhydrate, Medikation, ZusätzlicheAngaben, Datum, Uhrzeit) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                String update = "select * from Dokumentation";
                PreparedStatement pstmt = con.prepareStatement(sql);
                {
                    pstmt.setString(1, entry.getName());  // Name
                    pstmt.setString(2, entry.getVorname());  // Vorname
                    pstmt.setString(3, entry.getGeburtsdatum());  // Geburtstag
                    pstmt.setString(4, entry.getBlutzuckerwerte());  // Blutzuckerwert
                    pstmt.setDouble(5, entry.getKohlenhydrate());  // Kohlenhydrate
                    pstmt.setString(6, entry.getMedikation());  // Medikation
                    pstmt.setString(7, entry.getZa());  // Zusätzliche Angaben
                    pstmt.setString(8, entry.getDatum());  // Datum
                    pstmt.setString(9, entry.getUhrzeit());  // Uhrzeit
                    pstmt.executeUpdate();
                }
                PreparedStatement pstmt2 = con.prepareStatement(update);
                pstmt2.executeQuery(); //select wird genutzt um die Datenbank zu updaten


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Leeres Feld, Bitte alle felder eintragen");
                System.out.println("Leeres Feld, Bitte alle felder eintragen");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            tf1.setText("");
            tf2.setText("");
            ftf1.setText("");
            ftf2.setText("");
            ftf3.setText("");
            tf3.setText("");
            tf4.setText("");
        }
        if (ae.getSource() == b2) {  //auswerten
            String werte = "select Blutzuckerwert, Datum, Uhrzeit  from Dokumentation where date(Datum) > date('now', '-31 days');";
            try (PreparedStatement pstmt2 = con.prepareStatement(werte)) {
                if(tabelle != null) {
                    tabelle.dispose();
                }
                ResultSet rs = pstmt2.executeQuery();
                tabelle = new JFrame();
                tabelle.setTitle("Werte des letzten Monats");
                tabelle.setSize(500, 400);
                tabelle.setLocationRelativeTo(null);
                DefaultTableModel model = new DefaultTableModel();
                JTable table = new JTable(model);
                model.addColumn("Blutzuckerwert in mg/dl");
                model.addColumn("Datum");
                model.addColumn("Uhrzeit");
                String cvs;
                String zww;
                String einheit;


                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    double cValue;
                    cvs = rs.getString("Blutzuckerwert");
                    zww = cvs.split(" ")[0];
                    einheit = cvs.split(" ")[1];
                    if (einheit.equals("mmol/l")) {
                        cValue = Double.parseDouble(zww) * 18; //convert auf 'mg/dl'
                    } else {
                        cValue = Double.parseDouble(zww);
                    }
                    row.add(cValue); //Blutzuckerwert
                    row.add(rs.getString("Datum"));
                    row.add(rs.getString("Uhrzeit"));
                    model.addRow(row);
                }
                table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        double cValue = (Double)table.getValueAt(row, 0);
                        int eval = Auswertung(cValue);
                        if(eval == 0) {
                            c.setBackground(Color.red);   //Unterzucker
                        } else if (eval == 1) {
                            c.setBackground(Color.green);  //Normal
                        }else if (eval == 2) {
                            c.setBackground(Color.yellow); //Ueberzucker
                        }else if (eval == -1) {
                            c.setBackground(Color.blue); //Fehler oder Loch in Auswertung
                        }
                        return c;
                    }
                });
                JScrollPane pane = new JScrollPane(table);
                tabelle.add(pane);
                tabelle.setVisible(true);
                toPdf(table);
            } catch (RuntimeException | SQLException e) {
                throw new RuntimeException(e);
            }

        } //auswerten ende

        if (ae.getSource() == mi1) {  //submenu beenden
                System.exit(0);

        } if (ae.getSource() == mi2) {  //submenu einheitenauswahl
            mi3.setSelected(false);
                if(!ftf2.getText().isEmpty() && tf5.getText().equals("mg/dl") ) {
                    ftf2.setText(String.valueOf(Double.parseDouble(ftf2.getText())*18));
                }
                defaultUnit = JOptionPane.showOptionDialog(this, "Wollen Sie die Einheit als default festlegen?", "Default",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Optionen, Optionen[1]); //0 = ja, 1 = nein
                if (defaultUnit == 0) {
                    try (OutputStream defaultU = new FileOutputStream("Einheiten")) {
                        prop.setProperty("default-Einheit", Einheit[0]);
                        prop.store(defaultU, "Einheit");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            this.tf5.setText(Einheit[0]);
            this.tf5.setEditable(false);

        }if (ae.getSource() == mi3) { //mg/dl
            mi2.setSelected(false);
            if(!ftf2.getText().isEmpty() && tf5.getText().equals("mmol/l") ) {
                ftf2.setText(String.valueOf(Double.parseDouble(ftf2.getText())/18));
            }

                defaultUnit = JOptionPane.showOptionDialog(this, "Wollen Sie die Einheit als default festlegen?", "Default",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Optionen, Optionen[1]);  //0 = ja, 1 = nein
                if (defaultUnit == 0) {
                    try (OutputStream defaultU = new FileOutputStream("Einheiten")) {
                        prop.setProperty("default-Einheit", Einheit[1]);
                        prop.store(defaultU, "Einheit");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            this.tf5.setText(Einheit[1]);
            this.tf5.setEditable(false);
        }
    }

}