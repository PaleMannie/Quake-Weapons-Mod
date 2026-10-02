# Quake Weapons – NeoForge 26.3

Dieser Branch benötigt Minecraft **26.3**, NeoForge **26.3.0.40-beta** oder einen kompatiblen neueren 26.3-Build sowie **GeckoLib 5.5.7 für NeoForge / Minecraft 26.3**. GeckoLib ist eine separate Laufzeitabhängigkeit und wird nicht in die Mod-JAR eingebettet.

## Entwicklung

JDK 25 verwenden. Gradle und die passenden NeoForge-/GeckoLib-Abhängigkeiten werden über den Wrapper heruntergeladen:

```powershell
.\gradlew.bat build
.\gradlew.bat runClient
.\gradlew.bat runServer
```

Die fertige Mod liegt unter `build/libs/quakeweapons-3v-26.3-neoforge.jar`. `build` führt auch `verifyPayloadCodecs` aus: Der Check prüft die drei Netzwerkformate und die Unabhängigkeit aufeinanderfolgender Rückstoßpakete.

Das Spielverzeichnis lässt sich für isolierte Tests ändern:

```powershell
.\gradlew.bat runClient -PrunDir=build/client-test
.\gradlew.bat runServer -PrunDir=build/server-test
```

Die Konfigurationsdateinamen bleiben `quakeweapons-common.toml` und `quakeweapons-server.toml`. NeoForge 26.3 verwendet dafür die Typen `LOCAL` und `SYNCED`.

GeckoLib wird über eine feste Modrinth-Versions-ID bezogen, damit Gradle eindeutig die NeoForge-Ausgabe für 26.3 lädt. Versionen stehen in `gradle.properties`.
