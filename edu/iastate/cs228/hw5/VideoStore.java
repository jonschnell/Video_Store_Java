package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import edu.iastate.cs228.hw5.SplayTree.Node; 

/**
 *  
 * @author Jonathon Schnell
 * @version 1.0
 * @since 4-20-2020
 * COM S 228
 * homework4
 *
 */

public class VideoStore 
{
	protected SplayTree<Video> inventory;     // all the videos at the store
	public String ex = "";
	
	// ------------
	// Constructors 
	// ------------
	
    /**
     * Default constructor sets inventory to an empty tree. 
     */
    public VideoStore()
    {
    	// no need to implement. 
    }
    
    
	/**
	 * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of  
	 * the project description for details regarding the format of a video file. 
	 * 
	 * Calls setUpInventory(). 
	 * 
	 * @param videoFile  no format checking on the file
	 * @throws FileNotFoundException
	 */
    public VideoStore(String videoFile) throws FileNotFoundException  
    {
    	inventory = new SplayTree();
		setUpInventory(videoFile);
 
    }
    
    
   /**
     * Accepts a video file to initialize the splay tree inventory.  To be efficient, 
     * add videos to the inventory by calling the addBST() method, which does not splay. 
     * 
     * Refer to Section 3.2 for the format of video file. 
     * 
     * @param  videoFile  correctly formated if exists
     * @throws FileNotFoundException 
     */
    public void setUpInventory(String videoFile) throws FileNotFoundException
    {
    	File f = new File(videoFile);
		Scanner sc = new Scanner(f);
		
		while (sc.hasNext()) {
			String line = sc.nextLine();
			
				inventory.addBST(new Video(parseFilmName(line),parseNumCopies(line)));
			}
			
		}
    	
	
    
    // ------------------
    // Inventory Addition
    // ------------------
    
    /**
     * Find a Video object by film title. 
     * 
     * @param film
     * @return the film data
     */
	public Video findVideo(String film) 
	{
		return inventory.findEntry(new Video(film)).data;
		
	}


	/**
	 * Updates the splay tree inventory by adding a number of video copies of the film.  
	 * (Splaying is justified as new videos are more likely to be rented.) 
	 * 
	 * Calls the add() method of SplayTree to add the video object.  
	 * 
	 *     a) If true is returned, the film was not on the inventory before, and has been added.  
	 *     b) If false is returned, the film is already on the inventory. 
	 *     
	 * The root of the splay tree must store the corresponding Video object for the film. Update 
	 * the number of copies for the film.  
	 * 
	 * @param film  title of the film
	 * @param n     number of video copies 
	 */
	public void addVideo(String film, int n)  
	{
		if (inventory.contains(new Video(film, n))) {
			inventory.findEntry(new Video(film, n)).data.addNumCopies(n);
		}
		inventory.add(new Video(film, n));

	}
	

	/**
	 * Add one video copy of the film. 
	 * 
	 * @param film  title of the film
	 */
	public void addVideo(String film)
	{
		addVideo(film, 1);
	}
	

	/**
     * Update the splay trees inventory by adding videos.  Perform binary search additions by 
     * calling addBST() without splaying. 
     * 
     * The videoFile format is given in Section 3.2 of the project description. 
     * 
     * @param videoFile  correctly formated if exists 
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException 
    {
    	File f = new File(videoFile);
		Scanner sc = new Scanner(f);
		
		while (sc.hasNext()) {
			String line = sc.nextLine();
			
			addVideo(parseFilmName(line), parseNumCopies(line));
			
		}
    	
    }

    
    // ----------------------------
    // Video Query, Rental & Return 
    // ----------------------------
    
	/**
	 * Search the splay tree inventory to determine if a video is available. 
	 * 
	 * @param  film
	 * @return true if available
	 */
	public boolean available(String film)
	{
		if (inventory.contains(new Video(film)) && inventory.findEntry(new Video(film)).data.getNumAvailableCopies() > 0) {
			return true;
		}
		
		return false; 
	}

	
	
	/**
     * Update inventory. 
     * 
     * Search if the film is in inventory by calling findElement(new Video(film, 1)). 
     * 
     * If the film is not in inventory, prints the message "Film <film> is not 
     * in inventory", where <film> shall be replaced with the string that is the value 
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * 
     * If there is at least one available copy but n is greater than the number of 
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.  
     * 
     * @param film   
     * @param n 
     * @throws IllegalArgumentException      if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException   if film is not in the inventory
	 * @throws AllCopiesRentedOutException   if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException,  
									     			 AllCopiesRentedOutException 
	{
		if (n <= 0 || film == null || film.isEmpty()) {
			throw new IllegalArgumentException();
		}
		else if (!inventory.contains(new Video(film))) {
			throw new FilmNotInInventoryException("Film " + film +  " is not in inventory");
		}
		else if (inventory.findElement(new Video(film)).getNumAvailableCopies() == 0) {
			throw new AllCopiesRentedOutException("Film " + film + " has been rented out");
		}
		else {
			inventory.findEntry(new Video(film)).data.rentCopies(n);
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 *    1. Calls videoRent() repeatedly for every video listed in the file.  
	 *    2. For each requested video, do the following: 
	 *       a) If it is not in inventory or is rented out, an exception will be 
	 *          thrown from videoRent().  Based on the exception, prints out the following 
	 *          message: "Film <film> is not in inventory" or "Film <film> 
	 *          has been rented out." In the message, <film> shall be replaced with 
	 *          the name of the video. 
	 *       b) Otherwise, update the video record in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please read Section 3.4 
	 * of the project description. 
	 *       
	 * @param videoFile  correctly formatted if exists
	 * @throws FileNotFoundException
     * @throws IllegalArgumentException     if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException  if any film from the videoFile is not in the inventory 
	 * @throws AllCopiesRentedOutException  if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException, 
												  FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		ex = "";
		File f = new File(videoFile);
		Scanner sc = new Scanner(f);
		
		while (sc.hasNext()) {
			String line = sc.nextLine();
						
			try
			{
				videoRent(parseFilmName(line), parseNumCopies(line));
			}
			catch(IllegalArgumentException e) {
				ex += e.getMessage() + "\n";
			}
			catch(FilmNotInInventoryException e)
			{
				ex += e.getMessage() + "\n";
			}
			catch(AllCopiesRentedOutException e)
			{
				ex += e.getMessage() + "\n";
			}
			
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of rented copies
	 * while ignoring the extra copies. 
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException     if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException  if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException 
	{
		if (n <= 0 || film == null || film.isEmpty()) {
			throw new IllegalArgumentException();
		}
		//return number of rented copies
		else if (inventory.findEntry(new Video(film)).data.getNumRentedCopies() <= n) {
			inventory.findEntry(new Video(film)).data.returnCopies(inventory.findEntry(new Video(film)).data.getNumRentedCopies());
		}
		else if (!inventory.contains(new Video(film))) {
			throw new FilmNotInInventoryException("Film " + film + " is not in inventory");
		}else {
			inventory.findEntry(new Video(film)).data.returnCopies(n);
		}		
		
	}
	
	
	/**
	 * Update inventory. 
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section 
	 * 3.4 of the project description on how to handle multiple exceptions. 
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException,
													FilmNotInInventoryException												
	{
		ex = "";
		File f = new File(videoFile);
		Scanner sc = new Scanner(f);
		
		while (sc.hasNext()) {
			String line = sc.nextLine();
						
			try
			{
				videoReturn(parseFilmName(line), parseNumCopies(line));
			}
			catch(IllegalArgumentException e) {
				ex += e.getMessage() + "\n";
			}
			catch(FilmNotInInventoryException e)
			{
				ex += e.getMessage() + "\n";
			}
			
		}
	}
		
	

	// ------------------------
	// Methods without Splaying
	// ------------------------
		
	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos by film 
	 * title, whether rented or not.  Below is a sample string if printed out: 
	 * 
	 * 
	 * Films in inventory: 
	 * 
	 * A Streetcar Named Desire (1) 
	 * Brokeback Mountain (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Singin' in the Rain (2)
	 * Slumdog Millionaire (5) 
	 * Taxi Driver (1) 
	 * The Godfather (1) 
	 * 
	 * 
	 * @return list of veides in inventory as well as num copies
	 */
	public String inventoryList()
	{
		String toReturn = "";
		String line = "";
		Iterator<Video> iterator = inventory.iterator();

		for (int i = 0; i < inventory.size; i++) {
			line = iterator.next().toString();
			toReturn += line.substring(0, line.indexOf("(") - 1) + " " + line.substring(line.indexOf("("), line.indexOf(":")) + ")" + "\n";
		}
		
		//alternative methods
		/*
		String toReturn = "";
		for (Iterator<Video> i = inventory.iterator(); i.hasNext();) {
			toReturn += i.next().getFilm() + " " + i.next().getNumCopies() + "\n";
			
		}
		
		while (iterator.hasNext()) {
			iterator.next().toString();
		
			toReturn += iterator.next().getFilm() + " " + iterator.next().getNumCopies() + "\n";
			
		}
		
		String toReturn = "";
		Node temp = inventory.root;
		for (int i = 0; i < inventory.size; i++) {
			toReturn += temp.data.toString() + "\n";
			temp = temp.parent;
		}
		*/
		
		return toReturn; 
	}

	
	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format, 
	 * see Transaction 5 in the sample simulation in Section 4 of the project description. 
	 *   
	 * @return rented videos and quantity as well as unrended and num copies
	 */
	public String transactionsSummary()
	{
		return rentedVideosList() + "\n" + unrentedVideosList(); 
	}	
	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films: 
	 * 
	 * Brokeback Mountain (1)
	 * Forrest Gump (1) 
	 * Singin' in the Rain (2)
	 * The Godfather (1)
	 * 
	 * 
	 * @return rented videos and num rented
	 */
	private String rentedVideosList()
	{
		String toReturn = "Rented films:\n\n";
		String line = "";
		Iterator<Video> iterator = inventory.iterator();

		for (int i = 0; i < inventory.size; i++) {
			line = iterator.next().toString();
			if (Integer.parseInt(line.substring(line.indexOf(":") + 1, line.indexOf(")"))) > 0) {
				toReturn += line.substring(0, line.indexOf("(") - 1) + " (" + line.substring(line.indexOf(":") + 1, line.indexOf(")") + 1) + "\n";

			}

		}
		return toReturn; 
	}

	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * Prints only the films that have unrented copies. 
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Slumdog Millionaire (4) 
	 * Taxi Driver (1) 
	 * 
	 * 
	 * @return unrented videos and num unrented
	 */
	private String unrentedVideosList()
	{
		String toReturn = "Films remaining in inventory:\n\n";
		String line = "";
		Iterator<Video> iterator = inventory.iterator();
		Iterator<Video> iterator2 = inventory.iterator();


		for (int i = 0; i < inventory.size; i++) {
			line = iterator.next().toString();
			if (Integer.parseInt(line.substring(line.indexOf(":") + 1, line.indexOf(")"))) < Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(":")))) {
				toReturn += line.substring(0, line.indexOf("(") - 1) + " (" + iterator2.next().getNumAvailableCopies() + ")\n";
			}else {
				iterator2.next();
			}

		}
		return toReturn;
	}	

	
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return string the name of the film
	 */
	public static String parseFilmName(String line) 
	{
		
		if (line.contains("(") && line.contains(")")) {
			//new one liner
			return line.substring(0, line.indexOf("(") - 1);
			//OLD CODE
			//handle 2 digit numbers
			//allows for up to 99 copies in stock
			//if(Character.isDigit(line.charAt(line.length() - 3))) {
			//	return line.substring(0,line.length() - 5);
			//}else {
			//	return line.substring(0,line.length() - 4);
			//}
		}
		//line had no number so just use line as string
		return line; 
	}
	
	
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return int number of copies parsed out of line
	 */
	public static int parseNumCopies(String line) 
	{
		if (line.contains("(") && line.contains(")")) {
			//lol jk did it in one line
			return Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(")")));
			//OLD CODE
			//handle 2 digit numbers
			//allows for up to 99 copies in stock
			//if(Character.isDigit(line.charAt(line.length() - 3))) {
			//	return Integer.parseInt(line.substring(line.length() - 3, line.length() - 1));
			//}else {
			//	return Integer.parseInt(line.substring(line.length() - 2, line.length() - 1));
			//}
		}
		//the movie did not have a number after so add 1 copy
		return 1; 
	}
}

