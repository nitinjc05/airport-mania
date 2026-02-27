import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class airportester {

    private enum Mode { // Track which section we're in
        NONE,
        US_AIRPORTS,
        US_AIRLINES,
        CA_AIRPORTS,
        CA_AIRLINES
    }

    public static void main(String[] args) {

        Mode mode = Mode.NONE;

        int usAirportCount = 0;
        int usAirlineCount = 0;
        int caAirportCount = 0;
        int caAirlineCount = 0;

        try (Scanner scanner = new Scanner(new File("USCA.txt"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // ---- Detect Section Headers ----
                if (isHeader(line)) {

                    // Print total of previous section before switching
                    printSectionTotal(mode, usAirportCount, usAirlineCount,
                            caAirportCount, caAirlineCount);

                    if (line.equalsIgnoreCase("US Airports:")) {
                        mode = Mode.US_AIRPORTS; // Switch to US Airports mode
                        printHeader("US Airports:");
                        continue;
                    }

                    if (line.equalsIgnoreCase("US Airlines:")) {
                        mode = Mode.US_AIRLINES; // Switch to US Airlines mode
                        printHeader("US Airlines:");
                        continue;
                    }

                    if (line.equalsIgnoreCase("Canadian Airports:")) {
                        mode = Mode.CA_AIRPORTS; // Switch to Canadian Airports mode
                        printHeader("Canadian Airports:");
                        continue;
                    }

                    if (line.equalsIgnoreCase("Canadian Airlines:") 
                            || line.equalsIgnoreCase("C Airlines:")) {
                        mode = Mode.CA_AIRLINES; // Switch to Canadian Airlines mode
                        printHeader("Canadian Airlines:");
                        continue;
                    }
                }

                // ---- Airport Lines ----
                if (mode == Mode.US_AIRPORTS || mode == Mode.CA_AIRPORTS) {

                    if (!line.contains(",")) continue;

                    String[] parts = line.split(",");
                    if (parts.length < 5) continue;

                    airport a = new airport( // Create airport object
                            parts[0].trim(), // Name
                            parts[1].trim(), // Code
                            parts[2].trim(), // City
                            parts[3].trim(), // State
                            parts[4].trim().replace("\\", "") // Country (remove any backslashes if present)
                    );

                    System.out.println(a); // Print airport details

                    if (mode == Mode.US_AIRPORTS)
                        usAirportCount++;
                    else
                        caAirportCount++;

                    continue;
                }

                // ---- Airline Lines ----
                if (mode == Mode.US_AIRLINES || mode == Mode.CA_AIRLINES) { // Airlines should not contain commas

                    if (line.contains(",")) continue;

                    System.out.println(line);

                    if (mode == Mode.US_AIRLINES)
                        usAirlineCount++;
                    else
                        caAirlineCount++;
                }
            }

            // Print final section total
            printSectionTotal(mode, usAirportCount, usAirlineCount,
                    caAirportCount, caAirlineCount);

            // ---- Grand Totals ----
            String title = "Grand Totals:";
            System.out.println("\n" + title);
            System.out.println("-".repeat(title.length()));

            System.out.println("US Airports: " + usAirportCount);
            System.out.println("US Airlines: " + usAirlineCount);
            System.out.println("Canadian Airports: " + caAirportCount);
            System.out.println("Canadian Airlines: " + caAirlineCount);

            System.out.println("--------------------");
            System.out.println("Total Airports Overall: "
                    + (usAirportCount + caAirportCount));
            System.out.println("Total Airlines Overall: "
                    + (usAirlineCount + caAirlineCount));

        } catch (FileNotFoundException e) {
            System.out.println("File not found: USCA.txt");
        }
    }

    private static boolean isHeader(String line) {
        return line.equalsIgnoreCase("US Airports:")
                || line.equalsIgnoreCase("US Airlines:")
                || line.equalsIgnoreCase("Canadian Airports:")
                || line.equalsIgnoreCase("Canadian Airlines:")
                || line.equalsIgnoreCase("C Airlines:");
    }

    private static void printHeader(String title) {
        System.out.println("\n" + title);
        System.out.println("-".repeat(title.length()));
    }

    private static void printSectionTotal(Mode mode,
                                          int usAirportCount,
                                          int usAirlineCount,
                                          int caAirportCount,
                                          int caAirlineCount) {

        switch (mode) {
            case US_AIRPORTS ->
                    System.out.println("Total US Airports: " + usAirportCount);

            case US_AIRLINES ->
                    System.out.println("Total US Airlines: " + usAirlineCount);

            case CA_AIRPORTS ->
                    System.out.println("Total Canadian Airports: " + caAirportCount);

            case CA_AIRLINES ->
                    System.out.println("Total Canadian Airlines: " + caAirlineCount);

            default -> { }
        }
    }
}
