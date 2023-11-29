package com.poly.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.CategoryDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.entity.Product;
import com.poly.entity.RevenueItem;
import com.poly.service.AccountService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/revenue")
public class RevenueRestController {
	@Autowired
	OrderDAO dao;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	CategoryDAO categoryDAO;
	
	@GetMapping
	public List<Integer> getAllYear() {
		return dao.findByYear();
	}
	
	@GetMapping("{year}")
	public List<Object[]> showRevenueByYear(@PathVariable(value = "year", required = false) Integer year) {
		return dao.findByDoanhThuNam(year);
	}
	
	// 4 bảng trong admin
	@GetMapping("/today")
	public Double getDailyRevenue() {
	    return dao.getTotalRevenueToday(); 
	}
	@GetMapping("/saleVolume")
	public Integer getsaleVolume() {
	    return orderDetailDAO.getTotalQuantitySoldThisMonth(); 
	}
	@GetMapping("/averageOrderValue")
	public Double AverageOrderValue() {
	    return dao.AverageOrderValue(); 
	}
	@GetMapping("/revenueYear")
	public Double getRevenueYear() {
	    return dao.getTotalRevenueThisYear(); 
	}
	// BẢNG CATEGORY
	@GetMapping("/totalQuantityByCategory")
	public List<Object[]> getTotalQuantityByCategory() {
	    return categoryDAO.getTotalQuantityByCategoryNative(); 
	}
	@GetMapping("/totalQuantitySoldByCategory")
	public List<Object[]> getTotalQuantitySoldByCategory() {
	    return categoryDAO.getTotalQuantitySoldByCategoryNative(); 
	}
	//BẢNG CITY
	@GetMapping("/city")
	public List<Object[]> getCity() {
	    return dao.getCityOrderStatistics(); 
	}
}
