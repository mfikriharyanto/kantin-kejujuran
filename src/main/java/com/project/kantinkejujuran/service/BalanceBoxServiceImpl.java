package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.model.BalanceBox;
import com.project.kantinkejujuran.repository.BalanceBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceBoxServiceImpl implements BalanceBoxService {

    @Autowired
    private BalanceBoxRepository balanceBoxRepository;

    @Override
    public synchronized void add(Long change) {
        if (change < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        Long lastTotal = this.getLastTotal();
        BalanceBox balanceBox = new BalanceBox(change, lastTotal + change);
        balanceBoxRepository.save(balanceBox);
    }

    @Override
    public synchronized void withdraw(Long change) {
        Long lastTotal = this.getLastTotal();
        if (lastTotal < change || change < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        BalanceBox balanceBox = new BalanceBox(-1 * change, lastTotal - change);
        balanceBoxRepository.save(balanceBox);
    }

    @Override
    public Long getLastTotal() {
        if (balanceBoxRepository.findCount() == 0) {
            return 0L;
        }
        BalanceBox lastBalanceBox = balanceBoxRepository.findLast();
        return lastBalanceBox.getTotal();
    }
}
