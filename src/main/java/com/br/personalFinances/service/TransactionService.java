package com.br.personalFinances.service;

import com.br.personalFinances.entity.Transaction;
import com.br.personalFinances.repository.TransactionRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public List<Transaction> find(){
        return transactionRepository.findAll();
    }

    public Transaction delete(Long id){
        Optional<Transaction> transaction = transactionRepository.findById(id);
        transactionRepository.delete(transaction.get());
        return transaction.get();
    }

    public ResponseEntity<byte[]> downloadTransactionExcel() throws IOException {

        List<Transaction> transactionList = transactionRepository
                .findAll(Sort.by(Sort.Direction.ASC, "date"));

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Transaction Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Description");
        headerRow.createCell(1).setCellValue("Amount");
        headerRow.createCell(2).setCellValue("Date");

        for (int i = 0; i < transactionList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(transactionList.get(i).getDescription());
            row.createCell(1).setCellValue(transactionList.get(i).getAmount().doubleValue());
            row.createCell(2).setCellValue(transactionList.get(i).getDate().toString());
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "transactions.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.OK);

    }

}
