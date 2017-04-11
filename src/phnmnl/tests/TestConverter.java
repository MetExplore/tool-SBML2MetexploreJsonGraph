package phnmnl.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import phnmnl.sbml.Reader;
import phnmnl.sbml.converter.AbstractConverter;
import phnmnl.sbml.converter.FBC2toJsonConverter;
import phnmnl.sbml.converter.SBML2jsonConverter;
import phnmnl.tests.utils.Dummy;
import phnmnl.tests.utils.MiniRec2Dummy;
import phnmnl.tests.utils.UbFluxNetDummy;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestConverter {
	
	public static AbstractConverter conv;
	
	@Parameters
    public static Collection<Dummy[]> data() {
        return Arrays.asList(new Dummy[][] {
                 {new MiniRec2Dummy() }, 
                 {new UbFluxNetDummy() }  
           });
    }

    @Parameter(0)
    public Dummy inputDummy;
    
    @Test
    public void testA_constructor(){
    	Reader r=new Reader(inputDummy.getInputFile());
    	
    	try {
			r.read();
			if(r.isFBCModel()){
				conv=new FBC2toJsonConverter(r.getModel());
			}else{
				conv=new SBML2jsonConverter(r.getModel());
			}
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
    	assertNotNull("Converter is null", conv);
    }

	@Test
	public void testB_Convert() {
		
		conv.convert();	
		assertNotNull("converted Json is null",conv.getJson());
		inputDummy.setJson(conv.getJson());
		inputDummy.testJson();
		
	}

//	@Test
//	public void testB_ParseReaction() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testC_AddLink() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testD_GetAdditionalInfoInNote() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testE_CreateReactionNode() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testF_CreateMetaboliteNode() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testD_WriteJsonToFile() {
//		fail("Not yet implemented"); // TODO
//	}

}
