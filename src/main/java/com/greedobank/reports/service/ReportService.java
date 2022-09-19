package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.mapper.NewsMapper;
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
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final StylesGenerator stylesGenerator;
    private final NewsDAO newsDAO;
    private final NewsMapper newsMapper;
    private final Field[] fields;
    private final int descriptionIndex = 4;

    @Autowired
    public ReportService(StylesGenerator stylesGenerator, NewsDAO newsDAO, NewsMapper mapper) {
        this.stylesGenerator = stylesGenerator;
        this.newsDAO = newsDAO;
        this.newsMapper = mapper;
        fields = News.class.getDeclaredFields();
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

        List<NewsResponseDTO> expectedNews = getExpectedNews();

        createHeaderRow(sheet, styles);

        writeNews(sheet, expectedNews, styles);

        setColumnsAutoSize(sheet, styles);
        setWarpOfDescriptionField(styles, sheet);
        formatCellToUp(sheet, styles);
        return wb;
    }

    private void formatCellToUp(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (j != descriptionIndex) {
                    row.getCell(j).setCellStyle(styles.get(CustomCellStyle.TOP_ALIGNED));
                }
            }
        }
    }

    private void setWarpOfDescriptionField(Map<CustomCellStyle, CellStyle> styles, XSSFSheet sheet) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Cell cell = sheet.getRow(i).getCell(descriptionIndex);
            cell.setCellStyle(styles.get(CustomCellStyle.WARP_TEXT_TEST));
        }
    }

    private List<NewsResponseDTO> getExpectedNews() {
        OffsetDateTime now = OffsetDateTime.now();
        return newsDAO
                .findAllExpectedNews(now)
                .stream()
                .map(newsMapper::toNewsResponseDTO)
                .collect(Collectors.toList());
    }

    private void setColumnsAutoSize(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        for (int i = 0; i < fields.length; i++) {
            if (i != descriptionIndex) {
                sheet.autoSizeColumn(i);
            } else {
                sheet.setColumnWidth(i, 10000);
            }
        }
    }

    private void createHeaderRow(Sheet sheet, Map<CustomCellStyle, CellStyle> styles) {
        var row = sheet.createRow(0);

        for (int i = 0; i < fields.length; i++) {
            var cell = row.createCell(i);
            cell.setCellValue(" " + fields[i].getName() + " ");
            cell.setCellStyle(styles.get(CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER));
        }
    }

    private void writeNews(Sheet sheet, List<NewsResponseDTO> expectedNews, Map<CustomCellStyle, CellStyle> styles) {
        if (expectedNews.size() > 0) {
            for (int i = 0; i < getExpectedNews().size(); i++) {
                var row = sheet.createRow(i + 1);
                NewsResponseDTO responseDTO = expectedNews.get(i);
                row.createCell(0).setCellValue(responseDTO.id());
                row.createCell(1).setCellValue(responseDTO.displayOnSite());
                row.createCell(2).setCellValue(responseDTO.sendByEmail());
                row.createCell(3).setCellValue(responseDTO.content().title());
                row.createCell(4).setCellValue(responseDTO.content().description());
                row.createCell(5).setCellValue(responseDTO.publicationDate().truncatedTo(ChronoUnit.MINUTES).toString());
                row.createCell(6).setCellValue(responseDTO.active());
                row.createCell(7).setCellValue(responseDTO.createdAt().truncatedTo(ChronoUnit.MINUTES).toString());
                row.createCell(8).setCellValue(responseDTO.updatedAt().truncatedTo(ChronoUnit.MINUTES).toString());
            }
        }
    }
}

