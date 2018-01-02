package alterg.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Class represents session info
 */

@Data
@NoArgsConstructor
public class SessionData {
    private DateTime startTime;
    private int userId;
    private String url;
    private int wastedTime;

    public SessionData(SessionData other) {
        startTime = other.startTime;
        userId = other.userId;
        url = other.url;
        wastedTime = other.wastedTime;
    }
}
