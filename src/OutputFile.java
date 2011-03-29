import java.io.*;

public class OutputFile {
 
      //FileOutputStream Print;
	  FileWriter writer;
	  String FileName;
      PrintStream p;
      
      public OutputFile(String Fname){
    	  this.FileName = Fname;
      }
      
      public int WriteFile(String str) throws FileNotFoundException{
            //Print = new FileOutputStream(this.FileName, true);
            //p = new PrintStream(Print);
            //p.write(str);
    	  try {
			writer = new FileWriter(new File(this.FileName),true);
			writer.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return 0;
      }
      
      public int Order(){
    	  return 0;
      }
}
