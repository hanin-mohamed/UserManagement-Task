package com.task.employeemanagement.users.service;

import com.task.employeemanagement.users.entity.User;
import com.task.employeemanagement.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserExportService {

    private final UserRepository userRepository;

    public byte[] exportUsersExcel(Long currentUserId) throws IOException {
        List<User> users = userRepository.findByIdNot(currentUserId);

        Workbook wb = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

            Sheet sheet = wb.createSheet("Users");

            CellStyle headerStyle = wb.createCellStyle();
            Font bold = wb.createFont(); bold.setBold(true);
            headerStyle.setFont(bold);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            String[] headers = {"Full Name", "Username", "Email", "Salary", "Created At", "Updated At"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            int r = 1;
            for (User u : users) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(u.getFullName());
                row.createCell(1).setCellValue(u.getUsername());
                row.createCell(2).setCellValue(u.getEmail());
                row.createCell(3).setCellValue(u.getSalary()!=null ? u.getSalary().doubleValue() : BigDecimal.ZERO.doubleValue());
                row.createCell(4).setCellValue(u.getCreatedAt() != null ? dtf.format(u.getCreatedAt()) : "");
                row.createCell(5).setCellValue(u.getUpdatedAt() != null ? dtf.format(u.getUpdatedAt()) : "");
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            wb.write(out);
            return out.toByteArray();
    }

}
