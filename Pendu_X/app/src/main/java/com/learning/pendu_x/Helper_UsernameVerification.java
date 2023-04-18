package com.learning.pendu_x;

public class Helper_UsernameVerification {

    private static boolean user_status;

    public static boolean getUser_status() {
        return user_status;
    }

    public static void setUser_status(boolean email_status) {
        Helper_UsernameVerification.user_status = email_status;
    }
}
