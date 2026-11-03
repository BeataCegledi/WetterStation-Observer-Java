package wetterStationObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class WarnungChecker extends JFrame implements WetterChecker {

    private static final long serialVersionUID = 1L;
    private final JLabel lblWarnung = new JLabel("Warte auf Daten …", SwingConstants.CENTER);

    public WarnungChecker(String name) {
        super("Wetterwarnung "+name);  //Verschiedene Titel für die WarnungFenster
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        setLocationByPlatform(true);
        lblWarnung.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblWarnung.setOpaque(true);                 // sonst ist die Hintergrundfarbe unsichtbar
        add(lblWarnung, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void refresche(WetterDaten daten) {
        // 1) Auswerten: läuft noch im Thread der Station (schnell, kein GUI-Zugriff)
        String titel;
        Color farbe;
        if (daten.getTemperatur() > 30) {
            titel = "GEFAHR! HITZEWELLE";
            farbe = Color.RED;
        } else if (daten.getTemperatur() < 0) {
            titel = "GEFAHR! FROST";
            farbe = Color.CYAN;
        } else if (daten.getStatus() == Status.GEWITTER) {
            titel = "STARKES GEWITTER";
            farbe = Color.ORANGE;
        } else {
            titel = "Alles in Ordnung";
            farbe = Color.GREEN;
        }

        // JLabel kann mit HTML mehrzeilig anzeigen: \n wird zu <br>
        String text = "<html><center><b>" + titel + "</b><br><br>"
                    + daten.toString().replace("\n", "<br>")
                    + "</center></html>";

        // 2) Anzeigen: an den Swing-Thread (EDT) übergeben, die Station läuft sofort weiter
        SwingUtilities.invokeLater(() -> {
            lblWarnung.setText(text);
            lblWarnung.setBackground(farbe);
        });
    }
}
