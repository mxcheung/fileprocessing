package au.com.maxcheung.service.futureclearer.csv;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Product Classification DTO for company maintenance.
 * 
 * @author Max Cheung <max.cheung@lonsec.com.au>
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "columnName", "start", "end" })
public class FlatFileSpecRow {

    private String columnName;
    private int start;
    private int end;
    private int fieldSize;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

}
