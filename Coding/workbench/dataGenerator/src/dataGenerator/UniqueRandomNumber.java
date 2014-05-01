package dataGenerator;

import java.util.ArrayList;
import java.util.Random;

public class UniqueRandomNumber extends Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <Integer> seenInt;
	
	public UniqueRandomNumber()
	{
		super();
		this.seenInt = new ArrayList <Integer>();
	}
	
	
	public int customNextInt(int maxInt)
	{
		int nextInt = super.nextInt(maxInt);
		while(this.seenInt.contains(nextInt) || nextInt == 0)
		{
			nextInt = super.nextInt(maxInt);
		}
		
		this.seenInt.add(nextInt);
		return nextInt;
	}
	
}
