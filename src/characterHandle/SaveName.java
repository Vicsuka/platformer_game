package characterHandle;

import java.io.Serializable; 

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SaveName implements Serializable{
	
	//osszesnev tarolasa
	public ArrayList<String> allNames;
	
	public SaveName() {
		allNames = new ArrayList<String>();
	}
	
	//adott nev keresese; ha megvan akkor visszaadja az indexet
	public int findName(String a, String b, String c, String d) {
		String incomingName = a+b+c+d;
		if (allNames.contains(incomingName)) {
			return allNames.indexOf(incomingName);
		} else return -1;
	}
	
	
}
