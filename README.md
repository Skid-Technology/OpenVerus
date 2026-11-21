<p align="center">
  <img src="assets/ss.png" alt="Skid Solutions Banner" width="350" />
  <br><br>
  <a href="https://discord.gg/WYjATa6BrU">
    <img src="https://img.shields.io/badge/Discord-Skid%20Solutions-18181b?style=for-the-badge&logo=discord&logoColor=ffffff&labelColor=09090b" alt="Discord" />
  </a>
  <img src="https://img.shields.io/badge/Version-b3952-27272a?style=for-the-badge&labelColor=09090b" alt="Version b3952" />
  <img src="https://img.shields.io/badge/Java-17-3f3f46?style=for-the-badge&logo=openjdk&logoColor=ffffff&labelColor=09090b" alt="Java 17" />
  <img src="https://img.shields.io/badge/Gradle-8.14.3-52525b?style=for-the-badge&logo=gradle&logoColor=ffffff&labelColor=09090b" alt="Gradle 8.14.3" />
</p>

<p align="center">
  <a href="https://discord.gg/WYjATa6BrU">https://discord.gg/WYjATa6BrU</a>
</p>

<p align="center">
  <img src="assets/divider.svg" alt="" width="720" />
</p>

<p align="center">
  <img src="assets/jak.jpg" alt="jak" height="150" />
  <img src="assets/hack.jpg" alt="hack" height="150" />
  <img src="assets/deobf2.png" alt="deobf2" height="150" />
  <img src="assets/deobf.jpg" alt="deobf" height="150" />
</p>

<p align="center">
  <img src="assets/divider.svg" alt="" width="720" />
</p>

## Why?
- The anticheat is horrible, a lot of servers paid for it and almost every single one changed to another anticheat because this crap doesn't even have an attempt of prediction like Vulcan, the development is pretty inactive and it looks like the devs of Verus never played minecraft in their entire life.

<p align="center">
  <img src="assets/divider.svg" alt="" width="720" />
</p>

## How to build

### Prerequisites
- **Java Development Kit**: JDK 17 (the Gradle wrapper 8.14.3 does not run on newer JDKs)

### Building OpenVerus

Run from this folder:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"

.\gradlew.bat prepareIde
.\gradlew.bat clean build
```

The build also compiles every NMS adapter listed in `platforms/versions.txt` (`v1_7_R4` through `v1_20_R3`).

Generated `build/libs/`:
- `OpenVerus-b3952-java17.jar`
- `OpenVerus-b3952-plain.jar`
- `OpenVerus-b3952-sources.jar`
