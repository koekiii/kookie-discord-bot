package koeki.core.Server.Moderation;

import koeki.util.DatabaseUtil;
import koeki.util.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ServerStatus extends ListenerAdapter {

    private static JDA jda;
    private static Message message;
    private static EmbedBuilder eb;
    private static TextChannel tc;

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){
        // DELETE
        if(!event.getAuthor().getId().equals(Secrets.OwnerID)){
            return;
        }

        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        long ServerID = event.getGuild().getIdLong();
        Guild guild = event.getGuild();
        Member member = event.getMember();


//        tc.equals(channel);

        if(content.toLowerCase().startsWith("k@serverstatus")) {
            List<Member> memberList = new ArrayList<>();

            for (Member m : event.getGuild().getMembers()) {
                if (!m.getUser().isBot()) {
                    memberList.add(m);
                }
            }
            int MemberCount = memberList.size();
            eb = new EmbedBuilder();
            eb.setAuthor("Server Status");
            String CreationTime = String.valueOf(guild.getTimeCreated());
            String year = CreationTime.substring(0, 10);
            String time = CreationTime.substring(11, 19);
            eb.addField("Server Creation Date\n and Time:", year + " " + time, true);
            eb.addField("Last time updated:", DatabaseUtil.ServerLastTimeUpdated(ServerID), true);
            eb.addField("Member Count:", String.valueOf(MemberCount), true);
            if (DatabaseUtil.getServerPrefix(ServerID) != null) {
                eb.addField("Server User Prefix:", DatabaseUtil.getServerPrefix(ServerID), true);
            } else {
                eb.addField("Server User Prefix:", " ", true);
            }
            eb.addField("Current Owner:", guild.getOwner().getUser().getName(), true);
//            eb.addBlankField(true);
            eb.addField("Role Amount", String.valueOf(guild.getRoles().size()), true);

            List<String> memberRoleAdmin = new ArrayList<>();
            Role admin = guild.getRoleById("671412787573948493");
            for (Member m : guild.getMembers()) {
                if (m.getRoles().contains(admin)) {
                    memberRoleAdmin.add(m.getUser().getName());
                }
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < memberRoleAdmin.size(); i++) {
                if (i == memberRoleAdmin.size() - 1) {
                    sb.append(memberRoleAdmin.get(i));
                } else {
                    sb.append(memberRoleAdmin.get(i) + ", ");
                }
            }
            if (!sb.toString().isEmpty()) {
                eb.addField("Admins:", sb.toString(), true);
            } else {
                eb.addField("Admins:", "If you can read this, please configure your Admin Role w/ k@configureroles", true);
            }
            eb.addField("Moderators:", "If you can read this, please configure your Moderator Role w/ k@configureroles", true);
            eb.addField("Supporter:", "If you can read this, please configure your Supporter Role w/ k@configureroles", true);
            eb.setFooter("For questions, find my contacts via. k!aboutkoeki.");

//            int i = 0;
//            int AdminCount = memberRoleAdmin.size();
//            while(AdminCount > 0 && AdminCount < AdminCount + 1){
//                i++;
//                System.out.println(1);
//               // String Admins = memberRoleAdmin.get(i);
//            }

//            System.out.println(memberRoleAdmin.get(1));
//            eb.addField("Admins:", String.valueOf(sb), true);
            eb.setThumbnail(event.getGuild().getIconUrl());
//            if(guild.getBannerUrl().isEmpty()){
//                System.out.println("test");
//            }else{
//                eb.setImage(guild.getBannerUrl());
//            }
            if (!DatabaseUtil.ServerStatus(event.getGuild().getIdLong())) {
                eb.setDescription(event.getGuild().getName() + " needs to be saved or updated.");
                eb.setColor(Color.red);
            } else {
                eb.setDescription(event.getGuild().getName() + " is currently saved.");
                eb.setColor(Color.green);
            }
            message = channel.sendMessage(eb.build()).complete();
        }
    }
}
