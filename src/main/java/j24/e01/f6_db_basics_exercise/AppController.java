package j24.e01.f6_db_basics_exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String root(Model model) {
        String sql = "SELECT * FROM products";
        List<Product> products = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                )
        );
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/addItem")
    public String showAddItemForm(Model model) {
        model.addAttribute("product", new Product(null, "", "", 0.0, 0));
        return "addItem";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Product product) {
        String sql = "INSERT INTO products (name, description, price, stock_quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.name(), product.description(), product.price(), product.stockQuantity());
        return "redirect:/";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "redirect:/";
    }

    @GetMapping("/editItem/{id}")
    public String showEditItemForm(@PathVariable Long id, Model model) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                )
        );
        model.addAttribute("product", product);
        return "editItem";
    }

    @PostMapping("/editItem")
    public String editItem(@ModelAttribute Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock_quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.name(), product.description(), product.price(), product.stockQuantity(), product.id());
        return "redirect:/";
    }
}
