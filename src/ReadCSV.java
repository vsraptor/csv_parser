import static my.utils.say;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import my.CSV;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ReadCSV {

	CSV file;
	static String root = "/my/work/eclipse/CSV/data/";
	static String file_name = "write_test.csv";
	String header = "field1,field2,field3";
	String[] lines = 
		{ "data11,data12,data13", "data21,data22,data23"};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		say("> Read from csv ..." + root + file_name);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		say("> finished reading ...");
	}

	@Before
	public void setUp() throws Exception {
		file = new CSV();
		file.open(root + file_name, "r");
	}

	@After
	public void tearDown() throws Exception {
		file.close();
	}
	
	@Test
	public void by_id() throws IOException {
		file.parse_line();
		String[] lst = lines[0].split(",");
		for (int i=0; i < lst.length; i++) {
			assertNotNull(file.get(i+1));
			assertEquals("fail> no match", lst[i],file.get(i+1));
			say("ok> " + lst[i] + " = " + file.get(i+1));
		}
	}

}
