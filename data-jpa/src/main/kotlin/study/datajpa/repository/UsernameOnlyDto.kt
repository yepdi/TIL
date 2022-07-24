package study.datajpa.repository

import lombok.Getter

class UsernameOnlyDto {
    val username: String

    constructor(username: String) {
        this.username = username
    }
}