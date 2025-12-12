package stud;


//Bean Class  // Entity  //  POJO Class // we have create userBean class
public class ValidateUser {

	
	//encapsulated properties so giving access we have created getter and setter 
	private String user, pass;

		
	public String getUser() {
		return user;
	}

	// to set the user name --  this we will set with the help of setproperty
	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	// to set the password -- this we will set with the help of setproperty
	public void setPass(String pass) {
		this.pass = pass;
	}

		// validate method is to validate the user name and password
	public boolean validate(String u1, String p1)
	{
		if(u1.equals(user)&&p1.equals(pass))
		return true;
	else
		return false;
	}
}
