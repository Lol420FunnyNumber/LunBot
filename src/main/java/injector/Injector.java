package injector;

import javassist.*;

import java.io.IOException;
import java.util.Arrays;

public class Injector {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("org.necrotic.client.Client");
        CtMethod m = cc.getDeclaredMethod("doAction");
        m.setModifiers(Modifier.PUBLIC);
        m.insertBefore("{ System.out.println(\"Menu Action ID/Action: \" + this.menuActionID[actionId] + \" cmd1/nodeId: \" + this.menuActionCmd1[actionId] + \" cmd2/slot: \" + this.menuActionCmd2[actionId] + \" cmd3/interfaceId: \" + this.menuActionCmd3[actionId] + \" cmd4/fourtnMenuAction: \" + this.menuActionCmd4[actionId]); }");
        CtField[] fields = cc.getDeclaredFields();
        String[] fieldsToInject = {"menuActionID", "menuActionCmd1", "menuActionCmd2", "menuActionCmd3", "menuActionCmd4"};
        for (CtField field : fields) {
            if (Arrays.asList(fieldsToInject).contains(field.getName())) {
                field.setModifiers(Modifier.PUBLIC);
            }
        }
        CtClass nodeClass = cp.get("org.necrotic.client.cache.node.Node");
        CtField nodeId = nodeClass.getDeclaredField("id");
        nodeId.setModifiers(Modifier.PUBLIC);

        CtClass interfaceClass = cp.get("org.necrotic.client.RSInterface");
        CtField valueField = interfaceClass.getDeclaredField("valueIndexArray");
        valueField.setModifiers(Modifier.PUBLIC);


        interfaceClass.writeFile(".");
        nodeClass.writeFile(".");
        cc.writeFile(".");
    }
}
