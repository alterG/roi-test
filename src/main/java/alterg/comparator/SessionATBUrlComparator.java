package alterg.comparator;

import alterg.dto.SessionAverageTimeBean;

import java.util.Comparator;

public class SessionATBUrlComparator implements Comparator<SessionAverageTimeBean> {

    @Override
    public int compare(SessionAverageTimeBean o1, SessionAverageTimeBean o2) {
        return o1.getUrl().compareToIgnoreCase(o2.getUrl());
    }
}
