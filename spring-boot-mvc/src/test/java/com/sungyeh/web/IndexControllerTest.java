package com.sungyeh.web;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * IndexControllerTest
 *
 * @author sungyeh
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class IndexControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void loginPage() throws Exception {
        ModelAndView model = mockMvc.perform(
                get("/login")
        ).andExpect(status().isOk()).andReturn().getModelAndView();
        Assertions.assertNotNull(model);
        Assertions.assertNotNull(model.getModel().get("site"));
    }

    @Test
    public void acl() throws Exception {
        mockMvc.perform(
                        get("/acl/user")
                                .accept("application/json")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

}
