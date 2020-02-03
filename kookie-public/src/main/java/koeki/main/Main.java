package koeki.main;

import koeki.about.aboutkoeki;
import koeki.core.Client.RoLife.RL0GameUpdate;
import koeki.core.Server.JoinEvents.JoinedNewServer.BotJoinEvent;
import koeki.core.Server.JoinEvents.UserJoinedServer.UserJoinEvent;
import koeki.core.Server.Moderation.ConfigureRoles.ConfigureRoles;
import koeki.core.Server.Moderation.EditUserPrefix.EditUserPrefix;
import koeki.core.Server.Moderation.KickAndBanUser.KickAndBan;
import koeki.core.Server.Moderation.Save.SaveServer;
import koeki.core.Server.Moderation.Status.ServerStatus;
import koeki.util.DatabaseUtil;
import koeki.util.Secrets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    private static JDA builder;

    public static void main(String[] args) {
        if(DatabaseUtil.ConnectToDB("src/main/java/koeki/util/database.db")){
            try {
                System.out.println("Connection to SQLite has been established.");
                builder = new JDABuilder(Secrets.token).build().awaitReady();
                builder.addEventListener(new aboutkoeki(), new ServerStatus(), new SaveServer(), new EditUserPrefix(),
                new BotJoinEvent(), new ConfigureRoles(), new UserJoinEvent(), new KickAndBan());
                // Client based commands
                // RoLife
                builder.addEventListener(new RL0GameUpdate());
            } catch (LoginException | InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Connection failed");
        }
    }

    public static JDA getBuilder() {
        return builder;
    }
}
