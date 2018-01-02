package alterg.service;

import alterg.bean.SessionDataInputBean;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvFileReader {

    public static final String[] HEADERS = {"timeStamp", "userId", "url", "wastedTime"};
    public static final CellProcessor[] PROCESSORS = new CellProcessor[]{
        new NotNull(new ParseInt()), new NotNull(), new NotNull(), new NotNull(new ParseInt())};

    public List<SessionDataInputBean> read(String fileName) {
        List<SessionDataInputBean> inputBeans = new ArrayList<>();
        try (ICsvBeanReader reader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE)) {
            SessionDataInputBean bean;
            while ((bean = reader.read(SessionDataInputBean.class, HEADERS, PROCESSORS)) != null) {
                inputBeans.add(bean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputBeans;
    }
}