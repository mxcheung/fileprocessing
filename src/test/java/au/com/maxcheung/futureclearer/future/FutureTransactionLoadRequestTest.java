package au.com.maxcheung.futureclearer.future;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FutureTransactionLoadRequestTest extends FutureTransactionLoadRequestTst {

    private final static String JSON_STRING = "{\"specfileName\":\"specfileName\",\"datafileName\":\"datafileName\",\"reportfileName\":\"reportfileName\"}";

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        loadRequest = getRequest();
    }

    @Test
    public void shouldSerialize() throws JsonProcessingException {
        String json = this.mapper.writeValueAsString(loadRequest);
        assertEquals(JSON_STRING, json);
    }

    @Test
    public void shouldDeserialize() throws JSONException, JsonParseException, JsonMappingException, IOException {
        FutureTransactionLoadRequest lookupValue = mapper.readValue(JSON_STRING, FutureTransactionLoadRequest.class);
        assertEquals(DATAFILE_NAME, lookupValue.getDatafileName());
        assertEquals(SPECFILE_NAME, lookupValue.getSpecfileName());
        assertEquals(REPORTFILE_NAME, lookupValue.getReportfileName());
    }

}