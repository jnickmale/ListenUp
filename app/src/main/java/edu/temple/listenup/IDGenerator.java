package edu.temple.listenup;

public class IDGenerator {
    public static String generateID(String user1ID, String user2ID){
        String retval = "";
        StringBuilder builder;
        if(user1ID.compareTo(user2ID) > 0){
            builder = new StringBuilder(user1ID);
            builder.append(user2ID);
        }else{
            builder = new StringBuilder(user2ID);
            builder.append(user1ID);
        }
        retval = builder.toString();
        return retval;
    }
}
