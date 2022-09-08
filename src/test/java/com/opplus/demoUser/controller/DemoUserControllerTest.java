package com.opplus.demoUser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.opplus.demoUser.dto.UserDto;
import com.opplus.demoUser.service.UserService;

@WebMvcTest(DemoUserController.class)
class DemoUserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private final String URL = "/User/";

	@Test
	public void findAll_test() throws Exception {
		List<UserDto> list_usuarios = getUsuarios();
		when(userService.findAll()).thenReturn(list_usuarios);

		mockMvc.perform(get("/User/")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(list_usuarios.size())).andDo(print());

	}
	
	@Test
	public void createUser_test() throws Exception {
		UserDto user = new UserDto("Usuario", "Test", "Test");

	    mockMvc.perform(post("/User/").contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(user)))
	        .andExpect(status().isCreated())
	        .andDo(print());
	}

	private List<UserDto> getUsuarios() {
		List<UserDto> usuarios = new ArrayList<>();
		usuarios.add(new UserDto("Manolo", "Lopez", "Sanchez"));
		usuarios.add(new UserDto("Aitana", "Sanchez", "Gijon"));
		usuarios.add(new UserDto("Clotilda", "Fernandez", "Sandia"));
		return usuarios;

	}

}
