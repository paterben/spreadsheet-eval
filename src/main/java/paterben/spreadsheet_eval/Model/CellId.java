package paterben.spreadsheet_eval.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A cell's column and row, e.g. A3.
 */
public class CellId {
    private String column;
    private int row;

    public CellId(String column, int row) {
        this.column = column;
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((column == null) ? 0 : column.hashCode());
        result = prime * result + row;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CellId other = (CellId) obj;
        if (column == null) {
            if (other.column != null)
                return false;
        } else if (!column.equals(other.column))
            return false;
        if (row != other.row)
            return false;
        return true;
    }

    // Group 1: column name, group 2: row num.
    private static Pattern CellIdPattern = Pattern.compile("^([a-zA-Z]+)(\\d+)$");

    /**
     * Returns whether the given string can be parsed into a cell ID.
     * 
     * @param s The cell ID in string format, e.g. "A3" or "b12".
     */
    public static boolean isValidCellIdFormat(String s) {
        if (s == null)
            return false;
        Matcher cellIdMatcher = CellIdPattern.matcher(s);
        return cellIdMatcher.matches();
    }

    /**
     * Parses a cell ID from a string.
     * 
     * @param s The cell ID in string format, e.g. "A3" or "b12".
     * @return The canonical cell ID, with column names normalized to uppercase.
     */
    public static CellId parseFromString(String s) {
        if (s == null)
            throw new IllegalArgumentException("invalid null cell ID");
        Matcher cellIdMatcher = CellIdPattern.matcher(s);
        if (cellIdMatcher.matches()) {
            String column = cellIdMatcher.group(1).toUpperCase();
            int row = Integer.parseInt(cellIdMatcher.group(2));
            return new CellId(column, row);
        }
        throw new IllegalArgumentException("invalid cell ID: " + s);
    }

    /**
     * Returns the canonical cell ID, e.g. "A3" or "B12".
     */
    public String getCanonicalId() {
        return column + row;
    }

    @Override
    public String toString() {
        return getCanonicalId();
    }
}
