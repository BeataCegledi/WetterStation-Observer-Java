package wetterStationObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	private final String dateiname;

	public FileIO(String name) {
		new File(".\\src\\wetterStationObserver\\Backup").mkdirs();         
		this.dateiname = ".\\src\\wetterStationObserver\\Backup\\"+ name +".txt";
	}
		
		
	public void speichere(WetterDaten daten) {
		try (PrintWriter pr = new PrintWriter(new FileWriter(dateiname, true))) {
			pr.println(daten.saveString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
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
}
