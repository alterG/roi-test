package alterg.comparator;

import alterg.entity.SessionData;

import java.util.Comparator;

public class SessionUrlComparator implements Comparator<SessionData> {

    @Override
    public int compare(SessionData o1, SessionData o2) {
        return o1.getUrl().compareToIgnoreCase(o2.getUrl());
    }
}
