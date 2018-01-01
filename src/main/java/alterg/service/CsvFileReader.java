package alterg.service;

import alterg.entity.SessionInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvFileReader {

    public static List<SessionInfo> read(String fileName) {
        List<SessionInfo> result = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bf.readLine()) != null) {
                try (Scanner scanner = new Scanner(line)) {
                    scanner.useDelimiter(",");
                    SessionInfo elem = new SessionInfo();
                    int readIndex = 0;
                    while (scanner.hasNext()) {
                        String data = scanner.next();
                        switch (readIndex++) {
                            case 0:
                                elem.setTimeStamp(Integer.parseInt(data));
                                break;
                            case 1:
                                elem.setUserId(data);
                                break;
                            case 2:
                                elem.setUrl(data);
                                break;
                            case 3:
                                elem.setWastedTime(Integer.parseInt(data));
                                break;
                        }
                    }
                    result.add(elem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
