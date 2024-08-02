package com.example.loja.models;

import jakarta.persistence.*;
import org.springframework.core.annotation.RepeatableContainers;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produtos extends RepresentationModel<Produtos> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idProduto;
    private String nome;
    private BigDecimal valor;

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
