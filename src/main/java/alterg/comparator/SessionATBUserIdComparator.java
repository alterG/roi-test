package alterg.comparator;

import alterg.dto.SessionAverageTimeBean;
import alterg.service.SessionDataHandler;

import java.util.Comparator;

public class SessionATBUserIdComparator implements Comparator<SessionAverageTimeBean> {

    @Override
    public int compare(SessionAverageTimeBean o1, SessionAverageTimeBean o2) {
        return SessionDataHandler.parseId(o1.getUserId()) - SessionDataHandler.parseId(o2.getUserId());
    }
}
