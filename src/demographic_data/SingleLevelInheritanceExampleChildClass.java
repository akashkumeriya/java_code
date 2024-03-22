package demographic_data;

public class SingleLevelInheritanceExampleChildClass extends SingleLevelInheritanceExampleParentClass 
{
	void ChildClassMethod()
	{
		System.out.println("child class method called successfully");
	}
	public static void main(String [] args)
	{		
		SingleLevelInheritanceExampleChildClass obj=new SingleLevelInheritanceExampleChildClass ();
		obj.parentClassMethod();
		obj.ChildClassMethod();
	}
}
