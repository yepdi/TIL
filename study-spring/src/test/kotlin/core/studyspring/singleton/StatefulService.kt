package core.studyspring.singleton

class StatefulService {

//    private var price: Int = 0 // 10000 -> 20000

    fun order(name: String, price: Int): Int {
        println("name = $name price = $price")
//        this.price = price
        return price;
    }

//    fun getPrice() = price
}