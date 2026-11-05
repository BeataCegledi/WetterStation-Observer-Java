package wetterStationObserver;

public class StarteWetterbericht {
	
	public static void main(String[] args) {
	
		WetterStation munich = new WetterStation("München", 2);
		WetterStation vs = new WetterStation("Villingen-Schwenningen", 3);
		
		WetterChecker konsole = new KonsoleAnzeige();
		munich.hinzufuegen(konsole);
		vs.hinzufuegen(konsole);
		
		WarnungChecker.erstelle(munich);
		WarnungChecker.erstelle(vs); 
		
		munich.start();
		vs.start();

		
	}
}
