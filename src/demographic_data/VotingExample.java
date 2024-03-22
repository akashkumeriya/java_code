package demographic_data;

class VotingExample{
	public static void main(String args[]){
	      int age = 16;
       try {
            if(age < 18)
            throw new UserMinor("User is not eligible to vote");
            else
            System.out.println("User is eligible to vote");
        }
        catch (UserMinor ex) {
            System.out.println("Caught");
            System.out.println(ex.getMessage());
        }
}
}


