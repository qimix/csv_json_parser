import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
    }

    protected static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> staff = new ArrayList<Employee>();

        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);

        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            staff = csv.parse();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return staff;
    }

    protected static String listToJson(List<Employee> staff){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        String json = gson.toJson(staff, listType);
        return json;
    }

}
