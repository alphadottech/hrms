package com.adt.hrms.service.impl;

import com.adt.hrms.model.ProjectEngagement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Helper {
    public static String[] Headers = {"projectId", "endDate", "primaryResource", "secondaryResource", "endClient", "contractor", "startDate", "status"};
    public static String Sheet_name = "ProjectEngagementExcel_data";

    public static ByteArrayInputStream dataToExcel(List<ProjectEngagement> list) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(Sheet_name);


            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);


            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < Headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(Headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIndex = 1;
            for (ProjectEngagement pe : list) {
                Row rowData = sheet.createRow(rowIndex++);
                rowData.createCell(0).setCellValue(pe.getProjectId());
                rowData.createCell(1).setCellValue(pe.getEndDate());
                rowData.createCell(2).setCellValue(pe.getPrimaryResource());
                rowData.createCell(3).setCellValue(pe.getSecondaryResource());
                rowData.createCell(4).setCellValue(pe.getEndClient());
                rowData.createCell(5).setCellValue(pe.getContractor());
                rowData.createCell(6).setCellValue(pe.getStartDate());
                rowData.createCell(7).setCellValue(pe.isStatus());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed");
            return null;
        } finally {
            workbook.close();
            out.close();
        }
    }
}