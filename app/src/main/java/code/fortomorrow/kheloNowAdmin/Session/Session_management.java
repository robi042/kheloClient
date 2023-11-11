package code.fortomorrow.kheloNowAdmin.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_management {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "SESSION_GAME_KEY";
    String SESSION_LAST_PAGE = "SESSION_LAST_PAGE";
    String SESSION_GAMEID = "SESSION_GAMEID";
    String SESSION_PLAY_TYPE_ID = "SESSION_PLAY_TYPE_ID";

    String SESSION_MESSAGE_COUNT = "SESSION_MESSAGE_COUNT";
    String NotificationForMessage = "NotificationForMessage";

    public Session_management(Context con) {
        sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveGameID(String gameID) {

        editor.putString(SESSION_GAMEID, gameID).commit();
    }

    public void savePlayTypeID(String gameID) {

        editor.putString(SESSION_PLAY_TYPE_ID, gameID).commit();
    }

    public void saveGameKey(String gameKey) {

        editor.putString(SESSION_KEY, gameKey).commit();
    }

    public String getUserID() {
        return sharedPreferences.getString(SESSION_GAMEID, "-1");
    }

    public String getUserKey() {
        return sharedPreferences.getString(SESSION_KEY, "-1");
    }

    public String getPlayTypeID() {
        return sharedPreferences.getString(SESSION_PLAY_TYPE_ID, "-1");
    }

    public void saveMessageCount(String count) {

        editor.putString(SESSION_MESSAGE_COUNT, count).commit();
    }

    public String getMessageCount() {
        return sharedPreferences.getString(SESSION_MESSAGE_COUNT, "-1");
    }

    public void NotificationForMessage(String count) {
        editor.putString(NotificationForMessage, count).commit();
    }

    public String getNotificationForMessage() {
        return sharedPreferences.getString(NotificationForMessage, "-1");
    }

}
