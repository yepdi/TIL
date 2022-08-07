package core.studyspring.beanfind

import core.studyspring.discount.DiscountPolicy
import core.studyspring.discount.FixDiscountPolicy
import core.studyspring.discount.RateDiscountPolicy
import core.studyspring.member.MemoryMemberRepository
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class ApplicationContextExtendsFindTest {

    val ac: AnnotationConfigApplicationContext = AnnotationConfigApplicationContext(TestConfig::class.java)

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면, 중복 오류가 발생한다")
    fun findBeanByParentTypeDuplicate() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException::class.java){
            ac.getBean(DiscountPolicy::class.java)
        }
    }

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    fun findBeanByParentTypeBeanName() {
        val rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy::class.java)
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy::class.java)
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    fun findBeanBySubType() {
        val bean = ac.getBean(RateDiscountPolicy::class.java)
        assertThat(bean).isInstanceOf(RateDiscountPolicy::class.java)
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    fun findAllBeansByParentType() {
        val beansOfType = ac.getBeansOfType(DiscountPolicy::class.java)
        assertThat(beansOfType.size).isEqualTo(2)
        for (key in beansOfType.keys) {
            println("key = $key value = ${beansOfType.get(key)}")
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    fun findAllBeanByObjectType() {
        val beansOfType = ac.getBeansOfType(Object::class.java)
        for (key in beansOfType.keys) {
            println("key = $key value = ${beansOfType.get(key)}")
        }
    }

    @Configuration
    class TestConfig {
        companion object Factory {
            @Bean
            fun rateDiscountPolicy() = RateDiscountPolicy()

            @Bean
            fun fixDiscountPolicy() = FixDiscountPolicy()
        }
    }

}