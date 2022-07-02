package com.project.kantinkejujuran.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Date;

public class BalanceBoxTest {
    private Class<?> balanceBoxClass;
    private BalanceBox balanceBox;

    @BeforeEach
    public void setUp() throws Exception {
        balanceBoxClass = Class.forName("com.project.kantinkejujuran.model.BalanceBox");
        balanceBox = new BalanceBox(10000L, 10000L);
    }

    @Test
    public void testUserIsConcreteClass() {
        assertFalse(Modifier.isAbstract(balanceBoxClass.getModifiers()));
    }

    @Test
    public void testGetIdShouldReturnCorrectly() {
        String id = balanceBox.getId();
        assertNull(id);
    }

    @Test
    public void testGetChangeShouldReturnCorrectly() {
        Long change = balanceBox.getChange();
        assertEquals(10000L, change);
    }

    @Test
    public void testGetTotalShouldReturnCorrectly() {
        Long total = balanceBox.getTotal();
        assertEquals(10000L, total);
    }

    @Test
    public void testGetCreatedDateShouldReturnCorrectly() {
        Date date = balanceBox.getDateCreated();
        assertNull(date);
    }

    @Test
    void testSetIdShouldChangePassword() {
        balanceBox = new BalanceBox();
        balanceBox.setId("1234567890");
        String id = balanceBox.getId();
        assertEquals("1234567890", id);
    }

    @Test
    void testSetChangeShouldChangePassword() {
        balanceBox = new BalanceBox();
        balanceBox.setChange(20000L);
        Long change = balanceBox.getChange();
        assertEquals(20000L, change);
    }

    @Test
    void testSetTotalShouldChangePassword() {
        balanceBox = new BalanceBox();
        balanceBox.setTotal(20000L);
        Long total = balanceBox.getTotal();
        assertEquals(20000L, total);
    }

    @Test
    void testSetDateShouldChangePassword() {
        Date currentDate = new Date();
        balanceBox = new BalanceBox();
        balanceBox.setDateCreated(currentDate);
        assertEquals(currentDate, balanceBox.getDateCreated());
    }
}