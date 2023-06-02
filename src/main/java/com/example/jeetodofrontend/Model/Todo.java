package com.example.jeetodofrontend.Model;

public class Todo {
    public Todo(int id, String description, int statut) {
        this.id = id;
        this.description = description;
        this.statut = statut;
    }

    public Todo(String description, int statut) {
        this.description = description;
        this.statut = statut;
    }

    public Todo() {
    }

    private int id;
    private String description;
    private int statut;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", statut=" + statut +
                '}';
    }
}
