package koeki.core.server.Moderation.MuteUser;

public class MUAttributes {

    private String GuildID;
    private String UserID;
//    private int Timer;

    public MUAttributes(){

    }

    public String getGuildID() {
        return GuildID;
    }

    public void setGuildID(String guildID){
        GuildID = guildID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

//    public int getTimer(){
//        return Timer;
//    }
//
//    public void setTimer(int timer){
//        Timer = timer;
//    }
}
