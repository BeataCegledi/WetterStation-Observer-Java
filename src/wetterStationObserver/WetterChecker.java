package wetterStationObserver;

@FunctionalInterface
public interface WetterChecker {
	
	public void refresche(WetterDaten daten);

}
