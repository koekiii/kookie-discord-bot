package Event.Core.Votesystem;

import Files.FileUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Voting extends ListenerAdapter {

    private ArrayList<VoteCountdown> allVoteCD = new ArrayList<>();
    String emojisUUIDs[] = {"U+31U+20e3", "U+32U+20e3", "U+33U+20e3", "U+34U+20e3", "U+35U+20e3", "U+36U+20e3", "U+37U+20e3", "U+38U+20e3", "U+39U+20e3", "U+1F51F"};

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        JDA jda = event.getJDA();
        User user = event.getAuthor();
        Message m = event.getMessage();
        String content = m.getContentRaw();
        TextChannel channel = event.getChannel();

        //!vote What Game do we want to play, 60, LoL, Overwatch, Minecraft
        if (!user.isBot() && content.toLowerCase().startsWith("r!vote ")){
            content = content.substring("r!vote ".length()); //if Message starts with "!vote "
            int untilComma = content.indexOf(",");
            String desc = "";
            int time = 0;

            if (untilComma != -1) {
                desc = content.substring(0 , untilComma);
                content = content.substring(untilComma+1, content.length()).trim();
            }
            untilComma = content.indexOf(",");
            if (untilComma != -1) {
                time = Integer.parseInt(content.substring(0 , untilComma).trim());
                content = content.substring(untilComma+1, content.length()).trim();
            }

            String[] options = content.split(",");
            List<String> optionsWithEmoji = new ArrayList<>();
            for (int i = 0; i < options.length; i++){
                optionsWithEmoji.add(FileUtil.getEmojiForNum(i) + options[i].trim());
            }

            VoteMessage vm = new VoteMessage(desc, time, optionsWithEmoji, m.getMember(), jda);
            Message voteMsg = channel.sendMessage(vm.getEmbed()).complete();

            VoteCountdown vc = new VoteCountdown(voteMsg, time, optionsWithEmoji);
            allVoteCD.add(vc);
            Thread thread = new Thread(vc);
            thread.start();
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        String emoteID = event.getReaction().getReactionEmote().getAsCodepoints();
        long mID = event.getMessageIdLong();

        for (VoteCountdown vc : allVoteCD){
            if (vc.getRunning() && mID == vc.getMessageID()){
                for (int i = 0; i < emojisUUIDs.length; i++){
                    if (emoteID.equalsIgnoreCase(emojisUUIDs[i])){
                        vc.increase(i);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {

        String emoteID = event.getReaction().getReactionEmote().getAsCodepoints();
        long mID = event.getMessageIdLong();

        for (VoteCountdown vc : allVoteCD){
            if (vc.getRunning() && mID == vc.getMessageID()){
                for (int i = 0; i < emojisUUIDs.length; i++){
                    if (emoteID.equalsIgnoreCase(emojisUUIDs[i])){
                        vc.decrease(i);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onGenericGuild(GenericGuildEvent e) {

        for (int i = 0; i < allVoteCD.size(); i++){
            if(!allVoteCD.get(i).getRunning()){
                allVoteCD.remove(i);
            }
        }
    }


}
