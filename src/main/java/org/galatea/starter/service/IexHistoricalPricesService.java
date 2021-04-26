package org.galatea.starter.service;

import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.galatea.starter.domain.IEXHistoricalPrices;
import org.springframework.stereotype.Service;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexHistoricalPricesService {

  @NonNull
  private IexHistoricalPricesClient iexHistoricalPricesClient;
  /**
   * Get the Historical Prices of the symbol passed in.
   *
   * @param symbol the list of symbols to get a last traded price for.
   * @param date date for which historical price is needed for the symbol
   * @param range range for which historical price is needed for the symbol
   * @return a list of Historical price objects for the Symbol that is passed in.
   */

  public List<IEXHistoricalPrices> getHistoricalPrices(final String symbol, final String date,
      final String range) {

    if (symbol.isEmpty()) {
      return Collections.emptyList();
    } else if (StringUtils.isBlank(date) && StringUtils.isNoneBlank(range)) {
      return iexHistoricalPricesClient.getHistoricalPricesForRange(symbol, range);
    } else if (StringUtils.isBlank(range) && StringUtils.isNoneBlank(date)) {
      return iexHistoricalPricesClient.getHistoricalPricesForDate(symbol, date);
    } else {
      return iexHistoricalPricesClient.getHistoricalPricesForSymbol(symbol);
    }
  }
}
