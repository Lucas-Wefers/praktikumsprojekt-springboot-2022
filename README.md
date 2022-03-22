# Chicken - Anwendung zur Urlaubs- und Klausurverwaltung

## Anleitung zur Konfiguration

:warning: **Zum Deployment der Anwendung muss Docker und Docker-Compose installiert sein** :warning:

Für die Konfiguration muss im `./chicken` Verzeichnis eine `.env` Datei mit den folgenden Variablen
erstellt werden:

```
mysqlpassword=**** (Kann beliebig gesetzt werden / Mariadb Server ist unter Port 3306 erreichbar)
clientid=**** (Github Client ID)
clientsecret=**** (Github Client Secret)
start=2022-03-07 (Format yyyy-mm-tt)
ende=2022-03-26 (Format yyyy-mm-tt)
organisatoren= organisator1, organisator2, ...
tutoren= tutor1, tutor2, ...
```

Alternativ können in der `docker-compose.yml` die jeweiligen Platzhalter durch die entsprechenden
Werte ersetzt werden:

```yaml
...
  ...
    environment:
      - "mysqlpassword=${mysqlpassword}"
      - "clientid=${clientid}"
      - "clientsecret=${clientsecret}"
      - "start=${start}"
      - "ende=${ende}"
      - "organisatoren=${organisatoren}"
      - "tutoren=${tutoren}"
```

Anschließend kann die Anwendung über den Befehl `docker-compose up` im Verzeichnis `./chicken`
gestartet werden.