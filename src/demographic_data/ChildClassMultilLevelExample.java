package demographic_data;

public class ChildClassMultilLevelExample  extends ParentClassMultilLevelExample 
{
	void childClassMethod()
	{
		System.out.println("child class method called successfully");
	}
	public static void main(String [] args)
	{
		ChildClassMultilLevelExample obj = new ChildClassMultilLevelExample ();
		obj.grandParentMethod();
		obj.parentClassMethod();
		obj.childClassMethod();
	}
}
