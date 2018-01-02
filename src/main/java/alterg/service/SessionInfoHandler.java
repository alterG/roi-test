package alterg.service;

import alterg.bean.SessionDataBean;
import alterg.entity.SessionData;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

@AllArgsConstructor
public class SessionInfoHandler {

    private List<SessionData> sessionDataList;

    public List<SessionDataBean> divideSessionByDay() {
        for (int i = 0; i < sessionDataList.size(); i++) {
            checkDivision(sessionDataList.get(i));
        }
        return null;
    }


    private boolean checkDivision(SessionData sessionData) {
        DateTime startTime = sessionData.getStartTime();
        DateTime endTime = startTime.plus(sessionData.getWastedTime() * 1000L);
        // crutch with toLocalDate(), joda-startTime can lose difference between 2 dates
        Days days = Days.daysBetween(startTime.toLocalDate(), endTime.toLocalDate());
        int dayDifference = days.getDays();
        if (dayDifference == 1) {
            SessionData sessionDataClone = new SessionData(sessionData);
            int fullWastedTime = sessionData.getWastedTime();
            sessionData.setWastedTime(
                Days.ONE.toStandardSeconds().getSeconds() - sessionData.getStartTime().millisOfDay().get() / 1000);

            sessionDataClone.setStartTime(sessionData.getStartTime().withTimeAtStartOfDay().plus(Days.ONE));
            sessionDataClone.setWastedTime(fullWastedTime - sessionData.getWastedTime());
            sessionDataList.add(sessionDataClone);
        }
        return true;
    }

}
