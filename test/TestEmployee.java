import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.flp.ems.util.InvalidMenuSelectionException;
import com.flp.ems.view.BootClass;

import junit.framework.Assert;

public class TestEmployee {

	BootClass start = new BootClass();
	
	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test() throws InvalidMenuSelectionException {
		start.menuSelection();
		
	}
	
}
