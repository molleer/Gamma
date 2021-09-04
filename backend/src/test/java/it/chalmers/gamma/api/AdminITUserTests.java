package it.chalmers.gamma.api;

import it.chalmers.gamma.GammaApplication;
import it.chalmers.gamma.factories.MockITUserFactory;

import it.chalmers.gamma.utils.JSONUtils;
import it.chalmers.gamma.utils.ResponseUtils;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = GammaApplication.class)
@ActiveProfiles("test")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class AdminITUserTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private MockITUserFactory mockITUserFactory;

    @Before
    public void setupTests() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @WithUserDetails("admin")
    @Test
    public void testAdminCreateUserAsAdmin() throws Exception {
        testAdminCreateUser(true);
    }

    @WithMockUser
    @Test
    public void testAdminCreateUserAsNonAdmin() throws Exception {
        testAdminCreateUser(false);
    }

    private void testAdminCreateUser(boolean authorized) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(
                "/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(JSONUtils.objectToJSONString(
                        this.mockITUserFactory.generateValidAdminCreateUserRequest()))))
                .andExpect(ResponseUtils.expectedStatus(authorized));
    }

}
