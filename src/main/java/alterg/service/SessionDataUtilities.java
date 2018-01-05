package alterg.service;

public class SessionDataUtilities {

    public static final String PREFIX_USER_ID = "user";
    public static final double PREFIX_MILLI = 1e-3;

    /**
     * utility method works with "userXXX", where XXX is required id
     */
    public static int parseId(String userId) {
        return Integer.parseInt(userId.substring(userId.lastIndexOf(PREFIX_USER_ID) + PREFIX_USER_ID.length()));
    }
}
