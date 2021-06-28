package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("Hello TEST")
    fun hellTest(){
        mockMvc.perform(
            get("/api/exception/hello")
        ).andExpect(
            status().isBadRequest
        ).andExpect{
            content().string("hello")
        }.andDo(print())
    }

    @Test
    @DisplayName("Get TEST")
    fun getTest(){
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "steve")
        queryParams.add("age", "20")

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams)
        ).andExpect(
            status().isOk
        ).andExpect(
            content().string("steve 20")
        ).andDo(print())

    }

    @Test
    @DisplayName("Get Fail TEST")
    fun getFailTest(){
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "steve")
        queryParams.add("age", "9")

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams)
        ).andExpect(
            status().isBadRequest
        ).andExpect(
            content().contentType("application/json")
        ).andExpect(
            jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
            jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            jsonPath("\$.errors[0].value").value("9")
        ).andDo(print())
    }

    @Test
    @DisplayName("Post TEST")
    fun postTest(){

        val userRequest = UserRequest().apply {
            this.name = "steve"
            this.age = 10
            this.phoneNumber = "010-1111-2222"
            this.address = "경기도 성남시"
            this.email = "steve@gmail.com"
            this.createdAt = "2021-06-25 22:58:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            post("/api/exception")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("\$.name").value("steve")
        ).andExpect(
            jsonPath("\$.age").value("10")
        ).andExpect(
            jsonPath("\$.phoneNumber").value("010-1111-2222")
        ).andExpect(
            jsonPath("\$.email").value("steve@gmail.com")
        ).andExpect(
            jsonPath("\$.address").value("경기도 성남시")
        ).andExpect(
            jsonPath("\$.createdAt").value("2021-06-25 22:58:00")
        ).andDo(
            print()
        )
    }

    @Test
    @DisplayName("Post Fail TEST")
    fun postFailTest() {

        val userRequest = UserRequest().apply {
            this.name = "steve"
            this.age = -1
            this.phoneNumber = "010-1111-2222"
            this.address = "경기도 성남시"
            this.email = "steve@gmail.com"
            this.createdAt = "2021-06-25 22:58:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            post("/api/exception")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().`is`(400)
        ).andDo(
            print()
        )
    }
}