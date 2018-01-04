package alterg.transform;

import alterg.dto.SessionData;
import alterg.dto.SessionDataInputBean;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class SessionTransformer implements Transformer<SessionDataInputBean, SessionData> {

    @Override
    public SessionData transform(SessionDataInputBean source) {
        SessionData result = new SessionData();
        result.setStartTime(new DateTime(source.getTimeStamp() * 1000L, DateTimeZone.UTC));
        result.setUserId(parseId(source.getUserId()));
        result.setUrl(source.getUrl());
        result.setWastedTime(source.getWastedTime());
        return result;
    }

    /**
     * method works with "userXXX", where XXX is required id
     */
    private int parseId(String userId) {
        return Integer.parseInt(userId.substring(userId.lastIndexOf("user") + "user".length()));
    }
}
