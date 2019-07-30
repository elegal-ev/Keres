package de.elegal;

import java.util.Map;

public class Employee extends CSVHandle implements TagStrings {

    public Employee(String csvFilePath) {
        super(csvFilePath);
    }

    @Override
    public Map<String, String> getSubstitutions() {
        return null;
    }
}
