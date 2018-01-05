package alterg.dto;

import lombok.Data;

/**
 * Class represents data from csv files
 */

@Data
public class SessionDataInputBean {

    private int timeStamp;
    private String userId;
    private String url;
    private int wastedTime;
}
