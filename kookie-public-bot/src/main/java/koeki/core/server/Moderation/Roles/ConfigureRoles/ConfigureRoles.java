package koeki.core.server.Moderation.Roles.ConfigureRoles;

import koeki.main.Main;
import koeki.util.DatabaseUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConfigureRoles extends ListenerAdapter {

//    private static Message message;
    private static JDA jda = Main.getBuilder();
    private static List<attributes> attributesList = new ArrayList<attributes>();
//    private static List<Message> messageList = new ArrayList<>();
    private static boolean AdminFlag = false;
    private static boolean ModFlag = false;
    private static boolean SuppFlag = false;

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        // CHECK IF MEMBER HAS ENOUGH PERMISSIONS
        // MANAGE_ROLES
        
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        Message message;
        long ServerID = event.getGuild().getIdLong();
        if(content.toLowerCase().equals("k@configureroles")){
            channel.sendTyping().complete();
            message = channel.sendMessage("Is there an Admin role available on your Server?").complete();
            message.addReaction("✅").queue();
            message.addReaction("❎").queue();
            attributes attributes = new attributes();
            long MessageID = message.getIdLong();
            attributes.setGuildID(ServerID);
            attributes.setMessageID(MessageID);
            attributesList.add(attributes);
//            System.out.println(guildList);
            return;
        }

        if(!event.getMessage().getMentionedRoles().isEmpty()){
            long messageID = 0;
            for(attributes a : attributesList){
                if(ServerID == a.getGuildID()){
                    messageID = a.getMessageID();

                    event.getMessage().delete().complete();
                    channel.editMessageById(messageID, "Thanks.").complete();
                    attributesList.remove(event.getGuild());
                    break;
                }
            }

//            AdminFlag = true;
//            // Put IF in for errors
//            if(AdminFlag == true){
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                    channel.editMessageById(message.getId(), "Please provide me your Moderator Role via. a ping.\n" +
//                            "If you're not able to ping the role, type " + prefix + "helpconfigureroles.").complete();
//                    ModFlag = true;
//                    // Put IF in for errors
//                    if(ModFlag == true);
//                        TimeUnit.SECONDS.sleep(5);
//                    channel.editMessageById(message.getId(), "Please provide me your Moderator Role via. a ping.\n" +
//                            "If you're not able to ping the role, type " + prefix + "helpconfigureroles.").complete();
//                    SuppFlag = true;
//                    if(SuppFlag){
//
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            guildList.remove(event.getGuild());
//            System.out.println(guildList);
            return;
        }
    }

    public void onGuildMessageReactionAdd (GuildMessageReactionAddEvent event){
        long messageID = 0;
        long ServerID = event.getGuild().getIdLong();
        MessageChannel channel = event.getChannel();
        if(event.getReactionEmote().getName().equals("✅") &&
                !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
            for(attributes a : attributesList){
                if(ServerID == a.getGuildID()){
                    messageID = a.getMessageID();
                    channel.sendTyping().complete();
                    event.getReaction().removeReaction(event.getUser()).queue();
                    String prefix = DatabaseUtil.getServerPrefix(ServerID);
                    channel.editMessageById(messageID, "Please provide me your Moderator Role via. a ping.\n" +
                            "If you're not able to ping the role, type " + prefix + "helpconfigureroles.").queue(message ->{
                                message.clearReactions().queue();
                    });
                    break;
                }
            }
        }
    }
}
