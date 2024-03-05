package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.Project;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.w3c.dom.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.aspose.words.*;
public class GenerateContract {
    public static void generate(Project project){
        String templatePath = "src/main/resources/contract.docx";
        String outputPath = "src/main/resources/contract2.docx";
        try {
            // Открытие шаблона docx
//            Document document = new Document("template.docx");
//
//            // Создание объекта для замены переменных
//            ReplaceOptions replaceOptions = new ReplaceOptions();
//            replaceOptions.setMatchCase(false); // Установка регистронезависимости при замене
//
//            // Замена переменных в документе
//            document.getRange().replace("{startDate.day}", "01", replaceOptions);
//            document.getRange().replace("{startDate.month}", "января", replaceOptions);
//            document.getRange().replace("{startDate.year}", "2024", replaceOptions);
//            // Продолжайте заменять остальные переменные в документе
//
//            // Сохранение заполненного документа в новый файл
//            document.save("filled_template.docx");
//
//            System.out.println("Шаблон успешно заполнен и сохранен.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
