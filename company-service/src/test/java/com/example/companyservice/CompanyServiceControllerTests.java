package com.example.companyservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 整合测试，需要启动Spring上下文
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyServiceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testCompanyId;
    private Long testJobPostId;
    private Long testEmployId;

    @BeforeEach
    public void setup() throws Exception {
        // 1. 新建一个公司，存储ID，后续测试用
        String companyJson = "{"
                + "\"name\":\"TestTech Ltd.\","
                + "\"industry\":\"Software\","
                + "\"address\":\"123 Tech Road\","
                + "\"website\":\"https://testtech.com\","
                + "\"description\":\"A leading software company.\","
                + "\"verified\":true"
                + "}";

        String companyResponse = mockMvc.perform(post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // 假设返回格式 {"data": {"id": 1, ...}}
        testCompanyId = objectMapper.readTree(companyResponse).path("data").path("id").asLong();

        // 2. 新建一个职位
        String jobPostJson = "{"
                + "\"companyId\":" + testCompanyId + ","
                + "\"title\":\"Java Backend Developer\","
                + "\"description\":\"Responsible for backend services.\","
                + "\"location\":\"Remote\","
                + "\"salary\":\"15k-20k\","
                + "\"status\":\"OPEN\""
                + "}";

        String jobPostResponse = mockMvc.perform(post("/jobpost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobPostJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        testJobPostId = objectMapper.readTree(jobPostResponse).path("data").path("id").asLong();

        // 3. 新建一条投递记录
        String employJson = "{"
                + "\"companyId\":" + testCompanyId + ","
                + "\"jobPostId\":" + testJobPostId + ","
                + "\"jobSeekerId\":101,"
                + "\"resumeId\":501,"
                + "\"status\":\"PENDING\""
                + "}";

        String employResponse = mockMvc.perform(post("/employ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        testEmployId = objectMapper.readTree(employResponse).path("data").path("id").asLong();
    }

    @Test
    public void testGetCompanyById() throws Exception {
        mockMvc.perform(get("/company/" + testCompanyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("TestTech Ltd."));
    }

    @Test
    public void testUpdateJobPostStatus() throws Exception {
        String updateStatusJson = "{"
                + "\"status\":\"CLOSED\""
                + "}";

        mockMvc.perform(put("/jobpost/" + testJobPostId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateStatusJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("CLOSED"));
    }

    @Test
    public void testEmployStatusChange() throws Exception {
        String updateEmployStatusJson = "{"
                + "\"status\":\"ACCEPTED\""
                + "}";

        mockMvc.perform(put("/employ/" + testEmployId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployStatusJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("ACCEPTED"));
    }

    @Test
    public void testDeleteCompany() throws Exception {
        mockMvc.perform(delete("/company/" + testCompanyId))
                .andExpect(status().isOk());

        // 删除后查询应404或者空
        mockMvc.perform(get("/company/" + testCompanyId))
                .andExpect(status().isNotFound());
    }
}
