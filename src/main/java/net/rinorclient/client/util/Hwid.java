package net.rinorclient.client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class Hwid {

    public Hwid() {
        boolean bool = readURL().contains(getSystemInfo());
        if (!bool) {
            Display();
            RuntimeException v = new RuntimeException("No tienes Acceso para usar este cliente");
            v.setStackTrace(null);
            v.initCause(null);
            throw v;
        }
    }

    public boolean bo() {
        return readURL().contains(getSystemInfo());
    }

    public static void Display() {
        DisplayUtil frame = new DisplayUtil();
        frame.setVisible(true);
        //No revelar informacion al crashear
        RuntimeException v = new RuntimeException("Verification was unsuccessful!");
        v.setStackTrace(null);
        v.initCause(null);
        throw v;
    }

    public static String getSystemInfo() {
        return DigestUtils.sha256Hex(DigestUtils.sha256Hex(System.getenv("os")
                + System.getProperty("os.name")
                + System.getProperty("os.arch")
                + System.getProperty("user.name")
                + System.getenv("SystemRoot")
                + System.getenv("HOMEDRIVE")
                + System.getenv("PROCESSOR_LEVEL")
                + System.getenv("PROCESSOR_REVISION")
                + System.getenv("PROCESSOR_IDENTIFIER")
                + System.getenv("PROCESSOR_ARCHITECTURE")
                + System.getenv("PROCESSOR_ARCHITEW6432")
                + System.getenv("NUMBER_OF_PROCESSORS")
        ));
    }

    public static List<String> readURL() {
        List<String> s = new ArrayList<>();
        try {
            final URL url = new URL("https://raw.githubusercontent.com/XG2025-Akaishin/xg2025/refs/heads/main/HwidSystem.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String hwid;
            while ((hwid = bufferedReader.readLine()) != null) {
                s.add(hwid);
            }
        } catch (Exception e) {
            
        }
        return s;
    }
}
