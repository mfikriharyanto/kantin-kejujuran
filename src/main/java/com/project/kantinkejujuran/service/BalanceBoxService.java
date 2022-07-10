package com.project.kantinkejujuran.service;

public interface BalanceBoxService {
    void add(Long change);

    void withdraw(Long change);

    Long getLastTotal();
}
