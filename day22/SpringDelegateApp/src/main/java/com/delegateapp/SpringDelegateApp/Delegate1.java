package com.delegateapp.SpringDelegateApp;

class Delegate1{

	// Here delegate depends upon interface only
  private Allocator allocator; // depends on abstraction 
	
  // dependency provided from interface outside the concrete classes 
  //which makes independent of concrete classes(Manager , teamleader) with delegate
  public Delegate1(Allocator allocator) {
		super();
		this.allocator = allocator;
	  };
  
  
  
public void notifyUser()
{
//manager.taskAllocation("Niti");
	
	allocator.taskAllocation("Niti");




 
}

}

