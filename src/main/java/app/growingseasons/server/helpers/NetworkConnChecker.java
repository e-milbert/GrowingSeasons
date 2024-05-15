package app.growingseasons.server.helpers;

import org.tinylog.Logger;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkConnChecker {

    public static boolean hasInternet() {

        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            if (address.isReachable(5000)) { // 5000 milliseconds (5 seconds) timeout

                return true;
            }
        } catch (IOException e) {
            Logger.error(e);
        }

        return false;
    }

}
