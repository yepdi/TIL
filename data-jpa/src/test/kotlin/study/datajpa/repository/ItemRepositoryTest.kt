package study.datajpa.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.datajpa.entity.Item

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Test
    fun save() {
        val item = Item("A")
        // PK에 값이 있기 때문에 merge로 가버린다 (isNew) 에서 false 로 판단
        itemRepository.save(item)
    }

//    @Transactional
//    @Override
//    public <S extends T> S save(S entity) {
//
//        Assert.notNull(entity, "Entity must not be null.");
//
//        if (entityInformation.isNew(entity)) { <- item을 save 할때는 pk가 0 or null이기 때문에 해당 구문으로
//            em.persist(entity);
//            return entity;
//        } else {
//            return em.merge(entity);
//        }
//    }
}