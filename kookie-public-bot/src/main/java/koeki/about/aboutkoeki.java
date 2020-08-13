package koeki.about;

import koeki.main.Main;
import koeki.util.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class aboutkoeki extends ListenerAdapter {

    private static long messageid;
    private static List<AK0attributes> AK0attributesList = new ArrayList<>();
    private static List<AK0attributes> AllowNewPostList = new ArrayList<>();
    private static int page;
    private static JDA jda = Main.getBuilder();
    private static Color color = Color.decode("#fffff");

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Message msg = event.getMessage();
        User owner = jda.getUserById(Secrets.OwnerID);
        Guild guild = event.getGuild();
        String content = msg.getContentRaw();
        MessageChannel msgchannel = event.getChannel();
        String channelid = msgchannel.getId();
        TextChannel channel = guild.getTextChannelById(channelid);
        Message message;

        if (content.toLowerCase().startsWith("k@aboutkoeki")) {
            AK0attributes AllowPost = new AK0attributes();
            if(!AllowNewPostList.isEmpty()){
                boolean flag = AllowNewPostList.get(0).isFlag();
                if(!flag){
                    AllowPost.setAvailable(true);
                    AllowPost.setGuildID(event.getGuild().getIdLong());
                    AllowNewPostList.add(AllowPost);
                    EmbedBuilder ebP1 = new EmbedBuilder();
                    ebP1.setAuthor("koeki's portfolio", null, null);
                    ebP1.setThumbnail(owner.getAvatarUrl());
                    ebP1.setImage("https://i.imgur.com/D6z0Qud.png");
                    ebP1.setTitle("Who am I?");
                    ebP1.setDescription("I am a hobby model designer, study computer science and you see me once in a while on Discord.");
                    ebP1.addField("What do you offer?", "User specialized bots and commands. You can use kookie but you can also get a complete handmade one from me.", true);
                    ebP1.addField("Where can I contact you?", "via Discord most likely. I'm also available through the linked social networks below.", true);
                    ebP1.addField("Do you charge any money?", "Yes, I do charge money. The amount is based on what services the bot is going to provide you and what I need to provide for you, to make everything running.", false);
                    ebP1.addField("Do you make any tutorials?", "Yes, I plan on doing some, for other people to learn how to make Discord Bots. This will most likely happen on YouTube.", true);
                    ebP1.addField("Social Media:", "Twitter: @koekiofficial", false);
                    ebP1.setColor(color);
                    ebP1.setFooter("Contact me: koeki#0002 - Page 1/4");
                    message = channel.sendMessage(ebP1.build()).complete();
                    message.addReaction("⬅️").queue();
                    message.addReaction("➡️").queue();
                    AK0attributes AK0attributes = new AK0attributes();
                    AK0attributes.setGuildID(event.getGuild().getIdLong());
                    AK0attributes.setMessageID(message.getIdLong());
                    AK0attributes.setPage(1);
                    page = AK0attributes.getPage();
                    AK0attributesList.add(AK0attributes);
                    Thread threadAvailableTimer = new Thread(() ->{
                        try{
                            TimeUnit.MINUTES.sleep(15);
                            AK0attributesList.clear();
                            AllowNewPostList.clear();
                            message.delete().complete();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    threadAvailableTimer.start();
                }else {
                    return;
                }
            }else {
                AllowPost.setAvailable(true);
                AllowPost.setGuildID(event.getGuild().getIdLong());
                AllowNewPostList.add(AllowPost);
                EmbedBuilder ebP1 = new EmbedBuilder();
                ebP1.setAuthor("koeki's portfolio", null, null);
                ebP1.setThumbnail(owner.getAvatarUrl());
                ebP1.setImage("https://i.imgur.com/D6z0Qud.png");
                ebP1.setTitle("Who am I?");
                ebP1.setDescription("I am a hobby model designer, study computer science and you see me once in a while on Discord.");
                ebP1.addField("What do you offer?", "User specialized bots and commands. You can use kookie but you can also get a complete handmade one from me.", true);
                ebP1.addField("Where can I contact you?", "via Discord most likely. I'm also available through the linked social networks below.", true);
                ebP1.addField("Do you charge any money?", "Yes, I do charge money. The amount is based on what services the bot is going to provide you and what I need to provide for you, to make everything running.", false);
                ebP1.addField("Do you make any tutorials?", "Yes, I plan on doing some, for other people to learn how to make Discord Bots. This will most likely happen on YouTube.", true);
                ebP1.addField("Social Media:", "Twitter: @koekiofficial", false);
                ebP1.setColor(color);
                ebP1.setFooter("Contact me: koeki#0002 - Page 1/4");
                message = channel.sendMessage(ebP1.build()).complete();
                message.addReaction("⬅️").queue();
                message.addReaction("➡️").queue();
                AK0attributes AK0attributes = new AK0attributes();
                AK0attributes.setGuildID(event.getGuild().getIdLong());
                AK0attributes.setMessageID(message.getIdLong());
                AK0attributes.setPage(1);
                page = AK0attributes.getPage();
                AK0attributesList.add(AK0attributes);
                Thread threadAvailableTimer = new Thread(() ->{
                    try{
                        TimeUnit.MINUTES.sleep(15);
                        AK0attributesList.clear();
                        AllowNewPostList.clear();
                        message.delete().complete();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                threadAvailableTimer.start();
            }
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        Guild guild = event.getGuild();
        MessageChannel msgchannel = event.getChannel();
        String channelid = msgchannel.getId();
        TextChannel channel = guild.getTextChannelById(channelid);
        User owner = jda.getUserById(Secrets.OwnerID);

        EmbedBuilder ebP1 = new EmbedBuilder();
        ebP1.setAuthor("koeki's portfolio", null, null);
        ebP1.setThumbnail(owner.getAvatarUrl());
        ebP1.setImage("https://i.imgur.com/5lwJJ7E.png");
        ebP1.setTitle("Who am I?");
        ebP1.setDescription("I am a hobby model designer, study computer science and you see me once in a while on Discord.");
        ebP1.addField("What do you offer?", "User specialized bots and commands. You can use kookie but you can also get a complete handmade one from me.", true);
        ebP1.addField("Where can I contact you?", "via Discord most likely. I'm also available through the linked social networks below.", true);
        ebP1.addField("Do you charge any money?", "Yes, I do charge money. The amount is based on what services the bot is going to provide you and what I need to provide for you, to make everything running.", false);
        ebP1.addField("Do you make any tutorials?", "Yes, I plan on doing some, for other people to learn how to make Discord Bots. This will most likely happen on YouTube.", true);
        ebP1.addField("Social Media:", "Twitter: @koekiofficial", false);
        ebP1.setColor(color);
        ebP1.setFooter("Contact me: koeki#3844 - Page 1/4");

        EmbedBuilder ebP2 = new EmbedBuilder();
        ebP2.setAuthor("koeki's portfolio", null, null);
        ebP2.setThumbnail(owner.getAvatarUrl());
        ebP2.setImage("https://i.imgur.com/5lwJJ7E.png");
        ebP2.setTitle("Useful Information");
        ebP2.addField("About kookie, the bot.", "kookie was originally made by me to just gather some studies on Discord Bots. After a lot of practice it turned out that I wanted to keep on doing this." +
                " This eventually lead me to today, where I sell user specific bots and commands.", true);
        ebP2.addField("Pricing's of the Bots.", "As I already said on the site before, I sell bots. The prices vary from what you want from me. That's why I can't really give any specific money amounts, yet this" +
                " would also be against my TOS, since I don't give out any Client/User Information.", true);
        ebP2.setFooter("Contact me: koeki#3844 - Page 2/4");
        ebP2.setColor(color);

        EmbedBuilder ebP3 = new EmbedBuilder();
        ebP3.setAuthor("koeki's portfolio", null, null);
        ebP3.setThumbnail(owner.getAvatarUrl());
        ebP3.setImage("https://i.imgur.com/5lwJJ7E.png");
        ebP3.setTitle("Bot selling; Commands and Functions.");
        ebP3.setDescription("Since kookie is basically a Public Bot, I still want to make money off of it.");
        ebP3.addField("Customizable Commands?", "User customizable commands basically means that you are able to reach out to me, via. Discord for example and Invite me to your Server, where I can customize some Commands and Functions for your Server.", true);
        ebP3.addField("Self-defined Functions?", "Self-defined Functions are a things, which basically allows you to e.g. Save your Server in my Database, for advanced Support.", false);
        ebP3.addField("Actual Bot selling", "As I already stated, I do sell Bots. It's always a pleasure to me, to customize a whole new Bot with all the Client wants it to have. So feel free to discuss it with me, if you're interested.", false);
        ebP3.setColor(color);
        ebP3.setFooter("Contact me: koeki#3844 - Page 3/4");

        EmbedBuilder ebP4 = new EmbedBuilder();
        ebP4.setAuthor("koeki's portfolio", null, null);
        ebP4.setThumbnail(owner.getAvatarUrl());
        ebP4.setImage("https://i.imgur.com/5lwJJ7E.png");
        ebP4.setTitle("Support me!");
        ebP4.addField("Patreon:", "N/A", true);
        ebP4.addField("PayPal:", "N/A", true);
        ebP4.addField("Ko-Fi:", "N/A", true);
        ebP4.setFooter("Contact me: koeki#3844 - Page 4/4");
        ebP4.setColor(color);

        AK0attributes AK0attributes = new AK0attributes();
        AK0attributes.setGuildID(event.getGuild().getIdLong());
        AK0attributes.setMessageID(event.getReaction().getMessageIdLong());
        AK0attributes.setPage(page);
        AK0attributesList.add(AK0attributes);

        for(AK0attributes a : AK0attributesList){
            if(event.getGuild().getIdLong() == a.getGuildID() && event.getReaction().getMessageIdLong() == a.getMessageID()){
                if (event.getReactionEmote().getName().equals("➡️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 1 &&
                        !event.getMember().getUser().isBot()) {
                    // PAGE 1 TO 2
                    event.getReaction().removeReaction(event.getUser()).queue();
                    messageid = a.getMessageID();
                    page = a.getPage() + 1;
                    a.setPage(page);
                    channel.editMessageById(messageid, ebP2.build()).complete();
                    AK0attributesList.remove(event.getGuild().getIdLong());
                    return;
                } else if (event.getReactionEmote().getName().equals("➡️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 2 &&
                        !event.getMember().getUser().isBot()) {
                    // PAGE 2 TO 3
                    event.getReaction().removeReaction(event.getUser()).queue();
                    messageid = a.getMessageID();
                    page = a.getPage() + 1;
                    a.setPage(page);
                    channel.editMessageById(messageid, ebP3.build()).complete();
                    AK0attributesList.remove(event.getGuild().getIdLong());
                    return;
                } else if(event.getReactionEmote().getName().equals("➡️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 3 &&
                        !event.getMember().getUser().isBot()){
                    //PAGE 3 TO 4
                    event.getReaction().removeReaction(event.getUser()).complete();
                    messageid = a.getMessageID();
                    page = a.getPage() + 1;
                    a.setPage(page);
                    channel.editMessageById(messageid, ebP4.build()).complete();
                    AK0attributesList.remove(event.getGuild().getIdLong());
                    return;
                } else if (event.getReactionEmote().getName().equals("⬅️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 2 &&
                        !event.getMember().getUser().isBot()) {
                    event.getReaction().removeReaction(event.getUser()).complete();
                    messageid = a.getMessageID();
                    page = a.getPage() - 1;
                    a.setPage(page);
                    channel.editMessageById(messageid, ebP1.build()).complete();
                    AK0attributesList.remove(event.getGuild().getIdLong());
                    return;
                } else if (event.getReactionEmote().getName().equals("⬅️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 3 &&
                        !event.getMember().getUser().isBot()) {
                    // PAGE 3 TO 2
                    event.getReaction().removeReaction(event.getUser()).queue();
                    messageid = a.getMessageID();
                    page = a.getPage() - 1;
                    a.setPage(page);
                    channel.editMessageById(messageid, ebP2.build()).complete();
                    AK0attributesList.remove(event.getGuild().getIdLong());
                    return;
                } else if(event.getReactionEmote().getName().equals("⬅️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 4 &&
                        !event.getMember().getUser().isBot()){
                    event.getReaction().removeReaction(event.getUser()).queue();
                    messageid = a.getMessageID();
                    page = a.getPage() - 1;
                    a.setPage(page);
                    channel.editMessageById(messageid, ebP3.build()).complete();
                    AK0attributesList.remove(event.getGuild().getIdLong());
                    return;
                } else if (event.getReactionEmote().getName().equals("⬅️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 1 &&
                        !event.getMember().getUser().isBot()) {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    return;
                    // Dead End, basically
                } else if(event.getReactionEmote().getName().equals("➡️") &&
                        !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && a.getPage() == 4 &&
                        !event.getMember().getUser().isBot()){
                    event.getReaction().removeReaction(event.getUser()).queue();
                    return;
                }
            }
        }
    }
}
//}
