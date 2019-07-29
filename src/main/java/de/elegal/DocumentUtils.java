package de.elegal;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

// TODO: Multiple Ocurrences

public class DocumentUtils {
  public static XWPFDocument openFile(final String str) throws NullPointerException, InvalidFormatException, IOException {
    Objects.requireNonNull(str);
    File file = new File(str);
    OPCPackage opcPackage = OPCPackage.open(file);
    Objects.requireNonNull(opcPackage, "Something went wrong opening " + str);
    return new XWPFDocument(opcPackage);
  }

  public static void writeFile(final XWPFDocument doc, final String str) throws NullPointerException, IOException {
    Objects.requireNonNull(doc);
    Objects.requireNonNull(str);
    doc.write(new FileOutputStream(str));
  }

  // Not working with tables
  // If needed, should look sth like that https://stackoverflow.com/a/22269035
  // Also, it allows to replace multiple occurences
  // TODO: Currently returns XWPFDocument via Reference
  public static boolean replaceText(XWPFDocument doc, final String tag, final String replace) {
    if(tag == null) return false;
    Objects.requireNonNull(doc);
    Objects.requireNonNull(replace);
    boolean tagFound = false;
     List<XWPFRun> debug = doc.getParagraphs()
             .stream()
             .map(XWPFParagraph::getRuns)
             .filter(Objects::nonNull)
             .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    for (XWPFRun r : debug) {
      String text = r.getText(0);
      if(text != null && text.contains(tag)) {
        System.out.println(text);
        text = text.replace(tag, replace);
        r.setText(text, 0);
        tagFound = true;
      }
    }
    return tagFound;
  }
}
