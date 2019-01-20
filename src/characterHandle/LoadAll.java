package characterHandle;

import java.io.*;

@SuppressWarnings("serial")
public class LoadAll implements Serializable{
	
	public SaveAll save;
	
	public LoadAll() {
		try {
			// fajl megnyitasa, ha nem talalja exceptiont fog dobni
	         FileInputStream fileIn = new FileInputStream("savefile.txt");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         save = (SaveAll) in.readObject();
	         in.close();
	         fileIn.close();
	         System.out.println("Loaded");
	      } catch (IOException i) {
	         i.printStackTrace();
	         return;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Save not found!");
	         c.printStackTrace();
	         return;
	      }
	}
	
	
}
