import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
//loop through each job (row) in allJobs

        for (HashMap<String, String> row : allJobs) {
//loop through each job property (column) in a single job (name, location, etc)
            //get the information and store it in aValue
            String aValue = row.get(column);
//if aValue contains a value, add it to my Hashmap titled row - this isn't right but something like it
            System.out.println("aValue outside of the if block" + aValue);
            if ((aValue.toUpperCase().contains(value.toUpperCase()))){
                System.out.println("aValue in the if block is" + aValue);
                System.out.println("I'm on the if block of findByColumnAndValue");
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        // TODO - implement this method
//first create an ArrayList object to hold the jobs
        // if ((searchField.equals("all")) && (searchField.toUpperCase().contains(searchTerm.toUpperCase()))) {
        //if (column.toUpperCase().contains(propertyValue.toUpperCase())) {
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
        System.out.println("I'm in findByValue");
        //for every row (job) in allJobs - in order to loop through them we have to get them first
        for (HashMap<String, String> row : allJobs) { //for every row in allJobs
            for (String column : row.keySet()) {//for every column in that row, get the value
                String propertyValue = row.get(column);//put the column value into jobPropertyValue
                //column.toUpperCase();//I don't care about the column. That's the heading. I want the value

                    if (propertyValue.toUpperCase().contains(value.toUpperCase())) {

                        System.out.println("value is:" + value + "and propertyValue is:" + propertyValue );
                        jobs.add(row);//may need to be jobs.add(column)
                    }
                }



        }
        return jobs;
    }




    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
