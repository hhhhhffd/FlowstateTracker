<div align="center">

<h1>FlowstateTracker</h1>
<hr>

**Client-side Fabric HUD that tracks the Flowstate block counter on your held tool**

![Minecraft](https://img.shields.io/badge/MINECRAFT-26.2-62B47A?style=flat-square&logo=minecraft&logoColor=white)
![Fabric](https://img.shields.io/badge/FABRIC_LOADER-0.19.3-DBD0B4?style=flat-square)
![Java](https://img.shields.io/badge/JAVA-26-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![License](https://img.shields.io/github/license/hhhhhffd/FlowstateTracker?style=flat-square)

[Download](#install) · [Build](#build) · [License](#license)

</div>

> [!NOTE]
> Archived — no longer maintained.

## What it is

FlowTracker shows a small on-screen counter for the Flowstate effect: how many blocks you have mined with the item in your main hand, out of a cap of 200. It reads the `compact_blocks` value from the item's custom data component, so it needs no server-side support.

## Features

- HUD text with three lines: `Flowstate`, `progress / 200`, and a countdown of the 10-second idle timeout.
- The text turns green when the counter reaches 200.
- The counter resets when you switch items, die, disconnect, stop mining for 10 seconds, or the item's value drops.
- Drag the HUD while the chat is open; the position is saved to `config/flowtracker.json`.

## Stack

| Part | Version |
| --- | --- |
| Minecraft | 26.2 (`FlowTracker-26.2.jar`); a 1.21.11 build is also in the repository (`FlowTracker-1.21.11.jar`) |
| Fabric Loader | >= 0.19.3 |
| Fabric API | 0.155.2+26.2 |
| Java | 26 |
| Mod version | 1.1 |

## Install

Put `FlowTracker-26.2.jar` (or `FlowTracker-1.21.11.jar` for Minecraft 1.21.11) and Fabric API into `mods/`. The JARs are in the repository root; no GitHub release was published.

## Build

```bash
./gradlew build
```

The mod JAR is written to `build/libs/`.

## License

MIT, see [LICENSE](LICENSE).
