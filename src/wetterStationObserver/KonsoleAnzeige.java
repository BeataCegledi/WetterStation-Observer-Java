package wetterStationObserver;


public class KonsoleAnzeige implements WetterChecker {

	@Override
	public void refresche(WetterDaten daten) {
		System.out.println("WETTERBERICHT\n" + daten + "\n" + "*".repeat(60) + "\n");
	}

}
