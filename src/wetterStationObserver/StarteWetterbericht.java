package wetterStationObserver;

public class StarteWetterbericht {
	
	public static void main(String[] args) {
		WetterStation munich = new WetterStation("München", 2);
		WetterStation vs = new WetterStation("Villingen-Schwenningen", 3);
		WetterChecker konsole = new KonsoleAnzeige();
	
		
		munich.hinzufuegen(konsole);
		vs.hinzufuegen(konsole);
		munich.hinzufuegen(new WarnungChecker("München"));
		vs.hinzufuegen(new WarnungChecker("Villingen-Schwenningen"));
		
		munich.start();
		vs.start();
		
	}

}
