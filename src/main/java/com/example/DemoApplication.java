package com.example;

import com.example.entity.BillCategory;
import com.example.entity.Biller;
import com.example.entity.Product;
import com.example.repository.BillCategoryRepository;
import com.example.repository.BillerRepository;
import com.example.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			BillCategoryRepository billCategoryRepository,
			BillerRepository billerRepository,
			ProductRepository productRepository) {
		return args -> {
			// Initialize Bill Categories
			BillCategory electricCategory = BillCategory.builder()
					.name("Electric")
					.build();
			billCategoryRepository.save(electricCategory);

			BillCategory airtimeCategory = BillCategory.builder()
					.name("Airtime")
					.build();
			billCategoryRepository.save(airtimeCategory);

			// Initialize Billers
			Biller ikejaElectric = Biller.builder()
					.name("Ikeja Electric")
					.category(electricCategory)
					.build();
			billerRepository.save(ikejaElectric);

			Biller airtel = Biller.builder()
					.name("Airtel")
					.category(airtimeCategory)
					.build();
			billerRepository.save(airtel);

			Biller mtn = Biller.builder()
					.name("MTN")
					.category(airtimeCategory)
					.build();
			billerRepository.save(mtn);

			// Initialize Products
			Product prepaidMeterRecharge = Product.builder()
					.name("Prepaid meter recharge")
					.biller(ikejaElectric)
					.build();
			productRepository.save(prepaidMeterRecharge);

			Product airtimeTopUpAirtel = Product.builder()
					.name("Airtime Top-up")
					.biller(airtel)
					.build();
			productRepository.save(airtimeTopUpAirtel);

			Product airtimeTopUpMTN = Product.builder()
					.name("Airtime Top-up")
					.biller(mtn)
					.build();
			productRepository.save(airtimeTopUpMTN);
		};
	}
}