package koeki.core.server.Moderation.SuggestionSystem;

public class SS0attributes {

    private String SuggestionChannel;
    private String UserID;
    private String Suggestion;

    public void setUserID(String userID) { UserID = userID; }

    public String getUserID() { return UserID; }

    public void setSuggestionChannel(String suggestionChannel) { SuggestionChannel = suggestionChannel; }

    public String getSuggestionChannel() { return SuggestionChannel; }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }

    public String getSuggestion() {
        return Suggestion;
    }
}
