package alterg.service;

import alterg.bean.SessionDataInputBean;
import alterg.bean.SessionDataOutputBean;
import alterg.entity.SessionData;
import alterg.transform.SessionTransformer;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AverageCsvProcessingTask implements Runnable {

    private File inputFile;
    private File outputDirectory;
    private SessionTransformer transformer;

    @Override
    public void run() {
        CsvFileReader reader = new CsvFileReader();
        List<SessionDataInputBean> inputBeans = reader.read(inputFile);
        List<SessionData> sessionDataList = transform(inputBeans);
        Map<LocalDate, List<SessionDataOutputBean>> sessionOutputBeans = process(sessionDataList);
        CsvFileWriter writer = new CsvFileWriter();
        File outputFile = getOutputFile(inputFile, outputDirectory);
        writer.write(sessionOutputBeans, outputFile);
    }

    private Map<LocalDate, List<SessionDataOutputBean>> process(List<SessionData> sessionDataList) {
        SessionDataHandler handler = new SessionDataHandler(sessionDataList);
        handler.divideSessionsByDay();
        return handler.getSessionOutputBeans();
    }

    private File getOutputFile(File inputFile, File outputDirectory) {
        String outputFileName = "avg_" + inputFile.getName();
        return new File(outputDirectory, outputFileName);
    }

    private List<SessionData> transform(List<SessionDataInputBean> inputBeans) {
        return inputBeans.stream()
            .map(x -> transformer.transform(x))
            .collect(Collectors.toList());
    }
}
