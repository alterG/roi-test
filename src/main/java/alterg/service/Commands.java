package alterg.service;

public enum Commands {
    CALC("calc"),
    LS("ls"),
    CD("cd"),
    CAT("cat"),
    MKDIR("mkdir"),
    MOV("mov"),
    MENU("menu"),
    RM("rm"),
    EXIT("exit"),
    UNKNOWN_COMMAND("unknown");

    private String value;

    Commands(String value) {
        this.value = value;
    }

    public static Commands parse(String source) {
        for (Commands elem : Commands.values()) {
            if (elem.value.equals(source)) {
                return elem;
            }
        }
        return Commands.UNKNOWN_COMMAND;
    }
}
