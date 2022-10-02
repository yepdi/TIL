package core.studyspring.web

import core.studyspring.common.MyLogger
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
class LogDemoController(
    val logDemoService: LogDemoService,
    val myLogger: MyLogger
//    val myLoggerProvider: ObjectProvider<MyLogger>
) {

//    .springframework.beans.factory.support.ScopeNotActiveException: Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread;
//    request scope이지만 http request가 들어오지 않았기 때문에 에러 발생. 생존 범위가 아닌데 내놓으라고 한다
    @RequestMapping("log-demo")
    @ResponseBody // responseBody면 문자열 그대로 내보낼 수 있다
    fun logDemo(request: HttpServletRequest): String {
    // http request가 살아있는 상황에서 Request가 오기 때문에 괜찮다
//        val myLogger = myLoggerProvider.getObject()
        val requestUrl = request.requestURL.toString()
        myLogger.setRequestUrl(requestUrl)

        myLogger.log("controller test")
        logDemoService.logic("testId")
        return "OK"
    }
}