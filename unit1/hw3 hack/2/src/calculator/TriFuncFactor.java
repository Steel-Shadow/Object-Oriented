package calculator;

public abstract class TriFuncFactor extends IndexFactor {
    private final Vector content;

    public TriFuncFactor(FactorKind factorKind, int index, Vector content) {
        super(factorKind, index);
        this.content = content;
    }

    public Vector getContent() {
        return content;
    }

    @Override
    public int joinCompare(IndexFactor o) {
        int result = super.joinCompare(o);
        if (result == 0) {
            return content.compareTo(((TriFuncFactor) o).content);
        } else {
            return result;
        }
    }

    @Override
    protected final int compare(IndexFactor o) {
        if (getIndex() == o.getIndex()) {
            return content.compareTo(((TriFuncFactor) o).getContent());
        } else {
            return Integer.compare(getIndex(), o.getIndex());
        }
    }
}
