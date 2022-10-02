package core.studyspring.common

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
// HTTP 요청당 하나씩 생성되고 HTTP 요청이 끝나는 시점에 소멸됨
// Proxy 사용시 이렇게 하면 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.
class MyLogger {
    private lateinit var uuid: String
    private lateinit var requestUrl: String

    fun setRequestUrl(requestUrl: String) {
        this.requestUrl = requestUrl
    }

    fun log(message: String) {
        println("[$uuid][$requestUrl] $message")
    }

    @PostConstruct
    fun init() {
        // 빈이 생성되는 시점에 uuid 생성하고 저장
        this.uuid = UUID.randomUUID().toString()
        println("[$uuid] request scope bean create: $this")
    }

    @PreDestroy
    fun close() {
        println("[$uuid] request scope bean close: $this")
    }
}