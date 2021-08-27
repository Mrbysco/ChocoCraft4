# Version 0.9.9 - This version is thanks to balika011.
- Added translation for the Hungarian language.
- Added new checks for Chocobo ownership, that way your friend cant plop a nametag on your Chocobo n stuff.
- Added a gold bolder around nesting zones.
- Added a hatching progress bar to eggs.
- Updated eggs to store parents color.
- Updated the flame Chocobo to spawn in the nether.
- Updated the flame Chocobo to have fire resistance.
- Updated the flame Chocobo to have little flame babies.
- Updated the Chocobo feather to be an alternative to normal feathers.
- Fixed Baked Pickled Gysahl Green not being smeltable.
- Fixed owners checks on servers.
- Internal refactoring and fixes.

# Version 0.9.8 - beta
- Added framework to make a guide in Chocopedia, and then made a guide in said Chocopedia.
- Added new buttons to the Chocopedia GUI to unlock abilities to replace the ability fruits. - Thanks to mallrat208 for helping me make these!
- Added tooltips for buttons specifying how much exp is needed to unlock abilities along with translations for them.
- Added configuration options for the ability unlock buttons to change exp needed.
- Added Chocobo whistle sounds for each mode.
- Added a stay mode for the Chocobo whistle and translation for it.
- Added configuration options to adjust possible stat gain / loss for breeding.
- Added more features to the dive ability, your Chocobo can now walk normally under water. (No you can't abuse this with sprint =D)
- Added some in-air and in-water movement factors, while in air you will be faster if glide or fly is unlocked.
- Added text and translations for the different Chocobo saddles.
- Added saddle pack and saddle bags recipes.
- Updated all of the GUIs to look nicer and more polished.
- Updated the client and common proxies to account for player interaction.
- Updated the Chocobo sound to avoid possible copyright issues from SquareEnix in the future.
- Updated the config values to be a bit more balanced.
- Updated the internal stamina regen value while moving to be more lenient.
- Updated the sprint attribute multiplier to be more balanced.
- Updated the fly ability to require the sprint key pressed to fly.
- Updated the inventory GUI to hide slots it does not support.
- Updated the Chocobo saddle recipe to be like OG Chococraft.
- Fixed the Chocobo fruit dungeon loot weight. - Thanks to mallrat208 for fixing this!
- Fixed crash with Reskillable and maybe other mods by adding some null checks.
- Fixed sprinting not actually needing the sprint ability.
- Fixed Chocobo not dropping inventory when killed.
- Fixed fly taking stamina under water.
- Fixed sprint taking stamina when not unlocked or not appropriate.
- Fixed Chocobo upgrade packet not being scheduled serverside.
- Fixed spam jumping being faster than walking and equal to sprinting.

# Version 0.9.7 - beta
- Updated Forge Version and MCP Mappings. -mallrat208
- Added the Chocobo whistle for getting your Chocobo to follow you. (right click them with the whistle)
- Added Chocobo feathers. (they drop from Chocobos)
- Adjusted the way textures/rendering works for Chocobos, this saves lots of space and shortens loading times. -mallrat208
- Adjusted recipies to include Chocobo feathers.
- Adjusted Chocobo spawning code.
- Fixed serious bug when logging off riding a Chocobo, you can log off riding one without worry now. -mallrat208
- Fixed overpowered food saturation.
- Fixed localization for creative tab.
- Fixed Chocobo WAA HEE WOOOOOO spam.
- Fixed an issue that allowed riding baby Chocobos.

# Version 0.9.6 - beta
- Fixed curse_id pointing to old chococraft thread.
- Fixed incompatibility with primitive mobs. (and probably others)
- Fixed Chocobo not facing right direction for other players. Thanks to Paul Fulham for the fix.
- Fixed Chocobo not centered for new models. Thanks to Paul Fulham for the fix.
- Added config option for only generating gysahl greens in overworld. (Enabled by default.)
- Added Chocobos to hills spawn list, because lets be honest they are hard to find with any biome mod.
- Added new Chocobo models, credit to Kraehe for textures/models. (Report any issues with these, might animations I have to work on.)
- Added a new animation for running/sprinting/flying forward, Chocobo will lean its head.
- Removed unused imports.

# Version 0.9.5 - beta
- Fixed female-happy eggs, maybe you'll actually get a male this time.

# Version 0.9.4 - beta
- Fixed flame Chocobo not rare, whoopsies.
- Adjusted time it takes for chicobo to hatch, matches sunup to sundown in ticks.

# Version 0.9.3 - beta
- Refractored file structure.
- Adjusted lovely gysahl to be consumable.
- Fixed the last world gen interference by swapping "world.rand" with "Math.random()" method.
- Fixed "Chocobo moved wrongly" console spam by adding Log4jFilter, Thanks to MattCzyr!
- Fixed purple Chocobo not purple. Thanks to MikaPikaaa for textures.
- Fixed green female Chocobo being a damn zombie. Thanks to MikaPikaaa for textures.
- Added rare flame class to make use of scrapped textures.
- Added chance for lovely gysahl farming drop.
- Removed lovely gysahl crafting recipe.
- Removed misc_ideas.
- Removed breeding_ideas.

# Version 0.9.2 - beta
- Adjusted stat and trait chances.
- Fixed gaining fly from breeding.
- Fixed gaining glide from breeding.
- Added ability to heal tamed Chocobos with gysahl greens.
- Added colors for newly born Chocobos depending on abilities/traits.
- Removed now unused config option.

# - Start of branch Slayer5934 -

# Version 0.9.1 - beta
- fix Chocobos not able to dive when having the dive ability

# Version 0.9.0 - beta
- Breeding Chocobos with a lovely gysahl green will no longer directly spawn a baby Chocobo. Instead, the mother Chocobo will place an egg on the ground.
  To hatch this egg, it needs to be picked up and placed in a Chocobo Nest.
- The color of a Chocobo can now be changed using dyes. Supported are yellow, lime, blue, white, black, ping, red, purple. (The full MC color range will be added in a future update)
- Added Stamina stat to Chocobos that is displayed as a bar of lighting strikes when mounted. Stamina is used for special Chocobo abilities and
  regenerates slowly. Standing still increases the regeneration rate slighly.
- Added 4 Chocobo abilities that are granted to a Chocobo when breeding (really low chance) or by using rare dungeon loot fruits:
  - **Spike Fruit**: Teaches the **Sprint** ability. Allows the Chocobo to run 200% faster than normal while holding the sprint key until stamina is exhausted.
  - **Aeroshroom**: Teaches the **Glide** ability. Allows the Chocobo to slow glide to the ground. (Disabled while holding the *sneak* button)
  - **Aqua Berry**: Teaches the **Dive** ability. Allows the Chocobo to dive underwater. Also grants water breathing.
  - **Dead Pepper**: Teaches the **Fly** ability. Allows the Chocobo to fly. Requires a high amount of stamina.
- The chococraft sourcecode is now licensed under the MIT license. Most of the assets are licensed under CC-BY-SA. See the appropriate license document or the readme on github for further information.
- Added chocopedia, which can be used on a Chocobo to view its stats. The stats on the screen are (left to right, top to bottom [sorry, tooltips will come later]):
  - Gender
  - Max Health
  - Speed
  - Max Stamina
  - Can Sprint
  - Can Glide
  - Can Dive
  - Can Fly
- Added pickled gysahl green as another food source for players

### Known Issues
- *Chocobos dont take fall damage*
- *Statscaling might be inbalanced. No balance testing has been done yet.*
- *Server consoles might spam "Chocobo moved wrongly" when moving quickly across sloped terrain*

# Version 0.2.1 - alpha
- fix Chocobos eating saddles when being rightclicked with one in hand.
  I dont want to force Chocobos onto a certain diet, but i dont think
  leather is very nutritious and therefore should be avoided.

# Version 0.2 - alpha
- First release
- Chocobos spawn randomly in the overworld (and maybe other dimensions)
- Gysahl Green crop worldgen
- Temporary breeding system: Use two *lovely gysahl greens* to breed two Chocobos.
  The offspring will be of a random color. (This system will change in a future release!)
- Chocobos can be tamed using *gysahl green* (15% chance per green used)
- Tamed Chocobos can be ridden using the Chocobo saddle
- special saddles with inventories attached are implemented but not fully tested. You might lose your items!
  (Recipes are missing because of this)
- *Traits and Abilities are currently not implemented.*
