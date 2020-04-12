package cn.wode490390.nukkit.helixgen;

public class HelixGeneratorSettings {

    private final int borderId;
    private final int borderMeta;
    private final int pathId;
    private final int pathMeta;

    public HelixGeneratorSettings(int borderId, int borderMeta, int pathId, int pathMeta) {
        this.borderId = borderId;
        this.borderMeta = borderMeta;
        this.pathId = pathId;
        this.pathMeta = pathMeta;
    }

    public int getBorderId() {
        return this.borderId;
    }

    public int getBorderMeta() {
        return this.borderMeta;
    }

    public int getPathId() {
        return this.pathId;
    }

    public int getPathMeta() {
        return this.pathMeta;
    }
}
