package com.example.petProject1.Controller.Product;

import com.example.petProject1.Model.Product.product;
import com.example.petProject1.Model.User.User;
import com.example.petProject1.Security.User_Details;
import com.example.petProject1.Service.Product.productService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class productControllerToSellers {
    private productService operations;

    @Autowired
    public void setOperations(productService operations) {
        this.operations = operations;
    }

    //ADD
    @GetMapping("/add")
    public String newPage(@ModelAttribute("newProduct") product product){
        return "product/addPage";
    }
    @PostMapping
    public String newInDB(@ModelAttribute("newProduct") @Valid product product, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "product/addPage";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// Получаем имя текущего пользователя
        Object principal = authentication.getPrincipal();
        if (principal instanceof User_Details) {
            User_Details details = (User_Details) principal;
            User user = details.getUser(); // Получаем объект Manager
            product.setProductOwner(user);
        }
        operations.saveProduct(product);
        return "redirect:/product";
    }
    //EDIT
    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id")int id, Model model){
        model.addAttribute("concreteProduct",operations.getConcreteProduct(id));
        return "product/editPage";
    }
    @PatchMapping("/{id}")
    public String editInDB(@PathVariable("id")int id,@ModelAttribute("concreteProduct") @Valid product product,BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "product/editPage";
        operations.updateProduct(id,product);
        return "redirect:/product";
    }
    //DELETE
    @DeleteMapping("/{id}")
    public String deleteInDB(@PathVariable("id")int id){
        operations.deleteProduct(id);
        return "redirect:/product";
    }
}
