package alterg.service;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExplorer {

    private File pwd;
    private final Pattern firstArgument = Pattern.compile("^\\s*([a-zA-Z]+)");
    private final Pattern secondArgument = Pattern.compile("\\s+([._\\w]+)");

//    public void runExplorer() {
//        pwd = new File("").getAbsoluteFile();
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.print(pwd.getAbsolutePath() + "@user: ");
//            String userInput = scanner.next();
//            Matcher matcher = firstArgument.matcher(userInput);
//            if (!matcher.find()) {
//                System.out.println("Unknown command.");
//                continue;
//            }
//            String command = matcher.group(1);
//            switch (command) {
//                case "ls":
//                    executeLs();
//                    break;
//                case "mov":
//            }
//
//        }
//    }


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

    private void printMenu() {
        System.out.println("Command list:\n"
                           + "* calc - to calculate sessions average visit time\n"
                           + "* mov filename directory - to move file to directory\n"
                           + "* del filename - to delete file\n"
                           + "* ls - to show files in work directory"
                           + "* menu - show menu\n"
                           + "* exit - close program");
    }

    private void printCalcStart() {
        System.out.println("Processing session info...");
    }

    private void printCalcEnd() {
        System.out.println("Session info is calculated...");
    }

    private void printExit() {
        System.out.println("Program is closing...");
    }

}
