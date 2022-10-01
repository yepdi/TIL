package core.studyspring.lifecycle

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class NetworkClient(
    private var url: String?
//): InitializingBean, DisposableBean {
) {
    constructor() : this(null) {
        println("생성자 호출 url = $url")
    }

    fun setUrl(url: String) {
        this.url = url
    }

    // 서비스 시작시 호출

    fun connect() {
        println("connect : $url")
    }

    fun call(message: String) {
        println("call $url message $message")
    }

    // 서비스 종료시 호출
    fun disconnect() {
        println("close $url")
    }

    // 의존관계 주입이 끝나면 호출
//
//    생성자 호출 url = null
//    afterPropertiesSet
//    connect : http://hello-spring.dev
//    call http://hello-spring.dev message 초기화 연결 메시지
//    20:35:43.991 [Test worker] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@10d307f1, started on Sat Oct 01 20:35:43 KST 2022
//    destroy
//    close http://hello-spring.dev
//    override fun afterPropertiesSet() {
    @PostConstruct
    fun init() {
        println("afterPropertiesSet")
        connect()
        call("초기화 연결 메시지")
    }

//    override fun destroy() {
    @PreDestroy
    fun close() {
        println("destroy")
        disconnect()
    }
}