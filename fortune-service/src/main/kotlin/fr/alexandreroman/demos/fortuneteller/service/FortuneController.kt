/*
 * Copyright (c) 2018 Pivotal Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.alexandreroman.demos.fortuneteller.service

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress
import javax.annotation.PostConstruct

/**
 * REST controller exposing fortunes to other microservices.
 */
@RestController
class FortuneController(private val fortuneService: FortuneService) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var host: String

    @PostConstruct
    private fun init() {
        // Get host name once and for all.
        host = InetAddress.getLocalHost().canonicalHostName
    }

    @GetMapping("/v1/random")
    fun getRandomFortune(): FortuneResponse {
        val fortune = fortuneService.randomFortune()
        logger.info("Random fortune: {}", fortune)
        return FortuneResponse(fortune, host)
    }
}

/**
 * Class holding a fortune and the host where this response was made.
 */
data class FortuneResponse(val text: String, val host: String)
