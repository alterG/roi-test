package alterg.io;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FilenameFilter;

@AllArgsConstructor
public class ExtensionFilenameFilter implements FilenameFilter {

    private final String extension;

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(extension);
    }
}
