public class GraphException extends RuntimeException 
{
	/**
	* Exception class thrown when there is an inexistent key called in the BinarySearchTree
	 * @param string 
	*/
	public GraphException(String string)
	{
		super (string);
	}
}
