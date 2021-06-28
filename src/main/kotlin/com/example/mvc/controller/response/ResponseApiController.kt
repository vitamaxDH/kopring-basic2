package com.example.mvc.controller.response

import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    // 1. get 4xx
    // GET http://localhost:8080/api/response?age=10
    @GetMapping
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> {

        age?.let {
            // 1. age == null -> 400 error
            if(age < 20){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값은 20보다 커야합니다.")
            }
        }?: kotlin.run {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값이 누락")
        }

        return ResponseEntity.ok().build()
    }
    /*
        // 1. age == null -> 400 error
        if(age == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값이 누락")
        }

        if (age < 20){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값은 20보다 커야합니다.")
        }

    }
     */

    @PostMapping
    fun postMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(userRequest)
    }

    @PutMapping
    fun putMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id: Int): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }
}