package wetterStationObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WetterDaten {
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy. MMMM dd. E 'um' HH:mm:ss", Locale.GERMAN);

    private final String name;
    private final LocalDateTime zeit;
    private final double temperatur;
    private final int luftFeuchtigkeit;
    private final WindRichtung windRichtung;
    private final int windStaerke;
    private final Status status;

    public WetterDaten(String name,
                       LocalDateTime zeit,
                       double temperatur,
                       int luftFeuchtigkeit,
                       WindRichtung windRichtung,
                       int windStaerke,
                       Status status) {
        this.name = name;
        this.zeit = zeit;
        this.temperatur = temperatur;
        this.luftFeuchtigkeit = luftFeuchtigkeit;
        this.windRichtung = windRichtung;
        this.windStaerke = windStaerke;
        this.status = status;
    }

    public String getName() { return this.name; }
    public LocalDateTime getZeit() { return this.zeit; }
    public double getTemperatur() { return this.temperatur; }
    public int getLuftFeuchtigkeit() { return this.luftFeuchtigkeit; }
    public WindRichtung getWindRichtung() { return this.windRichtung; }
    public int getWindStaerke() { return this.windStaerke; }
    public Status getStatus() { return this.status; }

    @Override
    public String toString() {
        return "Wetter in " + name + ":\n"
             + zeit.format(FORMAT) + "\n"
             + String.format("%.1f °C, %d %%, %s %d Bft, %s",
                             temperatur, luftFeuchtigkeit, windRichtung, windStaerke, status);
    }
    
    public String saveString() {
	    return zeit.format(FORMAT) + ", " + String.format("%.1f °C, %d %%, %s %d Bft, %s",
	                             temperatur, luftFeuchtigkeit, windRichtung, windStaerke, status);
	}
}