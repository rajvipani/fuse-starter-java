package org.galatea.starter.service;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.galatea.starter.domain.IEXHistoricalPrices;
import org.galatea.starter.domain.IexHistoricalPricesEntity;
import org.galatea.starter.domain.rpsy.IexHistoricalPricesRpsy;
import org.springframework.stereotype.Service;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexHistoricalPricesService {

  @NonNull
  private IexHistoricalPricesRpsy iexHistoricalPricesRpsy;

  @NonNull
  private IexHistoricalPricesClient iexHistoricalPricesClient;

  private LocalDate startDate, endDate;
  private List<IEXHistoricalPrices> historicalPrices;
  private List<IexHistoricalPricesEntity> historicalPricesEntities;
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
      startDate = getStartDateFromRange(range);
      endDate = LocalDate.now();

    } else if (StringUtils.isBlank(range) && StringUtils.isNoneBlank(date)) {

      startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
      endDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
    } else {
      startDate = getStartDateFromRange("1m");
      endDate = LocalDate.now();
    }

    List<IexHistoricalPricesEntity> pricesFromDatabase = iexHistoricalPricesRpsy
        .findSymbolBetweenDates(symbol, startDate, endDate);

    if (pricesFromDatabase.isEmpty()) {

      if (StringUtils.isBlank(date) && StringUtils.isNoneBlank(range)) {
      historicalPrices = iexHistoricalPricesClient.getHistoricalPricesForRange(symbol, range);
      } else if (StringUtils.isBlank(range) && StringUtils.isNoneBlank(date)) {
      historicalPrices = iexHistoricalPricesClient.getHistoricalPricesForDate(symbol, date);
      } else {
      historicalPrices = iexHistoricalPricesClient.getHistoricalPricesForSymbol(symbol);
      }
      historicalPricesEntities = IexHistoricalPricesEntity.createEntityList(historicalPrices);
      iexHistoricalPricesRpsy.saveAll(historicalPricesEntities);

    } else {

      for(IexHistoricalPricesEntity priceInDB : pricesFromDatabase) {
        historicalPrices.add(new IEXHistoricalPrices(
            priceInDB.getSymbol(),
            priceInDB.getOpen(),
            priceInDB.getClose(),
            priceInDB.getHigh(),
            priceInDB.getLow(),
            priceInDB.getVolume(),
            priceInDB.getDate()));
      }
    }

    return historicalPrices;
  }

  private LocalDate getStartDateFromRange(String range) {
    LocalDate calcDate;
    if (range == "max") {
      calcDate = LocalDate.now().minusYears(15);
    } else if (Character.toLowerCase(range.charAt(1)) == 'y') {
      calcDate = LocalDate.now().minusYears(Character.getNumericValue(range.charAt(0)));
    } else if (range.toLowerCase().equals("ytd")) {
      calcDate = LocalDate.now().withDayOfYear(1);
    } else if (Character.toUpperCase(range.charAt(1)) == 'W') {
      calcDate = LocalDate.now().minusWeeks(Character.getNumericValue(range.charAt(0)));
    } else if (Character.toUpperCase(range.charAt(1)) == 'M' && range.length() == 2) {
      calcDate = LocalDate.now().minusMonths(Character.getNumericValue(range.charAt(0)));
    } else {
      calcDate = LocalDate.now().minusMonths(1);
    }
    return calcDate;
  }
}
