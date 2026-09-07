package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldSpringTestApplicationTests2 {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void apiEsempioTest() throws Exception {
		this.mockMvc.perform(get("/api/esempio")).
		andDo(print()).
		andExpect(content().string("hello2"));
	}

	@Test
	public void ricercaPerIdTest() throws Exception {
		this.mockMvc.perform(get("/api/cercaid/1")).
		andDo(print()).
		andExpect(jsonPath("$.countryName", is("Italia")));
	}
}
