package alterg.dto;

import lombok.Data;

@Data
public class SessionAverageTimeBean {

    private String userId;
    private String url;
    private int averageTimeVisit;
}
