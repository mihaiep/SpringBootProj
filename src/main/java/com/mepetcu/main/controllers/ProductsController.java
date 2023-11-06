package com.mepetcu.main.controllers;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import com.mepetcu.main.elements.Result;
import com.mepetcu.main.elements.Product;
import org.springframework.data.domain.Page;
import com.mepetcu.main.services.ProductService;
import org.springframework.web.bind.annotation.*;
import com.mepetcu.main.elements.ProductHTMLTable;
import com.mepetcu.main.services.ThymeleafService;
import com.mepetcu.main.interfaces.ThymeleafElement;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(path = "/products")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductsController extends GenericController {

    private final ThymeleafService thymeleafService;
    private final ProductService productService;
    private final ProductHTMLTable tableBuilder;
    private String iframeURL;

    @Autowired
    public ProductsController(@Qualifier("resultService") ThymeleafService service, ProductService productService) {
        this.thymeleafService = service;
        this.productService = productService;
        this.tableBuilder = new ProductHTMLTable();
        iframeURL = "/products/page=1";
    }

    @GetMapping(path = "")
    @SuppressWarnings("rawtypes")
    public String getProductsPage(Model model, Authentication authentication) {
        setupPage(model, authentication);
        model.addAttribute("iframeURL", iframeURL);
        ThymeleafElement element = thymeleafService.getNext();
        if (element != null) {
            if (element.getWebObject() == null) model.addAttribute(element.getID(), element.getValue());
            else {
                Object webObject = element.getWebObject();
                if (webObject instanceof List) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Object obj : (List) webObject)
                        stringBuilder.append(obj.toString()).append("\n");
                    model.addAttribute(element.getID(), stringBuilder.toString());
                }
            }
        }
        return "products.html";
    }

    @ResponseBody
    @PostMapping(path = "/add/random")
    public void generateRandomProducts(@RequestParam Integer productsNum, HttpServletResponse httpServlet) throws IOException {
        if (productsNum != null) {
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < productsNum; i++) {
                Product p = productService.generateRandomProduct();
                productList.add(p);
            }
            thymeleafService.add(Result.getInstance("generateRandomProducts", productList));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @PostMapping(path = "/add")
    public void createNewProduct(@RequestParam String newName, @RequestParam String newCategory, @RequestParam Double newPrice, HttpServletResponse httpServlet) throws IOException {
        Product p = productService.insertProduct(newName, newCategory, newPrice);
        thymeleafService.add(new Result("createNewProduct", p.toString()));
        httpServlet.sendRedirect("/products");
    }


    @ResponseBody
    @PostMapping(path = "/search/id")
    public void getProductById(@RequestParam Integer productId, HttpServletResponse httpServlet) throws IOException {
        if (productId != null) {
            Product p = productService.findProductById(productId);
            if (p != null)
                thymeleafService.add(new Result("getProductById", p.toString()));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @PostMapping(path = "/search/name")
    public void getProductsByName(@RequestParam String productName, HttpServletResponse httpServlet) throws IOException {
        if (productName != null) {
            thymeleafService.add(Result.getInstance("getProductsByName", productService.findProductByName(productName)));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @PostMapping(path = "/search/category")
    public void getProductsByCategory(@RequestParam String productCategory, HttpServletResponse httpServlet) throws IOException {
        if (productCategory != null) {
            thymeleafService.add(Result.getInstance("getProductsByCategory", productService.findProductByCategory(productCategory)));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @PostMapping(path = "/search/price")
    public void getProductsByPrice(@RequestParam String productPrice, HttpServletResponse httpServlet) throws IOException {
        if (productPrice != null) {
            thymeleafService.add(Result.getInstance("getProductsByPrice", productService.findProductByPrice(productPrice)));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @PostMapping(path = "/delete/id")
    public void deleteProductByID(@RequestParam String deleteIds, HttpServletResponse httpServlet) throws IOException {
        if (deleteIds != null) {
            thymeleafService.add(Result.getInstance("deleteProductByID", productService.deleteByIDs(deleteIds)));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @PostMapping(path = "/delete/name")
    public void deleteProductByName(@RequestParam String deleteName, HttpServletResponse httpServlet) throws IOException {
        if (deleteName != null) {
            thymeleafService.add(Result.getInstance("deleteProductByName", productService.deleteByName(deleteName)));
        }
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @GetMapping(path = "/json/page={pageNumber}")
    public List<Product> getPageAsJson(@PathVariable int pageNumber) {
        return productService.getDefaultPage(pageNumber).getContent();
    }

    @ResponseBody
    @GetMapping(path = "/apply")
    public void applyTableFilters(@RequestParam String filter, @RequestParam String filterBy, @RequestParam String sort, @RequestParam String sortBy, HttpServletResponse httpServlet) throws IOException {
        iframeURL = "/products/page=1?filter=" + filter + "&filterBy=" + filterBy + "&sort=" + sort + "&sortBy=" + sortBy;
        iframeURL = iframeURL.replaceAll("%", "%25");
        httpServlet.sendRedirect("/products");
    }

    @ResponseBody
    @GetMapping(path = "/page={pageNumber}")
    public String setupTable(@PathVariable Integer pageNumber, @Nullable @RequestParam String filter, @Nullable @RequestParam String filterBy, @Nullable @RequestParam String sort, @Nullable @RequestParam String sortBy) {
        tableBuilder.clear();
        if (filter == null || filter.isBlank()) {
            Page<Product> products;
            if ((sortBy == null || sortBy.equalsIgnoreCase("id")) && (sort == null || sort.equalsIgnoreCase("asc"))) {
                products = productService.getDefaultPage(pageNumber - 1);
                tableBuilder.setReqParams("", "", "asc", "id");
            } else {
                boolean isAscending = !sort.equals("dsc");
                products = productService.getPageSorted(pageNumber - 1, sortBy, isAscending);
                tableBuilder.setReqParams("", "", sort, sortBy);
            }
            tableBuilder.addProducts(products.getContent());
            return tableBuilder.build(pageNumber, products.getTotalPages(), products.hasNext());
        } else {
            boolean isAscending = !sort.equals("dsc");
            Page<Product> products = productService.getPageFilteredAndSorted(pageNumber - 1, filter, filterBy, sortBy, isAscending);
            tableBuilder.setReqParams(filter, filterBy, sort, sortBy);
            tableBuilder.addProducts(products.getContent());
            return tableBuilder.build(pageNumber, products.getTotalPages(), products.hasNext());
        }
    }
}
