package me.levansj01.verus.data.state.lazy;

class Ref<T> {
    private final T value;

    @Override
    public int hashCode() {
        byte b = 59;
        byte b2 = 1;
        Object value = this.getValue();
        return b2 * 59 + (value == null ? 43 : value.hashCode());
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Ref)) {
            return false;
        }

        Ref ref = (Ref)obj;
        if (!ref.canEqual(this)) {
            return false;
        }

        Object value = this.getValue();
        Object value2 = ref.getValue();
        return value == null ? value2 == null : value.equals(value2);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Ref;
    }

    @Override
    public String toString() {
        return "Ref(value=" + this.getValue() + ")";
    }

    public Ref(T value) {
        this.value = value;
    }
}
