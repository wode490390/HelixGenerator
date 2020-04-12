package cn.wode490390.nukkit.helixgen;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockQuartz;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.wode490390.nukkit.helixgen.util.MetricsLite;

import java.util.NoSuchElementException;

public class HelixGeneratorPlugin extends PluginBase {

    private static HelixGeneratorPlugin INSTANCE;

    private HelixGeneratorSettings settings;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 7105);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "border.material";
        int borderId = Block.QUARTZ_BLOCK;
        try {
            borderId = config.getInt(node, borderId);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int borderMeta = BlockQuartz.QUARTZ_CHISELED;
        node = "border.meta";
        try {
            borderMeta = config.getInt(node, borderMeta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        node = "path.material";
        int pathId = Block.QUARTZ_BLOCK;
        try {
            pathId = config.getInt(node, pathId);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int pathMeta = 3; //smooth
        node = "path.meta";
        try {
            pathMeta = config.getInt(node, pathMeta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        try {
            GlobalBlockPalette.getOrCreateRuntimeId(borderId, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(borderId, borderMeta);
            } catch (NoSuchElementException e) {
                borderMeta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            borderId = Block.QUARTZ_BLOCK;
            borderMeta = BlockQuartz.QUARTZ_CHISELED;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }
        try {
            GlobalBlockPalette.getOrCreateRuntimeId(pathId, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(pathId, pathMeta);
            } catch (NoSuchElementException e) {
                pathMeta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            pathId = Block.QUARTZ_BLOCK;
            pathMeta = 3;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }

        this.settings = new HelixGeneratorSettings(borderId, borderMeta, pathId, pathMeta);

        Generator.addGenerator(HelixGenerator.class, "default", Generator.TYPE_INFINITE);
        Generator.addGenerator(HelixGenerator.class, "normal", Generator.TYPE_INFINITE);
    }

    public HelixGeneratorSettings getSettings() {
        return this.settings;
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }

    public static HelixGeneratorPlugin getInstance() {
        return INSTANCE;
    }
}
