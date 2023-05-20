/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [4 Sep 2013, 16:59:57 (GMT)]
 */
package thaumic.tinkerer.common.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

public final class ModPotions {

    public static Potion potionFire;
    public static Potion potionWater;
    public static Potion potionEarth;
    public static Potion potionAir;

    public static void initPotions() {
        MinecraftForge.EVENT_BUS.register(new PotionEffectHandler());
        FMLCommonHandler.instance().bus().register(new PotionEffectHandler());

        // Code based on potion code from WayOfTime
        Potion[] potionTypes = null;

        if (Potion.potionTypes.length < 256) {
            for (Field f : Potion.class.getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                        Field modfield = Field.class.getDeclaredField("modifiers");
                        modfield.setAccessible(true);
                        modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                        final Potion[] oldPotionTypes = Potion.potionTypes;
                        final Potion[] newPotionTypes = new Potion[256];
                        System.arraycopy(oldPotionTypes, 0, newPotionTypes, 0, oldPotionTypes.length);
                        f.set(null, newPotionTypes);
                    }
                } catch (Exception e) {
                    System.err.println("Severe error when extending the potion array:");
                    System.err.println(e);
                }
            }
        }

        potionFire = (new DummyPotions(ConfigHandler.potionFireId, true, 0)).setIconIndex(0, 0)
                .setPotionName("Fire Imbued");
        potionWater = (new DummyPotions(ConfigHandler.potionWaterId, true, 0)).setIconIndex(0, 0)
                .setPotionName("Water Imbued");
        potionEarth = (new DummyPotions(ConfigHandler.potionEarthId, true, 0)).setIconIndex(0, 0)
                .setPotionName("Earth Imbued");
        potionAir = (new DummyPotions(ConfigHandler.potionAirId, true, 0)).setIconIndex(0, 0)
                .setPotionName("Air Imbued");
    }
}
