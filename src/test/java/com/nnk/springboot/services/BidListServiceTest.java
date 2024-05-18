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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

	@Mock
	private BidListRepository bidListRepository;

	@InjectMocks
	private BidListService bidListService;

	private BidList bidList;

	@BeforeEach
	public void setup() {
		bidList = new BidList(1, "account", "type", 10.0, 20.0, 15.0, 25.0, "benchmark",
				new Timestamp(System.currentTimeMillis()), "commentary", "security", "status", "trader", "book",
				"creationName", new Timestamp(System.currentTimeMillis()), "revisionName",
				new Timestamp(System.currentTimeMillis()), "dealName", "dealType", "sourceListId", "side");
	}

	@Test
	public void testFindAll() {
		List<BidList> bidLists = Arrays.asList(bidList);
		when(bidListRepository.findAll()).thenReturn(bidLists);

		List<BidList> result = bidListService.findAll();

		assertEquals(1, result.size());
		assertEquals(bidList.getAccount(), result.get(0).getAccount());
	}

	@Test
	public void testAdd() {
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		bidListService.add(bidList);

		verify(bidListRepository, times(1)).save(any(BidList.class));
		assertNotNull(bidList.getCreationDate());
	}

	@Test
	public void testFindById() {
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

		BidList result = bidListService.findById(1);

		assertNotNull(result);
		assertEquals(bidList.getAccount(), result.getAccount());
	}

	@Test
	public void testFindById_NotFound() {
		when(bidListRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			bidListService.findById(1);
		});

		assertEquals("Invalid BidList Id:1", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		BidList updatedBidList = new BidList(1, "newAccount", "newType", 30.0, 40.0, 35.0, 45.0, "benchmark",
				new Timestamp(System.currentTimeMillis()), "commentary", "security", "status", "trader", "book",
				"creationName", new Timestamp(System.currentTimeMillis()), "b",
				new Timestamp(System.currentTimeMillis()), "dealName", "dealType", "sourceListId", "side");
		bidListService.update(updatedBidList, 1);

		verify(bidListRepository, times(1)).save(any(BidList.class));
		assertEquals("newAccount", bidList.getAccount());
	}

	@Test
	public void testUpdate_NotFound() {
		when(bidListRepository.findById(1)).thenReturn(Optional.empty());

		BidList updatedBidList = new BidList(1, "newAccount", "newType", 30.0, 40.0, 35.0, 45.0, "benchmark",
				new Timestamp(System.currentTimeMillis()), "commentary", "security", "status", "trader", "book",
				"creationName", new Timestamp(System.currentTimeMillis()), "b",
				new Timestamp(System.currentTimeMillis()), "dealName", "dealType", "sourceListId", "side");

		Exception exception = assertThrows(Exception.class, () -> {
			bidListService.update(updatedBidList, 1);
		});

		assertEquals("Invalid bidlist Id:1", exception.getMessage());
	}

	@Test
	public void testDelete() {
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
		doNothing().when(bidListRepository).deleteById(1);

		bidListService.delete(1);

		verify(bidListRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDelete_NotFound() {
		when(bidListRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			bidListService.delete(1);
		});

		assertEquals("Invalid BidList Id:1", exception.getMessage());
	}
}
