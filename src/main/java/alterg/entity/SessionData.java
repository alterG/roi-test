package alterg.entity;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * Class represents session info
 */

@Data
public class SessionData implements Cloneable {

    private DateTime startTime;
    private int userId;
    private String url;
    private int wastedTime;
}
