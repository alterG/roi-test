package alterg.entity;

import lombok.Data;

/**
 * Class represents data from csv files
 */

@Data
public class SessionInfo {

    private int timeStamp;
    private int userId;
    private String url;
    private int wastedTime;
}
