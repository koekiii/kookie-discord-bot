//package koeki.core.Server.Moderation.MuteUser;
//
//import koeki.main.Main;
//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.Permission;
//import net.dv8tion.jda.api.entities.*;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//
//import javax.annotation.Nonnull;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MuteUser extends ListenerAdapter {
//
//    private static JDA jda = Main.getBuilder();
//    private static List<Role> roleList = new ArrayList<Role>();
//    private static List<MUAttributes> muteList = new ArrayList<>();
//    private static List<String> guildIDList = new ArrayList<>();
//    private static List<String> userIDList = new ArrayList<>();
//    private static Role muteRole;
//
//    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
//        Guild guild = jda.getGuildById(event.getGuild().getId());
//        String content = event.getMessage().getContentRaw();
//        MUAttributes attributes = new MUAttributes();
//        if(event.getMember().hasPermission(Permission.KICK_MEMBERS) || event.getMember().hasPermission(Permission.ADMINISTRATOR)){
//            roleList = guild.getRolesByName("Muted", true);
//            if(content.startsWith("k@mute")){
//                try{
//                    if(roleList.isEmpty()){
//                        muteRole = guild.createRole()
//                                .setName("Muted")
//                                .setColor(Color.GRAY)
//                                .setPermissions(Permission.MESSAGE_HISTORY)
//                                .complete();
//                        roleList.add(muteRole);
//                        String userid = content.substring(10, 28);
//                        String reason = content.substring(30);
//                        Member member = guild.getMemberById(userid);
//                        if(reason.isEmpty()){
//                            event.getChannel().sendMessage("Please provide me a reason!").complete();
//                            return;
//                        }else {
//                            event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", has been muted, for: " + reason + "!").complete();
//                            if(!muteList.contains(event.getGuild().getId())){
//                                attributes.setGuildID(event.getGuild().getId());
//                                attributes.setUserID(member.getId());
//                                muteList.add(attributes);
//                                guildIDList.add(attributes.getGuildID());
//                                userIDList.add(attributes.getUserID());
//                                int indexGuild = guildIDList.indexOf(event.getGuild().getId());
//                                Guild g = jda.getGuildById(String.valueOf(guildIDList.get(indexGuild)));
//                                User user = jda.getUserById(member.getUser().getId());
//                                int indexMember = userIDList.indexOf(user.getId());
//                                Member m = g.getMemberById(String.valueOf(userIDList.get(indexMember)));
//                                Thread threadMuteRemoveTimer = new Thread(()->{
//                                    try {
//                                        Thread.sleep(60000);
//                                        guild.removeRoleFromMember(m, muteRole).complete();
//                                        guildIDList.remove(attributes.getGuildID());
//                                        userIDList.remove(attributes.getUserID());
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//                                threadMuteRemoveTimer.start();
//                            }else{
//                                attributes.setGuildID(event.getGuild().getId());
//                                attributes.setUserID(member.getId());
//                                muteList.add(attributes);
//                                guildIDList.add(attributes.getGuildID());
//                                userIDList.add(attributes.getUserID());
//                                int indexGuild = guildIDList.indexOf(event.getGuild().getId());
//                                Guild g = jda.getGuildById(String.valueOf(guildIDList.get(indexGuild)));
//                                User user = jda.getUserById(member.getUser().getId());
//                                int indexMember = userIDList.indexOf(user.getId());
//                                Member m = g.getMemberById(String.valueOf(userIDList.get(indexMember)));
//                                Thread threadMuteRemoveTimer = new Thread(()->{
//                                    try {
//                                        Thread.sleep(60000);
//                                        guild.removeRoleFromMember(m, muteRole).complete();
//                                        guildIDList.remove(attributes.getGuildID());
//                                        userIDList.remove(attributes.getUserID());
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//                                threadMuteRemoveTimer.start();
//                            }
//                            guild.addRoleToMember(member, muteRole).complete();
//                            return;
//                        }
//                    }else {
//                        muteRole = roleList.get(0);
//                        try{
//                            String userid = content.substring(10, 28);
//                            String reason = content.substring(30);
//                            Member member = guild.getMemberById(userid);
//
//                            int i = member.getRoles().indexOf(muteRole);
//                            if(i < 0){
//                                event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", has been muted, for: " + reason + "!").complete();
//                                guild.addRoleToMember(member, muteRole).complete();
//                                if(!muteList.contains(event.getGuild().getId())){
//                                    attributes.setGuildID(event.getGuild().getId());
//                                    attributes.setUserID(member.getId());
//                                    muteList.add(attributes);
//                                    guildIDList.add(attributes.getGuildID());
//                                    userIDList.add(attributes.getUserID());
//                                    int indexGuild = guildIDList.indexOf(event.getGuild().getId());
//                                    Guild g = jda.getGuildById(String.valueOf(guildIDList.get(indexGuild)));
//                                    User user = jda.getUserById(member.getUser().getId());
//                                    int indexMember = userIDList.indexOf(user.getId());
//                                    Member m = g.getMemberById(String.valueOf(userIDList.get(indexMember)));
//                                    Thread threadMuteRemoveTimer = new Thread(()->{
//                                        try {
//                                            Thread.sleep(60000);
//                                            guild.removeRoleFromMember(m, muteRole).complete();
//                                            guildIDList.remove(attributes.getGuildID());
//                                            userIDList.remove(attributes.getUserID());
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                    });
//                                    threadMuteRemoveTimer.start();
//                                }else{
//                                    attributes.setGuildID(event.getGuild().getId());
//                                    attributes.setUserID(member.getId());
//                                    muteList.add(attributes);
//                                    guildIDList.add(attributes.getGuildID());
//                                    userIDList.add(attributes.getUserID());
//                                    int indexGuild = guildIDList.indexOf(event.getGuild().getId());
//                                    Guild g = jda.getGuildById(String.valueOf(guildIDList.get(indexGuild)));
//                                    User user = jda.getUserById(member.getUser().getId());
//                                    int indexMember = userIDList.indexOf(user.getId());
//                                    Member m = g.getMemberById(String.valueOf(userIDList.get(indexMember)));
//                                    Thread threadMuteRemoveTimer = new Thread(()->{
//                                        try {
//                                            Thread.sleep(60000);
//                                            guild.removeRoleFromMember(m, muteRole).complete();
//                                            guildIDList.remove(attributes.getGuildID());
//                                            userIDList.remove(attributes.getUserID());
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                    });
//                                    threadMuteRemoveTimer.start();
//                                }
//                                return;
//                            }else{
//                                event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", is already muted!").complete();
//                                return;
//                            }
//                        }catch (StringIndexOutOfBoundsException e){
//                            String userid = content.substring(10, 28);
//                            Member member = guild.getMemberById(userid);
//
//                            int i = member.getRoles().indexOf(muteRole);
//                            if(i < 0){
//                                event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", has been muted!").complete();
//                                guild.addRoleToMember(member, muteRole).complete();
//                                if(!muteList.contains(event.getGuild().getId())){
//                                    attributes.setGuildID(event.getGuild().getId());
//                                    attributes.setUserID(member.getId());
//                                    muteList.add(attributes);
//
//                                    guildIDList.add(attributes.getGuildID());
//                                    userIDList.add(attributes.getUserID());
//                                    int indexGuild = guildIDList.indexOf(event.getGuild().getId());
//                                    Guild g = jda.getGuildById(String.valueOf(guildIDList.get(indexGuild)));
//                                    User user = jda.getUserById(member.getUser().getId());
//                                    int indexMember = userIDList.indexOf(user.getId());
//                                    Member m = g.getMemberById(String.valueOf(userIDList.get(indexMember)));
//                                    Thread threadMuteRemoveTimer = new Thread(()->{
//                                        try {
//                                            Thread.sleep(60000);
//                                            guild.removeRoleFromMember(m, muteRole).complete();
//                                            guildIDList.remove(attributes.getGuildID());
//                                            userIDList.remove(attributes.getUserID());
//                                        } catch (InterruptedException e1) {
//                                        }
//                                    });
//                                    threadMuteRemoveTimer.start();
//                                }else{
//                                    attributes.setGuildID(event.getGuild().getId());
//                                    attributes.setUserID(member.getId());
//                                    muteList.add(attributes);
//                                    guildIDList.add(attributes.getGuildID());
//                                    userIDList.add(attributes.getUserID());
//                                    int indexGuild = guildIDList.indexOf(event.getGuild().getId());
//                                    Guild g = jda.getGuildById(String.valueOf(guildIDList.get(indexGuild)));
//                                    User user = jda.getUserById(member.getUser().getId());
//                                    int indexMember = userIDList.indexOf(user.getId());
//                                    Member m = g.getMemberById(String.valueOf(userIDList.get(indexMember)));
//                                    Thread threadMuteRemoveTimer = new Thread(()->{
//                                        try {
//                                            Thread.sleep(60000);
//                                            guild.removeRoleFromMember(m, muteRole).complete();
//                                            guildIDList.remove(attributes.getGuildID());
//                                            userIDList.remove(attributes.getUserID());
//                                        } catch (InterruptedException e2) {
//                                            e2.printStackTrace();
//                                        }
//                                    });
//                                    threadMuteRemoveTimer.start();
//                                }
//                                return;
//                            }else {
//                                event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", is already muted!").complete();
//                                return;
//                            }
//                        }
//                    }
//                }catch (NullPointerException e){ }
//            }else if(content.startsWith("k@unmute")){
//                muteRole = roleList.get(0);
//                try{
//                    String userid = content.substring(13, 31);
//                    String reason = content.substring(32);
//                    Member member = guild.getMemberById(userid);
//                    int i = member.getRoles().indexOf(muteRole);
//                    if(i == 0){
//                        event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", has been unmuted, for: " + reason + "!").complete();
//                        guild.removeRoleFromMember(member, muteRole).complete();
//                        return;
//                    }else{
//                        event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", is not muted!").complete();
//                        return;
//                    }
//                }catch (StringIndexOutOfBoundsException e){
//                    String userid = content.substring(12, 30);
//                    Member member = guild.getMemberById(userid);
//                    int i = member.getRoles().indexOf(muteRole);
//                    if(i != 0){
//                        event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", has been unmuted!").complete();
//                        guild.removeRoleFromMember(member, muteRole).complete();
//                        return;
//                    }else{
//                        event.getChannel().sendMessage(member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + ", is not muted!").complete();
//                        return;
//                    }
//                }
//            }
//        }else{
//            event.getChannel().sendMessage("You don't have enough Permissions!").complete();
//            return;
//        }
//    }
//}
