import static my.utils.join;
import static my.utils.say;
import static org.junit.Assert.*;

import java.io.IOException;

import my.CSV;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ReadQuotedCSV {
	
	CSV file;
	static String root = "/my/work/eclipse/CSV/data/";
	static String file_name = "quoted.csv";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		say("> Read from quoted csv ..." + root + file_name);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		say("> finished reading ...");
	}

	@Before
	public void setUp() throws Exception {
		file = new CSV();
		file.set_quote("\"");//we are parsing quoted csv file
		file.open(root + file_name, "r");		
	}

	@After
	public void tearDown() throws Exception {
		file.close();
	}

	@Test
	public void test() throws IOException {
		while( file.parse_line() ) {
			say(file.line);
			say(join(":",file.get_fields() ));
		}
		
	}

}
