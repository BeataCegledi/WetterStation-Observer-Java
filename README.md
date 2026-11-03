# ☕ WetterStation-Observer-Java

> Simulierte Wetterstationen, die ihre Messwerte per Observer-Pattern an Konsole und Swing-Warnfenster melden — entwickelt im Rahmen der Berufsschulausbildung zur Fachinformatikerin Anwendungsentwicklung.

## 📋 Projektbeschreibung

Mehrere Wetterstationen (z. B. München, Villingen-Schwenningen) laufen jeweils in einem eigenen Thread und erzeugen in festen Intervallen zufällige Wetterdaten.
Jede Station benachrichtigt alle angemeldeten Beobachter (Observer): eine Konsolenausgabe und ein eigenes Warnfenster pro Station.
Das Projekt zeigt, wie das Observer-Pattern Datenquelle und Anzeige sauber voneinander trennt.

## 🔧 Funktionsumfang

### Eingaben
- Keine Benutzereingabe — Stationsname und Messintervall (Sekunden) werden in `StarteWetterbericht` festgelegt
- Messwerte werden mit `ThreadLocalRandom` zufällig erzeugt

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
| Temperatur < 0 °C | GEFAHR! FROST | Cyan |
| Status = `GEWITTER` | STARKES GEWITTER | Orange |
| sonst | Alles in Ordnung | Grün |

### Ausgabe
- **Konsole:** formatierter Wetterbericht mit Datum, Uhrzeit und allen Messwerten
- **Swing-Fenster:** ein Warnfenster pro Station mit farbigem Hintergrund und aktuellem Bericht

## 💡 Verwendete Java-Konzepte

| Konzept | Anwendung im Projekt |
|---|---|
| Observer-Pattern | `WetterStation` (Subject) benachrichtigt alle `WetterChecker` (Observer) |
| Interface mit `default`-Methoden | `WetterList` liefert `hinzufuegen`, `entfernen`, `bescheid` fertig mit |
| `@FunctionalInterface` | `WetterChecker` mit genau einer Methode `refresche` |
| `Thread` / `run()` | Jede Station misst unabhängig in eigenem Thread |
| `InterruptedException` | Sauberes Beenden des Stations-Threads |
| `enum` | `Status` und `WindRichtung` |
| Unveränderliche Klasse | `WetterDaten` mit `final`-Feldern und Gettern |
| `LocalDateTime` + `DateTimeFormatter` | Deutsches Datumsformat im Bericht |
| `ThreadLocalRandom` | Thread-sichere Zufallswerte |
| Swing (`JFrame`, `JLabel`) | Warnfenster mit HTML-Text und Hintergrundfarbe |
| `SwingUtilities.invokeLater` | GUI-Update nur im Event-Dispatch-Thread (EDT) |
| `List.copyOf` | Unveränderliche Kopie der Stationsnamen |

## ▶️ Ausführen

```bash
javac -d bin src/wetterStationObserver/*.java
java -cp bin wetterStationObserver.StarteWetterbericht
```

> **Voraussetzungen:** Java 11+ — Keine externen Bibliotheken nötig

## 👩‍💻 Über die Entwicklerin

Dieses Projekt entstand als Übungsaufgabe in der Berufsschule.
Es zeigt meine Fähigkeit, ein Entwurfsmuster (Observer) mit Interfaces umzusetzen, nebenläufige Threads zu steuern und die Swing-Oberfläche thread-sicher zu aktualisieren.
