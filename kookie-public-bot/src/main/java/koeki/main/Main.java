package koeki.main;

import koeki.Pluginsystem.OpenPluginSystem.openPluginSystem;
import koeki.about.aboutkoeki;
import koeki.core.server.JoinEvents.JoinedNewServer.BotJoinEvent;
import koeki.core.server.JoinEvents.UserJoinedServer.UserJoinEvent;
import koeki.core.server.Moderation.EditUserPrefix.EditUserPrefix;
import koeki.core.server.Moderation.MuteUser.MuteUser;
import koeki.core.server.Moderation.Roles.ConfigureRoles.ConfigureRoles;
import koeki.core.server.Moderation.Roles.UpdateRole.UpdateRole;
import koeki.core.server.Moderation.Save.SaveServer;
import koeki.core.server.Moderation.Status.ServerStatus;
import koeki.core.server.Moderation.SuggestionSystem.InitiateSuggestion;
import koeki.core.server.Moderation.Update.ServerUpdate;
import koeki.util.DatabaseUtil;
import koeki.util.Secrets;
import koeki.util.shutdown;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

//import koeki.core.Server.Moderation.KickAndBanUser.KickAndBan;

public class Main {

    private static JDA builder;

    public static void main(String[] args) {
        if(DatabaseUtil.ConnectToDB("database.db")) {
            try {
                System.out.println("Connection to SQLite has been established.");
                builder = new JDABuilder(Secrets.token).build().awaitReady();
                Thread PresenceLoop = new Thread(() -> {
                    try {
                        while(true){
                            int servers = builder.getGuilds().size();
                            builder.getPresence().setActivity(Activity.watching(servers + " Servers."));
                            TimeUnit.MINUTES.sleep(5);
                            builder.getPresence().setActivity(Activity.playing(" with the databases."));
                            TimeUnit.MINUTES.sleep(5);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                PresenceLoop.start();

                builder.addEventListener(new aboutkoeki(), new ServerStatus(), new SaveServer(), new EditUserPrefix(),
                        new BotJoinEvent(), new ConfigureRoles(), new UserJoinEvent(), new MuteUser(),
                        new UpdateRole(), new ServerUpdate(), new test(), new shutdown());
                // Pluginsystem
                builder.addEventListener(new openPluginSystem());

                // SuggestionSystem
                builder.addEventListener(new InitiateSuggestion());

                // Client based commands
                // RoLife
//                builder.addEventListener(new RL0GameUpdate(), new RL0VerifySystem(), new RL0JoinEvent(),
//                        new RL0ContentFilter());

//                User owner = builder.getUserById(Secrets.OwnerID);
//                owner.openPrivateChannel().queue(privateChannel -> {
//                    privateChannel.sendMessage("Bot is ready.").queue();
//                });
//                RunJSBot.RunJSBot();
            } catch (LoginException | InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Connection failed.");
        }
    }

    public static JDA getBuilder() {
        return builder;
    }
}
