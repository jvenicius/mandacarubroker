package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Retorna todas as ações")
    @Test
    void getAllStocks_ReturnsListOfStocks() {
        // Arrange
        List<Stock> expectedStocks = Arrays.asList(new Stock(), new Stock());
        when(stockRepository.findAll()).thenReturn(expectedStocks);

        // Act
        List<Stock> actualStocks = stockService.getAllStocks();

        // Assert
        assertEquals(expectedStocks, actualStocks);
    }

    @DisplayName("Retorna uma ação por ID")
    @Test
    void getStockById_ReturnsStockById() {
        // Arrange
        String stockId = "validId";
        Stock expectedStock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(expectedStock));

        // Act
        Optional<Stock> actualStock = stockService.getStockById(stockId);

        // Assert
        assertTrue(actualStock.isPresent());
        assertEquals(expectedStock, actualStock.get());
    }

    @DisplayName("Cria uma nova ação")
    @Test
    void createStock_CreatesNewStock() {
        // Arrange
        RequestStockDTO requestData = new RequestStockDTO("BB3", "Banco do Brasil S.A.", 56.90);
        Stock expectedStock = new Stock(requestData);
        when(stockRepository.save(any(Stock.class))).thenReturn(expectedStock);

        // Act
        Stock actualStock = stockService.createStock(requestData);

        // Assert
        assertEquals(expectedStock, actualStock);
    }

    @DisplayName("Atualiza uma ação existente")
    @Test
    void updateStock_UpdatesExistingStock() {
        // Arrange
        String stockId = "validId";
        RequestStockDTO updatedData = new RequestStockDTO("BB4", "Banco do Brasil S.A.", 60.00);
        Stock existingStock = new Stock(updatedData); // Mock an existing stock
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(existingStock);

        // Act
        Optional<Stock> actualUpdatedStock = stockService.updateStock(stockId, existingStock);

        // Assert
        assertTrue(actualUpdatedStock.isPresent());
        assertEquals(existingStock, actualUpdatedStock.get());
    }

    @DisplayName("Deleta uma ação por ID")
    @Test
    void deleteStock_DeletesStockById() {
        // Arrange
        String stockId = "validId";

        // Act
        stockService.deleteStock(stockId);

        // Assert
        verify(stockRepository, times(1)).deleteById(stockId);
    }

}
