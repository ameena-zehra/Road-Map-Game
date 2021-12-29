public class MapException extends RuntimeException 
{
	/**
	* Exception class thrown when there is an inexistent key called in the BinarySearchTree
	 * @param string 
	*/
	public MapException(String string)
	{
		super (string);
	}
}
