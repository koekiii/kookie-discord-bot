package koeki.util;

import koeki.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class shutdown extends ListenerAdapter {

    private JDA builder = Main.getBuilder();

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if(event.getAuthor().getId().equals(Secrets.OwnerID)){
            String content = event.getMessage().getContentRaw();

            if(content.toLowerCase().equals("k@shutdown")){
                Guild guild = event.getJDA().getGuildById("731188670228332595");
                TextChannel tc = guild.getTextChannelById("734872751583526983");
                tc.sendMessage("I'm shutting down in 5 seconds!").complete();
                try{
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }else{
            return;
        }
    }
}
