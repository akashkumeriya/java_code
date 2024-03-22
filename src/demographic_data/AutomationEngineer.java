package demographic_data;

public class AutomationEngineer extends TestEngineer{
	void automationEngineerMethod()
	{
		System.out.println("methods executed successfully which belongs from Automation Engineer class");
	}
	public static void main( String [] args)
	{
		AutomationEngineer obj=new AutomationEngineer ();	
		obj.TestEngineerMethod();
		obj.automationEngineerMethod();
		
	}
}
