import static my.utils.say;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import my.CSV;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteCSV {
	
	CSV file;
	static String root = "/my/work/eclipse/CSV/data/";
	static String file_name = "write_test.csv";
	String header = "field1,field2,field3";
	String[] lines = 
		{ "data11,data12,data13", "data21,data22,data23"};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		say("> Write to csv ..." + root + file_name);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		say("> finished writing ...");
	}

	
	@Before
	public void setUp() throws Exception {
		file = new CSV();
		//file.set_qq();
		say(file.is_quoted());
		String full_name = root + file_name;
		File tmp = new File(full_name);
		if (tmp.exists()) { 
			tmp.delete();
			say("info: Existing file deleted : " + full_name);
		}
		file.open(full_name, "w");
	}

	@After
	public void tearDown() throws Exception {
		file.close();
	}

	public void write_lines() throws IOException {
		for (int i=0;i< lines.length; i++) {
			List<String> lst = Arrays.asList( lines[i].split(",") );
			file.write(lst);
		}
	}
	@Test
	public void basic_write() throws IOException {
		write_lines();		
	}

}
