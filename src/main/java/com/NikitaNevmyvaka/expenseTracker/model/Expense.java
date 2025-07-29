package com.NikitaNevmyvaka.expenseTracker.model;

import com.NikitaNevmyvaka.expenseTracker.service.ExpenseService;

import java.time.LocalDate;
import java.util.Objects;

public class Expense {

;

    private int id;
    private LocalDate date;
    private String name;
    private String description;
    private ExpenseService.Category category;
    private int cost;

    public Expense(int id, LocalDate date, String name, String description, ExpenseService.Category category, int cost){
        this.id= id;
        this.date=date;
        this.name= name;
        this.category= category;
        this.description=description;
        this.cost=cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ExpenseService.Category getCategory() {
        return category;
    }

    public void setCategory(ExpenseService.Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", date=" + date + '\''+
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", cost=" + cost+"₸" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expense expense)) return false;
        return cost == expense.cost && Objects.equals(date, expense.date) && Objects.equals(name, expense.name) && Objects.equals(description, expense.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name, description, cost);
    }
}
