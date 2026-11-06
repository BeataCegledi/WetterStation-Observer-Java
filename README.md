# WetterStation-Observer-Java

> Simulierte Wetterstationen, die ihre Messwerte per Observer-Pattern an Konsole und Swing-Warnfenster melden — entwickelt im Rahmen der Berufsschulausbildung zur Fachinformatikerin Anwendungsentwicklung.

## Projektbeschreibung

Mehrere Wetterstationen (z. B. München, Villingen-Schwenningen) laufen jeweils in einem eigenen Thread und erzeugen in festen Intervallen zufällige Wetterdaten.
Jede Station benachrichtigt alle angemeldeten Beobachter (Observer): eine Konsolenausgabe und ein eigenes Warnfenster pro Station.
Die Messung lässt sich pro Station anhalten und fortsetzen; schließt man ein Fenster, wird die zugehörige Station beendet. Alle Messwerte werden zusätzlich in einer Textdatei gesichert.
Das Projekt zeigt, wie das Observer-Pattern Datenquelle und Anzeige sauber voneinander trennt.

## Funktionsumfang

### Eingaben
- Stationsname und Messintervall (Sekunden) werden in `StarteWetterbericht` festgelegt
- Messwerte werden mit `ThreadLocalRandom` zufällig erzeugt
- Button **Pause / Restart** im Warnfenster hält die Messung der Station an bzw. setzt sie fort

### Messwerte
| Wert | Bereich |
|---|---|
| Temperatur | -15 bis 35 °C |
| Luftfeuchtigkeit | 0 bis 100 % |
| Windrichtung | `NORD`, `SUED`, `OST`, `WEST`, `SUED_WEST`, `SUED_OST`, `NORD_WEST`, `NORD_OST` |
| Windstärke | 0 bis 10 Bft |
| Status | `BEWOELKT`, `SONNIG`, `REGEN`, `NEBEL`, `GEMISCHT`, `GEWITTER`, `SCHNEE` |

### Regeln / Warnlogik (`WarnungChecker`)
| Bedingung | Warnung | Farbe |
|---|---|---|
| Temperatur > 30 °C | GEFAHR! HITZEWELLE | Rot |
| Temperatur < 0 °C | GLATTEISGEFAHR | Cyan |
| Status = `GEWITTER` | STARKES GEWITTER | Orange |
| sonst | Alles in Ordnung | Grün |

### Steuerung (Start / Stop)
| Aktion | Ergebnis |
|---|---|
| Klick auf **Pause** | Station misst nicht mehr, Thread läuft weiter; Button zeigt **Restart** |
| Klick auf **Restart** | Station misst wieder; Button zeigt **Pause** |
| Fenster schließen | Fenster meldet sich ab, Stations-Thread wird beendet |
| alle Fenster geschlossen | alle Stationen beendet → Konsolenausgabe endet, danach zeigt `TesteBackup` die gespeicherten Messwerte jeder Station an |

### Ausgabe
- **Konsole:** formatierter Wetterbericht mit Datum, Uhrzeit und allen Messwerten
- **Swing-Fenster:** genau ein Warnfenster pro Station mit farbigem Hintergrund und aktuellem Bericht
- **Datei:** `FileIO` ist selbst ein Observer und hängt jede Messung an `src/wetterStationObserver/Backup/<Station>.txt` an

## Verwendete Java-Konzepte

| Konzept | Anwendung im Projekt |
|---|---|
| Observer-Pattern | `WetterStation` (Subject) benachrichtigt alle `WetterChecker` (Observer) |
| Interface mit `default`-Methoden | `WetterList` liefert `hinzufuegen`, `entfernen`, `bescheid` fertig mit |
| `@FunctionalInterface` | `WetterChecker` mit genau einer Methode `refresche` |
| Fabrikmethode + `Map` (Multiton) | `WarnungChecker` und `FileIO` mit `erstellenUndHinzufuegen(station)` und `private` Konstruktor: genau ein Fenster bzw. eine Datei pro Station, genau einmal angemeldet |
| `ConcurrentHashMap` | Thread-sichere Multiton-Map (`put` im `main`-Thread, `remove` im EDT) |
| SOLID (S, O, D) | `WetterStation` kennt nur das Interface `WetterChecker` – Speichern, Anzeige und Warnung sind eigene Observer |
| Hilfsklasse mit `private` Konstruktor | `TesteBackup` mit statischer Methode `teste(station)` |
| `Thread` / `run()` | Jede Station misst unabhängig in eigenem Thread |
| `join()` | `main` wartet, bis alle Stations-Threads beendet sind, und liest erst dann die Backup-Dateien |
| `volatile boolean` | Pause/Restart-Flag, das der Swing-Thread setzt und der Stations-Thread sofort sieht |
| `interrupt()` + `InterruptedException` | Sauberes Beenden des Stations-Threads beim Schließen des Fensters |
| `CopyOnWriteArrayList` | Thread-sichere Observer-Liste (Abmelden im EDT während `bescheid` im Stations-Thread läuft) |
| `WindowAdapter` / `windowClosed` | Abmelden und Beenden beim Schließen (`DISPOSE_ON_CLOSE`) |
| `ActionListener` | Pause/Restart-Button mit wechselnder Beschriftung |
| Kapselung | Zustand `aktiv` ist `private`, Zugriff nur über `pause()`, `restart()`, `isAktiv()` |
| `enum` | `Status` und `WindRichtung` |
| Unveränderliche Klasse | `WetterDaten` mit `final`-Feldern und Gettern |
| `LocalDateTime` + `DateTimeFormatter` | Deutsches Datumsformat im Bericht |
| `ThreadLocalRandom` | Thread-sichere Zufallswerte |
| Dateien (`FileWriter`, `BufferedReader`) | Messwerte anhängen und wieder einlesen, mit try-with-resources |
| Swing (`JFrame`, `JLabel`, `JButton`, `JTextArea`, `JScrollPane`) | Warnfenster mit HTML-Text, Button und scrollbarer Datenanzeige |
| `SwingUtilities.invokeLater` | GUI-Update nur im Event-Dispatch-Thread (EDT) |

## Ausführen

```bash
javac -d bin src/wetterStationObserver/*.java
java -cp bin wetterStationObserver.StarteWetterbericht
```

> **Voraussetzungen:** Java 11+ — Keine externen Bibliotheken nötig. Aus dem Projektordner starten, da die Backup-Dateien relativ unter `src/wetterStationObserver/Backup/` angelegt werden.

## Über die Entwicklerin

Dieses Projekt entstand als Übungsaufgabe in der Berufsschule.
Es zeigt meine Fähigkeit, Entwurfsmuster (Observer, Fabrikmethode, Multiton) mit Interfaces nach SOLID-Prinzipien umzusetzen, nebenläufige Threads thread-sicher zu steuern und anzuhalten sowie eine Swing-Oberfläche ereignisgesteuert zu bedienen.
