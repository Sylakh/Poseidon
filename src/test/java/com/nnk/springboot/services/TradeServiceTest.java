package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;

	@InjectMocks
	private TradeService tradeService;

	private Trade trade;

	@BeforeEach
	public void setup() {
		trade = new Trade(1, "account", "type", 100.0, 200.0, 50.0, 60.0, "benchmark",
				new Timestamp(System.currentTimeMillis()), "security", "status", "trader", "book", "creationName",
				new Timestamp(System.currentTimeMillis()), "revisionName", new Timestamp(System.currentTimeMillis()),
				"dealName", "dealType", "sourceListId", "side");
	}

	@Test
	public void testFindAll() {
		List<Trade> trades = Arrays.asList(trade);
		when(tradeRepository.findAll()).thenReturn(trades);

		List<Trade> result = tradeService.findAll();

		assertEquals(1, result.size());
		assertEquals(trade.getAccount(), result.get(0).getAccount());
	}

	@Test
	public void testAdd() {
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		tradeService.add(trade);

		verify(tradeRepository, times(1)).save(any(Trade.class));
		assertNotNull(trade.getTradeDate());
		assertEquals("a", trade.getRevisionName());
	}

	@Test
	public void testFindById() {
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

		Trade result = tradeService.findById(1);

		assertNotNull(result);
		assertEquals(trade.getAccount(), result.getAccount());
	}

	@Test
	public void testFindById_NotFound() {
		when(tradeRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tradeService.findById(1);
		});

		assertEquals("Invalid trade Id:1", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		Trade updatedTrade = new Trade(1, "newAccount", "newType", 100.0, 200.0, 50.0, 60.0, "benchmark",
				new Timestamp(System.currentTimeMillis()), "security", "status", "trader", "book", "creationName",
				new Timestamp(System.currentTimeMillis()), "b", new Timestamp(System.currentTimeMillis()), "dealName",
				"dealType", "sourceListId", "side");
		tradeService.update(updatedTrade, 1);

		verify(tradeRepository, times(1)).save(any(Trade.class));
		assertEquals("newAccount", trade.getAccount());
	}

	@Test
	public void testUpdate_NotFound() {
		when(tradeRepository.findById(1)).thenReturn(Optional.empty());

		Trade updatedTrade = new Trade(1, "newAccount", "newType", 100.0, 200.0, 50.0, 60.0, "benchmark",
				new Timestamp(System.currentTimeMillis()), "security", "status", "trader", "book", "creationName",
				new Timestamp(System.currentTimeMillis()), "b", new Timestamp(System.currentTimeMillis()), "dealName",
				"dealType", "sourceListId", "side");

		Exception exception = assertThrows(Exception.class, () -> {
			tradeService.update(updatedTrade, 1);
		});

		assertEquals("Invalid trade Id:1", exception.getMessage());
	}

	@Test
	public void testDelete() {
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		doNothing().when(tradeRepository).deleteById(1);

		tradeService.delete(1);

		verify(tradeRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDelete_NotFound() {
		when(tradeRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tradeService.delete(1);
		});

		assertEquals("Invalid trade Id:1", exception.getMessage());
	}
}
