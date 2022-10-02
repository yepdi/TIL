package core.studyspring.web

import core.studyspring.common.MyLogger
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service
//
//여기서 중요한점이 있다.
// request scope를 사용하지 않고 파라미터로 이 모든 정보를 서비스 계층에 넘긴다면, 파라미터가 많아서 지저분해진다. 더
// 문제는 requestURL 같은 웹과 관련된 정보가 웹과 관련없는 서비스 계층까지 넘어가게 된다. 웹과 관련된 부분은 컨트롤러까지만 사용해야 한다.
// 서비스 계층은 웹 기술에 종속되지 않고, 가급적 순수하게 유지하는 것이 유지보수 관점에서 좋다.
@Service
class LogDemoService(
//    private val myLoggerProvider: ObjectProvider<MyLogger>
    val myLogger: MyLogger
) {
    fun logic(id: String) {
//        val myLogger = myLoggerProvider.getObject()
        myLogger.log("service id = $id")
    }
}
