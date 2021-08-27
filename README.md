# Chococraft 4
This repository is based on Slayer5934's [ChocoCraft3](https://github.com/Slayer5934/ChocoCraft3SLY) repository.

Which was rewritten from scratch based on ChocoCraft 1 and 2.

# License information
The source code of this mod, which is located under `src/main/java`, is licensed under the MIT license, see [LICENSE-code.md](LICENSE-code.md)

![https://licensebuttons.net/l/by-sa/3.0/88x31.png](https://licensebuttons.net/l/by-sa/3.0/88x31.png)
The assets in this mod are licensed under [**CC-BY-SA 4.0**](https://creativecommons.org/licenses/by-sa/4.0/), see [LICENSE-code.md](LICENSE-art.md)

# Changes
To comply with the [**CC-BY-SA 4.0**](https://creativecommons.org/licenses/by-sa/4.0/) license of the assets the following adjustments were made:
- While porting Slayer5934's repository to 1.16.5 the `chocobo_stats.png` asset got slightly adjusted (A section got moved by a few pixels) otherwise it's identical.

# Setting up a development workspace
1. Ensure that `Java` (found [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)), `Git` (found [here](http://git-scm.com/)) are installed correctly on your system.
- Clone this repository into a new folder using your IDE, a git gui or the git command line.
- In your IDE, import the `build.gradle` from the chococraft project
- Generate the run configurations by running the following gradle task, depending on your IDE:
  - Eclipse: `gradlew genEclipseRuns`
  - IntelliJ IDEA: `gradlew genIntellijRuns`
  - VSCode: `gradlew genVSCodeRuns`

# Credits
* EddieV for creating the first version of [Chococraft for Minecraft 1.1](http://www.minecraftforum.net/forums/search?by-author=EddieV&page=5&search-thread-id=1280466)
* ArnoSaxena for [creating Chococraft 2 for Minecraft 1.4+](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1282382-1-6-x-1-5-x-forge-torojimas-chococraft-3-0-3). (Most of the Assets used in Chococraft 3 are done by him!)
* Clienthax for porting [Chococraft 2 to Minecraft 1.7.10 and 1.8](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2269183-1-8-clienthaxs-chococraft-2-happiness-distilled)
