package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.*;
import com.example.ElectronicWebJournal.util.Roles;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final GroupService groupService;
    public byte[] exportToExcel(int subjectId, int groupId) {
        Group group = groupService.findById(groupId);

        List<Person> people = group.getPeopleGroups().stream()
                .sorted(Comparator.comparing(Person::getSecondName))
                .toList();

        List<Task> tasks = group.getTaskSet().stream()
                .sorted(Comparator.comparing(Task::getTaskDate))
                .toList();

        Optional<Subject> foundSubject = group.getSubjectSet().stream()
                .filter(subject -> subject.getId() == subjectId)
                .findFirst();

        if (foundSubject.isPresent()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(group.getName());
                sheet.setColumnWidth(0, 6000);
                sheet.setColumnWidth(1, 4000);

                Row sub = sheet.createRow(0);

                Row header = sheet.createRow(1);

                CellStyle headerStyle = workbook.createCellStyle();

                headerStyle.setWrapText(true);

                XSSFFont font = workbook.createFont();
                font.setFontName("Arial");
                font.setFontHeightInPoints((short) 10);
                font.setBold(true);
                headerStyle.setFont(font);
                //Ячейка с названием предмета
                Cell subCell = sub.createCell(0);
                subCell.setCellValue(foundSubject.get().getSubjectName());
                subCell.setCellStyle(headerStyle);

                //Главная информация
                Cell headerCell = header.createCell(0);
                headerCell.setCellValue("Студент");
                headerCell.setCellStyle(headerStyle);

                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);

                Map<Task, Integer> taskMap = new HashMap<>();

                for (int i = 0, j = 1; i < tasks.size(); i++) {
                    headerCell = header.createCell(j);
                    Task task = tasks.get(i);

                    LocalDate date = LocalDate.from(task.getTaskDate());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Ваш собственный шаблон формата
                    String formattedDate = date.format(formatter);

                    headerCell.setCellValue(formattedDate + " \n" + task.getDescription());
                    headerCell.setCellStyle(headerStyle);
                    taskMap.put(task, j);
                    sheet.autoSizeColumn(j);
                    j++;
                }

                for (int i = 0, j = 2; i < people.size(); i++) {

                    if(!people.get(i).getRole().equals(Roles.ROLE_STUDENT)) continue;

                    Row row = sheet.createRow(j);

                    Cell cell = row.createCell(0);
                    Person person = people.get(i);
                    cell.setCellValue(person.getFirstname() + " " + person.getSecondName());
                    cell.setCellStyle(style);
                    j++;

                    for (Task task : tasks) {
                        for (Grade grade : task.getGradeSet()) {
                            if (grade.getPerson().getId() == person.getId()) {
                                cell = row.createCell(taskMap.get(task));
                                cell.setCellValue(grade.getValue());
                                cell.setCellStyle(style);
                                break;
                            }
                        }
                    }
                }

                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    workbook.write(outputStream);
                    workbook.close();
                    return outputStream.toByteArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return new byte[0];
    }
}
