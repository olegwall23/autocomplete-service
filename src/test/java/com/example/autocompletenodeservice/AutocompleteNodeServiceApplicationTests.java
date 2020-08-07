package com.example.autocompletenodeservice;

import com.example.autocompletenodeservice.service.AutocompleteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AutocompleteNodeServiceApplicationTests {

	@Autowired
	private AutocompleteService autocompleteService;

	@Test
	void contextLoads() {
		autocompleteService.add("hello", true);
		assertTrue(autocompleteService.wordExists("hello"));

		autocompleteService.add("hello", true);
		assertTrue(autocompleteService.getWordRank("hello") == 2);


		autocompleteService.add("world", true);
		autocompleteService.add("test", true);
		autocompleteService.add("microsoft", true);
		autocompleteService.add("apple", true);
		autocompleteService.add("hey", true);

		autocompleteService.getRankForAllWords();
		System.out.println("---");
		autocompleteService.getTopSuggestedWords("h", 1);
	}

}
