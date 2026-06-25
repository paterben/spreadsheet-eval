package paterben.spreadsheet_eval.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class CellIdTest {
    @Test
    void testParseFromString() {
        assertTrue(CellId.isValidCellIdFormat("aa11"));
        assertEquals(
                new CellId("AA", 11),
                CellId.parseFromString("aa11"));
        assertTrue(CellId.isValidCellIdFormat("Z3"));
        assertEquals(
                new CellId("Z", 3),
                CellId.parseFromString("Z3"));
    }

    @Test
    void testParseNullString() {
        assertFalse(CellId.isValidCellIdFormat(null));
        try {
            CellId.parseFromString(null);
            fail();
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e.getMessage()).contains("invalid null cell ID");
        }
    }

    @Test
    void testParseEmptyString() {
        assertFalse(CellId.isValidCellIdFormat(""));
        try {
            CellId.parseFromString("");
            fail();
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e.getMessage()).contains("invalid cell ID");
        }
    }

    @Test
    void testParseInvalidString() {
        assertFalse(CellId.isValidCellIdFormat("abc"));
        try {
            CellId.parseFromString("abc");
            fail();
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e.getMessage()).contains("invalid cell ID");
        }
    }
}
