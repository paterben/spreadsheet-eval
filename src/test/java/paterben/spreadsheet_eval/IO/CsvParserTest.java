package paterben.spreadsheet_eval.IO;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.Cell;
import paterben.spreadsheet_eval.Model.CellId;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.NumberToken;

public class CsvParserTest {

    @Test
    void testParseValidCsv() throws FileNotFoundException, IOException {
        // Arrange
        String csvData = "10,       1 3 +,  2 3 -\n" +
                "b1 b2 *,  a1";
        StringReader reader = new StringReader(csvData);

        // Act
        List<List<Cell>> rows = CsvParser.parseCsvReader(reader);

        // Assert
        assertThat(rows).hasSize(2);
        assertThat(rows.get(0)).hasSize(3);
        assertThat(rows.get(0)).containsExactly(
                new Cell(new CellId("A", 1), List.of(
                        new NumberToken(10))),
                new Cell(new CellId("B", 1), List.of(
                        new NumberToken(1), new NumberToken(3), new BinaryOperatorToken(BinaryOperatorTokenType.ADD))),
                new Cell(new CellId("C", 1), List.of(
                        new NumberToken(2), new NumberToken(3), new BinaryOperatorToken(BinaryOperatorTokenType.SUB))));
        assertThat(rows.get(1)).hasSize(2);
        assertThat(rows.get(1)).containsExactly(
                new Cell(new CellId("A", 2), List.of(
                        new CellReferenceToken(new CellId("B", 1)), new CellReferenceToken(new CellId("B", 2)),
                        new BinaryOperatorToken(BinaryOperatorTokenType.MULT))),
                new Cell(new CellId("B", 2), List.of(
                        new CellReferenceToken(new CellId("A", 1)))));
    }

    @Test
    void testParseEmptyCsv() throws FileNotFoundException, IOException {
        // Arrange
        StringReader reader = new StringReader("");

        // Act
        List<List<Cell>> rows = CsvParser.parseCsvReader(reader);

        // Assert
        assertThat(rows).isEmpty();
    }
}
