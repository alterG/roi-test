package alterg.transform;

import alterg.dto.SessionData;
import alterg.dto.SessionDataInputBean;
import alterg.service.SessionDataUtilities;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class SessionTransformer implements Transformer<SessionDataInputBean, SessionData> {

    @Override
    public SessionData transform(SessionDataInputBean source) {
        SessionData result = new SessionData();
        result.setStartTime(new DateTime(source.getTimeStamp() * 1000L, DateTimeZone.UTC));
        result.setUserId(SessionDataUtilities.parseId(source.getUserId()));
        result.setUrl(source.getUrl());
        result.setWastedTime(source.getWastedTime());
        return result;
    }

}
