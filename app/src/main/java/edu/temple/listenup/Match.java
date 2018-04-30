package edu.temple.listenup;

public class Match {
    private String userID, withUserID, matchID, userName, withUserName;

    public Match(String userID, String withUserID, String matchID, String userName, String withUserName) {
        this.userID = userID;
        this.withUserID = withUserID;
        this.matchID = matchID;
        this.userName = userName;
        this.withUserName = withUserName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getWithUserID() {
        return withUserID;
    }

    public void setWithUserID(String withUserID) {
        this.withUserID = withUserID;
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWithUserName() {
        return withUserName;
    }

    public void setWithUserName(String withUserName) {
        this.withUserName = withUserName;
    }

}
