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
      
      public int Order(int times){
    	  try {	
    		    //Reading lines of the file and saving in a vector 
    	        BufferedReader in = new BufferedReader(new FileReader(this.FileName));
    	        String[] str = new String[3*times];
    	        String auxStr;
    	        int i = 0, aux = 0;
    	        int[] pos = new int[3*times];
    	        while (in.ready()) {
    	                str[i] = in.readLine();
    	                pos[i] = Integer.parseInt(str[i].split("\t")[0]);
    	                i++;
    	        }
    	        in.close();
    	  
    	        for (i = str.length; i >= 1; i--) {
    	        	for (int j = 1; j < i; j++) {
    	        		if (pos[j - 1] > pos[j]) {
    	        			aux = pos[j];
    	        			pos[j] = pos[j - 1];
    	        			pos[j - 1] = aux;
    	        			auxStr = str[j];
    	        			str[j] = str[j - 1];
    	        			str[j - 1] = auxStr;
    	        		}
    	        	}
    	        }
    	        for (i = 0; i < str.length; i++){
    	        	if(i==0) this.WriteFile(str[i], false);
    	        	else this.WriteFile(str[i], true);
    	        }
    	  } catch (IOException e) {
    		  	System.out.printf("Error");
    	  }
    	  return 0;
      }
}

