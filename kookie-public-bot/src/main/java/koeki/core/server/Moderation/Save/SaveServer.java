package koeki.core.server.Moderation.Save;

import koeki.util.DatabaseUtil;
import koeki.util.Secrets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class SaveServer extends ListenerAdapter {

    private static JDA jda;
    private static String msgid;
    private static TextChannel tc;
    private static Message message;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        if(content.toLowerCase().startsWith("k@saveserver")) {
            message = channel.sendMessage("Connecting to the Database...").complete();
            tc = event.getChannel();

            if(DatabaseUtil.ConnectToDB("database.db")) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Connected to the Database!").complete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Do you want to save " + event.getGuild().getName() + "?").complete();
                    message.addReaction("✅").queue();
                    message.addReaction("❎").queue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Connecting failed!").complete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onGuildMessageReactionAdd (GuildMessageReactionAddEvent event){
        Guild guild = event.getGuild();
        if(event.getChannel() == tc) {
            if(event.getReactionEmote().getName().equals("✅") &&
                    !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
                event.getReaction().removeReaction(event.getUser()).queue();
                message.clearReactions().queue();

                long ServerID = Long.parseLong(event.getGuild().getId());
                String ServerName = event.getGuild().getName();
                int UserAnzahl = event.getGuild().getMemberCount();
                String ServerPrefix = "k!";

                if(DatabaseUtil.SaveServer(ServerID, ServerName, UserAnzahl, ServerPrefix)){
                    message.editMessage("Saving...").queue();
                    for(Member m : guild.getMembers()){
                        DatabaseUtil.SaveServerUser(guild.getIdLong(), m.getIdLong(), m.getUser().getName(), m.getUser().getDiscriminator());
                    }
                    message.editMessage(event.getGuild().getName() + " has been saved!").queue();
                    return;
                }else if(DatabaseUtil.flag == true){
                    message.editMessage("Server already saved! Please use k@updateserver").queue();
                    return;
                }else{
                    User owner = jda.getUserById(Secrets.OwnerID);
                    message.editMessage("Server couldn't be saved! Please message " + owner.getName() + "#" + owner.getDiscriminator() + ".").queue();
                    return;
                }
            }else if(event.getReactionEmote().getName().equals("❎") &&
                    !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
                event.getReaction().removeReaction(event.getUser()).complete();
                message.clearReactions().queue();
                message.editMessage("Saving of " + event.getGuild().getName() + " has been cancelled.").queue();
                return;
            }
        }
    }
}

