package koeki.about;

public class AK0attributes {

    private long GuildID;
    private long MessageID;
    private int Page;
    private boolean Flag = false;

    public AK0attributes(){
    }

    public long getGuildID() { return GuildID; }

    public void setGuildID(long GuildID) { this.GuildID = GuildID; }

    public long getMessageID() { return MessageID; }

    public void setMessageID(long MessageID) { this.MessageID = MessageID; }

    public int getPage() { return Page; }

    public void setPage(int Page) { this.Page = Page; }

    public boolean isFlag() { return Flag; }

    public void setAvailable(boolean Flag) { this.Flag = Flag; }
}
