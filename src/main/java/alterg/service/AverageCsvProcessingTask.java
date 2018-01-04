package alterg.service;

import alterg.dto.SessionAverageTimeBean;
import alterg.dto.SessionData;
import alterg.dto.SessionDataInputBean;
import alterg.transform.SessionAverageTimeBeanTransformer;
import alterg.transform.SessionTransformer;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AverageCsvProcessingTask implements Runnable {

    public static final String PREFIX_RESULT_FILE = "avg_";
    private File inputFile;
    private File outputDirectory;
    private SessionTransformer transformer;

    @Override
    public void run() {
        try {
        CsvFileReader reader = new CsvFileReader();
        List<SessionDataInputBean> inputBeans = reader.read(inputFile);
        List<SessionData> sessionDataList = transform(inputBeans);
        Map<LocalDate, List<SessionAverageTimeBean>> sessionOutputBeans = process(sessionDataList);
        CsvFileWriter writer = new CsvFileWriter();
        File outputFile = getOutputFile(inputFile, outputDirectory);
            writer.write(sessionOutputBeans, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<LocalDate, List<SessionAverageTimeBean>> process(List<SessionData> sessionDataList) {
        SessionDataHandler handler = new SessionDataHandler(new SessionAverageTimeBeanTransformer(), sessionDataList);
        handler.divideSessionsByDay();
        return handler.getSessionOutputBeans();
    }

    private File getOutputFile(File inputFile, File outputDirectory) {
        String outputFileName = PREFIX_RESULT_FILE + inputFile.getName();
        return new File(outputDirectory, outputFileName);
    }

    private List<SessionData> transform(List<SessionDataInputBean> inputBeans) {
        return inputBeans.stream()
            .map(x -> transformer.transform(x))
            .collect(Collectors.toList());
    }
}
