package hello.itemservice.sian;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static Map<Long, Item> store = new HashMap<>();
    private static Long sequence = 0L;

    public ItemRepository() {
        this.store = store;
    }

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long id, Item newItem) {
        Item originItem = findById(id);
        originItem.setItemName(newItem.getItemName());
        originItem.setPrice(newItem.getPrice());
        originItem.setQuantity(newItem.getQuantity());
    }

    public void clear() {
        store.clear();
    }
}
