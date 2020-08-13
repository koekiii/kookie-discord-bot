package koeki.core.server.Moderation.SuggestionSystem;

import koeki.main.Main;
import koeki.util.DatabaseUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InitiateSuggestion extends ListenerAdapter {

    private static JDA builder = Main.getBuilder();
    private static List<String> ssList = new ArrayList<>();

    // Messages and Servers need to be linked
    private static List<Message> messageList = new ArrayList<>();
    private static List<String> serverIDList = new ArrayList<>();

    // Author and content need to be linked
    private static List<String> AuthorIDList = new ArrayList<>();
    private static List<String> contentList = new ArrayList<>();

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        String content = event.getMessage().getContentRaw();
        long ServerID = event.getGuild().getIdLong();
        String prefix = DatabaseUtil.getServerPrefix(ServerID);

        if(content.toLowerCase().equals(prefix + "suggestion")){
            SS0attributes attributes = new SS0attributes();
            attributes.setUserID(event.getAuthor().getId());
            ssList.add(attributes.getUserID());
            event.getMessage().delete().complete();
            User user = event.getAuthor();
            PrivateChannel privateChannel = user.openPrivateChannel().complete();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Suggestion");
            eb.setDescription("Please state your suggestion and send it.");
            privateChannel.sendMessage(eb.build()).queue();
            serverIDList.add(event.getGuild().getId());
            return;
        }else {
            return;
        }
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        SS0attributes attributes = new SS0attributes();
        attributes.setUserID(event.getAuthor().getId());
        String UserID = attributes.getUserID();

        if(ssList.contains(UserID)) {
            if (!event.getAuthor().isBot()) {
                int index = ssList.indexOf(UserID);
                String content = event.getMessage().getContentRaw();
                attributes.setSuggestion(content);
                ssList.add(index, attributes.getSuggestion());
                contentList.add(content);
                EmbedBuilder eb = new EmbedBuilder();
                Message message;
                User user = builder.getUserById(UserID);
                eb.setTitle(user.getName());
                eb.setDescription(content);
                eb.setAuthor("Suggestion", user.getAvatarUrl(), user.getAvatarUrl());
                TextChannel tc = builder.getTextChannelById("732958598153633824");
                message = tc.sendMessage(eb.build()).complete();
                message.addReaction("\uD83D\uDD3C").complete();
                message.addReaction("\uD83D\uDD3D").complete();
                message.addReaction("❌").complete();
                messageList.add(message);
                int indexAuthor = messageList.indexOf(message);
                AuthorIDList.add(indexAuthor, user.getId());
                ssList.clear();
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if(serverIDList.contains(event.getGuild().getId())) {
            if (event.getChannel().getId().equals("732958598153633824") || event.getChannel().getId().equals("741332312129798177")) {
                TextChannel tc = event.getChannel();
                String EmoteCase = event.getReactionEmote().getEmoji();
                if(!event.getMember().getUser().isBot()){
                    tc.retrieveMessageById(event.getMessageId()).queue((message -> {
                        int UpvoteCounter = message.getReactions().get(0).getCount();
                        int DownvoteCounter = message.getReactions().get(1).getCount();
                        switch (EmoteCase) {
                            case "\uD83D\uDD3C":
                                if(UpvoteCounter == 2 && !(DownvoteCounter >= UpvoteCounter)) {
                                    message.addReaction("✨").queue();
                                    System.out.println(message.getEmbeds().toString());
                                }
                                break;
                            case "\uD83D\uDD3D":
                                if(DownvoteCounter == 10 && !(UpvoteCounter >= DownvoteCounter)) {
                                    message.addReaction("✨").queue();
                                }
                                break;
                            case "❌":
                                int index = messageList.indexOf(message);
                                String AuthorID = AuthorIDList.get(index);
                                if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getMember().getUser().getId().equals(AuthorID)){
                                    message.delete().queue();
                                }
                                break;
                        }
                    }));
                }
            }
        }
    }
}
