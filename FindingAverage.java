import java.util.*;
import java.io.*;

public class FindingAverage {
	public static void main(String[] args) throws FileNotFoundException
	{
		File output = new File("output.txt");
		Scanner scan = new Scanner(output);
		double average = 0;
		double highest = 0;
		double lowest = 1000;

		//skip point system
		for (int i=0; i<34; i++)
		{
			scan.next();
		}
		//100 iterations of average
		for (int i=0; i<1000; i++) {
			//skip all bs
			for (int j=0; j<924; j++) {
				scan.next();
			}
			int temp = scan.nextInt();
			average += temp;
			if (temp > highest)
				highest = temp;
			if (temp < lowest)
				lowest = temp;

		}

		//print total average after 1000 games
		System.out.println("Average: " + average/1000);
		System.out.println("Highest: " + highest);
		System.out.println("Lowest: " + lowest);
	}
}