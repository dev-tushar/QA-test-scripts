package org.myProject.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Serailize_Deserialize_Object {
	
	public static void serializeObject(Object ob){
		
			try (
		      OutputStream file = new FileOutputStream("testObject.ser");
		      OutputStream buffer = new BufferedOutputStream(file);
			  ObjectOutput output = new ObjectOutputStream(buffer);
			){
		      output.writeObject(ob);
		      output.close();
		    }catch(IOException ex1){
		    	System.out.println("Not able to write object...!!!!");
		    }
	}
	
	public static Object deSerliazeObject(){
		Object ob = null;
		    try(
		      InputStream file1 = new FileInputStream("testObject.ser");
		      InputStream buffer1 = new BufferedInputStream(file1);
		      ObjectInput input = new ObjectInputStream (buffer1);
		    ){
		     ob = input.readObject();		      
		    }
		    catch(ClassNotFoundException ex){
		      System.out.println("Class Not Found Exception thrown when reading object from file");
		    }
		    catch(IOException ex){
		    	System.out.println("IO Exception thrown when reading object from file");
		   }
		    return ob;
	}
}
