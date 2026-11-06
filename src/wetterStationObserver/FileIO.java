package wetterStationObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileIO implements WetterChecker{
	private final String dateiname;
	private static final Map<WetterStation, FileIO> stationListe = new ConcurrentHashMap<>();
	    
	  
    public static FileIO erstellenUndHinzufuegen(WetterStation ws) {
    	if (!stationListe.containsKey(ws)) {
    		FileIO neu = new FileIO(ws);
    		stationListe.put(ws, neu);
    		ws.hinzufuegen(neu);
    	}
		return stationListe.get(ws);
    }
	    

	private FileIO(WetterStation ws) {
		new File(".\\src\\wetterStationObserver\\Backup").mkdirs();         
		this.dateiname = ".\\src\\wetterStationObserver\\Backup\\"+ ws.getName() +".txt";
	}
		
	
	
	public List<String> lese() {
		List <String> zeilen = new ArrayList<>();
		String zeile = "";
		try (BufferedReader br = new BufferedReader(new FileReader(dateiname))) {
			while ((zeile = br.readLine()) != null) {
				zeilen.add(zeile);
			}
		} catch (IOException e) {
			return List.of(e.getMessage());
		}
		return zeilen;
	}


	@Override
	public void refresche(WetterDaten daten) {
		try (PrintWriter pr = new PrintWriter(new FileWriter(dateiname, true))) {
			pr.println(daten.saveString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
