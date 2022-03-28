package hello.itemservice.sian;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    private ItemRepository itemRepository = new ItemRepository();

    @BeforeEach
    void beforeEach() {
        itemRepository.clear();
    }

    @Test
    void save() {
        Item item = new Item("itemA", 10000, 10);
        Item saveItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(saveItem.getId());
        assertThat(saveItem).isEqualTo(findItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000,20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> findItems = itemRepository.findAll();
        assertThat(findItems.size()).isSameAs(2);
        assertThat(findItems).contains(item1, item2);
    }

    @Test
    void update() {
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000,20);

        Item saveItem = itemRepository.save(item1);
        Item updateItem = itemRepository.save(item2);
        itemRepository.update(saveItem.getId(), updateItem);

        assertThat(saveItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(saveItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(saveItem.getQuantity()).isEqualTo(updateItem.getQuantity());
        assertThat(saveItem.getId()).isNotEqualTo(updateItem.getId());
    }
}