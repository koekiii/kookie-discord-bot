package koeki.JSBot;

import java.io.IOException;

public class RunJSBot {

    public static void RunJSBot(){
        try {
            Runtime.getRuntime().exec("cmd /c start \"\" /src/main/java/koeki/JSBot/run.bat");
            System.out.println("JS-Bot running!");

            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
