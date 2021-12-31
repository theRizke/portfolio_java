package com.greenfoxacademy.webshop.controllers;

import com.greenfoxacademy.webshop.models.ShopItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebShopController {

  List<ShopItem> items = createShop();
  static final double CURRENCY_RATE_TO_EUR = 0.5;

  private List<ShopItem> createShop() {
    List<ShopItem> items = new ArrayList<>();
    items.add(new ShopItem("Running shoes", "Nike running s hoes for every day sport", 1000, 5,
        "Clothes and Shoes"));
    items.add(
        new ShopItem("Printer", "Some HP printer that will print pages", 3000, 2, "Electronics"));
    items.add(new ShopItem("Coca cola", "0.5l standard coke", 25, 0, "Beverages and Snacks"));
    items.add(new ShopItem("Wokin", "Chicken with fried rice and WOKIN sauce", 119, 100,
        "Beverages and Snacks"));
    items.add(new ShopItem("T-Shirt", "Blue with a corgi on a bike", 300, 1, "Clothes and Shoes"));
    return items;
  }


  @RequestMapping(value = "/webshop")
  public String webShop(Model model) {
    model.addAttribute("items", items);
    return "shop";
  }

  @RequestMapping(value = "/only-available")
  public String onlyAvailable(Model model) {
    model.addAttribute("items",
        items.stream().filter(item -> item.getQuantityOfStock() > 0).collect(
            Collectors.toList()));
    return "shop";
  }

  @RequestMapping(value = "/cheapest-first")
  public String cheapestFirst(Model model) {
    model.addAttribute("items",
        items.stream().sorted(Comparator.comparingDouble(ShopItem::getPrice))
            .collect(Collectors.toList()));
    return "shop";
  }

  @RequestMapping(value = "/contains-nike")
  public String containsNike(Model model) {
    model.addAttribute("items", items.stream().filter(
        item -> item.getName().toLowerCase().contains("nike") ||
            item.getDescription().toLowerCase().contains("nike")).collect(Collectors.toList()));
    return "shop";
  }

  @RequestMapping(value = "/average-stock")
  public String averageStock(Model model) {
    double avgPrice =
        items.stream().mapToDouble(ShopItem::getQuantityOfStock).average().getAsDouble();
    model.addAttribute("result", "Average stock: " + avgPrice);
    return "shop-single";
  }

  @RequestMapping(value = "/most-expensive")
  public String mostExpensive(Model model) {
    ShopItem mostExpensiveItem =
        items.stream().max(Comparator.comparingDouble(ShopItem::getPrice)).get();
    model.addAttribute("result", "Most expensive item: " + mostExpensiveItem.getName() + " - " +
        mostExpensiveItem.getPrice());
    return "shop-single";
  }

  @RequestMapping(value = "/more-filters")
  public String moreFilters(Model model) {
    model.addAttribute("items", items);
    return "shop-more-filters";
  }

  @RequestMapping(value = "/search")
  public String search(Model model, @RequestParam String search) {
    model.addAttribute("items", items.stream().filter(
        item -> item.getName().toLowerCase().contains(search) ||
            item.getDescription().toLowerCase().contains(search)).collect(Collectors.toList()));
    return "shop";
  }

  @RequestMapping(value = "/filter-by-type/{type}")
  public String filterByType(Model model, @PathVariable String type) {

    String typeNew = type.split("=")[1];
    System.out.println(typeNew);
    model.addAttribute("items",
        items.stream().filter(item -> item.getType().equals(typeNew)).collect(
            Collectors.toList()));
    return "shop-more-filters";
  }

  @RequestMapping(value = "/price-in-euro")
  public String priceInEuro(Model model) {
    items.forEach(item -> {item.setPrice(item.getPrice() * CURRENCY_RATE_TO_EUR); item.setCurrency("€");});
    model.addAttribute("items", items);
    return "shop-more-filters";
  }

  @RequestMapping(value = "/price-in-original")
  public String priceInOriginal(Model model){
    items.forEach(item -> {item.setPrice(item.getPrice() / CURRENCY_RATE_TO_EUR); item.setCurrency("Kc");});
    model.addAttribute("items", items);
    return "shop-more-filters";
  }

  @RequestMapping(value = "/filter-by-price", params = "above")
  public String filterByPriceAbove(Model model, @RequestParam String value) {
    double valueInDouble = Double.parseDouble(value);
    model.addAttribute("items", items.stream().filter(item -> item.getPrice() > valueInDouble).collect(
        Collectors.toList()) );
    return "shop-more-filters";
  }

  @RequestMapping(value = "/filter-by-price", params = "below")
  public String filterByPriceBelow(Model model, @RequestParam String value) {
    double valueInDouble = Double.parseDouble(value);
    model.addAttribute("items", items.stream().filter(item -> item.getPrice() < valueInDouble).collect(
        Collectors.toList()) );
    return "shop-more-filters";
  }

  @RequestMapping(value = "/filter-by-price", params = "exact")
  public String filterByPriceExact(Model model, @RequestParam String value) {
    double valueInDouble = Double.parseDouble(value);
    model.addAttribute("items", items.stream().filter(item -> item.getPrice() == valueInDouble).collect(
        Collectors.toList()) );
    return "shop-more-filters";
  }
}
