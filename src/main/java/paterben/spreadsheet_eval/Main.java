package paterben.spreadsheet_eval;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import paterben.spreadsheet_eval.Core.ExpressionResolver;
import paterben.spreadsheet_eval.Core.GraphResolver;
import paterben.spreadsheet_eval.IO.CsvParser;
import paterben.spreadsheet_eval.Model.Cell;

public class Main {
    private static final DecimalFormat FiveDigitFormat = new DecimalFormat("#.#####");

    public static void processCsvSpreadsheet(String csvFile) throws FileNotFoundException, IOException {
        Reader reader = new FileReader(csvFile);
        processCsvSpreadsheet(reader);
    }

    public static void processCsvSpreadsheet(Reader csvReader) throws FileNotFoundException, IOException {
        List<List<Cell>> cells = CsvParser.parseCsvReader(csvReader);
        ExpressionResolver expressionResolver = new ExpressionResolver();
        GraphResolver graphResolver = new GraphResolver(expressionResolver);
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);
        for (List<Optional<Double>> row : rows) {
            List<String> results = row.stream()
                    .map(od -> {
                        if (od.isEmpty()) {
                            return "#ERR";
                        }
                        return FiveDigitFormat.format(od.get());
                    })
                    .toList();
            System.out.println(String.join(",", results));
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length != 1) {
            System.out.println("Expected 1 command line argument, got " + args.length);
            return;
        }
        processCsvSpreadsheet(args[0]);
    }
}
