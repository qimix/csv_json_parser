import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String jsonFileName = "data.json";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, jsonFileName);
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

    protected static void writeString(String json, String jsonFileName){
        try(Writer writer = new FileWriter(jsonFileName)){
            writer.write(json.toCharArray());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
