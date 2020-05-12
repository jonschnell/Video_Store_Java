package edu.iastate.cs228.hw5;

/**
 *  
 * @author Jonathon Schnell
 * @version 1.0
 * @since 4-20-2020
 * COM S 228
 * homework5
 *
 */

public class Video implements Comparable<Video>
{
	private String film;           // film title for the video
	private int numCopies; 
	private int numRentedCopies; 
	
	/**
	 * film constructor
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException if copies <= 0
	 */
	public Video(String film, int n) throws IllegalArgumentException 
	{
		this.film = film;
		this.numCopies = n;
	}
	
	/**
	 * film constructor helper
	 * @param film
	 */
	public Video(String film)
	{
		this(film, 1); 
	}

	/**
	 * return the name of the film
	 */
	public String getFilm()
	{
		return film; 
	}
	
	/**
	 * return the number of copies
	 */
	public int getNumCopies()
	{
		return numCopies; 
	}

	
	/**
	 * add copies to a film
	 * @param n
	 * @throws IllegalArgumentException if n <= 0
	 */
	public void addNumCopies(int n) throws IllegalArgumentException
	{
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		this.numCopies += n;
	}
	/**
	 * return number of available copies
	 */	
	public int getNumAvailableCopies()
	{
		return numCopies - numRentedCopies; 
	}
	/**
	 * return number of rented copies
	 */	
	public int getNumRentedCopies()
	{
		return numRentedCopies; 
	}

	
	/**
	 * Updates numRentedCopies.  If n + numRentedCopies > numCopies, sets numRentedCopies 
	 * to numCopies.  (In other words, rent out all the available copies.) 
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n <= 0
	 * @throws AllCopiesRentedOutException if numRentedCopies == numCopies 
	 */
    public void rentCopies(int n) throws IllegalArgumentException, AllCopiesRentedOutException
    {
    	if (n <= 0 ) {
    		throw new IllegalArgumentException();
    	}
    	else if (n > this.numCopies - this.numRentedCopies) {
    		throw new IllegalArgumentException(n + " copies not in stock");
    	}
    	else if (this.numRentedCopies == this.numCopies) {
    		throw new AllCopiesRentedOutException();
    	}else {
    		this.numRentedCopies += n;
    	}
    }
    
    
    /**
     * Updates numRentedCopies.  If n > numRentedCopies, set numRentedCopies to zero.  
     * 
     * @param n
     * @throws IllegalArgumentException if n <= 0  
     */
    public void returnCopies(int n) throws IllegalArgumentException
    {
    	if (n > this.numRentedCopies) {
    		this.numRentedCopies = 0;
    	}
    	this.numRentedCopies -= n;
    }
	

    /**
	 * Compares two videos by name using string comparison.
	 */
	public int compareTo(Video vd)
	{
		//if (this.equals(vd)) {
		//	return 0;
		//}
		return this.film.compareTo(vd.film);
	}
	
	
	/**
	 * Write in the format "<film> (<numCopies>:<numRentedCopies>)", where every substring 
	 * in the form of a variable name delimited by a pair of angle brackets is replaced with the 
	 * value of the variable. For example, if a Video object has its private instance variables 
	 * take on the values below: 
	 * 
	 * film == "Forrest Gump" 
	 * numCopies == 2
	 * numRentedCopies == 1 
	 *  
	 * then the method returns the string "Forrest Gump (2:1)". 
	 */
	@Override 
	public String toString()
	{
		return this.film + " (" + this.numCopies + ":" + this.numRentedCopies + ")"; 
	}
}
