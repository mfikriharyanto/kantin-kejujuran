package com.project.kantinkejujuran.controller;

import com.project.kantinkejujuran.service.BalanceBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/balance-box")
public class BalanceBoxController {

    @Autowired
    private BalanceBoxService balanceBoxService;

    @GetMapping
    public String balanceBox(Model model) {
        Long total = balanceBoxService.getLastTotal();
        model.addAttribute("total", total);
        return "balance_box";
    }

    @PostMapping(path = "/add", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Long> balanceBoxAdd(@RequestBody Long change) {
        try {
            balanceBoxService.add(change);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long total = balanceBoxService.getLastTotal();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @PostMapping(path = "/withdraw", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Long> balanceBoxWithdraw(@RequestBody Long change) {
        try {
            balanceBoxService.withdraw(change);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long total = balanceBoxService.getLastTotal();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}