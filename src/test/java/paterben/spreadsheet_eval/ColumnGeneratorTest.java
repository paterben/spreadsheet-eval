package paterben.spreadsheet_eval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.Column;

public class ColumnGeneratorTest {
    @Test
    void testColumnAtIndex() {
        assertEquals(new Column("A"), ColumnGenerator.ColumnAtIndex(1));
        assertEquals(new Column("B"), ColumnGenerator.ColumnAtIndex(2));
        assertEquals(new Column("Z"), ColumnGenerator.ColumnAtIndex(26));
        assertEquals(new Column("AA"), ColumnGenerator.ColumnAtIndex(27));
        assertEquals(new Column("AB"), ColumnGenerator.ColumnAtIndex(28));
        assertEquals(new Column("AZ"), ColumnGenerator.ColumnAtIndex(52));
        assertEquals(new Column("BA"), ColumnGenerator.ColumnAtIndex(53));
    }

    @Test
    void testNext() {
        var generator = new ColumnGenerator();
        assertEquals(new Column("A"), generator.Next());
        assertEquals(new Column("B"), generator.Next());
        assertEquals(new Column("C"), generator.Next());
        assertEquals(new Column("D"), generator.Next());
    }
}
