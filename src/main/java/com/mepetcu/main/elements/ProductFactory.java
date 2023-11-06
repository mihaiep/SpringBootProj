package com.mepetcu.main.elements;

import java.util.*;


public class ProductFactory {

    private final List<String> categories = new ArrayList<>();
    private final Map<String, Integer[]> prices = new HashMap<>();
    private final Map<String, String[]> products = new HashMap<>();

    public ProductFactory() {
        categories.addAll(List.of("Grains", "Sweets", "Pastry", "Meat", "Dairy Products", "Fruits", "Vegetables", "Drinks"));

        products.put("Grains", new String[]{"Wheat", "Rye", "Oats", "Corn", "Barley", "Buckwheat", "Rice"});
        products.put("Sweets", new String[]{"Candy", "Elly Beans", "Lollipop", "Bonbons", "Chocolates", "Marshmallow"});
        products.put("Pastry", new String[]{"Pancake", "Muffin", "Biscuit", "Apple Pie", "Blueberry Pie", "Cherry Pie", "Tart"});
        products.put("Meat", new String[]{"Beef", "Pork", "Veal", "Lamb", "Mutton"});
        products.put("Dairy Products", new String[]{"Milk", "Sour Cream", "Butter", "Cheese", "Mozzarella", "Cheddar"});
        products.put("Fruits", new String[]{"Apple", "Pear", "Apricot", "Peach", "Nectarine", "Plum", "Grapes", "Cherry"});
        products.put("Vegetables", new String[]{"Tomato", "Cucumber", "Carrot", "Potato", "Onion", "Lettuce", "Cabbage"});
        products.put("Drinks", new String[]{"Juice", "Tea", "Coffee", "Espresso", "Water", "Lemonade"});

        prices.put("Grains", new Integer[]{100, 500, 1500});
        prices.put("Sweets", new Integer[]{1000, 5000, 15000});
        prices.put("Pastry", new Integer[]{100, 300, 500});
        prices.put("Meat", new Integer[]{1000, 15000, 50000});
        prices.put("Dairy Products", new Integer[]{100, 300, 700});
        prices.put("Fruits", new Integer[]{100, 300, 1200});
        prices.put("Vegetables", new Integer[]{100, 200, 700});
        prices.put("Drinks", new Integer[]{100, 400, 900});
    }

    public Product generateRandomProduct() {
        Random random = new Random(System.nanoTime() * System.currentTimeMillis());
        Product product = new Product();
        int categoryIndex = random.nextInt(categories.size());
        String category = categories.get(categoryIndex);

        int productIndex = random.nextInt(products.get(category).length);
        String name = products.get(category)[productIndex];

        double factor = (double) prices.get(category)[0];
        int minPrice = prices.get(category)[1];
        int maxPrice = prices.get(category)[2];
        double price = (minPrice + random.nextInt(maxPrice - minPrice)) / factor;

        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);

        return product;
    }

}
