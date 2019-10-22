package com.akshay.ninjagold;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller

public class NinjaGoldApplication {

	public static void main(String[] args) {
		SpringApplication.run(NinjaGoldApplication.class, args);
	}
	
	@Autowired
	private HttpSession session;
	private ArrayList<String> dates = new ArrayList<String>();
	
	
	@RequestMapping("/")
	public String index(Model model) {
		if(session.getAttribute("gold") == null) {
			session.setAttribute("gold", 0);
		}
		return "index.jsp";
	}
	
	
	@PostMapping("/process-money")
	public String processMoney(@RequestParam ("building")String building, Model model) { 
		
		Random random = new Random();
		Integer gold = (Integer) session.getAttribute("gold");
		
		LocalDate ld = LocalDate.now();
		Month month = ld.getMonth();
		DayOfWeek dow = ld.getDayOfWeek();
		int dayOfMonth = ld.getDayOfMonth();
		int year = ld.getYear();
		String date = String.format("(%s, the %dst of %s,  %d)", dow, dayOfMonth, month, year);
		
		if(building.equals("farm")) {
			Integer earn = random.nextInt((20-10) + 1) + 10;
			System.out.printf("\nEarn : %d", earn);
			gold += earn;
			session.setAttribute("gold", gold);
			String info = "You entered a " + building + " and earned " + earn + "." + date;
			dates.add(info);
			Collections.reverse(dates);
			session.setAttribute("dates", dates);
		}
		
		else if (building.equals("cave")) {
			Integer earn = random.nextInt((10-5) + 1) + 5;
			gold += earn;
			session.setAttribute("gold", gold);
			String info = "You entered a " + building + " and earned " + earn + "." + date;
			dates.add(info);
			Collections.reverse(dates);
			session.setAttribute("dates", dates);
		}
		
		else if (building.equals("house")) {
			Integer earn = random.nextInt((5-2) + 1) + 2;
			gold += earn;
			session.setAttribute("gold", gold);
			String info = "You entered a " + building + " and earned " + earn + "." + date;
			dates.add(info);
			Collections.reverse(dates);
			session.setAttribute("dates", dates);
		}
		else {
			Integer earn = random.nextInt((50-0) + 1) + 0;
			if (earn % 2 == 0) {
				gold += earn;
				session.setAttribute("gold", gold);
				String info = "You entered a " + building + " and earned " + earn + "." + date;
				dates.add(info);
				Collections.reverse(dates);
				session.setAttribute("dates", dates);
			}
			else {
				gold -= earn;
				session.setAttribute("gold", gold);
				String info = "You entered a " + building + " and lost " + earn + "." + date;
				dates.add(info);
				Collections.reverse(dates);
				session.setAttribute("dates", dates);
			}
		}
			
		System.out.printf("\nGold : %d", gold);
		
		return "index.jsp";
	}
	
	
}
