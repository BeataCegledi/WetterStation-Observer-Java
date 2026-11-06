package wetterStationObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class WarnungChecker extends JFrame implements WetterChecker {

    private static final long serialVersionUID = 1L;
    private final JLabel labelWarnung = new JLabel("Warte auf Daten …", SwingConstants.CENTER);
    private final WetterStation station;
    
    private static final Map<WetterStation, WarnungChecker> stationListe = new ConcurrentHashMap<>();
    
    private WarnungChecker(WetterStation station) {
        super("Wetterwarnung "+station.getName());
        this.station = station;
        baueFenster();
    }
    
    public static WarnungChecker erstellenUndHinzufuegen(WetterStation ws) {
    	if (!stationListe.containsKey(ws)) {
    		WarnungChecker neu = new WarnungChecker(ws);
    		stationListe.put(ws, neu);
    		ws.hinzufuegen(neu);
    	}
		return stationListe.get(ws);
    }
    
   
    private void baueFenster() {
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(600, 300);
	    setLocationByPlatform(true);
	    labelWarnung.setFont(new Font("Tahoma", Font.BOLD, 22));
	    labelWarnung.setOpaque(true);      
	    JButton button = new JButton("Pause");
	    button.setFont(new Font("Tahoma", Font.BOLD, 25));
	    button.setBorderPainted(true);
	    button.addActionListener(new ActionListener() {
	        @Override
	    	public void actionPerformed(ActionEvent e) {
	        	if (station.isAktiv()) {
	        		station.pause();
	        		button.setText("Restart");
	        	} else {
	        		station.restart();
	        		button.setText("Pause");
	        	}
	        }
	    });
	    add(labelWarnung, BorderLayout.CENTER);
	    add(button, BorderLayout.SOUTH); 
	    addWindowListener(new WindowAdapter(){
	    	@Override
	    	public void windowClosed(WindowEvent e) {
	    		station.entfernen(WarnungChecker.this);
	    		station.interrupt();
	    		stationListe.remove(station);
	    	}
	    });
	    setVisible(true);
    }
    
    
    @Override
    public void refresche(WetterDaten daten) {
        String titel;
        Color farbe;
        if (daten.getTemperatur() > 30) {
            titel = "GEFAHR! HITZEWELLE";
            farbe = Color.RED;
        } else if (daten.getTemperatur() < 0) {
            titel = "GLATTEISGEFAHR";
            farbe = Color.CYAN;
        } else if (daten.getStatus() == Status.GEWITTER) {
            titel = "STARKES GEWITTER";
            farbe = Color.ORANGE;
        } else {
            titel = "Alles in Ordnung";
            farbe = Color.GREEN;
        }

        String text = "<html><center><b>" + titel + "</b><br><br>"
                    + daten.toString().replace("\n", "<br>")
                    + "</center></html>";

        SwingUtilities.invokeLater(() -> {
            labelWarnung.setText(text);
            labelWarnung.setBackground(farbe);
        });
    }
}
