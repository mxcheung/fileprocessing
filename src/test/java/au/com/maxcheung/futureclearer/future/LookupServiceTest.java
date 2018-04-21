package au.com.maxcheung.futureclearer.future;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.fasterxml.jackson.core.JsonProcessingException;

import au.com.maxcheung.futureclearer.flatfile.FlatFileReader;
import au.com.maxcheung.futureclearer.validate.FutureValidator;

@RunWith(MockitoJUnitRunner.class)
public class LookupServiceTest extends FutureTransactionLoadRequestTst {

    private LookupService lookupService;

    @Mock
    FlatFileReader flatFileReader;

    @Mock
    FutureValidator futureValidator;

    @Before
    public void setup() {
        lookupService = new LookupService(flatFileReader, futureValidator);
        loadRequest = getRequest();
    }

    @Test(expected = FileLoadException.class)
    public void shouldThrowFileLoadExceptionWhenReadInvalidLookupFile() throws UnexpectedInputException, ParseException, Exception  {
        loadRequest.setSpecfileName("NonExistentFile");
        doThrow(new FileLoadException("")).when(flatFileReader).read(
                loadRequest.getSpecfileName(), loadRequest.getDatafileName());
        lookupService.lookupLoad(loadRequest);
    }
    
    
    
/*
    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionReadInvalidLookupFile() throws FileNotFoundException, IOException {
        lookupLoadRequest.setFileName("actually_a_directory.csv");
        doThrow(new IOException()).when(lookupCSVReader).readLookup(lookupLoadRequest.getFileName());
        lookupService.lookupLoad(lookupLoadRequest);
    }

    @Test
    public void shouldFetchLookupByType() throws JsonProcessingException, FileNotFoundException, IOException {
        ArrayList<LookupRow> data = new ArrayList<LookupRow>();
        data.add(getLookUpRow());
        when(lookupCSVReader.readLookup(lookupLoadRequest.getFileName())).thenReturn(data);
        when(lookupRepository.findByLookupTypeAndLookupTypeName(LOOKTYPE, LOOKTYPE_NAME)).thenReturn(lookupEntityList);
        Map<String, List<LookupValue>> lookupRows = lookupService.fetchLookupByType(lookupGetRequest);
        assertEquals(1, lookupRows.size());
    }

    @Test
    public void shouldConvertUp() {
        List<LookupValue> result = lookupService.convertUp(lookupEntityList);
        assertEquals(1, result.size());
        LookupValue lookupValue = result.get(0);
        verifyLookupValue(lookupValue);
    }

    @Test
    public void shouldConvertToMap() {
        lookupMap = getLookupMap();
        Map<String, Map<String, String>> result = lookupService.convertToMap(lookupMap);
        assertEquals(lookupMap.size(), result.size());
    }

    private void verifyLookupValue(LookupValue lookupValue) {
        assertEquals(CODE, lookupValue.getKey());
        assertEquals(VALUE, lookupValue.getValue());
    }

    @Test
    public void shouldLoadLookup() throws JsonProcessingException, FileNotFoundException, IOException {
        ArrayList<LookupRow> data = new ArrayList<LookupRow>();
        data.add(getLookUpRow());
        when(lookupCSVReader.readLookup(lookupLoadRequest.getFileName())).thenReturn(data);
        List<LookupEntity> lookupRows = lookupService.lookupLoad(lookupLoadRequest);
        assertEquals(1, lookupRows.size());
    }

    @Test
    public void shouldSaveLookupFile() {
        List<LookupEntity> newList = new ArrayList<LookupEntity>();
        lookupService.save(newList);
    }

    @Test
    public void shouldConvertToEntity() {
        List<LookupRow> rows = new ArrayList<LookupRow>();
        rows.add(getLookUpRow());
        List<LookupEntity> result = lookupService.convertDown(rows);
        assertEquals(1, result.size());
    }

    @Test
    public void verifyLookupGetRequest() {
        assertEquals(LOOKTYPE, lookupGetRequest.getLookupType());
        assertEquals(1, lookupGetRequest.getShoppingList().size());
    }

    @Test
    public void verifyLookupLoadRequest() {
        assertEquals(LOOKUP_CSV, lookupLoadRequest.getFileName());
    }

    @Test
    public void verifyLookupEntity() {
        assertEquals(ENTITY_ID, lookupEntity.getEntityId());
        assertEquals(LOOKTYPE, lookupEntity.getLookupType());
        assertEquals(LOOKTYPE_NAME, lookupEntity.getLookupTypeName());
        assertEquals(CODE, lookupEntity.getLookupCode());
        assertEquals(VALUE, lookupEntity.getLookupValue());

    }
*/
}