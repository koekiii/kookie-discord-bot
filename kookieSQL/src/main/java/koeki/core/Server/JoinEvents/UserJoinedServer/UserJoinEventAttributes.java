package koeki.core.Server.JoinEvents.UserJoinedServer;

import koeki.util.DatabaseUtil;

public class UserJoinEventAttributes {

    private long GuildID;
    private long UserID;

    public void setGuildID(long guildID) {
        GuildID = guildID;
    }

    public long getGuildID() {
        return GuildID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public long getUserID() {
        return UserID;
    }
}
