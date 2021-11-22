# Compilation

Compiling 1.7.10 forge mods is no easy task, as Forge dropped 1.7.10 support looong ago.

But it can be done via bootlegs and dedication.

Here is documented my experience of this mod's compilation, which may be for any number of reasons different for you.

# Prerequisites

Java 1.8.0

# Steps

1. Download forge-1.7.10 (1566) SRC.
2. Replace gradle in said SRC with 1.8 MDK
3. Update said gradle to 3.0 with `./gradlew wrapper --gradle-version=3.0`
4. Add required mod APIs (see [Adding mod APIs](#adding-mod-apis))
5. Setup workspace with `./gradlew setupDecompWorkspace`
5.1. (Optional) Setup eclipse workspace with `./gradlew eclipse`
6. `./gradlew build`
7. You're golden.

## Adding mod APIs

In 1.7.10 you can just add mod .jar files to `/libs` folder of your project. These are the mods required to compile TerraFirmaPunk-tweaks:
- TerraFirmaCraft 0.79.29.922
- Buildcraft 7.1.14
- CoFHCore 3.1.3-328
- Flaxbeard's Steam Power 0.28.12
- Forestry 4.2.11.59
- ImmersiveEngineering 0.7.4
- MineTweaker 3.0.10B
- Necromancy 1.7.10
- Railcraft 9.8.0.0
- Steamcraft2 B5.0.1

However, for me, personally, some of the mods needed to be deobfuscated, specifically:
- Flaxbeard's Steam Power
- Necromancy

To deobfuscate them I have used Bearded Octo Nemesis 2(BON2).
