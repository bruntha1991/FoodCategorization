import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bruntha on 6/4/15.
 */
public class PreProcess {
    final static String filePathRead = "/home/bruntha/Documents/FYP/Data/English/" +
            "englishReviews.json";
    final static String filePathWrite = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "test.json";
    final static String filePathWriteIndex = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "index.txt";
    int count = 0;
    int noOfNonEngRev = 0;
    int noOfEngRev = 0;
    int totalReviewsViewed = 0;
    ArrayList<String> reviewIDS = new ArrayList<>();
    Hashtable<String,Integer> businessList=new Hashtable<>();


    public static void main(String[] args) {
        PreProcess preProcess = new PreProcess();
        Utility.printHashTable(preProcess.getBusinessFrequency());
    }

    public Hashtable getBusinessFrequency(){
        try {
            readReviews(1, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return businessList;
    }

    private void readReviews(int startPosition, int endPosition) throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            if (startPosition <= totalReviewsViewed + 1)
                getBusinessFrequency(line);
//                System.out.println(line);
//                splitJson(line);    //splitting each json into ...
            totalReviewsViewed++;
            if (startPosition < totalReviewsViewed)
                writePrintStream(totalReviewsViewed);

            if (endPosition == totalReviewsViewed)
                break;
        }
        br.close();
        fr.close();
        System.out.println("Finish reading reviews");
    }

    private void getBusinessFrequency(String review) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        try {

            Object obj = parser.parse(review);
            JSONObject jsonObject = (JSONObject) obj;
            String business_id = (String) jsonObject.get("business_id");    // get review text from json

            if (businessList.containsKey(business_id)) {
                businessList.put(business_id,businessList.get(business_id)+1);
            }else {
                businessList.put(business_id,1);
            }


        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }


    private void count() throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            splitJsonCount(line);    //splitting each json into ...
        }
        System.out.println("English reviews = " + noOfEngRev);
        br.close();
        fr.close();
    }

    private void readLinesUsingFileReaderAndPrintJSON() throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            count++;
            System.out.println(line);

//            if(line.contains("6w6gMZ3iBLGcUM4RBIuifQ"))
//            {
//                System.out.println("yes");
//            }
//            if(count==300)
//                break;
        }
        System.out.println("Reviews = " + count);
        br.close();
        fr.close();
    }

    public void writePrintStream(String line) {
        PrintStream fileStream = null;
        File file = new File(filePathWrite);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

    public void writePrintStream(int line) {
        PrintStream fileStream = null;
        File file = new File(filePathWriteIndex);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

    public void splitJson(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        boolean isEnglish = false;
        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String review = (String) jsonObject.get("text");    // get review text from json
            Pattern pattern = Pattern.compile("[a-zA-Z]");
            Matcher matcher = pattern.matcher(review);


        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public void splitJsonCount(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(((JSONObject) obj).keySet());

            String reviewID = (String) jsonObject.get("review_id");

            if (reviewIDS.contains(reviewID)) {
                System.out.println("COME OVER   " + reviewID);
            } else {
                noOfEngRev++;
                reviewIDS.add(reviewID);
            }

            System.out.println(noOfEngRev);

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public static void getKeyJson(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject query = (JSONObject) jsonObject.get("query");
            Object pages = query.get("pages");

            System.out.println(((JSONObject) pages).keySet());


        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
