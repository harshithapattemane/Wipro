package com.delegateapp.SpringDelegateApp;




public class App2
{
public static void main(String[] args)
{
Delegate1 delegate = new Delegate1(new Manager1()); // concrete class 
delegate.notifyUser();

Delegate1 delegate2 = new Delegate1(new TeamLead1()); // concrete class 
delegate2.notifyUser();
}
}

// so , here still it's not a loose coupling where developer has to manually wired the dependencies which of the implementation or object
// to be used whether manager or team leader or etc.

// still developer has to modify the code manuallyy
  

