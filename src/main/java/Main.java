import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
    }

    protected static List<Employee> parseCSV(String[] columnMapping, String fileName){
        List<Employee> employees = new ArrayList<Employee>();
        ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy();
        columnPositionMappingStrategy.setType(Employee.class);
        columnPositionMappingStrategy.setColumnMapping(columnMapping);

        try(CSVReader csvReader = new CSVReader(new FileReader(fileName))){
           String[] csvRecord;
           while((csvRecord = csvReader.readNext()) != null){
               System.out.println(Arrays.toString(csvRecord));
           }
        }catch (IOException | CsvValidationException ex) {
            System.out.println(ex.getMessage());
        }

        return employees;
    }
}
