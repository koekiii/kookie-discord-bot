package koeki.core.server.Moderation.Roles.ConfigureRoles;

public class attributes {

    private long GuildID;
    private long MessageID;

    public attributes(){

    }

    public long getGuildID(){
        return GuildID;
    }

    public void setGuildID(long GuildID){
        this.GuildID = GuildID;
    }

    public long getMessageID(){
        return MessageID;
    }

    public void setMessageID(long MessageID){
        this.MessageID = MessageID;
    }
}
