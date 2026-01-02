# Cell Tower Call Data Generator

This project is a Java application designed to generate synthetic call data records for testing and analysis purposes. It simulates a large volume of phone calls and exports the data to a CSV file.

## Features

-   **High Volume Generation**: Generates 10,000,000 synthetic call records.
-   **Realistic Data**:
    -   **Time Range**: Simulates calls occurring between December 1, 2025, and January 8, 2026.
    -   **Call Duration**: Random durations between 5 seconds and 90 minutes.
    -   **Locations**: Randomly assigns calls to a set of predefined cell towers in major Dutch cities (Utrecht, Maastricht, Amsterdam, Leiden, Rotterdam, Groningen).
    -   **Phone Numbers**: Generates random Dutch mobile numbers (starting with 00316).
-   **CSV Export**: Writes the generated data to `call_data.csv` with the following columns:
    -   `date-time-start`
    -   `date-time-end`
    -   `celltower-id`
    -   `celltower-name`
    -   `from-number`
    -   `to-number`

## Getting Started

### Prerequisites

-   Java Development Kit (JDK) installed.
-   Visual Studio Code with the "Extension Pack for Java" recommended.

### Running the Application

1.  Open the project in Visual Studio Code.
2.  Navigate to `src/CallDataWriter.java`.
3.  Run the `main` method.
4.  The application will generate the data and create a `call_data.csv` file in the project root.

## Output

The output file `call_data.csv` will contain records in the following format:

```csv
date-time-start,date-time-end,celltower-id,celltower-name,from-number,to-number
2025-12-01T10:15:30,2025-12-01T10:20:45,CT-1003,Amsterdam Amstel Tower,0031612345678,0031687654321
...
```
