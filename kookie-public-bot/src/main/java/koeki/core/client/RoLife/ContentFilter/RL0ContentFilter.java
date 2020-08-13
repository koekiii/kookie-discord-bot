package koeki.core.client.RoLife.ContentFilter;
import koeki.core.client.RoLife.RL0Secrets;
import koeki.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RL0ContentFilter extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();

    private static String[] words = {"nigger", "neger", "negger", "nega", "negga", "nibba", "nigga", "niggah", "faggot", "fag", "bastard"};
    private static String[] spaces = {" "};

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(event.getGuild().getId().equals(RL0Secrets.GuildID)){
            String content = event.getMessage().getContentRaw();

            boolean containsSpaces = Arrays.stream(spaces).anyMatch(content.toLowerCase()::contains);
            boolean containsWords = Arrays.stream(words).anyMatch(content.toLowerCase()::contains);
            if(containsSpaces){
                content = content.replaceAll("\\s+", "");
                boolean containsWordsNoSpaces = Arrays.stream(words).anyMatch(content.toLowerCase()::contains);
                if(containsWordsNoSpaces){
                    User user = event.getMessage().getAuthor();
                    event.getMessage().delete().complete();
                    user.openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage("You have been warned for breaking Rule NR. 5 on" + event.getGuild().getName() + "!").queue();
                    });
                    containsSpaces = false;
                    containsWords = false;
                    return;
                }
            }else if(containsWords){
                User user = event.getMessage().getAuthor();
                event.getMessage().delete().complete();
                user.openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("You have been warned for breaking Rule NR. 5 on " + event.getGuild().getName() + "!").queue();
                });
                containsSpaces = false;
                containsWords = false;
                return;
            }else{
                return;
            }
        }
    }
}
