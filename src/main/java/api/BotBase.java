package api;

import org.necrotic.client.Client;
import org.necrotic.client.RSInterface;
import org.necrotic.client.entity.player.Player;
import org.necrotic.client.renderable.NPC;

import java.util.List;


public class BotBase {
    private Client client;
    private long lastAnimation = -1;
    private static final int PROTECT_MELEE = 32521;
    private static final int TURMOIL = 32541;


    public BotBase(Client client) {
        this.client = client;
    }

    public boolean isLoggedIn() {
        return client.loggedIn;
    }


    public Tile getPlayerTile() {
        int x = 0;
        int y = 0;

        Player player = getLocalPlayer();
        if (player != null) {
            x = player.x;
            y = player.y;
            return new Tile(x,y);
        }

        return null;
    }

    public Player getLocalPlayer() {
        Player myPlayer = client.getLocalPlayer();
        if (myPlayer != null) {
            return myPlayer;
        }
        return null;
    }

    public void attack(LunNPC npc) {
        if (npc != null) {
            sendAction(412, npc.getIndex(), 0,0, 0);
        }
    }

    public List<LunNPC> getNpcsByName(String name) {
        List<LunNPC> npcs = new java.util.ArrayList<LunNPC>();
        int[] npcIndices = client.npcIndices;
        NPC[] npcArray = client.npcArray;
        for (int i : npcIndices) {
            if (i != 0) {
                if (npcArray[i] != null && npcArray[i].definitionOverride != null) {
                    NPC npc = client.npcArray[i];
                    if (npc != null && npc.definitionOverride.name.equalsIgnoreCase(name)) {
                        LunNPC lunNPC = new LunNPC(npc, i);
                        npcs.add(lunNPC);
                    }
                }
            }
        }
        if (npcs.size() > 0) {
            return npcs;
        }
        return null;
    }

    public static final double distanceBetween(Tile a, Tile b) {
        int x = b.getX() - a.getX();
        int y = b.getY() - a.getY();

        return Math.sqrt((x * x) + (y * y));
    }


    public void startInstance() {
        sendAction(539, 4278, 2, 3214, 0);
    }

    public boolean isLocalPlayerAnimating() {
        if (client.getLocalPlayer() != null) {
//            System.out.println("LOCAL PLAYER NOT NULL");
//            System.out.println("ANIMATION: " + client.getLocalPlayer().anim);
//            System.out.println("Last Animation: " + lastAnimation);
//            System.out.println("Time since last animation" + (System.currentTimeMillis() - lastAnimation));

//            if (client.getLocalPlayer().anim == -1 && (System.currentTimeMillis() > (lastAnimation + 1000))) {
            if (client.getLocalPlayer().anim == -1) {
                return false;
            } else if (client.getLocalPlayer().anim != -1){
//                lastAnimation = System.currentTimeMillis();
                return true;
            }
        }
        return true;
    }

    protected void sendAction(int action, int cmd1, int cmd2, int cmd3, int cmd4) {
        if (client.loggedIn) {
            client.menuActionID[client.menuActionRow] = action;
            client.menuActionCmd1[client.menuActionRow] = cmd1;
            client.menuActionCmd2[client.menuActionRow] = cmd2;
            client.menuActionCmd3[client.menuActionRow] = cmd3;
            client.menuActionCmd4[client.menuActionRow] = cmd4;
            client.doAction(client.menuActionRow);
            client.menuActionRow++;
        }
    }

}


//    public boolean activatePrayers() throws InterruptedException {
//        if (isLoggedIn()) {
//            RSInterface protectMelee = RSInterface.interfaceCache[PROTECT_MELEE];
//            RSInterface turmoil = RSInterface.interfaceCache[TURMOIL];
//
//            if (protectMelee.valueIndexArray != null) {
////                if (client.variousSettings[protectMelee.valueIndexArray[0][1]] == 1) {
////                    sendAction(169, 0 ,0, 32521, 0);
////                    Thread.sleep(500);
////                    sendAction(169, 0 ,0, 32541, 0);
////
////                }
////                System.out.println("Melee: " + client.variousSettings[protectMelee.valueIndexArray[0][1]]);
////                System.out.println("Turmoil: " + client.variousSettings[turmoil.valueIndexArray[0][1]]);
//
//            }
//        }
//        return true;
//    }