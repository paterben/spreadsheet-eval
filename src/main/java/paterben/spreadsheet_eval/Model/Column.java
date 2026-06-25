package paterben.spreadsheet_eval.Model;

/**
 * A column, e.g. A, B or AA.
 */
public class Column {
    public String canonicalValue;

    public Column(String canonicalValue) {
        this.canonicalValue = canonicalValue;
    }

    public String getCanonicalValue() {
        return canonicalValue;
    }

    public void setCanonicalValue(String canonicalValue) {
        this.canonicalValue = canonicalValue;
    }

    public static Column fromString(String s) throws IllegalArgumentException {
        if (!s.chars().allMatch(Character::isLetter)) {
            throw new IllegalArgumentException("Invalid column: " + s);
        }
        return new Column(s.toUpperCase());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((canonicalValue == null) ? 0 : canonicalValue.hashCode());
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
        Column other = (Column) obj;
        if (canonicalValue == null) {
            if (other.canonicalValue != null)
                return false;
        } else if (!canonicalValue.equals(other.canonicalValue))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Column [canonicalValue=" + canonicalValue + "]";
    }
}
