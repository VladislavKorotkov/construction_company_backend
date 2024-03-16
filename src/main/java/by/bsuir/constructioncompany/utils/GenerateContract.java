package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.Project;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateContract {
    public static void generate(Project project, int cost){
        try {
            FileInputStream templateFile = new FileInputStream("src/main/resources/contract_template.docx");
            XWPFDocument document = new XWPFDocument(templateFile);
            Map<String, String> replacementMap = getStringStringMap(project, cost);
            replaceText(document, replacementMap);
            FileOutputStream out = new FileOutputStream("output.docx");
            document.write(out);
            out.close();
            System.out.println("Документ успешно создан.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> getStringStringMap(Project project, int cost) {
        Map<String, String> replacementMap = new HashMap<>();
        replacementMap.put("day", "1");
        replacementMap.put("month", "2");
        replacementMap.put("year", "2993");
        replacementMap.put("surname", project.getUser().getSurname());
        replacementMap.put("name", project.getUser().getName());
        replacementMap.put("city", project.getAddress().getCity());
        replacementMap.put("street", project.getAddress().getStreet());
        replacementMap.put("house", project.getAddress().getNumberHouse());
        replacementMap.put("cost", String.valueOf(cost));
        replacementMap.put("phone", project.getUser().getPhoneNumber());
        return replacementMap;
    }

    private static void replaceText(XWPFDocument document, Map<String, String> replacementMap) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
                        String findText = entry.getKey();
                        String replaceText = entry.getValue();
                        if (text.contains(findText)) {
                            text = text.replace(findText, replaceText);
                        }
                    }
                    run.setText(text, 0);
                }
            }
        }
    }

}
