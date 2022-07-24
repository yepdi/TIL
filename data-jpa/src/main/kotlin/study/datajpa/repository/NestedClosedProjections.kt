package study.datajpa.repository

interface NestedClosedProjections {
    // username만 가져오지만
    fun getUsername(): String
    // 전체 entity를 가져온다
    fun getTeam(): TeamInfo

    interface TeamInfo {
        fun getName(): String
    }
}