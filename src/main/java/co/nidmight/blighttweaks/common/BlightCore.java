package co.nidmight.blighttweaks.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.nidmight.blighttweaks.common.handlers.ChunkHandler;
import co.nidmight.blighttweaks.common.handlers.FoodHandler;
import co.nidmight.blighttweaks.common.handlers.InteractHandler;
import co.nidmight.blighttweaks.common.items.Items;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockBlood;

@Mod(modid = BTStrings.MOD_ID, name = BTStrings.MOD_NAME, version = BTStrings.VERSION)
public class BlightCore {

    public static Logger logger = LogManager.getLogger(BTStrings.MOD_ID);

    @Mod.EventHandler()
    public void preInit(FMLInitializationEvent event) {
        Items.init();
    }

    @Mod.EventHandler()
    public void init(FMLInitializationEvent event) {
        new FoodHandler();
        new InteractHandler();
        new ChunkHandler();
    }

    @Mod.EventHandler()
    public void postInit(FMLPostInitializationEvent event) {
        if (CustomItems.blood instanceof BlockBlood) { // take account for config being disabled
            CustomItems.blood.setHardness(.5f);
        }
    }

    @Mod.EventHandler
    public void missingMappings(FMLMissingMappingsEvent event) throws Exception {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.getAll()) {
            logger.info("Found missing mapping: {}", mapping.name);
            if (!mapping.name.startsWith("blightbuster")) {
                continue;
            }
            switch (mapping.name) {
                case "blightbuster:boundRing":
                    logger.info("Remapping blightbuster:boundRing to " + Items.boundRing.getUnlocalizedName());
                    mapping.remap(Items.boundRing);
                    break;
                case "blightbuster:researchnote":
                    logger.info("Remapping blightbuster:researchnote to " + Items.alienTome.getUnlocalizedName());
                    mapping.remap(Items.alienTome);
                    break;
                case "blightbuster:worldOreKiller":
                    logger
                        .info("Remapping blightbuster:worldOreKiller to " + Items.worldOreKiller.getUnlocalizedName());
                    mapping.remap(Items.worldOreKiller);
                    break;
                default:
                    logger.error("Unknown mapping: {}", mapping.name);
            }
        }
    }
}
