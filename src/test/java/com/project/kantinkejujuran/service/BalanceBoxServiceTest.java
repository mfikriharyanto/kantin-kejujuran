package com.project.kantinkejujuran.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.project.kantinkejujuran.model.BalanceBox;
import com.project.kantinkejujuran.repository.BalanceBoxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalanceBoxServiceTest {
    @Mock
    private BalanceBoxRepository balanceBoxRepository;

    @InjectMocks
    private BalanceBoxServiceImpl balanceBoxService;

    private BalanceBox balanceBox;

    @BeforeEach
    void setUp() {
        balanceBox = new BalanceBox(10000L, 10000L);
    }

    @Test
    void testAddBalanceBox() {
        when(balanceBoxRepository.findCount()).thenReturn(1);
        when(balanceBoxRepository.findLast()).thenReturn(balanceBox);

        balanceBoxService.add(10000L);

        verify(balanceBoxRepository, times(1)).save(any(BalanceBox.class));
    }

    @Test
    void testAddBalanceBoxWithNegativeNumber() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            balanceBoxService.add(-10000L);
        });
        assertEquals("Invalid input", thrown.getMessage());

        verify(balanceBoxRepository, times(0)).save(any(BalanceBox.class));
    }

    @Test
    void testWithdrawnBalanceBox() {
        when(balanceBoxRepository.findCount()).thenReturn(1);
        when(balanceBoxRepository.findLast()).thenReturn(balanceBox);

        balanceBoxService.withdraw(10000L);

        verify(balanceBoxRepository, times(1)).save(any(BalanceBox.class));
    }

    @Test
    void testWithdrawnBalanceBoxWithNegativeNumber() {
        when(balanceBoxRepository.findCount()).thenReturn(1);
        when(balanceBoxRepository.findLast()).thenReturn(balanceBox);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            balanceBoxService.withdraw(-10000L);
        });
        assertEquals("Invalid input", thrown.getMessage());

        verify(balanceBoxRepository, times(0)).save(any(BalanceBox.class));
    }

    @Test
    void testWithdrawnBalanceBoxLessTotal() {
        when(balanceBoxRepository.findCount()).thenReturn(1);
        when(balanceBoxRepository.findLast()).thenReturn(balanceBox);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            balanceBoxService.withdraw(20000L);
        });
        assertEquals("Invalid input", thrown.getMessage());

        verify(balanceBoxRepository, times(0)).save(any(BalanceBox.class));
    }

    @Test
    void testGetLastTotal() {
        when(balanceBoxRepository.findCount()).thenReturn(1);
        when(balanceBoxRepository.findLast()).thenReturn(balanceBox);

        Long total = balanceBoxService.getLastTotal();
        assertEquals(10000L, total);

        verify(balanceBoxRepository, times(1)).findCount();
        verify(balanceBoxRepository, times(1)).findLast();
    }

    @Test
    void testGetLastTotalEmpty() {
        when(balanceBoxRepository.findCount()).thenReturn(0);

        Long total = balanceBoxService.getLastTotal();
        assertEquals(0, total);

        verify(balanceBoxRepository, times(1)).findCount();
        verify(balanceBoxRepository, times(0)).findLast();
    }
}