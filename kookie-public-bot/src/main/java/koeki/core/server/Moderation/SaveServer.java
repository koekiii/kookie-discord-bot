package koeki.core.server.Moderation;

import koeki.util.DatabaseUtil;
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
//        MessageBuilder mb = new MessageBuilder();
        MessageChannel channel = event.getChannel();
//        Message send = mb.append("test").build();
        //String id = send.getId();
        //System.out.println(send.getContentRaw() + " : " + id);
        if(content.toLowerCase().startsWith("k@saveserver")) {
//            channel.sendMessage(send).complete();
            message = channel.sendMessage("Connecting to the Database...").complete();
            tc = event.getChannel();

            if(DatabaseUtil.ConnectToDB("D:/Discord/kookieSQL/src/main/java/koeki/util/database.db")) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Connected to the Database!").complete();
//                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                    Date date = new Date();
//                    System.out.println(formatter.format(date));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                msg.editMessage("Connected to the Database!").queue();
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

                }else if(!DatabaseUtil.SaveServer(ServerID, ServerName, UserAnzahl, ServerPrefix) && DatabaseUtil.flag != true){
                    message.editMessage("Saving..").queue();
                    for(Member m : guild.getMembers()){
                        DatabaseUtil.SaveServerUser(guild.getIdLong(), m.getIdLong(), m.getUser().getName(), m.getUser().getDiscriminator());
                    }
                    message.editMessage(event.getGuild().getName() + " has been updated!").queue();

                }else if(DatabaseUtil.flag == true){
                    message.editMessage("Server already saved!").queue();
                }else{
                    message.editMessage("Server couldn't be saved!").queue();
                }
            }else if(event.getReactionEmote().getName().equals("❎") &&
                    !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
                event.getReaction().removeReaction(event.getUser()).complete();
                message.clearReactions().queue();
                message.editMessage("Saving of " + event.getGuild().getName() + " has been cancelled.").queue();
            }
        }
    }
}

