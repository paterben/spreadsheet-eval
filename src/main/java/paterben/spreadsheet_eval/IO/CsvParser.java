package paterben.spreadsheet_eval.IO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import paterben.spreadsheet_eval.Core.ColumnGenerator;
import paterben.spreadsheet_eval.Model.Cell;
import paterben.spreadsheet_eval.Model.Token;

public class CsvParser {
    /**
     * Parses a CSV file containing rows of expressions.
     * @param csvFile The path to the CSV file.
     * @return The list of rows, with the list of cells for each row.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<List<Cell>> parseCsvFile(String csvFile) throws FileNotFoundException, IOException {
        Reader reader = new FileReader(csvFile);
        return parseCsvReader(reader);
    }

    public static List<List<Cell>> parseCsvReader(Reader reader) throws FileNotFoundException, IOException {
        ArrayList<List<Cell>> rows = new ArrayList<List<Cell>>();
        CSVParser csvParser = CSVFormat.RFC4180.parse(reader);

        int rowNum = 1;
        for (CSVRecord csvRecord : csvParser) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            ColumnGenerator columnGenerator = new ColumnGenerator();
            for (String expr : csvRecord) {
                List<Token> tokens = ExpressionParser.parseExpressionIntoTokens(expr);
                Cell cell = new Cell(columnGenerator.Next(), rowNum, tokens);
                row.add(cell);
            }
            rows.add(row);
            rowNum++;
        }
        return rows;
    }
}
