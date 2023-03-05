import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		
		String input= args[0];	
		String output = args[1];
//		System.out.println(2312);
//		Scanner scan=new Scanner(System.in);
//		   
//		String input= scan.next();	
//		String output = scan.next();
		
		
		AvlTreeImplementation a= new AvlTreeImplementation();
		BST b= new BST();
		
		File Obj = new File(input); 
        Scanner Reader = new Scanner(Obj); 
    	FileWriter myWriter2 = new FileWriter(output+"_Bst.txt", false);
    	FileWriter myWriter = new FileWriter(output+"_Avl.txt", false);
    	
        while (Reader.hasNextLine()) {  
        	String data = Reader.nextLine(); 
            String [] mylist = data.split(" ");  
            
            if(mylist[0].equals("SEND")) {
            	String s1 = mylist[1];
            	String s2 = mylist[2];  
        		myWriter.write(a.sendMessage(s1, s2));
        		myWriter2.write(b.sendMessage(s1, s2));

            }

            else if(mylist[0].equals("ADDNODE")) {
            	String s1 = mylist[1];
            	myWriter.write(a.insertFinal(s1));
            	myWriter2.write(b.insertFinal(s1));
            }
            
            else if(mylist[0].equals("DELETE")) {
            	String s1 = mylist[1];
        		myWriter.write(a.removeFinal(s1));
        		myWriter2.write(b.removeFinal(s1));
            }
            
            else {
            	String s1 = mylist[0];
            	a.insertFinal(s1);
            	b.insertFinal(s1);
			}
           
	}
        Reader.close();  
        myWriter.close();
        myWriter2.close();
//        scan.close();
	}
}
