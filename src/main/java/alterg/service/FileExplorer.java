package alterg.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExplorer {

    private File pwd;
    private final Pattern firstArgument = Pattern.compile("^\\s*([a-zA-Z]+)");
    private final Pattern secondArgument = Pattern.compile("\\s+([._\\w]+)");

    public void runExplorer() {
        pwd = new File("").getAbsoluteFile();
        Scanner scanner = new Scanner(System.in);
        while (pwd != null) {
            System.out.print(pwd.getAbsolutePath() + "@user: ");
            String userInput = scanner.next();
            Matcher matcher = firstArgument.matcher(userInput);
            if (!matcher.find()) {
                System.out.println("Unknown command.");
                continue;
            }
            String command = matcher.group(1);
            File commandArgument = getSecondArgument(userInput.substring(matcher.end(1)))
            switch (command) {
                case "ls":
                    ls(commandArgument);
                    break;
                case "mov":
//                    File commandArgument2 = getSecondArgument()
                    mov(commandArgument);
                    break;
                case "menu":
                    printMenu();
                    break;
                case "rm":
                    rm(commandArgument);
                    break;
                case "exit":
                    System.exit(0);
                default:
            }
        }
    }

    /**
     * parse second argument from user input
     * note: ".." means parent file
     * @param source
     * @param isExistValidation
     * @return
     * @throws IllegalArgumentException
     */
    private File getSecondArgument(String source, boolean isExistValidation) throws IllegalArgumentException {
        Matcher matcher = secondArgument.matcher(source);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Wrong argument.");
        }
        if ("..".equals(matcher.group(1))) {
            return pwd.getParentFile();
        }
        if (pwd.listFiles() == null) {
            throw new IllegalArgumentException(("Directory is empty."));
        }
        File parsedFile = new File(pwd.getAbsolutePath() + "\\" + matcher.group(1));
        if (isExistValidation) {
            if (Arrays.asList(pwd.listFiles()).contains(parsedFile)) {
                return parsedFile;
            } else {
                throw new IllegalArgumentException("File not found.");
            }
        }
        return parsedFile;
    }


    /**
     * Change pwd to given directory
     *
     * @param file target directory
     */
    private void cd(File file) throws IllegalArgumentException {
        if (!file.canRead() || !file.canExecute()) {
            throw new IllegalArgumentException("Access denied.");
        }
        pwd = file;
    }

    /**
     * Print content of file
     *
     * @param file to print it's content
     * @throws IOException with reason of it
     */
    private void cat(File file) throws IOException {
        try {
            System.out.println(new String(Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
            String errorMessage;
            if (file.isDirectory()) {
                errorMessage = "Can't read directory.";
            } else if (file.isFile() && !file.canRead()) {
                errorMessage = "Can't read file. Access error.";
            } else {
                errorMessage = "Can't read file. Unknown error.";
            }
            throw new IOException(errorMessage);
        }
    }

    /**
     * Create directory
     */
    private void mkdir(File directory) {
        directory.mkdir();
    }

    /**
     * Delete file or directory
     *
     * @param file to delete
     * @return true if file has been deleted
     */
    private void rm(File file) {
        if (file.delete()) {
            System.out.println(file.getName() + "was deleted.");
        } else {
            System.out.println("can't delete " + file.getName());
        }
    }

    /**
     * Print files in directory
     *
     * @param file target directory
     */
    private void ls(File file) {
        for (File x : file.listFiles()) {
            System.out.println(String.format("%s\t%s", x.isFile() ? "-" : "d", x.getName()));
        }
    }

    private void mov(File file, File directory) {
        file.renameTo(new File(directory, file.getName()));
    }

    private void printMenu() {
        System.out.println("Command list:\n"
                           + "* calc - to calculate sessions average visit time\n"
                           + "* mov filename directory - to move file to directory\n"
                           + "* rm filename - to delete file\n"
                           + "* ls - to show files in work directory"
                           + "* cd dirname - to change work directory"
                           + "* cat filename - to print file content"
                           + "* menu - show menu\n"
                           + "* exit - close program");
    }

    private void exit() {
        System.out.println("Program is closing...");
        System.exit(0);
    }

    private void printCalcStart() {
        System.out.println("Processing session info...");
    }

    private void printCalcEnd() {
        System.out.println("Session info is calculated...");
    }


}
