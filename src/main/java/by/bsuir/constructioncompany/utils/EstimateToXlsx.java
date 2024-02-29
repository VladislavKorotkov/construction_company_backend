package by.bsuir.constructioncompany.utils;


import by.bsuir.constructioncompany.responses.EstimateResponse;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class EstimateToXlsx {
    public static byte[] convertEstimateResponseToXlsx(EstimateResponse estimateResponse){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Estimate");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Название материала");
        headerRow.createCell(1).setCellValue("Стоимость материала");
        headerRow.createCell(2).setCellValue("Количество материала");
        headerRow.createCell(3).setCellValue("Общая стоимость материала");

        List<MaterialProjectResponse> materials = estimateResponse.getMaterials();
        int rowNum = 1;
        for (MaterialProjectResponse material : materials) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(material.getMaterialName());
            row.createCell(1).setCellValue(material.getCost());
            row.createCell(2).setCellValue(material.getQuantity());
            row.createCell(3).setCellValue(material.getTotalCost());
        }

        sheet.createRow(rowNum++);

        Row workHeaderRow = sheet.createRow(rowNum++);
        workHeaderRow.createCell(0).setCellValue("Название работы");
        workHeaderRow.createCell(1).setCellValue("Стоимость работы");
        workHeaderRow.createCell(2).setCellValue("Количество работы");
        workHeaderRow.createCell(3).setCellValue("Общая стоимость работы");

        List<WorkProjectResponse> works = estimateResponse.getWorks();
        for (WorkProjectResponse work : works) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(work.getWorkName());
            row.createCell(1).setCellValue(work.getCost());
            row.createCell(2).setCellValue(work.getQuantity());
            row.createCell(3).setCellValue(work.getTotalCost());
        }

        Row totalCostRow = sheet.createRow(rowNum);
        totalCostRow.createCell(0).setCellValue("Общая стоимость");
        totalCostRow.createCell(1).setCellValue(estimateResponse.getTotalCost());

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        byte[] excelBytes;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            excelBytes = byteArrayOutputStream.toByteArray();
            return excelBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
