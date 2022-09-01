package com.greedobank.reports.service;

import com.greedobank.reports.model.CustomCellStyle;
import com.greedobank.reports.utils.StylesGenerator;
import lombok.val;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class ReportService {
    private final StylesGenerator stylesGenerator;

    @Autowired
    public ReportService(StylesGenerator stylesGenerator) {
        this.stylesGenerator = stylesGenerator;
    }

    public Workbook generateXlsxReport() {
        var wb = new XSSFWorkbook();
        return generateReport(wb);
    }

    private Workbook generateReport(XSSFWorkbook wb) {
        var styles = stylesGenerator.prepareStyles(wb);
        var sheet = wb.createSheet("Example sheet name");

        setColumnsWidth(sheet);

        createHeaderRow(sheet, styles);
        createStringsRow(sheet, styles);
        createDoublesRow(sheet, styles);
        createDatesRow(sheet, styles);
        return wb;
    }

    private void setColumnsWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 256 * 20);

        for (int i = 1; i < 5; i++) {
            sheet.setColumnWidth(i, 256 * 15);
        }
    }

    private void createHeaderRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        var row = sheet.createRow(0);

        for (int i = 1; i < 5; i++) {
            val cell = row.createCell(i);

            cell.setCellValue("Column " +i);
            cell.setCellStyle(styles.get(CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER));
        }
    }

    private void createRowLabelCell(Row row, Map<CustomCellStyle, CellStyle> styles, String label) {
        var rowLabel = row.createCell(0);
        rowLabel.setCellValue(label);
        rowLabel.setCellStyle(styles.get(CustomCellStyle.RED_BOLD_ARIAL_WITH_BORDER));
    }

    private void createStringsRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        var row = sheet.createRow(1);
        createRowLabelCell(row, styles, "Strings row");

        for (int i = 1; i < 5; i++) {
            val cell = row.createCell(i);

            cell.setCellValue("String " +i);
            cell.setCellStyle(styles.get(CustomCellStyle.RIGHT_ALIGNED));
        }
    }

    private void createDoublesRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        val row = sheet.createRow(2);
        createRowLabelCell(row, styles, "Doubles row");

        for (int i = 1; i < 5; i++) {
            val cell = row.createCell(i);

            cell.setCellValue(new BigDecimal("1.99").doubleValue());
            cell.setCellStyle(styles.get(CustomCellStyle.RIGHT_ALIGNED));
        }
    }

    private void createDatesRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        var row = sheet.createRow(3);
        createRowLabelCell(row, styles, "Dates row");

        for (int i = 1; i < 5; i++) {
            val cell = row.createCell(i);

            cell.setCellValue((LocalDate.now()));
            cell.setCellStyle(styles.get(CustomCellStyle.RIGHT_ALIGNED_DATE_FORMAT));
        }
    }
}

