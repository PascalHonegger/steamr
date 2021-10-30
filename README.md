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
Zuerst wird ein Load-Indicator angezeigt. Falls keine Daten geladen werden konnent, wird ein Fehler angezeigt.

## Einstellungen
Benutzer haben die Möglichkeit einstellungen von Steamr zu verwalten.
Die einzige Einstellung hierbei ist das Farbschema der App, welches explizit auf Light oder Dark gestellt werden kann.

## Punkte
Wir versuchen zu zweit folgende Punkte für das Testat zu erreichen:

| Feature                 | Punkte |
|-------------------------|-------:|
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
