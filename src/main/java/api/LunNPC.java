package api;

import org.necrotic.client.renderable.NPC;

public class LunNPC {
    int index;
    String name;
    int x;
    int y;
    Tile tile;

    public LunNPC(NPC npc, int index) {
        this.index = index;
        this.name = npc.definitionOverride.name;
        this.x = npc.x;
        this.y = npc.y;
        this.tile = new Tile(npc.x, npc.y);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tile getTile() {
        return tile;
    }

}
