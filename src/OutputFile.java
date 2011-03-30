import java.io.*;

public class OutputFile {
 
      //FileOutputStream Print;
	  FileWriter writer;
	  String FileName;

      public OutputFile(String Fname){
    	  this.FileName = Fname;
      }
      
      public boolean WriteFile(String str, boolean check) throws FileNotFoundException{

    	  try {
    		if(check == false){
    			this.writer = new FileWriter(new File(this.FileName),false);
    			check = true;
    		}
    		else this.writer = new FileWriter(new File(this.FileName),true);
    		PrintWriter out = new PrintWriter(this.writer);
			out.println(str);
			this.writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	  return check;
      }
      
      public int Order(){
    	  return 0;
      }
}

