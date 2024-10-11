import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;

public class JavaCodeLineCounter {

    private static int fileCount = 0; 
    private static boolean stopRequested = false; 

    public static void main(String[] args) {
        String directoryPath = "./huce-final-project-backend"; 
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist!");
            return;
        }

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                if ("0".equals(input)) {
                    stopRequested = true; 
                    System.out.println("Stopping the program...");
                    break;
                }
            }
            scanner.close();
        }).start();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (stopRequested) {
                    timer.cancel(); 
                    return;
                }
                fileCount = 0; 
                int totalLines = countLinesInDirectory(directory);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedNow = now.format(formatter);

                System.out.println("************************************************");
                System.out.println("Timestamp: " + formattedNow);
                System.out.println("Total number of Java files scanned: " + fileCount);
                System.out.println("Total number of lines of Java code: " + totalLines);
                System.out.println("Waiting 10 seconds for the next check...");
            }
        };

        timer.scheduleAtFixedRate(task, 0, 10000); 
    }

    private static int countLinesInDirectory(File directory) {
        int totalLines = 0;
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    totalLines += countLinesInDirectory(file); 
                } else if (file.getName().endsWith(".java")) {
                    fileCount++; 
                    totalLines += countLinesInFile(file);
                }
            }
        }
        return totalLines;
    }

    private static int countLinesInFile(File file) {
        int lines = 0;
        boolean inBlockComment = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    continue; 
                }

                if (inBlockComment) {
                    if (line.endsWith("*/")) {
                        inBlockComment = false;
                    }
                    continue; 
                }

                if (line.startsWith("/*")) {
                    inBlockComment = true;
                    continue; 
                }

                lines++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getAbsolutePath());
        }

        return lines;
    }
}
