package alterg.service;

import alterg.bean.SessionDataInputBean;
import lombok.AllArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class ReaderTask implements Callable<List<SessionDataInputBean>> {

    private File file;

    @Override
    public List<SessionDataInputBean> call() {
        CsvFileReader reader = new CsvFileReader();
        return reader.read(file.getAbsolutePath());
    }
}
