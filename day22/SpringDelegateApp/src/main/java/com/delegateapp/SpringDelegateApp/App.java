package com.delegateapp.SpringDelegateApp;



class Manager
{

public void taskAllocation(String user)
{
System.out.println("Task is allocated by : Manager to " + user);
}

}

class TeamLead
{

public void taskAllocation(String user)
{
System.out.println("Task is allocated by : Team Lead to " + user);
}

}

//Tight Coupling Code in Core Java

/* why it is tight coupling 
 * because delegate decides to use Manager
 * and if tomorrow let's say you replace Manager with TeamLead , you must change Delegate class 
 * and delegate cannot work without Manager --- It depends on a concrete class
 * and creating a concrete class and providing the full control to developer to create as many as objects which is not abstract
 * 
 * */
class Delegate{

//private Manager manager = new Manager(); // hard -coded dependecy
private TeamLead teamlead = new TeamLead(); // manually you are injecting the object of another class
public void notifyUser()
{
//manager.taskAllocation("Niti");
	
	teamlead.taskAllocation("Niti");
}

}

public class App
{
public static void main(String[] args)
{
Delegate d = new Delegate(); // concrete class 
d.notifyUser();
}
}


  








interface Allocator
{
	void taskAllocation(String user);

}


class Manager
{

public void taskAllocation(String user)
{
System.out.println("Task is allocated by : Manager to " + user);
}

}

class TeamLead
{

public void taskAllocation(String user)
{
System.out.println("Task is allocated by : Team Lead to " + user);
}

}

//Tight Coupling Code in Core Java

/* why it is tight coupling 
 * because delegate decides to use Manager
 * and if tomorrow let's say you replace Manager with TeamLead , you must change Delegate class 
 * and delegate cannot work without Manager --- It depends on a concrete class
 * and creating a concrete class and providing the full control to developer to create as many as objects which is not abstract
 * 
 * */
class Delegate{

//private Manager manager = new Manager(); // hard -coded dependecy
private TeamLead teamlead = new TeamLead(); // manually you are injecting the object of another class
public void notifyUser()
{
//manager.taskAllocation("Niti");
	
	teamlead.taskAllocation("Niti");
}

}

public class App
{
public static void main(String[] args)
{
Delegate d = new Delegate(); // concrete class 
d.notifyUser();
}
}


  

class Manager1 implements Allocator
{

public void taskAllocation(String user)
{
System.out.println("Task is allocated by : Manager to " + user);
}

}

class TeamLead1 implements Allocator
{

public void taskAllocation(String user)
{
System.out.println("Task is allocated by : Team Lead to " + user);
}

}

//Handling the Tight Coupling Code in Core Java itself by using Interface to make it as Loose Coupling

/* 
 * */


