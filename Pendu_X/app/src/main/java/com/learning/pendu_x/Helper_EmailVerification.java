package com.learning.pendu_x;

public class Helper_EmailVerification {

    private static boolean email_status;

    public static boolean getEmail_status() {
        return email_status;
    }

    public static void setEmail_status(boolean email_status) {
        Helper_EmailVerification.email_status = email_status;
    }
}
