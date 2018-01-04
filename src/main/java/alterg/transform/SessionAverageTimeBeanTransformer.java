package alterg.transform;

import alterg.dto.SessionAverageTimeBean;
import alterg.dto.SessionData;

/**
 * Class for convert SessionData to SessionAverageTimeBean
 * Note: wastedTime directly converts to average time visit
 */
public class SessionAverageTimeBeanTransformer implements Transformer<SessionData, SessionAverageTimeBean> {

    @Override
    public SessionAverageTimeBean transform(SessionData source) {
        SessionAverageTimeBean result = new SessionAverageTimeBean();
        result.setUserId("user" + source.getUserId());
        result.setUrl(source.getUrl());
        result.setAverageTimeVisit(source.getWastedTime());
        return result;
    }
}
