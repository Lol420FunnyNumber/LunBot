import api.BotBase;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.necrotic.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class BotClient extends JFrame{
    private JButton pauseButton;
    private JPanel panel1;
    private BotThread botThread;

    public BotClient() {
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botThread.pauseThread();
                if (pauseButton.getText() == "Start") {
                    pauseButton.setText("Pause");
                } else {
                    pauseButton.setText("Start");
                }
            }
        });
    }
    public void runBot(String[] args) throws NotFoundException, CannotCompileException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("org.necrotic.client.Client");
        Class c = cc.toClass();
        Client client = (Client) c.getDeclaredConstructor().newInstance();
        client.main(args);
        BotThread botThread = new BotThread(new BotBase(client.instance));
        setTitle("BotClient");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        botThread.start();
        botThread.pauseThread();
        this.botThread = botThread;
        System.out.println("Bot started!");
    }

    public static void main(String[] args) throws CannotCompileException, NotFoundException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BotClient botClient = new BotClient();
        botClient.runBot(args);
    }
}
