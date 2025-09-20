package thaumic.tinkerer.preloader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

/**
 * Created by Katrina on 06/04/14.
 */
@MCVersion("1.7.10")
public class ThaumicTLoaderContainer extends DummyModContainer implements IFMLLoadingPlugin, IEarlyMixinLoader {

    public static Logger logger = LogManager.getLogger("TTinkers");
    private final ModMetadata md = new ModMetadata();

    public ThaumicTLoaderContainer() {
        md.credits = "nekosune, Pixlepix, mitchej123, and all thanks to Vazkii";
        md.modId = getModId();
        md.version = getVersion();
        md.name = getName();
        md.authorList = Arrays.asList("nekosune", "Pixlepix", "Vazkii", "mitchej123");
        md.parent = "ThaumicTinkerer";
    }

    @Override
    public String getModId() {
        return "ThaumicTinkerer-preloader";
    }

    @Override
    public String getName() {
        return "Thaumic Tinkerer Core";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String getDisplayVersion() {
        return getVersion();
    }

    @Override
    public ModMetadata getMetadata() {
        return md;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return "thaumic.tinkerer.preloader.ThaumicTLoaderContainer";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getMixinConfig() {
        return "mixins.ThaumicTinkerer.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        if (!FMLLaunchHandler.side().isClient()) {
            return Collections.singletonList("AccessorItemInWorldManager");
        }
        return Arrays.asList("renderer.MixinRenderBlocks", "AccessorItemInWorldManager");
    }
}
