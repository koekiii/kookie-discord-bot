package koeki.core.client.RoLife.GameUpdate;

import koeki.core.client.RoLife.RL0Secrets;
import koeki.main.Main;
import koeki.util.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class RL0GameUpdate extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();
    private static boolean firstmsgflag = false;
    private static boolean editmessage = false;
    private static boolean editmessageYES = false;
    private static boolean editmessageNO = false;
    private static boolean newMessage = false;
    private static String editmessageid;
    private static int editmessageindex;
    private static String newmessage;
    private static int panel;
    private static List<RL0GUattributes> rl0GUattributesList = new ArrayList<>();
    private static List<String> msgIDList = new ArrayList<>();
    private static List<String> IDList = new ArrayList<>();
    private static String color = "#258560";
    private static EmbedBuilder eb1 = new EmbedBuilder();
    private static EmbedBuilder ebD = new EmbedBuilder();
    private static long privchannelid;
    private static long privchanneleditedid;
    private static TextChannel tc;
    private static User owner = jda.getUserById(Secrets.OwnerID);

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        RL0GUattributes RL0GUattributes = new RL0GUattributes();
        TextChannel channel = Main.getBuilder().getTextChannelById(RL0GUattributes.getChannelID());
        tc = channel;
        String guild = RL0GUattributes.getGuildID();
        String OwnerID = RL0Secrets.OwnerID;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        java.util.Date date = new Date();

        if(!event.getGuild().getId().equals(guild)){
            // Check if it's the right Guild
            return;
        }else{
            if(event.getChannel() != channel){
                // Check if it's the right channel
                return;
            }else{
                // Get the Message into a String
                Message msg = event.getMessage();
                String content = msg.getContentRaw();
                if(!event.getAuthor().getId().equals(OwnerID)){
                    // Check if it was the Owner of the Server
                    return;
                }else{
                    if(!content.equals("k@gameupdate")){
                        // Check if it's the right command
                        return;
                    }else{
                        // Get user by id, open a Private channel and declare a message
                        User user = jda.getUserById(OwnerID);
                        PrivateChannel privateChannel = user.openPrivateChannel().complete();
                        Message message;

                        // Delete the user message
                        event.getMessage().delete().complete();
                        // Clear Embed Builder
                        eb1.clear();
                        eb1.setTitle("Game Update");
                        eb1.setDescription("Do you wish to edit any updates?\n reply with yes or no");
                        eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                        eb1.setColor(Color.decode(color));
                        // Send Embed into Private Channel
                        message = privateChannel.sendMessage(eb1.build()).complete();
                        // Get the long private channel id
                        privchannelid = message.getIdLong();
                        // Set Message ID and ID of Attributes Object
                        RL0GUattributes.setID(1);
                        // Add Object to list
                        rl0GUattributesList.add(RL0GUattributes);
                        // Put both static var's to true and false
                        firstmsgflag = true;
                        // First panel
                        panel = 1;
                    }
                }
            }
        }
    }

    public void onPrivateMessageReceived (PrivateMessageReceivedEvent event){
        // Get the message
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        // messageID to work with
        // Get the private channel of the event
        PrivateChannel privateChannel = event.getChannel();
        // make a new Object
        RL0GUattributes rl0GUattributes = new RL0GUattributes();
        // Channel of Server where stuff should be posted
        TextChannel channel = Main.getBuilder().getTextChannelById(rl0GUattributes.getChannelID());
        // Declare a new message
        Message message;
        // Make a new Random, for the Message ID's
        Random random = new Random();
        EmbedBuilder eb = new EmbedBuilder();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        java.util.Date date = new Date();

        if(!event.getAuthor().getId().equals(RL0Secrets.OwnerID)){
            // If Author wasn't Owner, return.
            return;
        }else{
            if(firstmsgflag != true){
                // if static isn't true
                return;
            } else if(content.equals("Yes") || content.equals("yes") && editmessageYES == false && editmessageNO == false) {
                // put panel to 2
                panel = 2;
                int i = 1;
                String msgid = "";
                try{
                    for(RL0GUattributes a : rl0GUattributesList){
                        while(i < IDList.size()){
                            i++;
                        }
                        if(a.getID() != 0){
                            panel = 2;
                            if(i == 1){
                                panel = 2;
                                i = 0;
                                if(i == IDList.size()){
                                    panel = 2;
                                    eb1.clear();
                                    eb1.setTitle("There are no Updates to edit");
                                    eb1.setDescription("Do you want to continue?");
                                    eb1.setFooter("reply with yes or no.");
                                    eb1.setColor(Color.decode(color));
                                    eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                                    privateChannel.editMessageById(privchannelid, eb1.build()).complete();
                                    editmessageYES = false;
                                    editmessageNO = true;
                                    editmessage = true;
                                    rl0GUattributesList.remove(privchannelid);
                                    return;
                                }else{
                                    while(i < IDList.size()){
                                        msgid = IDList.get(i);
                                        i++;
                                        eb.addField("MessageID:", String.valueOf(msgid), true);
                                    }
                                    eb.setTitle("List of past " + i + " Update");
                                    eb.setDescription("Please reply with the ID of the message, you wish to edit!");
                                    eb.setColor(Color.decode(color));
                                    eb.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                                    privateChannel.editMessageById(privchannelid, eb.build()).complete();
                                    newMessage = false;
                                    editmessageYES = true;
                                    editmessageNO = false;
                                    editmessage = false;
                                    firstmsgflag = true;
                                    rl0GUattributesList.remove(privchannelid);
                                    return;
                                }
                            }else{
                                panel = 2;
                                i = 0;
                                while(i < IDList.size()){
                                    msgid = IDList.get(i);
                                    i++;
                                    eb.addField("MessageID:", String.valueOf(msgid), true);
                                }
                                eb.setTitle("List of past " + i + " Updates");
                                eb.setDescription("Please reply with the ID of the message, you wish to edit!");
                                eb.setColor(Color.decode(color));
                                eb.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                                privateChannel.editMessageById(privchannelid, eb.build()).complete();
                                newMessage = false;
                                editmessageYES = true;
                                editmessageNO = false;
                                editmessage = false;
                                firstmsgflag = true;
                                rl0GUattributesList.remove(privchannelid);
                                return;
                            }
                        }
                    }
                }catch (ConcurrentModificationException e){ }
            } else if(content.equals("Yes") || content.equals("yes") && editmessageNO == true && editmessage == true) {
                panel = 3;
                try{
                    for(RL0GUattributes a : rl0GUattributesList){
                        int msgid = a.getID();
                        if(msgid == 1){
                            eb1.clear();
                            eb1.setTitle("Provide the Update.");
                            eb1.setDescription("Just put in the message, you want to have in the updates");
                            eb1.setColor(Color.decode(color));
                            ebD.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                            privateChannel.editMessageById(privchannelid, eb1.build()).complete();
                            newMessage = false;
                            editmessageYES = false;
                            editmessageNO = false;
                            editmessage = true;
                            firstmsgflag = true;
                            rl0GUattributesList.remove(privchannelid);
                            return;
                        }
                    }
                }catch (ConcurrentModificationException e){
                    e.printStackTrace();
                }
            } else if(content.equals("No") || content.equals("no") && editmessageNO == false) {
                panel = 3;
                try{
                    for(RL0GUattributes a : rl0GUattributesList){
                        int msgid = a.getID();
                        if(msgid == 1){
                            eb1.clear();
                            eb1.setTitle("Provide the Update.");
                            eb1.setDescription("Just put in the message, you want to have in the updates");
                            eb1.setColor(Color.decode(color));
                            eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                            privateChannel.editMessageById(privchannelid, eb1.build()).complete();
                            newMessage = false;
                            editmessageYES = false;
                            editmessageNO = false;
                            editmessage = true;
                            firstmsgflag = true;
                            rl0GUattributesList.remove(privchannelid);
                            return;
                        }
                    }
                }catch (ConcurrentModificationException e){ }
            }else if(content.equals("No") || content.equals("no") && editmessageNO == true && editmessageYES == false){
                for(RL0GUattributes a : rl0GUattributesList) {
                    int msgid = a.getID();
                    if (msgid == 1) {
                        ebD.clear();
                        ebD.setTitle("Process has been cancelled!");
                        ebD.setDescription("If you want to restart, redo the whole process.");
                        ebD.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                        ebD.setColor(Color.decode(color));
                        privateChannel.editMessageById(privchannelid, ebD.build()).complete();
                        newMessage = false;
                        editmessageYES = false;
                        editmessageNO = false;
                        editmessage = true;
                        firstmsgflag = true;
                        rl0GUattributesList.remove(privchannelid);
                        return;
                    }
                }
            } else if(content.equals("Exit") || content.equals("exit")
                    || content.equals("Decline") || content.equals("decline") || content.equals("Cancel")
                    || content.equals("cancel")) {
                for(RL0GUattributes a : rl0GUattributesList) {
                    int msgid = a.getID();
                    if (msgid == 1) {
                        ebD.clear();
                        ebD.setTitle("Process has been cancelled!");
                        ebD.setDescription("If you want to restart, redo the whole process.");
                        ebD.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                        ebD.setColor(Color.decode(color));
                        privateChannel.deleteMessageById(privchannelid).complete();
                        privateChannel.deleteMessageById(privchanneleditedid).complete();
                        privateChannel.sendMessage(ebD.build()).complete();
                        rl0GUattributesList.remove(privchannelid);
                        newMessage = false;
                        editmessageYES = false;
                        editmessageNO = false;
                        editmessage = false;
                        firstmsgflag = false;
                        return;
                    }
                }
            } else if(firstmsgflag = true && editmessageNO == false && editmessageYES == false && editmessage == true) {

                if(content.equals("Yes") || content.equals("yes") || content.equals("No")
                        || content.equals("no")) {
                    for(RL0GUattributes a : rl0GUattributesList) {
                        int msgid = a.getID();
                        if (msgid == 1) {
                            eb1.clear();
                            eb1.setTitle("Wrong command usage!");
                            eb1.setDescription("The process has been cancelled. Please redo the process.");
                            eb1.setColor(Color.decode(color));
                            eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                            privateChannel.editMessageById(privchannelid, eb1.build()).complete();
                            rl0GUattributesList.remove(privchannelid);
                            return;
                        }
                    }
                } else if(content.equals("Exit") || content.equals("exit")
                        || content.equals("Decline") || content.equals("decline") || content.equals("Cancel")
                        || content.equals("cancel")) {
                    for(RL0GUattributes a : rl0GUattributesList) {
                        int msgid = a.getID();
                        if (msgid == 1) {
                            ebD.clear();
                            ebD.setTitle("Process has been cancelled!");
                            ebD.setDescription("If you want to restart, redo the whole process.");
                            ebD.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                            ebD.setColor(Color.decode(color));
                            privateChannel.editMessageById(privchannelid, ebD.build()).complete();
                            rl0GUattributesList.remove(privchannelid);
                            newMessage = false;
                            editmessageYES = false;
                            editmessageNO = false;
                            editmessage = false;
                            firstmsgflag = false;
                            return;
                        }
                    }
                }

                rl0GUattributes.setMessageText(content);

                for(RL0GUattributes a : rl0GUattributesList){
                    int msgid = a.getID();
                    if(msgid == 1){
                        int id = random.nextInt(200);
                        while(a.getID() == id){
                            id++;
                        }
                        message = channel.sendMessage(rl0GUattributes.getMessageText() + "\n`ID: " + id + "`").complete();
                        eb1.clear();
                        eb1.setTitle("Update sent!");
                        eb1.setDescription("You successfully posted a new game update!");
                        eb1.setColor(Color.decode(color));
                        eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                        privateChannel.editMessageById(privchannelid, eb1.build()).complete();
                        rl0GUattributesList.remove(privchannelid);
                        a.setMessageText(content);
                        a.setID(id);
                        a.setMessageID(message.getId());
                        rl0GUattributesList.add(a);
                        msgIDList.add(String.valueOf(a.getMessageID()));
                        IDList.add(String.valueOf(id));
                        firstmsgflag = false;
                        editmessageNO = false;
                        editmessageYES = false;
                        editmessage = false;
                        newMessage = false;
                        return;
                    }
                }
            } else if(IDList.contains(content) && editmessageYES){
                int i = IDList.indexOf(content);
                editmessageindex = i;
                editmessageid = msgIDList.get(i);
                message = tc.retrieveMessageById(editmessageid).complete();
                String text = message.getContentRaw();
                eb1.clear();
                message = privateChannel.sendMessage("Here is the Update that you want to edit!\n\n" + text).complete();
                privchannelid = message.getIdLong();
                eb1.setTitle("Update: " + IDList.get(IDList.indexOf(content)));
                eb1.setDescription("If you're done, **and** sent the message, then reply with 'done'.");
                eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                eb1.setColor(Color.decode(color));
                Message message1 = privateChannel.sendMessage(eb1.build()).complete();
                privchanneleditedid = message1.getIdLong();

                newMessage = true;

                firstmsgflag = true;
                editmessageNO = false;
                editmessageYES = false;
                editmessage = false;
                return;

            } else if(content.equals("Done") || content.equals("done") && newMessage == true){int i = 0;
                int id = 0;
                if(msgIDList.contains(editmessageid)){
                    i = msgIDList.indexOf(editmessageid);
                    id = Integer.parseInt(IDList.get(i));
                } else {
                    privateChannel.sendMessage("An error occured! Please message my Owner.").complete();
                }

                tc.editMessageById(editmessageid, newmessage + "\n`ID: " + id + "`").complete();
                eb1.clear();
                privateChannel.deleteMessageById(privchannelid).complete();
                eb1.setTitle("Update: " + IDList.get(editmessageindex));
                eb1.setDescription("You successfully edited the update with ID: " + IDList.get(editmessageindex));
                eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                eb1.setColor(Color.decode(color));

                try{
                    System.out.println(privchanneleditedid);
                    privateChannel.editMessageById(privchanneleditedid, eb1.build()).complete();
                }catch (IllegalArgumentException e) {
                    privateChannel.sendMessage("An error occured! Please message my Owner.").complete();
                }
                newMessage = false;
                firstmsgflag = true;
                editmessageNO = false;
                editmessageYES = false;
                editmessage = false;
                return;

            } else if(!content.equals("Done") || !content.equals("done") && newMessage == true){
                newmessage = content;

                eb1.clear();
                eb1.setTitle("Saved the message!");
                eb1.setDescription("Are you done? If yes, type done!");
                eb1.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                eb1.setColor(Color.decode(color));
                privateChannel.editMessageById(privchanneleditedid, eb1.build()).complete();

                newMessage = true;
                firstmsgflag = true;
                editmessageNO = false;
                editmessageYES = false;
                editmessage = false;
                return;

            } else if(!content.equals("Yes") || !content.equals("yes") || !content.equals("No")
                    || !content.equals("no") || !content.equals("Exit") || !content.equals("exit")
                    || !content.equals("Decline") || !content.equals("decline") || !content.equals("Cancel")
                    || !content.equals("cancel") && editmessageNO == false && editmessageYES == false){
                for(RL0GUattributes a : rl0GUattributesList) {
                    int msgid = a.getID();
                    if (msgid == 1) {
                        eb1.clear();
                        eb1.setTitle("Wrong command usage!");
                        eb1.setDescription("The process has been cancelled. Please redo the process.");
                        eb1.setColor(Color.decode(color));
                        ebD.setFooter("If you need help, message " + owner.getName() + "#" + owner.getDiscriminator() + " - " + formatter.format(date));
                        privateChannel.deleteMessageById(privchannelid).complete();
                        privateChannel.deleteMessageById(privchanneleditedid).complete();
                        privateChannel.sendMessage(eb1.build()).complete();
                        rl0GUattributesList.remove(privchannelid);
                        firstmsgflag = false;
                        newMessage = false;
                        editmessageNO = false;
                        editmessageYES = false;
                        editmessage = false;
                        return;
                    }
                }
            }
        }
    }
}