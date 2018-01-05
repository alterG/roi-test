package alterg.service;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class SessionDataProcessor {

    public static final int THREADS_MAX = 10;
    public static final String PREFIX_RESULT_FILE = "avg_";
    private final File inputDirectory;
    private final File outputDirectory;
    private final FilenameFilter filter;

    public void process() {
        List<File> inputFiles = Arrays.asList(inputDirectory.listFiles(filter));
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_MAX);
        for (File inputFile : inputFiles) {
            executorService.submit(new ProcessorWorker(inputFile, outputDirectory));
        }
        executorService.shutdown();
    }
}
