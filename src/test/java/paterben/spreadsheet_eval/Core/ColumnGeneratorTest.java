package paterben.spreadsheet_eval.Core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColumnGeneratorTest {
    @Test
    void testColumnAtIndex() {
        assertEquals("A", ColumnGenerator.ColumnAtIndex(1));
        assertEquals("B", ColumnGenerator.ColumnAtIndex(2));
        assertEquals("Z", ColumnGenerator.ColumnAtIndex(26));
        assertEquals("AA", ColumnGenerator.ColumnAtIndex(27));
        assertEquals("AB", ColumnGenerator.ColumnAtIndex(28));
        assertEquals("AZ", ColumnGenerator.ColumnAtIndex(52));
        assertEquals("BA", ColumnGenerator.ColumnAtIndex(53));
    }

    @Test
    void testNext() {
        var generator = new ColumnGenerator();
        assertEquals("A", generator.Next());
        assertEquals("B", generator.Next());
        assertEquals("C", generator.Next());
        assertEquals("D", generator.Next());
    }
}
