package alterg.service;

import alterg.bean.SessionDataOutputBean;
import org.joda.time.LocalDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class for writing data (@see SessionDataOutputBean) to csv files
 */
public class CsvFileWriter {

    private static final CellProcessor[] PROCESSORS = new CellProcessor[]{new NotNull(), new NotNull(), new NotNull()};
    private static final String[] HEADERS = {"userId", "url", "averageTimeVisit"};

    public void write(Map<LocalDate, List<SessionDataOutputBean>> sessionDataMap, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file);
             ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
            for (LocalDate date : sessionDataMap.keySet()) {
                csvWriter.writeHeader(date.toString("dd-MMM-yyyy", Locale.ENGLISH));
                List<SessionDataOutputBean> sessionDataOutputBeans = sessionDataMap.get(date);
                for (SessionDataOutputBean sessionDataOutputBean : sessionDataOutputBeans) {
                    csvWriter.write(sessionDataOutputBean, HEADERS, PROCESSORS);
                }
            }
        }
    }

}
