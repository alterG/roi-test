package alterg.bean;

import lombok.Data;

/**
 * Class represents data from csv files
 */

@Data
public class SessionDataBean {

    private int timeStamp;
    private String userId;
    private String url;
    private int wastedTime;
}
