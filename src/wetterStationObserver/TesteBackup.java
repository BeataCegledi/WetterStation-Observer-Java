package wetterStationObserver;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TesteBackup {
	
	private TesteBackup(){}
	
	public static void teste(WetterStation station) {
			String text = String.join("\n", FileIO.erstellenUndHinzufuegen(station).lese());
			JTextArea area = new JTextArea(text, 15, 50);                     
			area.setEditable(false);
			JOptionPane.showMessageDialog(null, new JScrollPane(area), "File schreiben und lesen testen! Daten aus " + station.getName() + ": ", JOptionPane.INFORMATION_MESSAGE);
	    }
	    

}
