# Spreadsheet evaluation

Simple command-line application that reads in a CSV file containing spreadsheet formulas in [reverse Polish notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation) and outputs the result.

Supports cell references in formulas. Cells are numbered `A1`, `B1`, `C1`, ... for the first row, `A2`, `B2`, `C2`, ... for the second row, and so on, similar to the format used in Excel.

# Prerequisites

Install a Java JDK (21 or later), e.g. [Microsoft OpenJDK](https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-21).

# Building and running

To build and run tests:

```shell
mvn clean package
```

To run with [`testinput.csv`](testinput.csv):

```shell
java -jar target/spreadsheet_eval-1.0-SNAPSHOT.jar testinput.csv
```
