package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.model.CustomCellStyle;
import com.greedobank.reports.model.News;
import com.greedobank.reports.utils.StylesGenerator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final StylesGenerator stylesGenerator;
    private final NewsDAO newsDAO;
    private final static int DESCRIPTION_INDEX = 4;
    private final static int NUMBERS_OF_COLUMNS = 9;


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
        var sheet = wb.createSheet("News unpublished");

        List<News> expectedNews = getExpectedNews();

        createHeaderRow(sheet, styles);

        writeNews(sheet, expectedNews);

        setColumnsAutoSize(sheet);
        setWarpOfDescriptionField(styles, sheet);
        formatCellToUp(sheet, styles);
        return wb;
    }

    private void formatCellToUp(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (j != DESCRIPTION_INDEX) {
                    row.getCell(j).setCellStyle(styles.get(CustomCellStyle.TOP_ALIGNED));
                }
            }
        }
    }

    private void setWarpOfDescriptionField(Map<CustomCellStyle, CellStyle> styles, XSSFSheet sheet) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Cell cell = sheet.getRow(i).getCell(DESCRIPTION_INDEX);
            cell.setCellStyle(styles.get(CustomCellStyle.WARP_TEXT_TEST));
        }
    }

    private List<News> getExpectedNews() {
        return newsDAO.findAllExpectedNews(OffsetDateTime.now());
    }

    private void setColumnsAutoSize(Sheet sheet) {
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            if (i != DESCRIPTION_INDEX) {
                sheet.autoSizeColumn(i);
            } else {
                sheet.setColumnWidth(i, 10000);
            }
        }
    }

    private void createHeaderRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        var row = sheet.createRow(0);
        String[] headersOfReport = createHeadersOfReport();
        for (int i = 0; i < NUMBERS_OF_COLUMNS; i++) {
            var cell = row.createCell(i);
            cell.setCellValue(" " + headersOfReport[i] + " ");
            cell.setCellStyle(styles.get(CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER));
        }
    }

    private String[] createHeadersOfReport() {
        return new String[]{
                "news_id",
                "display_on_site",
                "send_by_email",
                "title",
                "description",
                "publication_date",
                "active",
                "created_at",
                "updated_at"};
    }

    private void writeNews(Sheet sheet, List<News> expectedNews) {
        if (expectedNews.size() > 0) {
            for (int i = 0; i < getExpectedNews().size(); i++) {
                var row = sheet.createRow(i + 1);
                News responseDTO = expectedNews.get(i);
                int index = 0;
                row.createCell(index++).setCellValue(responseDTO.getId());
                row.createCell(index++).setCellValue(responseDTO.isDisplayOnSite());
                row.createCell(index++).setCellValue(responseDTO.isSendByEmail());
                row.createCell(index++).setCellValue(responseDTO.getTitle());
                row.createCell(index++).setCellValue(responseDTO.getDescription());
                row.createCell(index++).setCellValue(responseDTO.getPublicationDate().truncatedTo(ChronoUnit.MINUTES).toString());
                row.createCell(index++).setCellValue(responseDTO.isActive());
                row.createCell(index++).setCellValue(responseDTO.getCreatedAt().truncatedTo(ChronoUnit.MINUTES).toString());
                row.createCell(index++).setCellValue(responseDTO.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES).toString());
            }
        }
    }
}

