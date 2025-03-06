package net.rinorclient.client.util;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DisplayUtil extends JFrame {

    public DisplayUtil() {
        this.setTitle("Verification failed.");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        copyToClipboard();
        String message = "Sorry, you are not on the HWID list." + "\n" + "HWID: " + Hwid.getSystemInfo() + "\n(Copied to clipboard.)";
        JOptionPane.showMessageDialog(this, message, "Could not verify your HWID successfully.", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
    }


    public static void copyToClipboard() {
        StringSelection selection = new StringSelection(Hwid.getSystemInfo());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}