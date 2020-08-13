package koeki.core.server.Moderation.Update;

import koeki.main.Main;
import koeki.util.DatabaseUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class ServerUpdate extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();
    private static TextChannel tc;
    private static Message message;

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();

        if(content.toLowerCase().startsWith("k@updateserver")){
            message = channel.sendMessage("Connecting to the Database...").complete();
            tc = event.getChannel();

            if(DatabaseUtil.ConnectToDB("D:/Discord/kookie-public-bot/src/main/java/koeki/util/database.db")) {
                try{
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Connected to the Database!").complete();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Do you want to update " + event.getGuild().getName() + "?").complete();
                    message.addReaction("✅").queue();
                    message.addReaction("❎").queue();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    message.editMessage("Connection failed!").complete();
                    return;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

//    public void onGuildMessageReactionAdd (GuildMessageReactionAddEvent event){
//        Guild guild = event.getGuild();
//        if(event.getChannel() == tc){
//            if(event.getReactionEmote().getName().equals("✅") &&
//                !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
//                event.getReaction().removeReaction(event.getUser()).complete();
//                message.clearReactions().complete();
//
//                long ServerID = Long.parseLong(event.getGuild().getId());
//                String ServerName = event.getGuild().getName();
//                int MemberCount = event.getGuild().getMemberCount();
//                String ServerPrefix = DatabaseUtil.getServerPrefix(ServerID);
//
//
//                if(DatabaseUtil.UpdateServer(ServerID, ServerName, MemberCount, ServerPrefix)){
//
//                }
//            }
//        }
//    }
}
