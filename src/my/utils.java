package my;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
	//the biggest invention in Java-world since sliced-bread ;)
	public static <ARG> void say(ARG arg) { System.out.println(arg); }
	
	public static boolean match(String re, String str) {
		Pattern pat = Pattern.compile(re);
		Matcher m = pat.matcher(str);
		if (m.find()) return true;
		return false;
	}

	//joins Iterable or Collection into a String
	public static <T> String join(String delimiter, Iterable<T> lst) {
		StringBuilder str = new StringBuilder();
		Iterator<T> it = lst.iterator();
		while ( it.hasNext() ) {
			str.append(it.next().toString());
			if (it.hasNext()) str.append(delimiter);
		}
		return str.toString();
	}

	//limited map() functionality as in Functional languages
	public static <T> Collection<T> map(Collection<T> lst, MapFun fun) {
		List<T> new_lst = new ArrayList<T>();
		for (T el : lst) new_lst.add( (T) fun.map_fun(el) );
		return new_lst;
	}
	
	//read a whole file into a List : of one line per element
	public static List<String> slurp(String file_name) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file_name));
		List<String> lst = new ArrayList<String>();
		String line;
		while( (line = br.readLine() ) != null ) {
			lst.add( line );
		};
		br.close();
		return lst;
	}
	


}

