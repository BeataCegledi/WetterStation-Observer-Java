package wetterStationObserver;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class WetterStation extends Thread implements WetterList{
	private final List<WetterChecker> checkers;
	private final int secunden;
	private volatile boolean aktiv = true;
	
	public WetterStation(String name, int secunden) {
		super(name);
		this.checkers = new CopyOnWriteArrayList<>();
		this.secunden = secunden;
	}
	
	public WetterStation(String name) {
		this(name, 1);
	}
	
	public void pause() {
		aktiv = false;
	}
	
	public void restart() {
		aktiv = true;
	}
	
	public boolean isAktiv() {
		return aktiv;
	}
	

	@Override
	public List<WetterChecker> getList() {
		return this.checkers;
	}
	
	
	private void messen() {
		LocalDateTime zeit = LocalDateTime.now();
		double t = ThreadLocalRandom.current().nextDouble(-15, 35);
		int f = ThreadLocalRandom.current().nextInt(0,101);
		WindRichtung[] alleWR = WindRichtung.values();
		WindRichtung wr = alleWR[ThreadLocalRandom.current().nextInt(0, alleWR.length)];
		int ws = ThreadLocalRandom.current().nextInt(0,11);
		Status[] alleS = Status.values();        
		Status s = alleS[ThreadLocalRandom.current().nextInt(0, alleS.length)];
		WetterDaten daten = new WetterDaten(getName(), zeit,t,f, wr, ws, s);
		bescheid(daten);
	}
	
	@Override
	public void run() {
		while(true) {
			if (aktiv) {messen();}
			try {
				Thread.sleep(secunden*1000);
				}catch (InterruptedException e) {
					  System.out.println(getName() + " wird beendet.");
					    return;        
				}
		}
		
	}

}
