package de.elegal.Documents;

import de.elegal.CSVHandle;

import javax.naming.OperationNotSupportedException;
import java.util.Collection;

public class DocumentFactory {
    public static Collection<Document> createDocumentsFromCSV(Document doc, DocumentTypes type,
                                                       CSVHandle csv) throws OperationNotSupportedException{
        switch (type) {
            case ODT:
                throw new OperationNotSupportedException();
            case DOCX:
                throw new OperationNotSupportedException();
        }
        return null; // Not reachable
    }
}
