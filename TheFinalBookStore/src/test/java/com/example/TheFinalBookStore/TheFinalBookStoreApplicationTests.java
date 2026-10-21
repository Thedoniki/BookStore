package com.example.TheFinalBookStore;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Kräver en körbar databas och miljövariablerna DB_URL, DB_USERNAME och DB_PASSWORD.
// Aktivera när en testdatabas finns.
@Disabled("Kräver databasanslutning via miljövariabler")
@SpringBootTest
class TheFinalBookStoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
