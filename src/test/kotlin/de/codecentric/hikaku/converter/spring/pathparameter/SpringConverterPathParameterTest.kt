package de.codecentric.hikaku.converter.spring.pathparameter

import de.codecentric.hikaku.converter.spring.SpringConverter
import de.codecentric.hikaku.endpoints.Endpoint
import de.codecentric.hikaku.endpoints.HttpMethod.*
import de.codecentric.hikaku.endpoints.PathParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import kotlin.test.assertFailsWith

class SpringConverterPathParameterTest {

    @Nested
    @WebMvcTest(PathParameterNamedByVariableController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class PathParameterNamedByVariableTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by variable name`() {
            //given
            val specification: Set<Endpoint> = setOf(
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
                            pathParameters = setOf(
                                PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    ),
                    Endpoint("/todos/{id}", OPTIONS),
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = HEAD,
                            pathParameters = setOf(
                                PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    )
            )

            //when
            val implementation = SpringConverter(context)

            //then
            assertThat(implementation.conversionResult).containsExactlyInAnyOrderElementsOf(specification)
        }
    }

    @Nested
    @WebMvcTest(PathParameterNamedByValueAttributeController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class PathParameterNamedByValueAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by 'value' attribute`() {
            //given
            val specification: Set<Endpoint> = setOf(
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
                            pathParameters = setOf(
                                    PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    ),
                    Endpoint("/todos/{id}", OPTIONS),
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = HEAD,
                            pathParameters = setOf(
                                    PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    )
            )

            //when
            val implementation = SpringConverter(context)

            //then
            assertThat(implementation.conversionResult).containsExactlyInAnyOrderElementsOf(specification)
        }
    }

    @Nested
    @WebMvcTest(PathParameterNamedByNameAttributeController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class PathParameterNamedByNameAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by 'name' attribute`() {
            //given
            val specification: Set<Endpoint> = setOf(
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = GET,
                            pathParameters = setOf(
                                    PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    ),
                    Endpoint("/todos/{id}", OPTIONS),
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = HEAD,
                            pathParameters = setOf(
                                    PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    )
            )

            //when
            val implementation = SpringConverter(context)

            //then
            assertThat(implementation.conversionResult).containsExactlyInAnyOrderElementsOf(specification)
        }
    }

    @Nested
    @WebMvcTest(PathParameterHavingBothValueAndNameAttributeController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class PathParameterHavingBothValueAndNameAttributeTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter name defined by both 'value' and 'name' attribute`() {
            assertFailsWith<IllegalStateException> {
                SpringConverter(context).conversionResult
            }
        }
    }

    @Nested
    @WebMvcTest(PathParameterSupportedForOptionsIfExplicitlyDefinedController::class, excludeAutoConfiguration = [ErrorMvcAutoConfiguration::class])
    inner class PathParameterSupportedForOptionsIfExplicitlyDefinedTest {
        @Autowired
        lateinit var context: ConfigurableApplicationContext

        @Test
        fun `path parameter are supported for OPTIONS if defined explicitly`() {
            //given
            val specification: Set<Endpoint> = setOf(
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = OPTIONS,
                            pathParameters = setOf(
                                    PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    ),
                    Endpoint(
                            path = "/todos/{id}",
                            httpMethod = HEAD,
                            pathParameters = setOf(
                                    PathParameter("id")
                            ),
                            produces = setOf(APPLICATION_JSON_UTF8_VALUE)
                    )
            )

            //when
            val implementation = SpringConverter(context)

            //then
            assertThat(implementation.conversionResult).containsExactlyInAnyOrderElementsOf(specification)
        }
    }
}