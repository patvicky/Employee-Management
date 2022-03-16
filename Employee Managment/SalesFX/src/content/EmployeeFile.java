package content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EmployeeFile {

    private static final String FILE_EMPLOYEE = "Employee.dat";

    /**
     * 
     * @return true if file is exist else false
     */
    public static boolean isFileExists() {
        File file = new File(FILE_EMPLOYEE);
        return file.exists();
    }

    /**
     * Initialize first time when file is not created.
     * @param employeeList 
     */
    public static void setupFileAndWriteInitialData(ArrayList<Employee> employeeList) {
        try {
            File file = new File(FILE_EMPLOYEE);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    setInitialData(employeeList);
                    // adding data
                    writeData(employeeList);
                }
            }
        } catch (IOException e) {
            System.err.println("File not created");
        }
    }

    /**
     * Write data in file from list
     * @param employeeList 
     */
    public static void writeData(ArrayList<Employee> employeeList) {
        try {
            try ( FileWriter fileWriter = new FileWriter(FILE_EMPLOYEE);  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

                // now write into file
                for (Employee employee : employeeList) {
                    bufferedWriter.write(employee.toString());
                    bufferedWriter.newLine();
                }
            }

        } catch (IOException ex) {
            System.err.println("Can not write into file.");
        }

    }

    /**
     * Read data from file
     * @return 
     */
    public static ArrayList<Employee> readData() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {
            try ( FileReader fileReader = new FileReader(FILE_EMPLOYEE);  BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                List<String> data = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    data.add(line);
                }
                bufferedReader.close();
                if (data.size() > 0) {
                    for (String rowData : data) {
                        String[] items = rowData.split("\\s*,\\s*");
                        employeeList.add(new Employee(Integer.parseInt(items[0]), items[1], items[2], items[3]));
                    }
                }
            }

        } catch (IOException ex) {
            System.err.println("Can not write into file.");
        }
        return employeeList;
    }

    /**
     * Set default 10 data in list
     * @param employeeList 
     */
    private static void setInitialData(ArrayList<Employee> employeeList) {
        employeeList.add(new Employee(100, "Ravi", "Toronto", "SalesPerson"));
        employeeList.add(new Employee(101, "Vicky", "Brampton", "Worker"));
        employeeList.add(new Employee(102, "John", "Hamilton", "Worker"));
        employeeList.add(new Employee(103, "Peter", "Windsor", "SalesPerson"));
        employeeList.add(new Employee(104, "Alex", "Kitchner", "SalesPerson"));
        employeeList.add(new Employee(105, "Nikunj", "Toronto", "Worker"));
        employeeList.add(new Employee(106, "Raj", "Hamilton", "Worker"));
        employeeList.add(new Employee(107, "Wilson", "Windsor", "SalesPerson"));
        employeeList.add(new Employee(108, "Morris", "Toronto", "Worker"));
        employeeList.add(new Employee(109, "Nirmal", "Brampton", "SalesPerson"));
    }
}
