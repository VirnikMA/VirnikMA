package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.ImageRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.util.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductValidator productValidator;
    private final ProductService productService;

    private final CategoryRepository categoryRepository;
    private final PersonService personService;
    private final OrderService orderService;

    private final ImageRepository imageRepository;

    public String search_hz;

    @Autowired
    public AdminController(ProductValidator productValidator, ProductService productService, CategoryRepository categoryRepository, PersonService personService, OrderService orderService, ImageRepository imageRepository) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.personService = personService;
        this.orderService = orderService;
        this.imageRepository = imageRepository;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('')")
//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('')")

    // Метод по отображению главной страницы администратора с выводом товаров
    @GetMapping()
    public String admin(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();

        if(role.equals("ROLE_USER")){
            return "redirect:/index";
        }
        model.addAttribute("products", productService.getAllProduct());

        return "admin/admin";
    }

    // Метод по отображению формы добавление
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
//        System.out.println(categoryRepository.findAll().size());
        return "product/addProduct";
    }

    // Метод по добавлению объекта с формы в таблицу product
//    @PostMapping("/product/add")
//    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five) throws IOException {

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four) throws IOException {
        productValidator.validate(product, bindingResult);
        if(bindingResult.hasErrors()){
            return "product/addProduct";
        }
        // Проверка на пустоту файла
        if(file_one != null){
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_two != null){
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_three != null){
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_four != null){
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
//        if(!file_five.isEmpty()){
//            System.out.println("!!!!!!!!!!!!!!!!"+file_five);
//            // Дирректория по сохранению файла
//            File uploadDir = new File(uploadPath);
//            // Если данной дирректории по пути не сущетсвует
//            if(!uploadDir.exists()){
//                // Создаем данную дирректорию
//                uploadDir.mkdir();
//            }
//            // Создаем уникальное имя файла
//            // UUID представляет неищменный универсальный уникальный идентификатор
//            String uuidFile = UUID.randomUUID().toString();
//            // file_one.getOriginalFilename() - наименование файла с формы
//            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
//            // Загружаем файл по указаннопу пути
//            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(resultFileName);
//            product.addImageProduct(image);
//        }

        productService.saveProduct(product);
        return "redirect:/admin";
    }

    // Метод по удалению товара по id
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    // Метод по получению товара по id и отображение шаблона редактирования
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("editProduct", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("editProduct") Product product, @PathVariable("id") int id){
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ФОТО
    @GetMapping("/product/{id_product}/image/delete/{id_img}")
    public String deleteImage(@PathVariable("id_product") int id_product, @PathVariable("id_img") int id_img) {

        Product product = productService.getProductId(id_product);
        if(product.getImageList().size() > 1) {
            imageRepository.deleteImage(id_img);
        }
        return "redirect:/admin/product/edit/"+id_product;
    }

    //добавить фотку существующему товару
    @PostMapping("/product/{id_product}/image/add")
    public String addImage(@PathVariable("id_product") int id_product, BindingResult bindingResult,
                           @RequestParam("file_one") MultipartFile file_one) throws IOException {

        Product product = productService.getProductId(id_product);
//        productValidator.validate(product, bindingResult);
//        if(bindingResult.hasErrors()){
//            return "redirect:/admin/product/edit/{id_product}";
//        }
        // Проверка на пустоту файла
        if(file_one != null){
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }
        productService.updateProduct(id_product, product);
        return "redirect:/admin/product/edit/{id_product}";
    }




    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ЮЗЕРЫ

    // Метод возвращает страницу с выводом пользователей и кладет объект пользователя в модель
    @GetMapping("/persons")
    public String getPersons(Model model){;
        model.addAttribute("persons", personService.getAllPerson());
        return "person/persons";
    }

    // Метод принимает объект с формы и обновляет пользователя
    @PostMapping("/persons/{id}")
    public String editPerson(@PathVariable("id") int id, @RequestParam("role") String role){

        Person person_role = personService.getPersonById(id);
        personService.updateRole(role,person_role);
        return "redirect:/admin/persons";
    }

//!!!!!!!!!!!!!!!!!!!!!!ЗАКАЗЫ

    // метод возвращает страницу выводом заказов и кладет объект заказа в модель
    @GetMapping("/ordersUsers")
    public String ordersUsers(@PathVariable("search") String search,Model model) {
        if(!search.isEmpty()) {
            model.addAttribute("orders", orderService.findByFourChar(search));
        }else {
            model.addAttribute("orders", orderService.getAllOrder());
        }
        model.addAttribute("search", search);
        search_hz = search;
        return "/admin/ordersUsers";
    }

    @PostMapping("/ordersUsers")
    public String searchOrders(@RequestParam("search") String search, Model model){
        if(!search.isEmpty()) {
            model.addAttribute("orders", orderService.findByFourChar(search));
        }else {
            model.addAttribute("orders", orderService.getAllOrder());
        }
        model.addAttribute("search", search);
        search_hz = search;
        return "/admin/ordersUsers";
    }

    // Метод принимает объект с формы и обновляет строку заказа
    @PostMapping("/ordersUsers/{id}")
    public String editOrder(@PathVariable("id") int id, @RequestParam("status") Status status, Model model){

        Order order_status = orderService.getOrderById(id);
        orderService.updateStatus(status,order_status);

        if(!search_hz.isEmpty()) {
            model.addAttribute("orders", orderService.findByFourChar(search_hz));
        }else {
            model.addAttribute("orders", orderService.getAllOrder());
        }
        model.addAttribute("search", search_hz);

        return "/admin/ordersUsers";
    }

}


