package org.galatea.starter.domain.rpsy;

import java.time.LocalDate;
import java.util.List;
import org.galatea.starter.domain.IexHistoricalPricesEntity;
import org.springframework.data.repository.CrudRepository;

public interface IexHistoricalPricesRpsy extends CrudRepository<IexHistoricalPricesEntity, String> {

  List<IexHistoricalPricesEntity> findSymbolBetweenDates(String symbol, LocalDate startDate, LocalDate endDate);

}
