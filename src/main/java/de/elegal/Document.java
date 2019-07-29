package de.elegal;

import java.util.Map;

public abstract class Document {
    protected Map<Integer, String> stringMap;

    public void replace(String tag, String string) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public boolean isReplaceable(String string) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public Map<Integer, String> getStringMap() {
        return stringMap;
    }
}
