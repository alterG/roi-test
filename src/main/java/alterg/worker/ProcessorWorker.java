package alterg.worker;

import alterg.dto.SessionAverageTimeBean;
import alterg.dto.SessionData;
import alterg.dto.SessionDataInputBean;
import alterg.io.CsvFileReader;
import alterg.io.CsvFileWriter;
import alterg.service.SessionDataHandler;
import alterg.service.SessionDataProcessor;
import alterg.transform.SessionTransformer;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProcessorWorker implements Runnable {

    private File inputFile;
    private File outputDirectory;

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
        SessionDataHandler handler = new SessionDataHandler(sessionDataList);
        handler.divideSessionsByDay();
        return handler.getSessionOutputBeans();
    }

    private File getOutputFile(File inputFile, File outputDirectory) {
        String outputFileName = SessionDataProcessor.PREFIX_RESULT_FILE + inputFile.getName();
        return new File(outputDirectory, outputFileName);
    }

    private List<SessionData> transform(List<SessionDataInputBean> inputBeans) {
        SessionTransformer transformer = new SessionTransformer();
        return inputBeans.stream()
            .map(x -> transformer.transform(x))
            .collect(Collectors.toList());
    }
}
