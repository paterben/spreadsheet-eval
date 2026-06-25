package paterben.spreadsheet_eval.Model;

/**
 * A cell reference token, e.g. A3.
 */
public class CellReferenceToken extends Token {
    private CellId cellId;

    public CellReferenceToken(CellId cellId) {
        this.cellId = cellId;
    }

    public CellId getCellId() {
        return cellId;
    }

    public void setCellId(CellId cellId) {
        this.cellId = cellId;
    }

    @Override
    public TokenType getType() {
        return TokenType.CELL_REFERENCE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cellId == null) ? 0 : cellId.hashCode());
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
        if (cellId == null) {
            if (other.cellId != null)
                return false;
        } else if (!cellId.equals(other.cellId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CellReferenceToken [cellId=" + cellId + "]";
    }
}
