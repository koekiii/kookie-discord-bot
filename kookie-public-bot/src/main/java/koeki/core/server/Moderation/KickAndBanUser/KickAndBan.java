package koeki.core.server.Moderation.KickAndBanUser;

import koeki.main.Main;
import koeki.util.DatabaseUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class KickAndBan extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();
    private static List<KABAttributes> attributesList = new ArrayList<>();
    private static List<Long> memberList = new ArrayList<>();
    private static List<Long> userList = new ArrayList<>();
    private static List<String> reasonList = new ArrayList<>();
    private static List<Long> messageList = new ArrayList<>();
    private static boolean ban = false;

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){
        long ServerID = DatabaseUtil.getServerID(event.getGuild().getIdLong());
        KABAttributes attributes = new KABAttributes();
        int integerMemberBan = 0;
        int integerUserBan = 0;
        int integerReasonBan = 0;
        int integerMessageBan = 0;

        int integerMemberKick = 0;
        int integerUserKick = 0;
        int integerReasonKick = 0;
        int integerMessageKick = 0;

        User user = null;
        Member member = null;
        String reasonBan = "";
        String reasonKick = "";
        Message message;
        Message msg = event.getMessage();
        String content  = msg.getContentRaw();
        try{
            if(content.startsWith("k@kick")){
                if(event.getMember().hasPermission(Permission.KICK_MEMBERS) || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if(ServerID != 0){
                        if(!msg.getMentionedUsers().isEmpty()){


                            String command = content.substring(0, 7);
                            try{
                                member = msg.getMentionedMembers().get(0);
                                user = msg.getMentionedUsers().get(0);
                            }catch (IndexOutOfBoundsException e){ }

                            memberList.add(member.getIdLong());
                            if(memberList.contains(member.getIdLong())){
                                integerMemberKick = memberList.indexOf(member.getIdLong());
                            }

                            userList.add(user.getIdLong());
                            if(userList.contains(user.getIdLong())){
                                integerUserKick = userList.indexOf(user.getIdLong());
                            }

                            try{
                                reasonKick = content.substring(29);
                            }catch (StringIndexOutOfBoundsException e){
                                event.getMessage().delete().complete();
                                event.getChannel().sendMessage("Please provide me a reason, " + event.getMember().getAsMention() + "!").complete();
                                return;
                            }

                            reasonList.add(reasonKick);
                            if(reasonList.contains(reasonKick)){
                                integerReasonKick = reasonList.indexOf(reasonKick);
                            }

                            attributes.setUserID(user.getIdLong());
                            attributes.setGuildID(event.getGuild().getIdLong());


                            user = jda.getUserById(userList.get(integerUserKick));
                            String finalReasonKick = reasonKick;
                            user.openPrivateChannel().queue(privateChannel -> {
                                privateChannel.sendMessage("You have been kicked from " + event.getGuild().getName() + ", by " + event.getMember().getUser().getName() + " for the following reason(s): " +
                                        "\n" +
                                        "\n" + finalReasonKick +
                                        "\n" +
                                        "\nPlease think about your behavior next time!").queue();
                            });
                            event.getChannel().sendMessage("User " + user.getName() + " has been kicked from " + event.getGuild().getName() + ", " + event.getMember().getAsMention() + "!" +
                                    "\nReason: " + reasonKick).complete();
                            event.getGuild().kick(member).complete();
                        }else{
                            event.getChannel().sendMessage("Please provide me a user via. a mention, " + event.getMember().getAsMention() + "!").complete();
                            return;
                        }
                    } else{
                        event.getChannel().sendMessage("Your Server isn't available in my database! Please make sure to save it, to use my commands, " + event.getMember().getAsMention() + "!").complete();
                        return;
                    }
                } else {
                    event.getChannel().sendMessage("You don't have enough permissions for that command, " + event.getMember().getAsMention() + "!").complete();
                    return;
                }
            }else if(content.startsWith("k@ban")){
                if(event.getMember().hasPermission(Permission.BAN_MEMBERS) || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if(ServerID != 0){
                        if(!msg.getMentionedUsers().isEmpty()){

                            String command = content.substring(0, 7);
                            try{
                                user = msg.getMentionedUsers().get(0);
                            }catch (IndexOutOfBoundsException e){ }

                            userList.add(user.getIdLong());
                            if(userList.contains(user.getIdLong())){
                                integerUserBan = userList.indexOf(user.getIdLong());
                            }

                            try{
                                reasonBan = content.substring(29);
                            }catch (StringIndexOutOfBoundsException e){
                                event.getMessage().delete().complete();
                                event.getChannel().sendMessage("Please provide me a reason, " + event.getMember().getAsMention() + "!").complete();
                                return;
                            }

                            reasonList.add(reasonBan);
                            if(reasonList.contains(reasonBan)){
                                integerReasonBan = reasonList.indexOf(reasonBan);
                            }

                            attributes.setUserID(user.getIdLong());
                            attributes.setGuildID(event.getGuild().getIdLong());

                            msg.delete().complete();
                            message = event.getChannel().sendMessage("For how long do you want to ban " + user.getName() + ", " + event.getMember().getAsMention() + "?").complete();
                            messageList.add(message.getIdLong());
                            if(messageList.contains(message.getIdLong())){
                                integerMessageBan = messageList.indexOf(message.getIdLong());
                            }
                            attributes.setAuthorID(event.getMember().getIdLong());
                            attributes.setUserID(user.getIdLong());
                            attributes.setUserMessageID(event.getMember().getIdLong());
                            attributesList.add(attributes);
                            memberList.add(event.getMember().getIdLong());
                            if(memberList.contains(attributes.getAuthorID())){
                                integerMemberBan = memberList.indexOf(attributes.getAuthorID());
                            }

                            ban = true;
                            return;
                        }else{
                            event.getMessage().delete().complete();
                            event.getChannel().sendMessage("Please provide me a user via. a mention, " + event.getMember().getAsMention() + "!").complete();
                            return;
                        }
                    } else{
                        event.getMessage().delete().complete();
                        event.getChannel().sendMessage("Your Server isn't available in my database! Please make sure to save it, to use my commands, " + event.getMember().getAsMention() + "!").complete();
                        return;
                    }
                } else {
                    event.getMessage().delete().complete();
                    event.getChannel().sendMessage("You don't have enough permissions for that command, " + event.getMember().getAsMention() + "!").complete();
                    return;
                }
            }else if(event.getMember().getIdLong() == memberList.get(integerMemberBan) && ban){
                for(KABAttributes a : attributesList){
                    if(a.getGuildID() == event.getGuild().getIdLong()){
                        long id = messageList.get(integerMessageBan);
                        user = jda.getUserById(String.valueOf(userList.get(integerUserBan)));
                        try{
                            if(Integer.parseInt(content) >= 8){
                                event.getMessage().delete().complete();
                                message = event.getChannel().editMessageById(id,"Don't you think banning " + user.getName() + " for " + content + " days is a bit too harsh, " + event.getMember().getAsMention() + "?" +
                                        "\n*Note: It's impossible to ban for more than 7 days.*").complete();
                                attributesList.remove(a);
                                memberList.remove(event.getMember());
                                reasonList.remove(reasonBan);
                                messageList.remove(message.getIdLong());
                                userList.remove(user);
                                ban = false;
                                return;
                            }else{
                                event.getMessage().delete().complete();
                                a.setDays(Integer.parseInt(content));
                                String finalReason = reasonList.get(integerReasonBan);

                                user.openPrivateChannel().queue(privateChannel ->{
                                    privateChannel.sendMessage("You have been banned from " + event.getGuild().getName() + ", for " + a.getDays() + " days, by " + event.getMember().getUser().getName() + " for the following reason(s): " +
                                            "\n" +
                                            "\n" + finalReason +
                                            "\n" +
                                            "\nPlease think about your behavior next time!").queue();
                                });
                                message = event.getChannel().editMessageById(id,"User " + user.getName() + " has been banned from " + event.getGuild().getName() + " for " + a.getDays() + " days, " + event.getMember().getAsMention() + "!" +
                                        "\nReason: " + finalReason).complete();

                                event.getGuild().ban(user, a.getDays(), finalReason).complete();

                                attributesList.remove(a);
                                memberList.remove(event.getMember());
                                reasonList.remove(reasonBan);
                                messageList.remove(message.getIdLong());
                                userList.remove(user);
                                ban = false;
                                return;
                            }
                        }catch (NumberFormatException e){
                            event.getMessage().delete().complete();
                            event.getChannel().sendMessage("Please give me an actual amount of days, " + event.getMember().getAsMention() + "!").complete();
                            ban = false;
                            return;
                        }
                    }else{
                        event.getMessage().delete().complete();
                        event.getChannel().sendMessage("Critical Error!").complete();
                        ban = false;
                        return;
                    }
                }
            }else {
                return;
            }
        }catch (IndexOutOfBoundsException e){

        }
    }
}
