package my;
import my.MapFun;
import static my.utils.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CSV implements AutoCloseable {

	public String file_name;
	private String separator = ",";
	//if this value is set we will assume quoted csv file
	private String quote = null;
	public String mode;
	public Object file;
	public String line;//current line being processed 
	private List<String> fields = new ArrayList<>();
	Boolean is_first_line = true; 
	public Boolean with_header = false;
	//we want to be able to inspect field names and fields idx forward and backward
	public TwoWayHashmap<Integer, String> header;
	
	//functional like programming : to be used by utils.map()	
	private MapFun quote_fun = new MapFun() {
		public <T> T map_fun(Collection<T> lst, int idx) {
			return (T) (quote + ((List<T>) lst).get(idx) + quote);				
		}
	};
	
	public void with_header() { with_header = true;}
	public void set_sep(String str) { separator = str; }
	public void set_quote(String str) { quote = str; }
	public boolean is_quoted() { return quote == null ? false : true; }
	public void set_qq() { set_quote("\""); }
	
	public void open(File file, String mode) throws IOException {
		this.mode = mode;
		switch(mode) {
		case "r" :
			this.file = new BufferedReader(new FileReader(file));
			break;
		case "w" :
			this.file = new BufferedWriter(new FileWriter(file));
			break;	
		}
	}
	
	public void open(String file_name, String mode) throws IOException {
		this.file_name = file_name;
		File the_file = new File(file_name);
		//if (the_file.exists())
		open( the_file, mode );
	}
	
	public int fields_count() { return fields.size(); }
	//access field by ID
	public String get(int idx) { return fields.get(idx-1); }
	//access field by Name
	public String get(String idx) { return fields.get( header.via_val(idx)); };
	//get the parsed field values as List
	public List<String> get_fields() { return fields; }
	// ditto ....
	public Collection<String> field_names() { return header.values(); }
	
	//parse non quoted line
	public void parse(String line) { fields = Arrays.asList(line.split(separator)); }
	
	//guess what this do ?;)
	public void parse_quoted(String line) {
		
		fields.clear();//get clean buffer
		int quote_idx = line.indexOf(quote);//current quote idx
		int sep_idx = line.indexOf(separator);// current separator idx
		int pos = 0;
		
		while (! (quote_idx == -1 && sep_idx == -1)) {
					
			boolean has_quote = quote_idx != -1;//is there quote
			boolean has_sep = sep_idx != -1;
			boolean q_before_s = quote_idx < sep_idx;
			
			//it is a separator, just grab the sub string between old and new pos
			if ((has_quote && has_sep && !q_before_s) || (has_sep && !has_quote) ) {		
				fields.add(line.substring(pos,pos+sep_idx));
				pos += sep_idx + 1;//move the cursor for the next search
			}
			
			//handling quoted fields is abit more complex
			if ((has_quote && has_sep && q_before_s) || (has_quote && !has_sep) ) {
				
				int old_pos = pos;
				pos += quote_idx + 1;
				//quote_idx has to change in sync, for the loop to catch end of string conditions
				quote_idx = line.substring(pos).indexOf(quote);//look ahead
				pos += quote_idx + 1; 
				fields.add(line.substring(old_pos+1,pos-1));// grab the quoted string w/o the quotes		
				//quote is normally followed by separator Ex: "something",
				if (pos < line.length()) pos += 1; // so skip it
			}
		
			//search for new separator and quote
			quote_idx = line.substring(pos).indexOf(quote);
			sep_idx = line.substring(pos).indexOf(separator);			
		}
		
		// add the rest of the string (without last quote, if exist)
		int len = line.length();
		if (line.charAt(len-1) != quote.charAt(0)) fields.add(line.substring(pos,len));
	}
	
	public boolean parse_line() throws IOException {
		line = ((BufferedReader) file).readLine();
		if (line != null) { 
			if(quote == null) { parse(line); }
			else { parse_quoted(line); }
		} else { return false; }
		//process this line as header
		if (with_header && is_first_line) {
			header = new TwoWayHashmap<>();
			for (int i=0; i < fields_count() ;i++) {
				header.add(i,get(i+1));//fields start from 1 ... n, not 0
			};
			is_first_line = false;
			return parse_line();//it was header get the next line
		}
		return true;
	}

	public <T> String lst2csv(List<T> values) {
		if (is_quoted()) return join(separator, map(values, quote_fun));
		return join(separator, values);
	}
	public String lst2csv(String[] values) {
		return lst2csv(Arrays.asList(values));
	}
	
	public void write_line(String str) throws IOException {
		((BufferedWriter) file).write(str + "\n");
	}
	public <T> void write_lst(List<T> lst) throws IOException {
		write_line(lst2csv(lst));
	}
	public <T> void write(List<T> lst) throws IOException {
		write_lst(lst);
	}
	
	public void close() throws IOException {
		switch (mode) {
			case "r": ((BufferedReader) file).close(); break;
			case "w": ((BufferedWriter) file).close(); break;
		}
	}

}


