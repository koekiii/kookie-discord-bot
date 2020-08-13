//package koeki.Pluginsystem.OpenPluginSystem;
//
//import koeki.main.Main;
//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.entities.Message;
//import net.dv8tion.jda.api.entities.TextChannel;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
//import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//
//import javax.annotation.Nonnull;
//import java.util.ArrayList;
//import java.util.List;
//
//public class openPluginSystem extends ListenerAdapter {
//
//    private static JDA builder = Main.getBuilder();
//    private static List<String> GuildID = new ArrayList<>();
//    private static List<String> MessageID = new ArrayList<>();
//    private static List<String> AuthorUserID = new ArrayList<>();
//    private static List<String> ChannelID = new ArrayList<>();
//
//    @Override
//    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
//        String content = event.getMessage().getContentRaw();
//        String authorid = event.getAuthor().getId();
//        AuthorUserID.add(authorid);
//        Message message;
//
//        if(content.equals("k@plugins")){
//            if(GuildID.contains(event.getGuild().getId())){
//                EmbedBuilder eb = new EmbedBuilder();
//                eb.setTitle("Plugins");
//                eb.setDescription("List of all settings.");
//                eb.addField("Utility", "React with: \uD83D\uDDC2", false);
//                eb.addField("Fun", "React with: \uD83C\uDF8A", false);
//                eb.addField("Other Settings", "React with: ⚙️", false);
//                eb.setFooter("Use the Reactions to navigate!");
//                message = event.getChannel().sendMessage(eb.build()).complete();
//                message.addReaction("\uD83D\uDDC2").complete();
//                message.addReaction("\uD83C\uDF8A").complete();
//                message.addReaction("⚙️").complete();
//                message.addReaction("❌").complete();
//                MessageID.add(message.getId());
//                ChannelID.add(event.getChannel().getId());
//                return;
//            }else{
//                GuildID.add(event.getGuild().getId());
//                EmbedBuilder eb = new EmbedBuilder();
//                eb.setTitle("Plugins");
//                eb.setDescription("List of all settings.");
//                eb.addField("Utility", "React with: \uD83D\uDDC2", false);
//                eb.addField("Fun", "React with: \uD83C\uDF8A", false);
//                eb.addField("Other Settings", "React with: ⚙️", false);
//                eb.setFooter("Use the Reactions to navigate!");
//                message = event.getChannel().sendMessage(eb.build()).complete();
//                message.addReaction("\uD83D\uDDC2").complete();
//                message.addReaction("\uD83C\uDF8A").complete();
//                message.addReaction("⚙️").complete();
//                message.addReaction("❌").complete();
//                MessageID.add(message.getId());
//                ChannelID.add(event.getChannel().getId());
//                return;
//            }
//        }
//    }
//
//    public void onGuildMessageReactionAdd (@Nonnull GuildMessageReactionAddEvent event){
//        String messageid = event.getReaction().getMessageId();
//        String authorid = event.getUser().getId();
//
//        EmbedBuilder ebP1 = new EmbedBuilder();
//        ebP1.setTitle("Plugins");
//        ebP1.setDescription("List of all settings.");
//        ebP1.addField("Utility", "React with: \uD83D\uDDC2", false);
//        ebP1.addField("Fun", "React with: \uD83C\uDF8A", false);
//        ebP1.addField("Other Settings", "React with: ⚙️", false);
//        TextChannel tc = builder.getTextChannelById(event.getChannel().getId());
//
//        ebP1.setFooter("Use the Reactions to navigate! - Page 1/4");
//
//        OPSAttributes attributes = new OPSAttributes();
//        if(ChannelID.contains(event.getChannel().getId())) {
//            if(MessageID.contains(messageid)){
//                if(AuthorUserID.contains(authorid)){
//                    attributes.setAuthorUserID(authorid);
//                    attributes.setMessageID(messageid);
//                    attributes.setChannelID(event.getChannel().getId());
//                    if(event.getReaction().getReactionEmote().getName().equals("❌")
//                            && !event.getMember().getUser().equals(event.getJDA().getSelfUser())
//                            && !event.getMember().getUser().isBot()){
//                        EmbedBuilder eb = new EmbedBuilder();
//                        eb.setTitle("Closed");
//                        eb.setDescription("Plugin system has been closed!");
//                        eb.setFooter("Type k@plugins to re-open.");
//                        Message message = tc.editMessageById(messageid, eb.build()).complete();
//                        message.clearReactions().complete();
//                        return;
//                    }else if(event.getReaction().getReactionEmote().getName().equals("\uD83D\uDDC2")
//                            && !event.getMember().getUser().equals(event.getJDA().getSelfUser())
//                            && !event.getMember().getUser().isBot()){
//                        EmbedBuilder eb = new EmbedBuilder();
//                        eb.setTitle("Utility");
//                        eb.setDescription("Navigate through the plugins with the reactions");
//                        eb.setFooter("Page 1/?");
//                        Message message = tc.editMessageById(messageid, eb.build()).complete();
//                        message.clearReactions().complete();
//                        message.addReaction("⬅️").complete();
//                        message.addReaction("❌").complete();
//                        message.addReaction("➡️").complete();
//                        return;
//                    }else if(event.getReaction().getReactionEmote().getName().equals("\uD83C\uDF8A")
//                            && !event.getMember().getUser().equals(event.getJDA().getSelfUser())
//                            && !event.getMember().getUser().isBot()){
//                        event.getReaction().removeReaction(event.getUser()).complete();
//                        EmbedBuilder eb = new EmbedBuilder();
//                        eb.setTitle("Closed");
//                        eb.setDescription("Plugin system has been closed!");
//                        eb.setFooter("Type k@plugins to re-open.");
//                        Message message = tc.editMessageById(messageid, eb.build()).complete();
//                        message.addReaction("⬅️").complete();
//                        message.addReaction("❌").complete();
//                        message.addReaction("➡️").complete();
//                        return;
//                    }else if(event.getReaction().getReactionEmote().getName().equals("⚙️")
//                            && !event.getMember().getUser().equals(event.getJDA().getSelfUser())
//                            && !event.getMember().getUser().isBot()){
//                        event.getReaction().removeReaction(event.getUser()).complete();
//                        EmbedBuilder eb = new EmbedBuilder();
//                        eb.setTitle("Other Settings");
//                        eb.setDescription("All available settings, besides the plugins");
//                        eb.addField("koeki's portfolio", "Turned: ON", true);
//                        eb.addField("Music Volume", "Volume: 10", true);
//                        eb.setFooter("Type k@plugins to re-open.");
//                        Message message = tc.editMessageById(messageid, eb.build()).complete();
//                        message.clearReactions().complete();
//                        message.addReaction("⬅️").complete();
//                        message.addReaction("❌").complete();
//                        message.addReaction("➡️").complete();
//                        return;
//                    }
//                    // NOT THE ORIGINAL AUTHOR
//                } else if(event.getReaction().getReactionEmote().getName().equals("❌")
//                        && !event.getMember().getUser().equals(event.getJDA().getSelfUser())
//                        && !event.getMember().getUser().isBot()){
//                    event.getReaction().removeReaction(event.getUser()).complete();
//                    ChannelID.remove(event.getChannel().getId());
//                    MessageID.remove(messageid);
//                    AuthorUserID.remove(authorid);
//                    return;
//                }
//            }
//        }
//    }
//}
