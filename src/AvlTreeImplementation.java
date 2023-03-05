import java.util.ArrayList;
import java.util.NoSuchElementException;


public class AvlTreeImplementation {
	public AvlNode root;
	public ArrayList<String> trace = new ArrayList<>();  // It keeps the nodes I passed while doing the deletion
	public String strSend = "";  // It holds the string that will be printed to the file when I send
	public String strAdd = "";  // It holds the string that will be printed to the file when I add
	public String strRemove = "";  //It holds the string that will be printed to the file when I delete
	public String strRotate = "";  //It holds the string that will be printed to the file when I rotate
    private static final int ALLOWED_IMBALANCE = 1;
	
	public static class AvlNode{
        String element;            // The data in the node
        AvlNode left;   // Left child
        AvlNode right;  // Right child
        int height = 0;

        // Constructors
        AvlNode( String element ) {
            this( element, null, null );
        }

        AvlNode( String element, AvlNode left, AvlNode right ) {
            this.element  = element;
            this.left     = left;
            this.right    = right;
        }

    }
	
	public AvlTreeImplementation(){
        root = null;
    }

	
	public String findMin( )
    {
        if( isEmpty())
            throw new NoSuchElementException();
        return findMin( root ).element;
    }
	
	private AvlNode findMin( AvlNode t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }
	
	private boolean isEmpty( )  {
        return root == null;
    }
	
    private int height( AvlNode t )  {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );
    }	
	
    public String insertFinal(String s) {
    	insert(s);
    	String strAdd2= strAdd;
    	strAdd= "";
    	String strRotate2= strRotate;
    	strRotate="";
    	if (strRotate2 == "")
    		return strAdd2 ;
    	else {
    		return strAdd2  +strRotate2 + "\n";
		}
    }
    
    private void insert( String x ) {
    	root = insert( x, root );
    }

    private AvlNode insert( String x, AvlNode t ) {
        if( t == null ) {
        	return new AvlNode( x, null, null );
        }
        
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
        	strAdd= strAdd + t.element + ": New node being added with IP:"+ x +"\n";
            t.left = insert( x, t.left );     
        }
        
        else {
        	strAdd= strAdd + t.element + ": New node being added with IP:"+ x + "\n";
        	t.right = insert( x, t.right );      	
        }        
        return balance(t);
    }
	
    private void remove( String x ) {
        root = remove( x, root );
    }
    
    public String removeFinal(String x) {
    	remove(x);
    	String strRemove2= strRemove;
    	strRemove= "";
    	String strRotate2= strRotate;
    	strRotate="";
    	removecontrol=0;
    	if (strRotate2 == "")
    		return strRemove2 ;
    	else {
    		return strRemove2  +strRotate2 + "\n";
		}  	
    }

    private int removecontrol=0;
    
    private AvlNode remove( String x, AvlNode t ) {    	
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
        	if( t.left != null && t.right != null )  {        //  if node has two children        
                t.element = findMin( t.right ).element;
        		
                if(trace.size()!=0 && removecontrol==0) {
        			strRemove= strRemove + trace.get(trace.size()-1)+ ": Non Leaf Node Deleted; removed: " + x + " replaced: " + t.element +"\n";
        			removecontrol=1;
        		}
        		trace.clear();                     		
                t.right = remove( t.element, t.right );  
        }
  
        else if(t.left== null && t.right==null ) {  //  if node has 0 children   
        	t = ( t.left != null ) ? t.left : t.right;
        	if(trace.size()!=0 && removecontrol==0) {
        		strRemove= strRemove +  trace.get(trace.size()-1) + ": Leaf Node Deleted: "+ x +"\n";
        	}
             trace.clear();
	}
        
        else   {     //   if node has 1 child
            t = ( t.left != null ) ? t.left : t.right;
        	if(trace.size()!=0 && removecontrol==0)
        		strRemove= strRemove + trace.get(trace.size()-1) + ": Node with single child Deleted: " + x +"\n";	
            
        	trace.clear();
        }      	
    }
        	return balance(t);
        }
    	
       
    public String sendMessage(String n1, String n2) {
    	sendMessage(root, n1, n2);

    	String str2= strSend;
    	strSend= "";
    	return str2;
    }
    
    private void sendMessage(AvlNode root, String n1, String n2) {
    	if(root.element.compareTo(n2) <0 && root.element.compareTo(n1) <0)
    		 sendMessage(root.right, n1, n2);
    	
    	else if(root.element.compareTo(n2) >0 && root.element.compareTo(n1) >0)
    		sendMessage(root.left, n1, n2);
    	
    	else {
    		sendMessageImpl(root, n1, n2);
    	}    	
    }    
    
    private void sendMessageImpl(AvlNode root, String n1, String n2)
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

    
    private boolean getPath(AvlNode root, ArrayList<String> path, String x)
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
    
  
    int balance=0;
    
    
    private AvlNode balance( AvlNode t )
    {
        balance=1;
    	if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) ) {

            	t = rotateWithRightChild( t );
            }
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }
   

    
    private AvlNode rotateWithLeftChild( AvlNode k2 )
    {
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        
        if(rot!=5) {
        	if(strRotate!="") {
        	if (rot==1 ) {
        		strRotate= strRotate +"\n" +  "Rebalancing: left-right rotation";
        	}
        	else if(rot==-1 ) {
        		strRotate= strRotate +"\n" +   "Rebalancing: right-left rotation";
        	}

        	else
        		strRotate= strRotate +"\n" + "Rebalancing: right rotation";
        	rot=0;
        }
        	else {
        		if (rot==1 ) {
            		strRotate=   "Rebalancing: left-right rotation";
            	}
            	else if(rot==-1 ) {
            		strRotate=    "Rebalancing: right-left rotation";
            	}

            	else
            		strRotate=  "Rebalancing: right rotation";
            	rot=0;
			}
        }      
		
        return k1;
    }

    
    private AvlNode rotateWithRightChild( AvlNode k1 )
    {
        AvlNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        
        if(rot!=5) {
            if(strRotate!="") {
        	
        	if (rot==1 ) {
            	strRotate= strRotate +"\n" +  "Rebalancing: left-right rotation";
            }
            else if(rot==-1 ) {
            	strRotate= strRotate + "\n" +  "Rebalancing: right-left rotation";
            }

            else 
            	strRotate= strRotate + "\n" + "Rebalancing: left rotation";
            rot=0;
            }
            else {
        		if (rot==1 ) {
            		strRotate=   "Rebalancing: left-right rotation";
            	}
            	else if(rot==-1 ) {
            		strRotate=    "Rebalancing: right-left rotation";
            	}

            	else
            		strRotate=  "Rebalancing: left rotation";
            	rot=0;
			}
        }

        
        return k2;
    }


     int rot= 0;
    
    private AvlNode doubleWithLeftChild( AvlNode k3 )
    {
        rot=5;
    	k3.left = rotateWithRightChild( k3.left );
        rot=1;

        return rotateWithLeftChild( k3 );
    }


    private AvlNode doubleWithRightChild( AvlNode k1 )
    {
        rot=5;
    	k1.right = rotateWithLeftChild( k1.right );
        rot=-1;

        return rotateWithRightChild( k1 );
    }
    
	}
	

