package org.galatea.starter.domain;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
public class IexHistoricalPricesEntity {

  @Id
  @NonNull
  private String symbolID;

  @NonNull
  private String symbol;

  @NonNull
  private BigDecimal open;

  @NonNull
  private BigDecimal close;

  @NonNull
  private BigDecimal high;

  @NonNull
  private BigDecimal low;

  @NonNull
  private BigDecimal volume;

  @NonNull
  private String date;

  public static List<IexHistoricalPricesEntity> createEntityList
      (List<IEXHistoricalPrices> iexHistoricalPrices) {
    List<IexHistoricalPricesEntity> historicalPricesEntityList = null;

    for(IEXHistoricalPrices historicalPrice : iexHistoricalPrices) {

      historicalPricesEntityList.add(new IexHistoricalPricesEntity
          (historicalPrice.getSymbol() + historicalPrice.getDate(),
              historicalPrice.getSymbol(),
              historicalPrice.getOpen() ,
              historicalPrice.getClose(),
              historicalPrice.getHigh(),
              historicalPrice.getLow(),
              historicalPrice.getVolume(),
              historicalPrice.getDate()));
    }
    return historicalPricesEntityList;
  }
}
