package alterg.transform;

import alterg.bean.SessionDataBean;
import alterg.entity.SessionData;
import org.joda.time.DateTime;

public class SessionTransformer implements Transformer<SessionDataBean, SessionData> {

    @Override
    public SessionData transform(SessionDataBean source) {
        SessionData result = new SessionData();
        result.setTime(new DateTime(source.getTimeStamp()));
        result.setUserId(parseId(source.getUserId()));
        result.setUrl(source.getUrl());
        result.setWastedTime(source.getWastedTime());
        return result;
    }

    /**
     * method works with "userXXX", where XXX is required id
     */
    private int parseId(String userId) {
        return Integer.parseInt(userId.substring(userId.lastIndexOf("user")));
    }
}
