package alterg.service;

import alterg.dto.SessionAverageTimeBean;
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
 * Class for writing data (@see SessionAverageTimeBean) to csv files
 */
public class CsvFileWriter {

    private static final CellProcessor[] PROCESSORS = new CellProcessor[]{new NotNull(), new NotNull(), new NotNull()};
    private static final String[] HEADERS = {"userId", "url", "averageTimeVisit"};

    public void write(Map<LocalDate, List<SessionAverageTimeBean>> sessionDataMap, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file);
             ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
            for (LocalDate date : sessionDataMap.keySet()) {
                csvWriter.writeHeader(date.toString("dd-MMM-yyyy", Locale.ENGLISH));
                List<SessionAverageTimeBean> sessionAverageTimeBeans = sessionDataMap.get(date);
                for (SessionAverageTimeBean sessionAverageTimeBean : sessionAverageTimeBeans) {
                    csvWriter.write(sessionAverageTimeBean, HEADERS, PROCESSORS);
                }
            }
        }
    }

}
