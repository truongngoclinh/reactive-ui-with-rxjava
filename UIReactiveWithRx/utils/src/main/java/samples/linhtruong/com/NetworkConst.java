package samples.linhtruong.com;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:28.
 * @organization VED
 */

public interface NetworkConst {

    int CMD_TCP_PACKET = 0;
    int CMD_UDP_PACKET = 1;
    int CMD_CONNECTED = 235;
    int CMD_DISCONNECTED = 236;

    int PACKET_HEADER_LENGTH_BYTES = 2;

    // UDP specific
    int UDP_ENCRYPT_KEY_SIZE = 16;
    int UDP_MAX_PACKET_SIZE = 1024; //1k

    // HTTP specific
    int HTTP_CONN_TIMEOUT = 15000;
    int HTTP_READ_TIMEOUT = 15000;
    int HTTP_WRITE_TIMEOUT = 15000;

    interface TIME {
        interface SECONDS {
            // unit
            int MIN = 60;
            int HOUR = MIN * 60;
            int DAY = 24 * HOUR;
            int WEEK = 7 * DAY;
        }
    }
}
