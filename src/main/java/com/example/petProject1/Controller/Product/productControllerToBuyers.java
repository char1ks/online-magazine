package com.example.petProject1.Controller.Product;

import com.example.petProject1.Model.Product.product;
import com.example.petProject1.Model.User.User;
import com.example.petProject1.Security.User_Details;
import com.example.petProject1.Service.Product.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class productControllerToBuyers {
    private productService operations;

    @Autowired
    public void setOperations(productService operations) {
        this.operations = operations;
    }

    @GetMapping
    public String mainPage(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        Page<product> productsPage = operations.getAllProducts(page, size);
        model.addAttribute("productsPage", productsPage);
        return "product/mainPage";
    }
    @GetMapping("/{id}")
    public String concretePage(@PathVariable("id")int id,Model model){
        model.addAttribute("concreteProduct",operations.getConcreteProduct(id));
        return "product/concretePage";
    }
    @GetMapping("/buy/{id}")
    public String buyPage(@PathVariable("id")int productId){
        product product=operations.getConcreteProduct(productId);
        User user=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// Получаем имя текущего пользователя
        Object principal = authentication.getPrincipal();
        if (principal instanceof User_Details) {
            User_Details details = (User_Details) principal;
            user = details.getUser(); // Получаем объект Manager
        }
        if(product.getProductOwner().getId()!=user.getId()){
            operations.deleteProduct(productId);
        }
        return "redirect:/product";
    }
}
