# Steamr

## Idee
Steamr ist eine App, die einem erlaubt diverse öffentlich einsehbare Daten von einem [Steam Community](https://steamcommunity.com/) Profil anzuzeigen.
Dazu kann auf der Startseite eine Profil-ID eingegeben werden, woraufhin damit verbundene Daten geladen werden.
Die angezeigten Profildaten sind öffentlich einsehbar, weshalb wir keinen API Schlüssel oder änhliches benötigen.
Da das Eingeben einer ID mühsam ist, werden die früher eingegebenen IDs lokal gespeichert und zur Auswahl angezeigt.

## Profil-ID eingabe
Eingabe der Steam ID als Text. Der "View Profile" Button ist deaktiviert, solange keine Eingabe erfolgt ist.
Unterhalb der Eingabe werden vorherige Eingaben dargestellt, welche bei einem Klick ins Input übernommen werden.
Vergangene Inputs können über den Mülleimer gelöscht werden.

## Profil-Ansicht
Zuerst wird ein Load-Indicator angezeigt.
Falls keine Daten geladen werden konnent, wird ein Fehler angezeigt.
In der Profil-Ansicht werden einige Daten des Benutzer dargestellt, falls diese vorhanden sind.
Die Beschreibung und der Spiele-Titel können HTML enthalten und werden dementsprechend verarbeitet.

## Einstellungen
Benutzer haben die Möglichkeit einstellungen von Steamr zu verwalten.
Die einzige Einstellung ist das Farbschema der App, welches explizit auf Light oder Dark gestellt werden kann.

## Screenshots
| Light Theme                                  | Dark Theme                                 |
|----------------------------------------------|--------------------------------------------|
| ![Start Light](/media/start_light.png)       | ![Start Dark](/media/start_dark.png)       |
| ![Settings Light](/media/settings_light.png) | ![Settings Dark](/media/settings_dark.png) |
| ![Profile Light](/media/profile_light.png)   | ![Profile Dark](/media/profile_dark.png)   |

## Weiteres

### Intent Filter
Zusätzlich kann ein Benutzer im Browser ein Steam-Profil öffnen und die URL mit der `Steamr` App öffnen.

### Mehrsprachigkeit
Wir unterstützen Englisch und Deutsch (CH).

### Light- und Darktheme
Wir haben ein Light- und Darktheme, wobei das Farbschema an Steam angelehnt ist.

### Kotlin
Technisch verwenden wir ViewBindings und ausschliesslich Kotlin.
Ebenfalls haben wir unsere Gradle files von Groovy nach Kotlin Script migriert und verwenden die neusten Versionen aller dependencies.

### Tests
Wir haben versucht sinnvolle Unit- und Android-Tests zu schreiben.
Da unsere grösste Logik in der Startseite und im Parsen von XML liegt sind diese zwei Komponenten getestet. 
Wir haben auch eine kleine CI-Pipeline in GitHub konfiguriert, welche unsere Unit-Tests automatisch ausführt.

## Punkte
Wir versuchen zu zweit folgende Punkte für das Testat zu erreichen:

| Feature                 | Punkte |
|-------------------------|--------|
| Kotlin                  | 6P     |
| Profil-ID Eingabe       | 2P     |
| Profil-Ansicht          | 2P     |
| Einstellungen           | 1P     |
| Verwendung Preferences  | 1P     |
| Englisch & Deutsch      | 1P     |
| Light- und Dark-Theme   | 1P     |
| Anbindung Steam API     | 2P     |
| Sinnvolle Unit-Tests    | 1P     |
| Total                   | 17P    |
