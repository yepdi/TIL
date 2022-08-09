package core.studyspring.singleton

class SingletonService {
    companion object {
        //    1. Static 영역에 객체 instance를 미리 하나 생성해서 올려둔다
        private val instance = SingletonService()
        //    2. 객체 인스턴스가 필요하면 getInstance() 메서드를 통해서만 조회할 수 있다. 같은 인스턴스 반환
        fun getInstance(): SingletonService = instance
    }
    //    3. 딱 1개의 객체 인스턴스만 존재해야 하므로 생성자를 private으로 막아서 외부에 new 키워드로 객체 인스턴스가 생성되는 것을 막는다
    private constructor()

    fun logic() {
        println("싱글톤 객체 로직 호출")
    }
}