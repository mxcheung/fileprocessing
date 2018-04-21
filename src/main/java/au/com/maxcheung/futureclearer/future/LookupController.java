package au.com.maxcheung.futureclearer.future;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

/**
 * ProductController - supports crud method for product repository.
 * 
 * @author Max Cheung <max.cheung@lonsec.com.au>
 */

@RestController
@Validated
@RequestMapping(value = LookupController.LOOKUP_BASE_CONTEXT)
public class LookupController {

    protected static final String LOOKUP_BASE_CONTEXT = "/lookup";

    private static final String LOOKUP_REFRESH = "/lookupLoadALL";

    @Autowired
    private LookupService lookupService;



    @RequestMapping(value = LOOKUP_REFRESH, method = POST)
    @ResponseStatus(OK)
    @ResponseBody
    public ResponseEntity<List<FutureTransactionSummary>> lookupRefresh(
            @Valid @RequestBody final FutureTransactionLoadRequest lookupLoadRequest) throws FileLoadException {
        return new ResponseEntity<>(lookupService.lookupLoad(lookupLoadRequest), OK);
    }

}
