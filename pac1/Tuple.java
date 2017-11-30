package pac1;

public class Tuple<T1, T2>
{
	public T1 left;
	public T2 right;
	
	public Tuple(T1 left, T2 right) 
	{
		this.left = left;
		this.right = right;
	}
	
	public String toString()
	{
		return "("+left+", "+right+")"; 
	}
}
