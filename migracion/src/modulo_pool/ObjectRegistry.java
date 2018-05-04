package modulo_pool;

/*If you have a static method getInstance(), constructor of this class * be private.
//This is used when you want to control the number of the instances of the object returned by getInstance().
//One of the examples is a Singleton pattern and getInstance() ensures that only one instance of the class
//will exist at any time.
//If this class had a public constructor, multiple instances could have been created.
*/

public class ObjectRegistry {
	private static ObjectRegistry _instance = new ObjectRegistry() ;
	private Pool pool = null;	
	
	public static ObjectRegistry getInstance(){
		return( _instance ) ;
	}
	
	private ObjectRegistry(){
		pool = new Pool();
		//System.out.println("private ObjectRegistry new Pool()");
	} 
	
	public void finalize(){
         try{
             super.finalize();
             //System.out.println("Finalizing ObjectRegistry");
         	}
         catch(Throwable ex){System.out.println( "ObjectRegistry finalize failed : "+ ex );}
    }
	
	public Object getDataAccessObject(){
		if(pool == null){
			pool = new Pool();
			//System.out.println("ObjectRegistry getDataAccessObject new Pool()");
			return pool ;
		}
		else {
			//System.out.println("getDataAccessObject return Pool()");
			return pool ;
			}
			
	}
	
}
