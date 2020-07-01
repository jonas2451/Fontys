package entities;

import dao.Entity1;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Receipt implements Entity1<String> {


    String receiptnumber;
    boolean isUnloading;
    BigDecimal actualWeight;
    LocalDate date;

    @Override
    public String getId() {
        return receiptnumber;
    }

    @Override
    public String getNaturalId() {
        return "receiptno";
    }
}
