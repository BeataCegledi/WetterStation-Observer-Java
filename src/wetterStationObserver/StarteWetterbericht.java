package wetterStationObserver;

public class StarteWetterbericht {
	
	public static void main(String[] args) {
	
		WetterStation munich = new WetterStation("München", 2);
		WetterStation vs = new WetterStation("Villingen-Schwenningen", 3);
		
		WetterChecker konsole = new KonsoleAnzeige();
		munich.hinzufuegen(konsole);
		vs.hinzufuegen(konsole);
		
		WarnungChecker.erstellenUndHinzufuegen(munich);
		WarnungChecker.erstellenUndHinzufuegen(vs); 
		
		FileIO.erstellenUndHinzufuegen(munich);
		FileIO.erstellenUndHinzufuegen(vs);
		
		munich.start();
		vs.start();
		try {
		    munich.join();          
		    vs.join();              
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		TesteBackup.teste(munich);   
		TesteBackup.teste(vs);

	}
}
