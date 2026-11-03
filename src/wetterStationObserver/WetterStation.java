package wetterStationObserver;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
// import java.util.Random; wäre auch gut, aber dann für alle object neue random variable: 
//   						private final Random rnd = new Random();

public class WetterStation extends Thread implements WetterList{
	private final List<WetterChecker> checkers;
	private static final List<String> alleNamen = new ArrayList<>();
	private final int secunden;
	private final String name;
	
	public WetterStation(String name, int secunden) {
		super(name);
		alleNamen.add(name);
		this.checkers = new ArrayList<>();
		this.secunden = secunden;
		this.name = name;
	}
	
	public WetterStation(String name) {
		this(name, 1);
	}
	
	@Override
	public List<WetterChecker> getList() {
		return this.checkers;
	}
	
	public static List<String> getAlleNamen() {
		return List.copyOf(alleNamen);
	}
	
	public void messen() {
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
			messen();
			try {
				Thread.sleep(secunden*1000);
				}catch (InterruptedException e) {
					  System.out.println(getName() + " wird beendet.");
					    return;        
				}
		}
		
	}

}
