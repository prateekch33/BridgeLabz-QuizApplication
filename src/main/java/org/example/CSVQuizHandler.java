package org.example;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVQuizHandler {
    private String filePath;
    public CSVQuizHandler(String filePath) throws QuizException {
        this.filePath=filePath;
        try {
            File file=new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new QuizException(e.getMessage());
        }
    }
    public void writeToCSV(List<String[]> users) throws QuizException {
        try (
            CSVWriter writer=new CSVWriter(new FileWriter(filePath));) {
            String[] headers={"Id","Name"};
            writer.writeNext(headers);
            for(String[] user:users) {
                writer.writeNext(user);
            }
        } catch (IOException e) {
            throw new QuizException(e.getMessage());
        }
    }
}
