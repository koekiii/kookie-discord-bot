package koeki.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseUtil {

    private static String path;
    public static boolean flag = false;

    public static boolean ConnectToDB(String filepath){

        Connection con = null;
        try {
            path = "jdbc:sqlite:" + filepath;
            System.out.println(path);
            //create a connection to the database
            con = DriverManager.getConnection(path);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static boolean SaveServer(long ServerID, String ServerName, int MemberCount, String ServerPrefix){
        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;
        flag = false;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        java.util.Date date = new Date();
//        System.out.println(formatter.format(date));

        try {
            con = DriverManager.getConnection(path);
            System.out.println(path);
            prepStmnt = con.prepareStatement("SELECT ServerID FROM Server WHERE ServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(!rs.next()){
                prepStmnt = con.prepareStatement("INSERT INTO Server(ServerID, ServerName, MemberCount, ServerPrefix, ServerLastUpdated) VALUES(?, ?, ?, ?, ?);");
                prepStmnt.setLong(1, ServerID);
                prepStmnt.setString(2, ServerName);
                prepStmnt.setInt(3, MemberCount);
                prepStmnt.setString(4, ServerPrefix);
                prepStmnt.setString(5, formatter.format(date));
                prepStmnt.executeUpdate();
                prepStmnt.close();
                flag = true;
                return true;
            }else{
                prepStmnt.close();
                flag = false;
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
            return false;
        }
    }

    public static boolean SaveInitiationAndPostingChannelServer (long ServerID, long ITCID, long PTCID){
        boolean ret = false;
        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("INSERT INTO ServerSuggestionSystem VALUES (NULL, ?, ?, ?)");
            prepStmnt.setLong(1, ServerID);
            prepStmnt.setLong(2, ITCID);
            prepStmnt.setLong(3, PTCID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                prepStmnt.close();
                ret = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public static boolean SaveServerUser(long ServerID, long UserID, String UserName, String UserDiscriminator){
        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT UserID FROM discorduser WHERE UserID = ?");
            prepStmnt.setLong(1, UserID);
            rs = prepStmnt.executeQuery();

            if(!rs.next()){
                prepStmnt.close();

                prepStmnt = con.prepareStatement("INSERT INTO discorduser(UserID, UserName, UserDiscriminator) VALUES(?, ?, ?)");
                prepStmnt.setLong(1, UserID);
                prepStmnt.setString(2, UserName);
                prepStmnt.setString(3, UserDiscriminator);
                prepStmnt.executeUpdate();
                prepStmnt.close();

                prepStmnt = con.prepareStatement("INSERT INTO ServerDiscordUser(ServerDiscordUserID, DCUserID, DCServerID) VALUES(NULL, ?, ?)");
                prepStmnt.setLong(1, UserID);
                prepStmnt.setLong(2, ServerID);
                prepStmnt.executeUpdate();
                prepStmnt.close();

                return true;
            }else {
                prepStmnt.close();
                flag = false;
                prepStmnt = con.prepareStatement("INSERT INTO ServerDiscordUser(ServerDiscordUserID, DCUserID, DCServerID) VALUES(NULL, ?, ?)");
                prepStmnt.setLong(1, UserID);
                prepStmnt.setLong(2, ServerID);
                prepStmnt.executeUpdate();
                prepStmnt.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean ServerStatus(long ServerID){

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT ServerID FROM server WHERE ServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(!rs.next()){
                prepStmnt.close();
                return false;
            }else{
                prepStmnt.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String ServerLastTimeUpdated(long ServerID){
        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        String time = "";

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT ServerLastUpdated FROM Server WHERE ServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){

                time = rs.getString(1);
                prepStmnt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return time;
    }

    public static boolean SaveServerPrefix(String ServerPrefix, long ServerID){

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT ServerID FROM Server WHERE ServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                prepStmnt.close();

                prepStmnt = con.prepareStatement("UPDATE Server SET ServerPrefix = ? WHERE ServerID = ?");
                prepStmnt.setString(1, ServerPrefix);
                prepStmnt.setLong(2, ServerID);
                prepStmnt.executeUpdate();
                prepStmnt.close();
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getServerPrefix(long ServerID) {

        String ServerPrefix = "";

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT ServerPrefix FROM Server WHERE ServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                ServerPrefix = rs.getString(1);
                prepStmnt.close();
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ServerPrefix;
    }

    public static boolean activateServerWelcomeChannelPlugin (long ServerID){
        boolean ret = false;

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("UPDATE ServerWelcomeSystem SET Online = true WHERE DCServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                ret = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public static boolean saveServerWelcomeChannel (long ServerID, long WTCID){
        boolean ret = false;

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("INSERT INTO ServerWelcomeSystem VALUES(NULL, false, ?, ?)");
            prepStmnt.setLong(1, ServerID);
            prepStmnt.setLong(2, WTCID);
            rs = prepStmnt.executeQuery();
            if(rs.next()) {
                ret = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public static long getServerWelcomeChannel(long ServerID){
        long WelcomeChannelID = 0;

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT WTCID FROM ServerWelcomeSystem WHERE DCServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                WelcomeChannelID = rs.getLong(1);
                prepStmnt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WelcomeChannelID;
    }

    public static long getServerID(long ServerID){
        long ID = 0;
        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);

            prepStmnt = con.prepareStatement("SELECT ServerID FROM Server WHERE ServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                ID = rs.getLong(1);
                prepStmnt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }
        // Needs to be worked on ASAP
//    public static boolean UpdateServer (long ServerID, String ServerName, int MemberCount, String ServerPrefix){
//        Connection con = null;
//        PreparedStatement prepStmnt = null;
//        ResultSet rs = null;
//
//        try {
//            con = DriverManager.getConnection(path);
//
//            prepStmnt = con.prepareStatement("SELECT ");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    public static long getServerInitiateSuggestionChannel (long ServerID){
        int ISCID = 0;

        Connection con = null;
        PreparedStatement prepStmnt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(path);
            prepStmnt = con.prepareStatement("SELECT ITCID from ServerSuggestionSystem where DCServerID = ?");
            prepStmnt.setLong(1, ServerID);
            rs = prepStmnt.executeQuery();
            if(rs.next()){
                ISCID = Math.toIntExact(rs.getLong(1));
                prepStmnt.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ISCID;
    }
}
