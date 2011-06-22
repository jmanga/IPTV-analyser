
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class OutputFile {
 
      //FileOutputStream Print;
	  FileWriter writer;
	  String FileName;
	  public static final Random RND = new Random();

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
      
      private static void swap(Object[] array, int i, int j, String[] pac) {
          Object tmp = array[i];
          String tmp2 = pac[i];
          array[i] = array[j];
          pac[i] = pac[j];
          array[j] = tmp;
          pac[j] = tmp2;
      }
   
      private static <E extends Comparable<? super E>> int partition(E[] array, int begin, int end, String[] pac) {
          int index = begin + RND.nextInt(end - begin + 1);
          E pivot = array[index];
          swap(array, index, end, pac);
          for (int i = index = begin; i < end; ++i) {
              if (array[i].compareTo(pivot) <= 0) {
                  swap(array, index++, i, pac);
              }
          }
          swap(array, index, end, pac);
          return (index);
      }
   
      private static <E extends Comparable<? super E>> void qsort(E[] array, int begin, int end, String[] pac) {
          if (end > begin) {
              int index = partition(array, begin, end, pac);
              qsort(array, begin, index - 1, pac);
              qsort(array, index + 1, end, pac);
          }
      }
   
      public static <E extends Comparable<? super E>> void sort(E[] array, String[] pac) {
          qsort(array, 0, array.length - 1, pac);
      }
   
      public static String[] ReadFile(double size, String file){
      	String[] str = new String[(int) size];
      	int i = 0;
      	try {	
  		    //Reading lines of the file and saving in a vector 
  	        BufferedReader in = new BufferedReader(new FileReader(file));
  	        while (in.ready()) {
                  str[i] = in.readLine();	
                  //System.out.printf("%s\n", str[i]);
                  i++;
  	        }
  	        
  	        in.close();
  	        
      	}
  	    catch (IOException e) {
  		  	System.out.printf("Error");
  	    }
  	    return str;
      }
      
      public void Order(double size, String file){
       	  String[] s;
      	
      	  s = ReadFile(size, file);
      	  Double[] time = new Double[(int)size];
      	  
      	  for(int cont = 0;cont < size; cont++){
      	      time[cont] = Double.parseDouble(s[cont].split("\t")[3]);
      	  }
      	
      	  this.sort(time, s);
      	  //System.out.println("l2 sorted:" + Arrays.toString(s));  
      	
      	  try {
  		      FileWriter writer  = new FileWriter(new File(file),false);
  			  PrintWriter out = new PrintWriter(writer);
  			  for (int cont = 0; cont < s.length; cont++){
  			      out.println(s[cont]);
  		      }
  			  writer.close();
      	  } catch (IOException e) {
  			  // TODO Auto-generated catch block
  			  e.printStackTrace();
  		  }
      }
}


