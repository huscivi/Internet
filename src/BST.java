import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ArrayList;

// I utilized the PS notes and geeksforgeeks.org site while writing this code.  

public class BST {
	private BinaryNode root;
	private ArrayList<String> trace = new ArrayList<>();  // It keeps the nodes I passed while doing the deletion
	private String strSend = "";  // It holds the string that will be printed to the file when I send
	private String strAdd = "";  // It holds the string that will be printed to the file when I add
	private String strRemove = "";  // It holds the string that will be printed to the file when I delete
	private String strRotate = "";  // It holds the string that will be printed to the file when I rotate
	
	public static class BinaryNode{
        String element;            // The data in the node
        BinaryNode left;   // Left child
        BinaryNode right;  // Right child

        // Constructors
        BinaryNode( String element ) {
            this( element, null, null );
        }

        BinaryNode( String element, BinaryNode left, BinaryNode right ) {
            this.element  = element;
            this.left     = left;
            this.right    = right;
        }

    }
	
	public BST(){
        root = null;
    }

	public String findMin( )
    {
        if( isEmpty())
            throw new NoSuchElementException();
        return findMin( root ).element;
    }
	
	private BinaryNode findMin( BinaryNode t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

	
	private boolean isEmpty( )
    {
        return root == null;
    }
	
    public String insertFinal(String s) {
    	insert(s);
    	String strAdd2= strAdd;
    	strAdd= "";
    	return strAdd2;
    }
    
    private void insert( String x ) {
    	root = insert( x, root );
    }

    private BinaryNode insert( String x, BinaryNode t )
    {
        if( t == null ) {
        	return new BinaryNode( x, null, null );
        }
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
        	strAdd= strAdd + t.element + ": New node being added with IP:"+ x +"\n";
            t.left = insert( x, t.left );
            
        }
        
        else  {       	
        	strAdd= strAdd + t.element + ": New node being added with IP:"+ x + "\n";
        	t.right = insert( x, t.right );       	
        }
        return t;
    }	

  
    public String removeFinal(String x) {
    	remove(x);
    	String strRemove2= strRemove;
    	strRemove= "";
    	removecontrol=0;
    	return strRemove2;    	
    }
    private void remove( String x )    {
        root = remove( x, root );
    }

    private int removecontrol=0;
    
    private BinaryNode remove( String x, BinaryNode t )
    {    	
    	if( t == null )  {
        	return t;   
        }
   	
        int compareResult = x.compareTo( t.element );
        if( compareResult < 0 ) {
        	trace.add(t.element);
        	t.left = remove( x, t.left );
        }
        
        else if( compareResult > 0 ) {
        	trace.add(t.element);
        	t.right = remove( x, t.right );
        }
        
        else {  // deletion is written in else block
        	if( t.left != null && t.right != null )   // if node has two children
        {
                t.element = findMin( t.right ).element;
        		if(trace.size()!=0 && removecontrol==0) {
        			strRemove= strRemove + trace.get(trace.size()-1)+ ": Non Leaf Node Deleted; removed: " + x + " replaced: " + t.element +"\n";
        			removecontrol=1;
        		}
        		trace.clear();                      		
                t.right = remove( t.element, t.right );  
        }
  
        else if(t.left== null && t.right==null ) {  //  if node has 0 children   
        	t= null;
        	if(trace.size()!=0 && removecontrol==0) {
        		strRemove= strRemove +  trace.get(trace.size()-1) + ": Leaf Node Deleted: "+ x +"\n";
        	}
             trace.clear();
	}
        
        else   {     // if node has 1 child
            t = ( t.left != null ) ? t.left : t.right;

        	if(trace.size()!=0 && removecontrol==0)
        		strRemove= strRemove + trace.get(trace.size()-1) + ": Node with single child Deleted: " + x +"\n";	

        	trace.clear();
        }      	
    }
        	return t;
        }
    
    public String sendMessage(String n1, String n2) {
    	sendMessage(root, n1, n2);
    	String strSend2= strSend;  // I am copying the string to be printed in the send function
    	strSend= "";  
    	return strSend2;
    }
    
    private void sendMessage(BinaryNode root, String n1, String n2) {
    	if(root.element.compareTo(n2) <0 && root.element.compareTo(n1) <0)
    		 sendMessage(root.right, n1, n2);
    	
    	else if(root.element.compareTo(n2) >0 && root.element.compareTo(n1) >0)
    		sendMessage(root.left, n1, n2);
    	
    	else 
    		sendMessageImpl(root, n1, n2);
    	    	
    }   
    
    private void sendMessageImpl(BinaryNode root, String n1, String n2)
    {
        ArrayList<String> path1= new ArrayList<String>();         // ArrayList to store the path of first node n1 from root
     
        ArrayList<String> path2=new ArrayList<String>();         // ArrayList to store the path of first node n2 from root
     
        getPath(root, path1, n1);  // path1 holds the path from root to node
        getPath(root, path2, n2);	// path2 holds the path from root to node

        
        ArrayList<String> path1reverse = new ArrayList<>();
        for(int i=path1.size()-1; i>=0;i--) {    // reverse first path
        	path1reverse.add(path1.get(i));
        }
              
        path1reverse.addAll(path2);   // combine two paths
		
		ArrayList<String> path3= new ArrayList<>();
		for(int i=0; i<path1reverse.size();i++) {    // remove duplicate nodes
			if(!path3.contains(path1reverse.get(i)))
				path3.add(path1reverse.get(i));

		}
     
        for(int k=0;k<path3.size();k++) {
        	String first = path3.get(0);
        	String last = path3.get(path3.size()-1);
        	if(k==0) {
        		strSend= strSend+ first+ ": Sending message to: " + last +"\n";
        	}
        	
        	else if(k != 0  && k!= path3.size()-1) {
        		strSend= strSend + path3.get(k) + ": Transmission from: "+ path3.get(k-1)+" receiver: "+ last+" sender:" + first +"\n";
        	}
        	
        	else   {
        		strSend = strSend+ last+ ": Received message from: "+ first +"\n";
        	}
        }
        }
    
    private boolean getPath(BinaryNode root, ArrayList<String> path, String x)
    {
        
        if (root==null) {     // if root is null there is no path
        	return false;
        }
        
        path.add(root.element);      // push the node's value in 'path'
     
        if (root.element.equals(x)) {      // if it is the required node return true
        	return true;
        }
     
        if (getPath(root.left, path, x) || getPath(root.right, path, x)) {
            return true;
        }
        path.remove(path.size()-1);
        return false;
    }
        
	}
	

