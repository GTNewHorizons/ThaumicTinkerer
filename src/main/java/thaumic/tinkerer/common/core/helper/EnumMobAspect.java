package thaumic.tinkerer.common.core.helper;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityFireBat;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockSummon;
import thaumic.tinkerer.common.item.ItemMobAspect;
import thaumic.tinkerer.common.item.ItemMobDisplay;
import thaumic.tinkerer.common.lib.LibResearch;

public enum EnumMobAspect {

    // Passive
    Chicken(EntityChicken.class, new Aspect[] { Aspect.CROP, Aspect.FLIGHT, Aspect.BEAST }, 1.0f),
    Pig(EntityPig.class, new Aspect[] { Aspect.BEAST, Aspect.EARTH, Aspect.TRAVEL }),
    /// Must be before Cow because EntityMooshroom extends EntityCow
    MushroomCow(EntityMooshroom.class, new Aspect[] { Aspect.BEAST, Aspect.EARTH, Aspect.CROP }, 0.9f),
    Cow(EntityCow.class, new Aspect[] { Aspect.BEAST, Aspect.EARTH, Aspect.BEAST }, 0.9f),
    Sheep(EntitySheep.class, new Aspect[] { Aspect.EARTH, Aspect.CLOTH, Aspect.BEAST }, 0.9f),
    Ozelot(EntityOcelot.class, new Aspect[] { Aspect.BEAST, Aspect.EARTH, Aspect.ELDRITCH }),
    Wolf(EntityWolf.class, new Aspect[] { Aspect.BEAST, Aspect.BEAST, Aspect.BEAST }),
    EntityHorse(EntityHorse.class, new Aspect[] { Aspect.BEAST, Aspect.BEAST, Aspect.TRAVEL }, 0.8f),
    SnowMan(EntitySnowman.class, new Aspect[] { Aspect.WATER, Aspect.WATER, Aspect.MAN }, 0.8f),
    VillagerGolem(EntityIronGolem.class, new Aspect[] { Aspect.METAL, Aspect.METAL, Aspect.MAN }, 0.6f),
    Villager(EntityVillager.class, new Aspect[] { Aspect.MAN, Aspect.MAN, Aspect.MAN }, 0.8f),
    Squid(EntitySquid.class, new Aspect[] { Aspect.WATER, Aspect.WATER, Aspect.WATER }, 0.6f, 0.75f),
    Bat(EntityBat.class, new Aspect[] { Aspect.AIR, Aspect.AIR, Aspect.FLIGHT }, 1.9f),
    // Hostiles
    Blaze(EntityBlaze.class, new Aspect[] { Aspect.FIRE, Aspect.FIRE, Aspect.FIRE }, 0.8f),
    PigZombie(EntityPigZombie.class, new Aspect[] { Aspect.UNDEAD, Aspect.FLESH, Aspect.FIRE }, 0.8f),
    /// Must be before Slime because EntityMagmaCube extends EntitySlime
    LavaSlime(EntityMagmaCube.class, new Aspect[] { Aspect.FIRE, Aspect.SLIME, Aspect.SLIME }, 1.5f) {

        @Override
        protected Entity createEntity(World worldObj) {
            return setSlimeSize(super.createEntity(worldObj), 1);
        }
    },
    Ghast(EntityGhast.class, new Aspect[] { Aspect.FIRE, Aspect.FLIGHT, Aspect.FLIGHT }, 0.2f, 0.6f),
    Firebat(EntityFireBat.class, new Aspect[] { Aspect.FLIGHT, Aspect.FIRE, Aspect.MAGIC }, 1.9f, 0f, "Thaumcraft."),
    /// Must be before Zombie because EntityBrainyZombie extends EntityZombie
    BrainyZombie(EntityBrainyZombie.class, new Aspect[] { Aspect.MAGIC, Aspect.UNDEAD, Aspect.FLESH }, 0.8f, 0,
            "Thaumcraft."),
    Zombie(EntityZombie.class, new Aspect[] { Aspect.FLESH, Aspect.FLESH, Aspect.UNDEAD }, 0.8f),
    Skeleton(EntitySkeleton.class, new Aspect[] { Aspect.UNDEAD, Aspect.MAN, Aspect.UNDEAD }, 0.8f),
    Creeper(EntityCreeper.class, new Aspect[] { Aspect.MAGIC, Aspect.BEAST, Aspect.ELDRITCH }, 0.8f),
    Witch(EntityWitch.class, new Aspect[] { Aspect.MAGIC, Aspect.UNDEAD, Aspect.ELDRITCH }, 0.8f),
    /// Must be before Spider because EntityCaveSpider extends EntitySpider
    CaveSpider(EntityCaveSpider.class, new Aspect[] { Aspect.BEAST, Aspect.POISON, Aspect.POISON }, 0.8f),
    Spider(EntitySpider.class, new Aspect[] { Aspect.BEAST, Aspect.UNDEAD, Aspect.UNDEAD }, 0.8f),
    Slime(EntitySlime.class, new Aspect[] { Aspect.SLIME, Aspect.SLIME, Aspect.BEAST }, 1.5f) {

        @Override
        protected Entity createEntity(World worldObj) {
            return setSlimeSize(super.createEntity(worldObj), 1);
        }
    },
    Silverfish(EntitySilverfish.class, new Aspect[] { Aspect.METAL, Aspect.METAL, Aspect.EARTH }, 1.2f),
    Enderman(EntityEnderman.class, new Aspect[] { Aspect.ELDRITCH, Aspect.ELDRITCH, Aspect.MAN }, 0.7f),
    Wisp(EntityWisp.class, new Aspect[] { Aspect.AIR, Aspect.MAGIC, Aspect.MAGIC }, "Thaumcraft.");

    public static final Map<EnumMobAspect, Entity> entityCache = Maps.newHashMap();
    public final Aspect[] aspects;
    public final Class<? extends Entity> entity;
    public String prefix;
    private final float scale;
    private final float offset;

    EnumMobAspect(Class<? extends Entity> entity, Aspect[] aspects, float scale, float offset) {
        this.aspects = aspects;
        this.entity = entity;
        this.scale = scale;
        this.offset = offset;
    }

    EnumMobAspect(Class<? extends Entity> entity, Aspect[] aspects, float scale) {
        this(entity, aspects, scale, 0);
    }

    EnumMobAspect(Class<? extends Entity> entity, Aspect[] aspects, float scale, float offset, String prefix) {
        this(entity, aspects, scale, offset);
        this.prefix = prefix;
    }

    EnumMobAspect(Class<? extends Entity> entity, Aspect[] aspects) {
        this(entity, aspects, 1.0f, 0);
    }

    EnumMobAspect(Class<? extends Entity> entity, Aspect[] aspects, String prefix) {
        this(entity, aspects);
        this.prefix = prefix;
    }

    public static Entity getEntityFromCache(EnumMobAspect ent, World worldObj) {
        Entity entity = entityCache.get(ent);
        if (entity == null) {
            entity = ent.createEntity(worldObj);
            entityCache.put(ent, entity);
        }
        return entity;
    }

    private static Entity setSlimeSize(Entity entity, int size) {

        if (entity instanceof EntitySlime) {
            ((EntitySlime) entity).setSlimeSize(size);
        }

        return entity;
    }

    public static Aspect[] getAspectsForEntity(Entity e) {
        return getAspectsForEntity(e.getClass());
    }

    public static EnumMobAspect getMobAspectForType(String name) {
        if (name.isEmpty()) return null;
        Class<? extends Entity> clazz = EntityList.stringToClassMapping.get(name);
        for (EnumMobAspect e : EnumMobAspect.values()) {
            if (e.entity.isAssignableFrom(clazz)) {
                return e;
            }
        }
        return null;
    }

    public static Aspect[] getAspectsForEntity(Class<? extends Entity> clazz) {
        for (EnumMobAspect e : EnumMobAspect.values()) {
            if (e.entity.isAssignableFrom(clazz)) {
                return e.aspects;
            }
        }
        return null;
    }

    public float getVerticalOffset() {
        return offset;
    }

    public float getScale() {
        return scale;
    }

    public Class<? extends Entity> getEntityClass() {
        return entity;
    }

    public Entity getEntity(World worldObj) {
        return getEntityFromCache(this, worldObj);
    }

    protected Entity createEntity(World worldObj) {
        return EntityList.createEntityByName(toString(), worldObj);
    }

    public ResearchPage GetRecepiePage() {
        ItemStack output = new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemMobDisplay.class));
        ((ItemMobDisplay) output.getItem()).setEntityType(output, toString());
        ItemStack[] inputs = new ItemStack[this.aspects.length];
        int i = 0;
        for (Aspect a : this.aspects) {
            inputs[i++] = ItemMobAspect.getStackFromAspect(a);
        }
        InfusionRecipe recepie = new InfusionRecipe(
                LibResearch.KEY_SUMMON,
                output,
                0,
                new AspectList(),
                new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockSummon.class)),
                inputs);
        return new ResearchPage(recepie);
    }

    @Override
    public String toString() {
        return prefix == null ? super.toString() : prefix + super.toString();
    }

    static {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public static class EventHandler {

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public void onWorldUnload(WorldEvent.Unload event) {
            Minecraft mc = Minecraft.getMinecraft();
            if (event.world == mc.theWorld) {
                entityCache.clear();
            }
        }
    }
}
