//package koeki.core.Fun;
//
//import com.github.jreddit.entity.User;
//import com.github.jreddit.utils.restclient.HttpRestClient;
//import com.github.jreddit.utils.restclient.RestClient;
//import koeki.main.Main;
//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//import org.json.simple.parser.ParseException;
//
//import java.io.IOException;
//
//public class test extends ListenerAdapter {
//
//    private static JDA jda = Main.getBuilder();
//
//    public void onGuildMessageReceived (GuildMessageReceivedEvent event){
//        String content = event.getMessage().getContentRaw();
//
//        if(content.equals("k!test")){
//            RestClient restClient = new HttpRestClient();
//            restClient.setUserAgent("bot/1.0 by name");
//
//            User redditUser = new User(restClient, "koekiii", "xy-10000-z");
//            try{
//                redditUser.connect();
//                System.out.println("Reddit User connected!");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
