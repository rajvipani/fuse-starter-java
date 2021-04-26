package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IEXHistoricalPrices;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEXHistoricalPrices", url = "${spring.rest.iexHistoricalBasePath}")
public interface IexHistoricalPricesClient {

  /**
   * Get historical prices for a symbol passed. See https://iextrading.com/developer/docs/#historical-prices.
   *
   * @param symbol stock symbols to get Historical price for.
   * @return a list of the prices for the symbol passed in.
   */
  @GetMapping("/stock/{symbol}/chart?token=${spring.rest.iexHistoricalToken}")
  List<IEXHistoricalPrices> getHistoricalPricesForSymbol(@PathVariable("symbol") String symbol);

  /**
   * Get historical prices for a date of the symbol passed. See https://iextrading.com/developer/docs/#historical-prices.
   *
   * @param symbol stock symbols to get Historical price for.
   * @param date date for the symbol we want historical price for.
   * @return a list of the prices for the symbol passed in.
   */
  @GetMapping(
      "/stock/{symbol}/chart/date/{date}?chartByDay=true&token=${spring.rest.iexHistoricalToken}")
  List<IEXHistoricalPrices> getHistoricalPricesForDate(@PathVariable("symbol") String symbol,
      @PathVariable("date") String date);

  /**
   * Get historical prices for a range for the symbol passed. See https://iextrading.com/developer/docs/#historical-prices.
   *
   * @param symbol stock symbols to get Historical price for.
   * @param range range for the symbol we want historical price from.
   * @return a list of the prices for the symbol passed in.
   */
  @GetMapping("/stock/{symbol}/chart/{range}?token=${spring.rest.iexHistoricalToken}")
  List<IEXHistoricalPrices> getHistoricalPricesForRange(@PathVariable("symbol") String symbol,
      @PathVariable("range") String range);

}


