package alterg;

import alterg.service.FileExplorer;
import alterg.service.SessionDataProcessor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Application {

    public static final String CONFIG_FILE_PATH = "C:\\dev\\roi\\roitest\\src\\main\\resources\\config.properties";
    public static final String INPUT_KEY = "input";
    public static final String OUTPUT_KEY = "output";
    public static final String EXTENSION_CSV = ".csv";

    public static void main(String[] args) {
        try {
            Properties properties = readProperties(CONFIG_FILE_PATH);
            File inputDirectory = Paths.get(properties.getProperty(INPUT_KEY)).toFile();
            File outputDirectory = Paths.get(properties.getProperty(OUTPUT_KEY)).toFile();
            FilenameFilter filter = new ExtensionFilenameFilter(EXTENSION_CSV);
            printProgramInfo(inputDirectory);
            SessionDataProcessor processor = new SessionDataProcessor(inputDirectory, outputDirectory, filter);
            FileExplorer fileExplorer = new FileExplorer(processor);
            fileExplorer.runExplorer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Program uses windows-style separators, that replace on escape-sequences
     * that's why we process property-file before its read
     */
    private static Properties readProperties(String filePath) throws IOException {
        String stringFileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        stringFileContent = stringFileContent.replaceAll("\\\\", "\\\\\\\\");
        Properties properties = new Properties();
        properties.load(new StringReader(stringFileContent));
        return properties;
    }

    private static void printProgramInfo(File inputDirectory) {
        System.out.println("Program is designed to calculate the average time of user visits on web-pages.\n"
                           + "Created for Return on Intelligence Inc. Version 1.0.\n"
                           + "Author Igor Shchipanov, email: oneyearday@gmail.com.\n\n"
                           + "Tracked directory: " + inputDirectory.getParentFile().getName()
                           + File.separator + inputDirectory.getName() + " (set in config.properties)\n"
                           + "Now you are in tracked directory. Program uses only these files.\n");
    }
}
