# TrainGame

TrainGame is a small Java Swing railway-routing puzzle. Trains move
automatically around a grid while the player changes switches and connects
tunnel entrances to guide their wagons.

## Gameplay

Each train contains colored passenger wagons. The goal is to route each wagon
past a station of the same color so its passengers can leave the train.

- Click a railway switch to change its selected branch.
- Click a tunnel entrance to select or deselect it.
- Two selected tunnel entrances form a connection.
- If two train elements occupy the same rail, the player loses.
- The level is won when the trains have been emptied.

Trains are created automatically while the game is running. After completing
the first level, the second bundled level starts automatically.

## Requirements

- JDK 8 or newer
- PowerShell for the commands below

The project uses only the Java standard library and has no external
dependencies.

## Build and Run

Run the following commands from the repository root. This is required because
the game loads level files and images using paths relative to the current
working directory.

```powershell
New-Item -ItemType Directory -Force -Path out | Out-Null
$sources = Get-ChildItem -Path src -Recurse -Filter *.java |
    ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sources
java -cp out application.Main
```

The main class is `application.Main`.

## Controls

The menu and game messages are in Hungarian. The three menu actions are:

| Menu action | Meaning |
| --- | --- |
| Start | Begin a new game at level 1 |
| Load | Load a serialized game |
| Exit | Close the application |

During a game, use the left mouse button:

| Target | Action |
| --- | --- |
| Railway switch | Toggle the active route |
| Tunnel entrance | Select or deselect the entrance |

At most two tunnel entrances can be selected at once.

## Levels

Two levels are included in `1.txt` and `2.txt`. Each file is a whitespace
separated grid whose symbols are interpreted as follows:

| Symbol | Field |
| --- | --- |
| `E` | Empty field |
| `R` | Rail |
| `S` | Railway switch |
| `T` | Tunnel entrance |
| `C` | Rail crossing |
| `1` | Red station |
| `2` | Blue station |
| `3` | Green station |
| `4` | Orange station |
| `5` | Yellow station |

The two bundled levels use red, blue, green, and orange stations. Level files
must remain in the repository root unless the level-loading code is changed.

## Project Structure

```text
TrainGame/
|-- 1.txt, 2.txt          Level definitions
|-- Resources/            Railway, station, wagon, and terrain images
`-- src/
    |-- application/      Entry point, game controller, and level builder
    |-- graphics/         Swing window, menu, game loop, and rendering
    |-- map/              Rail and map-field types
    `-- vehicles/         Locomotive and wagon types
```

## Current Limitations

- There is no Maven or Gradle build configuration.
- There is no automated test suite.
- Levels and image resources are loaded through relative filesystem paths, so
  the game must be launched from the repository root.
- Loading a serialized game is available from the menu, but the existing save
  method is not connected to a menu item or keyboard shortcut.
- The source contains some incorrectly encoded Hungarian text.
