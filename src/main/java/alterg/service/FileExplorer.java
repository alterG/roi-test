package alterg.service;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class FileExplorer {

    private final Pattern commandPattern = Pattern.compile("^\\s*([a-zA-Z]+)");
    private final Pattern filePattern = Pattern.compile("\\s+(\\w+)");
    private final SessionDataProcessor processor;
    private File pwd;

    public void runExplorer() {
        pwd = processor.getInputDirectory();
        Scanner scanner = new Scanner(System.in);
        printMenu();

        while (true) {
            System.out.print(pwd.getAbsolutePath() + "@user: ");
            String userInput = scanner.nextLine();
            Matcher matcher = commandPattern.matcher(userInput);
            if (!matcher.find()) {
                System.out.println("Unknown command.");
                continue;
            }
            String stringCommand = matcher.group(1);
            Commands command = Commands.parse(stringCommand);
            String userInputWithoutCommand = userInput.substring(matcher.end(1));
            try {
                executeCommand(command, userInputWithoutCommand);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /**
     * Execute command
     */
    private void executeCommand(Commands command, String userInput) {
        switch (command) {
            case CALC:
                System.out.println("Processing session info...");
                processor.process();
                System.out.println("Session info is calculated.");
                break;
            case LS:
                ls(userInput);
                break;
            case CD:
                try {
                    cd(userInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Can't change working directory.");
                }
                break;
            case CAT:
                try {
                    cat(userInput);
                } catch (IOException e) {
                    System.out.println("File can't be printed");
                }
                break;
            case MKDIR:
                if (!mkdir(userInput)) {
                    System.out.println("Can't create directory.");
                }
                break;
            case MOV:
                try {
                    mov(userInput);
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
            case MENU:
                printMenu();
                break;
            case RM:
                if (rm(userInput)) {
                    System.out.println("File was deleted.");
                } else {
                    System.out.println("File can't be deleted.");
                }
                break;
            case EXIT:
                exit();
                break;
            default:
                System.out.println("Unknown command.");
        }
    }

    /**
     * parse second argument from user input
     * note: ".." means parent file
     */
    private File getSecondArgument(String source, Commands command) throws IllegalArgumentException {

        Matcher matcher = filePattern.matcher(source);
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

    private boolean isStringEmpty(String source) {
        return source.matches("\\s*");
    }

    private boolean isParentSymbol(String source) {
        return source.matches("\\s*\\.\\.");
    }

    /**
     * Change pwd to given directory
     */
    private void cd(String source) throws IllegalArgumentException {
        File argument = null;
        if (isParentSymbol(source)) {
            argument = pwd.getParentFile();
        } else {
            Matcher matcher = filePattern.matcher(source);
            String fileName = matcher.group(1);
            argument = new File(pwd, fileName);
        }
        if (!argument.canRead() || !argument.canExecute()) {
            throw new IllegalArgumentException("Access denied.");
        }
        pwd = argument;
    }

    /**
     * Print content of file
     *
     * @throws IOException with reason of it
     */
    private void cat(String source) throws IOException {
        Matcher matcher = filePattern.matcher(source);
        String argument = matcher.group(1);
        File file = new File(pwd, argument);
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
     * @return true if operation is successful
     */
    private boolean mkdir(String source) {
        Matcher matcher = filePattern.matcher(source);
        String argument = matcher.group(1);
        File directory = new File(pwd, argument);
        return directory.mkdir();
    }

    /**
     * Delete file or directory
     *
     * @return true if file has been deleted
     */
    private boolean rm(String source) {
        Matcher matcher = filePattern.matcher(source);
        String argument = matcher.group(1);
        File file = new File(pwd, argument);
        return file.delete();
    }

    /**
     * Print files in directory
     * @param source user input without command part
     */
    private void ls(String source) {
        File argument = null;
        if (isStringEmpty(source)) {
            argument = pwd;
        }
        if (isParentSymbol(source)) {
            argument = pwd.getParentFile();
        }
        Matcher matcher = filePattern.matcher(source);
        String fileName = matcher.group(1);
        argument = new File(pwd, fileName);
        for (File file : argument.listFiles()) {
            System.out.println(String.format("%s\t%s", file.isFile() ? "-" : "d", file.getName()));
        }
    }

    /**
     * move file to directory
     */
    private void mov(String source) {
        Matcher matcher = filePattern.matcher(source);
        String fileName = matcher.group(1);
        File argumentFile = new File(pwd, fileName);
        Matcher matcher2 = filePattern.matcher(source.substring(matcher.end(1)));
        String directoryName = matcher2.group(1);
        File argumentDirectory = new File(pwd, directoryName);
        if (!argumentDirectory.isDirectory()) {
            throw new IllegalArgumentException(argumentDirectory + " isn't directory.");
        }
        argumentFile.renameTo(new File(argumentDirectory, argumentFile.getName()));
    }

    /**
     * show menu
     */
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

    /**
     * close program
     */
    private void exit() {
        System.out.println("Program is closing...");
        System.exit(0);
    }


}
