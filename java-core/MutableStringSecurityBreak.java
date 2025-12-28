import java.util.concurrent.TimeUnit;
/**
 * Demonstration of how mutable Strings can lead to security vulnerabilities (TOCTOU attack).
 */
public class MutableStringSecurityBreak {
    static StringBuilder mutableFilePath = new StringBuilder("/data/user_info.txt");
    static String immutableFilePath = "/data/user_info.txt";

    public static void main(String[] args) {
        // Thread A: The System Process
        Thread systemThread = new Thread(() -> {
            // Simulate system processing the file request
            handleRequest(immutableFilePath);
            handleRequest(mutableFilePath);
        });

        // Thread B: The Malicious Hacker
        Thread hackerThread = new Thread(() -> {
            try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) {}
            // Hacker attempts to swap the path after the check but before the use
            mutableFilePath.setLength(0); 
            mutableFilePath.append("/etc/passwd"); 
            immutableFilePath = "/etc/passwd"; // This line has no effect on the original immutable string
            System.out.println("[Hacker] Maliciously changed path to: " + mutableFilePath);
            System.out.println("[Hacker] Attempted to change immutable path to: " + immutableFilePath);
        });

        systemThread.start();
        hackerThread.start();
    }

    // --- HELPER METHODS ---
    private static boolean checkPermission(String path) {
        return path.equals("/data/user_info.txt");
    }

    private static boolean checkPermission(StringBuilder path) {
        return path.toString().equals("/data/user_info.txt");
    }

    private static void handleRequest(StringBuilder path) {
        System.out.println("[System][Mutable Case] Handle path: " + path);
        if (checkPermission(path)) {
            System.out.println("[System][Mutable Case] Permission GRANTED for: " + path);
            try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) {}
            openFile(path);
        } else {
            System.err.println("[System][Mutable Case] ACCESS DENIED: Unauthorized access to " + path);
        }
    }

    private static void handleRequest(String path) {
        System.out.println("[System][Immutable Case] Handle path: " + path);
        if (checkPermission(path)) {
            System.out.println("[System][Immutable Case] Permission GRANTED for: " + path);
            try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) {}
            openFile(path);
        } else {
            System.err.println("[System][Immutable Case] ACCESS DENIED: Unauthorized access to " + path);
        }
    }

    private static void openFile(String path) {
        if (path.equals("/data/user_info.txt")) {
            System.out.println("[System][Immutable Case] SUCCESS: Safely accessed " + path);
        } else {
            System.err.println("[System][Immutable Case] CRITICAL SECURITY BREACH: Unauthorized access to " + path);
        }
    }

    private static void openFile(StringBuilder path) {
        if (path.equals("/data/user_info.txt")) {
            System.out.println("[System][Mutable Case] SUCCESS: Safely accessed " + path);
        } else {
            System.err.println("[System][Mutable Case] CRITICAL SECURITY BREACH: Unauthorized access to " + path);
        }
    }

}
