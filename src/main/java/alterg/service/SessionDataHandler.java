package alterg.service;

import alterg.comparator.SessionATBUrlComparator;
import alterg.comparator.SessionATBUserIdComparator;
import alterg.dto.SessionAverageTimeBean;
import alterg.dto.SessionData;
import alterg.transform.SessionAverageTimeBeanTransformer;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SessionDataHandler {

    public static final double PREFIX_MILLI = 10e-3;
    public static final String PREFIX_USER_ID = "user";

    private SessionAverageTimeBeanTransformer transformer;
    private List<SessionData> sessionDataList;

    /**
     * utility method works with "userXXX", where XXX is required id
     */
    public static int parseId(String userId) {
        return Integer.parseInt(userId.substring(userId.lastIndexOf(PREFIX_USER_ID) + PREFIX_USER_ID.length()));
    }

    /**
     * Divide sessions which start on one day and end on another
     * and add it to storage session
     *
     * @return divided sessions
     */
    public List<SessionData> divideSessionsByDay() {
        for (SessionData sessionData : sessionDataList) {
            int dayDifference = getDayDifference(sessionData);
            if (dayDifference == 1) {
                SessionData slicedSession = divideAndGetSlicedSession(sessionData);
                sessionDataList.add(slicedSession);
            }
        }
        return sessionDataList;
    }


    /**
     * unify sessions start on the same day, calculate average time visit
     *
     * @return sessionATB Map divided by days
     */
    public Map<LocalDate, List<SessionAverageTimeBean>> getSessionOutputBeans() {
        Map<LocalDate, List<SessionData>> sessionsByDay = getSessionsByDayMap();
        Map<LocalDate, List<SessionAverageTimeBean>> sessionOutputBeans = new HashMap<>(); // rename
        for (LocalDate date : sessionsByDay.keySet()) {
            List<SessionData> sameDaySessions = sessionsByDay.get(date);
            List<SessionAverageTimeBean> sameDayAverageTimeSessions = transform(sameDaySessions);
            List<SessionAverageTimeBean> unitedByDaySessions = getUnitedByDaySessions(sameDayAverageTimeSessions);
            sessionOutputBeans.put(date, unitedByDaySessions);
        }
        return sessionOutputBeans;
    }

    /**
     * transform SessionData list to SessionAverageTimeBean list
     */
    private List<SessionAverageTimeBean> transform(List<SessionData> sameDaySessions) {
        return sameDaySessions.stream()
            .map(x -> transformer.transform(x))
            .collect(Collectors.toList());
    }

    /**
     * calculate day difference between start and end of session
     * note: its crutch to convert toLocalDate(), because Joda-time can lose difference between 2 dates
     *
     * @return number of days
     */
    private int getDayDifference(SessionData sessionData) {
        DateTime startTime = sessionData.getStartTime();
        DateTime endTime = startTime.plus((int) (sessionData.getWastedTime() / PREFIX_MILLI));
        Days days = Days.daysBetween(startTime.toLocalDate(), endTime.toLocalDate());
        return days.getDays();
    }

    /**
     * divideAndGetSlicedSession SessionData into 2 objects and add 2-nd to storage
     *
     * @param sessionData object to divideAndGetSlicedSession
     */
    private SessionData divideAndGetSlicedSession(SessionData sessionData) {
        SessionData sessionDataClone = new SessionData(sessionData);
        int fullWastedTime = sessionData.getWastedTime();
        sessionData.setWastedTime((int)
                                      (Days.ONE.toStandardSeconds().getSeconds()
                                       - sessionData.getStartTime().millisOfDay().get() * PREFIX_MILLI));

        sessionDataClone.setStartTime(sessionData.getStartTime().withTimeAtStartOfDay().plus(Days.ONE));
        sessionDataClone.setWastedTime(fullWastedTime - sessionData.getWastedTime());
        return sessionDataClone;
    }

    /**
     * Divide sessions by day
     */
    private Map<LocalDate, List<SessionData>> getSessionsByDayMap() {
        Map<LocalDate, List<SessionData>> daySessionMap = new HashMap<>();
        for (SessionData sessionData : sessionDataList) {
            LocalDate sessionDate = sessionData.getStartTime().toLocalDate();
            if (!daySessionMap.containsKey(sessionDate)) {
                daySessionMap.put(sessionDate, new ArrayList<>());
            }
            List<SessionData> daySessionSet = daySessionMap.get(sessionDate);
            daySessionSet.add(sessionData);
        }
        return daySessionMap;
    }

    /**
     * unify sessionATB start on the same days
     *
     * @return united sessions with average time visit
     */
    private List<SessionAverageTimeBean> getUnitedByDaySessions(List<SessionAverageTimeBean> sameDaySessions) {
        sameDaySessions.sort(new SessionATBUrlComparator());
        sameDaySessions.sort(new SessionATBUserIdComparator());

        List<SessionAverageTimeBean> unitedByDaySessions = new ArrayList<>();
        List<SessionAverageTimeBean> sessionsToUnify = new ArrayList<>();

        SessionAverageTimeBean prev = sameDaySessions.get(0);
        sessionsToUnify.add(prev);
        for (int i = 1; i < sameDaySessions.size(); i++) {
            SessionAverageTimeBean cur = sameDaySessions.get(i);
            if (cur.getUserId().equals(prev.getUserId()) && cur.getUrl().equals(prev.getUrl())) {
                sessionsToUnify.add(cur);
            } else {
                SessionAverageTimeBean outputBean = unifyAndGet(sessionsToUnify);
                unitedByDaySessions.add(outputBean);
                sessionsToUnify.clear();
                prev = sameDaySessions.get(i);
                sessionsToUnify.add(prev);
            }
        }
        if (!sessionsToUnify.isEmpty()) {
            SessionAverageTimeBean outputBean = unifyAndGet(sessionsToUnify);
            unitedByDaySessions.add(outputBean);
        }
        return unitedByDaySessions;
    }

    /**
     * calculate average time visit
     */
    private SessionAverageTimeBean unifyAndGet(List<SessionAverageTimeBean> sessionsToUnify) {
        int sum = 0;
        for (SessionAverageTimeBean elem : sessionsToUnify) {
            sum += elem.getAverageTimeVisit();
        }
        sessionsToUnify.get(0).setAverageTimeVisit(sum / sessionsToUnify.size());
        return sessionsToUnify.get(0);
    }

}
