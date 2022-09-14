package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.model.CustomCellStyle;
import com.greedobank.reports.utils.StylesGenerator;
import lombok.val;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final StylesGenerator stylesGenerator;
    private final NewsDAO newsDAO;
    private final int size = NewsResponseDTO.class.getDeclaredFields().length;
    private final Field[] declaredFields = NewsResponseDTO.class.getDeclaredFields();


    @Autowired
    public ReportService(StylesGenerator stylesGenerator, NewsDAO newsDAO) {
        this.stylesGenerator = stylesGenerator;
        this.newsDAO = newsDAO;
    }

    public byte[] generateXlsxReport() throws IOException {
        var wb = new XSSFWorkbook();
        generateReport(wb);
        try (var out = new ByteArrayOutputStream()) {
            wb.write(out);
            return out.toByteArray();
        }
    }

    private Workbook generateReport(XSSFWorkbook wb) {
        var styles = stylesGenerator.prepareStyles(wb);
        var sheet = wb.createSheet("News expected");

        List<NewsResponseDTO> expectedNews = getExpectedNews();

        createHeaderRow(sheet, styles);
        setColumnsWidth(sheet);
//        createStringsRow(sheet, styles, expectedNews);
//        createDoublesRow(sheet, styles, expectedNews);
//        createDatesRow(sheet, styles, expectedNews);
        writeNews(sheet, expectedNews);
        setColumnsWidth(sheet);
        return wb;
    }

    private List<NewsResponseDTO> getExpectedNews() {
        OffsetDateTime now = OffsetDateTime.now();
        return newsDAO.findAllExpectedNews(now);
    }

    private void setColumnsWidth(Sheet sheet) {
        for (int i = 0; i < size; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createHeaderRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        var row = sheet.createRow(0);
        Field[] declaredFields = NewsResponseDTO.class.getDeclaredFields();

        for (int i = 0; i < size; i++) {
            val cell = row.createCell(i);
            cell.setCellValue(" " + declaredFields[i].getName() + " ");
            cell.setCellStyle(styles.get(CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER));
        }
    }

    private void writeNews(Sheet sheet, List<NewsResponseDTO> expectedNews) {
        if (expectedNews.size() > 0) {
            var row = sheet.createRow(1);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < getExpectedNews().size(); j++) {
                    val cell = row.createCell(i);
                    cell.setCellValue(expectedNews.get(j).id());
                    cell.setCellValue(expectedNews.get(j).displayOnSite());
                    cell.setCellValue(expectedNews.get(j).sendByEmail());
                    cell.setCellValue(expectedNews.get(j).content().title());
                    cell.setCellValue(expectedNews.get(j).content().description());
                    cell.setCellValue(expectedNews.get(j).publicationDate().toLocalDateTime());
                    cell.setCellValue(expectedNews.get(j).active());
                    cell.setCellValue(expectedNews.get(j).createdAt().toLocalDateTime());
                    cell.setCellValue(expectedNews.get(j).updatedAt().toLocalDateTime());
                }
            }
        }
    }

//    private void createRowLabelCell(Row row, Map<CustomCellStyle, CellStyle> styles, String label, List<NewsResponseDTO> expectedNews) {
//        var rowLabel = row.createCell(0);
//        rowLabel.setCellValue(label);
//        rowLabel.setCellStyle(styles.get(CustomCellStyle.RED_BOLD_ARIAL_WITH_BORDER));
//    }

//    private void createStringsRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles, List<NewsResponseDTO> expectedNews) {
//        int quantity = expectedNews.size();
//        var row = sheet.createRow(1);
//        createRowLabelCell(row, styles, "Strings row");
//
//        for (int i = 1; i < 5; i++) {
//            val cell = row.createCell(i);
//
//            cell.setCellValue("String " + i);
//            cell.setCellStyle(styles.get(CustomCellStyle.RIGHT_ALIGNED));
//        }
//    }

//    private void createDoublesRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles, List<NewsResponseDTO> expectedNews) {
//        val row = sheet.createRow(2);
//        createRowLabelCell(row, styles, "Doubles row");
//
//        for (int i = 1; i < 5; i++) {
//            val cell = row.createCell(i);
//
//            cell.setCellValue(new BigDecimal("1.99").doubleValue());
//            cell.setCellStyle(styles.get(CustomCellStyle.RIGHT_ALIGNED));
//        }
//    }

//    private void createDatesRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles, List<NewsResponseDTO> expectedNews) {
//        var row = sheet.createRow(3);
//        createRowLabelCell(row, styles, "Dates row");
//
//        for (int i = 1; i < 5; i++) {
//            val cell = row.createCell(i);
//
//            cell.setCellValue((LocalDate.now()));
//            cell.setCellStyle(styles.get(CustomCellStyle.RIGHT_ALIGNED_DATE_FORMAT));
//        }
//    }
}

