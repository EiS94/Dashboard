# SWP19-Dashboard

## Benutzung

## Installation

### Voraussetzungen

Für den Server:

- Oracle Java (mindestens Version 8)
- Apache Tomcat® (mindestens Version 8)


Für den Client:

- aktueller Browser (z.B. Google Chrome)

### Einrichtung

Da die genaue Ermittlung des Standorts über eine Anfrage im Browser abläuft, die nur bei zertifizierten HTTPS-Seiten verfügbar ist,
muss Tomcat so angepasst werden, damit es über eine HTTPS-Seite aufgerufen werden kann. Um diese Einrichtung vorzunehmen, gehen Sie
bitte folgende Schritte durch:

1. "cd $JAVA_HOME/bin" (falls JAVA_HOME nicht definiert kann in Linux der Pfad über "dirname $(dirname $(readlink -f $(which javac)))" ausgeben 
   werden. Dann kann man manuell in diesen Ordner wechseln (danach noch mit "cd bin" in den Unterordner bin wechseln)).
2. "keytool -genkeypair -alias tom -keyalg RSA -keystore "$CATALINA_BASE/conf/SSLKey""
   (ist CATALINA_BASE nicht definiert, manuell den Tomcat-Ordner "conf" angeben und anschließend "/SSLKey" anhängen)
3. In den erscheinenden Fragen zunächst ein Passwort erstellen und merken. Anschließend die anderen Fragen beantworten. Am Schluss
   bei der Frage, ob alles korrekt ist mit y (wenn die Fragen auf englisch waren) oder j (wenn die Fragen auf deutsch waren) 
   bestätigen.
4. Die IP-Adresse des Rechners in dem Netzwerk, in dem das Dashboard verwendet werden soll herausfinden, und diese statisch machen.
5. In "$CATALINA_BASE/conf" sollte nun die Datei SSLKey vorhanden sein, sowie die Datei server.xml. Diese muss nun noch angepasst
   werden mit "sudo nano server.xml" (wenn man sich schon im entsprechenden Ordner befindet).
   In der Datei den Eintrag
   <Connector 	port="8080" 
		protocol="HTTP/1.1"
              	connectionTimeout="20000"
               	redirectPort="8443" />
   suchen, und nach port="8080" den Eintrag "address="DEINE_IP_ADRESSE"" hinzufügen.

   Dann noch nach dem Eintrag
   <Connector
           protocol="org.apache.coyote.http11.Http11NioProtocol"
           port="8443" maxThreads="150" SSLEnabled="true"  
           sslProtocol="TLS"/>
   suchen. Dieser kann je nach Tomcat Version ein bisschen anders aussehen und ist im Ausgangszustand auskommentiert. Die 
   Auskommentierung entfernen und den Eintrag so anpassen, dass er wie folgt aussieht:
   <Connector
           protocol="org.apache.coyote.http11.Http11NioProtocol"
           port="8443" maxThreads="150" SSLEnabled="true" scheme="https" 
           secure="true" clientAuth="false" sslProtocol="TLS" 
           keystoreFile="$CATALINA_BASE/conf/SSLKey" 
           keystorePass="DEIN_SSL_PASSWORT"/>
6. Anschließend Tomcat ggf. neustarten. Danach ist Tomcat im Browser unter "https://DEINE_IP_ADRESSE:8443" verfügbar und kann auch 
   von anderen Geräten im selben Netzwerk darüber aufgerufen werden.

Hinweis: Sollte der Browser sagen, dass es sich um keine sichere Verbindung handelt, muss man zunächst noch manuell bestätigen, dass man dem Zertifikat (welches man ja selbst erstellt hat) vertraut. 

### Deployment

1. Tomcat auf dem Computer so konfigurieren, dass unter "localhost:8080" im Browser die Tomcat-Startseite erscheint,
   wenn der Server gestartet wurde.
   Hinweis: Um von anderen Geräten im gleichen Netzwerk auf das Dashboard zuzugreifen, muss Tomcat angepasst werden, damit der
   Server unter seiner IP-Adresse erreichbar ist. Um diese Änderung durchzuführen folgen Sie den Schritten im nächsten Abschnitt "Von anderen Geräten auf das Dashboard zugreifen".

2. Auf der Tomcat-Startseite auf "Manager App" klicken und sich mit dem festgelegtem Nutzernamen und Passwort anmelden.
   Hat man noch keinen Benutzer angelegt oder die Anmeldedaten vergessen, so kann man diese festlegen, indem man die im Verzeichnis
   "/TOMCAT_HOME/conf/" die Datei "tomcat-users.xml" so anpasst, dass folgende Zeilen enthalten sind:
		
	<tomcat-users>
  	<role rolename="manager-gui"/>
  	<user username="BENUTZERNAME" password="PASSWORT" roles="manager-gui"/>
	</tomcat-users>

   BENUTZERNAME und PASSWORT kann dann nach belieben angepasst werden. Danach sollte ein einloggen möglich sein.

3. In der "Manager App" bis zum Abschnitt "Lokale WAR Datei zur Installation hochladen" scrollen. Hier kann mit "Durchsuchen..."
   die hier mitgelieferte WAR-Datei "Dashboard.war" ausgewählt werden. Anschließend klickt man auf "Installieren". 

4. Unter "Anwendungen" sollte nun ein Eintrag "Dashboard" zu finden sein. Klickt man diesen an, wird man auf das Dashboard
   weitergeleitet.

5. Sobald der Tomcat-Server läuft, kann man jetzt im Browser immer über "localhost:8080/Dashboard" auf das Dashboard zugreifen.


## Von anderen Geräten auf das Dashboard zugreifen

1. Die lokale IP-Adresse des Servers im Netzwerk ermitteln. Dazu die Konsole öffnen und in Windows "ipconfig", in Linux "ifconfig" eingeben. Anschließend die IPv4-Adresse auslesen und merken.
2. Den Server so einstellen, dass er eine statische IP-Adresse erhält und dabei die eben ermittelte Adresse verwenden.
3. Unter CATALINA_HOME/conf/ die Datei server.xml öffnen und nach folgendem Eintrag suchen:
        <Connector port="8080" protocol="HTTP/1.1" address="127.0.0.1"
               connectionTimeout="20000"
               redirectPort="8443" />
4. Die Adresse nach address auf die eben eingerichtete IP-Adresse des Servers ändern.
5. Nun sollte Tomcat im Browser von allen Geräten im selben Netzwerk unter "http://IP_ADDRESS_SERVER:8080" aufgerufen werden können. 


