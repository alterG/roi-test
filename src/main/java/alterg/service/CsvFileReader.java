package alterg.service;

import alterg.dto.SessionDataInputBean;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for read csv data to beans (@see SessionDataInputBean)
 */
public class CsvFileReader {

    private static final String[] HEADERS = {"timeStamp", "userId", "url", "wastedTime"};
    private static final CellProcessor[] PROCESSORS = new CellProcessor[]{
        new NotNull(new ParseInt()), new NotNull(), new NotNull(), new NotNull(new ParseInt())};

    public List<SessionDataInputBean> read(File file) throws IOException {
        List<SessionDataInputBean> inputBeans = new ArrayList<>();
        try (ICsvBeanReader reader = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE)) {
            SessionDataInputBean bean;
            while ((bean = reader.read(SessionDataInputBean.class, HEADERS, PROCESSORS)) != null) {
                inputBeans.add(bean);
            }
        }
        return inputBeans;
    }
}