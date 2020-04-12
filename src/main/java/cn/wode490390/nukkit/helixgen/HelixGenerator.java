package cn.wode490390.nukkit.helixgen;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockUnknown;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.MathHelper;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.wode490390.nukkit.helixgen.util.BlockEntry;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class HelixGenerator extends Generator {

    protected static final List<BlockEntry> BLOCKS = Lists.newArrayListWithCapacity(256 - 113);

    protected ChunkManager level;
    protected NukkitRandom nukkitRandom;
    protected long localSeed1;
    protected long localSeed2;
    protected HelixGeneratorSettings settings;

    public HelixGenerator() {
        this(null);
    }

    public HelixGenerator(Map<String, Object> options) {
        this.settings = HelixGeneratorPlugin.getInstance().getSettings();
    }

    @Override
    public int getId() {
        return TYPE_INFINITE;
    }

    @Override
    public String getName() {
        return "normal";
    }

    @Override
    public ChunkManager getChunkManager() {
        return this.level;
    }

    @Override
    public Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
        this.nukkitRandom = random;
        this.nukkitRandom.setSeed(this.level.getSeed());
        this.localSeed1 = ThreadLocalRandom.current().nextLong();
        this.localSeed2 = ThreadLocalRandom.current().nextLong();
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        this.nukkitRandom.setSeed(chunkX * this.localSeed1 ^ chunkZ * this.localSeed2 ^ this.level.getSeed());
        BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int xOffset = x - 8;
                int zOffset = z - 8;
                float distance = MathHelper.sqrt(xOffset * xOffset + zOffset * zOffset);

                if (distance <= 9 && distance >= 6) {
                    if ((int) distance == 7) {
                        chunk.setBlock(x, 128, z, this.settings.getPathId(), this.settings.getPathMeta());
                    } else {
                        chunk.setBlock(x, 128, z, this.settings.getBorderId(), this.settings.getBorderMeta());
                    }
                }

                chunk.setBiome(x, z, EnumBiome.PLAINS.biome);
            }
        }

        float xFactor = this.nukkitRandom.nextFloat() / 10;
        float zFactor = this.nukkitRandom.nextFloat() / 10;
        int max = 10 + this.nukkitRandom.nextBoundedInt(75);

        for (int i = -max; i < max; ++i) {
            int x = 8 + (int) (5 * MathHelper.sin(xFactor * i));
            int z = 8 + (int) (5 * MathHelper.sin(zFactor * i));
            BlockEntry block = BLOCKS.get(this.nukkitRandom.nextBoundedInt(BLOCKS.size()));

            chunk.setBlock(x, 128 + i, z, block.getId(), block.getMeta());
            chunk.setBlock(x, 128 - i, z, block.getId(), block.getMeta());
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {

    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 256, 0.5);
    }

    static {
        boolean[] unstable = new boolean[256];
        unstable[Block.AIR] = true;
        unstable[Block.GRASS] = true;
        unstable[Block.SAPLING] = true;
        unstable[Block.WATER] = true;
        unstable[Block.STILL_WATER] = true;
        unstable[Block.LAVA] = true;
        unstable[Block.STILL_LAVA] = true;
        unstable[Block.SAND] = true;
        unstable[Block.GRAVEL] = true;
        unstable[Block.LEAVES] = true;
        unstable[Block.BED_BLOCK] = true;
        unstable[Block.POWERED_RAIL] = true;
        unstable[Block.DETECTOR_RAIL] = true;
        unstable[Block.STICKY_PISTON] = true;
        unstable[Block.TALL_GRASS] = true;
        unstable[Block.DEAD_BUSH] = true;
        unstable[Block.PISTON] = true;
        unstable[Block.PISTON_HEAD] = true;
        unstable[Block.DANDELION] = true;
        unstable[Block.RED_FLOWER] = true;
        unstable[Block.BROWN_MUSHROOM] = true;
        unstable[Block.RED_MUSHROOM] = true;
        unstable[Block.TORCH] = true;
        unstable[Block.FIRE] = true;
        unstable[Block.CHEST] = true;
        unstable[Block.REDSTONE_WIRE] = true;
        unstable[Block.WHEAT_BLOCK] = true;
        unstable[Block.FARMLAND] = true;
        unstable[Block.SIGN_POST] = true;
        unstable[Block.WOODEN_DOOR_BLOCK] = true;
        unstable[Block.RAIL] = true;
        unstable[Block.WALL_SIGN] = true;
        unstable[Block.LEVER] = true;
        unstable[Block.STONE_PRESSURE_PLATE] = true;
        unstable[Block.IRON_DOOR_BLOCK] = true;
        unstable[Block.WOODEN_PRESSURE_PLATE] = true;
        unstable[Block.GLOWING_REDSTONE_ORE] = true;
        unstable[Block.UNLIT_REDSTONE_TORCH] = true;
        unstable[Block.REDSTONE_TORCH] = true;
        unstable[Block.STONE_BUTTON] = true;
        unstable[Block.SNOW] = true;
        unstable[Block.ICE] = true;
        unstable[Block.CACTUS] = true;
        unstable[Block.SUGARCANE_BLOCK] = true;
        unstable[Block.JUKEBOX] = true;
        unstable[Block.NETHER_PORTAL] = true;
        unstable[Block.CAKE_BLOCK] = true;
        unstable[Block.UNPOWERED_REPEATER] = true;
        unstable[Block.POWERED_REPEATER] = true;
        unstable[Block.INVISIBLE_BEDROCK] = true;
        unstable[Block.TRAPDOOR] = true;
        unstable[Block.PUMPKIN_STEM] = true;
        unstable[Block.MELON_STEM] = true;
        unstable[Block.VINE] = true;
        unstable[Block.MYCELIUM] = true;
        unstable[Block.WATER_LILY] = true;
        unstable[Block.NETHER_WART_BLOCK] = true;
        unstable[Block.ENCHANT_TABLE] = true;
        unstable[Block.BREWING_STAND_BLOCK] = true;
        unstable[Block.CAULDRON_BLOCK] = true;
        unstable[Block.END_PORTAL] = true;
        unstable[Block.DRAGON_EGG] = true;
        unstable[Block.DROPPER] = true;
        unstable[Block.ACTIVATOR_RAIL] = true;
        unstable[Block.COCOA] = true;
        unstable[Block.ENDER_CHEST] = true;
        unstable[Block.TRIPWIRE_HOOK] = true;
        unstable[137] = true;
        unstable[Block.BEACON] = true;
        unstable[Block.CARROT_BLOCK] = true;
        unstable[Block.POTATO_BLOCK] = true;
        unstable[Block.WOODEN_BUTTON] = true;
        unstable[Block.SKULL_BLOCK] = true;
        unstable[Block.ANVIL] = true;
        unstable[Block.TRAPPED_CHEST] = true;
        unstable[Block.LIGHT_WEIGHTED_PRESSURE_PLATE] = true;
        unstable[Block.HEAVY_WEIGHTED_PRESSURE_PLATE] = true;
        unstable[Block.UNPOWERED_COMPARATOR] = true;
        unstable[Block.POWERED_COMPARATOR] = true;
        unstable[Block.DAYLIGHT_DETECTOR] = true;
        unstable[Block.HOPPER_BLOCK] = true;
        unstable[Block.LEAVES2] = true;
        unstable[Block.IRON_TRAPDOOR] = true;
        unstable[Block.DOUBLE_PLANT] = true;
        unstable[Block.STANDING_BANNER] = true;
        unstable[Block.WALL_BANNER] = true;
        unstable[Block.DAYLIGHT_DETECTOR_INVERTED] = true;
        unstable[188] = true;
        unstable[189] = true;
        unstable[Block.SPRUCE_DOOR_BLOCK] = true;
        unstable[Block.BIRCH_DOOR_BLOCK] = true;
        unstable[Block.JUNGLE_DOOR_BLOCK] = true;
        unstable[Block.ACACIA_DOOR_BLOCK] = true;
        unstable[Block.DARK_OAK_DOOR_BLOCK] = true;
        unstable[Block.ITEM_FRAME_BLOCK] = true;
        unstable[202] = true;
        unstable[204] = true;
        unstable[Block.UNDYED_SHULKER_BOX] = true;
        unstable[Block.ICE_FROSTED] = true;
        unstable[Block.END_GATEWAY] = true;
        unstable[217] = true;
        unstable[Block.SHULKER_BOX] = true;
        unstable[Block.CONCRETE_POWDER] = true;
        unstable[238] = true;
        unstable[239] = true;
        unstable[240] = true;
        unstable[242] = true;
        unstable[Block.PODZOL] = true;
        unstable[Block.BEETROOT_BLOCK] = true;
        unstable[Block.PISTON_EXTENSION] = true;
        unstable[251] = true;
        unstable[252] = true;
        unstable[255] = true;

        for (int i = 0; i < 256; i++) {
            Block block = Block.fullList[i << 4];
            int id = block.getId();
            if (!(block instanceof BlockUnknown) && !unstable[id]) {
                BLOCKS.add(new BlockEntry(id, block.getDamage()));
            }
        }
    }
}
