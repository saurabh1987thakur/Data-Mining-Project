import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
public class DataSetParser {
	public static void main(String[] args) throws Exception {
		prepareMovieList();
		prepareFinalCSV();
	}
	private static void prepareMovieList() throws Exception {
		BufferedReader readFile = null;
		File file = new File("/Users/kartikeyashukla/Downloads/python-imdb-master/movieList.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			readFile = new BufferedReader(new FileReader("/Users/kartikeyashukla/Downloads/movies.csv"));
			while(true){
				String line = readFile.readLine();
				if (line == null) {
					break;
				}
				if (line.trim().length() == 0) {
					break;
				}
				String delims = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
				String[] values = line.trim().split(delims);
				String movie = prepareMovieName(values[0]); 
				String name = " (" + values[1] + ")";
				bw.write(movie + name);
				bw.write("\n");	
			}
		} finally {
			readFile.close();
			bw.close();
		}
	}

	private static String prepareMovieName(String string) {
		if(string.contains("\"") && string.contains(",")){
			String s = "@@@#%%%%%#@";
			String delims = ", ";
			String[] values = string.trim().split(delims);
			if(values[1].length() > 4){
				string = string.replace("\"", "");
				return string;
			}
			values[1] = values[1].replace("\"", "");
			values[0] = values[0].replace("\"", "");
			return values[1].trim() + " " + values[0].trim();
		}
		return string;
	}

	private static void prepareFinalCSV() throws Exception{
		BufferedReader readFile = null;
		File file = new File("/Users/kartikeyashukla/Downloads/python-imdb-master/Absfinal.csv");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);	
		try {
			readFile = new BufferedReader(new FileReader("/Users/kartikeyashukla/Downloads/python-imdb-master/final.csv"));
			while(true){
				String line = readFile.readLine();
				if (line == null) {
					break;
				}
				if (line.trim().length() == 0) {
					break;
				}
				String delims = "\",\"";
				String[] values = line.trim().split(delims);
				String movie = cleanTheMovieTitle(values[0]);
				String name1 = cleanThisName(values[1]);
				String name2 = getThisName(values[2], true);
				String name3 = getThisName(values[2], false);
				bw.write("\"" + movie + "\",\"" + name1 + "\",\"" + name2 + "\",\"" + name3 + "\"");
				bw.write("\n");
			}
		} finally {
			readFile.close();
			bw.close();
		}	
	}

	private static String getThisName(String string, boolean b) {
		String delims = "\",,\"";
		String[] values = string.trim().split(delims);
		System.out.println(values[0]);
		if(b){
			String s1 = cleanThisName(values[0]);
			return s1;
		}
		values[1] = values[1].substring(0);
		String s2 = cleanThisName(values[1]);
		return s2.trim();
	}

	private static String cleanTheMovieTitle(String string) {
		StringBuilder sb = new StringBuilder();
		String delims = ",|\\s+";
		String[] values = string.trim().split(delims);
		for(int i = 1 ; i<values.length - 1; i++){
			sb.append(values[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	}

	private static String cleanThisName(String string) {
		if(string.length() <= 4){
			return " ";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 3; i< string.length(); i++){
			char a = string.charAt(i);
			if(a == '\'' && i>4){
				break;
			}
			sb.append(a);
		}
		return fixThisName(sb.toString().trim());
	}

	private static String fixThisName(String string) {
		string = string.replace("\"", "");
		String delims = ",|\\s+";
		String[] values = string.trim().split(delims);
		if(values.length == 1){
			return values[0].trim();
		}
		if(values.length > 2){
			return values[2].trim() + " " + values[0].trim();
		}
		return values[1].trim() + " " + values[0].trim();
	}

}
