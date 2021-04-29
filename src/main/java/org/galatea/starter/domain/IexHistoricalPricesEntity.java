package org.galatea.starter.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@Entity
public class IexHistoricalPricesEntity {

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
}
