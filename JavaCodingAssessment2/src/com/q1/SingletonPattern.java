package com.q1;

//1.	Eager initialization

//2.	Static block initialization
//3.	Lazy Initialization
//4.	Thread Safe Singleton
//5.	Serialization issue
//6.	Cloning issue
//7.	Using Reflection to destroy Singleton Pattern
//8.	Enum Singleton

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

enum MySingleton {
	INSTANCE;
	// business logic..
}

class Singleton implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	// VOLATILE guarantee visibility of changes to variable accross threads.
	
	private static volatile Singleton singleton = null; // LAZY INSTANTIATION

	// EAGER INSTANTIATION WILL GENERATE OBJECT IN ADVANCE SO WE USE LAZY
	// INSTANTIATION, OBJECT WILL BE INVOKED
	// WHEN SOMEONE CALL getSingleton()

	private Singleton() {
		if (singleton != null) {
		
			// REFLECTION ISSUE
			// if somebody use reflection to break our singleton and access private
			// constructor.
			// to solve this, if singleton not equal to null, then throw exception.
			
			throw new IllegalStateException();
		}
	}

	public static Singleton getSingleton10() {
		if (singleton == null) {
			if (singleton == null) {
				// DOUBLE LOCK CHECKING
				// It helps to solve multithreading issue.
				synchronized (Singleton.class) {
					// SYNCHRONIZED BLOCK is needed because we need lock only 1st time when object
					// is needed.
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}

	// SERIALIZATION ISSUE
	// We can define readResolve() method so that JVM will not do deserialization
	// It returns the same singleton object as generated which has same hashcode.
	private Object readResolve() {
		return singleton;
	}

	// CLONING ISSUE
	// If someone do cloning then other object will be created which we dont want.
	// To solve this we throw exception in clone method or return same object as it
	// is.
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return singleton;
	}
}

public class SingletonPattern {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Implementing Enum singleton pattern

		MySingleton singletonEnum = MySingleton.INSTANCE;
		System.out.println(singletonEnum.hashCode());

		MySingleton singletonEnum2 = MySingleton.INSTANCE;
		System.out.println(singletonEnum2.hashCode());
	}
}
