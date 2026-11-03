package wetterStationObserver;

import java.util.List;

public interface WetterList {

	
	List<WetterChecker> getList();
	
	default void hinzufuegen(WetterChecker subscriber)
	{
		getList().add(subscriber);
	}
	
	default void entfernen(WetterChecker subscriber)
	{
		getList().remove(subscriber);
	}
	
	default void bescheid(WetterDaten daten)
	{
		for (WetterChecker subscriber : getList()) {
			subscriber.refresche(daten);
		}
	}
	
}
