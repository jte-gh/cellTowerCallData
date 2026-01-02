import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to generate synthetic cell tower call data and write it to a
 * CSV file.
 */
public class CallDataWriter {

    /**
     * Represents a single call record with start time, end time, location, and
     * phone numbers.
     */
    public static class CallRecord {
        private LocalDateTime start;
        private LocalDateTime end;
        private String cellTowerId;
        private String cellTowerName;
        private String fromNumber;
        private String toNumber;

        /**
         * Constructs a new CallRecord.
         *
         * @param start         The start date and time of the call.
         * @param end           The end date and time of the call.
         * @param cellTowerId   The ID of the cell tower where the call was initiated.
         * @param cellTowerName The name of the cell tower.
         * @param fromNumber    The phone number initiating the call.
         * @param toNumber      The phone number receiving the call.
         */
        public CallRecord(LocalDateTime start, LocalDateTime end, String cellTowerId, String cellTowerName,
                String fromNumber, String toNumber) {
            this.start = start;
            this.end = end;
            this.cellTowerId = cellTowerId;
            this.cellTowerName = cellTowerName;
            this.fromNumber = fromNumber;
            this.toNumber = toNumber;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public String getCellTowerId() {
            return cellTowerId;
        }

        public String getCellTowerName() {
            return cellTowerName;
        }

        public String getFromNumber() {
            return fromNumber;
        }

        public String getToNumber() {
            return toNumber;
        }
    }

    /**
     * Writes a list of CallRecord objects to a CSV file.
     *
     * @param filePath The path to the CSV file to be created.
     * @param records  The list of call records to write.
     */
    public static void writeCallDataToCsv(String filePath, List<CallRecord> records) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("date-time-start,date-time-end,celltower-id,celltower-name,from-number,to-number");

            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            for (CallRecord record : records) {
                StringBuilder sb = new StringBuilder();
                sb.append(record.getStart().format(formatter)).append(",");
                sb.append(record.getEnd().format(formatter)).append(",");
                sb.append(escapeSpecialCharacters(record.getCellTowerId())).append(",");
                sb.append(escapeSpecialCharacters(record.getCellTowerName())).append(",");
                sb.append(escapeSpecialCharacters(record.getFromNumber())).append(",");
                sb.append(escapeSpecialCharacters(record.getToNumber()));
                writer.println(sb.toString());
            }
            System.out.println("CSV file created successfully at " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Escapes special characters in a string for CSV format.
     * Replaces newlines with spaces and escapes double quotes.
     *
     * @param data The string data to escape.
     * @return The escaped string, enclosed in quotes if necessary.
     */
    private static String escapeSpecialCharacters(String data) {
        if (data == null) {
            return "";
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    /**
     * Main method to generate synthetic call data and write it to a file.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        List<CallRecord> records = new ArrayList<>();
        String[][] towers = {
                { "CT-1001", "Utrecht CO Tower" },
                { "CT-1002", "Maastricht Maas Tower" },
                { "CT-1003", "Amsterdam Amstel Tower" },
                { "CT-1004", "Leiden Tower" },
                { "CT-1005", "Rotterdam Coolsingel Tower" },
                { "CT-1006", "Groningen Martini Tower" }
        };

        java.util.Random random = new java.util.Random();
        LocalDateTime startDate = LocalDateTime.of(2025, 12, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2026, 1, 8, 23, 59);
        long startEpochSecond = startDate.toEpochSecond(java.time.ZoneOffset.UTC);
        long endEpochSecond = endDate.toEpochSecond(java.time.ZoneOffset.UTC);

        for (int i = 0; i < 10000000; i++) {
            // Random start time
            long randomStartEpoch = startEpochSecond + random.nextLong(endEpochSecond - startEpochSecond);
            LocalDateTime callStart = LocalDateTime.ofEpochSecond(randomStartEpoch, 0, java.time.ZoneOffset.UTC);

            // Random duration between 5 seconds and 90 minutes (5400 seconds)
            long durationSeconds = 5 + random.nextInt(5400 - 5 + 1);
            LocalDateTime callEnd = callStart.plusSeconds(durationSeconds);

            // Random tower
            String[] selectedTower = towers[random.nextInt(towers.length)];
            String towerId = selectedTower[0];
            String towerName = selectedTower[1];

            // Random phone numbers
            String fromNumber = generatePhoneNumber(random);
            String toNumber = generatePhoneNumber(random);

            records.add(new CallRecord(callStart, callEnd, towerId, towerName, fromNumber, toNumber));
        }

        writeCallDataToCsv("call_data.csv", records);
    }

    /**
     * Generates a random Dutch mobile phone number.
     *
     * @param random The Random instance to use for generation.
     * @return A random phone number string starting with "00316".
     */
    private static String generatePhoneNumber(java.util.Random random) {
        StringBuilder sb = new StringBuilder("00316");
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
