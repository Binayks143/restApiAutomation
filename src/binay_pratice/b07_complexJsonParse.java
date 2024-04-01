package binay_pratice;

import files.payload;
import io.restassured.path.json.JsonPath;

public class b07_complexJsonParse {
    public static void main(String[] args) {

        JsonPath js = new JsonPath(payload.CoursePrice());

        // 1. Print No of courses returned by API
        //Note : getInt Method used for number and for array i.e. start within []
        int NoOfCourses=js.getInt("courses.size()");
        System.out.println("No of courses = "  +NoOfCourses);

        // 2.Print Purchase Amount

        int purchaseAmount=js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount= "+purchaseAmount);

        // Print Title of the first course

        String title=js.get("courses[0].title");
        System.out.println("Title of the first course= "+title);

        //Print All course titles and their respective Prices
        for (int i=0;i<NoOfCourses;i++)
        {
            String t1=js.get("courses["+i+"].title");
            String t2=js.get("courses["+i+"].price").toString();

            System.out.println("The Course title is '"+ t1+ "' and its price is " + t2);
        }

        //Print no of copies sold by RPA Course
        for (int i=0;i<NoOfCourses;i++)
        {
            String t3=js.get("courses["+i+"].title");
            if (t3.equalsIgnoreCase("RPA"))
            {
                int t4=js.get("courses["+i+"].copies");
                System.out.println("No of copies sold by RPA Course is "+ t4);
                break;
            }

        }

        //Verify if Sum of all Course prices matches with Purchase Amount

        int sum = 0;
        for (int i=0;i<NoOfCourses;i++)
        {
            int t1=js.getInt("courses["+i+"].price");
            int t2=js.getInt("courses["+i+"].copies");
            String t3=js.get("courses["+i+"].title");
            int amount=t1*t2;
            System.out.println("Total selling price for "+t3+" course is: "+ amount);
            sum = sum+amount;

        }
        System.out.println("Total cost of the courses=" + sum);
        if (sum==purchaseAmount)
        {
            System.out.println("Sum of all Course prices matches with Purchase Amount");
        }
        else
            System.out.println("Sum of all Course prices does not matches with Purchase Amount");

    }
}