package paterben.spreadsheet_eval;

import paterben.spreadsheet_eval.Model.Column;

public class ColumnGenerator {
    private int index = 1;
    private static final int NumLetters = 26;

    public ColumnGenerator() {
    }

    /**
     * Returns the next column: A, B, ..., Z, AA, ...
     */
    public Column Next() {
        return ColumnAtIndex(index++);
    }

    /**
     * Returns the indexed column in this order: A, B, ..., Z, AA, ...
     * @param idx The column index (starting at 0 for A).
     */
    public static Column ColumnAtIndex(int idx) {
        if (idx < 1) {
            throw new IllegalArgumentException("Invalid column index: " + idx);
        }
        StringBuilder sb = new StringBuilder();
        while (idx >= 1) {
            int rem = (idx - 1) % NumLetters;
            idx = (idx - 1) / NumLetters;
            char c = (char) ('A' + rem);
            sb.append(c);
        }
        sb.reverse();
        return new Column(sb.toString());
    }
}
