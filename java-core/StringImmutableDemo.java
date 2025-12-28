import java.util.concurrent.TimeUnit;

/**
 * Demonstration of String immutability, String Pool, Security, and Thread Safety in Java.
 */
public class StringImmutableDemo {
    
    // Shared mutable variable to demonstrate TOCTOU vulnerability in the Security section
    static StringBuilder mutableFilePath = new StringBuilder("/data/user_info.txt");
    static String immutableFilePath = "/data/user_info.txt";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("String Immutability, String Pool, Security, and Thread Safety Demo\n");
        
        // --- 1. MEMORY & STRING POOL ---
        System.out.println("--- 1. Memory & String Pool Demo ---");
        /*
         * String literals with the same value point to the same object in the String Constant Pool.
         * This saves memory by avoiding redundant object creation.
         */
        String original = "Hello";
        String copyOfOriginal = "Hello";
        
        // Comparison using '==' checks if both variables refer to the same memory address
        System.out.println("original == copyOfOriginal: " + (original == copyOfOriginal)); // true
        
        // Identity hash code confirms they are the exact same instance in memory
        System.out.println("Identity HashCode original: " + System.identityHashCode(original));
        System.out.println("Identity HashCode copyOfOriginal: " + System.identityHashCode(copyOfOriginal));

        

        // --- 2. SECURITY (TOCTOU Attack Simulation) ---
        System.out.println("\n--- 2. Security Demo ---");
        /*
         * Scenario: If String were mutable, its value could be changed AFTER a security check 
         * but BEFORE the actual file operation.
         */
        
        // Thread A: The System Process
        Thread systemThread = new Thread(() -> {
            System.out.println("[System][Mutable Case] Checking permission for: " + mutableFilePath);
            System.out.println("[System][Immutable Case] Checking permission for: " + immutableFilePath);
            handleFileRequestMutable(mutableFilePath);
            handleFileRequestImmutable(immutableFilePath);
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
        
        // Wait for security demo to finish before starting thread safety demo
        systemThread.join();
        hackerThread.join();

    
        // --- 3. THREAD SAFETY ---
        System.out.println("\n--- 3. Thread Safe Demo ---");
        /*
         * Sharing a global configuration across multiple threads.
         * String (Immutable): Safe to share; no thread can modify the original value.
         * StringBuilder (Mutable): Unsafe; threads will overwrite/corrupt the data.
         */
        String immutableConfig = "jdbc:postgresql://localhost:5432/db";
        StringBuilder nonImmutableConfig = new StringBuilder("jdbc:postgresql://localhost:5432/db");

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            
            // Simulating a dangerous modification on a shared mutable object
            nonImmutableConfig.append(" -> modified by ").append(threadName);
            
            // Results: Immutable remains consistent, Non-Immutable becomes corrupted/messy
            System.out.println(threadName + " | Immutable: " + immutableConfig);
        };

        // Start 5 threads to demonstrate race conditions on the StringBuilder object
        for (int i = 1; i <= 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }
        
        // Wait for threads to finish
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\nFinal corrupted NON-Immutable Value (Data Corruption): \n" + nonImmutableConfig);
    }

    // --- HELPER METHODS ---
    
    /**
     * Simulates a security check.
     */
    private static boolean checkPermission(String path) {
        return path.equals("/data/user_info.txt");
    }
    
    private static boolean checkPermission(StringBuilder path) {
        return path.toString().equals("/data/user_info.txt");
    }

    private static void handleFileRequestImmutable(String path) {
        System.out.println("[System] Handle immutable path: " + path);
        if (checkPermission(path)) {
            System.out.println("[System] Permission GRANTED for: " + path);
            try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) {}
            openFile(path);
        } else {
            System.err.println("[System] ACCESS DENIED: Unauthorized access to " + path);
        }
    }

    private static void handleFileRequestMutable(StringBuilder path) {
        System.out.println("[System] Handle mutable path: " + path);
        if (checkPermission(path)) {
            System.out.println("[System] Permission GRANTED for: " + path);
            try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) {}
            openFile(path);
        } else {
            System.err.println("[System] ACCESS DENIED: Unauthorized access to " + path);
        }
    }

    /**
     * Simulates a critical system operation.
     */
    private static void openFile(String path) {
        if (path.equals("/data/user_info.txt")) {
            System.out.println("[System] SUCCESS: Safely accessed " + path);
        } else {
            System.err.println("[System] CRITICAL SECURITY BREACH: Unauthorized access to " + path);
        }
    }

    private static void openFile(StringBuilder path) {
        if (path.toString().equals("/data/user_info.txt")) {
            System.out.println("[System] SUCCESS: Safely accessed " + path);
        } else {
            System.err.println("[System] CRITICAL SECURITY BREACH: Unauthorized access to " + path);
        }
    }
}