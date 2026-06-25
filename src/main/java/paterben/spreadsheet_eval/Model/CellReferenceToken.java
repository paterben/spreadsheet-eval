package paterben.spreadsheet_eval.Model;

/**
 * A cell reference token, e.g. A3.
 */
public class CellReferenceToken extends Token {
    private Column column;
    private int row;

    public CellReferenceToken(Column column, int row) {
        this.column = column;
        this.row = row;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public TokenType getType() {
        return TokenType.CELL_REFERENCE;
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
        CellReferenceToken other = (CellReferenceToken) obj;
        if (column == null) {
            if (other.column != null)
                return false;
        } else if (!column.equals(other.column))
            return false;
        if (row != other.row)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CellReferenceToken [column=" + column + ", row=" + row + "]";
    }
}
