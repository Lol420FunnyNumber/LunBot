import api.BotBase;
import api.LunNPC;
import api.Tile;
import org.necrotic.client.Client;
import org.necrotic.client.cache.definition.MobDefinition;
import org.necrotic.client.renderable.NPC;

import java.util.List;

public class BotThread extends Thread {
    private volatile boolean stopped;
    private volatile BotBase client;
    private volatile boolean paused;


    public BotThread(BotBase client) {
        this.client = client;
    }

    public void stopThread() {
        stopped = true;
    }

    public void pauseThread() {
        this.paused = !this.paused;
    }

    public void run() {


        while (!stopped) {
            while (!paused) {
                try {
                    if (!client.isLoggedIn()) {
                        System.out.println("Not Logged In");
                        Thread.sleep(600);
                        continue;
                    }
                    if (client.isLocalPlayerAnimating()) {
                        System.out.println("We animating");
                        Thread.sleep(600);
                        continue;
                    }
                    List<LunNPC> npcs = client.getNpcsByName("Nechryael");
                    if (npcs == null) {
                        System.out.println("Starting instance");
                        client.startInstance();
                        Thread.sleep(1800);
                    } else {
                        LunNPC npc = npcs.get(0);
                        System.out.println("Attempting to attack NPC with index: " + npc.getIndex());
                        client.attack(npc);
                        Thread.sleep(600);
                    }
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}