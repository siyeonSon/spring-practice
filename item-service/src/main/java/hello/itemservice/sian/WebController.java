package hello.itemservice.sian;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequiredArgsConstructor  // ItemRepository 의 생성자 주입 생략 가능
public class WebController {

    private final ItemRepository itemRepository;

    @GetMapping("/items")
    public String itemsGet(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/item/add")
    public String addItemGet() {
        return "addForm";
    }

    @PostMapping("/item/add")
    public String addItemPost(@RequestParam String itemName,
                              @RequestParam int price,
                              @RequestParam int quantity,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        Item item = itemRepository.save(new Item(itemName, price, quantity));
        model.addAttribute("item", item);
        redirectAttributes.addAttribute("itemId", item.getId());  // return redirect: 에서 치환되어 들어감
        redirectAttributes.addAttribute("status", true);  // 쿼리 파라미터 형식으로 들어감
        return "redirect:{itemId}";
    }

    @GetMapping("/item/{itemId}")
    public String detailItemGet(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/item/{itemId}/edit")
    public String editItemGet(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "editForm";
    }

    @PostMapping("/item/{itemId}/edit")
    public String editItemPost(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/item/{itemId}";
    }

    @PostConstruct
    void init() {
        itemRepository.save(new Item("itemA", 10000,10));
        itemRepository.save(new Item("itemB", 20000,20));
    }
}
