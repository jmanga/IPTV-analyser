import java.io.*;

public class OutputFile {
 
      FileOutputStream Print;
      String FileName;
      Sem sem;
      
      public int WriteFile() throws FileNotFoundException{
            Print = new FileOutputStream(FileName);
            return 0;
      }
      
      public int Order(){
    	  return 0;
      }
}
