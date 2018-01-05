package alterg.comparator;

import static alterg.service.SessionDataUtilities.parseId;

import alterg.dto.SessionAverageTimeBean;

import java.util.Comparator;

public class SessionATBUserIdComparator implements Comparator<SessionAverageTimeBean> {

    @Override
    public int compare(SessionAverageTimeBean o1, SessionAverageTimeBean o2) {
        return parseId(o1.getUserId()) - parseId(o2.getUserId());
    }
}
