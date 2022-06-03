:information_source: **Praktikumsprojekt zur Veranstaltung "Softwareentwicklung im Team". Entwickelt gemeinsam mit [@deyirao](https://github.com/deyirao) [@AleGra1](https://github.com/AleGra1) [@thrNoah](https://github.com/thrNoah)** :information_source:

# Chicken - Anwendung zur Urlaubs- und Klausurverwaltung

:warning: **Zum Starten der Anwendung muss Docker und Docker-Compose installiert sein** :warning:

## Anleitung zur Konfiguration (Entwicklung)

:warning: **Damit die Anwendung über `gradle bootRun` gestartet werden kann, muss die aktuellste Gradle Version installiert sein. Alternativ kann die Anwendung mit `./gradlew bootRun` gestartet werden.** :warning:

Es müssen die Umgebungsvariablen `CLIENT_ID` und `CLIENT_SECRET` gesetzt sein. 

Die Entwicklungsdatendank muss im Ordner `./chicken` mithilfe des Befehls `docker-compose -f docker-compose-dev.yml` gestartet werden. 
Anschließend lässt sich die Anwendung im Order `./chicken` mit dem Befehl `./gradlew bootRun` starten.

Die Konfiguration der Anwendung kann in der `application.yaml` angepasst werden.

## Anleitung zur Konfiguration (Deployment)

Für die Konfiguration muss im `./chicken` Verzeichnis eine `.env` Datei mit den folgenden Variablen
erstellt werden: (oder die Umgebungsvariablen von Hand gesetzt werden)

```
mysqlpassword=**** (Kann beliebig gesetzt werden / Mariadb Server ist unter Port 3306 erreichbar)
CLIENT_ID=**** (Github Client ID)
CLIENT_SECRET=**** (Github Client Secret)
STARTDATUM=2022-03-07 (Format yyyy-mm-tt)
ENDDATUM=2022-03-26 (Format yyyy-mm-tt)
STARTUHRZEIT=09:30 (Format hh:mm)
ENDUHRZEIT=13:30 (Format hh:mm)
ORGANISATOREN= organisator1, organisator2, ...
TUTOREN= tutor1, tutor2, ...
```

Alternativ können in der `docker-compose.yml` die jeweiligen Platzhalter durch die entsprechenden
Werte ersetzt werden:

```yaml
...
  ...
    environment:
      - "MYSQLPASSWORD=${MYSQLPASSWORD}"
      - "CLIENT_ID=${CLIENT_ID}"
      - "CLIENT_SECRET=${CLIENT_SECRET}"
      - "STARTDATUM=${STARTDATUM}"
      - "STARTUHRZEIT=${STARTUHRZEIT}"
      - "ENDDATUM=${ENDDATUM}"
      - "ENDUHRZEIT=${ENDUHRZEIT}"
      - "ORGANISATOREN=${ORGANISATOREN}"
      - "TUTOREN=${TUTOREN}"
```

Anschließend kann die Anwendung über den Befehl `docker-compose up` im Verzeichnis `./chicken`
gestartet werden.
