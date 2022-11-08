package com.greedobank.reports.utils;

import com.greedobank.reports.model.CustomCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StylesGenerator {

    public Map<CustomCellStyle, CellStyle> prepareStyles(Workbook wb) {

        var boldArial = createBoldArialFont(wb);
        var redBoldArial = createRedBoldArialFont(wb);
        var topAlignedStyle = createTopAlignedStyle(wb);
        var greyCenteredBoldArialWithBorderStyle = createGreyCenteredBoldArialWithBorderStyle(wb, boldArial);
        var redBoldArialWithBorderStyle = createRedBoldArialWithBorderStyle(wb, redBoldArial);
        var rightAlignedDateFormatStyle = createRightAlignedDateFormatStyle(wb);
        var warpText = createWarp(wb);

        Map<CustomCellStyle, CellStyle> mapStyles = new HashMap<>();
        mapStyles.put(CustomCellStyle.TOP_ALIGNED, topAlignedStyle);
        mapStyles.put(CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER, greyCenteredBoldArialWithBorderStyle);
        mapStyles.put(CustomCellStyle.RED_BOLD_ARIAL_WITH_BORDER, redBoldArialWithBorderStyle);
        mapStyles.put(CustomCellStyle.RIGHT_ALIGNED_DATE_FORMAT, rightAlignedDateFormatStyle);
        mapStyles.put(CustomCellStyle.WARP_TEXT_TEST, warpText);
        return mapStyles;
    }

    private Font createBoldArialFont(Workbook wb) {
        var font = wb.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        return font;
    }

    private Font createRedBoldArialFont(Workbook wb) {
        var font = wb.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.RED.index);
        return font;
    }

    private CellStyle createTopAlignedStyle(Workbook wb) {
        var style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }

    private CellStyle createBorderedStyle(Workbook wb) {
        var thin = BorderStyle.THIN;
        var black = IndexedColors.BLACK.getIndex();
        var style = wb.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }

    private CellStyle createGreyCenteredBoldArialWithBorderStyle(Workbook wb, Font boldArial) {
        var style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldArial);
        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createRedBoldArialWithBorderStyle(Workbook wb, Font redBoldArial) {
        var style = createBorderedStyle(wb);
        style.setFont(redBoldArial);
        return style;
    }

    private CellStyle createRightAlignedDateFormatStyle(Workbook wb) {
        var style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }

    private CellStyle createWarp(Workbook wb) {
        var style = wb.createCellStyle();
        style.setWrapText(true);
        return style;
    }
}
