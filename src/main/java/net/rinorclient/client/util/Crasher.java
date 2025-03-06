package net.rinorclient.client.util;

public class Crasher extends RuntimeException {
    
    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        StackTraceElement stackTraceElement = new StackTraceElement("Cracked", "Cracked-Module", "V21", "AMS", "Unkunon", "Unkunon", 0);
        setStackTrace(new StackTraceElement[]{stackTraceElement});
    }
}
