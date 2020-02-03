package koeki.core.Server.Moderation.EditUserPrefix;

import koeki.util.DatabaseUtil;
import koeki.util.Secrets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EditUserPrefix extends ListenerAdapter {

    private static Message message;
    private static String AuthorID = "";
    private static boolean flag = false;

    public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
        // CHECK IF MEMBER HAS ENOUGH PERMISSIONS

        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();
        Message msg = event.getMessage();
        String command = msg.getContentRaw();
        Guild guild = event.getGuild();
        long ServerID = guild.getIdLong();
//        String prefix = DatabaseUtil.getServerPrefix(ServerID);

        if (command.startsWith("k@edituserprefix")) {
            message = channel.sendMessage(author.getAsMention() + ", please enter your desired prefix!").complete();
            AuthorID = author.getId();
            flag = true;
            return;
        }
        Message contentmsg = event.getMessage();
        if (contentmsg.getAuthor().getId().equals(AuthorID) && flag == true) {
            String content = contentmsg.getContentRaw();
            if (content.length() < 4 && AuthorID.equals(author.getId()) && !content.equals(DatabaseUtil.getServerPrefix(ServerID))) {
                if (DatabaseUtil.SaveServerPrefix(content, ServerID)) {
                    event.getMessage().delete().complete();
                    message.editMessage("Prefix successfully saved, by " + author.getAsMention() + "!  **Current Prefix: " + DatabaseUtil.getServerPrefix(ServerID) + "**").complete();
                    flag = false;
                    return;
                }else{
                    event.getMessage().delete().complete();
                    message.editMessage("Prefix couldn't be saved, " + event.getAuthor().getAsMention() + ". Please update your Server using k@updateserver, if you're and Moderator or above.").complete();
                    flag = false;
                }
            }else if(content.length() < 4 && AuthorID.equals(author.getId()) && content.equals(DatabaseUtil.getServerPrefix(ServerID))){
                event.getMessage().delete().complete();
                message.editMessage("This is the current prefix, " + author.getAsMention() +".").complete();
            } else if (content.equals("exit") || content.equals("cancel") && event.getMessage().getAuthor().getId().equals(AuthorID)) {
                event.getMessage().delete().complete();
                message.editMessage("Prefix customization has been cancelled, by " + author.getAsMention() + ". **Current Prefix: " + DatabaseUtil.getServerPrefix(ServerID) + "**").complete();
                flag = false;
                return;
            } else if (content.length() > 3 && event.getMessage().getAuthor().getId().equals(AuthorID)) {
                event.getMessage().delete().complete();
                message.editMessage("Entered Prefix: **" + content + "**, is not a valid Prefix. Please try again, " + author.getAsMention()).complete();
                flag = false;
                return;
            }
        }
    }
}
