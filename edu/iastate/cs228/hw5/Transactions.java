package edu.iastate.cs228.hw5;


import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Jonathon Schnell
 * @version 1.0
 * @since 4-20-2020
 * COM S 228
 * homework4
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store. 
 *
 */
public class Transactions 
{
	
	/**
	 * The main method generates a simulation of rental and return activities.  
	 *  
	 * @param args
	 * @throws FileNotFoundException
	 * @throws AllCopiesRentedOutException 
	 * @throws FilmNotInInventoryException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException
	{	
		boolean exit = false;
		//int selection = 0;
		String input = "";
		VideoStore a = new VideoStore("videoList1.txt");
		Scanner scanner = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);
		System.out.print("Transactions at a Video Store\nkeys:\t1 (rent)\t2 (bulk rent)\n\t3 (return)\t4 (bulk return)\n\t5 (summary)\t6 (exit)\n");
		
		while (exit == false) {
			System.out.print("\nTransaction: ");
			int selection = scanner.nextInt();
			
			if (selection == 1) {
				System.out.print("Film to rent: ");
				input = scanner2.nextLine();
				try
				{
					a.ex = "";
					a.videoRent(parseFilmName(input), parseNumCopies(input));
				}
				catch(IllegalArgumentException e) {
					a.ex += e.getMessage() + "\n";
				}
				catch(FilmNotInInventoryException e)
				{
					a.ex += e.getMessage() + "\n";
				}
				catch(AllCopiesRentedOutException e)
				{
					a.ex += e.getMessage() + "\n";
				}
				System.out.print(a.ex);
			}
			else if (selection == 2) {
				System.out.print("Video file (rent): ");
				input = scanner2.nextLine();
				try {
					a.ex = "";
					a.bulkRent(input);
				}
				catch(FileNotFoundException e)
				{
					a.ex += e.getMessage() + "\n";
				}
				System.out.print(a.ex);
			}
			else if (selection == 3) {
				System.out.print("Film to return: ");
				input = scanner2.nextLine();
				try {
					a.ex = "";
					a.videoReturn(parseFilmName(input), parseNumCopies(input));
				}
				catch(IllegalArgumentException e)
				{
					a.ex += e.getMessage() + "\n";
				}
				catch(FilmNotInInventoryException e)
				{
					a.ex += e.getMessage() + "\n";
				}
				System.out.print(a.ex);

			}
			else if (selection == 4) {
				System.out.print("Video file (return): ");
				input = scanner2.nextLine();
				try {
					a.ex = "";
					a.bulkReturn(input);
				}
				catch(FileNotFoundException e)
				{
					a.ex += e.getMessage() + "\n";
				}
				System.out.print(a.ex);
			}
			else if (selection == 5) {
				System.out.println(a.transactionsSummary());
			}
			else if (selection == 6) {
				scanner.close();
				System.exit(0);
				//exit = true;
			}

		}
		
		
		
		
		/*
		VideoStore a = new VideoStore("videoList1.txt");
		a.videoRent("The Godfather", 1);
		
		try {
			a.ex = "";
			a.bulkRent("videoList2.txt");
		}
		catch(FileNotFoundException e)
		{
			a.ex += e.getMessage() + "\n";
		}
		
		System.out.println(a.ex);

		
		a.videoRent("Brokeback Mountain", 1);
		a.videoReturn("Slumdog Millionaire", 2);
		
		try
		{
			a.ex = "";
			a.videoRent("error", 1);
		}
		catch(IllegalArgumentException e) {
			a.ex += e.getMessage() + "\n";
		}
		catch(FilmNotInInventoryException e)
		{
			a.ex += e.getMessage() + "\n";
		}
		catch(AllCopiesRentedOutException e)
		{
			a.ex += e.getMessage() + "\n";
		}
				
		a.videoRent("Singin' in the Rain", 2);
		a.bulkReturn("videoList3.txt");
		System.out.println(a.transactionsSummary());
		*/
		
		/*
		VideoStore a = new VideoStore("test.txt");
		Video b = new Video("The Godfather");
		
		System.out.println(a.inventory.findEntry(b).data.getFilm() + " " + a.inventory.findEntry(b).data.getNumCopies());
		System.out.println(a.inventory.findEntry(b).data.toString());
		System.out.println(a.findVideo("The Godfather"));
		a.addVideo("The Godfather", 1);
		a.addVideo("test", 1);
		a.addVideo("test");
		System.out.println(a.findVideo("test"));
		System.out.println(a.findVideo("The Godfather"));
		System.out.println(a.findVideo("Forrest Gump"));
		a.bulkImport("test.txt");
		System.out.println(a.findVideo("The Godfather"));
		System.out.println(a.findVideo("Taxi Driver"));
		a.videoRent("Taxi Driver", 1);
		System.out.println(a.findVideo("Taxi Driver"));
		a.videoRent("Taxi Driver", 1);
		System.out.println(a.findVideo("Taxi Driver"));
		System.out.println(a.available("Taxi Driver"));
		a.videoReturn("Taxi Driver", 1);
		System.out.println(a.findVideo("Taxi Driver"));
		System.out.println(a.available("Taxi Driver"));
		a.bulkRent("test2.txt");
		System.out.println(a.findVideo("The Godfather"));
		System.out.println(a.findVideo("Forrest Gump"));
		System.out.println(a.findVideo("Brokeback Mountain"));
		System.out.println("\n");
		System.out.println(a.inventoryList());
		System.out.println("\n");

		System.out.println(a.transactionsSummary());

		//a.setUpInventory("test.txt");
		 */
		
		// 
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the 
		//    the project description. 
	}
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return int number of copies parsed out of line
	 */
	private static int parseNumCopies(String line) {
		if (line.contains("(") && line.contains(")")) {
			return Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(")")));
		}
		return 1;
	}
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return string the name of the film
	 */
	private static String parseFilmName(String line) {
		if (line.contains("(") && line.contains(")")) {
			return line.substring(0, line.indexOf("(") - 1);
		}
		return line;
	}
}
